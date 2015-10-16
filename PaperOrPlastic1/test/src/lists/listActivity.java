package lists;

/**
 * Created by sull0678 on 10/13/2015.
 */
public class listActivity
{
    public static void main (String[] args)
    {

        KitchenLists KLists = new KitchenLists ();
        GroceryLists GLists = new GroceryLists ();

        KitchenList KList = new KitchenList ("my kitchen");

        GroceryList GList = new GroceryList ("my groceries");

        KLists.addList ("MyKitchen2");
        GLists.addList ("MyGroceries2");
        KLists.addList ("MyKitchen3");
        GLists.addList ("MyGroceries3");

        KList.printListName();//my kitchen
        GList.printListName();//my groceries

        KLists.printListNames();//MyKitchen2, MyKitcehn3
        GLists.printListNames();//MyGroceries2, MyGroceries3

        KLists.deleteList(0);
        KLists.printListNames();//MyKitcehn3

        GLists.deleteList(0);
        GLists.printListNames();//MyGroceries3
    }
}
