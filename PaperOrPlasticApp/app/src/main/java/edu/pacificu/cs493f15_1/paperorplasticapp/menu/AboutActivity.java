/**************************************************************************************************
 *   File:     AboutActivity.java
 *   Author:   Abigail Jones
 *   Date:     10/28/15
 *   Class:    Capstone/Software Engineering
 *   Project:  PaperOrPlastic Application
 *   Purpose:  This activity will be the activity that opens when the about button is pressed
 *             from the continue activity.
 ***************************************************************************************************/

package edu.pacificu.cs493f15_1.paperorplasticapp.menu;

import android.app.Activity;
import android.os.Bundle;

import edu.pacificu.cs493f15_1.paperorplasticapp.R;

/***************************************************************************************************
 *   Class:         AboutActivity
 *   Description:   Creates AboutActivity that takes user to about activity once about button
 *                  is pressed
 *   Parameters:    N/A
 *   Returned:      N/A
 ***************************************************************************************************/
public class AboutActivity extends Activity
{
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
}
