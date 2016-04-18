package edu.pacificu.cs493f15_1.paperorplasticapp.popList;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import edu.pacificu.cs493f15_1.paperorplasticapp.R;
import edu.pacificu.cs493f15_1.utils.Constants;

/**
 * Created by alco8653 on 4/3/2016.
 */
public class AddSimpleListDialogFragment extends DialogFragment
{
  String mEncodedEmail;
  EditText mEditTextListName;

  /**
   * Public static constructor that creates fragment and
   * passes a bundle with data into it when adapter is created
   */
  public static AddSimpleListDialogFragment newInstance(String encodedEmail)
  {
    AddSimpleListDialogFragment addListDialogFragment = new AddSimpleListDialogFragment();
    Bundle bundle = new Bundle();
    bundle.putString(Constants.KEY_ENCODED_EMAIL,encodedEmail);
    addListDialogFragment.setArguments(bundle);
    return addListDialogFragment;
  }

  /**
   * Initialize instance variables with data from bundle
   */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    mEncodedEmail = getArguments().getString(Constants.KEY_ENCODED_EMAIL);
  }

  /**
   * Open the keyboard automatically when the dialog fragment is opened
   */
  @Override
  public void onActivityCreated(Bundle savedInstanceState)
  {
    super.onActivityCreated(savedInstanceState);
    getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState)
  {
    // Use the Builder class for convenient dialog construction
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomTheme_Dialog);
    // Get the layout inflater
    LayoutInflater inflater = getActivity().getLayoutInflater();
    View rootView = inflater.inflate(R.layout.dialog_add_list, null);
    mEditTextListName = (EditText) rootView.findViewById(R.id.edit_text_list_name);

    /**
     * Call addShoppingList() when user taps "Done" keyboard action
     */
    mEditTextListName.setOnEditorActionListener(new TextView.OnEditorActionListener()
    {
      @Override
      public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent)
      {
        if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == KeyEvent.ACTION_DOWN)
        {
          addSimpleList();
        }
        return true;
      }
    });

        /* Inflate and set the layout for the dialog */
        /* Pass null as the parent view because its going in the dialog layout*/
    builder.setView(rootView)
                /* Add action buttons */
      .setPositiveButton("Create", new DialogInterface.OnClickListener()
      {
        @Override
        public void onClick(DialogInterface dialog, int id)
        {
          addSimpleList();
        }
      });

    return builder.create();
  }


  public void addSimpleList()
  {
    String userEnteredName = mEditTextListName.getText().toString();

    if (!userEnteredName.equals(""))
    {
//      Firebase listsRef = new Firebase(Constants.FIREBASE_URL_ACTIVE_LISTS);
//      Firebase newListRef = listsRef.push();
//
//      final String listId = newListRef.getKey();
//
//      HashMap<String, Object> timestampCreated = new HashMap<>();
//      timestampCreated.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);
//
//
//      ShoppingList newShoppingList = new ShoppingList(userEnteredName, mEncodedEmail, timestampCreated);
//
//      newListRef.setValue(newShoppingList);
//
//      AddListDialogFragment.this.getDialog().cancel();
    }

  }

}
