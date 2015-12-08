package edu.pacificu.cs493f15_1.paperorplasticjava; /**
 * Created by sull0678 on 10/12/2015.
 */


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class GroceryLists
{
    public static final String GROCERY_FILE_NAME = "groceryLists.txt";

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

    public String getGListName(int listIndex)
    {
        return mLists.get (listIndex).getListName();
    }

    public GroceryList getList (int listIndex)
    {

        GroceryList list = null;
        if (mLists.size() > listIndex)
        {
            list =  mLists.get(listIndex);
        }
        return list;

    }

    public int getSize ()
    {
        return mLists.size();
    }

    public ArrayList<GroceryList> getArrayOfLists ()
    {
        return mLists;
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


    /********************************************************************************************
     * Function name: writeListsToFile
     *
     * Description: Outputs the current mLists to the passed in PrintWriter
     *
     * Parameters: listsOutput - the printWriter which the groceryLists will be outputted to
     *
     * Returns: None
     ******************************************************************************************/
    public void writeListsToFile (PrintWriter listsOutput)
    {
        listsOutput.println(mLists.size());

        for (GroceryList glist : mLists)
        {
            glist.getListName();
            glist.writeListToFile(listsOutput);
            listsOutput.flush();
        }
    }

    /********************************************************************************************
     * Function name: readListsFromFile
     *
     * Description: reads from a file using a scanner and inputs the information into mLists
     *
     * Parameters: listsInput - the Scanner which the groceryLists will be read from
     *
     * Returns: None
     ******************************************************************************************/
    public void readListsFromFile (Scanner listsInput)
    {
        int size;
        GroceryList tempList;

        size = listsInput.nextInt();

        for (int i = 0; i < size; ++i)
        {
            addList("temp");
            tempList = getList(i);
            tempList.readListFromFile(listsInput);
        }
    }

    public void clearLists ()
    {
        for (int i = 0; i < mLists.size(); i++)
        {
            mLists.remove(i);
        }
    }


}
