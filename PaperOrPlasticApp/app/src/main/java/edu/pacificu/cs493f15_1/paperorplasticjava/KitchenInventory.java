package edu.pacificu.cs493f15_1.paperorplasticjava;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by sull0678 on 10/12/2015.
 */
public class KitchenInventory extends PoPList {
    boolean bIsLinked;
    boolean bIsShared;

    public KitchenInventory(String name) {
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

    /********************************************************************************************
     * Function name: writeListToFile
     * <p/>
     * Description: Outputs the current list to the passed in PrintWriter
     * <p/>
     * Parameters: listOutput - the printWriter which the KitchenInventory will be outputted to
     * <p/>
     * Returns: None
     ******************************************************************************************/
    public void writeListToFile(PrintWriter listOutput) {
        listOutput.println(getListName());
        listOutput.println(getSize() + " " + getCurrentSortingValue());
        for (ListItem item : mItems) {
            item.writeItemToFile(listOutput);
        }
        listOutput.flush();
    }


    /********************************************************************************************
     * Function name: readListFromFile
     * <p/>
     * Description: reads from a file using a scanner and inputs the information into the list
     * <p/>
     * Parameters: listInput - the Scanner which the KitchenInventory will be read from
     * <p/>
     * Returns: None
     ******************************************************************************************/
    public void readListFromFile(Scanner listInput) {
        String temp;
        int size;
        ListItem tempItem;

        listInput.nextLine(); //get the new line character left from before
        setListName(listInput.nextLine());

        size = listInput.nextInt();

        setCurrentSortingValue(listInput.nextInt());

        for (int i = 0; i < size; ++i) {
            tempItem = new ListItem("temp", "brand", "desc");
            tempItem.readItemFromFile(listInput);
            addItem(tempItem);
        }
    }
}