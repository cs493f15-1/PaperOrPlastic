package edu.pacificu.cs493f15_1.paperorplasticjava;
/**
 * Created by sull0678 on 10/12/2015.
 */


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class KitchenLists
{
    public static final String KITCHEN_FILE_NAME = "kitchenLists.txt";

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



    /*********************************
     * I/O
     ********************************/

    @Override
    public String toString ()
    {
        String returnString;

        returnString = Integer.toString(mLists.size());
        for (KitchenList list : mLists)
        {
            returnString.concat(list.toString());
        }

        return returnString;
    }


    /**
     * Outputs the current lists in to the passed in file.
     * @param listsOutput - the file being written to
     */
    public void writeListsToFile (PrintWriter listsOutput)
    {
        listsOutput.println(mLists.size());

        for (KitchenList list : mLists)
        {
            list.getListName();
            list.writeListToFile(listsOutput);
            listsOutput.flush();
        }

    }

    /**
     * reads from the file in to the current lists.
     * @param listsInput - the file being read from
     */
    public void readListsFromFile (Scanner listsInput)
    {
        int size;
        KitchenList tempList;

        size = listsInput.nextInt();

        for (int i = 0; i < size; ++i)
        {
            addList("temp");
            tempList = getList(i);
            tempList.readListFromFile(listsInput);
        }
    }
}
