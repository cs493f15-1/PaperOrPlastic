package edu.pacificu.cs493f15_1.paperorplasticapp.popList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import edu.pacificu.cs493f15_1.paperorplasticapp.R;
import edu.pacificu.cs493f15_1.paperorplasticjava.ListItem;
import edu.pacificu.cs493f15_1.paperorplasticjava.NutritionFactModel;
import edu.pacificu.cs493f15_1.paperorplasticjava.PoPList;
import edu.pacificu.cs493f15_1.paperorplasticjava.PoPLists;

import edu.pacificu.cs493f15_1.paperorplasticapp.BaseActivity;

/**
 * Created by alco8653 on 4/5/2016.
 */
public class PoPListItemsActivity extends BaseActivity
{
  static final float SLIDE_RIGHT_ITEM = 5;
  static final float SLIDE_LEFT_ITEM = -145;
  final int REQUEST_OK = 1;

  private ListView mItemListView;
  private ListItemAdapter mItemAdapter;
  private DeleteListDialogListener mDeleteListListener;
  //private Button mbBack;
  private ToggleButton mbEdit;
  private FloatingActionButton mbAddItem;
  private boolean mbIsOnEdit, mbAddingItem;
  private FragmentManager fm;
  private String mPoPListName;
  int mPositionClicked = 0;
  private Button delete;
  private PoPList mPoPList;
  private PoPLists mPoPLists;
  private String mPoPFileName;
  int mLastTabIndex;
  private ListItem newItem;
  private boolean mbIsGrocery;
  private Spinner mGroupBySpinner;

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

  protected void PoPOnCreate (Bundle savedInstanceState, PoPLists popLists, final String fileName, final boolean isGrocery)
  {
    setContentView(R.layout.activity_list_items);
    mbIsGrocery = isGrocery;
    mPoPFileName = fileName;
    mbIsOnEdit = false;
    mPoPLists = popLists;
    //get current viewing list
    mPoPListName = getIntent().getStringExtra("PoPListName");

    Log.d("PoPListItemsActivity", "PopList passed through: " + mPoPListName);

    readListsFromFile(popLists);
    mPoPList = popLists.getListByName(mPoPListName);
       /*if (1 == mPoPList.describeContents())
       {
           mPoPFileName = GroceryLists.GROCERY_FILE_NAME;
       }
        else
       {
           mPoPFileName = KitchenInventories.KITCHEN_FILE_NAME;
       }*/

    //set up add item button
    mbAddItem = (FloatingActionButton) findViewById(R.id.bAddList);

    setupEditDeleteButtonsForGLists();

    setUpListView();

    //setupBackButton (isGrocery);

    //setup the sorting group by spinner (drop down list sorting)
    setUpGroupSpinnerHandleSorting();
  }

  private void setUpListView()
  {
    mItemListView = (ListView) findViewById(R.id.listViewOfItems);
    //list adapter holds info of lists for listView
    //TODO: change name of grocery_list_item.xml to something generic to use in kitchen inventory as well
    mItemAdapter = new ListItemAdapter(this,
      R.layout.grocery_list_item, mPoPList.getItemArray()) {
    };

    mItemListView.setAdapter(mItemAdapter);

    //set up swipe listening
    mItemListView.setOnTouchListener(new OnSwipeTouchListener(this, mItemListView) {
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

    mItemListView.setItemsCanFocus(true);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == REQUEST_OK) {
      if (resultCode == RESULT_OK) {
        String item_name = data.getStringExtra("item_name");
        newItem = new ListItem(item_name);

        newItem.setAll(0, 0, 1, 0.00, 0, false, "init", new NutritionFactModel());
        newItem.setNutritionFacts(0, 0, 0, 0, 0, 0); //Initializing for outputing to a file;
        //OutputFileToLogcat("onActivityResult Part 1");
        mPoPLists.clearLists();
        readListsFromFile(mPoPLists);
        addItemToListView(newItem);
        writeListsToFile();


        mbAddingItem = true;
      }
    }
  }

  /********************************************************************************************
   * Function name: addItemToListView
   * <p/>
   * Description:   Adds item layout to listView as a new row and adds it to listadapter
   * <p/>
   * Parameters:    newItem - the new ListItem being added
   * <p/>
   * Returns:       none
   ******************************************************************************************/
  public void addItemToListView (ListItem newItem)
  {
    boolean bItemExisted = mPoPList.addItem(newItem);

    if (bItemExisted)
    {
      Context context = getApplicationContext();
      CharSequence text = newItem.getItemName() + " quantity was incremented!";
      int duration = Toast.LENGTH_LONG;

      Toast toast = Toast.makeText(context, text, duration);
      toast.show();
    }

    Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
      public void run() {
        // Actions to do after 60 milliseconds
        mItemAdapter.notifyDataSetChanged();

      }
    }, 60);


       /* if (mPoPList.addItem(newItem))
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.sDuplicateItemError), Toast.LENGTH_LONG);
            toast.show();
        }*/

