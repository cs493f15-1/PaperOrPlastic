package edu.pacificu.cs493f15_1.paperorplasticapp.popList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import edu.pacificu.cs493f15_1.paperorplasticapp.R;
import edu.pacificu.cs493f15_1.paperorplasticjava.ExecuteQueryTask;

import edu.pacificu.cs493f15_1.paperorplasticjava.ExecuteUPCScanTask;
import edu.pacificu.cs493f15_1.paperorplasticjava.GroceryList;
import edu.pacificu.cs493f15_1.paperorplasticjava.ListItem;
import edu.pacificu.cs493f15_1.paperorplasticjava.NutritionFactModel;
import edu.pacificu.cs493f15_1.paperorplasticjava.PoPLists;

import edu.pacificu.cs493f15_1.paperorplasticjava.ListItem;


public class ItemSearchActivity extends Activity implements ExecuteQueryTask.AsyncResponse {

    private EditText mItemSearchQuery;
    private ListView mItemListView;
    private String clickedName;
    private ListItem mItemResult;
    private ItemSearchAdapter mItemSearchAdapter;
    private ArrayList<ListItem> mSuggestions = new ArrayList<>();
    private JSONArray JSONResults;

    //For barcode Scanner
    private Button bScanButton;
    private TextView formatText, contentText;

    //For query nutritional fields
    public String[] mNutritionFields = new String[17];

    //Return from execute query task, receiving transfer item data for Suggestion List
    public ExecuteQueryTask.AsyncResponse response = new ExecuteQueryTask.AsyncResponse() {
        @Override
        public void processFinish(JSONArray output) {
            JSONResults = output;
        }
    };

    public ExecuteQueryTask queryTask = new ExecuteQueryTask(response);

    public ExecuteUPCScanTask upcScanTask = new ExecuteUPCScanTask();


    //Required because some bug
    @Override
    public void processFinish(JSONArray output) {
        JSONResults = output;
    }

    //After Barcode scan
    public void onActivityResult (int requestCode, int resultCode, Intent intent)
    {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult (requestCode, resultCode, intent);

        if (scanningResult != null)
        {
            String scanContent = scanningResult.getContents ();
            String scanFormat = scanningResult.getFormatName ();

            formatText.setText ("FORMAT: " + scanFormat);
            contentText.setText ("CONTENT: " + scanContent);
        }
        else
        {
            Toast toast = Toast.makeText (getApplicationContext (), "No scan data received!",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        bScanButton = (Button) findViewById (R.id.scan_button);

        bScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*IntentIntegrator scanIntegrator = new IntentIntegrator (ItemSearchActivity.this);

                scanIntegrator.initiateScan();*/

                upcScanTask.execute("49000036756");
            }
        });

        //For nutrtionix fields
        mNutritionFields[0] = "nf_calories";
        mNutritionFields[1] = "nf_total_fat";
        mNutritionFields[2] = "nf_saturated_fat";
        mNutritionFields[3] = "nf_total_carbohydrate";
        mNutritionFields[4] = "nf_protein";
        mNutritionFields[5] = "nf_sugars";
        mNutritionFields[6] = "nf_dietary_fiber";
        mNutritionFields[7] = "nf_sodium";
        mNutritionFields[8] = "nf_monounsaturated_fat";
        mNutritionFields[9] = "nf_polyunsaturated_fat";
        mNutritionFields[10] = "nf_trans_fatty_acid";
        mNutritionFields[11] = "nf_cholesterol";
        mNutritionFields[12] = "nf_potassium";
        mNutritionFields[13] = "nf_vitamin_a_dv";
        mNutritionFields[14] = "nf_vitamin_c_dv";
        mNutritionFields[15] = "nf_calcium_mg";
        mNutritionFields[16] = "nf_iron_mg";

        mItemListView = (ListView) findViewById(R.id.item_suggestion);

        // Get search field
        mItemSearchQuery = (EditText) findViewById(R.id.action_search);


        mItemSearchAdapter = (new ItemSearchAdapter(mItemListView.getContext(),
                R.layout.search_sug_item, mSuggestions));

        //For data exchange b/w I.S adapter, receiving.
        mItemSearchAdapter.setActivity(this);

        mItemListView.setAdapter(mItemSearchAdapter);

        // Show soft keyboard automatically and request focus to field
        mItemSearchQuery.requestFocus();

