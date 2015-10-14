package lists;

/**
 * Created by sull0678 on 10/5/2015.
 */

import java.util.ArrayList;

public abstract class PoPList
{
    String mListName;
    int AisleCategoryNames[];
    String FoodTypeCategoryNames[];
    String CustomCategoryNames[];
    ArrayList<ListItem> mItems;
    int mCurrentSortingValue;

    public ListItem getItem (int itemIndex)
    {
        return mItems.get(itemIndex);
    }

    public void addItemToList (ListItem item)
    {
        mItems.add (item);
    }

    //(only works for certain kinds of categories)
   /* public boolean moveItemToCat (int itemIndex, int sortingType, int categoryNameIndex)
    {
        boolean bIsValid = true;
        return bIsValid;
    }

    deleteItem ();
        removeCustomCategory ()
        addCustomCategory ()*/

    public void printListName()
    {
        System.out.println (mListName);
    }
       /* setCurrentSortingCategory() (this is the way the list will be shown to the user)
          printList ()
     */
}
