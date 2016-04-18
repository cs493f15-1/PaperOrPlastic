/**************************************************************************************************
 *   File:     SettingsActivity.java
 *   Author:   Abigail Jones
 *   Date:     10/28/15
 *   Class:    Capstone/Software Engineering
 *   Project:  PaperOrPlastic Application
 *   Purpose:  This activity will be the activity that is opened when the user selects settings
 *             from the continue activity
 ***************************************************************************************************/

package edu.pacificu.cs493f15_1.paperorplasticapp.menu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.pacificu.cs493f15_1.paperorplasticapp.BaseActivity;
import edu.pacificu.cs493f15_1.paperorplasticapp.coupons.CouponsSettingsActivity;
import edu.pacificu.cs493f15_1.paperorplasticapp.nutrition.NutritionSettingsActivity;
import edu.pacificu.cs493f15_1.paperorplasticapp.R;
import edu.pacificu.cs493f15_1.paperorplasticapp.recipe.RecipesSettingsActivity;

/***************************************************************************************************
 *   Class:         SettingsActivity
 *   Description:   Creates SettingsActivity class that controls what occurs when the user
 *                  reaches the settings page. Specifically, from the settings page user can
 *                  select a specific settings page (such as recipes, nutrition, lits).
 *                  creates intents that take users to those specific settings pages.
 *   Parameters:    N/A
 *   Returned:      N/A
 **************************************************************************************************/
public class SettingsActivity extends BaseActivity implements View.OnClickListener
{
    static final int G_LIST = 0;
    static final int K_LIST = 1;
    static final int COUPONS = 2;
    static final int NUTRITION = 3;
    static final int RECIPES = 4;
    static final int HOME = 5;

    private List<Switch> switches;
    private List<Button> buttons;

    private static final int[] SWITCH_IDS = {R.id.bSettGList,   R.id.bSettKList,
                                             R.id.bSettCoupons, R.id.bSettNutrition,
                                             R.id.bSettRecipes};

    private static final int[] BUTTON_IDS = {R.id.bOpenGListSett,  R.id.bOpenKInvSett,
                                             R.id.bOpenCouponSett, R.id.bOpenNutritionSett,
                                             R.id.bOpenRecipeSett, R.id.bGoHome};

    public static final String[] SWITCH_PREF_KEYS = {"showGListOnContPg",  "showKInventoryOnContPg",
                                                     "showCouponOnContPg", "showNutritionOnContPg",
                                                     "showRecipeOnContPg"};

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

        //Create and initialize switches and load saved preferences
        switches = new ArrayList<Switch>(SWITCH_IDS.length);

        int count = 0;
        for (int id: SWITCH_IDS)
        {
            Switch theSwitch = (Switch) findViewById(id);
            theSwitch.setOnClickListener(this);
            switches.add(theSwitch);
            loadSavedPreferences(SWITCH_PREF_KEYS[count], count, ContinueActivity.buttons.get (count));
            count++;
        }
    }

    /***********************************************************************************************
    *   Method:      onClick
    *
    *   Description: Called when a click has been captured.
    *                Clicks can occur on toggles to turn settings pages on or off
    *                Users can also click on buttons to settings if toggle is on
    *
    *   Parameters:  view - the view that has been clicked
    *
    *   Returned:    N/A
    ***********************************************************************************************/
    public void onClick (View view)
    {

        //Handle the clicking of switches
        for (int i = G_LIST; i <= RECIPES; i++)
        {
            if (switches.get(i) == view)
            {
                saveSwitchPrefsAndSetContButtonVisibility(SWITCH_PREF_KEYS[i], view, i);
            }
        }

        //Handle the clicking of buttons

        if (buttons.get (G_LIST) == view)
        {

        }
        else if (buttons.get (K_LIST) == view)
        {
        }
        else if (buttons.get (NUTRITION) == view)
        {
            startIntent (NUTRITION, NutritionSettingsActivity.class);
        }
        else if (buttons.get (COUPONS) == view)
        {
            startIntent (COUPONS, CouponsSettingsActivity.class);
        }
        else if (buttons.get (RECIPES) == view)
        {
            startIntent (RECIPES, RecipesSettingsActivity.class);
        }
        else if (buttons.get (HOME) == view)
        {
            startIntent(HOME, ContinueActivity.class);
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
    private void saveSwitchPrefsAndSetContButtonVisibility (String switchPrefKey, View view, int index)
    {
        boolean bIsChecked = switches.get (index).isChecked ();

        if (bIsChecked)
        {
            buttons.get (index).setClickable(true);
        }
        else
        {
            buttons.get (index).setClickable(false);
        }

        ContinueActivity.buttons.get (index).setVisibility(View.VISIBLE);

        savePreferences(SWITCH_PREF_KEYS[index], bIsChecked);
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
            startActivity(intent);
        }
        else
        {
            //will start a new activity using the intents
            intent = new Intent (this, activityClass);
            startActivity (intent);
        }
    }

    public void loadSavedPreferences (String prefToLoad, int index, Button contActivityButton)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences (this);
        boolean switchIsChecked = sharedPreferences.getBoolean (prefToLoad, true);

        if (switchIsChecked)
        {
            switches.get (index).setChecked(true);
            contActivityButton.setVisibility(View.VISIBLE);
            buttons.get (index).setClickable(true);

        }
        else
        {
            switches.get (index).setChecked(false);
            contActivityButton.setVisibility(View.GONE);
            buttons.get (index).setClickable(false);
        }
    }

    private void savePreferences (String prefToSave, boolean bIsChecked)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences (this);
        SharedPreferences.Editor editor = sharedPreferences.edit ();
        editor.putBoolean (prefToSave, bIsChecked);
        editor.commit ();
    }



}

