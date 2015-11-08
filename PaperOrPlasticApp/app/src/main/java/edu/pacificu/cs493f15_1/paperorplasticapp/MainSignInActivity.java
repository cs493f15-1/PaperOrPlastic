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


package edu.pacificu.cs493f15_1.paperorplasticapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Map;

public class MainSignInActivity extends AppCompatActivity implements View.OnClickListener
{
  private String FIREBASE_URL = "https://boiling-fire-3734.firebaseio.com/";

  //  buttons
  private Button  mButtonSignIn,
                  mButtonCreateAccount,
                  mButtonRecoverPassword,
                  mButtonResetPassword,
                  mButtonContinue;

  //testing new email input box
  private AutoCompleteTextView mEmailView;


  //  edit text fields
  private EditText  mEditPassword;


  //  firebase reference
  private Firebase  myFirebaseRef;

  private View mLoginFormView;

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

    initializeButtons();
    initializeEditFields();
    initializeFirebase();


    testWritingDataToFirebase();
    testCreateFirebaseUsers();

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

  //  on click listener for buttons (connect to the view)
  mButtonSignIn.setOnClickListener(this);
  mButtonCreateAccount.setOnClickListener(this);
  mButtonRecoverPassword.setOnClickListener(this);
  mButtonContinue.setOnClickListener(this);
  mButtonResetPassword.setOnClickListener(this);
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
 *   Method:         initializeFirebase
 *   Description:    links firebase with our application by setting our member variable myFirebaseRef
 *                   to the link of our cloud database
 *   Parameters:     N/A
 *   Returned:       N/A
 ***************************************************************************************************/
private void initializeFirebase()
{
  Firebase.setAndroidContext(this);
  myFirebaseRef = new Firebase (FIREBASE_URL);
}

/***************************************************************************************************
 *   Method:      testWritingDataToFirebase
 *   Description: just some testing...
 *   Parameters:  N/A
 *   Returned:    N/A
 ***************************************************************************************************/
private void testWritingDataToFirebase()
{
  myFirebaseRef.child ("message").setValue ("YO WADDUP");
  myFirebaseRef.child ("testing").setValue ("Testing123");


  // test creating a new reference "ref" to save data
  Firebase ref = new Firebase (FIREBASE_URL + "/test-saving-data");

  // creating another reference "alanRef" (which is the child of the above reference)
  // creating reference to a child object -  a user with id alanisawesome
  Firebase alanRef = ref.child ("users").child ("alanisawesome");

  // creating new object testUser
  TestUser alan = new TestUser ("Alan Turing", 1912);

  // setting the value of the reference we created to the java object we created
  alanRef.setValue(alan);

  //now test retrieving this information -- Attach a listener to read the data at our posts reference (test saving data)
  alanRef.addValueEventListener(new ValueEventListener()
  {
    @Override
    public void onDataChange(DataSnapshot snapshot)
    {
      System.out.println(snapshot.getValue());
      System.out.println("There are " + snapshot.getChildrenCount() + " fields to this user.");
    }

    @Override
    public void onCancelled(FirebaseError firebaseError)
    {
      System.out.println("The read failed: " + firebaseError.getMessage());
    }
  });
}

/***************************************************************************************************
 *   Method:      testCreateFirebaseUsers
 *   Description: just more testing....
 *   Parameters:  N/A
 *   Returned:    N/A
 ***************************************************************************************************/
private void testCreateFirebaseUsers ()
{
  //hard coding the addition of a registered user

  myFirebaseRef.createUser("alco8653@pacificu.edu", "password1234",
          new Firebase.ValueResultHandler<Map<String, Object>>()
          {
            @Override
            public void onSuccess(Map<String, Object> result)
            {
              System.out.println("Successfully created user account with uid: " + result.get("uid"));
            }

            @Override
            public void onError(FirebaseError firebaseError)
            {
              // there was an error
              System.out.println("Error creating user. ");
            }
          });

  myFirebaseRef.authWithPassword("alco8653@pacificu.edu", "password1234", new Firebase.AuthResultHandler()
  {
    @Override
    public void onAuthenticated(AuthData authData)
    {
      System.out.println("Authenticated the user.");
      System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
    }

    @Override
    public void onAuthenticationError(FirebaseError firebaseError)
    {
      // there was an error
      System.out.println("Error authenticating user. ");
    }
  });

}

/***************************************************************************************************
 *   Method:      onClick
 *   Description: called when a click has been captured.
 *                If selected:
 *                Sign-In:        - capture information in edit text fields and pass to
 *                                  firebase to attempt to authenticate the user
 *                Create Account: - capture information in edit text fields and
 *                                  pass to firebase to attempt to create the user
 *                Recover Password: - an email to recover password is sent to the email
 *                                    typed in the email field
 *                Reset Password: - resets the password associated with the user with token passed in
 *                Continue:       - starts the next activity (home screen)
 *   Parameters:  view - the view that has been clicked
 *   Returned:    N/A
 ***************************************************************************************************/
  public void onClick (View view)
  {
    Intent intent; //will be using this to launch to new activity (e.g. clicking continue)

    if (mButtonSignIn == view)
    {
      //capture text from password editText and email editText ->pass this to firebase authentication
      myFirebaseRef.authWithPassword (mEmailView.getText().toString(),
                                      mEditPassword.getText().toString(),
                                      new Firebase.AuthResultHandler()
      {
        @Override
        public void onAuthenticated(AuthData authData)
        {
          System.out.println("Authenticated the user.");
          System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
          messageDialog ("Sign-In", "Successful login.");
        }

        @Override
        public void onAuthenticationError(FirebaseError firebaseError)
        {
          // there was an error
          System.out.println("Error authenticating user. ");
          messageDialog ("Unable to Sign-In", "Invalid password or email.");
        }
      });
    }

    if (mButtonCreateAccount == view)
    {
      //capture text from password editText and email editText -> pass this to firebase user create

      myFirebaseRef.createUser (mEmailView.getText().toString(),
                                mEditPassword.getText().toString(),
                                new Firebase.ValueResultHandler<Map<String, Object>>()
      {
        @Override
        public void onSuccess(Map<String, Object> result)
        {
          System.out.println("Successfully created user account with uid: " + result.get("uid"));
          messageDialog("Create Account","Account has been created.");
        }

        @Override
        public void onError(FirebaseError firebaseError)
        {
          // there was an error
          System.out.println("Error creating user. ");
          messageDialog("Unable to Create Account","Email in use or invalid Email.");
        }
      });
    }

    if (mButtonRecoverPassword == view)
    {
      myFirebaseRef.resetPassword(mEmailView.getText().toString(), new Firebase.ResultHandler() {
        @Override
        public void onSuccess()
        {
          messageDialog ("Recover Password", "Recovery Email has been sent to: \n" + mEmailView.getText().toString());
        }
        @Override
        public void onError(FirebaseError firebaseError)
        {
          // error encountered
          messageDialog ("Recover Password", "Error sending recovery Email to: \n" + mEmailView.getText().toString());
        }
      });
    }

    if (mButtonResetPassword == view)
    {
      final Dialog login = new Dialog (this);

      login.setContentView(R.layout.dialog_pass_recovery);
      login.setTitle("Reset Password");

      Button btnReset = (Button) login.findViewById(R.id.btnReset);
      Button btnCancel = (Button) login.findViewById(R.id.btnCancel);

      final EditText  txtUser = (EditText) login.findViewById(R.id.userEmail),
                      txtToken = (EditText) login.findViewById(R.id.passwordToken),
                      txtNewPass = (EditText) login.findViewById(R.id.newPassword);

      //recoverPasswordDialog();

      btnReset.setOnClickListener(new View.OnClickListener()
      {
        @Override
        public void onClick(View v)
        {
          myFirebaseRef.authWithPassword(txtUser.getText().toString(),
            txtToken.getText().toString(),
            new Firebase.AuthResultHandler()
            {
              @Override
              public void onAuthenticated(AuthData authData)
              {
                myFirebaseRef.changePassword(txtUser.getText().toString(),
                txtToken.getText().toString(), txtNewPass.getText().toString(),
                new Firebase.ResultHandler()
                {
                  @Override
                  public void onSuccess()
                  {
                    System.out.println("Password changed. ");
                  }

                  @Override
                  public void onError(FirebaseError firebaseError)
                  {
                    System.out.println("Error changing password. ");
                  }
                });
              }

              @Override
              public void onAuthenticationError(FirebaseError firebaseError)
              {
                // there was an error
              }
            });

          login.dismiss();
        }
      });

      btnCancel.setOnClickListener(new View.OnClickListener()
      {
        @Override
        public void onClick(View v)
        {
          login.dismiss();
        }
      });

      login.show();
    }

    if (mButtonContinue == view)
    {
      intent = new Intent (this, ContinueActivity.class);
      startActivity (intent);
    }

  }


/***************************************************************************************************
 *   Method:      messageDialog
 *   Description: for now, using this as a template to see how to get a dialog box to appear
 *   Parameters:  N/A
 *   Returned:    N/A
 ***************************************************************************************************/
  private void messageDialog (String title, String message)
  {
    new AlertDialog.Builder (this).setTitle (title).setMessage(message)
            .setPositiveButton("OK", new DialogInterface.OnClickListener()
            {
              public void onClick(DialogInterface dialog, int ok)
              {
                //user clicked ok
              }
            }).show ();
  }


/*  private void recoverPasswordDialog()
  {
    LayoutInflater inflater = this.getLayoutInflater();

    AlertDialog.Builder test;
    DialogInterface.OnClickListener resetDialog;
    test = new AlertDialog.Builder (this).setView(inflater.inflate(R.layout.dialog_pass_recovery, null));



    test.setPositiveButton("OK", new DialogInterface.OnClickListener()
    {
      @Override
      public void onClick(DialogInterface dialog, int ok)
      {
        //user clicked ok

        myFirebaseRef.authWithPassword(dUserEmail.getText().toString(),
                dPassToken.getText().toString(),
                new Firebase.AuthResultHandler()
                {
                  @Override
                  public void onAuthenticated(AuthData authData)
                  {
                    myFirebaseRef.changePassword(dUserEmail.getText().toString(),
                            dPassToken.getText().toString(), dPassNew.getText().toString(),
                            new Firebase.ResultHandler()
                            {
                              @Override
                              public void onSuccess()
                              {
                                System.out.println("Password changed. ");
                              }

                              @Override
                              public void onError(FirebaseError firebaseError)
                              {
                                System.out.println("Error changing password. ");
                              }
                            });
                  }

                  @Override
                  public void onAuthenticationError(FirebaseError firebaseError)
                  {
                    // there was an error
                  }
                });
      }
    })
    .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface dialog, int cancel)
      {
        //user clicked cancel
        //LoginDialogFragment.this.getDialog().cancel();
      }
    });

    test.show();

  }*/



}
