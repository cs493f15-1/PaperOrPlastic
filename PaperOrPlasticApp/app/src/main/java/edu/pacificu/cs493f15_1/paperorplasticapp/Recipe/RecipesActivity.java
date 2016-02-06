/**************************************************************************************************
 *   File:     RecipesActivity.java
 *   Author:   Abigail Jones
 *   Date:     10/28/15
 *   Class:    Capstone/Software Engineering
 *   Project:  PaperOrPlastic Application
 *   Purpose:  This activity will be the activity that is opened when the user selects the
 *             recipes button from the continue activity
 ***************************************************************************************************/

package edu.pacificu.cs493f15_1.paperorplasticapp.Recipe;

import android.app.Activity;
import android.os.Bundle;

import edu.pacificu.cs493f15_1.paperorplasticapp.R;

/***************************************************************************************************
 *   Class:         RecipesActivity
 *   Description:   Creates RecipesActivity class that controls what occurs when the user
 *                  selects the recipes option from the continue activity.
 *   Parameters:    N/A
 *   Returned:      N/A
 **************************************************************************************************/
public class RecipesActivity extends Activity
{
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
    }
}
