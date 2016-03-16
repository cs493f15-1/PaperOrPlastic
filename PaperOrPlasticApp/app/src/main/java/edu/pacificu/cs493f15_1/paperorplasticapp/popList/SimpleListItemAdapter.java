package edu.pacificu.cs493f15_1.paperorplasticapp.popList;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.client.ServerValue;
import com.firebase.ui.FirebaseListAdapter;

import java.util.HashMap;

import edu.pacificu.cs493f15_1.paperorplasticapp.R;
import edu.pacificu.cs493f15_1.paperorplasticjava.SimpleList;
import edu.pacificu.cs493f15_1.paperorplasticjava.SimpleListItem;
import edu.pacificu.cs493f15_1.utils.Constants;

/**
 * Created by alco8653 on 3/16/2016.
 */
public class SimpleListItemAdapter extends FirebaseListAdapter<SimpleListItem>
{
  private SimpleList mList;
  private String mListId, mEncodedEmail;

  public SimpleListItemAdapter(Activity activity, Class<SimpleListItem> modelClass, int modelLayout,
                               Query ref, String listId, String encodedEmail)
  {
    super(activity, modelClass, modelLayout, ref);
    this.mActivity = activity;
    this.mListId = listId;
    this.mEncodedEmail = encodedEmail;
  }


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

    String owner = item.getmOwner();

    textViewItemName.setText(item.getmItemName());

    //if we want to delete an item, how do we update the view todo
    //set a click listener for when the user clicks the X in the PoPListActivity?
  }

  private void removeItem (String itemId)
  {
    Firebase firebaseRef = new Firebase(Constants.FIREBASE_URL);

        /* Make a map for the removal */
    HashMap<String, Object> updatedRemoveItemMap = new HashMap<String, Object>();

        /* Remove the item by passing null */
    updatedRemoveItemMap.put("/" + Constants.FIREBASE_LOCATION_SHOPPING_LIST_ITEMS + "/"
      + mListId + "/" + itemId, null);

        /* Make the timestamp for last changed */
    HashMap<String, Object> changedTimestampMap = new HashMap<>();
    changedTimestampMap.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);

        /* Add the updated timestamp */
    updatedRemoveItemMap.put("/" + Constants.FIREBASE_LOCATION_ACTIVE_LISTS +
      "/" + mListId + "/" + Constants.FIREBASE_PROPERTY_TIMESTAMP_LAST_CHANGED, changedTimestampMap);

        /* Do the update */
    firebaseRef.updateChildren(updatedRemoveItemMap);
  }


}
