package edu.pacificu.cs493f15_1.paperorplasticjava;

/**
 * Created by sull0678 on 10/5/2015.
 */

import java.util.ArrayList;
import java.util.List;

public abstract class PoPList
{
    String mListName;
    int mAisleCategoryNames[];
    String mFoodTypeCategoryNames[];
    String mCustomCategoryNames[];
    ArrayList<ListItem> mItems;
    int mCurrentSortingValue;
    int mSize;

    /*******************************************
     * Gets
     ******************************************/
    public String getListName ()
    {
        return mListName;
    }

    public ListItem getItem (int itemIndex)
    {
        return mItems.get(itemIndex);
    }

    /********************************************
     * Sets
     *******************************************/

    public void setListName (String name)
    {
        mListName = name;
    }


    /********************************************
    * Adds
     *******************************************/

    public void addItem (ListItem item)
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

          if (item.getItemName().contains (tempItem.getItemName()))
          {
            tempItem.setAddQuantity (1);
            mItems.set (i, tempItem);
            bExists = true;
          }
        }
      }

      if (bExists == false)
      {
        mItems.add (item);
        mSize++;
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
          mSize--;
        }
      }
    }

    public void clearList ()
    {
        mItems.clear ();
    }
     /*   removeCustomCategory ()
        addCustomCategory ()*/

    public void printListName()
    {
        System.out.println (mListName);
    }

    public int getSize ()
    {
        return mSize;
    }

    public ArrayList<ListItem> getItemArray ()
    {
        return mItems;
    }

       /* Functions
       setCurrentSortingCategory() (this is the way the list will be shown to the user)
          printListItems ()
     */
}