    //re-sort the list depending on the current sorting category
    switch (mPoPList.getCurrentSortingValue()) {

      case PoPList.SORT_ALPHA:
        mPoPList.sortListByName();

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


  /***********************************************************************************************
   * Method:      setUpGroupSpinnerHandleSorting
   * Description: Sets up the group by drop down menu and takes care of sorting the list items
   * within grocery lists.
   * Parameters:  NONE
   * Returned:    NONE
   ***********************************************************************************************/
  private void setUpGroupSpinnerHandleSorting() {
    mGroupBySpinner = (Spinner) findViewById(R.id.GroupBySpinner);
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(PoPListItemsActivity.this,
      android.R.layout.simple_spinner_item, PoPList.GroupByStrings);

    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    mGroupBySpinner.setAdapter(adapter);
    mGroupBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        PoPList list = mPoPList;

        if (null != list) {
          switch (position) {
            case PoPList.SORT_NONE: // first item in dropdown currently blank
              mPoPList.setCurrentSortingValue(PoPList.SORT_NONE);
              break;
            case PoPList.SORT_ALPHA: //second item in dropdown currently alphabetical

              mPoPList.setCurrentSortingValue(PoPList.SORT_ALPHA);
              mPoPList.sortListByName();
              mItemAdapter.notifyDataSetChanged();

              break;
            case PoPList.SORT_CAL: //calories
              mPoPList.setCurrentSortingValue(PoPList.SORT_CAL);
              break;
            case PoPList.SORT_DATE: //date entered
              mPoPList.setCurrentSortingValue(PoPList.SORT_DATE);
              break;
            case PoPList.SORT_AISLE: //aisle
              mPoPList.setCurrentSortingValue(PoPList.SORT_AISLE);
              break;
            case PoPList.SORT_PRICE: //price
              mPoPList.setCurrentSortingValue(PoPList.SORT_PRICE);
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

  public void onClick (View view)
  {
  }


   /* private void setupBackButton (final boolean isGrocery) {
        mbBack = (Button) findViewById(R.id.bBack);
        mbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go back to activity that called this page (possible pages are settings
                // or grocery list page
                String caller = getIntent().getStringExtra("Caller");
                Intent intent;
                if (caller.equals("SettingsActivity"))
                {
                    intent = new Intent(PoPListSettingsActivity.this, SettingsActivity.class); //TODO Come back to this maybe if statements?
                }
                else
                {
                    if (isGrocery) //whether the caller was groceryList
                    {
                        intent = new Intent(PoPListSettingsActivity.this, GroceryListActivity.class);
                    }
                    else
                    {
                        intent = new Intent(PoPListSettingsActivity.this, KitchenInventoryActivity.class);
                    }
                }
                startActivity(intent);
            }
        });
    }*/

  private void setupEditDeleteButtonsForGLists ()
  {
    mbEdit = (ToggleButton) findViewById (R.id.bEdit);
        /*mbEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if its clicked, show or hide delete buttons
                int size = mPoPLists.getSize();
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
                            hideDeleteButton(i);
                        }
                    }
                }
            }
        });*/
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
    int size = mPoPList.getSize();
    if (size > 0) {

      if (!mbIsOnEdit) {
        mbIsOnEdit = true;
        Log.d("PopListItems", Boolean.toString(mbIsOnEdit));
        mbEdit.setChecked(mbIsOnEdit);
        showDeleteButtons(size);
      } else {
        mbIsOnEdit = false;
        Log.d("PopListItems", Boolean.toString(mbIsOnEdit));
        mbEdit.setChecked(mbIsOnEdit);
        hideDeleteButtons(size);
      }
    }

  }


  /***********************************************************************************************
   * Method:      onAddItemClick
   * Description: If addItem button is clicked, call activity for searching for an item
   * Parameters:  view - the button that was clicked
   * Returned:    NONE
   ***********************************************************************************************/

  public void onAddItemClick(View view) {
    Intent addItemIntent = new Intent(PoPListItemsActivity.this, ItemSearchActivity.class);

    if (mbIsGrocery) {
      addItemIntent.putExtra("Caller", "GroceryListActivity");
    } else {
      addItemIntent.putExtra("Caller", "KitchenInventoryActivity");
    }
    startActivityForResult(addItemIntent, REQUEST_OK);
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
    View child = mItemListView.getChildAt(pos - mItemListView.getFirstVisiblePosition());
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
    View child = mItemListView.getChildAt(pos - mItemListView.getFirstVisiblePosition());
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

  public void slideItemView (View child, float translationAmount)
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
  public void deleteItem ()
  {
    mPoPList.delete(mPositionClicked);
    mItemAdapter.notifyDataSetChanged();
  }


  public void setPositionClicked (int position)
  {
    mPositionClicked = position;
  }

  /********************************************************************************************
   * Function name: DeleteItemDialogListener
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

  public PoPList getPoPList()
  {
    return mPoPList;
  }

  public ListItemAdapter getItemAdapter()
  {
    return mItemAdapter;
  }

  public boolean isOnEdit()
  {
    return mbIsOnEdit;
  }

}
