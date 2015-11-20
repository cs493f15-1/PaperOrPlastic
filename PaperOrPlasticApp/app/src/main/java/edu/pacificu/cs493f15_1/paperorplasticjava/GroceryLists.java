package edu.pacificu.cs493f15_1.paperorplasticjava; /**
 * Created by sull0678 on 10/12/2015.
 */


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

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



    /*********************************
     * I/O
     ********************************/

    /**
     * Outputs the current lists in to the passed in file.
     * @param listsOutput - the file being written to
     */
    public void writeListsToFile (PrintWriter listsOutput)
    {
        for (GroceryList list : mLists)
        {
            list.writeListToFile(listsOutput);
        }
    }

    /**
     * reads from the file in to the current lists.
     * @param listsInput - the file being read from
     */
    public void readListsFromFile (Scanner listsInput)
    {

    }


}
