/**************************************************************************************************
*   File:     MainSignInActivity.java
*   Author:   Brianna Alcoran
*   Date:     10/28/15
*   Class:    Capstone/Software Engineering
*   Project:  PaperOrPlastic Application
*   Purpose:  This activity will be the opening activity of the application.
*             A user may create an account on this page, or sign-in with an existing one.
*             Users also have the option to save their password for automatic sign-in.
*             Recovering forgotten passwords is also available on this screen.
*             It is also an option to skip these steps and continue on -> the user is then
*             essentially using the application offline.
***************************************************************************************************/


package edu.pacificu.cs493f15_1.paperorplasticapp.signIn;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.usage.ConfigurationStats;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.fasterxml.jackson.databind.node.ContainerNode;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;

import java.util.Map;

import edu.pacificu.cs493f15_1.paperorplasticapp.BaseActivity;
import edu.pacificu.cs493f15_1.paperorplasticjava.User;
import edu.pacificu.cs493f15_1.utils.Constants;
import edu.pacificu.cs493f15_1.paperorplasticjava.FirebaseUser;
import edu.pacificu.cs493f15_1.paperorplasticapp.menu.ContinueActivity;
import edu.pacificu.cs493f15_1.paperorplasticapp.R;
import edu.pacificu.cs493f15_1.utils.Utils;

/***************************************************************************************************
 *   Class:         MainSignInActivity
 *   Description:   Creates MainSignInActivity class that controls the initial sign in page
 *   Parameters:    N/A
 *   Returned:      N/A
 **************************************************************************************************/
public class MainSignInActivity extends BaseActivity
{
  private static final String LOG_TAG = MainSignInActivity.class.getSimpleName();
  private String SIGNIN_PREFS = "signinPrefs";
  private String SIGNIN_PREFS_BOOLEAN = "saveSignIn";

  //  buttons
  private Button  mButtonSignIn,
                  mButtonCreateAccount,
                  mButtonRecoverPassword,
                  mButtonResetPassword,
                  mButtonContinue;

  //  checkboxes
  private CheckBox mcbRememberPass;

  //email & password
  private AutoCompleteTextView mEmailView;
  private EditText  mEditPassword;

  private SharedPreferences mSignInPreferences;
  private SharedPreferences.Editor mSignInPrefsEditor;

  private boolean saveSignIn = false;

  //  firebase reference
  private Firebase  myFirebaseRef;
  private FirebaseUser mfCurrentUser;

  private boolean mAuthSuccess = false, mbResetSuccess, mbResetDismiss;

  private View mLoginFormView;

  private ProgressDialog mAuthProgressDialog;


  /**
   * Variables related to Google Login
   */
    /* A flag indicating that a PendingIntent is in progress and prevents us from starting further intents. */
  private boolean mGoogleIntentInProgress;
  /* Request code used to invoke sign in user interactions for Google+ */
  public static final int RC_GOOGLE_LOGIN = 1;
  /* A Google account object that is populated if the user signs in with Google */
  GoogleSignInAccount mGoogleAccount;



  /***************************************************************************************************
 *   Method:        onCreate
 *   Description:   is called when the activity is created. Sets the content view and initializes
 *                  our buttons, text fields, and firebase (connection to the cloud database).
 *                  As of right now, there are also testing functions being called after the
 *                  necessary initialization.
 *   Parameters:    savedInstanceState
 *   Returned:      N/A
 ***************************************************************************************************/
  @Override
  protected void onCreate (Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main_sign_in);

    initializeActivity();
  }

  /***************************************************************************************************
   *   Method:       onResume
   *   Description:  calls super.onResume() and initializes the sign-in prefs
   *   Parameters:   N/A
   *   Returned:     N/A
   ***************************************************************************************************/
  @Override
  protected void onResume ()
  {
    super.onResume();
    initializeSignInPrefs();
  }

  /***************************************************************************************************
   *   Method:       onPause
   *   Description:  calls super.onPause()
   *   Parameters:   N/A
   *   Returned:     N/A
   ***************************************************************************************************/
  @Override
  public void onPause()
  {
    super.onPause();
  }


