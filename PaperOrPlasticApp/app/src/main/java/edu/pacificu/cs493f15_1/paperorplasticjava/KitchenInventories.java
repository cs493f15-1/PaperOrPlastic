package edu.pacificu.cs493f15_1.paperorplasticjava;
/**
 * Created by sull0678 on 10/12/2015.
 */


import java.util.ArrayList;

public class KitchenInventories extends PoPLists {
    public static final String KITCHEN_FILE_NAME = "kitchenInventories.txt";


    public KitchenInventories() {
        mLists = new ArrayList<PoPList>();
    }

    /*********************************
     * Adding
     ********************************/

    public void addList(String listName) {
        KitchenInventory newList = new KitchenInventory(listName);
        mLists.add(newList);
    }
}