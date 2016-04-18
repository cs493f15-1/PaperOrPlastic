package edu.pacificu.cs493f15_1.paperorplasticapp.popList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
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
import edu.pacificu.cs493f15_1.paperorplasticapp.groceryList.GroceryListItemsActivity;
import edu.pacificu.cs493f15_1.paperorplasticapp.kitchenInventory.KitchenInventoryItemsActivity;
import edu.pacificu.cs493f15_1.paperorplasticjava.ListItem;
import edu.pacificu.cs493f15_1.paperorplasticjava.PoPLists;
import edu.pacificu.cs493f15_1.paperorplasticjava.SimpleList;
import edu.pacificu.cs493f15_1.utils.Constants;
import edu.pacificu.cs493f15_1.paperorplasticjava.User;

/***************************************************************************************************
 * Class:         POPListActivity
 * Description:   Creates POPListActivity class that is inherited by GroceryListActivity and
 * KitchenInventoryActivity that controls what occurs when the user
 * selects the grocery list option from the continue activity. Specifically
 * contains the list functionality.
 * Parameters:    N/A
 * Returned:      N/A
 **************************************************************************************************/
public abstract class PoPListActivity extends BaseActivity implements View.OnClickListener
{
  final int REQUEST_OK = 1;
  public static final float SLIDE_RIGHT_ITEM = 5;
  public static final float SLIDE_LEFT_ITEM = -145;

  private ListView mListOfListView;
  private PoPListAdapter mListAdapter;
  private DeleteListDialogListener mDeleteListListener;

  private Button mbAddList, mbAddItem, mbSettings, mbBack;
  private Spinner mGroupBySpinner;
  private FragmentManager fm;
  private ListView mListView;
  private boolean mbIsOnEdit, mbAddingItem, mbIsGrocery;
  private ListFragment listFrag;
  private ListItem newItem;
  private ToggleButton mbEdit;
  private int mLastClicked, mLastTabIndex;
  private String mPoPFileName;
  private PoPLists mPoPLists;
  private DialogListener mListInfoListener;

  private int mItemLayout;

  private ArrayList<ListItemAdapter> mListAdapters = new ArrayList<ListItemAdapter>();
  int position = 0;
  Button delete;

  private DialogListener mItemInfoListener;


  int mPositionClicked = 0;

  /**
   * NEW THINGS FOR TESTING
   */

  private Firebase mUserRef, mListsRef;
  private ValueEventListener mUserRefListener;
  private Firebase mSimpleListRef;
  private SimpleListItemAdapter mSimpleListItemAdapter;
  private ListView mListViewFB;
  private String mListId;

  private boolean mbCurrentUserIsOwner = false;
  private SimpleList mSimpleList;
  private ValueEventListener mSimpleListRefListener;

  private SimpleListAdapter mSimpleListAdapter;



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
  protected void onCreate(Bundle savedInstanceState)
  {
    //Used for add item
    Intent intent;

    super.onCreate(savedInstanceState);
  }

  protected void PoPOnCreate(Bundle savedInstanceState, PoPLists popLists, final int activitylayout,
                             final String fileName, final boolean isGrocery)
  {
    setContentView(activitylayout);
    mbIsOnEdit = false;
    mbIsGrocery = isGrocery;
    mPoPLists = popLists;
    mPoPFileName = fileName;
    mLastTabIndex = -1;
    mbAddingItem = false;

    //setupEditDeleteButtonsForGLists();

    //setupBackButton (isGrocery);

    setupToolbar();



    //Set Up ListView
    mListOfListView = (ListView) findViewById(R.id.listViewOfLists);
    mListOfListView.setItemsCanFocus(true);

    if (bUsingOffline)
    {
      setupPOPListAdapter();
    }
    else
    {
      setupFirebase();
      setupFBListAdapter();
    }

    setUpActivityTitle();

    setupSwipeListening();

    handleSwipingToDelete();
  }

