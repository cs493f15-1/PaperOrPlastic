package lists;

/**
 * Created by sull0678 on 10/13/2015.
 */
public class listActivity
{
    public static void main (String[] args)
    {
        KitchenList KList = new KitchenList ("my kitchen");

        GroceryList GList = new GroceryList ("my groceries");

        KList.printListName();
        GList.printListName();
    }
}
