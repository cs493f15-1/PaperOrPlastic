package edu.pacificu.cs493f15_1.paperorplasticapp;

import android.content.DialogInterface;
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

import java.util.ArrayList;

import edu.pacificu.cs493f15_1.paperorplasticjava.GroceryList;
import edu.pacificu.cs493f15_1.paperorplasticjava.GroceryLists;
import edu.pacificu.cs493f15_1.paperorplasticjava.ListItem;
import edu.pacificu.cs493f15_1.paperorplasticjava.PoPList;


/**
 * Created by sull0678 on 10/26/2015.
 */
public class GroceryListActivity extends FragmentActivity implements ListDFragment.EditNameDialogListener {
    private Button mbAddList, mbAddItem;
    private Spinner mGroupBySpinner;
    private ArrayList<TabHost.TabSpec> list = new ArrayList<TabHost.TabSpec>(); /* for later when you want to delete tabs?*/
    private GroceryLists mGLists;
    private TabHost mListTabHost;
    private FragmentManager fm;
    private ListView mListView;
    private Button mbEdit;
    private boolean mbIsOnEdit;
    private String mLastAddedItemName;

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

        setContentView(R.layout.activity_grocery_list);
        mbIsOnEdit = false;

        //init my grocery lists
        mGLists = new GroceryLists();

        //to view items
        mListView = (ListView) findViewById(R.id.listView);
        mListView.setOnTouchListener(new OnSwipeTouchListener(this, mListView)
        {
            @Override
            public void onSwipeRight (int pos)
            {
                //Toast.makeText (GroceryListActivity.this, "right", Toast.LENGTH_LONG).show();
                showDeleteButton(pos);
            }

            @Override
            public void onSwipeLeft(int pos) {
                //Toast.makeText (GroceryListActivity.this, "left", Toast.LENGTH_LONG).show();
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

        //setup edit button
        mbEdit = (Button) findViewById(R.id.bEdit);
        mbEdit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //note, GroceryList object doesn't keep track of size, only the array of items within
                // it does
                int size = getCurrentGList().getItemArray().size();
                if (size > 0) {
                    if (!mbIsOnEdit) {
                        mbIsOnEdit = true;
                        for (int i = 0; i < size; i++) {
                            showDeleteButton(i);
                        }
                    } else {

                        //showDeleteButton also gets rid of the delete button so we might not need this check
                        //TODO might need to show again if tab is changed
                        mbIsOnEdit = false;
                        for (int i = 0; i < size; i++) {
                            showDeleteButton(i);
                        }
                    }
                }

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
                        ListItem newItem = new ListItem(inputText);
                        addItemToListView(newItem);
                        mLastAddedItemName = inputText;
                    }
                };


                fm = getSupportFragmentManager();
                NewGroceryItemDFragment newItemFragment = new NewGroceryItemDFragment();
                newItemFragment.show(fm, "Hi");

            }
        });


        //For testing purposes
        mGLists.addList("My First List");

        //For the Group By Spinner (sorting dropdown)

        mGroupBySpinner = (Spinner) findViewById(R.id.GroupBySpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(GroceryListActivity.this,
                android.R.layout.simple_spinner_item, PoPList.GroupByStrings);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGroupBySpinner.setAdapter(adapter);
        mGroupBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                GroceryList currentList = getCurrentGList();

                switch (position) {

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

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Nothing to do if the dropdown is not selected.
            }
        });


        //add all existing lists in GroceryLists to tabs
        for (int i = 0; i < mGLists.getSize(); i++) {
            addListTab(mGLists.getList(i), i);
        }

    }

   /* @Override
    public void OnResume ()
    {

    }
*/

    /********************************************************************************************
     * Function name: addListTab
     *
     * Description:   Adds a tab to the top of the page corresponding to the newList passed in.
     *
     * Parameters:    newList - a List object whose tab will be added to the top of the page
     * index   - the index of the newList in the GroceryLists object, also the
     * new tab spec id
     *
     * Returns:       none
     ******************************************************************************************/

    private void addListTab(GroceryList newList, int index)
    {
        TabHost.TabSpec spec = mListTabHost.newTabSpec(Integer.toString(index));
        spec.setContent(R.id.fragment);
        spec.setIndicator(newList.getListName());
        mListTabHost.addTab(spec);


        //for keeping track of items in list
        addListAdapter(mGLists.getList(index));
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
        mGLists.addList(newListName);

        addListTab(mGLists.getList(mGLists.getSize() - 1), mGLists.getSize() - 1);

    }

    /********************************************************************************************
     * Function name: addItemToListView
     *
     * Description:   Adds item layout to listView as a new row and adds it to listadapter
     *
     * Parameters:    none
     *
     * Returns:       none
     ******************************************************************************************/

    public void addItemToListView (ListItem newItem)
    {
        mListAdapters.get(mListTabHost.getCurrentTab()).add(newItem);

        //resort the list depending on the current sorting category
        GroceryList currentList = getCurrentGList();

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
        int itemIndex = getCurrentGList().getItemIndex(itemName);
        if (mbIsOnEdit && itemIndex != -1)
        {
            showDeleteButton(itemIndex);
        }
    }

    /********************************************************************************************
     * Function name: addListAdapter
     * <p/>
     * Description:   Adds a list adapter for mListView to keep track of the info in gList
     * <p/>
     * Parameters:    gList - the new list whose info needs to be kept track of
     * <p/>
     * Returns:       none
     ******************************************************************************************/
    private void addListAdapter(GroceryList gList)
    {
        mListAdapters.add(new ListItemAdapter(mListView.getContext(),
                R.layout.grocery_list_item, gList.getItemArray()));
        ListItemAdapter newAdapter = mListAdapters.get(mListAdapters.size() - 1);
        mListView.setAdapter(newAdapter);
    }

    /********************************************************************************************
     * Function name: getCurrentGList
     *
     * Description:   Gets the GroceryList whose tab we have selected
     *
     * Parameters:    none
     *
     * Returns:       the current list selected
     ******************************************************************************************/

    public GroceryList getCurrentGList() {
        return mGLists.getList(mListTabHost.getCurrentTab());
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
                    GroceryList gList = getCurrentGList();
                    gList.delete(pos);
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


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        return super.dispatchTouchEvent(ev);
    }
    //https://github.com/sohambannerjee8/SwipeListView/blob/master/app/src/main/java/com/nisostech/soham/MainActivity.java

}