  /*************************************************************************************************
   * Method:
   * Description:
   * Parameters:  N/A
   * Returned:    N/A
   ************************************************************************************************/
  private void setUpActivityTitle()
  {

    if (bUsingOffline)
    {
      if (mbIsGrocery)
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
            if (mbIsGrocery)
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

  /*************************************************************************************************
   * Method:
   * Description:
   * Parameters:   N/A
   * Returned:     N/A
   ************************************************************************************************/
  //called in PopListAdapter and in listView On click listener
  public void onListClick(String listName) {
      Intent intent;

      if (mbIsGrocery) {
          intent = new Intent(this, GroceryListItemsActivity.class);
      } else {
          intent = new Intent(this, KitchenInventoryItemsActivity.class);
      }

      intent.putExtra("PoPListName", listName);

      startActivity(intent);
  }

    /********************************************************************************************
     * Function name: onCreate
     * <p/>
     * Description:   Initializes all needed setup for objects in page
     * <p/>
     * Parameters:    savedInstanceState  - a bundle object
     * <p/>
     * Returns:       none
     ******************************************************************************************//*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("lifecycle", "In On Create");
    }

    *//********************************************************************************************
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
     ******************************************************************************************//*
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

    startActivity(intent);
  }*/

  /*************************************************************************************************
   * Method:
   * Description:
   * Parameters:   N/A
   * Returned:     N/A
   ************************************************************************************************/
  public void onFBListClick(String listId)
  {
    Intent intent;

    if (mbIsGrocery)
        intent = new Intent(this, GroceryListItemsActivity.class);
    else
    {
      intent = new Intent(this, KitchenInventoryItemsActivity.class);
    }

    intent.putExtra(Constants.KEY_LIST_ID, listId);

    startActivity(intent);
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
    //to be able to swipe list items over for delete
    mListOfListView.setOnTouchListener(new OnSwipeTouchListener(this, mListOfListView) {
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

            Toast toast = Toast.makeText(getApplicationContext(), mLastClicked, Toast.LENGTH_LONG);
            toast.show();

        }
    });
  }

  /*************************************************************************************************
   * Method:
   * Description:
   * Parameters:   N/A
   * Returned:     N/A
   ************************************************************************************************/
  public PoPLists getPoPLists()
  {
    return mPoPLists;
  }

  /*************************************************************************************************
   * Method:
   * Description:
   * Parameters:   N/A
   * Returned:     N/A
   ************************************************************************************************/
  public void onClick(View view)
  {
  }

  /*************************************************************************************************
   * Method:
   * Description:
   * Parameters:   N/A
   * Returned:     N/A
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
   * Method:
   * Description:
   * Parameters:   N/A
   * Returned:     N/A
   ************************************************************************************************/
  public void setupFirebase()
  {
    if (!bUsingOffline)
    {
      mUserRef = new Firebase(Constants.FIREBASE_URL_USERS).child(mEncodedEmail);
      if (mbIsGrocery)
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
   * Method:
   * Description:
   * Parameters:   N/A
   * Returned:     N/A
   ************************************************************************************************/
  public void setupPOPListAdapter()
  {
    //list adapter holds info of lists for listView
    mListAdapter = new PoPListAdapter(mListOfListView.getContext(),
        R.layout.listview_list_row_settings, mPoPLists.getArrayOfLists())
    {
    };

    mListOfListView.setAdapter(mListAdapter);

    mListOfListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //pass list name to onListClick
            onListClick((String) ((PoPListAdapter.ListHolder) view.getTag()).listName.getTag());
        }
    });
  }

