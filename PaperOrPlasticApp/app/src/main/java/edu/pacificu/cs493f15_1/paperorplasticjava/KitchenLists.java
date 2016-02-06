package edu.pacificu.cs493f15_1.paperorplasticjava;
/**
 * Created by sull0678 on 10/12/2015.
 */


import java.util.ArrayList;

public class KitchenLists extends PoPLists {
    public static final String KITCHEN_FILE_NAME = "kitchenLists.txt";


    public KitchenLists() {
        mLists = new ArrayList<PoPList>();
    }

    /*********************************
     * Adding
     ********************************/

    public void addList(String listName) {
        KitchenList newList = new KitchenList(listName);
        mLists.add(newList);
    }
}