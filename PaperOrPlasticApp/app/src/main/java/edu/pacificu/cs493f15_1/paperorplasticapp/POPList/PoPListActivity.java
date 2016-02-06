/**************************************************************************************************
 *   File:     GroceryListActivity.java
 *   Author:   Abigail Jones, Lauren Sullivan, Evan Heydemann
 *   Date:     10/28/15
 *   Class:    Capstone/Software Engineering
 *   Project:  PaperOrPlastic Application
 *   Purpose:  This activity will be the activity that is opened when the user selects the
 *             grocery list button from the continue activity
 ***************************************************************************************************/

package edu.pacificu.cs493f15_1.paperorplasticapp.POPList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import edu.pacificu.cs493f15_1.paperorplasticapp.GroceryList.GroceryListActivity;
import edu.pacificu.cs493f15_1.paperorplasticapp.GroceryList.NewItemInfoDialogListener;
import edu.pacificu.cs493f15_1.paperorplasticapp.Menu.ContinueActivity;
import edu.pacificu.cs493f15_1.paperorplasticapp.R;
import edu.pacificu.cs493f15_1.paperorplasticjava.GroceryList;
import edu.pacificu.cs493f15_1.paperorplasticjava.GroceryLists;
import edu.pacificu.cs493f15_1.paperorplasticjava.ListItem;
import edu.pacificu.cs493f15_1.paperorplasticjava.PoPList;
import edu.pacificu.cs493f15_1.paperorplasticjava.PoPLists;


/***************************************************************************************************
 *   Class:         POPListActivity
 *   Description:   Creates POPListActivity class that is inherited by GroceryListActivity and
 *                  KitchenInventoryActivity
 *   Parameters:    N/A
 *   Returned:      N/A
 **************************************************************************************************/
public abstract class PoPListActivity extends FragmentActivity implements ListDFragment.EditNameDialogListener {

    final float SLIDE_RIGHT_ITEM = 5;
    final float SLIDE_LEFT_ITEM = -145;

    private QtyChangeDialogListener mQtyChangeListener;
    private Button mbAddList, mbAddItem, mbSettings, mbBack;
    private Spinner mGroupBySpinner;
    private ArrayList<TabHost.TabSpec> list = new ArrayList<TabHost.TabSpec>(); /* for later when you want to delete tabs?*/
    private TabHost mListTabHost;
    private FragmentManager fm;
    private ListView mListView;
    private Button mbEdit;
    private boolean mbIsOnEdit;
    private String mLastAddedItemName;

    private String mPoPFileName;
    private PoPLists mPoPLists;

    private int mItemLayout;

    private ArrayList<ListItemAdapter> mListAdapters = new ArrayList<ListItemAdapter>();
    int position = 0;
    Button delete;

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

