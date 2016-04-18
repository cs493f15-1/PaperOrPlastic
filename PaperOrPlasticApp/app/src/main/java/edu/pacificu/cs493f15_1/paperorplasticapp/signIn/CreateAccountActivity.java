package edu.pacificu.cs493f15_1.paperorplasticapp.signIn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ServerValue;
import com.firebase.client.ValueEventListener;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import edu.pacificu.cs493f15_1.paperorplasticapp.BaseActivity;
import edu.pacificu.cs493f15_1.paperorplasticapp.R;
import edu.pacificu.cs493f15_1.paperorplasticjava.User;
import edu.pacificu.cs493f15_1.utils.Constants;
import edu.pacificu.cs493f15_1.utils.Utils;

/**
 * Created by alco8653 on 2/28/2016.
 */
public class CreateAccountActivity extends BaseActivity
{
  private static final String LOG_TAG = CreateAccountActivity.class.getSimpleName();

  private ProgressDialog mProgressDialog;

  //firebase
  private Firebase mFirebaseRef;

  //edit text fields
  private EditText mEditTextUsernameCreate, mEditTextEmailCreate;

  //strings
  private String mUserName, mUserEmail, mPassword;
  private SecureRandom mRandom = new SecureRandom();


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_account);

    mFirebaseRef = new Firebase(Constants.FIREBASE_URL);

    initializeActivity();
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    return true;
  }

/***************************************************************************************************
*   Method:
*   Description:
*   Parameters:  N/A
*   Returned:    N/A
***************************************************************************************************/
  public void initializeActivity()
  {
    mEditTextUsernameCreate = (EditText) findViewById(R.id.editTextName);
    mEditTextEmailCreate = (EditText) findViewById(R.id.editTextNewEmail);
    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear_layout_caa);
    initializeBackground(linearLayout);

    mProgressDialog = new ProgressDialog(this);
    mProgressDialog.setTitle("Loading...");
    mProgressDialog.setMessage("Please check your inbox for your password");
    mProgressDialog.setCancelable(false);
  }



/***************************************************************************************************
*   Method:
*   Description: Go back to the MainSignInActivity when user taps on "Sign in" textView (the small button
*                 below the create account button)
*   Parameters:  N/A
*   Returned:    N/A
***************************************************************************************************/
  public void onSignInPressed(View view) {
    Intent intent = new Intent(CreateAccountActivity.this, MainSignInActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);
    finish();
  }

/***************************************************************************************************
*   Method:
*   Description:
*   Parameters:  N/A
*   Returned:    N/A
***************************************************************************************************/
  public void onCreateAccountPressed(View view)
  {
    mUserName = mEditTextUsernameCreate.getText().toString();
    mUserEmail = mEditTextEmailCreate.getText().toString();
    mPassword = new BigInteger(130, mRandom).toString(32); //creating a new random password for the user

    boolean validEmail = isEmailValid(mUserEmail);
    boolean validName = isUserNameValid(mUserName);

    if (!validEmail || !validName)
    {
      return;
    }

    mProgressDialog.show();


    mFirebaseRef.createUser(mUserEmail, mPassword, new Firebase.ValueResultHandler<Map<String, Object>>()
    {
      @Override
      public void onSuccess(Map<String, Object> result)
      {
        //after create sign-in
        mFirebaseRef.resetPassword(mUserEmail, new Firebase.ResultHandler()
        {
          @Override
          public void onSuccess()
          {
            mProgressDialog.dismiss();

            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(CreateAccountActivity.this);
            SharedPreferences.Editor spe = sp.edit();

            /**
             * Save name and email to sharedPreferences to create User database record
             * when the registered user will sign in for the first time
             */
            spe.putString(Constants.KEY_SIGNUP_EMAIL, mUserEmail).apply();

            createUserInFirebase();

            //setMfCurrentUser(authData, email, password);

            messageDialog("Create Account", "Account has been created.");

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_APP_EMAIL);
            try {
              startActivity(intent);
              finish();
            } catch (android.content.ActivityNotFoundException ex) {
                                    /* User does not have any app to handle email */
            }
          }

          @Override
          public void onError(FirebaseError firebaseError)
          {
            mProgressDialog.dismiss();
            messageDialog("Unable to Sign-In", "Invalid password or email.");
          }

        });
      }

      @Override
      public void onError(FirebaseError firebaseError)
      {
                /* Error occurred, log the error and dismiss the progress dialog */
        Log.d(LOG_TAG, "Error occurred: " + firebaseError);
        mProgressDialog.dismiss();
                /* Display the appropriate error message */
        if (firebaseError.getCode() == FirebaseError.EMAIL_TAKEN)
        {
          mEditTextEmailCreate.setError("The email is already taken. ");
        }

      }
    });

  }


/***************************************************************************************************
*   Method:
*   Description:
*   Parameters:  N/A
*   Returned:    N/A
***************************************************************************************************/
  private void createUserInFirebase() {
    final String encodedEmail = Utils.encodeEmail(mUserEmail);
    final Firebase userLocation = new Firebase(Constants.FIREBASE_URL_USERS).child(encodedEmail);
    /**
     * See if there is already a user (for example, if they already logged in with an associated
     * Google account.
     */
    userLocation.addListenerForSingleValueEvent(new ValueEventListener()
    {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot)
      {
            /* If there is no user, make one */
        if (dataSnapshot.getValue() == null)
        {
             /* Set raw version of date to the ServerValue.TIMESTAMP value and save into dateCreatedMap */
          HashMap<String, Object> timestampJoined = new HashMap<>();
          timestampJoined.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);

          User newUser = new User(mUserName, encodedEmail, timestampJoined);
          userLocation.setValue(newUser);
        }
      }

      @Override
      public void onCancelled(FirebaseError firebaseError)
      {
        Log.d(LOG_TAG, "Error occurred: " + firebaseError.getMessage());
      }
    });
  }


/***************************************************************************************************
*   Method:
*   Description:
*   Parameters:  N/A
*   Returned:    N/A
***************************************************************************************************/
  private boolean isEmailValid(String email) {
    boolean isGoodEmail =
      (email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
    if (!isGoodEmail) {
      mEditTextEmailCreate.setError(email + " is not a valid email.");
      return false;
    }
    return isGoodEmail;
  }



/***************************************************************************************************
*   Method:
*   Description:
*   Parameters:  N/A
*   Returned:    N/A
***************************************************************************************************/
  private boolean isUserNameValid(String userName) {
    if (userName.equals("")) {
      mEditTextUsernameCreate.setError("This cannot be empty.");
      return false;
    }
    return true;
  }


}
