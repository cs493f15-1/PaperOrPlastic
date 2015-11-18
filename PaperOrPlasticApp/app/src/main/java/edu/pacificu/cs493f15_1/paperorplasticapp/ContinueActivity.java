package edu.pacificu.cs493f15_1.paperorplasticapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by jone8832 on 10/26/2015.
 */
public class ContinueActivity extends Activity implements View.OnClickListener
{

    final int ALPHA_SETTING = 35;
    public static Button bGListButtonStatusFromSettings;
    public static Button bKListButtonStatusFromSettings;
    public static Button bNutritionButtonStatusFromSettings;
    public static Button bCouponsButtonStatusFromSettings;
    public static Button bRecipesButtonStatusFromSettings;

    private TextView titleText;

    private boolean visibilityFlag = true;


    //buttons
    private Button  mButtonLists,
                    mButtonSettings,
                    mButtonAbout,
                    mButtonGroceryList,
                    mButtonKitchenList,
                    mButtonNutrition,
                    mButtonCoupons,
                    mButtonRecipes;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue);

        titleText = (TextView) findViewById (R.id.PaperOrPlasticTitleText);
        Typeface laneUpperFont = Typeface.createFromAsset (getAssets (), "fonts/laneWUnderLine.ttf");
        Typeface laneNarrowFont = Typeface.createFromAsset (getAssets (), "fonts/LANENAR.ttf");
        titleText.setTypeface (laneUpperFont);

        //on click listener for buttons (connect to the view)
        mButtonLists = (Button) findViewById (R.id.bContLists);
        mButtonLists.setOnClickListener(this);

        mButtonSettings = (Button) findViewById (R.id.bContSettings);
        mButtonSettings.setOnClickListener(this);
        mButtonSettings.getBackground().setAlpha(ALPHA_SETTING);
        mButtonSettings.setTypeface(laneNarrowFont, Typeface.BOLD);

        mButtonAbout = (Button) findViewById (R.id.bContAbout);
        mButtonAbout.setOnClickListener(this);
        mButtonAbout.getBackground().setAlpha(ALPHA_SETTING);
        mButtonAbout.setTypeface(laneNarrowFont, Typeface.BOLD);

        mButtonGroceryList = (Button) findViewById (R.id.bContGList);
        mButtonGroceryList.setOnClickListener(this);
        mButtonGroceryList.getBackground().setAlpha(ALPHA_SETTING);
        mButtonGroceryList.setTypeface(laneNarrowFont, Typeface.BOLD);

        mButtonKitchenList = (Button) findViewById (R.id.bContKList);
        mButtonKitchenList.setOnClickListener(this);
        mButtonKitchenList.getBackground().setAlpha(ALPHA_SETTING);
        mButtonKitchenList.setTypeface(laneNarrowFont, Typeface.BOLD);

        mButtonNutrition = (Button) findViewById (R.id.bContNutrition);
        mButtonNutrition.setOnClickListener(this);
        mButtonNutrition.getBackground().setAlpha(ALPHA_SETTING);
        mButtonNutrition.setTypeface(laneNarrowFont, Typeface.BOLD);

        mButtonCoupons = (Button) findViewById (R.id.bContCoupons);
        mButtonCoupons.setOnClickListener(this);
        mButtonCoupons.getBackground().setAlpha(ALPHA_SETTING);
        mButtonCoupons.setTypeface(laneNarrowFont, Typeface.BOLD);

        mButtonRecipes = (Button) findViewById (R.id.bContRecipes);
        mButtonRecipes.setOnClickListener(this);
        mButtonRecipes.getBackground().setAlpha(ALPHA_SETTING);
        mButtonRecipes.setTypeface(laneNarrowFont, Typeface.BOLD);

        bGListButtonStatusFromSettings = (Button) findViewById (R.id.bContGList);
        bKListButtonStatusFromSettings = (Button) findViewById (R.id.bContKList);
        bNutritionButtonStatusFromSettings = (Button) findViewById (R.id.bContNutrition);
        bCouponsButtonStatusFromSettings = (Button) findViewById (R.id.bContCoupons);
        bRecipesButtonStatusFromSettings = (Button) findViewById (R.id.bContRecipes);

    }



    public void onSaveInstanceState (Bundle savedInstanceState)
    {
        super.onSaveInstanceState (savedInstanceState);
    }

    private void setVisible( )
    {
        visibilityFlag = true;

    }

    public void onClick (View view)
    {
        Intent intent;

        if (mButtonSettings == view)
        {
            //will start a new activity using the intents
            intent = new Intent (this, SettingsActivity.class);
            startActivity (intent);
        }


        if (mButtonAbout == view)
        {
            //will start a new activity using the intents
            intent = new Intent (this, AboutActivity.class);
            startActivity (intent);
        }

        if (mButtonGroceryList == view)
        {
            //will start a new activity using the intents
            intent = new Intent (this, GroceryListActivity.class);
            startActivity (intent);
        }

        if (mButtonKitchenList == view)
        {
            //will start a new activity using the intents
            intent = new Intent (this, KitchenListActivity.class);
            startActivity (intent);
        }

        if (mButtonNutrition == view)
        {
            //will start a new activity using the intents
            intent = new Intent (this, NutritionActivity.class);
            startActivity (intent);
        }

        if (mButtonCoupons == view)
        {
            //will start a new activity using the intents
            intent = new Intent (this, CouponsActivity.class);
            startActivity (intent);
        }

        if (mButtonRecipes == view)
        {
            //will start a new activity using the intents
            intent = new Intent (this, RecipesActivity.class);
            startActivity (intent);
        }
    }

}

