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
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

import edu.pacificu.cs493f15_1.paperorplasticapp.R;
import edu.pacificu.cs493f15_1.paperorplasticapp.groceryList.GroceryListSettingsActivity;
import edu.pacificu.cs493f15_1.paperorplasticapp.kitchenInventory.KitchenInventorySettingsActivity;
import edu.pacificu.cs493f15_1.paperorplasticapp.menu.ContinueActivity;
import edu.pacificu.cs493f15_1.paperorplasticjava.ListItem;
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
public abstract class PoPListActivity extends FragmentActivity implements ListDFragment.EditNameDialogListener {

    final float SLIDE_RIGHT_ITEM = 5;
    final float SLIDE_LEFT_ITEM = -145;

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
     *
     * Description:   Initializes all needed setup for objects in page
     *
     * Parameters:    savedInstanceState  - a bundle object
     *
     * Returns:       none
     ******************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Log.i("lifecycle", "In On Create");
    }
    /********************************************************************************************
     * Function name: PoPOnCreate
     * <p/>
     * Description:   a function that is used in the OnCreate of GroceryListActivity and
     *                KitchenInventoryActivity and is used to implement the functionality of the
     *                Activity.
     * <p/>
     * Parameters:    savedInstanceState  - a bundle object
     *                popList             - The lists object created in GroceryListActivity or KitchenInventoryActivity
     *                activityLayout      - the layout of GroceryListActivity or KitchenInventoryActivity
     *                itemLayout          - the layout of the items in GroceryListActivity or KitchenInventoryActivity
     *                fileName            - the file which the PoPLists should be stored in
     *                isGrocery           - A boolean on whether the activity is called from GroceryListActivity or not
     * <p/>
     * Returns:       none
     ******************************************************************************************/
    protected void PoPOnCreate (Bundle savedInstanceState, PoPLists popLists,
                                final int activityLayout, final int itemLayout,
                                final String fileName, final boolean isGrocery)
    {
        setContentView(activityLayout);

        mPoPLists = popLists;
        mItemLayout = itemLayout;
        mPoPFileName = fileName;
        mbIsOnEdit = false;

        //to view items
        mListView = (ListView) findViewById(R.id.listView);

        handleSwipingToDelete();

        //setup tabs
        setupTabs();

        //setup edit button
        setupEditDeleteButtonsForLists();

        //setup back buttons
        setupBackButton(isGrocery);

        //setup settings activity button
        setupSettingsActivityButton(isGrocery);

        //setup addList and addItem buttons
        setupAddListAndAddItemButtons();

        //setup the sorting group by spinner (drop down list sorting)
        setUpGroupSpinnerHandleSorting();

        addAllExistingListsInPoPListsToTabs();
    }




    /***********************************************************************************************
    *   Method:      handleSwipingToDelete
    *   Description: Handles when user swipes left or right on list items. Will show or
    *                hide delete buttons depending on the status of the edit button.
    *   Parameters:  NONE
    *   Returned:    NONE
    ***********************************************************************************************/
    private void handleSwipingToDelete ()
    {
/*
        mListView.setOnTouchListener(new OnSwipeTouchListener(this, mListView) {
            @Override
            public void onSwipeRight(int pos) {
                //Toast.makeText (GroceryListActivity.this, "right", Toast.LENGTH_LONG).show();

                if (!mbIsOnEdit) {
                    hideDeleteButton(pos);
                }

                if (mbIsOnEdit) {
                    if (mListAdapters.size() != 0) {
                        int size = getCurrentPoPList().getSize();
                        if (size > 0) {

                            for (int i = 0; i < size; i++) {
                                showDeleteButton(i);
                            }
                        }
                    }
                }
            }

            @Override
            public void onSwipeLeft(int pos) {
                //Toast.makeText (GroceryListActivity.this, "left", Toast.LENGTH_LONG).show();
                if (!mbIsOnEdit) {
                    showDeleteButton(pos);
                }
            }
        });
*/

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
        });
    }

    /***********************************************************************************************
     *   Method:      setupEditDeleteButtonsForLists
     *   Description: Sets up the grocery list settings edit button. Handles what happens when user
     *                selects edit when viewing grocery lits.
     *   Parameters:  NONE
     *   Returned:    NONE
     ***********************************************************************************************/
    private void setupEditDeleteButtonsForLists()
    {
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

                            //showDeleteButton also gets rid of the delete button so we might not need this check
                            //TODO might need to show again if tab is changed
                            mbIsOnEdit = false;
                            for (int i = 0; i < size; i++) {
                                hideDeleteButton(i);
                            }
                        }
                    }
                }
            }
        });
    }

    /***********************************************************************************************
    *   Method:      setupTabs
    *   Description: Sets up tabs
    *   Parameters:  NONE
    *   Returned:    NONE
    ***********************************************************************************************/
    private void setupTabs ()
    {
        mListTabHost = (TabHost) findViewById(R.id.listTabHost);
        mListTabHost.setup();
        mListTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                mListTabHost.setCurrentTab(Integer.parseInt(tabId));

                if (mListAdapters.size() > 0) {
                    mListView.setAdapter(mListAdapters.get(Integer.parseInt(tabId)));
                }

            }
        });
    }
    /***********************************************************************************************
     *   Method:      setupBackButton
     *   Description: Setup back button that takes user from grocery list page to continue page
     *   Parameters:  NONE
     *   Returned:    NONE
     ***********************************************************************************************/
    private void setupBackButton (final boolean isGrocery)
        {
        mbBack = (Button) findViewById (R.id.bBackToHome);
        mbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGrocery) {
                    Intent intent = new Intent(PoPListActivity.this, ContinueActivity.class);
                    intent.putExtra("Caller", "GroceryListActivity");
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(PoPListActivity.this, ContinueActivity.class);
                    intent.putExtra("Caller", "KitchenInventoryActivity");
                    startActivity(intent);
                }
            }
        });
    }

    /***********************************************************************************************
     *   Method:      setupAddListAndAddItemButtons
     *   Description: Sets up addList and addItem buttons
     *   Parameters:  NONE
     *   Returned:    NONE
     ***********************************************************************************************/
    private void setupAddListAndAddItemButtons ()
    {
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
                NewListItemDFragment newItemFragment = new NewListItemDFragment();
                newItemFragment.show(fm, "Hi");

            }
        });
    }

    /***********************************************************************************************
     *   Method:      setupSettingsActivityButton
     *   Description: Sets up settings activity button so that when the user selects the settings
     *                button from the grocery list activity they can be taken to the grocer list
     *                settings activity
     *   Parameters:  NONE
     *   Returned:    NONE
     ***********************************************************************************************/
    private void setupSettingsActivityButton (final boolean isGrocery)
    {
        mbSettings = (Button) findViewById(R.id.bListSettings);
        mbSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGrocery) {
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

    private void addAllExistingListsInPoPListsToTabs() {
        for (int i = 0; i < mPoPLists.getSize(); i++) {
            addListTab(mPoPLists.getList(i), i);
        }
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

        Log.i("lifecycle", "In On Pause");

        writeListsToFile();
        mPoPLists.clearLists();
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

        Log.i("lifecycle", "In On Resume");

        Context context = getApplicationContext();
        File popFile = context.getFileStreamPath(mPoPFileName);

        if (popFile.exists()) {
            mPoPLists.clearLists();
            readListsFromFile(mPoPLists);
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
        if (!mPoPLists.ListNameExists(newListName)) //List name does not already exist
        {
            //add List to Lists and create a tab
            mPoPLists.addList(newListName);

            addListTab(mPoPLists.getList(mPoPLists.getSize() - 1), mPoPLists.getSize() - 1);
        }
        else
        {
            //TODO output error to user saying List Name already exists
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
        mListAdapters.get(mListTabHost.getCurrentTab()).notifyDataSetChanged();
    }

    /********************************************************************************************
     * Function name: showDeleteOnEdit
     *
     * Description:   Shows delete button for item if editing is on
     *
     * Parameters:    itemName - the item that will be deleted
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
        Log.d("GroceryListActivity", "showDeleteButton function entered");
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
    private void slideItemView (View child, float translationAmount)
    {
        CheckBox checkBox = (CheckBox) child.findViewById(R.id.itemCheckBox);
        Button itemName = (Button) child.findViewById(R.id.bListItem);
        TextView qtyText = (TextView) child.findViewById(R.id.quantityText);
        EditText qtyInput = (EditText) child.findViewById(R.id.input_qty);

        checkBox.setTranslationX(translationAmount);
        itemName.setTranslationX(translationAmount);
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

        for (int i = 0; i < popLists.getSize(); ++i) {
            addListTab(popLists.getList(i), i);
        }
    }
}
