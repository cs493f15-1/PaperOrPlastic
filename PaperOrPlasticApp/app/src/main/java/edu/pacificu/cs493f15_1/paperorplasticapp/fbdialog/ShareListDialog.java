package edu.pacificu.cs493f15_1.paperorplasticapp.fbdialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import edu.pacificu.cs493f15_1.paperorplasticapp.R;
import edu.pacificu.cs493f15_1.paperorplasticapp.popList.PoPListItemsActivity;

/**
 * Created by alco8653 on 4/12/2016.
 */
public class ShareListDialog extends DialogFragment
{
  private Button mbCancel;
  private Button mbOK;
  private EditText mEditText;
  private Dialog mDialog;

  String mEncodedEmail;


  public ShareListDialog()
  {
    // Empty constructor required for DialogFragment
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState)
  {
    View rootView = inflater.inflate(R.layout.listdialogfragment, container,
      false);


    // Get field from view
    mEditText = (EditText) rootView.findViewById(R.id.edit_text);

    // makes editText selected
    mEditText.setHint("Enter your friend's email address");
    mEditText.requestFocus();


    mbCancel = (Button) rootView.findViewById(R.id.cancel_button);
    mbCancel.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        mDialog.dismiss();
      }
    });

    mbOK = (Button) rootView.findViewById(R.id.ok_button);
    mbOK.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        PoPListItemsActivity activity = (PoPListItemsActivity) getActivity();
        (activity.getListInfoListener()).onFinishNewListDialog(mEditText.getText().toString());
        mDialog.dismiss();
      }
    });

    mDialog = getDialog();


    mDialog.setTitle("Share this list with: ");

    mDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    // Do something else
    return rootView;
  }
}
