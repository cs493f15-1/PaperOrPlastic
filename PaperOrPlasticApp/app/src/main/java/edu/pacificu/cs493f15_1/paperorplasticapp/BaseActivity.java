package edu.pacificu.cs493f15_1.paperorplasticapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import edu.pacificu.cs493f15_1.paperorplasticapp.signIn.CreateAccountActivity;
import edu.pacificu.cs493f15_1.paperorplasticapp.signIn.MainSignInActivity;
import edu.pacificu.cs493f15_1.paperorplasticapp.signIn.PasswordRecoveryActivity;
import edu.pacificu.cs493f15_1.utils.Constants;

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
  //Google variables
  protected GoogleApiClient mGoogleApiClient;

  //Strings (for authorization)
  protected String mProvider, mEncodedEmail;

  //Firebase
  protected Firebase mFirebaseRef;
  protected Firebase.AuthStateListener mAuthListener;

  protected boolean bUsingOffline = true;

  /*************************************************************************************************
   *   Method:
   *   Description:
   *   Parameters:  N/A
   *   Returned:    N/A
   ************************************************************************************************/
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    /* Setting up the Google API object to allow Google logins */
    GoogleSignInOptions GSO = new GoogleSignInOptions
      .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
      .requestEmail().build();


    /**
     * Building our GoogleApiClient so that is has access to the Google Sign-In API and
     * the options specified by the GoogleSignInOptions GSO from above
     */
    mGoogleApiClient = new GoogleApiClient.Builder(this)
      .enableAutoManage(this /*Fragment Activity*/, this /* OnConnectionFailedListener*/)
      .addApi(Auth.GOOGLE_SIGN_IN_API, GSO)
      .build();

    /**
     * Getting mProvider and mEncodedEmail from SharedPreferences
     */
    final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(BaseActivity.this);
        /* Get mEncodedEmail and mProvider from SharedPreferences, use null as default value */
    mEncodedEmail = sp.getString(Constants.KEY_ENCODED_EMAIL, null);
    mProvider = sp.getString(Constants.KEY_PROVIDER, null);

    if (null == mEncodedEmail)
    {
      bUsingOffline = true;
    }
    else
    {
      bUsingOffline = false;
    }

    if (!((this instanceof MainSignInActivity) || (this instanceof CreateAccountActivity) ||
      (this instanceof PasswordRecoveryActivity) || bUsingOffline))
    {
      mFirebaseRef = new Firebase(Constants.FIREBASE_URL);
      mAuthListener = new Firebase.AuthStateListener()
      {
        @Override
        public void onAuthStateChanged(AuthData authData)
        {
                     /* The user has been logged out */
          if (authData == null)
          {
                        /* Clear out shared preferences */
            SharedPreferences.Editor spe = sp.edit();
            spe.putString(Constants.KEY_ENCODED_EMAIL, null);
            spe.putString(Constants.KEY_PROVIDER, null);

            spe.commit();

            takeUserToSignInScreenOnUnAuth();
          }
        }
      };
      mFirebaseRef.addAuthStateListener(mAuthListener);
    }

    invalidateOptionsMenu();
  }


  /*************************************************************************************************
   *   Method:
   *   Description:
   *   Parameters:  N/A
   *   Returned:    N/A
   ************************************************************************************************/
  @Override
  public void onDestroy()
  {
    super.onDestroy();
            /* Cleanup the AuthStateListener */
    if (!((this instanceof MainSignInActivity) || (this instanceof CreateAccountActivity) ||
      (this instanceof PasswordRecoveryActivity) || bUsingOffline)) {
      mFirebaseRef.removeAuthStateListener(mAuthListener);
    }
  }

  /*************************************************************************************************
   *   Method:
   *   Description:
   *   Parameters:  N/A
   *   Returned:    N/A
   ************************************************************************************************/
  @Override
  public void onSaveInstanceState(Bundle outState)
  {
    super.onSaveInstanceState(outState);
  }

  /*************************************************************************************************
   *   Method:
   *   Description:
   *   Parameters:  N/A
   *   Returned:    N/A
   ************************************************************************************************/
  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
          /* Inflate the menu; this adds items to the action bar if it is present. */
    getMenuInflater().inflate(R.menu.menu_base, menu);
    if (bUsingOffline)
    {
      menu.removeItem(R.id.action_logout);
    }
    return true;
  }

  /*************************************************************************************************
   *   Method:
   *   Description:
   *   Parameters:  N/A
   *   Returned:    N/A
   ************************************************************************************************/
  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    int id = item.getItemId();

    if (id == android.R.id.home)
    {
      super.onBackPressed();
      return true;
    }

    if (id == R.id.action_logout) {
      logout();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  /*************************************************************************************************
   *   Method:
   *   Description:
   *   Parameters:  N/A
   *   Returned:    N/A
   ************************************************************************************************/
  protected void initializeBackground(LinearLayout linearLayout)
  {
    /**
     * Set different background image for landscape and portrait layouts?
     */
    linearLayout.setBackgroundResource(R.drawable.grocerybackportraitsmall);
  }

  /*************************************************************************************************
   *   Method:      logout
   *   Description: Logs out the user from their current session and starts MainSignInActivity.
   *                Also disconnects the mGoogleApiClient if connected and provider is Google
   *   Parameters:  N/A
   *   Returned:    N/A
   ************************************************************************************************/
  protected void logout()
  {
        /* Logout if mProvider is not null */
    if (mProvider != null)
    {
      //unauthorize the firebase user
      mFirebaseRef.unauth();
      bUsingOffline = true;

      if (mProvider.equals(Constants.GOOGLE_PROVIDER))
      {
                /* Logout from Google+ */
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
          new ResultCallback<Status>()
          {
            @Override
            public void onResult(Status status)
            {
              //nothing
            }
          });
      }
    }
  }

