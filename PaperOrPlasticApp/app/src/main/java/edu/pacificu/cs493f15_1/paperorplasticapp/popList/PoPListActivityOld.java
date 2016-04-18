/**************************************************************************************************
 * File:     GroceryListActivity.java
 * Author:   Abigail Jones, Lauren Sullivan, Evan Heydemann
 * Date:     10/28/15
 * Class:    Capstone/Software Engineering
 * Project:  PaperOrPlastic Application
 * Purpose:  This activity will be the activity that is opened when the user selects the
 * grocery list button from the continue activity
 ***************************************************************************************************/

package edu.pacificu.cs493f15_1.paperorplasticapp.popList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ServerValue;
import com.firebase.client.ValueEventListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import edu.pacificu.cs493f15_1.paperorplasticapp.BaseActivity;
import edu.pacificu.cs493f15_1.paperorplasticapp.R;
import edu.pacificu.cs493f15_1.paperorplasticjava.ListItem;
import edu.pacificu.cs493f15_1.paperorplasticjava.PoPList;
import edu.pacificu.cs493f15_1.paperorplasticjava.PoPLists;
import edu.pacificu.cs493f15_1.paperorplasticjava.SimpleList;
import edu.pacificu.cs493f15_1.paperorplasticjava.User;
import edu.pacificu.cs493f15_1.utils.Constants;

/***************************************************************************************************
 * Class:         POPListActivity
 * Description:   Creates POPListActivity class that is inherited by GroceryListActivity and
 * KitchenInventoryActivity that controls what occurs when the user
 * selects the grocery list option from the continue activity. Specifically
 * contains the list functionality.
 * Parameters:    N/A
 * Returned:      N/A
 **************************************************************************************************/
public abstract class PoPListActivityOld extends BaseActivity
{
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

  private Firebase mUserRef, mListsRef;
  private ValueEventListener mUserRefListener;

  private boolean bIsGrocery;


  /**
   *  NEW THINGS FOR TESTING
   *
   */
  private Firebase mSimpleListRef;
  private SimpleListItemAdapter mSimpleListItemAdapter;
  private ListView mListViewFB;
  private String mListId;

  private boolean mbCurrentUserIsOwner = false;
  private SimpleList mSimpleList;
  private ValueEventListener mSimpleListRefListener;
  private SimpleListAdapterTab mSimpleListAdapterTab;



  /********************************************************************************************
   * Function name: onCreate
   * Description:   Initializes all needed setup for objects in page
   * Parameters:    savedInstanceState  - a bundle object
   * Parameters:    savedInstanceState  - a bundle object
   * Returns:       none
   ******************************************************************************************/
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    //Used for add item
    Intent intent;

