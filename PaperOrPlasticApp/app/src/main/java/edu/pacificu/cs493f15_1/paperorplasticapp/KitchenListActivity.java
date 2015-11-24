/**************************************************************************************************
 *   File:     KitchenListActivity.java
 *   Author:   Abigail Jones, Lauren Sullivan, Evan Heydemann
 *   Date:     10/28/15
 *   Class:    Capstone/Software Engineering
 *   Project:  PaperOrPlastic Application
 *   Purpose:  This activity will be the activity that is opened when the user selects the
 *             kitchen list button from the continue activity
 ***************************************************************************************************/

package edu.pacificu.cs493f15_1.paperorplasticapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import edu.pacificu.cs493f15_1.paperorplasticjava.KitchenList;
import edu.pacificu.cs493f15_1.paperorplasticjava.KitchenLists;
import edu.pacificu.cs493f15_1.paperorplasticjava.ListItem;
import edu.pacificu.cs493f15_1.paperorplasticjava.PoPList;


/***************************************************************************************************
 *   Class:         KitchenListActivity
 *   Description:   Creates KitchenListActivity class that controls what occurs when the user
 *                  selects the kitchen list option from the continue activity. Specifically
 *                  contains the list functionality.
 *   Parameters:    N/A
 *   Returned:      N/A
 **************************************************************************************************/
public class KitchenListActivity extends FragmentActivity implements ListDFragment.EditNameDialogListener {
    private Button mbAddList, mbAddItem;
    private Spinner mGroupBySpinner;
    private ArrayList<TabHost.TabSpec> list = new ArrayList<TabHost.TabSpec>(); /* for later when you want to delete tabs?*/
    private KitchenLists mKLists;
    private TabHost mListTabHost;
    private FragmentManager fm;
    private ListView mListView;
    private ArrayList<ListItemAdapter> mListAdapters = new ArrayList<ListItemAdapter>();
    int position = 0;
    Button delete;

    private long mLastClickTime;
    private NewItemInfoDialogListener mItemInfoListener;

    /********************************************************************************************
     * Function name: onCreate
     * <p/>
     * Description:   Initializes all needed setup for objects in page
     * <p/>
     * Parameters:    savedInstanceState  - a bundle object
     * <p/>
     * Returns:       none
     ******************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_kitchen_list);

        mLastClickTime = 0;

        //init my grocery lists
        mKLists = new KitchenLists();

        //to view items
        mListView = (ListView) findViewById(R.id.listView);
        mListView.setOnTouchListener(new OnSwipeTouchListener(this, mListView)
        {
            @Override
            public void onSwipeRight (int pos)
            {
                //Toast.makeText (KitchenListActivity.this, "right", Toast.LENGTH_LONG).show();
                showDeleteButton(pos);
            }

            @Override
            public void onSwipeLeft(int pos) {
                //Toast.makeText (KitchenListActivity.this, "left", Toast.LENGTH_LONG).show();
                showDeleteButton(pos);
            }
        });



        //setup tabs
        mListTabHost = (TabHost) findViewById(R.id.listTabHost);
        mListTabHost.setup();
        mListTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                mListTabHost.setCurrentTab(Integer.parseInt(tabId));
                if (mListAdapters.size() > 0) {
                    mListView.setAdapter(mListAdapters.get(Integer.parseInt(tabId)));
                }
            }
        });


        //on click listener for buttons (connect to the view)

        //set up addList button
        mbAddList = (Button) findViewById(R.id.bAddList);
        mbAddList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm = getSupportFragmentManager();
                ListDFragment listDFragment = new ListDFragment();
                listDFragment.show(fm, "Hi");
            }
        });

        //set up add item button
        mbAddItem = (Button) findViewById(R.id.bAddItem);
        mbAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemInfoListener = new NewItemInfoDialogListener() {
                    @Override
                    public void onFinishNewItemDialog(String inputText) {
                        ListItem newItem = new ListItem(inputText);
                        addItemToListView(newItem);
                    }

                };
                fm = getSupportFragmentManager();
                NewKitchenItemDFragment newItemFragment = new NewKitchenItemDFragment();
                newItemFragment.show(fm, "Hi");


            }
        });

        //For the Group By Spinner (sorting dropdown)

        mGroupBySpinner = (Spinner) findViewById(R.id.GroupBySpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(KitchenListActivity.this,
                android.R.layout.simple_spinner_item, PoPList.GroupByStrings);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGroupBySpinner.setAdapter(adapter);
        mGroupBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                KitchenList currentList = getCurrentKList();

                if (null != currentList)
                {
                    switch (position)
                    {
                        case PoPList.SORT_NONE: // first item in dropdown currently blank
                            currentList.setCurrentSortingValue(PoPList.SORT_NONE);
                            break;
                        case PoPList.SORT_ALPHA: //second item in dropdown currently alphabetical

                            currentList.setCurrentSortingValue(PoPList.SORT_ALPHA);
                            currentList.sortListByName();
                            mListAdapters.get(mListTabHost.getCurrentTab()).notifyDataSetChanged();


                            //TODO need to refresh the page so the new list displays
                            break;
                        case PoPList.SORT_CAL: //calories
                            currentList.setCurrentSortingValue(PoPList.SORT_CAL);
                            break;
                        case PoPList.SORT_DATE: //date entered
                            currentList.setCurrentSortingValue(PoPList.SORT_DATE);
                            break;
                        case PoPList.SORT_AISLE: //aisle
                            currentList.setCurrentSortingValue(PoPList.SORT_AISLE);
                            break;
                        case PoPList.SORT_PRICE: //price
                            currentList.setCurrentSortingValue(PoPList.SORT_PRICE);
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Nothing to do if the dropdown is not selected.
            }
        });


        //add all existing lists in KitchenLists to tabs
        for (int i = 0; i < mKLists.getSize(); i++) {
            addListTab(mKLists.getList(i), i);
        }

    }

    /********************************************************************************************
     * Function name: addListTab
     *
     * Description:   Adds a tab to the top of the page corresponding to the newList passed in.
     *
     * Parameters:    newList - a List object whose tab will be added to the top of the page
     * index   - the index of the newList in the KitchenLists object, also the
     * new tab spec id
     *
     * Returns:       none
     ******************************************************************************************/

