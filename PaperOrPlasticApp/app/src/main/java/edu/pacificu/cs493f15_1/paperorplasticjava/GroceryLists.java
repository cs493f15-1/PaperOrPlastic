package edu.pacificu.cs493f15_1.paperorplasticjava; /**
 * Created by sull0678 on 10/12/2015.
 */


import java.util.ArrayList;

public class GroceryLists extends PoPLists
{
  public static final String GROCERY_FILE_NAME = "groceryLists.txt";


  public GroceryLists ()
  {
    mLists = new ArrayList<PoPList>();
  }

  /*********************************
   * Adding
   ********************************/

  public void addList (String listName)
  {
    if (!ListNameExists(listName)) {
      GroceryList newList = new GroceryList(listName.toUpperCase());
      mLists.add(newList);
    }
  }

}