/**************************************************************************************************
 *   File:     TotalNutritionActivity.java
 *   Author:   Evan Heydemann
 *   Date:     4/24/65
 *   Class:    Capstone/Software Engineering
 *   Project:  PaperOrPlastic Application
 *   Purpose:  This activity will be the activity that is opened when the user selects the nutrition
 *             button from the continue activity
 ***************************************************************************************************/

package edu.pacificu.cs493f15_1.paperorplasticapp.nutrition;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import edu.pacificu.cs493f15_1.paperorplasticapp.BaseActivity;
import edu.pacificu.cs493f15_1.paperorplasticapp.R;

/***************************************************************************************************
 *   Class:         CouponsActivity
 *   Description:   Creates CouponsActivity class that controls what occurs when the user
 *                  selects the coupons option from the continue activity
 *   Parameters:    N/A
 *   Returned:      N/A
 **************************************************************************************************/
public class TotalNutritionActivity extends BaseActivity
{

    //Used to change fonts
    private TextView titleText;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_nutrition);

        titleText = (TextView) findViewById(R.id.nutritionTitle);
        Typeface laneUpperFont = Typeface.createFromAsset(getAssets(), "fonts/laneWUnderLine.ttf");
        Typeface laneNarrowFont = Typeface.createFromAsset(getAssets(), "fonts/LANENAR.ttf");
        titleText.setTypeface(laneUpperFont);
    }
}