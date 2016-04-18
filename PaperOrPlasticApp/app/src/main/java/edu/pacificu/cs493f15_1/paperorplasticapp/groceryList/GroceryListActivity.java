/**************************************************************************************************
*   File:     GroceryListActivity.java
*   Author:   Abigail Jones, Lauren Sullivan, Evan Heydemann
*   Date:     10/28/15
*   Class:    Capstone/Software Engineering
*   Project:  PaperOrPlastic Application
*   Purpose:  This activity will be the activity that is opened when the user selects the
*             grocery list button from the continue activity
***************************************************************************************************/

package edu.pacificu.cs493f15_1.paperorplasticapp.groceryList;

import android.content.Intent;
import android.os.Bundle;

import edu.pacificu.cs493f15_1.paperorplasticapp.popList.PoPListActivity;
import edu.pacificu.cs493f15_1.paperorplasticapp.R;
import edu.pacificu.cs493f15_1.paperorplasticjava.GroceryLists;
import edu.pacificu.cs493f15_1.paperorplasticjava.ListItem;


/***************************************************************************************************
*   Class:         GroceryListActivity
*   Description:   Creates GroceryListActivity class that controls what occurs when the user
*                  selects the grocery list option from the continue activity. Specifically
*                  contains the list functionality.
*   Parameters:    N/A
*   Returned:      N/A
**************************************************************************************************/
public class GroceryListActivity extends PoPListActivity {

    /********************************************************************************************
     * Function name: onCreate
     * <p/>
     * Description:   Initializes all needed setup for objects in page
     * <p/>
     * Parameters:    savedInstanceState  - a bundle object
     * <p/>
     * Returns:       none
     ******************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        PoPOnCreate(savedInstanceState, new GroceryLists(), R.layout.activity_lists,
          GroceryLists.GROCERY_FILE_NAME, true);
    }


}