package edu.pacificu.cs493f15_1.paperorplasticapp.popList;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ServerValue;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;

import java.util.HashMap;

import edu.pacificu.cs493f15_1.utils.Constants;
import edu.pacificu.cs493f15_1.paperorplasticapp.R;
import edu.pacificu.cs493f15_1.paperorplasticjava.SimpleList;
import edu.pacificu.cs493f15_1.paperorplasticjava.SimpleListItem;

import edu.pacificu.cs493f15_1.paperorplasticjava.User;

/**
 * Created by alco8653 on 3/16/2016.
 */
public class SimpleListItemAdapter extends FirebaseListAdapter<SimpleListItem>
{
  private SimpleList mList;
  private String mListId, mEncodedEmail;
  private boolean bIsGrocery;

  public SimpleListItemAdapter(Activity activity, Class<SimpleListItem> modelClass, int modelLayout,
                               Query ref, String listId, String encodedEmail, boolean grocery)
  {
    super(activity, modelClass, modelLayout, ref);
    this.mActivity = activity;
    this.mListId = listId;
    this.mEncodedEmail = encodedEmail;
    this.bIsGrocery = grocery;
  }

  /*************************************************************************************************
   *   Method:
   *   Description:
   *   Parameters:   N/A
   *   Returned:     N/A
   ************************************************************************************************/
  public void setList (SimpleList list)
  {
    this.mList = list;
    this.notifyDataSetChanged();
  }

  /**
   * Protected method that populates the view attached to the adapter (list_view_friends_autocomplete)
   * with items inflated from single_active_list_item.xml
   * populateView also handles data changes and updates the listView accordingly
   */
  @Override
  protected void populateView(View view, final SimpleListItem item, int position)
  {
    TextView textViewItemName = (TextView) view.findViewById(R.id.text_view_active_list_item_name);
    final TextView textViewBoughtByUser = (TextView) view.findViewById(R.id.text_view_bought_by_user);
    TextView textViewBoughtBy = (TextView) view.findViewById(R.id.text_view_bought_by);
    ImageButton deleteButton = (ImageButton) view.findViewById(R.id.button_remove_item);

    String owner = item.getmOwner();

    textViewItemName.setText(item.getmItemName());

    setViewOnItemBought(owner, textViewBoughtByUser, textViewBoughtBy, deleteButton,
        textViewItemName, item);

    final String itemToRemoveId = this.getRef(position).getKey();

    //if we want to delete an item, how do we update the view todo
    //set a click listener for when the user clicks the X in the PoPListActivity?

    deleteButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity, R.style.CustomTheme_Dialog)
                .setTitle("Remove Item")
                .setMessage("Are you sure you want to remove this item?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialog, int which) {

                    removeItem(itemToRemoveId);
                       /* Dismiss the dialog */
                    dialog.dismiss();
                  }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialog, int which) {
                       /* Dismiss the dialog */
                    dialog.dismiss();
                  }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
      }

    });

  }

  /*************************************************************************************************
   *   Method:
   *   Description:
   *   Parameters:   N/A
   *   Returned:     N/A
   ************************************************************************************************/
  private void removeItem (String itemId)
  {
    Firebase firebaseRef = new Firebase(Constants.FIREBASE_URL);

        /* Make a map for the removal */
    HashMap<String, Object> updatedRemoveItemMap = new HashMap<String, Object>();

        /* Remove the item by passing null */
    if (bIsGrocery)
    {
      updatedRemoveItemMap.put("/" + Constants.FIREBASE_LOCATION_GROCERY_LIST_ITEMS + "/"
        + mListId + "/" + itemId, null);
    }
    else
    {
      updatedRemoveItemMap.put("/" + Constants.FIREBASE_LOCATION_KITCHEN_INVENTORY_ITEMS + "/"
        + mListId + "/" + itemId, null);
    }

        /* Make the timestamp for last changed */
    HashMap<String, Object> changedTimestampMap = new HashMap<>();
    changedTimestampMap.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);


    if (bIsGrocery)
    {
              /* Add the updated timestamp */
      updatedRemoveItemMap.put("/" + Constants.FIREBASE_LOCATION_GROCERY_LISTS +
        "/" + mListId + "/" + Constants.FIREBASE_PROPERTY_TIMESTAMP_LAST_CHANGED, changedTimestampMap);
    }
    else
    {
                    /* Add the updated timestamp */
      updatedRemoveItemMap.put("/" + Constants.FIREBASE_LOCATION_KITCHEN_INVENTORY +
        "/" + mListId + "/" + Constants.FIREBASE_PROPERTY_TIMESTAMP_LAST_CHANGED, changedTimestampMap);
    }


        /* Do the update */
    firebaseRef.updateChildren(updatedRemoveItemMap);
  }


  /*************************************************************************************************
   *   Method:
   *   Description:
   *   Parameters:   N/A
   *   Returned:     N/A
   ************************************************************************************************/
  private void setViewOnItemBought(String owner, final TextView textViewBoughtByUser,
                                                   TextView textViewBoughtBy, ImageButton buttonRemoveItem,
                                                   TextView textViewItemName, SimpleListItem item) {
    /**
     * If selected item is bought
     * Set "Bought by" text to "You" if current user is owner of the list
     * Set "Bought by" text to userName if current user is NOT owner of the list
     * Set the remove item button invisible if current user is NOT list or item owner
     */
    if (item.isbBought() && item.getmBoughtBy() != null) {

      textViewBoughtBy.setVisibility(View.VISIBLE);
      textViewBoughtByUser.setVisibility(View.VISIBLE);
      buttonRemoveItem.setVisibility(View.INVISIBLE);

            /* Add a strike-through */
      textViewItemName.setPaintFlags(textViewItemName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

      if (item.getmBoughtBy().equals(mEncodedEmail)) {
        textViewBoughtByUser.setText("You");
      }
      else
      {

        Firebase boughtByUserRef = new Firebase(Constants.FIREBASE_URL_USERS).child(item.getmBoughtBy());
                /* Get the item's owner's name; use a SingleValueEvent listener for memory efficiency */
        boughtByUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
            User user = dataSnapshot.getValue(User.class);
            if (user != null) {
              textViewBoughtByUser.setText(user.getmName());
            }
          }

          @Override
          public void onCancelled(FirebaseError firebaseError) {
            Log.e(mActivity.getClass().getSimpleName(),
               "the read failed. " +
                    firebaseError.getMessage());
          }
        });
      }
    } else {
      /**
       * If selected item is NOT bought
       * Set "Bought by" text to be empty and invisible
       * Set the remove item button visible if current user is owner of the list or selected item
       */

            /* Remove the strike-through */
      textViewItemName.setPaintFlags(textViewItemName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));

      textViewBoughtBy.setVisibility(View.INVISIBLE);
      textViewBoughtByUser.setVisibility(View.INVISIBLE);
      textViewBoughtByUser.setText("");
      /**
       * If you are the owner of the item or the owner of the list, then the remove icon
       * is visible.
       */
      if (owner.equals(mEncodedEmail) || (mList != null && mList.getmOwner().equals(mEncodedEmail))) {
        buttonRemoveItem.setVisibility(View.INVISIBLE);
      } else {
        buttonRemoveItem.setVisibility(View.GONE);
      }
    }
  }


}
