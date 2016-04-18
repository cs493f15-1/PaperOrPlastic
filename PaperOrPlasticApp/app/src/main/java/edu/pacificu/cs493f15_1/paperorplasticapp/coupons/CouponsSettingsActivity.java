/**************************************************************************************************
*   File:     CouponsSettingsActivity.java
*   Author:   Abigail Jones
*   Date:     10/28/15
*   Class:    Capstone/Software Engineering
*   Project:  PaperOrPlastic Application
*   Purpose:  This activity will be the activity that is opened when the user chooses to
*             look at the coupon settings. This can happen through the settings button on the
*             continue activity or through the settings tab when the coupon button is pressed
*             from the continue activity.
***************************************************************************************************/

package edu.pacificu.cs493f15_1.paperorplasticapp.coupons;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.pacificu.cs493f15_1.paperorplasticapp.BaseActivity;
import edu.pacificu.cs493f15_1.paperorplasticapp.menu.ContinueActivity;
import edu.pacificu.cs493f15_1.paperorplasticapp.R;

/***************************************************************************************************
 *   Class:         CouponsSettingsActivity
 *   Description:   Creates CouponsSettingsActivity class that controls what occurs when the user
 *                  reaches the coupon settings page. Specifically, handles what happens when
 *                  the user specifies whether the coupons button should be displayed on the
 *                  continue activity.
 *                  creates intents that take users to those specific pages.
 *   Parameters:    N/A
 *   Returned:      N/A
 **************************************************************************************************/
public class CouponsSettingsActivity extends BaseActivity implements View.OnClickListener
{
    //Used to change fonts
    private TextView titleText;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons_settings);

        titleText = (TextView) findViewById (R.id.CouponSettingsTitleText);

        Typeface laneUpperFont = Typeface.createFromAsset (getAssets (), "fonts/laneWUnderLine.ttf");

        titleText.setTypeface(laneUpperFont);
    }

    /***********************************************************************************************
    *   Method:      onClick
     *
    *   Description: Called when a click has been captured.
    *
    *   Parameters:  view - the view that has been clicked
     *
    *   Returned:    N/A
    ***********************************************************************************************/
    public void onClick (View view)
    {

    }
}