        //Executes everytime user types
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
                        queryTask.get(10, TimeUnit.SECONDS);
                    } catch (InterruptedException i) {
                        i.printStackTrace();
                    } catch (ExecutionException ee) {
                        ee.printStackTrace();
                    } catch (TimeoutException t) {
                        t.printStackTrace();
                    }

                    mItemSearchAdapter.clear();

                    /* Return of query. Putting into SearchArrayAdapter. All item info set here. */
                    if (null != JSONResults) {
                        int resultLength;

                        resultLength = JSONResults.length();

                        for (int i = 0; i < resultLength; i++) {
                            try {
                                JSONObject resultItems = JSONResults.getJSONObject(i);

                                JSONObject resultFields = resultItems.getJSONObject("fields");

                                for (int j = 0; j < 17; j++) {
                                    boolean bIsNull = false;

                                    bIsNull = resultFields.isNull(mNutritionFields[j]);

                                    if (bIsNull) {
                                        resultFields.put(mNutritionFields[j], 0);
                                    }
                                }

                                String itemName = resultFields.getString("item_name");
                                String brandName = resultFields.getString("brand_name");
                                String itemDesc = resultFields.getString("item_description");
                                int itemCal = resultFields.getInt("nf_calories");
                                double itemTotalFat = resultFields.getDouble("nf_total_fat");
                                double itemSatFat = resultFields.getDouble("nf_saturated_fat");
                                double itemPolyFat = resultFields.getDouble("nf_polyunsaturated_fat");
                                double itemMonoFat = resultFields.getDouble("nf_monounsaturated_fat");
                                double itemTransFat = resultFields.getDouble("nf_trans_fatty_acid");
                                double itemCholesterol = resultFields.getDouble("nf_cholesterol");
                                double itemSodium = resultFields.getDouble("nf_sodium");
                                double itemCarbs = resultFields.getDouble("nf_total_carbohydrate");
                                double itemFiber = resultFields.getDouble("nf_dietary_fiber");
                                double itemSugar = resultFields.getDouble("nf_sugars");
                                double itemProtein = resultFields.getDouble("nf_protein");
                                double itemPotassium = resultFields.getDouble("nf_potassium");
                                int itemVitA = resultFields.getInt("nf_vitamin_a_dv");
                                int itemVitC = resultFields.getInt("nf_vitamin_c_dv");
                                int itemCalcium = resultFields.getInt("nf_calcium_mg");
                                int itemIron = resultFields.getInt("nf_iron_mg");

                                mItemResult = new ListItem(itemName, brandName, itemDesc);

                                mItemResult.setNutritionFacts(itemCal, itemTotalFat,
                                        itemSatFat, itemPolyFat, itemMonoFat,
                                        itemTransFat, itemCholesterol, itemSodium,
                                        itemCarbs, itemFiber, itemSugar,
                                        itemProtein, itemPotassium, itemVitA,
                                        itemVitC, itemCalcium, itemIron);

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
    }

    //Sends item name and NF back to list activity
    public void sendItemNameToList(String itemName, String brandName, String itemDesc, NutritionFactModel itemNF) {
        Intent dataBack = new Intent();

        dataBack.putExtra("item_name", itemName);
        dataBack.putExtra("brand_name", brandName);
        dataBack.putExtra("item_desc", itemDesc);
        dataBack.putExtra("nf_calories", itemNF.getCalories());
        dataBack.putExtra("nf_total_fat", itemNF.getTotalFat());
        dataBack.putExtra("nf_saturated_fat", itemNF.getSatFat());
        dataBack.putExtra("nf_total_carbohydrate", itemNF.getTotalCarbs());
        dataBack.putExtra("nf_protein", itemNF.getProtein());
        dataBack.putExtra("nf_sugars", itemNF.getSugars());
        dataBack.putExtra("nf_dietary_fiber", itemNF.getFiber());
        dataBack.putExtra("nf_sodium", itemNF.getSodium());

        dataBack.putExtra("nf_polyunsaturated_fat", itemNF.getPolyFat());
        dataBack.putExtra("nf_monounsaturated_fat", itemNF.getMonoFat());
        dataBack.putExtra("nf_trans_fatty_acid", itemNF.getTransFat());
        dataBack.putExtra("nf_cholesterol", itemNF.getCholesterol());
        dataBack.putExtra("nf_potassium", itemNF.getPotassium() );
        dataBack.putExtra("nf_vitamin_a_dv", itemNF.getVitA());
        dataBack.putExtra("nf_vitamin_c_dv", itemNF.getVitC());
        dataBack.putExtra("nf_calcium_mg", itemNF.getCalcium());
        dataBack.putExtra("nf_iron_mg", itemNF.getIron());


        setResult(RESULT_OK, dataBack);
        finish();
    }


    /***********************************************************************************************
     *   Method:      onClick
     *
     *   Description: Called when a click has been captured.
     *
     *   Parameters:  view - the view that has been clicked
     *
     *   Returned:    N/A
     ***********************************************************************************************/
//    public void onClick (View view)
//    {
//
//        if (view == bScanButton)
//        {
//
//        }
//
//    }
}