package edu.pacificu.cs493f15_1.paperorplasticapp.popList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import edu.pacificu.cs493f15_1.paperorplasticapp.BaseActivity;
import edu.pacificu.cs493f15_1.paperorplasticapp.R;
import edu.pacificu.cs493f15_1.paperorplasticjava.PoPLists;
import edu.pacificu.cs493f15_1.paperorplasticjava.SimpleList;
import edu.pacificu.cs493f15_1.utils.Constants;

/**
 * Altered by heyd5159 on 2/6/2016.
 */

/***************************************************************************************************
 * Class:         PoPListSettingsActivity
 * Description:   Creates PoPListSettingsActivity class that controls what occurs when the
 * user reaches either the kitchen or grocery list settings page. Specifically,
 * handles what happens when the user specifies whether the PoP list
 * button should be displayed on the continue activity.
 * creates intents that take users to those specific pages.
 * Parameters:    N/A
 * Returned:      N/A
 **************************************************************************************************/

public abstract class PoPListSettingsActivity extends BaseActivity implements View.OnClickListener
{
  final float SLIDE_RIGHT_ITEM = 5;
  final float SLIDE_LEFT_ITEM = -145;

  private ListView mListOfListView;
  private PoPLists mPoPLists;
  private PoPListAdapter mListAdapter;
  private DeleteListDialogListener mDeleteListListener;
  private Button mbBack;
  private boolean mbIsOnEdit;
  private boolean mbGrocery;
  private FragmentManager fm;
  int position = 0;
  int mPositionClicked = 0;
  Button delete;
  private String mPoPFileName;

  private Firebase mUserRef, mListsRef;
  private ValueEventListener mUserRefListener;


  private SimpleListAdapter mSimpleListAdapter;


  /********************************************************************************************
   * Function name: onCreate
   * Description:   Initializes all needed setup for objects in page
   * Parameters:    savedInstanceState  - a bundle object
   * Returns:       none
   ******************************************************************************************/
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
  }

  protected void PoPOnCreate(Bundle savedInstanceState, PoPLists popLists, final int activitylayout, final String fileName, final boolean isGrocery)
  {
    setContentView(activitylayout);
    mbIsOnEdit = false;
    mbGrocery = isGrocery;
    mPoPLists = popLists;
    mPoPFileName = fileName;

    //setupBackButton(isGrocery);
    setupToolbar();
    setupFirebase();


    mListOfListView = (ListView) findViewById(R.id.listViewOfLists);

    //setupPOPListAdapter();
    setupFBListAdapter();

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
  public void onClick(View view)
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
      if (mbGrocery)
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
    if (mbGrocery)
    {
      listRef = new Firebase(Constants.FIREBASE_URL_GROCERY_LISTS);
    }
    else
    {
      listRef = new Firebase(Constants.FIREBASE_URL_KITCHEN_INVENTORY);
    }

    mSimpleListAdapter = new SimpleListAdapter(this, SimpleList.class,
      R.layout.single_active_list, listRef);


    mListOfListView.setAdapter(mSimpleListAdapter);
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
      onClickEditButton();

      return true;
    }

    if (id == R.id.action_settings)
    {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }


  private void setupBackButton(final boolean isGrocery)
  {
//    mbBack = (Button) findViewById(R.id.bBack);
//    mbBack.setOnClickListener(new View.OnClickListener()
//    {
//      @Override
//      public void onClick(View v)
//      {
//
//        //go back to activity that called this page (possible pages are settings
//        // or grocery list page
//        String caller = getIntent().getStringExtra("Caller");
//        Intent intent;
//        if (caller.equals("SettingsActivity"))
//        {
//          intent = new Intent(PoPListSettingsActivity.this, SettingsActivity.class); //TODO Come back to this maybe if statements?
//        }
//
//        else
//        {
//          if (isGrocery) //whether the caller was groceryList
//          {
//            intent = new Intent(PoPListSettingsActivity.this, GroceryListActivity.class);
//          }
//          else
//          {
//            intent = new Intent(PoPListSettingsActivity.this, KitchenInventoryActivity.class);
//          }
//        }
//        startActivity(intent);
//      }
//    });
  }

  /***********************************************************************************************
   * Method:      onClickEditButton
   * Description: Handles click of edit button
   * Parameters:  view
   * Returned:    NONE
   ***********************************************************************************************/
  public void onClickEditButton()
  {
    //if its clicked, show or hide delete buttons
    int size = mPoPLists.getSize();
    if (size > 0)
    {

      if (!mbIsOnEdit)
      {
        mbIsOnEdit = true;
        Log.d("PopListSettings", Boolean.toString(mbIsOnEdit));
        //mbEdit.setChecked(mbIsOnEdit);
        showDeleteButtons(size);
      }
      else
      {
        mbIsOnEdit = false;
        Log.d("PopListSettings", Boolean.toString(mbIsOnEdit));
        //mbEdit.setChecked(mbIsOnEdit);
        hideDeleteButtons(size);
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
      mPoPLists.writeListsToFile(listsOutput);
      listsOutput.flush();
      listsOutput.close();
    } catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
  }

  /********************************************************************************************
   * Function name: showDeleteButton
   * <p/>
   * Description:   Shows the delete button for the child view within listView and sets the
   * onClickListener for the delete button
   * <p/>
   * Parameters:    pos - the child position within the list view whose delete button will be
   * shown
   * <p/>
   * Returns:       true if the child view with the button being hidden exists, else false
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
   * <p/>
   * Description:   Hides the delete button on each list view child
   * <p/>
   * Parameters:    pos - the child position within the list view
   * <p/>
   * Returns:       true if the child view with the button being hidden exists, else false
   ******************************************************************************************/
  private boolean hideDeleteButton(final int pos)
  {
    mPositionClicked = pos;
    View child = mListOfListView.getChildAt(pos - mListOfListView.getFirstVisiblePosition());
    if (child != null)
    {

      delete = (Button) child.findViewById(R.id.bDelete);
            /*delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                }
            });*/
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
   * Function name: slideItemView
   * <p/>
   * Description:   Slides the list view item over
   * <p/>
   * Parameters:    child             - the view that is sliding
   * translationAmount - how much the view will slide
   * <p/>
   * Returns:       none
   ******************************************************************************************/

  private void slideItemView(View child, float translationAmount)
  {
    //can use this function to slide any other items in view over, does not slide over list name since we want to see the name
    // listName = (TextView) child.findViewById(R.id.listName);
    //listName.setTranslationX(translationAmount);

  }


  /********************************************************************************************
   * Function name: onResume
   * <p/>
   * Description:   When the activity is resumed reads in PoPLists from mPoPFileName
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
    File groceryFile = context.getFileStreamPath(mPoPFileName);

    if (groceryFile.exists())
    {
      mPoPLists.clearLists();
      readListsFromFile(mPoPLists);
    }
  }

  /********************************************************************************************
   * Function name: onPause
   * <p/>
   * Description:   When the activity is paused writes the PoPLists to mPoPFileName
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

  }

  /********************************************************************************************
   * Function name: deleteList
   * <p/>
   * Description:   When the activity is paused writes the PoPLists to mPoPFileName
   * <p/>
   * Parameters:    position - which list will be deleted
   * <p/>
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
   * <p/>
   * Description:   returns the mDeleteListListener for other class to use
   * <p/>
   * Parameters:    none
   * <p/>
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

}