  /*************************************************************************************************
   * Method:
   * Description:
   * Parameters:   N/A
   * Returned:     N/A
   ************************************************************************************************/
  public void setupFBListAdapter()
  {
    Log.e("current", "current encoded email: " + mEncodedEmail);
    mSimpleListAdapter = new SimpleListAdapter(this, SimpleList.class,
        R.layout.single_active_list, mListsRef, mEncodedEmail);


    mListOfListView.setAdapter(mSimpleListAdapter);


    mListOfListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SimpleList selectedList = mSimpleListAdapter.getItem(position);
            if (null != selectedList) {
                String listId = mSimpleListAdapter.getRef(position).getKey();
                onFBListClick(listId);
            }
        }
    });
  }

  /*************************************************************************************************
   * Method:
   * Description:
   * Parameters:   N/A
   * Returned:     N/A
   ************************************************************************************************/
  public void setupSwipeListening()
  {
    //set up swipe listening
    mListOfListView.setOnTouchListener(new OnSwipeTouchListener(this, mListOfListView) {
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
  public void onClickEditButton()
  {
    if (mListAdapters.size() != 0)
    {
      int size = mPoPLists.getSize();

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


  /**
   * Override onOptionsItemSelected to use main_menu instead of BaseActivity menu
   *
   * @param menu
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
        /* Inflate the menu; this adds items to the action bar if it is present. */
    getMenuInflater().inflate(R.menu.menu_inventory_settings, menu);

    MenuItem edit = menu.findItem(R.id.action_edit_lists);
    MenuItem settings = menu.findItem(R.id.action_settings);

    if (bUsingOffline)
    {
      menu.removeItem(R.id.action_logout);
    }

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
      onClickEditButton();

      return true;
    }
    if (id == R.id.action_settings)
    {
      return true;
    }

    return super.onOptionsItemSelected(item);
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
     * Method:      onAddItemClick
     * Description: If addItem button is clicked, call activity for searching for an item
     * Parameters:  view - the button that was clicked
     * Returned:    NONE
     ***********************************************************************************************/

    public void onAddItemClick(View view)
    {
        Intent addItemIntent = new Intent(PoPListActivity.this, ItemSearchActivity.class);
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

/*
  private void setupEditDeleteButtonsForGLists()
  {
    mbEdit = (ToggleButton) findViewById(R.id.bEdit);
  }
*/
  /********************************************************************************************
   * Function name: addListToFirebase
   * Description:   adds the list name to the database
   * Parameters:    None
   * Returns:       None
   ******************************************************************************************/
  private void addListToFirebase(String listName)
  {
    Firebase listsRef;

    if (mbIsGrocery)
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

  /*************************************************************************************************
   * Method:
   * Description:
   * Parameters:   N/A
   * Returned:     N/A
   ************************************************************************************************/
  public void onAddListClick(View v)
  {
    mListInfoListener = new DialogListener()
    {
      @Override
      public void onFinishNewListDialog(String newListName)
      {
        if (!bUsingOffline)
        {
          addListToFirebase(newListName);
        }
        else
        {
          if (!mPoPLists.ListNameExists(newListName)) //List name does not already exist
          {
            //add List to Lists and create a tab
            mPoPLists.addList(newListName);
            mListAdapter.notifyDataSetChanged();
          }
          else
          {
            Toast toast = Toast.makeText(getApplicationContext(),
                getResources().getString(R.string.sDuplicateListError), Toast.LENGTH_LONG);
            toast.show();
          }
        }
      }
    };

    fm = getSupportFragmentManager();
    ListDFragment listDFragment = new ListDFragment();
    listDFragment.show(fm, "Hi");
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

    //   OutputFileToLogcat("onPause");

    mPoPLists.clearLists();
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
    //read list info from file
    Context context = getApplicationContext();
    File popFile = context.getFileStreamPath(mPoPFileName);

    mbIsOnEdit = false;

    if (popFile.exists())
    {
      mPoPLists.clearLists();
      readListsFromFile(mPoPLists);
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

  /********************************************************************************************
   * Function name: readListsFromFile
   * <p/>
   * Description:   Reads from the mPoPFileName the current GroceryLists
   * <p/>
   * Parameters:    None
   * <p/>
   * Returns:       None
   ******************************************************************************************/
  private void readListsFromFile(PoPLists popLists)
  {
    FileInputStream popInput;
    Scanner listsInput;

    try
    {
      popInput = openFileInput(mPoPFileName);

      listsInput = new Scanner(popInput);
      mLastTabIndex = listsInput.nextInt();
      popLists.readListsFromFile(listsInput);
      listsInput.close();
    } catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
  }

  /********************************************************************************************
   * Function name: writeGListsToGroceryFile
   * <p/>
   * Description:   Writes the current mPoPLists to mPoPFileName to store the information
   * stored in mPoPLists
   * <p/>
   * Parameters:    None
   * <p/>
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
      listsOutput.print(mLastTabIndex + " ");
      mPoPLists.writeListsToFile(listsOutput);
      listsOutput.flush();
      listsOutput.close();
    } catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
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
   * Function name: showDeleteButton
   * Description:
   * Parameters:
   * Returns:
   ******************************************************************************************/
  private boolean showDeleteButton(final int pos)
  {
    mPositionClicked = pos;
    View child = mListOfListView.getChildAt(pos - mListOfListView.getFirstVisiblePosition());
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
   * Function name: hideDeleteButton
   * Description:
   * Parameters:
   * Returns:
   ******************************************************************************************/
  private boolean hideDeleteButton(final int pos)
  {
    mPositionClicked = pos;
    View child = mListOfListView.getChildAt(pos - mListOfListView.getFirstVisiblePosition());
    if (child != null)
    {

      delete = (Button) child.findViewById(R.id.bDelete);

      if (delete != null)
      {
        if (delete.getVisibility() == View.VISIBLE)
        {
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
  //https://github.com/sohambannerjee8/SwipeListView/blob/master/app/src/main/java/com/nisostech/soham/MainActivity.java

  public PoPLists getLists()
  {
    return mPoPLists;
  }


  /********************************************************************************************
   * Function name: slideItemView
   * Description:   Slides the list view item over
   * Parameters:    child             - the view that is sliding
   * translationAmount - how much the view will slide
   * Returns:       none
   ******************************************************************************************/
  private void slideItemView(View child, float translationAmount)
  {
    //can use this function to slide any other items in view over, does not slide over list name since we want to see the name
    // listName = (TextView) child.findViewById(R.id.listName);
    //listName.setTranslationX(translationAmount);

  }


  /********************************************************************************************
   * Function name: deleteList
   * Description:   When the activity is paused writes the PoPLists to mPoPFileName
   * Parameters:    position - which list will be deleted
   * Returns:       none
   ******************************************************************************************/
  public void deleteList()
  {
    mPoPLists.deleteList(mPositionClicked);
    mListAdapter.notifyDataSetChanged();
  }


  public void setPositionClicked(int position)
  {
    mPositionClicked = position;
  }

  /********************************************************************************************
   * Function name: DeleteListDialogListener
   * Description:   returns the mDeleteListListener for other class to use
   * Parameters:    none
   * Returns:       mDeleteListListener
   ******************************************************************************************/
  public DeleteListDialogListener getDeleteDialogListener()
  {
    return mDeleteListListener;
  }

  public void setDeleteListListener(DeleteListDialogListener listener)
  {
    mDeleteListListener = listener;
  }

  public void showDeleteListFragment()
  {
    fm = getSupportFragmentManager();
    DeletePoPListDFragment deleteListFragment = new DeletePoPListDFragment();
    deleteListFragment.show(fm, "Yeah");
  }


  private void OutputFileToLogcat(String where)
  {
    int i = 0;
    FileInputStream popInput;
    Scanner listsInput;

    try
    {
      popInput = openFileInput(mPoPFileName);
      listsInput = new Scanner(popInput);

      Log.d("Called From", where);

      while (listsInput.hasNextLine() || i > 20)
      {
        Log.d("Line:" + i, listsInput.nextLine());
        ++i;
      }

      listsInput.close();
    } catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
  }


  /********************************************************************************************
   * Function name: isOnEdit
   * Description:   Returns whether or not the edit button is clicked
   * Parameters: None
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

}

