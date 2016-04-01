package edu.pacificu.cs493f15_1.paperorplasticapp.popList;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import edu.pacificu.cs493f15_1.paperorplasticapp.R;
import edu.pacificu.cs493f15_1.paperorplasticapp.groceryList.GroceryListActivity;
import edu.pacificu.cs493f15_1.paperorplasticapp.popList.ListItemAdapter;
import edu.pacificu.cs493f15_1.paperorplasticjava.ExecuteQueryTask;
import edu.pacificu.cs493f15_1.paperorplasticjava.GroceryList;
import edu.pacificu.cs493f15_1.paperorplasticjava.ListItem;
import edu.pacificu.cs493f15_1.paperorplasticjava.PoPLists;

public class ItemSearchActivity extends Activity implements ExecuteQueryTask.AsyncResponse {
    private EditText mItemSearchQuery;
    private Dialog mDialog;
    private PoPLists mPoPLists;
    private String mPoPFileName;
    private ListView mItemListView;
    private String clickedName;
    private ListItem mItemResult;
    private ItemSearchAdapter mItemSearchAdapter;
    private ArrayList<ListItem> mSuggestions = new ArrayList<>();
    private JSONArray JSONResults;

    public ExecuteQueryTask.AsyncResponse response = new ExecuteQueryTask.AsyncResponse() {
        @Override
        public void processFinish(JSONArray output) {
            JSONResults = output;
        }
    };

    public ExecuteQueryTask queryTask = new ExecuteQueryTask(response);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        mItemListView = (ListView) findViewById(R.id.item_suggestion);

        // Get search field
        mItemSearchQuery = (EditText) findViewById(R.id.action_search);

        //Testing items to show for suggestions
        mItemResult = new ListItem("Testing123");
        mSuggestions.add(mItemResult);

        mItemSearchAdapter = (new ItemSearchAdapter(mItemListView.getContext(),
                R.layout.search_sug_item, mSuggestions));

        //For selecting any search suggestion
        mItemSearchAdapter.setActivity(this);

        mItemListView.setAdapter(mItemSearchAdapter);

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
                    queryTask = new ExecuteQueryTask(response);

                    queryTask.execute(searchData, null, null);

                    try {
                        queryTask.get(5, TimeUnit.SECONDS);
                    } catch (InterruptedException i) {
                        i.printStackTrace();
                    } catch (ExecutionException ee) {
                        ee.printStackTrace();
                    } catch (TimeoutException t) {
                        t.printStackTrace();
                    }

                    mItemSearchAdapter.clear();

                    if (null != JSONResults) {
                        for (int i = 0; i < 5; i++) {
                            try {
                                JSONObject resultItems = JSONResults.getJSONObject(i);

                                JSONObject resultFields = resultItems.getJSONObject("fields");

                                String itemName = resultFields.getString("item_name");

                                mItemResult = new ListItem(itemName);
                                mItemSearchAdapter.add(mItemResult);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }

                    mItemSearchAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Search key is pressed
        mItemSearchQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String item_name;
                    Intent intent = getIntent();

                    int numListItems = intent.getIntExtra("num_list_items", 0);

                    if (0 < numListItems) {
                        item_name = mItemSearchQuery.getText().toString();
                        sendItemNameToList(item_name);

                    } else {
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

//        mDialog = getDialog();
//        mDialog.setTitle("Add Item");
//        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//        // Do something else
//
//
//        mbCancel = (Button) rootView.findViewById(R.id.cancel_button);
//        mbCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDialog.dismiss();
//            }
//        });
//
//
//
//        mbOK = (Button) rootView.findViewById (R.id.ok_button);
//        mbOK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                GroceryListActivity activity = (GroceryListActivity) getActivity();
//                ItemSearchDialogListener listener = activity.getItemInfoListener();
//                listener.onFinishNewItemDialog(mItemSearchQuery.getText().toString());
//                mDialog.dismiss();
//            }
//        });
//
    }

    @Override
    public void processFinish(JSONArray output) {
        JSONResults = output;
    }

    public void sendItemNameToList(String itemName) {
        Intent dataBack = new Intent();
        dataBack.putExtra("item_name", itemName);
        setResult(RESULT_OK, dataBack);
        finish();
    }
}