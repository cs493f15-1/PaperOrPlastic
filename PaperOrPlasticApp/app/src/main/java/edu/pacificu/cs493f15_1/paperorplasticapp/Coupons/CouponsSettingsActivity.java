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
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
public class CouponsSettingsActivity extends Activity implements View.OnClickListener
{
    private Button mButtonShowCouponsList;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons_settings);

        mButtonShowCouponsList = (Button) findViewById (R.id.bShowCouponsList);
        mButtonShowCouponsList.setOnClickListener(this);
    }

    public void onClick (View view)
    {
        if (mButtonShowCouponsList == view)
        {
            if (ContinueActivity.bCouponsButtonStatusFromSettings.getVisibility() == View.VISIBLE)
            {
                ContinueActivity.bCouponsButtonStatusFromSettings.setVisibility(View.GONE);
            }
            else if (ContinueActivity.bCouponsButtonStatusFromSettings.getVisibility() == View.GONE)
            {
                ContinueActivity.bCouponsButtonStatusFromSettings.setVisibility(View.VISIBLE);
            }
        }
    }
}
