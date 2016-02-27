package edu.pacificu.cs493f15_1.paperorplasticapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.pacificu.cs493f15_1.paperorplasticjava.ExecuteQueryTask;
import edu.pacificu.cs493f15_1.paperorplasticjava.GroceryList;
import edu.pacificu.cs493f15_1.paperorplasticjava.ListItem;

public class ItemSearchDialog extends DialogFragment
{
    private EditText mItemSearchQuery;
    private Dialog mDialog;
    private ListView mItemListView;
    private String mJSONItemResult;
    private ListItemAdapter mListItemAdapter;
    private ListItem mItemResult;

    private ArrayList<ListItem> mSuggestions = new ArrayList<ListItem>();

    public ItemSearchDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.activity_new_item, container,
                false);

        mItemListView = (ListView) rootView.findViewById(R.id.itemListView);

        // Get field from view
        mItemSearchQuery = (EditText) rootView.findViewById(R.id.action_search);

        mItemResult = new ListItem("Testing123");

        //gList.addItem(mItemResult);

        mSuggestions.add(mItemResult);

        mListItemAdapter = (new ListItemAdapter(mItemListView.getContext(),
                R.layout.search_sug_item, mSuggestions));

        mItemListView.setAdapter(mListItemAdapter);

        // Show soft keyboard automatically and request focus to field
        mItemSearchQuery.requestFocus();

        mItemSearchQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (null != s) {
                    String searchData = mItemSearchQuery.getText().toString();

                    //Executes the QueryTask.
                    ExecuteQueryTask queryTask = new ExecuteQueryTask();
                    queryTask.execute(searchData, null, null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Search key is pressed
        mItemSearchQuery.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH)
                {
                    GroceryListActivity activity = (GroceryListActivity) getActivity();

                    if (0 < activity.getNumGList())
                    {
                        ItemSearchDialogListener listener = activity.getItemInfoListener();
                        listener.onFinishNewItemDialog(mItemSearchQuery.getText().toString());
                        mDialog.dismiss();
                    }

                    else
                    {
                        AlertDialog noListDialog = new AlertDialog.Builder(mDialog.getContext()).create();
                        noListDialog.setTitle("Item Not Added");
                        noListDialog.setMessage("The item could not be added because there is no list available. Please create a list first.");
                        noListDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                        noListDialog.show();
                    }

                    return true;
                }

                return false;
            }
        });

        mDialog = getDialog();
        mDialog.setTitle("Add Item");
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // Do something else


        /*
        mbCancel = (Button) rootView.findViewById(R.id.cancel_button);
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
                GroceryListActivity activity = (GroceryListActivity) getActivity();
                ItemSearchDialogListener listener = activity.getItemInfoListener();
                listener.onFinishNewItemDialog(mItemSearchQuery.getText().toString());
                mDialog.dismiss();
            }
        });
        */



        return rootView;
        }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        //mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);*/
        /*
        mSearchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setIconifiedByDefault(false);

        System.out.println("TESTING:    " + mSearchView);
        */

        //return super.onCreateOptionsMenu(menu);
    //}


}