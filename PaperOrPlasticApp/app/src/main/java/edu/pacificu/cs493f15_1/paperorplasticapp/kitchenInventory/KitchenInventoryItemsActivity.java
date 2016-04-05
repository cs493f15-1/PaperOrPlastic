package edu.pacificu.cs493f15_1.paperorplasticapp.kitchenInventory;

import android.os.Bundle;

import edu.pacificu.cs493f15_1.paperorplasticapp.BaseActivity;
import edu.pacificu.cs493f15_1.paperorplasticapp.popList.PoPListItemsActivity;
import edu.pacificu.cs493f15_1.paperorplasticjava.KitchenInventories;

/**
 * Created by alco8653 on 4/5/2016.
 */
public class KitchenInventoryItemsActivity extends PoPListItemsActivity
{

  /********************************************************************************************
   * Function name: onCreate
   * Description:   Initializes all needed setup for objects in page
   * Parameters:    savedInstanceState  - a bundle object
   * Returns:       none
   ******************************************************************************************/
  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);

    PoPOnCreate(savedInstanceState, new KitchenInventories(), KitchenInventories.KITCHEN_FILE_NAME, false);
  }
}
