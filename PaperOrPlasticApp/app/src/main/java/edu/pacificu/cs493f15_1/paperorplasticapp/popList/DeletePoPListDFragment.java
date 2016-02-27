
package edu.pacificu.cs493f15_1.paperorplasticapp.popList;

/**************************************************************************************************
 *   File:     DeleteKitchenListDFragment.java
 *   Author:   Lauren Sullivan
 *   Date:     10/28/15
 *   Class:    Capstone/Software Engineering
 *   Project:  PaperOrPlastic Application
 *   Purpose:  Takes care of the process of deleting a kitchen list. When user goes to kitchen
 *             settings and selects edit or swipes and deletes a list, a dialog box will appear
 *             asking if they are sure they want to delete the list.
 **************************************************************************************************/

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

/***************************************************************************************************
 *   Class:         DeletePoPListDFragment
 *   Description:   Takes care of the process of deleting a pop list. When user goes to either
 *                  Kitchen or Grocery settings and selects edit or swipes and deletes a list, a
 *                  dialog box will appear asking if they are sure they want to delete the list.
 *   Parameters:    N/A
 *   Returned:      N/A
 **************************************************************************************************/
public class DeletePoPListDFragment extends DialogFragment
{
    private Button mbCancel;
    private Button mbOK;
    private EditText mItemNameText;
    private Dialog mDialog;
    private DeleteListDialogListener mListener;

    public DeletePoPListDFragment() {
            // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.dfragment_delete_list, container,
                false);

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
                PoPListSettingsActivity activity = (PoPListSettingsActivity) getActivity(); //TODO Come back to this maybe if statements?
                DeleteListDialogListener listener = activity.getDeleteDialogListener();
                listener.onDeleted();
                mDialog.dismiss();
            }
        });

        mDialog = getDialog();

        mDialog.setTitle("Delete List");


        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        // Do something else
        return rootView;
    }

}
