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
     * Gets
     ********************************/

    public ArrayList<KitchenList> getArrayOfLists ()
    {
        return mLists;
    }

    public String getKListName(int listIndex)
    {
        return mLists.get (listIndex).getListName();
    }

    public KitchenList getList (int listIndex)
    {

        KitchenList list = null;
        if (mLists.size() > listIndex)
        {
            list =  mLists.get(listIndex);
        }
        return list;

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

    public void printListNames ()
    {
        for (KitchenList tempList : mLists)
        {
            tempList.printListName();
        }
    }

    /********************************************************************************************
     * Function name: writeListsToFile
     *
     * Description: Outputs the current mLists to the passed in PrintWriter
     *
     * Parameters: listsOutput - the printWriter which the kitchenLists will be outputted to
     *
     * Returns: None
    ******************************************************************************************/
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

    /********************************************************************************************
     * Function name: readListsFromFile
     *
     * Description: reads from a file using a scanner and inputs the information into mLists
     *
     * Parameters: listsInput - the Scanner which the kitchenLists will be read from
     *
     * Returns: None
     ******************************************************************************************/
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
