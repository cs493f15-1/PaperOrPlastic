package edu.pacificu.cs493f15_1.paperorplasticapp;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.firebase.client.Firebase;

import edu.pacificu.cs493f15_1.utils.Constants;

/**
 * Reset Password Dialog Fragment (dialog to reset the user's password)
 */
public class ResetPassDialogFragment extends DialogFragment
{
  EditText mEditTextUserEmail, mEditTextUserToken, mEditTextNewPass;

  public static ResetPassDialogFragment newInstance()
  {
    ResetPassDialogFragment resetPassDialogFragment = new ResetPassDialogFragment();
    Bundle bundle = new Bundle();
    resetPassDialogFragment.setArguments(bundle);
    return resetPassDialogFragment;
  }

  /**
   * Initialize instance variables with data from bundle
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  /**
   * Open the keyboard automatically when the dialog fragment is opened
   */
  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    // Use the Builder class for convenient dialog construction
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppTheme);
    // Get the layout inflater
    LayoutInflater inflater = getActivity().getLayoutInflater();
    View rootView = inflater.inflate(R.layout.dialog_pass_recovery, null);

    mEditTextUserEmail = (EditText) rootView.findViewById(R.id.txtUserEmail);
    mEditTextUserToken = (EditText) rootView.findViewById(R.id.txtPassToken);
    mEditTextNewPass = (EditText) rootView.findViewById(R.id.txtNewPass);


        /* Inflate and set the layout for the dialog */
        /* Pass null as the parent view because its going in the dialog layout*/
    builder.setView(rootView)
                /* Add action buttons */
            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int id) {
                addInfo();
              }
            });

    return builder.create();
  }

  /**
   * Add new active list
   */
  public void addInfo() {
    Firebase ref = new Firebase(Constants.FIREBASE_URL);
    String userEmail = mEditTextUserEmail.getText().toString();
    String userToken = mEditTextUserToken.getText().toString();
    String newPass = mEditTextNewPass.getText().toString();


    //ShoppingList shoppingList = new ShoppingList(userEnteredName, "ANON");
    //ref.child("activeList").setValue(shoppingList);
    //ref.child("listName").setValue(userEnteredName);

  }
}
