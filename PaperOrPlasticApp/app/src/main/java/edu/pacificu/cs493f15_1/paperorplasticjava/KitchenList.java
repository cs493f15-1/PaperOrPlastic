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

    /**
     * Outputs the current list in to the passed in file.
     * @param listOutput - the file being written to
     */
    public void writeListToFile (PrintWriter listOutput)
    {
        listOutput.println (mListName + " " + mSize + " " + mCurrentSortingValue);
        for (ListItem item : mItems)
        {
            item.writeItemToFile(listOutput);
        }
        listOutput.print ("\n");
    }


    /**
     * reads from the file in to the current list.
     * @param listInput - the file being read from
     */
    public void readListFromFile (Scanner listInput)
    {

    }
}
