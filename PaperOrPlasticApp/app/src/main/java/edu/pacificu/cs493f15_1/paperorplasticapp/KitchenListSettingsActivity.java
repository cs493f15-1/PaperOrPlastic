/**************************************************************************************************
 *   File:     KitchenListSettingsActivity.java
 *   Author:   Abigail Jones
 *   Date:     10/28/15
 *   Class:    Capstone/Software Engineering
 *   Project:  PaperOrPlastic Application
 *   Purpose:  This activity will be the activity that is opened when the user chooses to
 *             look at the ktichen list settings. This can happen through the settings button on the
 *             continue activity or through the settings tab when the ktichen list button is pressed
 *             from the continue activity.
 ***************************************************************************************************/

package edu.pacificu.cs493f15_1.paperorplasticapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/***************************************************************************************************
 *   Class:         KitchenListSettingsActivity
 *   Description:   Creates KitchenListSettingsActivity class that controls what occurs when the
 *                  user reaches the kitchen list settings page. Specifically, handles what happens
 *                  when the user specifies whether the kitchen list button should be displayed on
 *                  the continue activity.
 *                  creates intents that take users to those specific pages.
 *   Parameters:    N/A
 *   Returned:      N/A
 **************************************************************************************************/
public class KitchenListSettingsActivity extends Activity implements View.OnClickListener
{
    private Button mButtonShowKitchenList;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_list_settings);

        mButtonShowKitchenList = (Button) findViewById (R.id.bShowKitchenList);
        mButtonShowKitchenList.setOnClickListener(this);
    }

    public void onClick (View view)
    {
        if (mButtonShowKitchenList == view)
        {
            if (ContinueActivity.bKListButtonStatusFromSettings.getVisibility() == View.VISIBLE)
            {
                ContinueActivity.bKListButtonStatusFromSettings.setVisibility(View.GONE);
            }
            else if (ContinueActivity.bKListButtonStatusFromSettings.getVisibility() == View.GONE)
            {
                ContinueActivity.bKListButtonStatusFromSettings.setVisibility(View.VISIBLE);
            }
        }


    }
}
