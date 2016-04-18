package edu.pacificu.cs493f15_1.paperorplasticapp.popList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import edu.pacificu.cs493f15_1.utils.Utils;
import edu.pacificu.cs493f15_1.paperorplasticapp.BaseActivity;
import edu.pacificu.cs493f15_1.paperorplasticapp.R;
import edu.pacificu.cs493f15_1.paperorplasticjava.SimpleList;
import edu.pacificu.cs493f15_1.paperorplasticjava.SimpleListItem;
import edu.pacificu.cs493f15_1.utils.Constants;


/********************************************************************************************
 * Class name:
 * Description:
 * Parameters:
 * Returns:
 *
 * Created by alco8653 on 4/3/2016.
 ******************************************************************************************/
public class SimpleListDetailsActivity extends BaseActivity
{
  private static final String LOG_TAG = SimpleListDetailsActivity.class.getSimpleName();
  private Firebase mSimpleListRef;
  private SimpleListItemAdapter mSimpleListItemAdapter;
  private ListView mListView;
  private String mListId;

  private boolean mbGrocery;
  private boolean mbCurrentUserIsOwner = false;
  private SimpleList mSimpleList;
  private ValueEventListener mSimpleListRefListener;



  /********************************************************************************************
   * Function name:
   * Description:
   * Parameters:
   * Returns:
   ******************************************************************************************/
  @Override
  protected void onCreate(final Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
  }

  /********************************************************************************************
   * Function name:
   * Description:
   * Parameters:
   * Returns:
   ******************************************************************************************/
  protected void ListOnCreate(final Bundle savedInstanceState, final boolean grocery)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_simple_list_details);

        /* Get the push ID from the extra passed by ShoppingListFragment */
    Intent intent = this.getIntent();
    mListId = intent.getStringExtra(Constants.KEY_LIST_ID);
    if (mListId == null) {
            /* No point in continuing without a valid ID. */
      finish();
      return;
    }
    mbGrocery = grocery;


    /**
     * Create Firebase references
     */
    Firebase listItemsRef;
    if (mbGrocery)
    {
      mSimpleListRef = new Firebase(Constants.FIREBASE_URL_GROCERY_LISTS).child(mListId);
      listItemsRef = new Firebase(Constants.FIREBASE_URL_GROCERY_LIST_ITEMS).child(mListId);
    }
    else
    {
      mSimpleListRef = new Firebase(Constants.FIREBASE_URL_KITCHEN_INVENTORY).child(mListId);
      listItemsRef = new Firebase(Constants.FIREBASE_URL_KITCHEN_INVENTORY_ITEMS).child(mListId);
    }



    /**
     * Link layout elements from XML and setup the toolbar?
     */
    initializeScreen();


    /**
     * Setup the adapter
     */


    mSimpleListItemAdapter = new SimpleListItemAdapter(this, SimpleListItem.class,
      R.layout.single_active_list_item, listItemsRef, mListId, mEncodedEmail, mbGrocery);
        /* Create ActiveListItemAdapter and set to listView */
    mListView.setAdapter(mSimpleListItemAdapter);


    /**
     * Add ValueEventListeners to Firebase references
     * to control get data and control behavior and visibility of elements
     */


    /**
     * Save the most recent version of current shopping list into mShoppingList instance
     * variable an update the UI to match the current list.
     */
    mSimpleListRefListener = mSimpleListRef.addValueEventListener(new ValueEventListener()
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
        //invalidateOptionsMenu();
        //setTitle(simpleList.getmListName());

      }

      @Override
      public void onCancelled(FirebaseError firebaseError)
      {
        Log.e(LOG_TAG, "FAILED SOMEHOW AH" + firebaseError.getMessage());
      }
    });

    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id)
      {
        //TODO the things that happen when we click on the item
      }
    });

  }
  /********************************************************************************************
   * Function name:
   * Description:
   * Parameters:
   * Returns:
   ******************************************************************************************/
  private void initializeScreen() {
    mListView = (ListView) findViewById(R.id.listItemView);
//    Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
//        /* Common toolbar setup */
//    setSupportActionBar(toolbar);
//        /* Add back button to the action bar */
//    if (getSupportActionBar() != null) {
//      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//    }
//        /* Inflate the footer, set root layout to null*/
//    View footer = getLayoutInflater().inflate(R.layout.footer_empty, null);
//    mListView.addFooterView(footer);
  }


  @Override
  public void onDestroy() {
    super.onDestroy();
    mSimpleListItemAdapter.cleanup();
    mSimpleListRef.removeEventListener(mSimpleListRefListener);
  }


}


