/**************************************************************************************************
 *   File:     KitchenInventorySettingsActivity.java
 *   Author:   Abigail Jones
 *   Date:     10/28/15
 *   Class:    Capstone/Software Engineering
 *   Project:  PaperOrPlastic Application
 *   Purpose:  This activity will be the activity that is opened when the user chooses to
 *             look at the kitchen inventory settings. This can happen through the settings button
 *             on the continue activity or through the settings tab when the kitchen inventory
 *             button is pressed from the continue activity.
 ***************************************************************************************************/

package edu.pacificu.cs493f15_1.paperorplasticapp.kitchenInventory;

import android.os.Bundle;

import edu.pacificu.cs493f15_1.paperorplasticapp.popList.PoPListSettingsActivity;
import edu.pacificu.cs493f15_1.paperorplasticapp.R;
import edu.pacificu.cs493f15_1.paperorplasticjava.KitchenInventories;



/***************************************************************************************************
 *   Class:         KitchenInventorySettingsActivity
 *   Description:   Creates KitchenInventorySettingsActivity class that controls what occurs when the
 *                  user reaches the kitchen inventory settings page. Specifically, handles what
 *                  happens when the user specifies whether the kitchen inventory button
 *                  should be displayed on the continue activity. creates intents that take
 *                  users to those specific pages.
 *   Parameters:    N/A
 *   Returned:      N/A
 **************************************************************************************************/

public class KitchenInventorySettingsActivity extends PoPListSettingsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PoPOnCreate(savedInstanceState, new KitchenInventories(),
          R.layout.activity_kitchen_inventory_settings, KitchenInventories.KITCHEN_FILE_NAME, false);
    }
}
