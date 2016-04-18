package edu.pacificu.cs493f15_1.paperorplasticapp.popList;

import android.app.Activity;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;

import edu.pacificu.cs493f15_1.paperorplasticapp.R;
import edu.pacificu.cs493f15_1.paperorplasticjava.SimpleList;
import edu.pacificu.cs493f15_1.paperorplasticjava.User;
import edu.pacificu.cs493f15_1.utils.Constants;

/**
 * Created by alco8653 on 3/15/2016.
 */
public class SimpleListAdapter extends FirebaseListAdapter<SimpleList>
{
  private String mEncodedEmail;

  /**
   * Public constructor that initializes private instance variables when adapter is created
   */
  public SimpleListAdapter(Activity activity, Class<SimpleList> modelClass, int modelLayout,
                           Query ref, String encodedEmail) {
    super(activity, modelClass, modelLayout, ref);
    this.mEncodedEmail = encodedEmail;
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

    TextView textViewListName = (TextView) view.findViewById(R.id.text_view_list_name);
    Typeface laneUpperFont = Typeface.createFromAsset(mActivity.getAssets(), "fonts/laneWUnderLine.ttf");
    Typeface laneNarrowFont = Typeface.createFromAsset(mActivity.getAssets(), "fonts/LANENAR.ttf");
    textViewListName.setTypeface(laneNarrowFont);

    final TextView textViewCreatedByUser = (TextView) view.findViewById(R.id.text_view_created_by_user);
    TextView textViewTime = (TextView) view.findViewById(R.id.text_view_edit_time);

    String ownerEmail = list.getmOwner();


    Log.e ("OWNEREMAIL", "owner of the list " + ownerEmail + ". Current user:" + mEncodedEmail);


        /* Set the list name and owner */
    textViewListName.setText(list.getmListName());

    if (null != ownerEmail)
    {
      if (ownerEmail.equals(mEncodedEmail))
      {
        textViewCreatedByUser.setText("You");
      }
      else
      {
        Firebase userRef = new Firebase(Constants.FIREBASE_URL_USERS).child(ownerEmail);
                /* Get the user's name */
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
            User user = dataSnapshot.getValue(User.class);

            if (user != null)
            {
              textViewCreatedByUser.setText(user.getmName());
            }
          }

          @Override
          public void onCancelled(FirebaseError firebaseError) {
            Log.e(mActivity.getClass().getSimpleName(),
              "Read failed: " + firebaseError.getMessage());
          }
        });
      }
    }
  }
}
