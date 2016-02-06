/**************************************************************************************************
 *   File:     NutritionActivity.java
 *   Author:   Abigail Jones
 *   Date:     10/28/15
 *   Class:    Capstone/Software Engineering
 *   Project:  PaperOrPlastic Application
 *   Purpose:  This activity will be the activity that is opened when the user selects the
 *             nutrition button from the continue activity
 ***************************************************************************************************/

package edu.pacificu.cs493f15_1.paperorplasticapp.Nutrition;

import android.app.Activity;
import android.os.Bundle;

import edu.pacificu.cs493f15_1.paperorplasticapp.R;

/***************************************************************************************************
 *   Class:         NutritionActivity
 *   Description:   Creates NutritionActivity class that controls what occurs when the user
 *                  selects the nutrition option from the continue activity.
 *   Parameters:    N/A
 *   Returned:      N/A
 **************************************************************************************************/
public class NutritionActivity extends Activity
{
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition);
    }
}
