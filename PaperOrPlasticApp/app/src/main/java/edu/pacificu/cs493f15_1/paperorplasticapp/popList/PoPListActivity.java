package edu.pacificu.cs493f15_1.paperorplasticapp.popList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.firebase.client.Firebase;
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
import edu.pacificu.cs493f15_1.paperorplasticjava.PoPList;
import edu.pacificu.cs493f15_1.paperorplasticjava.PoPLists;
import edu.pacificu.cs493f15_1.paperorplasticjava.SimpleList;
import edu.pacificu.cs493f15_1.utils.Constants;

/**
 * Created by sull0678 on 4/4/2016.
 */
public abstract class PoPListActivity extends BaseActivity implements View.OnClickListener
{
  final int REQUEST_OK = 1;
  public static final float SLIDE_RIGHT_ITEM = 5;
  public static final float SLIDE_LEFT_ITEM = -145;


  private ListView mListOfListView;
  private PoPLists mPoPLists;
  private PoPListAdapter mListAdapter;
  private DeleteListDialogListener mDeleteListListener;
  private DialogListener mListInfoListener;
  private Button mbBack;
  private ToggleButton mbEdit;
  private FragmentManager fm;
  private boolean mbIsOnEdit, mbIsGrocery;
  private String mPoPFileName;


  int mPositionClicked = 0;
  Button delete;
  int mLastTabIndex;

  private Firebase mUserRef, mListsRef;
  private ValueEventListener mUserRefListener;


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

  private SimpleListAdapter mSimpleListAdapter;
  private boolean bUseFB = true;


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
  protected void onCreate (Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
  }

  protected void PoPOnCreate (Bundle savedInstanceState, PoPLists popLists, final int activitylayout, final String fileName, final boolean isGrocery)
  {
    setContentView(activitylayout);
    mbIsOnEdit = false;
    mbIsGrocery = isGrocery;
    mPoPLists = popLists;
    mPoPFileName = fileName;
    mLastTabIndex = -1;

    setupEditDeleteButtonsForGLists();

    //setupBackButton (isGrocery);

    setupToolbar();
    setupFirebase();


    //Set Up ListView
    mListOfListView = (ListView) findViewById(R.id.listViewOfLists);


    //setupPOPListAdapter();
    setupFBListAdapter();


    setupSwipeListening();


    mListOfListView.setItemsCanFocus(true);
  }

  /*************************************************************************************************
   *   Method:
   *   Description:
   *   Parameters:   N/A
   *   Returned:     N/A
   ************************************************************************************************/
  //called in PopListAdapter and in listView On click listener
  public void onListClick (String listName)
  {
    Intent intent;

    if (mbIsGrocery)
    {
      intent = new Intent(this, GroceryListItemsActivity.class);
    }
    else
    {
      intent = new Intent(this, KitchenInventoryItemsActivity.class);
    }

    intent.putExtra("PoPListName", listName);

    startActivity(intent);
  }

  /*************************************************************************************************
   *   Method:
   *   Description:
   *   Parameters:   N/A
   *   Returned:     N/A
   ************************************************************************************************/
  public void onFBListClick (String listId)
  {
    Intent intent;

    if (mbIsGrocery)
    {
      intent = new Intent(this, GroceryListItemsActivity.class);
    }
    else
    {
      intent = new Intent(this, KitchenInventoryItemsActivity.class);
    }

    intent.putExtra(Constants.KEY_LIST_ID, listId);

    startActivity(intent);
  }



  /*************************************************************************************************
   *   Method:
   *   Description:
   *   Parameters:   N/A
   *   Returned:     N/A
   ************************************************************************************************/
  public PoPLists getPoPLists()
  {
    return mPoPLists;
  }

