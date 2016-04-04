/**************************************************************************************************
 *   File:     GroceryListActivity.java
 *   Author:   Abigail Jones, Lauren Sullivan, Evan Heydemann
 *   Date:     10/28/15
 *   Class:    Capstone/Software Engineering
 *   Project:  PaperOrPlastic Application
 *   Purpose:  This activity will be the activity that is opened when the user selects the
 *             grocery list button from the continue activity
 ***************************************************************************************************/

package edu.pacificu.cs493f15_1.paperorplasticapp.popList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import edu.pacificu.cs493f15_1.paperorplasticapp.R;
import edu.pacificu.cs493f15_1.paperorplasticapp.groceryList.GroceryListSettingsActivity;
import edu.pacificu.cs493f15_1.paperorplasticapp.kitchenInventory.KitchenInventorySettingsActivity;
import edu.pacificu.cs493f15_1.paperorplasticapp.menu.ContinueActivity;
import edu.pacificu.cs493f15_1.paperorplasticjava.ListItem;
import edu.pacificu.cs493f15_1.paperorplasticjava.NutritionFactModel;
import edu.pacificu.cs493f15_1.paperorplasticjava.PoPList;
import edu.pacificu.cs493f15_1.paperorplasticjava.PoPLists;

/***************************************************************************************************
 *   Class:         POPListActivity
 *   Description:   Creates POPListActivity class that is inherited by GroceryListActivity and
 *                  KitchenInventoryActivity that controls what occurs when the user
 *                  selects the grocery list option from the continue activity. Specifically
 *                  contains the list functionality.
 *   Parameters:    N/A
 *   Returned:      N/A
 **************************************************************************************************/
public abstract class PoPListActivity extends FragmentActivity {

    final int REQUEST_OK = 1;
    public static final float SLIDE_RIGHT_ITEM = 5;
    public static final float SLIDE_LEFT_ITEM = -145;

    private Button mbAddList, mbAddItem, mbSettings, mbBack;
    private Spinner mGroupBySpinner;
    private ArrayList<TabHost.TabSpec> list = new ArrayList<TabHost.TabSpec>(); /* for later when you want to delete tabs?*/
    private TabHost mListTabHost;
    private FragmentManager fm;
    private ListView mListView;

    private boolean mbIsOnEdit, mbAddingItem, mbIsGrocery;
    private ListFragment listFrag;
    private ListItem newItem;
    private ToggleButton mbEdit;
    private int mLastClicked;
    private String mPoPFileName;
    private PoPLists mPoPLists;
    private DialogListener mListInfoListener;

    private int mItemLayout;

    private ArrayList<ListItemAdapter> mListAdapters = new ArrayList<ListItemAdapter>();
    int position = 0;
    Button delete;

    private DialogListener mItemInfoListener;