    private void addListTab(KitchenList newList, int index)
    {
        TabHost.TabSpec spec = mListTabHost.newTabSpec(Integer.toString(index));
        spec.setContent(R.id.fragment);
        spec.setIndicator(newList.getListName());
        mListTabHost.addTab(spec);


        //for keeping track of items in list
        addListAdapter(mKLists.getList(index));
        mListTabHost.setCurrentTab(index);
    }

    /********************************************************************************************
     * Function name: onFinishListDialog
     * <p/>
     * Description:   When dialog for adding list is done, add list and list tab with text from
     * dialog as the new list name
     * <p/>
     * Parameters:    newListName - the new list's name
     * <p/>
     * Returns:       none
     ******************************************************************************************/

    @Override
    public void onFinishListDialog(String newListName)
    {
        //add List to Lists and create a tab
        mKLists.addList(newListName);

        addListTab(mKLists.getList(mKLists.getSize() - 1), mKLists.getSize() - 1);

    }

    /********************************************************************************************
     * Function name: addItemToListView
     * <p/>
     * Description:   Adds item layout to listView as a new row, and resort the list
     * <p/>
     * Parameters:    newItem - the new ListItem being added
     * <p/>
     * Returns:       none
     ******************************************************************************************/
    public void addItemToListView(ListItem newItem)
    {
        mListAdapters.get(mListTabHost.getCurrentTab()).add(newItem);

        //resort the list depending on the current sorting category
        KitchenList currentList = getCurrentKList();

        switch (currentList.getCurrentSortingValue())
        {
            case PoPList.SORT_ALPHA:
                currentList.sortListByName();
                mListAdapters.get(mListTabHost.getCurrentTab()).notifyDataSetChanged();
                break;
            case PoPList.SORT_AISLE:
            case PoPList.SORT_CAL:
            case PoPList.SORT_DATE:
            case PoPList.SORT_PRICE:
            case PoPList.SORT_NONE:
                break;
        }
    }

    /********************************************************************************************
     * Function name: onPause
     *
     * Description:   When the activity is paused writes the KitchenLists to kitchenList.txt
     *
     * Parameters:    none
     *
     * Returns:       none
     ******************************************************************************************/
    @Override
    protected void onPause ()
    {
        super.onPause();

        writeKListsToKitchenFile();

    }
    /********************************************************************************************
     * Function name: onResume
     *
     * Description:   When the activity is resumed reads in KitchenLists from KITCHEN_FILE_NAME and
     *                updates mKLists with the information.
     *
     * Parameters:    none
     *
     * Returns:       none
     ******************************************************************************************/
    @Override
    protected void onResume ()
    {
        super.onResume();

        Context context = getApplicationContext();
        File kitchenFile = context.getFileStreamPath(KitchenLists.KITCHEN_FILE_NAME);

        if (kitchenFile.exists()) {
            readKListsFromKitchenFile();
        }
    }
    /********************************************************************************************
     * Function name: addListAdapter
     * <p/>
     * Description:   Adds a list adapter for mListView to keep track of the info in kList
     * <p/>
     * Parameters:    kList - the new list whose info needs to be kept track of
     * <p/>
     * Returns:       none
     ******************************************************************************************/

