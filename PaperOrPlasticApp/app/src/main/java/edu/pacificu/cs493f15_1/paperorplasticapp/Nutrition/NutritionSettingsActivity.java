/**************************************************************************************************
 *   File:     NutritionSettingsActivity.java
 *   Author:   Abigail Jones
 *   Date:     10/28/15
 *   Class:    Capstone/Software Engineering
 *   Project:  PaperOrPlastic Application
 *   Purpose:  This activity will be the activity that is opened when the user chooses to
 *             look at the nutrition settings. This can happen through the settings button on the
 *             continue activity or through the settings tab when the nutrition button is pressed
 *             from the continue activity.
 ***************************************************************************************************/

package edu.pacificu.cs493f15_1.paperorplasticapp.Nutrition;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.pacificu.cs493f15_1.paperorplasticapp.Menu.ContinueActivity;
import edu.pacificu.cs493f15_1.paperorplasticapp.R;

/***************************************************************************************************
 *   Class:         NutritionSettingsActivity
 *   Description:   Creates NutritionSettingsActivity class that controls what occurs when the
 *                  user reaches the nutrition settings page. Specifically, handles what happens
 *                  when the user specifies whether the nutrition button should be displayed on
 *                  the continue activity.
 *   Parameters:    N/A
 *   Returned:      N/A
 **************************************************************************************************/
public class NutritionSettingsActivity extends Activity implements View.OnClickListener
{
    private Button mButtonShowNutritionList;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition_settings);

        mButtonShowNutritionList = (Button) findViewById (R.id.bShowNutritionList);
        mButtonShowNutritionList.setOnClickListener(this);
    }

    public void onClick (View view)
    {
        if (mButtonShowNutritionList == view)
        {
            if (ContinueActivity.bNutritionButtonStatusFromSettings.getVisibility() == View.VISIBLE)
            {
                ContinueActivity.bNutritionButtonStatusFromSettings.setVisibility(View.GONE);
            }
            else if (ContinueActivity.bNutritionButtonStatusFromSettings.getVisibility() == View.GONE)
            {
                ContinueActivity.bNutritionButtonStatusFromSettings.setVisibility(View.VISIBLE);
            }
        }
    }
}
