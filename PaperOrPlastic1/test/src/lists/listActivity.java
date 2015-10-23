package lists;

import java.util.ArrayList;

/**
 * Created by sull0678 on 10/13/2015.
 */
public class listActivity
{
    public static void main (String[] args)
    {
        ListItem TempItem;
        NutritionFacts TempNFacts;
        ListItem LItem = new ListItem ("Test Food");
        KitchenLists KLists = new KitchenLists ();
        GroceryLists GLists = new GroceryLists ();
        NutritionFacts NFacts = new NutritionFacts ();

        NFacts.setAll(10, 20, 30, 40, 50, 60);

        KitchenList KList = new KitchenList ("my kitchen");

        GroceryList GList = new GroceryList ("my groceries");

        LItem.setAll (1, 2, 3, 4, 5, NFacts);

        KLists.addList("MyKitchen2");
        GLists.addList("MyGroceries2");
        KLists.addList("MyKitchen3");
        GLists.addList("MyGroceries3");

        KList.mItems = new ArrayList<ListItem>();

        KList.addItem(LItem);

        KList.printListName();//my kitchen
        GList.printListName();//my groceries

        KLists.printListNames();//MyKitchen2, MyKitcehn3
        GLists.printListNames();//MyGroceries2, MyGroceries3

        System.out.println("\n Add item to KList Test");

        TempItem = KList.getItem (0);

        TempItem.printAll ();

        TempNFacts = TempItem.getNutritionFacts();


        System.out.println ("Nutrition Fact Test");

        System.out.println (TempNFacts.mCalories);
        System.out.println (TempNFacts.mProtein);
        System.out.println (TempNFacts.mTotalFat);
        System.out.println (TempNFacts.mCarbohydrates);
        System.out.println (TempNFacts.mSugars);
        System.out.println (TempNFacts.mFiber);


        KLists.deleteList(0);
        KLists.printListNames();//MyKitcehn3

        GLists.deleteList(0);
        GLists.printListNames();//MyGroceries3
    }
}