/***************************************************************************************************
 *   Method:       initializeActivity
 *   Description:  calls all initialization functions
 *   Parameters:   N/A
 *   Returned:     N/A
 ***************************************************************************************************/
  private void initializeActivity()
  {
    initializeButtons();
    initializeEditFields();
    initializeCheckboxes();
    initializeFirebase();
    initializeSignInPrefs();

    mLoginFormView = findViewById(R.id.loginForm);
    LinearLayout linearLayout = (LinearLayout) mLoginFormView;
    linearLayout.setBackgroundResource(R.drawable.grocerybackportrait2);

    mAuthProgressDialog = new ProgressDialog(this);
    mAuthProgressDialog.setTitle("Loading...");
    mAuthProgressDialog.setMessage("Authenticating with Firebase...");
    mAuthProgressDialog.setCancelable(false);

    setupGoogleSignIn();
  }


/***************************************************************************************************
 *   Method:       initializeButtons
 *   Description:  pairs member variable buttons with the views on this activity by ID
 *   Parameters:   N/A
 *   Returned:     N/A
 ***************************************************************************************************/
private void initializeButtons ()
{
  //  initializing/pairing buttons
  mButtonSignIn = (Button) findViewById (R.id.bSignIn);
  mButtonCreateAccount = (Button) findViewById (R.id.bCreateAccount);
  mButtonRecoverPassword = (Button) findViewById (R.id.bRecoverPassword);
  mButtonContinue = (Button) findViewById (R.id.bContinue);
  mButtonResetPassword = (Button) findViewById (R.id.bResetPassword);


  SignInButton signInButton = (SignInButton)findViewById(R.id.login_with_google);
  signInButton.setSize(SignInButton.SIZE_WIDE);

}

/***************************************************************************************************
 *   Method:       initializeCheckboxes
 *   Description:  pairs member variable checkboxes with the views on this activity by ID
 *   Parameters:   N/A
 *   Returned:     N/A
 ***************************************************************************************************/
private void initializeCheckboxes()
{
  mcbRememberPass = (CheckBox) findViewById(R.id.cbRememberPassword);
  mcbRememberPass.setChecked(false);
}


/***************************************************************************************************
 *   Method:       initializeEditFields
 *   Description:  pairs member variable edit text fields with the views on this activity
 *   Parameters:   N/A
 *   Returned:     N/A
 ***************************************************************************************************/
private void initializeEditFields()
{
  mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
  mEditPassword = (EditText) findViewById (R.id.editTextPassword);
  mLoginFormView = findViewById(R.id.loginForm);
}



/***************************************************************************************************
 *   Method:       initializeSignInPrefs
 *   Description:  sets shared preferences for signing in with this app
 *   Parameters:   N/A
 *   Returned:     N/A
 ***************************************************************************************************/
private void initializeSignInPrefs()
{
  mSignInPreferences = getSharedPreferences(SIGNIN_PREFS, MODE_PRIVATE);
  mSignInPrefsEditor = mSignInPreferences.edit();
  saveSignIn = mSignInPreferences.getBoolean(SIGNIN_PREFS_BOOLEAN, false);
  if (saveSignIn)
  {
    mEmailView.setText(mSignInPreferences.getString("email", ""));
    mEditPassword.setText(mSignInPreferences.getString("password", ""));
    mcbRememberPass.setChecked(true);
  }
}



/***************************************************************************************************
*   Method:         initializeFirebase
*   Description:    links firebase with our application by setting our member variable myFirebaseRef
*                   to the link of our cloud database
*   Parameters:     N/A
*   Returned:       N/A
***************************************************************************************************/
private void initializeFirebase()
{
  //Firebase.setAndroidContext(this.getApplication());
  myFirebaseRef = new Firebase (Constants.FIREBASE_URL);
}

/***************************************************************************************************
*   Method:      setupGoogleSignI
*   Description: when sign in button pressed, attempt to authenticate the user through
*   Parameters:  N/A
*   Returned:    N/A
***************************************************************************************************/
/* Sets up the Google Sign In Button : https://developers.google.com/android/reference/com/google/android/gms/common/SignInButton */
private void setupGoogleSignIn()
{
  SignInButton signInButton = (SignInButton) findViewById(R.id.login_with_google);
  signInButton.setSize(SignInButton.SIZE_WIDE);
  signInButton.setOnClickListener(new View.OnClickListener()
  {
    @Override
    public void onClick(View v)
    {
      onSignInGooglePressed(v);
    }
  });
}
/***************************************************************************************************
 *   Method:      rememberPass
 *   Description: remember the email and password if the checkbox is checked. otherwise, clear
 *                the sign in preferences data
 *   Parameters:  email - user email
 *                password - user password
 *   Returned:    N/A
 ***************************************************************************************************/
