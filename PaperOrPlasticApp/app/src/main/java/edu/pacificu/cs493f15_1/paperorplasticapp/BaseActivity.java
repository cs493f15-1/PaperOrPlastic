package edu.pacificu.cs493f15_1.paperorplasticapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * This class is to be used as a base class for all activities in PoP.
 * It will (hopefully) implement some GoogleApiClient callbacks to enable "Logout" in all activities
 * and also defines some variables that are being shared across all activities
 *
 *
 * Created by alco8653 on 2/27/2016.
 */
public abstract class BaseActivity extends AppCompatActivity implements
  GoogleApiClient.OnConnectionFailedListener
{
  protected GoogleApiClient mGoogleApiClient;
  protected String mProvider, mEncodedEmail;
  protected Firebase mFirebaseRef;
  protected Firebase.AuthStateListener mAuthListener;


  @Override
  protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);

    /* Setting up the Google API object to allow Google logins */
    GoogleSignInOptions GSO = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
      .requestEmail().build();

    /**
     * Building our GoogleApiClient so that is has access to the Google Sign-In API and
     * the options specified by the GoogleSignInOptions GSO from above
     */
    mGoogleApiClient = new GoogleApiClient.Builder(this)
      .enableAutoManage(this /*Fragment Activity*/, this /* OnConnectionFailedListener*/)
      .addApi(Auth.GOOGLE_SIGN_IN_API, GSO)
      .build();

  }


  @Override
  public void onDestroy() {
    super.onDestroy();
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
          /* Inflate the menu; this adds items to the action bar if it is present. */
    //TODO: getMenuInflater().inflate(R.menu.menu_base, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == android.R.id.home) {
      super.onBackPressed();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  protected void initializeBackground(LinearLayout linearLayout)
  {

    /**
     * Set different background image for landscape and portrait layouts?
     * TODO: possibly always be portrait for simplicity
     */
    linearLayout.setBackgroundResource(R.drawable.grocerybackportrait2);
  }

  @Override
  public void onConnectionFailed(ConnectionResult connectionResult) {

  }

}
