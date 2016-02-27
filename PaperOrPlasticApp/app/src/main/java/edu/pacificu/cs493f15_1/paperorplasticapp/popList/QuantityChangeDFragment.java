package edu.pacificu.cs493f15_1.paperorplasticapp.popList;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import edu.pacificu.cs493f15_1.paperorplasticapp.R;

/**
 * Created by sull0678 on 11/30/2015.
 */
public class QuantityChangeDFragment extends DialogFragment
{
    private Button mbOK;
    private EditText mQtyText;
    private Dialog mDialog;


    public QuantityChangeDFragment() {

        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_quantity_change, container,
                false);

        // Get field from view
        mQtyText = (EditText) rootView.findViewById(R.id.newQtyText);

        // Show soft keyboard automatically and request focus to field
        mQtyText.requestFocus();



        mbOK = (Button) rootView.findViewById (R.id.ok_button);
        mbOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PoPListActivity activity = (PoPListActivity) getActivity();
             //   QtyChangeDialogListener listener = activity.getQtyChangeListener();
             //   listener.onFinishQtyChangeDialog(mQtyText.getText().toString());
                mDialog.dismiss();
            }
        });

        mDialog = getDialog();

        //mDialog.setTitle("DialogFragment Tutorial");


        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        // Do something else
        return rootView;
    }
}
