package edu.pacificu.cs493f15_1.paperorplasticapp.popList;

import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ServerValue;
import com.firebase.client.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import edu.pacificu.cs493f15_1.paperorplasticapp.BaseActivity;
import edu.pacificu.cs493f15_1.paperorplasticapp.R;
import edu.pacificu.cs493f15_1.paperorplasticapp.fbdialog.ShareListDialog;
import edu.pacificu.cs493f15_1.paperorplasticjava.ListItem;
import edu.pacificu.cs493f15_1.paperorplasticjava.PoPList;
import edu.pacificu.cs493f15_1.paperorplasticjava.PoPLists;
import edu.pacificu.cs493f15_1.paperorplasticjava.SimpleList;
import edu.pacificu.cs493f15_1.paperorplasticjava.SimpleListItem;
import edu.pacificu.cs493f15_1.paperorplasticjava.User;
import edu.pacificu.cs493f15_1.utils.Constants;
import edu.pacificu.cs493f15_1.utils.Utils;

/**
 * Created by alco8653 on 4/5/2016.
 */
public abstract class PoPListItemsActivity extends BaseActivity implements View.OnClickListener
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
  private DialogListener mListInfoListener;

  TextView mTBarTitle;

  /**
   FIREBASE GOODIES
   */
  private Firebase mListRef, mListItemsRef;
  private SimpleListItemAdapter mSimpleListItemAdapter;
  private String mListID;
  private boolean mbCurrentUserIsOwner = false;
  private SimpleList mSimpleList;
  private ValueEventListener mListRefListener;

  private boolean bScannedItem = false;
  private TextView formatText, contentText, textFont;

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
    setContentView(R.layout.activity_list_items);
    mbIsGrocery = isGrocery;
    mPoPFileName = fileName;
    mbIsOnEdit = false;
    mPoPLists = popLists;

    //get current viewing list


    initializeLayoutItems();

    mTBarTitle = (TextView) findViewById(R.id.toolbar_title_item);
    Typeface laneUpperFont = Typeface.createFromAsset(getAssets(), "fonts/laneWUnderLine.ttf");
    Typeface laneNarrowFont = Typeface.createFromAsset(getAssets(), "fonts/LANENAR.ttf");
    mTBarTitle.setTypeface(laneNarrowFont, Typeface.BOLD);
    mTBarTitle.setTextColor(Color.WHITE);

    if (bUsingOffline)
    {
      mPoPListName = getIntent().getStringExtra("PoPListName");
      Log.d("PoPListItemsActivity", "PopList passed through: " + mPoPListName);

      readListsFromFile(popLists);
      mPoPList = popLists.getListByName(mPoPListName);
      //setTitle(mPoPListName);
      setTitle("");
      mTBarTitle.setText(mPoPListName);
      setUpListView();
    }
    else
    {
      mListID = getIntent().getStringExtra(Constants.KEY_LIST_ID);
      if (null != mListID)
      {
        setupFirebase();
        setupFirebaseListItems();
      }
    }



       /*if (1 == mPoPList.describeContents())
       {
           mPoPFileName = GroceryLists.GROCERY_FILE_NAME;
       }
        else
       {
           mPoPFileName = KitchenInventories.KITCHEN_FILE_NAME;
       }*/

    //set up add item button


    setupEditDeleteButtonsForGLists();



    //setupBackButton (isGrocery);

    //setup the sorting group by spinner (drop down list sorting)
    setUpGroupSpinnerHandleSorting();
  }
  /*************************************************************************************************
   *   Method:
   *   Description:
   *   Parameters:   N/A
   *   Returned:     N/A
   ************************************************************************************************/
  private void initializeLayoutItems()
  {
    mbAddItem = (FloatingActionButton) findViewById(R.id.bAddList);
    mItemListView = (ListView) findViewById(R.id.listViewOfItems);
    mGroupBySpinner = (Spinner) findViewById(R.id.GroupBySpinner);
//    formatText = (TextView) findViewById (R.id.scan_format);
//    contentText = (TextView) findViewById (R.id.scan_content);

    setupToolbar();
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
    if (mbIsGrocery)
    {
      mListRef = new Firebase(Constants.FIREBASE_URL_GROCERY_LISTS).child(mListID);
      mListItemsRef = new Firebase(Constants.FIREBASE_URL_GROCERY_LIST_ITEMS).child(mListID);
    }
    else
    {
      mListRef = new Firebase(Constants.FIREBASE_URL_KITCHEN_INVENTORY).child(mListID);
      mListItemsRef = new Firebase(Constants.FIREBASE_URL_KITCHEN_INVENTORY_ITEMS).child(mListID);
    }
  }


  /*************************************************************************************************
   *   Method:
   *   Description:
   *   Parameters:   N/A
   *   Returned:     N/A
   ************************************************************************************************/
  private void setupFirebaseListItems()
  {

    mSimpleListItemAdapter = new SimpleListItemAdapter(this, SimpleListItem.class,
      R.layout.single_active_list_item,
        mListItemsRef.orderByChild(Constants.FIREBASE_PROPERTY_BOUGHT_BY),
        mListID, mEncodedEmail, mbIsGrocery);

    mItemListView.setAdapter(mSimpleListItemAdapter);

    mListRefListener = mListRef.addValueEventListener(new ValueEventListener()
    {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot)
      {
        SimpleList simpleList = dataSnapshot.getValue(SimpleList.class);
        if (null == simpleList)
        {
          finish();
          return;
        }
        mSimpleList = simpleList;
        mSimpleListItemAdapter.setList(mSimpleList);
        mbCurrentUserIsOwner = Utils.checkIfOwner(simpleList, mEncodedEmail);

        if (mbCurrentUserIsOwner)
        {
          Log.e("list owner", "user is owner. Encoded email: " + mEncodedEmail
              + " List: " + simpleList.getmListName() + " owner of list " +
              simpleList.getmOwner());
        }


        invalidateOptionsMenu();
        //setTitle(simpleList.getmListName());
        setTitle("");
        //String text = ;
       mTBarTitle.setText(simpleList.getmListName());
      }

      @Override
      public void onCancelled(FirebaseError firebaseError)
      {
        Log.e("LIST ITEMS","READ FAILED AH");
      }
    });

    setUpListViewFB();
  }
  /*************************************************************************************************
   *   Method:
   *   Description:
   *   Parameters:   N/A
   *   Returned:     N/A
   ************************************************************************************************/
  private void setUpListViewFB()
  {
    mItemListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
    {
      @Override
      public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
      {
        //TODO: what do we want to do on a long click
        return false;
      }
    });

    mItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id)
      {
        final SimpleListItem selectedListItem = mSimpleListItemAdapter.getItem(position);
        String itemId = mSimpleListItemAdapter.getRef(position).getKey();
        buyItemOnClick (selectedListItem, itemId);
      }
    });

    mItemListView.setOnTouchListener(new OnSwipeTouchListener(this, mItemListView) {
      @Override
      public void onSwipeRight(int pos) {
        hideDeleteButton2(pos);
      }

      @Override
      public void onSwipeLeft(int pos) {
        showDeleteButton2(pos);
      }
    });

  }


  public void buyItemOnClick(final SimpleListItem selectedListItem, String itemId)
  {
    if (selectedListItem != null)
    {
                        /* If current user is shopping */

                            /* Create map and fill it in with deep path multi write operations list */
      HashMap<String, Object> updatedItemBoughtData = new HashMap<String, Object>();

                            /* Buy selected item if it is NOT already bought */
      if (!selectedListItem.isbBought())
      {
        updatedItemBoughtData.put(Constants.FIREBASE_PROPERTY_BOUGHT, true);
        updatedItemBoughtData.put(Constants.FIREBASE_PROPERTY_BOUGHT_BY, mEncodedEmail);
      }
      else
      {
                                /* Return selected item only if it was bought by current user */
        if (selectedListItem.getmBoughtBy().equals(mEncodedEmail))
        {
          updatedItemBoughtData.put(Constants.FIREBASE_PROPERTY_BOUGHT, false);
          updatedItemBoughtData.put(Constants.FIREBASE_PROPERTY_BOUGHT_BY, null);
        }
      }
                            /* Do update */
      if (mbIsGrocery)
      {
        Firebase firebaseItemLocation = new Firebase(Constants.FIREBASE_URL_GROCERY_LIST_ITEMS)
            .child(mListID).child(itemId);
        firebaseItemLocation.updateChildren(updatedItemBoughtData, new Firebase.CompletionListener()
        {
          @Override
          public void onComplete(FirebaseError firebaseError, Firebase firebase)
          {
            if (firebaseError != null)
            {
              Log.d("Click item", "Error updating data" +
                  firebaseError.getMessage());
            }
          }
        });
      }
      else
      {
        Firebase firebaseItemLocation = new Firebase(Constants.FIREBASE_URL_KITCHEN_INVENTORY_ITEMS)
            .child(mListID).child(itemId);
        firebaseItemLocation.updateChildren(updatedItemBoughtData, new Firebase.CompletionListener()
        {
          @Override
          public void onComplete(FirebaseError firebaseError, Firebase firebase)
          {
            if (firebaseError != null)
            {
              Log.d("Click item", "Error updating data" +
                  firebaseError.getMessage());
            }
          }
        });
      }
    }

  }

  /*************************************************************************************************
   *   Method:
   *   Description:
   *   Parameters:   N/A
   *   Returned:     N/A
   ************************************************************************************************/
  private void setUpListView()
  {

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

  /*************************************************************************************************
   *   Method:
   *   Description:
   *   Parameters:   N/A
   *   Returned:     N/A
   ************************************************************************************************/
  //Return from ItemSearchActivity
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == REQUEST_OK) {
      if (resultCode == RESULT_OK) {

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

        if (bScannedItem)
        {
          IntentResult scanningResult = IntentIntegrator.parseActivityResult (requestCode, resultCode, data);

          if (scanningResult != null)
          {
            String scanContent = scanningResult.getContents ();
            String scanFormat = scanningResult.getFormatName ();

            formatText.setText ("FORMAT: " + scanFormat);
            contentText.setText ("CONTENT: " + scanContent);
          }
          else
          {
            Toast toast = Toast.makeText (getApplicationContext (), "No scan data received!",
                    Toast.LENGTH_SHORT);
            toast.show();
          }
        }


        if (!bUsingOffline)
        {
          addItemToFB(item_name);
        }
        else
        {
          mPoPLists.clearLists();
          readListsFromFile(mPoPLists);
          addItemToListView(newItem);
          writeListsToFile();
          mPoPLists.clearLists();
        }


        mbAddingItem = true;
      }
    }
  }


  /*************************************************************************************************
   *   Method:
   *   Description:
   *   Parameters:   N/A
   *   Returned:     N/A
   ************************************************************************************************/
  public void addItemToFB(String itemName)
  {
    if (!itemName.equals(""))
    {
      Firebase fbRef = new Firebase(Constants.FIREBASE_URL);

      HashMap<String, Object> updatedItemToAddMap = new HashMap<String, Object>();

            /* Save push() to maintain same random Id */
      Firebase newRef = mListItemsRef.push();
      String itemId = newRef.getKey();

            /* Make a POJO for the item and immediately turn it into a HashMap */
      SimpleListItem itemToAddObject = new SimpleListItem(itemName, mEncodedEmail);
      HashMap<String, Object> itemToAdd =
        (HashMap<String, Object>) new ObjectMapper().convertValue(itemToAddObject, Map.class);

      if (mbIsGrocery)
      {
                    /* Add the item to the update map*/
        updatedItemToAddMap.put("/" + Constants.FIREBASE_LOCATION_GROCERY_LIST_ITEMS + "/"
          + mListID + "/" + itemId, itemToAdd);

            /* Make the timestamp for last changed */
        HashMap<String, Object> changedTimestampMap = new HashMap<>();
        changedTimestampMap.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);

            /* Add the updated timestamp */
        updatedItemToAddMap.put("/" + Constants.FIREBASE_LOCATION_GROCERY_LISTS  +
          "/" + mListID + "/" + Constants.FIREBASE_PROPERTY_TIMESTAMP_LAST_CHANGED, changedTimestampMap);
      }
      else
      {
                    /* Add the item to the update map*/
        updatedItemToAddMap.put("/" + Constants.FIREBASE_LOCATION_KITCHEN_INVENTORY_ITEMS + "/"
          + mListID + "/" + itemId, itemToAdd);

            /* Make the timestamp for last changed */
        HashMap<String, Object> changedTimestampMap = new HashMap<>();
        changedTimestampMap.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);

            /* Add the updated timestamp */
        updatedItemToAddMap.put("/" + Constants.FIREBASE_LOCATION_KITCHEN_INVENTORY +
          "/" + mListID + "/" + Constants.FIREBASE_PROPERTY_TIMESTAMP_LAST_CHANGED, changedTimestampMap);
      }

            /* Do the update */
      fbRef.updateChildren(updatedItemToAddMap);
    }
  }


  /********************************************************************************************
   * Function name: addItemToListView
   * Description:   Adds item layout to listView as a new row and adds it to listadapter
   * Parameters:    newItem - the new ListItem being added
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
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(PoPListItemsActivity.this,
      android.R.layout.simple_spinner_item, PoPList.GroupByStrings);

    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    mGroupBySpinner.setAdapter(adapter);
    mGroupBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        PoPList list = mPoPList;
        Query testQ;
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

          mItemListView.setAdapter(mItemAdapter);
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {
        //Nothing to do if the dropdown is not selected.
      }
    });
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

  /*************************************************************************************************
   *   Method:
   *   Description:
   *   Parameters:   N/A
   *   Returned:     N/A
   ************************************************************************************************/
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

     // PoPList tempList = mPoPLists.getListByName(mPoPListName);
     // mPoPList.copyList(tempList);

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

    PoPList tempList = mPoPLists.getListByName(mPoPListName);
    tempList.copyList(mPoPList);

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


  private boolean showDeleteButton2(final int pos) {
    mPositionClicked = pos;
    ImageButton bDelete;

    View child = mItemListView.getChildAt(pos - mItemListView.getFirstVisiblePosition());
    if (child != null) {

      bDelete = (ImageButton) child.findViewById(R.id.button_remove_item);

      if (bDelete != null)
      {
        if (bDelete.getVisibility() == View.INVISIBLE)
        {
          Animation deleteAnimation =
              AnimationUtils.loadAnimation(this,
                  R.anim.slide_out_left);

          bDelete.startAnimation(deleteAnimation);
          bDelete.setVisibility(View.VISIBLE);

          slideItemView(child, SLIDE_LEFT_ITEM);
        }
      }
      return true;
    }
    return false;
  }

  private boolean hideDeleteButton2(final int pos) {
    mPositionClicked = pos;
    View child = mItemListView.getChildAt(pos - mItemListView.getFirstVisiblePosition());
    if (child != null) {

      ImageButton bDelete = (ImageButton) child.findViewById(R.id.button_remove_item);
            /*delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                }
            });*/
      if (bDelete != null)
      {
        if (bDelete.getVisibility() == View.VISIBLE)
        {
          Animation deleteAnimation =
              AnimationUtils.loadAnimation(this,
                  R.anim.slide_in_right);

          bDelete.startAnimation(deleteAnimation);

          bDelete.setVisibility(View.INVISIBLE);

          slideItemView(child, SLIDE_RIGHT_ITEM);

        }
      }
      return true;
    }
    return false;
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
      mPoPList = mPoPLists.getListByName(mPoPListName);
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
//    fm = getSupportFragmentManager();
//    DeletePoPListDFragment deleteListFragment = new DeletePoPListDFragment();
//    deleteListFragment.show(fm, "Yeah");
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


    MenuItem edit = menu.findItem(R.id.action_edit_list_name);
    MenuItem share = menu.findItem(R.id.action_share_list);
    MenuItem remove = menu.findItem(R.id.action_remove_list);

    if (bUsingOffline)
    {
      menu.removeItem(R.id.action_logout);
      menu.removeItem(R.id.action_share_list);
    }

    if (!mbCurrentUserIsOwner) //current person is looking at a list they do not own
    {
      menu.removeItem(R.id.action_remove_list);
      menu.removeItem(R.id.action_share_list);
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


    if (id == R.id.action_edit_list_name)
    {
      onMenuEditListNameClick();

      return true;
    }

    if (id == R.id.action_barcode_add_item)
    {
      onBarcodeScanClick();

      return true;
    }

    if (id == R.id.action_edit)
    {
      if (mbIsOnEdit)
      {
        mbIsOnEdit = false;
      }
      else
      {
        mbIsOnEdit = true;
      }

      onMenuEditClick();
      return true;
    }

    if (id == R.id.action_share_list)
    {
      onMenuShareList();
      return true;
    }
    if (id == R.id.action_remove_list)
    {
      onMenuDeleteList();
      return true;
    }


    return super.onOptionsItemSelected(item);
  }

  /*************************************************************************************************
   *   Method:
   *   Description:
   *   Parameters:   N/A
   *   Returned:     N/A
   ************************************************************************************************/
  private void onBarcodeScanClick()
  {
    bScannedItem = true;
    IntentIntegrator scanIntegrator = new IntentIntegrator (this);

    scanIntegrator.initiateScan();
  }

  /*************************************************************************************************
   *   Method:
   *   Description:
   *   Parameters:   N/A
   *   Returned:     N/A
   ************************************************************************************************/
  private void onMenuEditListNameClick()
  {
    mListInfoListener = new DialogListener()
    {
      @Override
      public void onFinishNewListDialog(String newListName)
      {
        if (!newListName.equals(""))
        {
          if (bUsingOffline)
          {
            //edit the list name offline - file stuff TODO
          }
          else
          {
            if (null != mListID)
            {
              editListNameFirebase(newListName);
            }
          }
        }
      }
    };

    fm = getFragmentManager();
    /*EditListNameDialog list = new EditListNameDialog();
    list.show(fm, "fs");*/
  }

  /*************************************************************************************************
   *   Method:
   *   Description:
   *   Parameters:   N/A
   *   Returned:     N/A
   ************************************************************************************************/
  private void onMenuEditClick()
  {

    if (!bUsingOffline)
    {
      int numItems = mItemListView.getAdapter().getCount();

      if (mbIsOnEdit)
      {
        for (int i = 0; i < numItems; ++i)
        {
          showDeleteButton2(i);
        }
      }
      else
      {
        for (int i = 0; i < numItems; ++i)
        {
          hideDeleteButton2(i);
        }
      }
    }
    else
    {
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
  }


  /*************************************************************************************************
   *   Method:
   *   Description:
   *   Parameters:   N/A
   *   Returned:     N/A
   ************************************************************************************************/
  public void onMenuShareList()
  {
    mListInfoListener = new DialogListener()
    {
      @Override
      public void onFinishNewListDialog(String sharedUser)
      {
        if (!sharedUser.equals(""))
        {
          if (bUsingOffline)
          {
            //edit the list name offline - file stuff TODO
          }
          else
          {
            if (null != mListID)
            {
              shareListFirebase(sharedUser);
            }
          }
        }
      }
    };

    fm = getFragmentManager();
    ShareListDialog list = new ShareListDialog();
    list.show(fm, "fs");
  }

  /*************************************************************************************************
   *   Method:
   *   Description:
   *   Parameters:   N/A
   *   Returned:     N/A
   ************************************************************************************************/
  public void shareListFirebase(String email)
  {
    final String sharedEncodedEmail = Utils.encodeEmail(email);

    final String listId = mListRef.getKey(); //the current list we are trying to share

    final Firebase shareRef = new Firebase(Constants.FIREBASE_URL_SHARED_WITH).child(listId).child(sharedEncodedEmail);
    Firebase userRef = new Firebase(Constants.FIREBASE_URL_USERS).child(sharedEncodedEmail);
    //location of the shared used (assuming they already have an account)
    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        User user = dataSnapshot.getValue(User.class);

        if (user != null) {
          shareRef.setValue(user);
        }
      }

      @Override
      public void onCancelled(FirebaseError firebaseError) {
        Log.e("POP list items",
                "Read failed: " + firebaseError.getMessage());
      }
    });
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


  /*************************************************************************************************
   *   Method:
   *   Description:
   *   Parameters:   N/A
   *   Returned:     N/A
   ************************************************************************************************/
  public void onMenuDeleteList()
  {
    if (bUsingOffline)
    {
      //deleting a list on the file...
    }
    else
    {
      AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, R.style.CustomTheme_Dialog)
          .setTitle("Delete List")
          .setMessage("Are you sure you want to delete this list?")
          .setPositiveButton("Yes", new DialogInterface.OnClickListener()
          {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
              deleteListFirebase(mbIsGrocery);
              dialog.dismiss();
            }
          })
          .setNegativeButton("No", new DialogInterface.OnClickListener()
          {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
              dialog.dismiss();
            }
          })
          .setIcon(android.R.drawable.ic_dialog_alert);

      AlertDialog alertDialog = alertDialogBuilder.create();
      alertDialog.show();
    }
  }


  /*************************************************************************************************
   *   Method:
   *   Description:
   *   Parameters:   N/A
   *   Returned:     N/A
   ************************************************************************************************/
  public void deleteListFirebase (boolean bGrocery)
  {
    HashMap<String, Object> removedList = new HashMap<>();
    Firebase ref = new Firebase(Constants.FIREBASE_URL);

    if (bGrocery)
    {
         /* Add the item to the update map*/
      removedList.put("/" + Constants.FIREBASE_LOCATION_GROCERY_LISTS + "/"
          + mListID, null);

      removedList.put("/" + Constants.FIREBASE_LOCATION_GROCERY_LIST_ITEMS + "/" + mListID, null);


      ref.updateChildren(removedList, new Firebase.CompletionListener()
      {
        @Override
        public void onComplete(FirebaseError firebaseError, Firebase firebase)
        {
          if (null != firebaseError)
          {
            Log.e("Firebase - Grocery", captureFirebaseError(firebaseError));
          }
        }
      });
    }
    else
    {
                 /* Add the item to the update map*/
      removedList.put("/" + Constants.FIREBASE_LOCATION_KITCHEN_INVENTORY + "/"
          + mListID, null);

      removedList.put("/" + Constants.FIREBASE_LOCATION_KITCHEN_INVENTORY_ITEMS + "/" + mListID, null);

      ref.updateChildren(removedList, new Firebase.CompletionListener()
      {
        @Override
        public void onComplete(FirebaseError firebaseError, Firebase firebase)
        {
          if (null != firebaseError)
          {
            Log.e("Firebase - Kitchen", captureFirebaseError(firebaseError));
          }
        }
      });
    }
  }

  /*************************************************************************************************
   *   Method:
   *   Description:
   *   Parameters:   N/A
   *   Returned:     N/A
   ************************************************************************************************/
  public void editListNameFirebase(String newListName)
  {
    if (!newListName.equals(mSimpleList.getmListName()))
    {
      HashMap<String, Object> updatedInfo = new HashMap<>();
      updatedInfo.put(Constants.FIREBASE_PROPERTY_LIST_NAME, newListName);

      HashMap<String, Object> updatedTimeStamp = new HashMap<>();
      updatedTimeStamp.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);

      updatedInfo.put(Constants.FIREBASE_PROPERTY_TIMESTAMP_LAST_CHANGED, updatedTimeStamp);

      mListRef.updateChildren(updatedInfo);
    }
  }


}