public void rememberPass(String email, String password)
{
  if (mcbRememberPass.isChecked())
  {
    if (mAuthSuccess)
    {
      mSignInPrefsEditor.putBoolean(SIGNIN_PREFS_BOOLEAN, true);
      mSignInPrefsEditor.putString("email", email);
      mSignInPrefsEditor.putString("password", password);
      mSignInPrefsEditor.commit();
      if (mfCurrentUser != null)
      {
        mfCurrentUser.setmbRememberPass(true);
      }
    }

  }
  else
  {
    mSignInPrefsEditor.clear();
    mSignInPrefsEditor.commit();
    if (mfCurrentUser != null)
    {
      mfCurrentUser.setmbRememberPass(false);
    }
  }
}


/***************************************************************************************************
*   Method:      onSignInPressed
*   Description: when sign in button pressed, attempt to authenticate the user
*   Parameters:  N/A
*   Returned:    N/A
***************************************************************************************************/
  public void onSignInPressed (View view)
  {
    String email = mEmailView.getText().toString();
    String password = mEditPassword.getText().toString();


    if (email.equals(""))
    {
      mEmailView.setError("This cannot be empty.");
      return;
    }

    if (password.equals(""))
    {
      mEditPassword.setError("This cannot be empty.");
      return;
    }
    mAuthProgressDialog.show();
    signInAttempt();
  }




/***************************************************************************************************
*   Method:      onSignInGooglePressed
*   Description: when sign in button pressed, attempt to authenticate the user through
*   Parameters:  N/A
*   Returned:    N/A
***************************************************************************************************/
  public void onSignInGooglePressed(View view)
  {

  }



/***************************************************************************************************
*   Method:      onCreateAccountPressed
*   Description: Open CreateAccountActivity when user taps on "Create Account"
*   Parameters:  N/A
*   Returned:    N/A
***************************************************************************************************/

  public void onCreateAccountPressed (View view)
  {
    Intent intent = new Intent(MainSignInActivity.this, CreateAccountActivity.class);
    startActivity(intent);
  }

/***************************************************************************************************
*   Method:      onRecoverPressed
*   Description: Open PasswordRecoveryActivity when user taps on "Create Account"
*   Parameters:  N/A
*   Returned:    N/A
***************************************************************************************************/

  public void onRecoverPressed (View view)
  {
    Intent intent = new Intent(MainSignInActivity.this, PasswordRecoveryActivity.class);
    startActivity(intent);
  }


/***************************************************************************************************
*   Method:      onContinueOfflinePressed
*   Description: to be executed when continue offline button is pressed
*   Parameters:  N/A
*   Returned:    N/A
***************************************************************************************************/
  public void onContinueOfflinePressed (View view)
  {
    Intent intent = new Intent (MainSignInActivity.this, ContinueActivity.class);

    if (!mAuthSuccess)
    {
      //mfCurrentUser = new FirebaseUser("offline");

      //then we are choosing to continue with use of the app offline... TODO for now a quick fix
      bUsingOffline = true;
    }

    //intent.putExtra("currentUser", mfCurrentUser);

    startActivity(intent);
  }


/***************************************************************************************************
 *   Method:      messageDialog
 *   Description: for now, using this as a template to see how to get a dialog box to appear
 *   Parameters:  N/A
 *   Returned:    N/A
 ***************************************************************************************************/
  private void messageDialog (String title, String message, final boolean bContinue)
  {
    final Intent intent = new Intent (this, ContinueActivity.class);

    new AlertDialog.Builder (this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int ok) { //user clicked ok
                if (bContinue)
                {
                  if (mAuthSuccess)
                  {
                    intent.putExtra("currentUser", mfCurrentUser);
                  }
                  startActivity(intent);
                }
              }
            }).show();
  }

