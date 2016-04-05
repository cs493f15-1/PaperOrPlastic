package edu.pacificu.cs493f15_1.paperorplasticapp.groceryList;

import android.os.Bundle;

import edu.pacificu.cs493f15_1.paperorplasticapp.R;
import edu.pacificu.cs493f15_1.paperorplasticapp.popList.PoPListItemsActivity;
import edu.pacificu.cs493f15_1.paperorplasticjava.GroceryLists;

/**
 * Created by sull0678 on 4/4/2016.
 */
public class GroceryListItemsActivity extends PoPListItemsActivity {

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

    PoPOnCreate(savedInstanceState, new GroceryLists(), GroceryLists.GROCERY_FILE_NAME, true);
  }

}