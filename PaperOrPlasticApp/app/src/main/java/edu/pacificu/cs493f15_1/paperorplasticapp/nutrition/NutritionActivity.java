/**************************************************************************************************
<<<<<<< HEAD
 *   File:     NutritionActivity.java
 *   Author:   Kevin Jo
 *   Date:     10/28/15
 *   Class:    Capstone/Software Engineering
 *   Project:  PaperOrPlastic Application
 *   Purpose:  This activity will be the activity that is opened when the user selects the
 *             nutrition button from the continue activity
=======
 * File:     NutritionActivity.java
 * Author:   Abigail Jones
 * Date:     10/28/15
 * Class:    Capstone/Software Engineering
 * Project:  PaperOrPlastic Application
 * Purpose:  This activity will be the activity that is opened when the user selects the
 * nutrition button from the continue activity
>>>>>>> upstream/master
 ***************************************************************************************************/

package edu.pacificu.cs493f15_1.paperorplasticapp.nutrition;


import edu.pacificu.cs493f15_1.paperorplasticapp.BaseActivity;
import edu.pacificu.cs493f15_1.paperorplasticapp.R;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import edu.pacificu.cs493f15_1.paperorplasticjava.NutritionFactModel;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/***************************************************************************************************
 *   Class:         NutritionActivity
 *   Description:   Creates NutritionActivity class that controls what occurs when the user
 *                  selects the nutrition option from the continue activity.
 *   Parameters:    N/A
 *   Returned:      N/A
 **************************************************************************************************/
public class NutritionActivity extends BaseActivity {

    private TextView mItemName, mBrandName, mCalories, mTotalFats, mSatFat, mPolyFat, mMonoFat,
            mTransFat, mCholesterol, mSodium, mPotassium, mTotalCarbs, mFiber, mSugars, mProtein,
            mVitA, mVitC, mCalcium, mIron;

    private NutritionFactModel mNutrition;

    /********************************************************************************************
     * Function name: onCreate
     * <p/>
     * Description:   Initializes all needed setup for objects in page
     * <p/>
     * Parameters:    savedInstanceState  - a bundle object
     * <p/>
     * Returns:       none
     ******************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition);


        //Setting up Text fields for Nutr. values
        mNutrition = new NutritionFactModel();

        mItemName = (TextView) findViewById(R.id.itemName);
        mBrandName = (TextView) findViewById(R.id.brandName);
        mCalories = (TextView) findViewById(R.id.Calories);
        mTotalFats = (TextView) findViewById(R.id.Fats);
        mSatFat = (TextView) findViewById(R.id.SatFat);
        mPolyFat = (TextView) findViewById(R.id.PolySatFat);
        mMonoFat = (TextView) findViewById(R.id.MonoSatFat);
        mTransFat = (TextView) findViewById(R.id.TransFat);
        mCholesterol = (TextView) findViewById(R.id.Chol);
        mSodium = (TextView) findViewById(R.id.Sodium);
        mPotassium = (TextView) findViewById(R.id.Potassium);
        mTotalCarbs = (TextView) findViewById(R.id.TotalCarbs);
        mFiber = (TextView) findViewById(R.id.Fiber);
        mSugars = (TextView) findViewById(R.id.Sugars);
        mProtein = (TextView) findViewById(R.id.Protein);
        mVitA = (TextView) findViewById(R.id.VitA);
        mVitC = (TextView) findViewById(R.id.VitC);
        mCalcium = (TextView) findViewById(R.id.Calcium);
        mIron = (TextView) findViewById(R.id.Iron);

        Intent nutrIntent = getIntent();

        mNutrition.setAll(nutrIntent.getIntExtra("nf_calories", 0),
                nutrIntent.getDoubleExtra("nf_total_fat", 0.0),
                nutrIntent.getDoubleExtra("nf_saturated_fat", 0.0),
                nutrIntent.getDoubleExtra("nf_polyunsaturated_fat", 0.0),
                nutrIntent.getDoubleExtra("nf_monounsaturated_fat", 0.0),
                nutrIntent.getDoubleExtra("nf_trans_fatty_acid", 0.0),
                nutrIntent.getDoubleExtra("nf_cholesterol", 0.0),
                nutrIntent.getDoubleExtra("nf_sodium", 0.0),
                nutrIntent.getDoubleExtra("nf_total_carbohydrate", 0.0),
                nutrIntent.getDoubleExtra("nf_dietary_fiber", 0.0),
                nutrIntent.getDoubleExtra("nf_sugars", 0.0),
                nutrIntent.getDoubleExtra("nf_protein", 0.0),
                nutrIntent.getDoubleExtra("nf_potassium", 0.0),
                nutrIntent.getIntExtra("nf_vitamin_a_dv", 0),
                nutrIntent.getIntExtra("nf_vitamin_c_dv", 0),
                nutrIntent.getIntExtra("nf_calcium_dv", 0),
                nutrIntent.getIntExtra("nf_iron_dv", 0)
        );

        mBrandName.setText(String.format("%1$s", nutrIntent.getStringExtra("brand_name")));
        mItemName.setText(String.format("%1$s", nutrIntent.getStringExtra("item_name")));

        mCalories.setText(String.format("%1$s", mNutrition.getCalories()));
        mTotalFats.setText(String.format("%1$s" + "g", mNutrition.getTotalFat()));
        mSatFat.setText(String.format("%1$s" + "g", mNutrition.getSatFat()));
        mPolyFat.setText(String.format("%1$s" + "g", mNutrition.getPolyFat()));
        mMonoFat.setText(String.format("%1$s" + "g", mNutrition.getMonoFat()));
        mTransFat.setText(String.format("%1$s" + "g", mNutrition.getTransFat()));
        mCholesterol.setText(String.format("%1$s" + "mg", mNutrition.getCholesterol()));
        mSodium.setText(String.format("%1$s" + "mg", mNutrition.getSodium()));
        mPotassium.setText(String.format("%1$s" + "mg", mNutrition.getPotassium()));
        mTotalCarbs.setText(String.format("%1$s" + "g", mNutrition.getTotalCarbs()));
        mFiber.setText(String.format("%1$s" + "g", mNutrition.getFiber()));
        mSugars.setText(String.format("%1$s" + "g", mNutrition.getSugars()));
        mProtein.setText(String.format("%1$s" + "g", mNutrition.getProtein()));

        //To solve string formatting problems with the "%" char
        String vitAText = String.format("%1$s", mNutrition.getVitA()) + "%";
        String vitCText = String.format("%1$s", mNutrition.getVitC()) + "%";

        mVitA.setText(vitAText);
        mVitC.setText(vitCText);
        mCalcium.setText(String.format("%1$s" + "mg", mNutrition.getCalcium()));
        mIron.setText(String.format("%1$s" + "mg", mNutrition.getIron()));


        //bScanButton = (Button) findViewById (R.id.scan_button);
        //formatText = (TextView) findViewById (R.id.scan_format);
        //contentText = (TextView) findViewById (R.id.scan_content);

        //bScanButton.setOnClickListener (this);


    }
}