  /*************************************************************************************************
   *   Method:
   *   Description:
   *   Parameters:   N/A
   *   Returned:     N/A
   ************************************************************************************************/
  public void onClick (View view)
  {
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
   *   Method:
   *   Description:
   *   Parameters:   N/A
   *   Returned:     N/A
   ************************************************************************************************/
  public void setupPOPListAdapter()
  {
    //list adapter holds info of lists for listView
    mListAdapter = new PoPListAdapter(mListOfListView.getContext(),
      R.layout.listview_list_row_settings, mPoPLists.getArrayOfLists())
    {
    };

    mListOfListView.setAdapter(mListAdapter);

    mListOfListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id)
      {
        //pass list name to onListClick
        onListClick((String) ((PoPListAdapter.ListHolder) view.getTag()).listName.getTag());
      }
    });
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
    if (mbIsGrocery)
    {
      listRef = new Firebase(Constants.FIREBASE_URL_GROCERY_LISTS);
    }
    else
    {
      listRef = new Firebase(Constants.FIREBASE_URL_KITCHEN_INVENTORY);
    }

    mSimpleListAdapter = new SimpleListAdapter(this, SimpleList.class,
      R.layout.single_active_list, listRef, mEncodedEmail);


    mListOfListView.setAdapter(mSimpleListAdapter);


    mListOfListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id)
      {
        SimpleList selectedList = mSimpleListAdapter.getItem(position);
        if (null != selectedList)
        {
          String listId = mSimpleListAdapter.getRef(position).getKey();
          onFBListClick(listId);
        }
      }
    });
  }

  /*************************************************************************************************
   *   Method:
   *   Description:
   *   Parameters:   N/A
   *   Returned:     N/A
   ************************************************************************************************/
  public void setupSwipeListening()
  {
    //set up swipe listening
    mListOfListView.setOnTouchListener(new OnSwipeTouchListener(this, mListOfListView)
    {
      @Override
      public void onSwipeRight(int pos)
      {
        if (!mbIsOnEdit)
        {
          hideDeleteButton(pos);
        }

      }

      @Override
      public void onSwipeLeft(int pos)
      {

        if (!mbIsOnEdit)
        {
          showDeleteButton(pos);
        }
      }
    });
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
      //onClickEditButton();

      return true;
    }

    if (id == R.id.action_settings)
    {

      return true;
    }

    return super.onOptionsItemSelected(item);
  }


  /***********************************************************************************************
   * Method:      getListInfoListener
   * Description: If addList button is clicked, create dialog box and listener for finishing
   * dialog
   * Parameters:  view - the button that was clicked
   * Returned:    NONE
   ***********************************************************************************************/

  public DialogListener getListInfoListener() {
    return mListInfoListener;
  }

  private void setupEditDeleteButtonsForGLists ()
  {
    mbEdit = (ToggleButton) findViewById (R.id.bEdit);
  }

  /***********************************************************************************************
   *   Method:      onClickEditButton
   *   Description: Handles click of edit button
   *   Parameters:  view
   *   Returned:    NONE
   ***********************************************************************************************/
  public void onClickEditButton (View view)
  {

    //if its clicked, show or hide delete buttons
    int size = mPoPLists.getSize();
    if (size > 0) {

      if (!mbIsOnEdit) {
        mbIsOnEdit = true;
        Log.d("PopListSettings", Boolean.toString(mbIsOnEdit));
        mbEdit.setChecked(mbIsOnEdit);
        showDeleteButtons(size);
      } else {
        mbIsOnEdit = false;
        Log.d("PopListSettings", Boolean.toString(mbIsOnEdit));
        mbEdit.setChecked(mbIsOnEdit);
        hideDeleteButtons(size);
      }
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
   *   Method:
   *   Description:
   *   Parameters:   N/A
   *   Returned:     N/A
   ************************************************************************************************/
  public void onAddListClick (View v)
  {
    mListInfoListener = new DialogListener() {
      @Override
      public void onFinishNewListDialog(String newListName) {


        if (bUseFB)
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


//          if (!bUsingOffline)
//          {
//            addListToFirebase(newListName);
//          }
            
          } else {
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

  /********************************************************************************************
   * Function name: readListsFromFile
   *
   * Description:   Reads from the mPoPFileName the current GroceryLists
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
      mLastTabIndex = listsInput.nextInt();
      popLists.readListsFromFile(listsInput);
      listsInput.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  /********************************************************************************************
   * Function name: writeGListsToGroceryFile
   *
   * Description:   Writes the current mPoPLists to mPoPFileName to store the information
   *                stored in mPoPLists
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
      listsOutput.print(mLastTabIndex + " ");
      mPoPLists.writeListsToFile(listsOutput);
      listsOutput.flush();
      listsOutput.close();
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
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
    mPositionClicked = pos;
    View child = mListOfListView.getChildAt(pos - mListOfListView.getFirstVisiblePosition());
    if (child != null) {

      delete = (Button) child.findViewById(R.id.bDelete);

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
    mPositionClicked = pos;
    View child = mListOfListView.getChildAt(pos - mListOfListView.getFirstVisiblePosition());
    if (child != null) {

      delete = (Button) child.findViewById(R.id.bDelete);
            /*delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                }
            });*/
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
   * Description:   Slides the list view item over
   *
   * Parameters:    child             - the view that is sliding
   *                translationAmount - how much the view will slide
   *
   * Returns:       none
   ******************************************************************************************/

  private void slideItemView (View child, float translationAmount)
  {
    //can use this function to slide any other items in view over, does not slide over list name since we want to see the name
    // listName = (TextView) child.findViewById(R.id.listName);
    //listName.setTranslationX(translationAmount);

  }


  /********************************************************************************************
   * Function name: onResume
   *
   * Description:   When the activity is resumed reads in PoPLists from mPoPFileName
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


    //read list info from file
    Context context = getApplicationContext();
    File groceryFile = context.getFileStreamPath(mPoPFileName);

    if (groceryFile.exists())
    {
      mPoPLists.clearLists();
      readListsFromFile(mPoPLists);
    }
  }

  /********************************************************************************************
   * Function name: onPause
   *
   * Description:   When the activity is paused writes the PoPLists to mPoPFileName
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
   * Function name: deleteList
   *
   * Description:   When the activity is paused writes the PoPLists to mPoPFileName
   *
   * Parameters:    position - which list will be deleted
   *
   * Returns:       none
   ******************************************************************************************/
  public void deleteList ()
  {
    mPoPLists.deleteList(mPositionClicked);
    mListAdapter.notifyDataSetChanged();
  }


  public void setPositionClicked (int position)
  {
    mPositionClicked = position;
  }

  /********************************************************************************************
   * Function name: DeleteListDialogListener
   *
   * Description:   returns the mDeleteListListener for other class to use
   *
   * Parameters:    none
   *
   * Returns:       mDeleteListListener
   ******************************************************************************************/
  public DeleteListDialogListener getDeleteDialogListener()
  {
    return mDeleteListListener;
  }

  public void setDeleteListListener (DeleteListDialogListener listener)
  {
    mDeleteListListener = listener;
  }

  public void showDeleteListFragment()
  {
    fm = getSupportFragmentManager();
    DeletePoPListDFragment deleteListFragment = new DeletePoPListDFragment();
    deleteListFragment.show(fm, "Yeah");
  }


}