/***************************************************************************************************
*   Method:
*   Description:
*   Parameters:  N/A
*   Returned:    N/A
***************************************************************************************************/
  protected void takeUserToSignInScreenOnUnAuth()
  {
        /* Move user to LoginActivity, and remove the backstack */
    Intent intent = new Intent(BaseActivity.this, MainSignInActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);
    finish();
  }

  /*************************************************************************************************
   *   Method:      captureFirebaseError
   *   Description: upon receiving a firebase error, interpret this error and output to the system
   *                 the type of error.
   *   Parameters:  N/A
   *   Returned:    N/A
   ************************************************************************************************/

  public String captureFirebaseError (FirebaseError error)
  {
    String errorMessage = "Firebase Error.";
    if (null != error)
    {
      switch (error.getCode())
      {
        case FirebaseError.EMAIL_TAKEN:
          errorMessage = "Error: Email taken.";
          break;
        case FirebaseError.EXPIRED_TOKEN:
          errorMessage = "Error: Expired token.";
          break;
        case FirebaseError.INVALID_EMAIL:
          errorMessage = "Error: Invalid email.";
          break;
        case FirebaseError.INVALID_PASSWORD:
          errorMessage = "Error: Invalid password.";
          break;
        case FirebaseError.INVALID_TOKEN:
          errorMessage = "Error: Invalid token.";
          break;
        case FirebaseError.INVALID_CREDENTIALS:
          errorMessage = "Error: Invalid credentials.";
          break;
        case FirebaseError.PERMISSION_DENIED:
          errorMessage = "Error: Permission denied.";
          break;
        case FirebaseError.OPERATION_FAILED:
          errorMessage = "Error: Operation failed.";
          break;
        default:
          errorMessage = "Firebase error";
         // System.out.println("Firebase error");
          System.out.println("Firebase error");
          break;

      }
      System.out.println(errorMessage);
    }

    return errorMessage;
  }

  /*************************************************************************************************
   *   Method:
   *   Description:
   *   Parameters:  N/A
   *   Returned:    N/A
   ************************************************************************************************/
  @Override
  public void onConnectionFailed(ConnectionResult connectionResult)
  {

  }

  /***************************************************************************************************
   *   Method:
   *   Description:
   *   Parameters:  N/A
   *   Returned:    N/A
   ***************************************************************************************************/
  public void messageDialog(String title, String message)
  {
    final AlertDialog theDialog = new AlertDialog.Builder (this)
        .setMessage(message)
        .setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface dialog, int ok)
          { //user clicked ok
          }
        }).show();

    Handler handler = new Handler();
    handler.postDelayed(new Runnable()
    {
      public void run()
      {
        theDialog.dismiss();
      }
    }, 3000);

  }

}
