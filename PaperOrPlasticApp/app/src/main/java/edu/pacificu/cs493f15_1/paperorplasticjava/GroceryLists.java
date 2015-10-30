package edu.pacificu.cs493f15_1.paperorplasticjava; /**
 * Created by sull0678 on 10/12/2015.
 */


import java.util.ArrayList;

public class GroceryLists
{

    ArrayList<GroceryList> mLists;

    public GroceryLists ()
    {
        mLists = new ArrayList<GroceryList>();
    }

    /*********************************
     * Printing
     *********************************/

    public void printListNames ()
    {
        for (GroceryList tempList : mLists)
        {
            tempList.printListName();
        }
    }

    /*********************************
     * Gets
     ********************************/

    public String getListName (int listIndex)
    {
        return mLists.get (listIndex).getListName();
    }

    public GroceryList getList (int listIndex)
    {
        return mLists.get(listIndex);
    }

    public int getSize ()
    {
        return mLists.size();
    }

    /**********************************
     * Sets
     *********************************/

    public void setListName (int listIndex, String newListName)
    {
        mLists.get (listIndex).setListName (newListName);
    }

    /*********************************
     * Adding
     ********************************/

    public void addList (String listName)
    {
        GroceryList newList = new GroceryList (listName);
        mLists.add (newList);
    }

    /*********************************
     * Deletes
     ********************************/
    public void deleteList (int listIndex)
    {
        mLists.remove (listIndex);
    }

    /*Functions
        addItem (list, item, index?)
        deleteList (listName);
        linkList (listName, kitchenListName);
        shareList (listName, username);
        unShareList (listName, username);
     */


}
