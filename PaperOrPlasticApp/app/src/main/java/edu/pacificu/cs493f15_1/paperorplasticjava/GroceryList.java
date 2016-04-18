package edu.pacificu.cs493f15_1.paperorplasticjava;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by sull0678 on 10/12/2015.
 */
public class GroceryList extends PoPList
{
  boolean bIsLinked;
  boolean bIsShared;

  public GroceryList (String name)
  {
    mListName = name;

    mItems = new ArrayList<ListItem>();
    mCurrentSortingValue = SORT_NONE;
  }

    /*Functions
        LinkListToGrocery (groceryList);
        unLinkList ();
        shareList (); (with permissions?)
        unShareList ();
    */

  /*********************************
   * I/O
   ********************************/

  /********************************************************************************************
   * Function name: writeListToFile
   *
   * Description: Outputs the current list to the passed in PrintWriter
   *
   * Parameters: listOutput - the printWriter which the kitchenList will be outputted to
   *
   * Returns: None
   ******************************************************************************************/
  public void writeListToFile (PrintWriter listOutput)
  {
    listOutput.println(getListName());
    listOutput.println(getSize() + " " + getCurrentSortingValue());
    for (ListItem item : mItems)
    {
      item.writeItemToFile(listOutput);
    }
    listOutput.flush();
  }


  /********************************************************************************************
   * Function name: readListFromFile
   *
   * Description: reads from a file using a scanner and inputs the information into the list
   *
   * Parameters: listInput - the Scanner which the kitchenList will be read from
   *
   * Returns: None
   ******************************************************************************************/
  public void readListFromFile (Scanner listInput)
  {
    String temp;
    int size;
    ListItem tempItem;

    listInput.nextLine(); //get the new line character left from before
    setListName(listInput.nextLine());

    size = listInput.nextInt();

    setCurrentSortingValue(listInput.nextInt());

    for (int i = 0; i < size; ++i)
    {
      tempItem = new ListItem("temp", "brand", "desc");
      tempItem.readItemFromFile(listInput);
      addItem(tempItem);
    }
  }
}
