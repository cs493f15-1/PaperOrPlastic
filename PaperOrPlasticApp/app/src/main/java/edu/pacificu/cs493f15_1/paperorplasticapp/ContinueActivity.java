/**************************************************************************************************
*   File:     ContinueActivity.java
*   Author:   Abigail Jones
*   Date:     10/28/15
*   Class:    Capstone/Software Engineering
*   Project:  PaperOrPlastic Application
*   Purpose:  This activity will be the activity that is opened when the user signs into the
*             application or when a user presses continue without signing in
***************************************************************************************************/

package edu.pacificu.cs493f15_1.paperorplasticapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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
//    //Buttons found on continue page
//   // static final int NUM_CONT_BUTTONS = 7;
//    //Button buttons[] = new Button[NUM_CONT_BUTTONS];
//
//    private enum Buttons { G_LIST,  }

    static final int G_LIST = 0;
    static final int K_LIST = 1;
    static final int COUPONS = 2;
    static final int NUTRITION = 3;
    static final int RECIPIES = 4;
    static final int SETTINGS = 5;
    static final int ABOUT = 6;

    private List<Button> buttons;

    private static final int[] BUTTON_IDS = {R.id.bContGList,   R.id.bContKList,
                                             R.id.bContCoupons, R.id.bContNutrition,
                                             R.id.bContRecipes, R.id.bContSettings, R.id.bContAbout};


    //Button status used to control which buttons appears as specified by settings
    public static Button bGListButtonStatusFromSettings;
    public static Button bKListButtonStatusFromSettings;
    public static Button bNutritionButtonStatusFromSettings;
    public static Button bCouponsButtonStatusFromSettings;
    public static Button bRecipesButtonStatusFromSettings;

    //Used to specify whether a button should be seen or not as specified by the settings
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
        titleText = (TextView) findViewById (R.id.SettingsPageTitleText);
        Typeface laneUpperFont = Typeface.createFromAsset (getAssets (), "fonts/laneWUnderLine.ttf");
        Typeface laneNarrowFont = Typeface.createFromAsset (getAssets (), "fonts/LANENAR.ttf");
        titleText.setTypeface (laneUpperFont);

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

        //Determine whether or not buttons should be shown based on settings preferences
        bGListButtonStatusFromSettings = (Button) findViewById (BUTTON_IDS[G_LIST]);
        bKListButtonStatusFromSettings = (Button) findViewById (BUTTON_IDS[K_LIST]);
        bCouponsButtonStatusFromSettings = (Button) findViewById (BUTTON_IDS[COUPONS]);
        bNutritionButtonStatusFromSettings = (Button) findViewById (BUTTON_IDS[NUTRITION]);
        bRecipesButtonStatusFromSettings = (Button) findViewById (BUTTON_IDS[RECIPIES]);
    }


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

    /***********************************************************************************************
    *   Method:      onClick
    *   Description: Called when a click has been captured.
    *                If the about, settings, kitchen lists, grocery list, nutrition, or coupon
    *                buttons are selected than a new intent is created and the specified activity
    *                is started
    *   Parameters:  view - the view that has been clicked
    *   Returned:    N/A
    ***********************************************************************************************/
    public void onClick (View view)
    {
        Intent intent;

        if (buttons.get (G_LIST) == view)
        {
            //will start a new activity using the intents
            intent = new Intent (this, GroceryListActivity.class);
            startActivity (intent);
        }

        if (buttons.get (K_LIST) == view)
        {
            //will start a new activity using the intents
            intent = new Intent (this, KitchenListActivity.class);
            startActivity (intent);
        }

        if (buttons.get (COUPONS) == view)
        {
            //will start a new activity using the intents
            intent = new Intent (this, CouponsActivity.class);
            startActivity (intent);
        }

        if (buttons.get (NUTRITION) == view)
        {
            //will start a new activity using the intents
            intent = new Intent (this, NutritionActivity.class);
            startActivity (intent);
        }

        if (buttons.get (RECIPIES) == view)
        {
            //will start a new activity using the intents
            intent = new Intent (this, RecipesActivity.class);
            startActivity (intent);
        }

        if (buttons.get (SETTINGS) == view)
        {
            //will start a new activity using the intents
            intent = new Intent (this, SettingsActivity.class);
            startActivity (intent);
        }

        if (buttons.get (ABOUT) == view)
        {
            //will start a new activity using the intents
            intent = new Intent (this, AboutActivity.class);
            startActivity (intent);
        }
    }
}

