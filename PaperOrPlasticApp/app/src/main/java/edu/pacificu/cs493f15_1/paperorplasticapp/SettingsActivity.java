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
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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
    static final int G_LIST = 0;
    static final int K_LIST = 1;
    static final int COUPONS = 2;
    static final int NUTRITION = 3;
    static final int RECIPES = 4;

    private List<Button> switches;
    private List<Button> buttons;

    private static final int[] SWITCH_IDS = {R.id.bSettGList,   R.id.bSettKList,
                                             R.id.bSettCoupons, R.id.bSettNutrition,
                                             R.id.bSettRecipes};

    private static final int[] BUTTON_IDS = {R.id.bOpenGListSett,  R.id.bOpenKListSett,
                                             R.id.bOpenCouponSett, R.id.bOpenNutritionSett,
                                             R.id.bOpenRecipeSett};

    //Used to change fonts
    private TextView titleText;

    //Controls the transparency of buttons
    final int ALPHA_SETTING = 35;


    /********************************************************************************************
     * Function name: onCreate
     *
     * Description:   Initializes all needed setup for objects in page
     *
     * Parameters:    savedInstanceState  - a bundle object
     *
     * Returns:       none
     ******************************************************************************************/
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Initialize text fields
        titleText = (TextView) findViewById (R.id.SettingsPageTitleText);

        Typeface laneUpperFont = Typeface.createFromAsset (getAssets (), "fonts/laneWUnderLine.ttf");
        Typeface laneNarrowFont = Typeface.createFromAsset (getAssets (), "fonts/LANENAR.ttf");
        titleText.setTypeface(laneUpperFont);

        //Create and initialize buttons
        buttons = new ArrayList<Button>(BUTTON_IDS.length);

        for (int id: BUTTON_IDS)
        {
            Button button = (Button) findViewById(id);
            button.setOnClickListener(this);
            button.getBackground().setAlpha(ALPHA_SETTING);
            button.setTypeface(laneNarrowFont, Typeface.BOLD);
            buttons.add(button);
        }

        //Create and initialize switches
        switches = new ArrayList<Button>(SWITCH_IDS.length);

        for (int id: SWITCH_IDS)
        {
            Button buttonSwitches = (Button) findViewById(id);
            buttonSwitches.setOnClickListener(this);
            switches.add(buttonSwitches);
        }
    }

    /***********************************************************************************************
    *   Method:      onClick
    *
    *   Description: Called when a click has been captured.
    *                Clicks can occur on toggle switches to turn settings pages on or off
    *                Users can also click on buttons to settings if toggle is on
    *
    *   Parameters:  view - the view that has been clicked
    *
    *   Returned:    N/A
    ***********************************************************************************************/
    public void onClick (View view)
    {
        /*********************GROCERY LIST SETTINGS********************************************/

        if (switches.get (G_LIST) == view)
        {
            checkAndSetSettingsSwitchesAndButtons (view, ContinueActivity.bGListButtonStatusFromSettings, G_LIST);
       }

        if (buttons.get (G_LIST) == view)
        {
            startIntent (G_LIST, GroceryListSettingsActivity.class);
        }

        /*********************KITCHEN INVENTORY SETTINGS***************************************/


        if (switches.get (K_LIST) == view)
        {
            checkAndSetSettingsSwitchesAndButtons (view, ContinueActivity.bKListButtonStatusFromSettings, K_LIST);
        }

        if (buttons.get (K_LIST) == view)
        {
            startIntent (K_LIST, KitchenListSettingsActivity.class);
        }

        /*********************NUTRITION SETTINGS***********************************************/

        if (switches.get (NUTRITION) == view)
        {
            checkAndSetSettingsSwitchesAndButtons (view, ContinueActivity.bNutritionButtonStatusFromSettings, NUTRITION);
        }

        if (buttons.get (NUTRITION) == view)
        {
            startIntent (NUTRITION, NutritionSettingsActivity.class);
        }

        /*********************COUPON SETTINGS**************************************************/

        if (switches.get (COUPONS) == view)
        {
            checkAndSetSettingsSwitchesAndButtons (view, ContinueActivity.bCouponsButtonStatusFromSettings, COUPONS);
        }

        if (buttons.get (COUPONS) == view)
        {
            startIntent (COUPONS, CouponsSettingsActivity.class);
        }

        /*********************RECIPE SETTINGS**************************************************/

        if (switches.get (RECIPES) == view)
        {
            checkAndSetSettingsSwitchesAndButtons (view, ContinueActivity.bRecipesButtonStatusFromSettings, RECIPES);
        }

        if (buttons.get (RECIPES) == view)
        {
            startIntent (RECIPES, RecipesSettingsActivity.class);
        }
    }

    /***********************************************************************************************
     *   Method:      checkAndSetSettingsSwitchesAndButtons
     *
     *   Description: Checks the status of the toggle switch and allows users to navigate to settings
     *                pages based on status of toggle switch
     *
     *   Parameters:  view               - the view that has been clicked
     *                statusFromSettings - Status of toggle switch
     *                index              - Index into button and switch arrays
     *
     *   Returned:    N/A
     ***********************************************************************************************/
    private void checkAndSetSettingsSwitchesAndButtons (View view, Button statusFromSettings, int index)
    {
            if (statusFromSettings.getVisibility() == View.VISIBLE)
            {
                statusFromSettings.setVisibility(View.GONE);
                buttons.get (index).setClickable(false);
            }
            else if (statusFromSettings.getVisibility() == View.GONE)
            {
                statusFromSettings.setVisibility(View.VISIBLE);
                buttons.get(index).setClickable(true);
            }
    }

    /***********************************************************************************************
     *   Method:      startIntent
     *
     *   Description: Creates intent and starts new activity when user is allowed and does click
     *                on individual settings pages
     *
     *   Parameters:  index - Index into button and switch arrays that specifies which clicked
     *                activityClass - The activity to be opened
     *
     *   Returned:    N/A
     ***********************************************************************************************/
    private void startIntent (int index, Class activityClass)
    {
        Intent intent;

        if (G_LIST == index || K_LIST == index)
        {
            //will start a new activity using the intents
            intent = new Intent (this, activityClass);
            intent.putExtra("Caller", "SettingsActivity");
            startActivity (intent);
        }
        else
        {
            //will start a new activity using the intents
            intent = new Intent (this, activityClass);
            startActivity (intent);
        }
    }
}

