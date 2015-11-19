package edu.pacificu.cs493f15_1.paperorplasticapp;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by jone8832 on 10/26/2015.
 */
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
            startActivity (intent);
        }

        if (mButtonKitchenList == view)
        {
            //will start a new activity using the intents
            intent = new Intent (this, KitchenListSettingsActivity.class);
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

