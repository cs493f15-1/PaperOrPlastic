package edu.pacificu.cs493f15_1.paperorplasticapp.popList;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;

import edu.pacificu.cs493f15_1.paperorplasticapp.R;
import edu.pacificu.cs493f15_1.paperorplasticjava.SimpleList;

/**
 * Created by alco8653 on 4/4/2016.
 */
public class SimpleListAdapterTab extends FirebaseListAdapter<SimpleList>
{
  /**
   * Public constructor that initializes private instance variables when adapter is created
   */
  public SimpleListAdapterTab(Activity activity, Class<SimpleList> modelClass, int modelLayout, Query ref) {
    super(activity, modelClass, modelLayout, ref);
    this.mActivity = activity;
  }


  /**
   * Protected method that populates the view attached to the adapter
   * with items inflated from single_active_list.xml
   * populateView also handles data changes and updates the listView accordingly
   */
  @Override
  protected void populateView(View view, SimpleList list) {

    /**
     * Grab the needed Textivews and strings
     */


    //TextView txtTabInfo = (TextView) view.

    //TextView textViewListName = (TextView) view.findViewById(R.id.text_view_list_name);
    //TextView textViewCreatedByUser = (TextView) view.findViewById(R.id.text_view_created_by_user);


        /* Set the list name and owner */
    //textViewListName.setText(list.getmListName());
    //textViewCreatedByUser.setText(list.getmOwner());
  }
}
