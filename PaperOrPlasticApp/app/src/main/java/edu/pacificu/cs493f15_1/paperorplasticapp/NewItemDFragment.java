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
import edu.pacificu.cs493f15_1.paperorplasticapp.NewItemInfoDialogListener;

/**
 * Created by sull0678 on 11/3/2015.
 */
public class NewItemDFragment extends DialogFragment
{
    private Button mbCancel;
    private Button mbOK;
    private EditText mItemNameText;
    private Dialog mDialog;


    public NewItemDFragment() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.new_item_dialog_fragment, container,
                false);

        // Get field from view
        mItemNameText = (EditText) rootView.findViewById(R.id.item_name_input);

        // Show soft keyboard automatically and request focus to field
        mItemNameText.requestFocus();


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
                ListsActivity activity = (ListsActivity) getActivity();
                NewItemInfoDialogListener listener = activity.getItemInfoListener();
                listener.onFinishNewItemDialog(mItemNameText.getText().toString());
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
