package edu.pacificu.cs493f15_1.paperorplasticapp.popList;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.client.Firebase;

import edu.pacificu.cs493f15_1.paperorplasticapp.R;
import edu.pacificu.cs493f15_1.paperorplasticapp.nutrition.NutritionActivity;
import edu.pacificu.cs493f15_1.paperorplasticjava.SimpleList;
import edu.pacificu.cs493f15_1.utils.Constants;

/**
 * Created by alco8653 on 3/31/2016.
 */
public class ListsFragment extends Fragment
{
  private ListView mListView;
  private SimpleListAdapter mListAdapter;

  public ListsFragment()
  {}

  public static ListsFragment newInstance()
  {
    ListsFragment fragment = new ListsFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
    }
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState)
  {
    /**
     * Initialize UI elements
     */
    View rootView = inflater.inflate(R.layout.activity_kitchen_inventory, container, false);
    initializeScreen(rootView);

    /**
     * Create Firebase references
     */
    Firebase activeListsRef = new Firebase(Constants.FIREBASE_LOCATION_KITCHEN_INVENTORY);

    /**
     *  Create the adapter, giving it the activity, model class, layout for each row in
     *  the list and finally, a reference to the Firebase location with the list data
     */
    mListAdapter = new SimpleListAdapter(getActivity(), SimpleList.class,
      R.layout.single_active_list, activeListsRef, "EMAIL");

    /**
     * Set the adapter to the mListView
     */
    mListView.setAdapter(mListAdapter);


    /**
     * Set interactive bits, such as click events and adapters
     */
    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id)
      {
        SimpleList selectedList = mListAdapter.getItem(position);
        if (selectedList != null)
        {
          Intent intent = new Intent(getActivity(), NutritionActivity.class);

         /* Get the list ID using the adapter's get ref method to get the Firebase
          * ref and then grab the key.
          */
          String listId = mListAdapter.getRef(position).getKey();
          intent.putExtra(Constants.KEY_LIST_ID, listId);
        /* Starts an active showing the details for the selected list */
          startActivity(intent);
        }

      }
    });

    return rootView;  }


  /**
   * Cleanup the adapter when activity is destroyed.
   */
  @Override
  public void onDestroy() {
    super.onDestroy();
    mListAdapter.cleanup();
  }


  /**
   * Link list view from XML
   */
  private void initializeScreen(View rootView) {
    mListView = (ListView) rootView.findViewById(R.id.listView);
  }
}