/***************************************************************************************************
 *   Method:      setMfCurrentUser
 *   Description: after a successful sign-in attempts, sets the mf current user
 *   Parameters:  authData - firebase authorization data
 *                email - the email for the user
 *                password - the password for the user
 *   Returned:    N/A
 ***************************************************************************************************/
  public void setMfCurrentUser (AuthData authData, String email, String password)
  {
    mAuthSuccess = true;

    mfCurrentUser = new FirebaseUser(authData.getUid());
    mfCurrentUser.setmProvider(authData.getProvider());
    mfCurrentUser.getMyRef().child("provider").setValue(authData.getProvider());
    mfCurrentUser.setmEmail(email);

    rememberPass(email, password);
  }

  /***************************************************************************************************
 *   Method:      signInAttempt
 *   Description: to be executed when the user clicks on sign-in button. captures text in the email
 *                 and password edit text fields -> attempts to signin using these credentials. A
 *                 dialog appears after the sign in button has been pressed in order to indicate
 *                 if the sign in attempt was or was not successful
 *   Parameters:  N/A
 *   Returned:    N/A
 ***************************************************************************************************/
  public void signInAttempt()
  {
    final String email = mEmailView.getText().toString();
    final String password = mEditPassword.getText().toString();

    /* authenticate the user with the information in the fields!! */
    myFirebaseRef.authWithPassword(email, password,
      new PoPAuthResultHandler(Constants.PASSWORD_PROVIDER));

    rememberPass(email, password);
  }

  /** NEW CLASS -- Declaring our own methods of authenticating the user
   *
   *
   *
   */
  private class PoPAuthResultHandler implements Firebase.AuthResultHandler
  {
    private final String provider;

    public PoPAuthResultHandler (String provider)
    {
      this.provider = provider;
    }

    @Override
    public void onAuthenticated (AuthData authData)
    {
      mAuthSuccess = true;
      bUsingOffline = false;
      mAuthProgressDialog.dismiss();
      Log.i(LOG_TAG, provider + " " + "auth successful.");

      if (authData != null)
      {
        //shared preferences
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor spe = sp.edit();

        if (authData.getProvider().equals(Constants.PASSWORD_PROVIDER))
        {
          setAuthUserPassPro(authData);
        }
        else
        {
          if (authData.getProvider().equals(Constants.GOOGLE_PROVIDER))
          {
            setAuthUserGoogle(authData);
          }

        }
                /* Save provider name and encodedEmail for later use and start MainActivity */
        spe.putString(Constants.KEY_PROVIDER, authData.getProvider()).apply();
        spe.putString(Constants.KEY_ENCODED_EMAIL, mEncodedEmail).apply();

                /* Go to main activity */
        Intent intent = new Intent(MainSignInActivity.this, ContinueActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
      }
    }

    @Override
    public void onAuthenticationError (FirebaseError firebaseError)
    {
      mAuthProgressDialog.dismiss();
    }

  }


/***************************************************************************************************
*   Method:
*   Description:
*   Parameters:  N/A
*   Returned:    N/A
***************************************************************************************************/
  private void setAuthUserGoogle(AuthData authData)
  {

  }

/***************************************************************************************************
*   Method:
*   Description:
*   Parameters:  N/A
*   Returned:    N/A
***************************************************************************************************/
  private void setAuthUserPassPro(AuthData authData)
  {
    final String unprocessedEmail = authData.getProviderData()
      .get(Constants.FIREBASE_PROPERTY_EMAIL).toString().toLowerCase();
    /**
     * Encode user email replacing "." with ","
     * to be able to use it as a Firebase db key
     */
    mEncodedEmail = Utils.encodeEmail(unprocessedEmail);

    final Firebase userRef = new Firebase(Constants.FIREBASE_URL_USERS).child(mEncodedEmail);

    /**
     * Check if current user has logged in at least once
     */
    userRef.addListenerForSingleValueEvent(new ValueEventListener()
    {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot)
      {
        User user = dataSnapshot.getValue(User.class);

        if (user != null)
        {
          /**
           * If recently registered user has hasLoggedInWithPassword = "false"
           * (never logged in using password provider)
           */
          if (!user.isbLoggedInPassword())
          {

            /**
             * Change password if user that just signed in signed up recently
             * to make sure that user will be able to use temporary password
             * from the email more than 24 hours
             */
            mFirebaseRef.changePassword(unprocessedEmail, mEmailView.getText().toString(),
              mEditPassword.getText().toString(), new Firebase.ResultHandler()
              {
                @Override
                public void onSuccess()
                {
                  userRef.child(Constants.FIREBASE_PROPERTY_USER_HAS_LOGGED_IN_WITH_PASSWORD).setValue(true);
                                        /* The password was changed */
                  Log.d(LOG_TAG, "pass change successful" +
                    mEditPassword.getText().toString());
                }

                @Override
                public void onError(FirebaseError firebaseError)
                {
                  Log.d(LOG_TAG, "failed to change the password" + firebaseError);
                }
              });
          }
        }
      }

      @Override
      public void onCancelled(FirebaseError firebaseError)
      {
        Log.e(LOG_TAG,
          "the read failed" +
            firebaseError.getMessage());
      }
    });
  }


}
