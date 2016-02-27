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

package edu.pacificu.cs493f15_1.paperorplasticapp.groceryList;

import android.os.Bundle;

import edu.pacificu.cs493f15_1.paperorplasticapp.popList.PoPListSettingsActivity;
import edu.pacificu.cs493f15_1.paperorplasticapp.R;
import edu.pacificu.cs493f15_1.paperorplasticjava.GroceryLists;


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

public class GroceryListSettingsActivity extends PoPListSettingsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PoPOnCreate(savedInstanceState, new GroceryLists(), R.layout.activity_grocery_list_settings, GroceryLists.GROCERY_FILE_NAME, true);
    }
}