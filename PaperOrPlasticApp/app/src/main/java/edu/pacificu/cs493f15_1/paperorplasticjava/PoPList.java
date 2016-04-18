package edu.pacificu.cs493f15_1.paperorplasticjava;

/**
 * Created by sull0678 on 10/5/2015.
 */

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public abstract class PoPList
{
  String mListName;
  int mAisleCategoryNames[];
  String mFoodTypeCategoryNames[];
  String mCustomCategoryNames[];
  ArrayList<ListItem> mItems;
  int mCurrentSortingValue;
  public static final int SORT_NONE = 0;
  public static final int SORT_ALPHA = 1;
  public static final int SORT_CAL = 2;
  public static final int SORT_DATE = 3;
  public static final int SORT_AISLE = 4;
  public static final int SORT_PRICE = 5;

  public static final String[]GroupByStrings = {"Group By" , "Alphabetical", "Calories", "Date Entered", "Price"};

  /*******************************************
   * Gets
   ******************************************/
  public int getSize ()
  {
    return mItems.size();
  }

  public int getCurrentSortingValue ()
  {
    return mCurrentSortingValue;
  }

  public String getListName ()
  {
    return mListName;
  }

  public ListItem getItem (int itemIndex)
  {
    if (itemIndex >= getSize())
    {
      return null;
    }
    else
    {
      return mItems.get(itemIndex);
    }
  }

  public int getItemIndex (String itemName)
  {
    for (int i = 0; i < mItems.size(); i++)
    {
      if (mItems.get(i).getItemName().equals(itemName))
      {
        return i;
      }
    }
    return -1;
  }

  /********************************************
   * Sets
   *******************************************/

  public void setListName (String name)
  {
    mListName = name;
  }


  public void setItems (ArrayList<ListItem> items)
  {
    mItems = items;
  }
  /********************************************
   * Adds
   *******************************************/


  /********************************************************************************************
   * Function name: addItem
   *
   * Description: adds the ListItem to the list and returns true if the item already exists
   *              in the list added
   *
   * Parameters: item - the item being added to the list
   *
   * Returns: true if the item already exists in the list; otherwise false.
   ******************************************************************************************/
  public boolean addItem (ListItem item)
  {
    int i;
    boolean bExists;
    ListItem tempItem;

    bExists = false;

    // Makes sure if there is a duplicate item added, updates quantity instead of adding again.
    if (mItems.size () != 0)
    {
      for (i = 0; i < mItems.size(); i++)
      {
        tempItem = mItems.get (i);

        if (item.getItemName().equals (tempItem.getItemName()))
        {
          tempItem.setQuantity (tempItem.getQuantity() + 1);
          mItems.set (i, tempItem);

          bExists = true;
        }
      }
    }

    if (bExists == false) {
      mItems.add(item);
    }
    return bExists;
  }


  //(only works for certain kinds of category sorting)
   /* public boolean moveItemToCat (int itemIndex, int sortingType, int categoryNameIndex)
    {
        boolean bIsValid = true;
        return bIsValid;
    }
    addCategory
*/

  /******************************************
   * Deletes
   ******************************************/

  //TODO change this to return a value if not found
  // Deletion method could change.
  public void deleteItem (String itemName)
  {
    int i;
    ListItem tempItem;

    for (i = 0; i < mItems.size(); i++)
    {
      tempItem = mItems.get (i);

      if (itemName.equals (tempItem.getItemName()))
      {
        mItems.remove (i);
      }
    }
  }

  public void delete (int index)
  {
    mItems.remove(index);
  }

  public void clearList ()
  {
    mItems.clear();
  }
     /*   removeCustomCategory ()
        addCustomCategory ()*/

  public void printListName()
  {
    System.out.println (mListName);
  }


  public ArrayList<ListItem> getItemArray ()
  {
    return mItems;
  }

       /* Functions
       setCurrentSortingCategory() (this is the way the list will be shown to the user)
          printListItems ()
     */

  /*********************************
   * Sorts
   ********************************/



  public void setCurrentSortingValue (int sortingValue)
  {
    mCurrentSortingValue = sortingValue;
  }

  /**
   * Sorts the ListItems alphabetically by name using the Comparator NAME in ListItem
   */
  public void sortListByName ()
  {
    Collections.sort(this.mItems, ListItem.Comparators.NAME);
  }




  /*********************************
   * I/O
   ********************************/

  /**
   * Outputs the current list in to the passed in file.
   * @param listOutput - the file being written to
   */
  public abstract void writeListToFile (PrintWriter listOutput);


  /**
   * reads from the file in to the current list.
   * @param listInput - the file being read from
   */
  public abstract void readListFromFile (Scanner listInput);


}
