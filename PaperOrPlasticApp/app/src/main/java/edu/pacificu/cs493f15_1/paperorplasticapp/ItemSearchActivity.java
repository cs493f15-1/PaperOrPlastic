package edu.pacificu.cs493f15_1.paperorplasticapp;

import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.pacificu.cs493f15_1.paperorplasticjava.ExecuteQueryTask;
import edu.pacificu.cs493f15_1.paperorplasticjava.GroceryList;
import edu.pacificu.cs493f15_1.paperorplasticjava.ListItem;
import edu.pacificu.cs493f15_1.paperorplasticjava.PoPList;

/**
 * Created by heyd5159 on 11/18/2015.
 */
public class ItemSearchActivity extends DialogFragment
{
    private Button mbCancel;
    private Button mbOK;
    private EditText mItemSearchQuery;
    private Dialog mDialog;
    private ListView mSearchSuggestion;
    private ListItem mItemResult;
    private String mJSONItemResult;
    private ListItemAdapter ListItemAdapter1;
    private GroceryList gList;


    public ItemSearchActivity() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.activity_new_item, container,
                false);

        mDialog = getDialog();

        gList = new GroceryList ("Test");

        //mSearchSuggestion = (ListView) rootView.findViewById(R.id.itemSearchList);

        // Get field from view
        mItemSearchQuery = (EditText) rootView.findViewById(R.id.action_search);

        mItemResult = new ListItem("Testing123");

        gList.addItem(mItemResult);

        //ListItemAdapter1 = new ListItemAdapter (mSearchSuggestion.getContext(), R.layout.activity_new_item, gList.getItemArray());

        //mSearchSuggestion.setAdapter(ListItemAdapter1);

       // addListAdapter(gList);
        //mSearchSuggestion.setAdapter(mListAdapters.get(0));
        //addItemToListView (mItemResult);





        // Show soft keyboard automatically and request focus to field
        mItemSearchQuery.requestFocus();

        mItemSearchQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (null != s)
                {
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


        mItemSearchQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String searchData = mItemSearchQuery.getText().toString();

                    //Executes the QueryTask.
                    ExecuteQueryTask queryTask = new ExecuteQueryTask();
                    queryTask.execute(searchData, null, mJSONItemResult);

                    return true;
                }

                return false;
            }
        });


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
                NewItemInfoDialogListener listener = activity.getItemInfoListener();
                //listener.onFinishNewItemDialog(mItemSearchQuery.getText().toString());
                mDialog.dismiss();
            }


        });



        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        // Do something else

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