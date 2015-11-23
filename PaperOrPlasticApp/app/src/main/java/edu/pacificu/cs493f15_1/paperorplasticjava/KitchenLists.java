package edu.pacificu.cs493f15_1.paperorplasticjava; /**
 * Created by sull0678 on 10/12/2015.
 */


import java.util.ArrayList;

public class KitchenLists
{
    ArrayList<KitchenList> mLists;

    public KitchenLists ()
    {
        mLists = new ArrayList<KitchenList>();
    }

    /*********************************
     * Printing
     *********************************/

    public void printListNames ()
    {
        for (KitchenList tempList : mLists)
        {
            tempList.printListName();
        }
    }


    /*********************************
     * Gets
     ********************************/

    public ArrayList<KitchenList> getArrayOfLists ()
    {
        return mLists;
    }

    public String getListName (int listIndex)
    {
        return mLists.get (listIndex).getListName();
    }

    public KitchenList getList (int listIndex)
    {
        return mLists.get(listIndex);
    }

    public int getSize()
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
        KitchenList newList = new KitchenList (listName);
        mLists.add (newList);
    }

    //addListItem


    /*********************************
     * Deletes
     ********************************/

    public void deleteList (int listIndex)
    {
        mLists.remove(listIndex);
    }

    /*Functions
        addListItem (listName);
        deleteListItem ()
        linkList (listName, groceryListName);
        shareList (listName, username);
        unShareList (listName, username);
     */
}
