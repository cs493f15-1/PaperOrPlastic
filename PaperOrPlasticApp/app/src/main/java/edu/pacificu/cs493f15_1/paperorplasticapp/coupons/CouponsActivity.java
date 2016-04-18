/**************************************************************************************************
*   File:     CouponsActivity.java
*   Author:   Abigail Jones
*   Date:     10/28/15
*   Class:    Capstone/Software Engineering
*   Project:  PaperOrPlastic Application
*   Purpose:  This activity will be the activity that is opened when the user selects the coupons
*             button from the continue activity
***************************************************************************************************/

package edu.pacificu.cs493f15_1.paperorplasticapp.coupons;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.fasterxml.jackson.databind.deser.Deserializers;

import edu.pacificu.cs493f15_1.paperorplasticapp.BaseActivity;
import edu.pacificu.cs493f15_1.paperorplasticapp.R;

/***************************************************************************************************
 *   Class:         CouponsActivity
 *   Description:   Creates CouponsActivity class that controls what occurs when the user
 *                  selects the coupons option from the continue activity
 *   Parameters:    N/A
 *   Returned:      N/A
 **************************************************************************************************/
public class CouponsActivity extends BaseActivity
{

    //Used to change fonts
    private TextView titleText;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons);

        titleText = (TextView) findViewById(R.id.couponsTitleText);
        Typeface laneUpperFont = Typeface.createFromAsset(getAssets(), "fonts/laneWUnderLine.ttf");
        Typeface laneNarrowFont = Typeface.createFromAsset(getAssets(), "fonts/LANENAR.ttf");
        titleText.setTypeface(laneUpperFont);
    }
}
