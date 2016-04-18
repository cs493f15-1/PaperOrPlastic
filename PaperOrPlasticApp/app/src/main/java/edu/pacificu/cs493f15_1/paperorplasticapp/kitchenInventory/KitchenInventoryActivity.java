/**************************************************************************************************
 *   File:     KitchenInventoryActivity.java
 *   Author:   Abigail Jones, Lauren Sullivan, Evan Heydemann
 *   Date:     10/28/15
 *   Class:    Capstone/Software Engineering
 *   Project:  PaperOrPlastic Application
 *   Purpose:  This activity will be the activity that is opened when the user selects the
 *             kitchen inventory button from the continue activity
 ***************************************************************************************************/

package edu.pacificu.cs493f15_1.paperorplasticapp.kitchenInventory;

import android.os.Bundle;

import edu.pacificu.cs493f15_1.paperorplasticapp.popList.PoPListActivity;
import edu.pacificu.cs493f15_1.paperorplasticapp.R;
import edu.pacificu.cs493f15_1.paperorplasticjava.KitchenInventories;


/***************************************************************************************************
 *   Class:         KitchenInventoryActivity
 *   Description:   Creates KitchenInventoryActivity class that controls what occurs when the user
 *                  selects the kitchen inventory option from the continue activity. Specifically
 *                  contains the list functionality.
 *   Parameters:    N/A
 *   Returned:      N/A
 **************************************************************************************************/
public class KitchenInventoryActivity  extends PoPListActivity {

    /********************************************************************************************
     * Function name: onCreate
     * Description:   Initializes all needed setup for objects in page
     * Parameters:    savedInstanceState  - a bundle object
     * Returns:       none
     ******************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        PoPOnCreate(savedInstanceState, new KitchenInventories(), R.layout.activity_lists,
         KitchenInventories.KITCHEN_FILE_NAME, false);
    }

}
