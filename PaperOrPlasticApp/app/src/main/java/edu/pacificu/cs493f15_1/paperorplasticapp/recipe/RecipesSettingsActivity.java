/**************************************************************************************************
 *   File:     RecipesSettingsActivity.java
 *   Author:   Abigail Jones
 *   Date:     10/28/15
 *   Class:    Capstone/Software Engineering
 *   Project:  PaperOrPlastic Application
 *   Purpose:  This activity will be the activity that is opened when the user chooses to
 *             look at the recipes settings. This can happen through the settings button on the
 *             continue activity or through the settings tab when the recipes button is pressed
 *             from the continue activity.
 ***************************************************************************************************/

package edu.pacificu.cs493f15_1.paperorplasticapp.recipe;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.pacificu.cs493f15_1.paperorplasticapp.menu.ContinueActivity;
import edu.pacificu.cs493f15_1.paperorplasticapp.R;

/***************************************************************************************************
 *   Class:         RecipesSettingsActivity
 *   Description:   Creates RecipesSettingsActivity class that controls what occurs when the
 *                  user reaches the recipes settings page. Specifically, handles what happens
 *                  when the user specifies whether the recipes button should be displayed on
 *                  the continue activity.
 *   Parameters:    N/A
 *   Returned:      N/A
 **************************************************************************************************/
public class RecipesSettingsActivity extends Activity implements View.OnClickListener
{
    //Used to change fonts
    private TextView titleText;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_settings);

        titleText = (TextView) findViewById (R.id.RecipeSettingsTitleText);

        Typeface laneUpperFont = Typeface.createFromAsset (getAssets (), "fonts/laneWUnderLine.ttf");

        titleText.setTypeface(laneUpperFont);
    }

    public void onClick (View view)
    {

    }
}