    //Return from ItemSearchActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_OK)
        {
            if(resultCode == RESULT_OK)
            {
                String item_name = data.getStringExtra("item_name");
                String brand_name = data.getStringExtra("brand_name");
                String desc = data.getStringExtra("item_desc");

                newItem = new ListItem(item_name, brand_name, desc);

                newItem.setNutritionFacts(data.getIntExtra("nf_calories", 0),
                        data.getDoubleExtra("nf_total_fat", 0),
                        data.getDoubleExtra("nf_saturated_fat", 0),
                        data.getDoubleExtra("nf_polyunsaturated_fat", 0),
                        data.getDoubleExtra("nf_monounsaturated_fat", 0),
                        data.getDoubleExtra("nf_trans_fatty_acid", 0),
                        data.getDoubleExtra("nf_cholesterol", 0),
                        data.getDoubleExtra("nf_sodium", 0),
                        data.getDoubleExtra("nf_total_carbohydrate", 0),
                        data.getDoubleExtra("nf_dietary_fiber", 0),
                        data.getDoubleExtra("nf_sugars", 0),
                        data.getDoubleExtra("nf_protein", 0),
                        data.getDoubleExtra("nf_potassium", 0),
                        data.getIntExtra("nf_vitamin_a_dv", 0),
                        data.getIntExtra("nf_vitamin_c_dv", 0),
                        data.getIntExtra("nf_calcium_dv", 0),
                        data.getIntExtra("nf_iron_dv", 0));

                readListsFromFile(mPoPLists);
                addItemToListView(newItem);
                writeListsToFile();



                mbAddingItem = true;
            }
        }
    }


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

        //Used for add item
        Intent intent;

        super.onCreate(savedInstanceState);
    }

    /********************************************************************************************
     * Function name: PoPOnCreate
     * <p/>
     * Description:   a function that is used in the OnCreate of GroceryListActivity and
     * KitchenInventoryActivity and is used to implement the functionality of the
     * Activity.
     * <p/>
     * Parameters:    savedInstanceState  - a bundle object
     * popList             - The lists object created in GroceryListActivity or KitchenInventoryActivity
     * activityLayout      - the layout of GroceryListActivity or KitchenInventoryActivity
     * itemLayout          - the layout of the items in GroceryListActivity or KitchenInventoryActivity
     * fileName            - the file which the PoPLists should be stored in
     * isGrocery           - A boolean on whether the activity is called from GroceryListActivity or not
     * <p/>
     * Returns:       none
     ******************************************************************************************/
    protected void PoPOnCreate(Bundle savedInstanceState, PoPLists popLists,
                               final int activityLayout, final int itemLayout,
                               final String fileName, final boolean isGrocery) {
        setContentView(activityLayout);

        mPoPLists = popLists;
        mItemLayout = itemLayout;
        mPoPFileName = fileName;
        mbIsOnEdit = false;
        mbAddingItem = false;
        mbIsGrocery = isGrocery;
        //to view items
        mListView = (ListView) findViewById(R.id.listView);

        handleSwipingToDelete();

        //setup tabs
        setupTabs();

        //setup edit button
        setupEditDeleteButtonsForLists();

        //setup back buttons
        setupBackButton();

        //setup settings activity button
        setupSettingsActivityButton();

        //setup addList and addItem buttons
        setupAddListButtons();

        setupAddItemButtons();

        //setup the sorting group by spinner (drop down list sorting)
        setUpGroupSpinnerHandleSorting();

        addAllExistingListsInPoPListsToTabs();
    }





    /***********************************************************************************************
     * Method:      handleSwipingToDelete
     * Description: Handles when user swipes left or right on list items. Will show or
     * hide delete buttons depending on the status of the edit button.
     * Parameters:  NONE
     * Returned:    NONE
     ***********************************************************************************************/
    private void handleSwipingToDelete() {

        //to view items in list
        mListView = (ListView) findViewById(R.id.listView);
        //to be able to swipe list items over for delete
        mListView.setOnTouchListener(new OnSwipeTouchListener(this, mListView) {
            @Override
            public void onSwipeRight(int pos) {
                // Log.d("GroceryListActivity", "onSwipeRight function entered");

                if (!mbIsOnEdit) {
                    hideDeleteButton(pos);
                }

            }

            @Override
            public void onSwipeLeft(int pos) {
                // Log.d("GroceryListActivity", "onSwipeLeft function entered");
                if (!mbIsOnEdit) {
                    showDeleteButton(pos);
                    showDeleteButton(pos); //TODO can this be removed?
                }
            }

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // selected item
                mLastClicked = position;

                Toast toast = Toast.makeText(getApplicationContext(), mLastClicked, Toast.LENGTH_SHORT);
                toast.show();

            }
        });
    }

    /***********************************************************************************************
     * Method:      setupEditDeleteButtonsForLists
     * Description: Sets up the grocery list settings edit button. Handles what happens when user
     * selects edit when viewing grocery lits.
     * Parameters:  NONE
     * Returned:    NONE
     ***********************************************************************************************/
