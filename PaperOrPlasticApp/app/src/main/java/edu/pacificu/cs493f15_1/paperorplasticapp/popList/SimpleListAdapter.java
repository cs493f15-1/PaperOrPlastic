package edu.pacificu.cs493f15_1.paperorplasticapp.popList;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;

import edu.pacificu.cs493f15_1.paperorplasticapp.R;
import edu.pacificu.cs493f15_1.paperorplasticjava.SimpleList;

/**
 * Created by alco8653 on 3/15/2016.
 */
public class SimpleListAdapter extends FirebaseListAdapter<SimpleList>
{
  /**
   * Public constructor that initializes private instance variables when adapter is created
   */
  public SimpleListAdapter(Activity activity, Class<SimpleList> modelClass, int modelLayout, Query ref) {
    super(activity, modelClass, modelLayout, ref);
    this.mActivity = activity;
  }


  /**
   * Protected method that populates the view attached to the adapter (list_view_active_lists)
   * with items inflated from single_active_list.xml
   * populateView also handles data changes and updates the listView accordingly
   */
  @Override
  protected void populateView(View view, SimpleList list) {

    /**
     * Grab the needed Textivews and strings
     */
    //todo is the below right?
    TextView textViewListName = (TextView) view.findViewById(R.id.text_view_list_name);
    //TextView textViewCreatedByUser = (TextView) view.findViewById(R.id.text_view_created_by_user);


        /* Set the list name and owner */
    textViewListName.setText(list.getmListName());
    //textViewCreatedByUser.setText(list.getmOwner());
  }
}