    private void addListAdapter(KitchenList kList)
    {
        mListAdapters.add(new ListItemAdapter(mListView.getContext(),
                R.layout.kitchen_list_item, kList.getItemArray()));
        ListItemAdapter newAdapter = mListAdapters.get(mListAdapters.size() - 1);
        mListView.setAdapter(newAdapter);
    }

    /********************************************************************************************
     * Function name: getCurrentKList
     *
     * Description:   Gets the KitchenList whose tab we have selected
     *
     * Parameters:    none
     *
     * Returns:       the current list selected
     ******************************************************************************************/

    public KitchenList getCurrentKList()
    {
        KitchenList list = null;
        int currentTabIndex = mListTabHost.getCurrentTab();

        if (TabHost.NO_ID != currentTabIndex) {
            list = mKLists.getList(currentTabIndex);
        }

        return list;
    }

    /********************************************************************************************
     * Function name: getItemInfoListener
     *
     * Description:
     *
     * Parameters:
     *
     * Returns:
     ******************************************************************************************/
    public NewItemInfoDialogListener getItemInfoListener () {
        return mItemInfoListener;
    }

    /********************************************************************************************
     * Function name: showDeleteButton
     *
     * Description:
     *
     * Parameters:
     *
     * Returns:
     ******************************************************************************************/
    private boolean showDeleteButton(final int pos) {
        position = pos;
        View child = mListView.getChildAt(pos - mListView.getFirstVisiblePosition());
        if (child != null) {

            delete = (Button) child.findViewById(R.id.bDelete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    KitchenList kList = getCurrentKList();
                    kList.delete(pos);
                    mListAdapters.get(mListTabHost.getCurrentTab()).notifyDataSetChanged();

                }
            });
            if (delete != null)
            {
                if (delete.getVisibility() == View.INVISIBLE) {
                    Animation animation =
                            AnimationUtils.loadAnimation(this,
                                    R.anim.slide_out_left);
                    delete.startAnimation(animation);
                    delete.setVisibility(View.VISIBLE);
                } else {
                    Animation animation =
                            AnimationUtils.loadAnimation(this,
                                    R.anim.slide_in_right);
                    delete.startAnimation(animation);
                    delete.setVisibility(View.INVISIBLE);
                }
            }
            return true;
        }
        return false;
    }

    /********************************************************************************************
     * Function name: deleteItem
     *
     * Description:
     *
     * Parameters:
     *
     * Returns:
     ******************************************************************************************/

    public void deleteItem (View view, ListItem item) {

        mListAdapters.get(mListTabHost.getCurrentTab()).remove(item);
        delete.setVisibility(View.INVISIBLE);
        mListAdapters.get(mListTabHost.getCurrentTab()).notifyDataSetChanged();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        return super.dispatchTouchEvent(ev);
    }
    //https://github.com/sohambannerjee8/SwipeListView/blob/master/app/src/main/java/com/nisostech/soham/MainActivity.java

    /********************************************************************************************
     * Function name: writeKListsToKitchenFile
     *
     * Description: Writes the current mKLists to KITCHEN_FILE_NAME to store the information stored in mKLists
     *
     * Parameters: None
     *
     * Returns: None
     ******************************************************************************************/
    private void writeKListsToKitchenFile ()
    {
        FileOutputStream kitchenOutput = null;
        PrintWriter listsOutput = null;

        try
        {
            kitchenOutput = openFileOutput(KitchenLists.KITCHEN_FILE_NAME, Context.MODE_PRIVATE);

            listsOutput = new PrintWriter(kitchenOutput);

            mKLists.writeListsToFile(listsOutput);
            listsOutput.flush();
            listsOutput.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /********************************************************************************************
     * Function name: readKListsFromKitchenFile
     *
     * Description: Reads from the KITCHEN_FILE_NAME the current KitchenLists
     *
     * Parameters: None
     *
     * Returns: None
     ******************************************************************************************/
    private void readKListsFromKitchenFile ()
    {
        FileInputStream kitchenInput;
        Scanner listsInput;

        try {
            kitchenInput = openFileInput(KitchenLists.KITCHEN_FILE_NAME);

            listsInput = new Scanner(kitchenInput);
            mKLists.readListsFromFile(listsInput);
            listsInput.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < mKLists.getSize(); ++i) {
            addListTab(mKLists.getList(i), i);
        }
    }

}
