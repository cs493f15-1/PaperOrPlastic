package edu.pacificu.cs493f15_1.paperorplasticjava;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by sull0678 on 10/12/2015.
 */
public class KitchenList extends PoPList
{
    boolean bIsLinked;
    boolean bIsShared;

    public KitchenList (String name)
    {
        mListName = name;
        mItems = new ArrayList<ListItem>();
        mCurrentSortingValue = SORT_NONE;
    }
    /*Functions
        Eat ()
        ThrowAway () (these two functions matter if we are watching the users diet
        LinkListToGrocery (groceryList);
        unLinkList ();
        shareList (); (with permissions?)
        unShareList ();
     */




    /*********************************
     * I/O
     ********************************/

    @Override
    public String toString ()
    {
        String returnString;

        returnString = getListName() + "\n" + getSize() + " " + getCurrentSortingValue();
        for (ListItem item : mItems)
        {
            returnString.concat(item.toString() + "\n");
        }
        returnString.concat("\n");

        return returnString;
    }

    /**
     * Outputs the current list in to the passed in file.
     * @param listOutput - the file being written to
     */
    public void writeListToFile (PrintWriter listOutput)
    {
        listOutput.println(getListName());
        listOutput.println (getSize() + " " + getCurrentSortingValue());
        for (ListItem item : mItems)
        {
            item.writeItemToFile(listOutput);
        }
        listOutput.flush();
    }


    /**
     * reads from the file in to the current list.
     * @param listInput - the file being read from
     */
    public void readListFromFile (Scanner listInput)
    {
        String temp;
        int size;
        ListItem tempItem;

        setListName(listInput.nextLine()); //get the new line character left from before
        setListName(listInput.nextLine());

        size = listInput.nextInt();

        setCurrentSortingValue(listInput.nextInt());

        for (int i = 0; i < size; ++i)
        {
            tempItem = new ListItem("temp");
            tempItem.readItemFromFile(listInput);
            addItem(tempItem);
        }
    }
}
