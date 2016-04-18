package edu.pacificu.cs493f15_1.paperorplasticapp.signIn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import edu.pacificu.cs493f15_1.paperorplasticapp.BaseActivity;
import edu.pacificu.cs493f15_1.paperorplasticapp.R;
import edu.pacificu.cs493f15_1.utils.Constants;

/**
 * Created by alco8653 on 2/29/2016.
 */
public class PasswordRecoveryActivity extends BaseActivity
{
  private static final String LOG_TAG = PasswordRecoveryActivity.class.getSimpleName();

  private EditText mEditTextEmail, mEditTextResetEmail, mEditTextToken, mEditTextNewPass;
  private ProgressDialog mProgressDialog;
  private Firebase myFirebaseRef;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(LOG_TAG, "set the content view");

    setContentView(R.layout.activity_password_recovery);

    myFirebaseRef = new Firebase(Constants.FIREBASE_URL);


    initializeActivity();
  }



  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  { //no menu options rn
    return true;
  }

  @Override
  protected void onResume()
  {
    super.onResume();
  }

  @Override
  public void onPause()
  {
    super.onPause();
  }


/***************************************************************************************************
*   Method:
*   Description:
*   Parameters:  N/A
*   Returned:    N/A
***************************************************************************************************/
  public void initializeActivity()
  {
    mEditTextEmail = (EditText) findViewById(R.id.editTextEmailRecover);
    mEditTextResetEmail = (EditText) findViewById(R.id.editTextResetEmail);
    mEditTextToken = (EditText) findViewById(R.id.editTextToken);
    mEditTextNewPass = (EditText) findViewById(R.id.editTextNewPassword);

    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear_layout_password_recovery);
    initializeBackground(linearLayout);

    mProgressDialog = new ProgressDialog(this);
    mProgressDialog.setTitle("Loading...");
    mProgressDialog.setMessage("Please check your inbox for your password");
    mProgressDialog.setCancelable(false);

  }

/***************************************************************************************************
*   Method:
*   Description:
*   Parameters:  N/A
*   Returned:    N/A
***************************************************************************************************/
  public void onSendRecoverPressed(View view)
  {
    String email = mEditTextEmail.getText().toString();

    if (email.equals(""))
    {
      mEditTextEmail.setError("This cannot be empty.");
    }

    mProgressDialog.setMessage("Sending recovery email...");
    mProgressDialog.show();

    final String theEmail = email;

    myFirebaseRef.resetPassword(email, new Firebase.ResultHandler()
    {

      @Override
      public void onSuccess()
      {
        mProgressDialog.dismiss();
        messageDialog(getString(R.string.passRecover), "Recovery Email has been sent to: \n" + theEmail);
      }

      @Override
      public void onError(FirebaseError firebaseError)// error encountered
      {
        mProgressDialog.dismiss();
        messageDialog(getString(R.string.passRecover), "Error sending recovery Email to: \n" + theEmail);
      }
    });

  }

/***************************************************************************************************
*   Method:
*   Description:
*   Parameters:  N/A
*   Returned:    N/A
***************************************************************************************************/
  public void onResetPasswordPressed(View view)
  {
    boolean bPass = true;

    final String userEmail = mEditTextResetEmail.getText().toString();
    final String userToken = mEditTextToken.getText().toString();
    final String userPass = mEditTextNewPass.getText().toString();


    if (userEmail.equals(""))
    {
      mEditTextResetEmail.setError("This cannot be empty.");
      bPass = false;
    }

    if (userToken.equals(""))
    {
      mEditTextToken.setError("This cannot be empty.");
      bPass = false;
    }

    if (userPass.equals(""))
    {
      mEditTextNewPass.setError("This cannot be empty.");
      bPass = false;
    }

    if (bPass)
    {
      mProgressDialog.setMessage("Resetting password...");
      mProgressDialog.show();

      myFirebaseRef.authWithPassword(userEmail, userToken, new Firebase.AuthResultHandler()
      {
        @Override
        public void onAuthenticated(AuthData authData)
        {
          myFirebaseRef.changePassword(userEmail, userToken, userPass,
              new Firebase.ResultHandler()
              {
                @Override
                public void onSuccess()
                {
                  messageDialog("Reset Password", "Password reset successful!");
                  Intent intent = new Intent(PasswordRecoveryActivity.this, MainSignInActivity.class);
                  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                  startActivity(intent);
                  finish();
                }

                @Override
                public void onError(FirebaseError firebaseError)
                {
                  messageDialog("Reset Password", captureFirebaseError(firebaseError));
                }
              });
        }

        @Override
        public void onAuthenticationError(FirebaseError firebaseError)
        {
          messageDialog("Reset Password", captureFirebaseError(firebaseError));
        }
      });


      Handler handler = new Handler();
      handler.postDelayed(new Runnable()
      {
        public void run()
        {
          mProgressDialog.dismiss();
        }}, 3000);
    }
  }

}
