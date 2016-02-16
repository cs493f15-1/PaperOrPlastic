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
  boolean bIsLinked;
  boolean bIsShared;

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

  public static final String[] GroupByStrings = {"", "alphabetical", "calories", "date entered", "aisle", "price"};

  public PoPList()
  {
  }



  /*******************************************
   * Gets
   ******************************************/

  public String getmListName()
  {
    return mListName;
  }


  public boolean isbIsLinked()
  {
    return bIsLinked;
  }

  public boolean isbIsShared()
  {
    return bIsShared;
  }


  public int[] getmAisleCategoryNames()
  {
    return mAisleCategoryNames;
  }

  public String[] getmFoodTypeCategoryNames()
  {
    return mFoodTypeCategoryNames;
  }

  public String[] getmCustomCategoryNames()
  {
    return mCustomCategoryNames;
  }

  public ArrayList<ListItem> getmItems()
  {
    return mItems;
  }

  public int getmCurrentSortingValue()
  {
    return mCurrentSortingValue;
  }

  public static int getSortNone()
  {
    return SORT_NONE;
  }

  public static int getSortAlpha()
  {
    return SORT_ALPHA;
  }

  public static int getSortCal()
  {
    return SORT_CAL;
  }

  public static int getSortDate()
  {
    return SORT_DATE;
  }

  public static int getSortAisle()
  {
    return SORT_AISLE;
  }

  public static int getSortPrice()
  {
    return SORT_PRICE;
  }

  public static String[] getGroupByStrings()
  {
    return GroupByStrings;
  }

  /********************************************
   * Sets
   *******************************************/

  public void setItems(ArrayList<ListItem> items)
  {
    mItems = items;
  }

  public void setmListName(String mListName)
  {
    this.mListName = mListName;
  }

  public void setmAisleCategoryNames(int[] mAisleCategoryNames)
  {
    this.mAisleCategoryNames = mAisleCategoryNames;
  }

  public void setmFoodTypeCategoryNames(String[] mFoodTypeCategoryNames)
  {
    this.mFoodTypeCategoryNames = mFoodTypeCategoryNames;
  }

  public void setmCustomCategoryNames(String[] mCustomCategoryNames)
  {
    this.mCustomCategoryNames = mCustomCategoryNames;
  }

  public void setmItems(ArrayList<ListItem> mItems)
  {
    this.mItems = mItems;
  }

  public void setmCurrentSortingValue(int mCurrentSortingValue)
  {
    this.mCurrentSortingValue = mCurrentSortingValue;
  }

  public void setbIsLinked(boolean bIsLinked)
  {
    this.bIsLinked = bIsLinked;
  }

  public void setbIsShared(boolean bIsShared)
  {
    this.bIsShared = bIsShared;
  }

  /********************************************
   * Adds
   *******************************************/
  public void addItem(ListItem item)
  {
    int i;
    boolean bExists;
    ListItem tempItem;

    bExists = false;

    // Makes sure if there is a duplicate item added, updates quantity instead of adding again.
    if (mItems.size() != 0)
    {
      for (i = 0; i < mItems.size(); i++)
      {
        tempItem = mItems.get(i);

        if (item.getmName().contains(tempItem.getmName()))
        {
          tempItem.addQuantity(1);
          mItems.set(i, tempItem);
          bExists = true;
        }
      }
    }

    if (bExists == false)
    {
      mItems.add(item);
    }
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
  public void deleteItem(String itemName)
  {
    int i;
    ListItem tempItem;

    for (i = 0; i < mItems.size(); i++)
    {
      tempItem = mItems.get(i);

      if (itemName.equals(tempItem.getmName()))
      {
        mItems.remove(i);
      }
    }
  }

  public void delete(int index)
  {
    mItems.remove(index);
  }

  public void clearList()
  {
    mItems.clear();
  }
     /*   removeCustomCategory ()
        addCustomCategory ()*/

  public void printListName()
  {
    System.out.println(mListName);
  }




  public ArrayList<ListItem> returnItemArray()
  {
    return mItems;
  }

       /* Functions
       setCurrentSortingCategory() (this is the way the list will be shown to the user)
          printListItems ()
     */


  public int returnSize()
  {
    return mItems.size();
  }

  public int returnItemIndex(String itemName)
  {
    for (int i = 0; i < mItems.size(); i++)
    {
      if (mItems.get(i).getmName().equals(itemName))
      {
        return i;
      }
    }
    return -1;
  }


  public ListItem returnItem(int itemIndex)
  {
    if (itemIndex >= returnSize())
    {
      return null;
    }
    else
    {
      return mItems.get(itemIndex);
    }
  }


  /*********************************
   * Sorts
   ********************************/

  /**
   * Sorts the ListItems alphabetically by name using the Comparator NAME in ListItem
   */
  public void sortListByName()
  {
    Collections.sort(this.mItems, ListItem.Comparators.NAME);
  }


  /*********************************
   * I/O
   ********************************/

  /**
   * Outputs the current list in to the passed in file.
   *
   * @param listOutput - the file being written to
   */
  public abstract void writeListToFile(PrintWriter listOutput);


  /**
   * reads from the file in to the current list.
   *
   * @param listInput - the file being read from
   */
  public abstract void readListFromFile(Scanner listInput);


}
