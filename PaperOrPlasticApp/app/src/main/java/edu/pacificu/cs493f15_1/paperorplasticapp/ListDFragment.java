package edu.pacificu.cs493f15_1.paperorplasticapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import edu.pacificu.cs493f15_1.paperorplasticapp.ListDFragment.EditNameDialogListener;
import edu.pacificu.cs493f15_1.paperorplasticjava.GroceryList;

/**
 * Created by sull0678 on 11/3/2015.
 */
public class ListDFragment extends DialogFragment
{
    private Button mbCancel;
    private Button mbOK;
    private EditText mEditText;
    private Dialog mDialog;


    public interface EditNameDialogListener {
        void onFinishListDialog(String inputText);
    }

    public ListDFragment() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.listdialogfragment, container,
                false);

        // Get field from view
        mEditText = (EditText) rootView.findViewById(R.id.edit_text);

        // Show soft keyboard automatically and request focus to field
        mEditText.requestFocus();


        mbCancel = (Button) rootView.findViewById (R.id.cancel_button);
        mbCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        mbOK = (Button) rootView.findViewById (R.id.ok_button);
        mbOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditNameDialogListener activity = (EditNameDialogListener) getActivity();
                activity.onFinishListDialog(mEditText.getText().toString());
                mDialog.dismiss();
            }
        });

        mDialog = getDialog();

        mDialog.setTitle("DialogFragment Tutorial");


        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        // Do something else
        return rootView;
    }
}
