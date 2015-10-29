package testLists.lists;

/**
 * Created by sull0678 on 10/5/2015.
 */

import java.util.ArrayList;

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
/*
    public void addItem (ListItem item)
    {
        mItems.add (item);
    }*/

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
    public void deleteItem (String itemName)
    {
    }

    public void clearList ()
    {
        //doesn't give memory leaks?
        mItems.clear ();
    }
     /*   removeCustomCategory ()
        addCustomCategory ()*/

    public void printListName()
    {
        System.out.println (mListName);
    }



       /* Functions
       setCurrentSortingCategory() (this is the way the list will be shown to the user)
          printListItems ()
     */
}
