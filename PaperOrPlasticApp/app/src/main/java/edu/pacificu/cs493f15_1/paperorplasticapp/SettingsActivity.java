/**************************************************************************************************
 *   File:     SettingsActivity.java
 *   Author:   Abigail Jones
 *   Date:     10/28/15
 *   Class:    Capstone/Software Engineering
 *   Project:  PaperOrPlastic Application
 *   Purpose:  This activity will be the activity that is opened when the user selects settings
 *             from the continue activity
 ***************************************************************************************************/

package edu.pacificu.cs493f15_1.paperorplasticapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/***************************************************************************************************
 *   Class:         SettingsActivity
 *   Description:   Creates SettingsActivity class that controls what occurs when the user
 *                  reaches the settings page. Specifically, from the settings page user can
 *                  select a specific settings page (such as recipes, nutrition, lits).
 *                  creates intents that take users to those specific settings pages.
 *   Parameters:    N/A
 *   Returned:      N/A
 **************************************************************************************************/
public class SettingsActivity extends Activity implements View.OnClickListener
{
    //buttons
    private Button  mButtonGroceryList,
                    mButtonKitchenList,
                    mButtonNutrition,
                    mButtonCoupons,
                    mButtonRecipes;
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView(R.layout.activity_settings);

        mButtonGroceryList = (Button) findViewById (R.id.bSettGList);
        mButtonGroceryList.setOnClickListener (this);

        mButtonKitchenList = (Button) findViewById (R.id.bSettKList);
        mButtonKitchenList.setOnClickListener (this);

        mButtonNutrition = (Button) findViewById (R.id.bSettNutrition);
        mButtonNutrition.setOnClickListener (this);

        mButtonCoupons = (Button) findViewById (R.id.bSettCoupons);
        mButtonCoupons.setOnClickListener (this);

        mButtonRecipes = (Button) findViewById (R.id.bSettRecipes);
        mButtonRecipes.setOnClickListener (this);
    }


    public void onClick (View view)
    {
        Intent intent;

        if (mButtonGroceryList == view)
        {
            //will start a new activity using the intents
            intent = new Intent (this, GroceryListSettingsActivity.class);
            intent.putExtra("Caller", "SettingsActivity");
            startActivity (intent);
        }

        if (mButtonKitchenList == view)
        {
            //will start a new activity using the intents
            intent = new Intent (this, KitchenListSettingsActivity.class);
            intent.putExtra("Caller", "SettingsActivity");
            startActivity (intent);
        }

        if (mButtonNutrition == view)
        {
            //will start a new activity using the intents
            intent = new Intent (this, NutritionSettingsActivity.class);
            startActivity (intent);
        }

        if (mButtonCoupons == view)
        {
            //will start a new activity using the intents
            intent = new Intent (this, CouponsSettingsActivity.class);
            startActivity (intent);
        }

        if (mButtonRecipes == view)
        {
            //will start a new activity using the intents
            intent = new Intent (this, RecipesSettingsActivity.class);
            startActivity (intent);
        }


    }
}