    super.onCreate(savedInstanceState);
  }

  /********************************************************************************************
   * Function name: PoPOnCreate
   * Description:   a function that is used in the OnCreate of GroceryListActivity and
   * KitchenInventoryActivity and is used to implement the functionality of the
   * Activity.
   * Parameters:    savedInstanceState  - a bundle object
   * popList             - The lists object created in GroceryListActivity or KitchenInventoryActivity
   * activityLayout      - the layout of GroceryListActivity or KitchenInventoryActivity
   * itemLayout          - the layout of the items in GroceryListActivity or KitchenInventoryActivity
   * fileName            - the file which the PoPLists should be stored in
   * isGrocery           - A boolean on whether the activity is called from GroceryListActivity or not
   * Returns:       none
   ******************************************************************************************/
  protected void PoPOnCreate(Bundle savedInstanceState, PoPLists popLists,
                             final int activityLayout, final int itemLayout,
                             final String fileName, final boolean isGrocery)
  {
    setContentView(activityLayout);

    mPoPLists = popLists;
    mItemLayout = itemLayout;
    mPoPFileName = fileName;
    mbIsOnEdit = false;
    mbAddingItem = false;
    mbIsGrocery = isGrocery;
    //to view items
    mListView = (ListView) findViewById(R.id.listView); //this is the view for the list items
    bIsGrocery = isGrocery;

    if (null == mEncodedEmail)
    {
      bUsingOffline = true;
    }

    setupToolbar();
    setupFirebase();

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


    setUpActivityTitle(isGrocery);
    invalidateOptionsMenu();
  }

  /*************************************************************************************************
   *   Method:
   *   Description:
   *   Parameters:   N/A
   *   Returned:     N/A
   ************************************************************************************************/
  public void setupToolbar()
  {
    Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        /* Common toolbar setup */
    setSupportActionBar(toolbar);
        /* Add back button to the action bar */
    if (getSupportActionBar() != null)
    {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
  }

  /*************************************************************************************************
   *   Method:
   *   Description:
   *   Parameters:   N/A
   *   Returned:     N/A
   ************************************************************************************************/
  public void setupFirebase()
  {
    if (!bUsingOffline)
    {
      mUserRef = new Firebase(Constants.FIREBASE_URL_USERS).child(mEncodedEmail);
      if (bIsGrocery)
      {
        mListsRef = new Firebase(Constants.FIREBASE_URL_GROCERY_LISTS);
      }
      else
      {
        mListsRef = new Firebase(Constants.FIREBASE_URL_KITCHEN_INVENTORY);
      }
    }
  }


  /*************************************************************************************************
   *   Method:
   *   Description:
   *   Parameters:   N/A
   *   Returned:     N/A
   ************************************************************************************************/
  public void setupFBListAdapter()
  {
    Firebase listRef;

    //list adapter holds info of lists for listView
    if (bIsGrocery)
    {
      listRef = new Firebase(Constants.FIREBASE_URL_GROCERY_LISTS);
    }
    else
    {
      listRef = new Firebase(Constants.FIREBASE_URL_KITCHEN_INVENTORY);
    }

    //subject to change... using this rn to grab all of the lists the user has access to TODO

    //mSimpleListAdapterTab = new SimpleListAdapterTab(this, SimpleList.class, R.layout.single_active_list, listRef);

    //mListView.setAdapter(mSimpleListAdapterTab);
  }



  /********************************************************************************************
   * Function name:
   * Description:
   * Parameters:
   * Returns:
   ******************************************************************************************/
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data)
  {
    if (requestCode == REQUEST_OK)
    {
      if (resultCode == RESULT_OK)
      {
        String item_name = data.getStringExtra("item_name");
        newItem = new ListItem(item_name, null, null);

        readListsFromFile(mPoPLists);
        addItemToListView(newItem);
        writeListsToFile();

        mbAddingItem = true;
      }
    }
  }




  /**
   * Override onOptionsItemSelected to use main_menu instead of BaseActivity menu
   *
   * @param menu
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
        /* Inflate the menu; this adds items to the action bar if it is present. */
    getMenuInflater().inflate(R.menu.menu_pop_list_items, menu);

    MenuItem edit = menu.findItem(R.id.action_edit_lists);
    MenuItem settings = menu.findItem(R.id.action_settings);

    return true;
  }

  /**
   * Override onOptionsItemSelected to add action_settings only to the MainActivity
   *
   * @param item
   */
  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    int id = item.getItemId();

    if (id == R.id.action_edit_lists)
    {
      int size = getCurrentPoPList().getSize();
      if (size > 0)
      {
        if (!mbIsOnEdit)
        {
          mbIsOnEdit = true;

          //TODO make onEdit function that does this for loop and call when tab is changed as well (onTabChanged function, line 121)
          for (int i = 0; i < size; i++)
          {
            showDeleteButton(i);
          }
          mbAddItem.setTextColor(Color.rgb(170, 170, 170));
          mbAddItem.setEnabled(false);
        }
        else
        {

          //showDeleteButton also gets rid of the delete button so we might not need this check

          mbIsOnEdit = false;

          //showDeleteButton also gets rid of the delete button so we might not need this check
          //TODO might need to show again if tab is changed
          mbIsOnEdit = false;
          for (int i = 0; i < size; i++)
          {
            hideDeleteButton(i);
          }
        }
      }

      return true;
    }

    if (id == R.id.action_settings)
    {
      onSettingsClicked();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  /*************************************************************************************************
   * Method:
   * Description:
   * Parameters:  N/A
   * Returned:    N/A
   ************************************************************************************************/
  private void setUpActivityTitle(boolean isGrocery)
  {
    final boolean bGrocery = isGrocery;

    if (bUsingOffline)
    {
      if (bGrocery)
      {
        setTitle("Your Grocery Lists");
      }
      else
      {
        setTitle("Your Inventory");
      }

    }
    else
    {
      mUserRef = new Firebase(Constants.FIREBASE_URL_USERS).child(mEncodedEmail);
      mUserRefListener = mUserRef.addValueEventListener(new ValueEventListener()
      {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot)
        {
          User user = dataSnapshot.getValue(User.class);

          if (user != null)
          {
            String title;
            String name = user.getmName().split("\\s")[0];
            if (bGrocery)
            {
              title = name + "'s Lists";
            }
            else
            {
              title = name + "'s Inventory";
            }
            setTitle(title);
          }
        }

        @Override
        public void onCancelled(FirebaseError firebaseError)
        {

        }
      });
    }

  }

  /***********************************************************************************************
   * Method:      handleSwipingToDelete
   * Description: Handles when user swipes left or right on list items. Will show or
   * hide delete buttons depending on the status of the edit button.
   * Parameters:  NONE
   * Returned:    NONE
   ***********************************************************************************************/
  private void handleSwipingToDelete()
  {

    //to view items in list
    mListView = (ListView) findViewById(R.id.listView);
    //to be able to swipe list items over for delete
    mListView.setOnTouchListener(new OnSwipeTouchListener(this, mListView)
    {
      @Override
      public void onSwipeRight(int pos)
      {
        // Log.d("GroceryListActivity", "onSwipeRight function entered");
        if (!mbIsOnEdit)
        {
          hideDeleteButton(pos);
        }

      }

      @Override
      public void onSwipeLeft(int pos)
      {
        // Log.d("GroceryListActivity", "onSwipeLeft function entered");
        if (!mbIsOnEdit)
        {
          showDeleteButton(pos);
          showDeleteButton(pos); //TODO can this be removed?
        }
      }

      public void onItemClick(AdapterView<?> parent, View view,
                              int position, long id)
      {

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
  private void setupEditDeleteButtonsForLists()
  {

    mbEdit = (ToggleButton) findViewById(R.id.bEdit);
    mbEdit.setChecked(mbIsOnEdit);
  }

  /***********************************************************************************************
   * Method:      onClickEditButton
   * Description: Handles click of edit button
   * Parameters:  view
   * Returned:    NONE
   ***********************************************************************************************/
  public void onClickEditButton(View view)
  {
    if (mListAdapters.size() != 0)
    {
      int size = getCurrentPoPList().getSize();

      if (size > 0)
      {
        if (!mbIsOnEdit)
        {
          mbIsOnEdit = true;
          mbEdit.setChecked(mbIsOnEdit);
          //TODO make onEdit function that does this for loop and call when tab is changed as well (onTabChanged function, line 121)
          showDeleteButtons(size);
          mbAddItem.setTextColor(Color.rgb(170, 170, 170));
          mbAddItem.setEnabled(false);
        }
        else
        {

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
   * Method:      showDeleteButtons
   * Description: shows a delete button for every item in list view base on size passed in
   * Parameters:  size - size of list
   * Returned:    NONE
   ***********************************************************************************************/
  private void showDeleteButtons(int size)
  {
    for (int i = 0; i < size; i++)
    {
      showDeleteButton(i);
    }
  }

  /***********************************************************************************************
   * Method:      hideDeleteButtons
   * Description: hides a delete button for every item in list view base on size passed in
   * Parameters:  size - size of list
   * Returned:    NONE
   ***********************************************************************************************/
  private void hideDeleteButtons(int size)
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
  private void setupTabs()
  {
    mListTabHost = (TabHost) findViewById(R.id.listTabHost);
    mListTabHost.setup();
    mListTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener()
    {
      public void onTabChanged(String tabId)
      {

        mListTabHost.setCurrentTab(Integer.parseInt(tabId));

        if (mListAdapters.size() > 0)
        {
          mListView.setAdapter(mListAdapters.get(Integer.parseInt(tabId)));

          if (mbIsOnEdit)
          {
            Log.d("GroceryListActivity", "tab changed, on edit");

            Handler handler = new Handler();
            //wait for tab to be changed
            handler.postDelayed(new Runnable()
            {
              public void run()
              {
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
  private void setupBackButton()
  {
//    mbBack = (Button) findViewById(R.id.bBackToHome);
//    mbBack.setOnClickListener(new View.OnClickListener()
//    {
//      @Override
//      public void onClick(View v)
//      {
//        if (mbIsGrocery)
//        {
//          Intent intent = new Intent(PoPListActivityOld.this, ContinueActivity.class);
//          intent.putExtra("Caller", "GroceryListActivity");
//          startActivity(intent);
//        }
//        else
//        {
//          Intent intent = new Intent(PoPListActivityOld.this, ContinueActivity.class);
//          intent.putExtra("Caller", "KitchenInventoryActivity");
//          startActivity(intent);
//        }
//      }
//    });
  }

  /***********************************************************************************************
   * Method:      setupAddListAndAddItemButtons
   * Description: Sets up addList buttons
   * Parameters:  NONE
   * Returned:    NONE
   ***********************************************************************************************/
  private void setupAddListButtons()
  {
    //set up addList button
    mbAddList = (Button) findViewById(R.id.bAddList);
  }

  /***********************************************************************************************
   * Method:      setupAddItemButtons
   * Description: Sets up addItem buttons
   * Parameters:  NONE
   * Returned:    NONE
   ***********************************************************************************************/
  private void setupAddItemButtons()
  {
    //set up add item button
    mbAddItem = (Button) findViewById(R.id.bAddItem);
  }

  /***********************************************************************************************
   * Method:      onAddListClick
   * Description: If addList button is clicked, create dialog box and listener for finishing
   * dialog
   * Parameters:  view - the button that was clicked
   * Returned:    NONE
   ***********************************************************************************************/

  public void onAddListClick(View view)
  {
    mListInfoListener = new DialogListener()
    {
      @Override
      public void onFinishNewListDialog(String newListName)
      {

        if (!mPoPLists.ListNameExists(newListName)) //List name does not already exist
        {
          //add List to Lists and create a tab
          mPoPLists.addList(newListName);

          if (!bUsingOffline)
          {
            addListToFirebase(newListName);
          }


          addListTab(mPoPLists.getList(mPoPLists.getSize() - 1), mPoPLists.getSize() - 1);
        }
        else
        {
          //TODO output error to user saying List Name already exists
          Log.d("PoPListActivity", "Error with duplicate list names, not handled");
        }

      }
    };

    fm = getSupportFragmentManager();
    ListDFragment listDFragment = new ListDFragment();
    listDFragment.show(fm, "Hi");
  }

  /***********************************************************************************************
   * Method:      onAddItemClick
   * Description: If addItem button is clicked, call activity for searching for an item
   * Parameters:  view - the button that was clicked
   * Returned:    NONE
   ***********************************************************************************************/

  public void onAddItemClick(View view)
  {
    Intent addItemIntent = new Intent(PoPListActivityOld.this, ItemSearchActivity.class);
    addItemIntent.putExtra("num_list_items", getNumPoPList());

    if (mbIsGrocery)
    {
      addItemIntent.putExtra("Caller", "GroceryListActivity");
    }
    else
    {
      addItemIntent.putExtra("Caller", "KitchenInventoryActivity");
    }
    startActivityForResult(addItemIntent, REQUEST_OK);
  }

  /***********************************************************************************************
   * Method:      setupSettingsActivityButton
   * Description: Sets up settings activity button so that when the user selects the settings
   * button from the grocery list activity they can be taken to the grocer list
   * settings activity
   * Parameters:  NONE
   * Returned:    NONE
   ***********************************************************************************************/
  private void setupSettingsActivityButton()
  {
    mbSettings = (Button) findViewById(R.id.bListSettings);
    mbSettings.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {

//        if (mbIsGrocery)
//        {
//          Intent intent = new Intent(PoPListActivityOld.this, GroceryListSettingsActivity.class);
//          intent.putExtra("Caller", "GroceryListActivity");
//          startActivity(intent);
//        }
//        else
//        {
//          Intent intent = new Intent(PoPListActivityOld.this, KitchenInventorySettingsActivity.class);
//          intent.putExtra("Caller", "KitchenInventoryActivity");
//          startActivity(intent);
//        }
      }
    });
  }

  /***********************************************************************************************
   * Method:      setUpGroupSpinnerHandleSorting
   * Description: Sets up the group by drop down menu and takes care of sorting the list items
   * within grocery lists.
   * Parameters:  NONE
   * Returned:    NONE
   ***********************************************************************************************/
  private void setUpGroupSpinnerHandleSorting()
  {
    mGroupBySpinner = (Spinner) findViewById(R.id.GroupBySpinner);
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(PoPListActivityOld.this,
      android.R.layout.simple_spinner_item, PoPList.GroupByStrings);

    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    mGroupBySpinner.setAdapter(adapter);
    mGroupBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
    {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
      {
        PoPList currentList = getCurrentPoPList();

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
      public void onNothingSelected(AdapterView<?> parent)
      {
        //Nothing to do if the dropdown is not selected.
      }
    });
  }

  /********************************************************************************************
   * Function name: addAllExistingListsInPoPListsToTabs
   * <p/>
   * Description:   if offline, read the existing lists into tabs. if online, read from database
   * to add the list tab...
   * <p/>
   * Parameters:    none
   * <p/>
   * Returns:       none
   ******************************************************************************************/
  private void addAllExistingListsInPoPListsToTabs()
  {
    if (bUsingOffline)
    {
      for (int i = 0; i < mPoPLists.getSize(); i++)
      {
        addListTab(mPoPLists.getList(i), i);
      }
    }
    else
    {
      //TODO stuff in here for FB

    }
  }


  /********************************************************************************************
   * Function name: getCurrentListAdapter
   * <p/>
   * Description:   Returns the current List adapter being shown on the screen
   * <p/>
   * Parameters:    none
   * <p/>
   * Returns:       the current ListItemAdapter
   ******************************************************************************************/

  public ListItemAdapter getCurrentListAdapter()
  {
    return mListAdapters.get(mListTabHost.getCurrentTab());
  }

  /********************************************************************************************
   * Function name: onPause
   * <p/>
   * Description:   When the activity is paused writes the PoPLists to the file passed in OnCreate
   * <p/>
   * Parameters:    none
   * <p/>
   * Returns:       none
   ******************************************************************************************/
  @Override
  protected void onPause()
  {
    super.onPause();

    writeListsToFile();
    mPoPLists.clearLists();
    mListAdapters.clear();
  }

  /********************************************************************************************
   * Function name: onResume
   * <p/>
   * Description:   When the activity is resumed reads in PoPLists from the file passed in OnCreate
   * and updates mPoPLists with the information.
   * <p/>
   * Parameters:    none
   * <p/>
   * Returns:       none
   ******************************************************************************************/
  @Override
  protected void onResume()
  {
    super.onResume();

    Context context = getApplicationContext();
    File popFile = context.getFileStreamPath(mPoPFileName);

    mbIsOnEdit = false;

    //popFile.delete();

    if (popFile.exists())
    {
      mPoPLists.clearLists();
      readListsFromFile(mPoPLists);

      if (!mbAddingItem)
      {

      }

      fillTabs(mPoPLists);

      mbAddingItem = false;
    }
  }

  /********************************************************************************************
   * Function name: addListTab
   * Description:   Adds a tab to the top of the page corresponding to the newList passed in.
   * Parameters:    newList - a List object whose tab will be added to the top of the page
   * index   - the index of the newList in the PoPLists object, also the
   * new tab spec id
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
   * Function name: onFinishListDialog
   * Description:   When dialog for adding list is done, add list and list tab with text from
   * dialog as the new list name
   * Parameters:    newListName - the new list's name
   * Returns:       none
   ******************************************************************************************/

  public void onFinishListDialog(String newListName)
  {
    if (!mPoPLists.ListNameExists(newListName)) //List name does not already exist
    {
      //add List to Lists and create a tab
      mPoPLists.addList(newListName);


      if (!bUsingOffline)
      {
        addListToFirebase(newListName);
      }


      addListTab(mPoPLists.getList(mPoPLists.getSize() - 1), mPoPLists.getSize() - 1);
    }
    else
    {
      //TODO output error to user saying List Name already exists
    }
  }


  /********************************************************************************************
   * Function name: addItemToListView
   * Description:   Adds item layout to listView as a new row and adds it to listadapter
   * Parameters:    newItem - the new ListItem being added
   * Returns:       none
   ******************************************************************************************/
  public void addItemToListView(ListItem newItem)
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
    //mListAdapters.get(mListTabHost.getCurrentTab()).notifyDataSetChanged();
  }

  /********************************************************************************************
   * Function name: showDeleteOnEdit
   * Description:   Shows delete button for item if editing is on
   * Parameters:    itemName - the item that will be deleted
   * Returns:       none
   ******************************************************************************************/

  public void showDeleteOnEdit(String itemName)
  {
    int itemIndex = getCurrentPoPList().getItemIndex(itemName);
    if (mbIsOnEdit && itemIndex != -1)
    {
      showDeleteButton(itemIndex);
    }
  }

  /********************************************************************************************
   * Function name: addListAdapter
   * Description:   Adds a list adapter for mListView to keep track of the info in popList
   * Parameters:    popList - the new list whose info needs to be kept track of
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
   * Description:   Gets the PoPList whose tab we have selected
   * Parameters:    none
   * Returns:       the current list selected
   ******************************************************************************************/

  public PoPList getCurrentPoPList()
  {
    PoPList list = null;
    int currentTabIndex = mListTabHost.getCurrentTab();

    if (TabHost.NO_ID != currentTabIndex)
    {
      list = mPoPLists.getList(currentTabIndex);
    }

    return list;
  }

  /********************************************************************************************
   * Function name: showDeleteButton
   * Description:
   * Parameters:
   * Returns:
   ******************************************************************************************/
  private boolean showDeleteButton(final int pos)
  {
    Log.d("GroceryListActivity", "showDeleteButton function entered");
    position = pos;
    View child = mListView.getChildAt(pos - mListView.getFirstVisiblePosition());
    if (child != null)
    {

      delete = (Button) child.findViewById(R.id.bDelete);
      if (delete != null)
      {
        if (delete.getVisibility() == View.INVISIBLE)
        {
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
   * Function name: getNumGLists
   * Description:   Gets the total number of GroceryLists
   * Parameters:    none
   * Returns:       the total number of GLists
   ******************************************************************************************/

  public int getNumPoPList()
  {
    return mPoPLists.getSize();
  }


  /********************************************************************************************
   * Function name: hideDeleteButton
   * Description:
   * Parameters:
   * Returns:
   ******************************************************************************************/
  private boolean hideDeleteButton(final int pos)
  {
    Log.d("GroceryListActivity", "hideDeleteButton function entered");
    position = pos;
    View child = mListView.getChildAt(pos - mListView.getFirstVisiblePosition());
    if (child != null)
    {

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
        if (delete.getVisibility() == View.VISIBLE)
        {
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
   * Description: Displays the slideItemView
   * Parameters:
   * Returns:
   ******************************************************************************************/
  public void slideItemView(View child, float translationAmount)
  {
    TextView qtyText = (TextView) child.findViewById(R.id.quantityText);
    EditText qtyInput = (EditText) child.findViewById(R.id.input_qty);

    qtyText.setTranslationX(translationAmount);
    qtyInput.setTranslationX(translationAmount);
  }

  /********************************************************************************************
   * Function name: dispatchTouchEvent
   * Description:   calls the super for fragment activity for swiping
   * Parameters:    None
   * Returns:       None
   ******************************************************************************************/
  @Override
  public boolean dispatchTouchEvent(MotionEvent ev)
  {

    return super.dispatchTouchEvent(ev);
  }

  /********************************************************************************************
   * Function name:
   * Description:
   * Parameters:
   * Returns:
   ******************************************************************************************/
  public PoPLists getLists()
  {
    return mPoPLists;
  }

  /********************************************************************************************
   * Function name: writeListsToFile
   * Description:   Writes the current mPoPLists to mPoPFileName to store the information
   *                stored in mPoPLists
   * Parameters:    None
   * Returns:       None
   ******************************************************************************************/
  private void writeListsToFile()
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
    } catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
  }

  /********************************************************************************************
   * Function name: readListsFromFile
   * Description:   Reads from mPoPFileName the current PoPLists
   * Parameters: None
   * Returns: None
   ******************************************************************************************/
  private void readListsFromFile(PoPLists popLists)
  {
    FileInputStream popInput;
    Scanner listsInput;

    try
    {
      popInput = openFileInput(mPoPFileName);

      listsInput = new Scanner(popInput);
      popLists.readListsFromFile(listsInput);
      listsInput.close();
    } catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
  }

  /********************************************************************************************
   * Function name:
   * Description:
   * Parameters:
   * Returns:
   ******************************************************************************************/
  private void fillTabs(PoPLists popLists)
  {
    for (int i = 0; i < popLists.getSize(); ++i)
    {
      addListTab(popLists.getList(i), i);
    }
  }

  /********************************************************************************************
   * Function name: addListToFirebase
   * Description:   adds the list name to the database
   * Parameters:    None
   * Returns:       None
   ******************************************************************************************/
  private void addListToFirebase(String listName)
  {
    Firebase listsRef;

    if (bIsGrocery)
    {
      listsRef = new Firebase(Constants.FIREBASE_URL_GROCERY_LISTS);
    }
    else
    {
      listsRef = new Firebase(Constants.FIREBASE_URL_KITCHEN_INVENTORY);
    }

    Firebase newListRef = listsRef.push();

    final String listId = newListRef.getKey();

    HashMap<String, Object> timestampCreated = new HashMap<>();
    timestampCreated.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);

    SimpleList newSimpleList = new SimpleList(listName, mEncodedEmail, timestampCreated);

    newListRef.setValue(newSimpleList);
  }

  /********************************************************************************************
   * Function name:
   * Description:
   * Parameters:
   * Returns:
   ******************************************************************************************/
  public void onSettingsClicked()
  {

  }


  /********************************************************************************************
   * Function name: isOnEdit
   * <p/>
   * Description:   Returns whether or not the edit button is clicked
   * <p/>
   * Parameters: None
   * <p/>
   * Returns: mbIsOnEdit
   ******************************************************************************************/
  public boolean isOnEdit()
  {
    return mbIsOnEdit;
  }

  /***********************************************************************************************
   * Method:      getListInfoListener
   * Description: If addList button is clicked, create dialog box and listener for finishing
   * dialog
   * Parameters:  view - the button that was clicked
   * Returned:    NONE
   ***********************************************************************************************/

  public DialogListener getListInfoListener()
  {
    return mListInfoListener;
  }

  /***********************************************************************************************
   * Method:      getItemInfoListener
   * Description: If addList button is clicked, create dialog box and listener for finishing
   * dialog
   * Parameters:  view - the button that was clicked
   * Returned:    NONE
   ***********************************************************************************************/

  public DialogListener getItemInfoListener()
  {
    return mItemInfoListener;
  }

}