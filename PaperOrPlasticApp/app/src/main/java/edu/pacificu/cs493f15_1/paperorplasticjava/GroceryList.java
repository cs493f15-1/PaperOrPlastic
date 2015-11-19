package edu.pacificu.cs493f15_1.paperorplasticjava;

import java.util.ArrayList;

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
    mSize = 0;
      mItems = new ArrayList<ListItem>();
      mCurrentSortingValue = SORT_NONE;
  }

    /*Functions
        LinkListToGrocery (groceryList);
        unLinkList ();
        shareList (); (with permissions?)
        unShareList ();
    */
}