/*    private void setupEditDeleteButtonsForLists() {
        mbEdit = (Button) findViewById(R.id.bEdit);
        mbEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListAdapters.size() != 0) {
                    int size = getCurrentPoPList().getSize();
                    if (size > 0) {
                        if (!mbIsOnEdit) {
                            mbIsOnEdit = true;
                        }
                    }
                }
            }
        });
    }*/

    private void setupEditDeleteButtonsForLists()
    {
        mbEdit = (ToggleButton) findViewById(R.id.bEdit);
        mbEdit.setChecked(mbIsOnEdit);
    }

    /***********************************************************************************************
     *   Method:      onClickEditButton
     *   Description: Handles click of edit button
     *   Parameters:  view
     *   Returned:    NONE
     ***********************************************************************************************/
    public void onClickEditButton (View view)
    {
        if (mListAdapters.size() != 0) {
            int size = getCurrentPoPList().getSize();

            if (size > 0) {

                if (!mbIsOnEdit) {
                    mbIsOnEdit = true;
                    mbEdit.setChecked(mbIsOnEdit);
                    //TODO make onEdit function that does this for loop and call when tab is changed as well (onTabChanged function, line 121)
                    showDeleteButtons(size);
                    mbAddItem.setTextColor(Color.rgb(170, 170, 170));
                    mbAddItem.setEnabled(false);
                } else {

                    mbIsOnEdit = false;
                    mbEdit.setChecked(mbIsOnEdit);
                    //TODO might need to show again if tab is changed
                    hideDeleteButtons(size);
                    mbAddItem.setTextColor(Color.rgb(0, 0, 0));
                    mbAddItem.setEnabled(true);
                }
            }
        }
    }

    /***********************************************************************************************
     *   Method:      showDeleteButtons
     *   Description: shows a delete button for every item in list view base on size passed in
     *   Parameters:  size - size of list
     *   Returned:    NONE
     ***********************************************************************************************/
    private void showDeleteButtons (int size)
    {
        for (int i = 0; i < size; i++) {
            showDeleteButton(i);
        }
    }

    /***********************************************************************************************
     *   Method:      hideDeleteButtons
     *   Description: hides a delete button for every item in list view base on size passed in
     *   Parameters:  size - size of list
     *   Returned:    NONE
     ***********************************************************************************************/
    private void hideDeleteButtons (int size)
    {
        for (int i = 0; i < size; i++)
        {
            hideDeleteButton(i);
        }
    }

    /***********************************************************************************************
     * Method:      setupTabs
     * Description: Sets up tabs
     * Parameters:  NONE
     * Returned:    NONE
     ***********************************************************************************************/
    private void setupTabs() {
        mListTabHost = (TabHost) findViewById(R.id.listTabHost);
        mListTabHost.setup();
        mListTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {

                mListTabHost.setCurrentTab(Integer.parseInt(tabId));

                if (mListAdapters.size() > 0) {

                    mListView.setAdapter(mListAdapters.get(Integer.parseInt(tabId)));

                    if (mbIsOnEdit) {
                        Log.d("GroceryListActivity", "tab changed, on edit");

                        Handler handler = new Handler();
                        //wait for tab to be changed
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                // Actions to do after 1 seconds
                                int size = getCurrentPoPList().getSize();

                                showDeleteButtons(size);

                            }
                        }, 100);

                    }
                }
            }
        });
    }

    /***********************************************************************************************
     * Method:      setupBackButton
     * Description: Setup back button that takes user from grocery list page to continue page
     * Parameters:  NONE
     * Returned:    NONE
     ***********************************************************************************************/
    private void setupBackButton() {
        mbBack = (Button) findViewById(R.id.bBackToHome);
        mbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mbIsGrocery) {
                    Intent intent = new Intent(PoPListActivity.this, ContinueActivity.class);
                    intent.putExtra("Caller", "GroceryListActivity");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(PoPListActivity.this, ContinueActivity.class);
                    intent.putExtra("Caller", "KitchenInventoryActivity");
                    startActivity(intent);
                }
            }
        });
    }

    /***********************************************************************************************
     * Method:      setupAddListAndAddItemButtons
     * Description: Sets up addList buttons
     * Parameters:  NONE
     * Returned:    NONE
     ***********************************************************************************************/
    private void setupAddListButtons() {
        //set up addList button
        mbAddList = (Button) findViewById(R.id.bAddList);

/*        mbAddList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm = getSupportFragmentManager();
                ListDFragment listDFragment = new ListDFragment();
                listDFragment.show(fm, "Hi");
            }
        });*/
    }

    /***********************************************************************************************
     * Method:      setupAddItemButtons
     * Description: Sets up addItem buttons
     * Parameters:  NONE
     * Returned:    NONE
     ***********************************************************************************************/
    private void setupAddItemButtons () {

        //set up add item button
        mbAddItem = (Button) findViewById(R.id.bAddItem);

/*
        mbAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item_name;
                Intent addItemIntent = new Intent(PoPListActivity.this, ItemSearchActivity.class);
                addItemIntent.putExtra("num_list_items", getNumPoPList());

                if (mbIsGrocery) {
                    addItemIntent.putExtra("Caller", "GroceryListActivity");
                } else {
                    addItemIntent.putExtra("Caller", "KitchenInventoryActivity");
                }

        //set up add item button
        mbAddItem = (Button) findViewById(R.id.bAddItem);*/
    }

    /***********************************************************************************************
     *   Method:      onAddListClick
     *   Description: If addList button is clicked, create dialog box and listener for finishing
     *                dialog
     *   Parameters:  view - the button that was clicked
     *   Returned:    NONE
     ***********************************************************************************************/

    public void onAddListClick (View view)
    {
        mListInfoListener = new DialogListener() {
            @Override
            public void onFinishNewListDialog(String newListName) {

                if (!mPoPLists.ListNameExists(newListName)) //List name does not already exist
                {
                    //add List to Lists and create a tab
                    mPoPLists.addList(newListName);

                    addListTab(mPoPLists.getList(mPoPLists.getSize() - 1), mPoPLists.getSize() - 1);
                }
                else
                {
                    //TODO output error to user saying List Name already exists
                    Log.d ("PoPListActivity", "Error with duplicate list names, not handled");
                }

            }
        };

        fm = getSupportFragmentManager();
        ListDFragment listDFragment = new ListDFragment();
        listDFragment.show(fm, "Hi");
    }

    /***********************************************************************************************
     *   Method:      onAddItemClick
     *   Description: If addItem button is clicked, call activity for searching for an item
     *   Parameters:  view - the button that was clicked
     *   Returned:    NONE
     ***********************************************************************************************/

    public void onAddItemClick (View view)
    {
        Intent addItemIntent = new Intent(PoPListActivity.this, ItemSearchActivity.class);
        addItemIntent.putExtra("num_list_items", getNumPoPList());

        if (mbIsGrocery) {
            addItemIntent.putExtra("Caller", "GroceryListActivity");
        } else {
            addItemIntent.putExtra("Caller", "KitchenInventoryActivity");
        }
        startActivityForResult(addItemIntent, REQUEST_OK);
    }

    /***********************************************************************************************
     *   Method:      setupSettingsActivityButton
     *   Description: Sets up settings activity button so that when the user selects the settings
     *                button from the grocery list activity they can be taken to the grocer list
     *                settings activity
     *   Parameters:  NONE
     *   Returned:    NONE
     ***********************************************************************************************/
    private void setupSettingsActivityButton () {
        mbSettings = (Button) findViewById(R.id.bListSettings);
        mbSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mbIsGrocery) {
                    Intent intent = new Intent(PoPListActivity.this, GroceryListSettingsActivity.class);
                    intent.putExtra("Caller", "GroceryListActivity");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(PoPListActivity.this, KitchenInventorySettingsActivity.class);
                    intent.putExtra("Caller", "KitchenInventoryActivity");
                    startActivity(intent);
                }
            }
        });
    }

    /***********************************************************************************************
     *   Method:      setUpGroupSpinnerHandleSorting
     *   Description: Sets up the group by drop down menu and takes care of sorting the list items
     *                within grocery lists.
     *   Parameters:  NONE
     *   Returned:    NONE
     ***********************************************************************************************/
    private void setUpGroupSpinnerHandleSorting ()
    {
        mGroupBySpinner = (Spinner) findViewById(R.id.GroupBySpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PoPListActivity.this,
                android.R.layout.simple_spinner_item, PoPList.GroupByStrings);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGroupBySpinner.setAdapter(adapter);
        mGroupBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                PoPList currentList = getCurrentPoPList();

                if (null != currentList) {
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Nothing to do if the dropdown is not selected.
            }
        });
    }

    /********************************************************************************************
     * Function name: addAllExistingListsInPoPListsToTabs
     *
     * Description:   Creates a tab for every existing List in the mPopLists array
     *
     * Parameters:    none
     *
     * Returns:       none
     ******************************************************************************************/
    private void addAllExistingListsInPoPListsToTabs() {
        for (int i = 0; i < mPoPLists.getSize(); i++) {
            addListTab(mPoPLists.getList(i), i);
        }
    }

    /********************************************************************************************
     * Function name: getCurrentListAdapter
     *
     * Description:   Returns the current List adapter being shown on the screen
     *
     * Parameters:    none
     *
     * Returns:       the current ListItemAdapter
     ******************************************************************************************/

    public ListItemAdapter getCurrentListAdapter()
    {
        return mListAdapters.get(mListTabHost.getCurrentTab());
    }

    /********************************************************************************************
     * Function name: onPause
     *
     * Description:   When the activity is paused writes the PoPLists to the file passed in OnCreate
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
        mListAdapters.clear();
    }
    /********************************************************************************************
     * Function name: onResume
     *
     * Description:   When the activity is resumed reads in PoPLists from the file passed in OnCreate
     *                and updates mPoPLists with the information.
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
        File popFile = context.getFileStreamPath(mPoPFileName);

        mbIsOnEdit = false;

        //popFile.delete();

        if (popFile.exists()) {
            mPoPLists.clearLists();

            //popFile.delete();

            readListsFromFile(mPoPLists);
            fillTabs(mPoPLists);

            mbAddingItem = false;
        }
    }

    /********************************************************************************************
     * Function name: addListTab
     *
     * Description:   Adds a tab to the top of the page corresponding to the newList passed in.
     *
     * Parameters:    newList - a List object whose tab will be added to the top of the page
     *                index   - the index of the newList in the PoPLists object, also the
     *                          new tab spec id
     *
     * Returns:       none
     ******************************************************************************************/

    private void addListTab(PoPList newList, int index)
    {
        if (!mbAddingItem)
        {
            TabHost.TabSpec spec = mListTabHost.newTabSpec(Integer.toString(index));
            spec.setContent(R.id.listView);
            spec.setIndicator(newList.getListName());
            mListTabHost.addTab(spec);
        }

        //for keeping track of items in list
        addListAdapter(mPoPLists.getList(index));
        mListTabHost.setCurrentTab(index);
        mListAdapters.get(mListTabHost.getCurrentTab()).notifyDataSetChanged();


        //change AddItem button to enabled since you are going to have list tab selected
        if (!mbAddItem.isEnabled())
        {
            mbAddItem.setTextColor(Color.rgb(0, 0, 0));
            mbAddItem.setEnabled(true);
        }
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
        // Removed because I clear mListAdapters onPause().
        //mListAdapters.get(mListTabHost.getCurrentTab()).notifyDataSetChanged();
    }

    /********************************************************************************************
     * Function name: addListAdapter
     *
     * Description:   Adds a list adapter for mListView to keep track of the info in popList
     *
     * Parameters:    popList - the new list whose info needs to be kept track of
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
     * Description:   Gets the PoPList whose tab we have selected
     *
     * Parameters:    none
     *
     * Returns:       the current list selected
     ******************************************************************************************/

    public PoPList getCurrentPoPList()
    {
        PoPList list = null;
        int currentTabIndex = mListTabHost.getCurrentTab();

        if (TabHost.NO_ID != currentTabIndex) {
            list = mPoPLists.getList(currentTabIndex);
        }

        return list;
    }
    /********************************************************************************************
     * Function name: getNumGLists
     *
     * Description:   Gets the total number of GroceryLists
     *
     * Parameters:    none
     *
     * Returns:       the total number of GLists
     ******************************************************************************************/

    public int getNumPoPList()
    {
        return mPoPLists.getSize();
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
        Log.d("GroceryListActivity", "showDeleteButton function entered");
        position = pos;
        View child = mListView.getChildAt(pos - mListView.getFirstVisiblePosition());
        if (child != null) {

            delete = (Button) child.findViewById(R.id.bDelete);
           /* delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    PoPList poPList = getCurrentPoPList();
                    poPList.delete(getLastClickedItem());
                    mListAdapters.get(mListTabHost.getCurrentTab()).notifyDataSetChanged();

                }
            });*/
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
     * Description:
     *
     * Parameters:
     *
     * Returns:
     ******************************************************************************************/
    private boolean hideDeleteButton(final int pos) {
        Log.d("GroceryListActivity", "hideDeleteButton function entered");
        position = pos;
        View child = mListView.getChildAt(pos - mListView.getFirstVisiblePosition());
        if (child != null) {

            delete = (Button) child.findViewById(R.id.bDelete);
            /*delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    PoPList poPList = getCurrentPoPList();
                    poPList.delete(pos);
                    mListAdapters.get(mListTabHost.getCurrentTab()).notifyDataSetChanged();

                }
            });*/
            if (delete != null)
            {
                Log.d("GroceryListActivity", "swiping delete button");
                if (delete.getVisibility() == View.VISIBLE) {
                    Log.d("GroceryListActivity", " delete button not visible");
                    Animation deleteAnimation =
                            AnimationUtils.loadAnimation(this,
                                    R.anim.slide_in_right);

                    delete.startAnimation(deleteAnimation);

                    delete.setVisibility(View.INVISIBLE);

                    slideItemView(child, SLIDE_RIGHT_ITEM);

                }
            }
            else
            {
                Log.d("GroceryListActivity", "delete button is null");
            }
            return true;
        }
        return false;
    }

    /********************************************************************************************
     * Function name: slideItemView
     *
     * Description: Displays the slideItemView
     *
     * Parameters:
     *
     * Returns:
     ******************************************************************************************/
    public void slideItemView (View child, float translationAmount)
    {
       // CheckBox checkBox = (CheckBox) child.findViewById(R.id.itemCheckBox);
        //Button itemName = (Button) child.findViewById(R.id.bListItem);
        TextView qtyText = (TextView) child.findViewById(R.id.quantityText);
        EditText qtyInput = (EditText) child.findViewById(R.id.input_qty);

       //checkBox.setTranslationX(translationAmount);
       // itemName.setTranslationX(translationAmount);
        qtyText.setTranslationX(translationAmount);
        qtyInput.setTranslationX(translationAmount);
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
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        return super.dispatchTouchEvent(ev);
    }
    //https://github.com/sohambannerjee8/SwipeListView/blob/master/app/src/main/java/com/nisostech/soham/MainActivity.java

    public PoPLists getLists () {
        return mPoPLists;
    }

    /********************************************************************************************
     * Function name: writeListsToFile
     *
     * Description:   Writes the current mPoPLists to mPoPFileName to store the information
     *                stored in mPoPLists
     *
     * Parameters: None
     *
     * Returns: None
     ******************************************************************************************/
    private void writeListsToFile ()  {
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
     * Function name: readListsFromFile
     *
     * Description:   Reads from mPoPFileName the current PoPLists
     *
     * Parameters: None
     *
     * Returns: None
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
    }

    private void fillTabs (PoPLists popLists)
    {
        for (int i = 0; i < popLists.getSize(); ++i) {
            addListTab(popLists.getList(i), i);
        }
    }

    /********************************************************************************************
     * Function name: isOnEdit
     *
     * Description:   Returns whether or not the edit button is clicked
     *
     * Parameters: None
     *
     * Returns: mbIsOnEdit
     ******************************************************************************************/
    public boolean isOnEdit ()
    {
        return mbIsOnEdit;
    }

    /***********************************************************************************************
     *   Method:      getListInfoListener
     *   Description: If addList button is clicked, create dialog box and listener for finishing
     *                dialog
     *   Parameters:  view - the button that was clicked
     *   Returned:    NONE
     ***********************************************************************************************/

    public DialogListener getListInfoListener ()
    {
        return mListInfoListener;
    }

    /***********************************************************************************************
     *   Method:      getItemInfoListener
     *   Description: If addList button is clicked, create dialog box and listener for finishing
     *                dialog
     *   Parameters:  view - the button that was clicked
     *   Returned:    NONE
     ***********************************************************************************************/

    public DialogListener getItemInfoListener ()
    {
        return mItemInfoListener;
    }
}
