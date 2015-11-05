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
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
                  mButtonContinue;

  //  edit text fields
  private EditText  mEditEmail,
                    mEditPassword;

  //  firebase reference
  private Firebase  myFirebaseRef;



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

  //  on click listener for buttons (connect to the view)
  mButtonSignIn.setOnClickListener(this);
  mButtonCreateAccount.setOnClickListener (this);
  mButtonRecoverPassword.setOnClickListener (this);
  mButtonContinue.setOnClickListener(this);
}


/***************************************************************************************************
 *   Method:       initializeEditFields
 *   Description:  pairs member variable edit text fields with the views on this activity
 *   Parameters:   N/A
 *   Returned:     N/A
 ***************************************************************************************************/
private void initializeEditFields()
{
  mEditEmail = (EditText) findViewById (R.id.editTextEmail);
  mEditPassword = (EditText) findViewById (R.id.editTextPassword);
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
          new Firebase.ValueResultHandler <Map<String, Object>>()
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
 *                If Sign-In button selected: capture information in edit text fields and pass to
 *                                            firebase to attempt to authenticate the user
 *                If Create Account button selected: capture information in edit text fields and
 *                                                   pass to firebase to attempt to create the user
 *                If Recover Password button selected: TODO
 *                If Continue button selected:  TODO
 *   Parameters:  view - the view that has been clicked
 *   Returned:    N/A
 ***************************************************************************************************/
  public void onClick (View view)
  {
    Intent intent; //will be using this to launch to new activity (e.g. clicking continue)

    if (mButtonSignIn == view)
    {
      //capture text from password editText and email editText ->pass this to firebase authentication
      myFirebaseRef.authWithPassword (mEditEmail.getText().toString(),
                                      mEditPassword.getText().toString(),
                                      new Firebase.AuthResultHandler()
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
          messageDialog ("Unable to Sign-In", "Invalid password or email.");
        }
      });

    }

    if (mButtonCreateAccount == view)
    {
      //capture text from password editText and email editText -> pass this to firebase user create

      myFirebaseRef.createUser (mEditEmail.getText().toString(),
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
      myFirebaseRef.resetPassword(mEditEmail.getText().toString(), new Firebase.ResultHandler() {
        @Override
        public void onSuccess()
        {
          messageDialog ("Recover Password", "Recovery Email has been sent to: \n" + mEditEmail.getText().toString());
        }
        @Override
        public void onError(FirebaseError firebaseError)
        {
          // error encountered
          messageDialog ("Recover Password", "Error sending recovery Email to: \n" + mEditEmail.getText().toString());
        }
      });
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

}
