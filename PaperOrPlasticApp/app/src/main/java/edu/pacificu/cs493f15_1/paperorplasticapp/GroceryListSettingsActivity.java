/**************************************************************************************************
 *   File:     GroceryListSettingsActivity.java
 *   Author:   Abigail Jones
 *   Date:     10/28/15
 *   Class:    Capstone/Software Engineering
 *   Project:  PaperOrPlastic Application
 *   Purpose:  This activity will be the activity that is opened when the user chooses to
 *             look at the grocery list settings. This can happen through the settings button on the
 *             continue activity or through the settings tab when the grocery list button is pressed
 *             from the continue activity.
 ***************************************************************************************************/

package edu.pacificu.cs493f15_1.paperorplasticapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



/***************************************************************************************************
 *   Class:         GroceryListSettingsActivity
 *   Description:   Creates GroceryListSettingsActivity class that controls what occurs when the
 *                  user reaches the grocery list settings page. Specifically, handles what happens
 *                  when the user specifies whether the grocery list button should be displayed on
 *                  the continue activity.
 *                  creates intents that take users to those specific pages.
 *   Parameters:    N/A
 *   Returned:      N/A
 **************************************************************************************************/
public class GroceryListSettingsActivity extends Activity implements View.OnClickListener
{

    private Button mButtonShowGroceryList;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list_settings);

        mButtonShowGroceryList = (Button) findViewById (R.id.bShowGroceryList);
        mButtonShowGroceryList.setOnClickListener(this);
    }

    public void onClick (View view)
    {
        if (mButtonShowGroceryList == view)
        {
            if (ContinueActivity.bGListButtonStatusFromSettings.getVisibility() == View.VISIBLE)
            {
                ContinueActivity.bGListButtonStatusFromSettings.setVisibility(View.GONE);
            }
            else if (ContinueActivity.bGListButtonStatusFromSettings.getVisibility() == View.GONE)
            {
                ContinueActivity.bGListButtonStatusFromSettings.setVisibility(View.VISIBLE);
            }
        }
    }
}