        PoPOnCreate(savedInstanceState, new GroceryLists(), R.layout.activity_grocery_list); //TODO test line remove;
    }

    protected void PoPOnCreate (Bundle savedInstanceState, PoPLists popLists, final int activitylayout, final int itemLayout, final String fileName)
    {
        setContentView(activitylayout);

        mPoPLists = popLists;
        mItemLayout = itemLayout;
        mPoPFileName = fileName;

        mbIsOnEdit = false;

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

                if (mbIsOnEdit) {
                    if (mListAdapters.size() != 0)
                    {
                        int size = getCurrentPoPList().getSize();
                        if (size > 0) {

                            for (int i = 0; i < size; i++) {
                                showDeleteButton(i);
                            }
                        }
                    }
                }
            }});

        //setup edit button
        mbEdit = (Button) findViewById(R.id.bEdit);
        mbEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListAdapters.size() != 0) {
                    int size = getCurrentPoPList().getSize();
                    if (size > 0) {

                        if (!mbIsOnEdit) {
                            mbIsOnEdit = true;


                            //TODO make onEdit function that does this for loop and call when tab is changed as well (onTabChanged function, line 121)
                            for (int i = 0; i < size; i++) {
                                showDeleteButton(i);
                            }
                            mbAddItem.setTextColor(Color.rgb(170, 170, 170));
                            mbAddItem.setEnabled(false);

                        } else {

                            //showDeleteButton also gets rid of the delete button so we might not need this check

                            mbIsOnEdit = false;

                            mbAddItem.setTextColor(Color.rgb(0, 0, 0));
                            mbAddItem.setEnabled(true);
                            for (int i = 0; i < size; i++) {
                                hideDeleteButton(i);
                            }
                        }
                    }
                }
            }
        });

        //set up back button
        mbBack = (Button) findViewById (R.id.bBackToHome);
        mbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroceryListActivity.this, ContinueActivity.class); //TODO Come back to this maybe if statements?
                startActivity (intent);
            }
        });

        //set up settings activity button
        mbSettings = (Button) findViewById(R.id.bGListSettings);
        mbSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroceryListActivity.this, GroceryListSettingsActivity.class); //TODO Come back to this maybe if statements?
                intent.putExtra("Caller", "GroceryListActivity");
                startActivity(intent);
            }
        });

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
                        /*When newItemDialog finishes we want this to be called to make an item
                        with the inputText as the name of the newItem and add it to the current
                        selected list*/
                        ListItem newItem = new ListItem(inputText);

                        addItemToListView(newItem);
                        mLastAddedItemName = inputText;
                    }
                };


                fm = getSupportFragmentManager();
                NewListItemDFragment newItemFragment = new NewListItemDFragment();
                newItemFragment.show(fm, "Hi");
            }
        });


        //set Add Item Button to not enabled if we have no list selected
        if (mListAdapters.size() == 0) {
            mbAddItem.setTextColor(Color.rgb(170, 170, 170));
            mbAddItem.setEnabled(false);
        }



        //For the Group By Spinner (sorting dropdown)

        mGroupBySpinner = (Spinner) findViewById(R.id.GroupBySpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(GroceryListActivity.this,
                android.R.layout.simple_spinner_item, PoPList.GroupByStrings);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGroupBySpinner.setAdapter(adapter);
        mGroupBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                PoPList currentList = getCurrentPoPList();

                //functionality when we're using drop down sorting menu
                if (null != currentList) {
                    switch (position)
                    {
                        case PoPList.SORT_NONE: // first item in dropdown currently blank
                            currentList.setCurrentSortingValue(PoPList.SORT_NONE);
                            break;
                        case PoPList.SORT_ALPHA: //second item in dropdown currently alphabetical

                            currentList.setCurrentSortingValue(PoPList.SORT_ALPHA);
                            currentList.sortListByName();
                            mListAdapters.get(mListTabHost.getCurrentTab()).notifyDataSetChanged();

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



        //to view items in list
        mListView = (ListView) findViewById(R.id.listView);
        mListView.setOnTouchListener(new OnSwipeTouchListener(this, mListView) {
            @Override
            public void onSwipeRight(int pos) {


                if (!mbIsOnEdit) {
                    hideDeleteButton(pos);
                }

            }

            @Override
            public void onSwipeLeft(int pos) {

                if (!mbIsOnEdit) {
                    showDeleteButton(pos);
                }
            }
        });


    }

    /********************************************************************************************
     * Function name: onPause
     *
     * Description:   When the activity is paused writes the GroceryLists to groceryList.txt
     *
     * Parameters:    none
     *
     * Returns:       none
     ******************************************************************************************/
    @Override
    protected void onPause ()
    {
        super.onPause();

        writeListsToFile();
        mPoPLists.clearLists();
    }
    /********************************************************************************************
     * Function name: onResume
     *
     * Description:   When the activity is resumed reads in GroceryLists from GROCERY_FILE_NAME and
     *                updates mGLists with the information.
     *
     * Parameters:    none
     *
     * Returns:       none
     ******************************************************************************************/
    @Override
    protected void onResume ()
    {
        super.onResume();

        //turn off edit and enable add item button
        mbIsOnEdit = false;
        mbAddItem.setTextColor(Color.rgb(0, 0, 0));
        mbAddItem.setEnabled(true);

        //read lists in
        Context context = getApplicationContext();
        File popFile = context.getFileStreamPath(mPoPFileName);

        if (popFile.exists()) {
            mPoPLists.clearLists();
            readListsFromFile(mPoPLists);
        }

        if (mListAdapters.size() == 0) {
            mbAddItem.setTextColor(Color.rgb(170, 170, 170));
            mbAddItem.setEnabled(false);
        }
    }

    /********************************************************************************************
     * Function name: addListTab
     *
     * Description:   Adds a tab to the top of the page corresponding to the newList passed in.
     *
     * Parameters:    newList - a List object whose tab will be added to the top of the page
     *                index   - the index of the newList in the GroceryLists object, also the
     *                          new tab spec id
     *
     * Returns:       none
     ******************************************************************************************/

    private void addListTab(PoPList newList, int index)
    {
        TabHost.TabSpec spec = mListTabHost.newTabSpec(Integer.toString(index));
        spec.setContent(R.id.fragment);
        spec.setIndicator(newList.getListName());
        mListTabHost.addTab(spec);


        //for keeping track of items in list
        addListAdapter(mPoPLists.getList(index));
        mListTabHost.setCurrentTab(index);

        //change AddItem button to enabled since you are going to have list tab selected
        if (!mbAddItem.isEnabled())
        {
            mbAddItem.setTextColor(Color.rgb(0, 0, 0));
            mbAddItem.setEnabled(true);
        }
    }

    /********************************************************************************************
     * Function name: onFinishListDialog
     *
     * Description:   When dialog for adding list is done, add list and list tab with text from
     *                dialog as the new list name
     *
     * Parameters:    newListName - the new list's name
     *
     * Returns:       none
     ******************************************************************************************/

    @Override
    public void onFinishListDialog(String newListName)
    {
        //add List to Lists and create a tab
        mPoPLists.addList(newListName);

        addListTab(mPoPLists.getList(mPoPLists.getSize() - 1), mPoPLists.getSize() - 1);

    }

    /********************************************************************************************
     * Function name: addItemToListView
     *
     * Description:   Adds item layout to listView as a new row and adds it to listadapter
     *
     * Parameters:    newItem - the new ListItem being added
     *
     * Returns:       none
     ******************************************************************************************/
    public void addItemToListView (ListItem newItem)
    {
        getCurrentPoPList().addItem(newItem);

        //re-sort the list depending on the current sorting category
        switch (getCurrentPoPList().getCurrentSortingValue())
        {
            case PoPList.SORT_ALPHA:
                getCurrentPoPList().sortListByName();

                break;
            case PoPList.SORT_AISLE:
            case PoPList.SORT_CAL:
            case PoPList.SORT_DATE:
            case PoPList.SORT_PRICE:
            case PoPList.SORT_NONE:
                break;

        }
        //notify list adapter that we added an item
        mListAdapters.get(mListTabHost.getCurrentTab()).notifyDataSetChanged();
    }

    /********************************************************************************************
     * Function name: showDeleteOnEdit
     *
     * Description:   Shows delete button for item if editing is on
     *
     * Parameters:    gList - the new list whose info needs to be kept track of
     *
     * Returns:       none
     ******************************************************************************************/

    public void showDeleteOnEdit (String itemName)
    {
        int itemIndex = getCurrentPoPList().getItemIndex(itemName);
        if (mbIsOnEdit && itemIndex != -1)
        {
            showDeleteButton(itemIndex);
        }
    }

    /********************************************************************************************
     * Function name: addListAdapter
     *
     * Description:   Adds a list adapter for mListView to keep track of the info in gList
     *
     * Parameters:    gList - the new list whose info needs to be kept track of
     *
     * Returns:       none
     ******************************************************************************************/

    private void addListAdapter(PoPList popList)
    {
        mListAdapters.add(new ListItemAdapter(mListView.getContext(),
                mItemLayout, popList.getItemArray()));
        ListItemAdapter newAdapter = mListAdapters.get(mListAdapters.size() - 1);
        mListView.setAdapter(newAdapter);
    }

    /********************************************************************************************
     * Function name: getCurrentPoPList
     *
     * Description:   Gets the GroceryList whose tab we have selected
     *
     * Parameters:    none
     *
     * Returns:       the current list selected
     ******************************************************************************************/

    private PoPList getCurrentPoPList()
    {
        PoPList list = null;
        int currentTabIndex = mListTabHost.getCurrentTab();

        if (TabHost.NO_ID != currentTabIndex) {
            list = mPoPLists.getList(currentTabIndex);
        }

        return list;
    }

    /********************************************************************************************
     * Function name: getItemInfoListener
     *
     * Description:   returns the mItemInfoListener for other dialogs to use
     *
     * Parameters:     none
     *
     * Returns:        mItemInfoListener
     ******************************************************************************************/
    public NewItemInfoDialogListener getItemInfoListener () {
        return mItemInfoListener;
    }

    /********************************************************************************************
     * Function name: getQtyChangeListener
     *
     * Description:
     *
     * Parameters:
     *
     * Returns:
     ******************************************************************************************/
    public QtyChangeDialogListener getQtyChangeListener () {
        return mQtyChangeListener;
    }

    /********************************************************************************************
     * Function name: showDeleteButton
     *
     * Description:   Shows the delete button for the child view within listView and sets the
     *                onClickListener for the delete button
     *
     * Parameters:    pos - the child position within the list view whose delete button will be
     *                      shown
     *
     * Returns:       true if the child view with the button being hidden exists, else false
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
                    PoPList poPList = getCurrentPoPList();
                    poPList.delete(pos);
                    mListAdapters.get(mListTabHost.getCurrentTab()).notifyDataSetChanged();

                }
            });
            if (delete != null)
            {
                if (delete.getVisibility() == View.INVISIBLE) {
                    Animation deleteAnimation =
                            AnimationUtils.loadAnimation(this,
                                    R.anim.slide_out_left);

                    delete.startAnimation(deleteAnimation);
                    delete.setVisibility(View.VISIBLE);

                    slideItemView(child, SLIDE_LEFT_ITEM);
                }
            }
            return true;
        }
        return false;
    }

    /********************************************************************************************
     * Function name: hideDeleteButton
     *
     * Description:   Hides the delete button on each list view child
     *
     * Parameters:    pos - the child position within the list view
     *
     * Returns:       true if the child view with the button being hidden exists, else false
     ******************************************************************************************/

    private boolean hideDeleteButton(final int pos) {
        position = pos;
        View child = mListView.getChildAt(pos - mListView.getFirstVisiblePosition());
        if (child != null) {

            delete = (Button) child.findViewById(R.id.bDelete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    PoPList poPList = getCurrentPoPList();
                    poPList.delete(pos);
                    mListAdapters.get(mListTabHost.getCurrentTab()).notifyDataSetChanged();

                }
            });
            if (delete != null)
            {
                if (delete.getVisibility() == View.VISIBLE) {
                    Animation deleteAnimation =
                            AnimationUtils.loadAnimation(this,
                                    R.anim.slide_in_right);

                    delete.startAnimation(deleteAnimation);

                    delete.setVisibility(View.INVISIBLE);

                    slideItemView(child, SLIDE_RIGHT_ITEM);

                }
            }
            return true;
        }
        return false;
    }

    /********************************************************************************************
     * Function name: slideItemView
     *
     * Description:   translates all objects in the view (inside of ListView) by the translation
     *                amount
     *
     * Parameters:    child             - the view whose objects will be translated
     *                translationAmount - how many pixels the objects in the view will be changed
     *                                    by (on the x-axis)
     *
     * Returns:       None
     ******************************************************************************************/

    private void slideItemView (View child, float translationAmount)
    {
        CheckBox checkBox = (CheckBox) child.findViewById(R.id.itemCheckBox);
        Button itemName = (Button) child.findViewById(R.id.bListItem);
        TextView qtyText = (TextView) child.findViewById(R.id.quantityText);

        checkBox.setTranslationX(translationAmount);
        itemName.setTranslationX(translationAmount);
        qtyText.setTranslationX(translationAmount);
    }
    /********************************************************************************************
     * Function name: dispatchTouchEvent
     *
     * Description:   calls the super for fragment activity for swiping
     *
     * Parameters:    None
     *
     * Returns:       None
     ******************************************************************************************/

    public PoPLists getLists () {
        return mPoPLists;
    }

    /********************************************************************************************
     * Function name: writeGListsToGroceryFile
     *
     * Description:   Writes the current mGLists to GROCERY_FILE_NAME to store the information
     *                stored in mGLists
     *
     * Parameters:    None
     *
     * Returns:       None
     ******************************************************************************************/
    private void writeListsToFile ()
    {
        FileOutputStream popOutput = null;
        PrintWriter listsOutput = null;

        try
        {
            popOutput = openFileOutput(mPoPFileName, Context.MODE_PRIVATE);

            listsOutput = new PrintWriter(popOutput);
            mPoPLists.writeListsToFile(listsOutput);
            listsOutput.flush();
            listsOutput.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /********************************************************************************************
     * Function name: readGListsFromGroceryFile
     *
     * Description:   Reads from the GROCERY_FILE_NAME the current GroceryLists
     *
     * Parameters:    None
     *
     * Returns:       None
     ******************************************************************************************/
    private void readListsFromFile (PoPLists popLists)
    {
        FileInputStream popInput;
        Scanner listsInput;

        try {
            popInput = openFileInput(mPoPFileName);

            listsInput = new Scanner(popInput);
            popLists.readListsFromFile(listsInput);
            listsInput.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < popLists.getSize(); ++i) {
            addListTab(popLists.getList(i), i);
        }
    }
}
