/**************************************************************************************************
*   File:     ContinueActivity.java
*   Author:   Abigail Jones
*   Date:     10/28/15
*   Class:    Capstone/Software Engineering
*   Project:  PaperOrPlastic Application
*   Purpose:  This activity will be the activity that is opened when the user signs into the
*             application or when a user presses continue without signing in
***************************************************************************************************/

package edu.pacificu.cs493f15_1.paperorplasticapp.Menu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.pacificu.cs493f15_1.paperorplasticapp.Coupons.CouponsActivity;
import edu.pacificu.cs493f15_1.paperorplasticapp.GroceryList.GroceryListActivity;
import edu.pacificu.cs493f15_1.paperorplasticapp.KitchenInventory.KitchenListActivity;
import edu.pacificu.cs493f15_1.paperorplasticapp.Nutrition.NutritionActivity;
import edu.pacificu.cs493f15_1.paperorplasticapp.R;
import edu.pacificu.cs493f15_1.paperorplasticapp.Recipe.RecipesActivity;

/***************************************************************************************************
 *   Class:         ContinueActivity
 *   Description:   Creates ContinueActivity class that controls what occurs when the user
 *                  reaches the continue page. Controls the look of the continue page,
 *                  initializes buttons found on this page,
 *                  creates intents that take users to those specific pages.
 *   Parameters:    N/A
 *   Returned:      N/A
 **************************************************************************************************/
public class ContinueActivity extends Activity implements View.OnClickListener
{
    //Buttons found on continue page
    private Button  mButtonLists,
                    mButtonSettings,
                    mButtonAbout,
                    mButtonGroceryList,
                    mButtonKitchenList,
                    mButtonNutrition,
                    mButtonCoupons,
                    mButtonRecipes;

    //Button status used to control which buttons appears as specified by settings
    public static Button bGListButtonStatusFromSettings;
    public static Button bKListButtonStatusFromSettings;
    public static Button bNutritionButtonStatusFromSettings;
    public static Button bCouponsButtonStatusFromSettings;
    public static Button bRecipesButtonStatusFromSettings;

    //Used to specify weather a button should be seen or not as specified by the settings
    private boolean visibilityFlag = true;

    //Used to change fonts
    private TextView titleText;

    //Controls the transparency of buttons
    final int ALPHA_SETTING = 35;


    /***********************************************************************************************
    *   Method:        onCreate
    *   Description:   is called when the activity is created. Sets the content view and initializes
    *                  our buttons and text fields
    *   Parameters:    savedInstanceState
    *   Returned:      N/A
    ***********************************************************************************************/
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue);

        //Initialize text fields
        titleText = (TextView) findViewById (R.id.PaperOrPlasticTitleText);
        Typeface laneUpperFont = Typeface.createFromAsset (getAssets (), "fonts/laneWUnderLine.ttf");
        Typeface laneNarrowFont = Typeface.createFromAsset (getAssets (), "fonts/LANENAR.ttf");
        titleText.setTypeface (laneUpperFont);

        //Initialize buttons
        mButtonLists = (Button) findViewById (R.id.bContLists);
        mButtonLists.setOnClickListener(this);

//        initializeButtons (mButtonSettings, R.id.bContSettings, laneNarrowFont);

        mButtonSettings = (Button) findViewById (R.id.bContSettings);
        mButtonSettings.setOnClickListener(this);
        mButtonSettings.getBackground().setAlpha(ALPHA_SETTING);
        mButtonSettings.setTypeface(laneNarrowFont, Typeface.BOLD);

//        initializeButtons (mButtonAbout, R.id.bContAbout, laneNarrowFont);

        mButtonAbout = (Button) findViewById (R.id.bContAbout);
        mButtonAbout.setOnClickListener(this);
        mButtonAbout.getBackground().setAlpha(ALPHA_SETTING);
        mButtonAbout.setTypeface(laneNarrowFont, Typeface.BOLD);

//        initializeButtons (mButtonGroceryList, R.id.bContGList, laneNarrowFont);

        mButtonGroceryList = (Button) findViewById (R.id.bContGList);
        mButtonGroceryList.setOnClickListener(this);
        mButtonGroceryList.getBackground().setAlpha(ALPHA_SETTING);
        mButtonGroceryList.setTypeface(laneNarrowFont, Typeface.BOLD);


//        initializeButtons (mButtonKitchenList, R.id.bContKList, laneNarrowFont);

        mButtonKitchenList = (Button) findViewById (R.id.bContKList);
        mButtonKitchenList.setOnClickListener(this);
        mButtonKitchenList.getBackground().setAlpha(ALPHA_SETTING);
        mButtonKitchenList.setTypeface(laneNarrowFont, Typeface.BOLD);


//        initializeButtons (mButtonNutrition, R.id.bContNutrition, laneNarrowFont);

        mButtonNutrition = (Button) findViewById (R.id.bContNutrition);
        mButtonNutrition.setOnClickListener(this);
        mButtonNutrition.getBackground().setAlpha(ALPHA_SETTING);
        mButtonNutrition.setTypeface(laneNarrowFont, Typeface.BOLD);


//        initializeButtons (mButtonCoupons, R.id.bContCoupons, laneNarrowFont);

        mButtonCoupons = (Button) findViewById (R.id.bContCoupons);
        mButtonCoupons.setOnClickListener(this);
        mButtonCoupons.getBackground().setAlpha(ALPHA_SETTING);
        mButtonCoupons.setTypeface(laneNarrowFont, Typeface.BOLD);

//        initializeButtons (mButtonRecipes, R.id.bContRecipes, laneNarrowFont);


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


//    private void initializeButtons (Button button, int bButtonID, Typeface font)
//    {
//        button = (Button) findViewById (bButtonID);
//        button.setOnClickListener(this);
//        button.getBackground().setAlpha(ALPHA_SETTING);
//        button.setTypeface(font, Typeface.BOLD);
//    }

    /***********************************************************************************************
    *   Method:        onSaveInstanceState
    *   Description:   Called to retrieve per-instance state from an activity before being killed
    *                  so that the state can be restored in onCreate
    *   Parameters:    savedInstanceState
    *   Returned:      N/A
    ***********************************************************************************************/
    public void onSaveInstanceState (Bundle savedInstanceState)
    {
        super.onSaveInstanceState (savedInstanceState);
    }


    private void setVisible( )
    {
        visibilityFlag = true;
    }

    /***************************************************************************************************
    *   Method:      onClick
    *   Description: Called when a click has been captured.
    *                If the about, settings, kitchen lists, grocery list, nutrition, or coupon
    *                buttons are selected than a new intent is created and the specified activity
    *                is started
    *   Parameters:  view - the view that has been clicked
    *   Returned:    N/A
    ***************************************************************************************************/
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

