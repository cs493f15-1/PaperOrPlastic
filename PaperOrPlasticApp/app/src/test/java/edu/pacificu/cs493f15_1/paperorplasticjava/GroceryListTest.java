package edu.pacificu.cs493f15_1.paperorplasticjava;


import org.junit.*;


/**
 * Created by heyd5159 on 11/6/2015.
 */
public class GroceryListTest
{
    private GroceryList GList;
    private ListItem item1, item2, item3;

    @Before
    public void setUp () {
        GList = new GroceryList("Temp List");
        item1 = new ListItem("A");
        item2 = new ListItem("B");
        item3 = new ListItem("3");
    }

    @After
    public void tearDown () { GList = null; }

    @Test
    public void TestGetItemReturnItem ()
    {
        Assert.assertEquals("The size of the List was not as expected.", 0, GList.getSize());

        GList.addItem(item1);

        Assert.assertEquals("The size of the List was not as expected.", 1, GList.getSize());
        Assert.assertEquals("The List Item returned was not as expected.", item1, GList.getItem(0));
    }

    @Test
    public void TestGetItemReturnNull ()
    {
        Assert.assertEquals("The size of the List was not as expected.", 0, GList.getSize());
        Assert.assertEquals("The List Item returned was not as expected.", null, GList.getItem(0));
    }

    @Test
    public void TestAddItemWith0Items ()
    {
        Assert.assertEquals("The size of the List was not as expected.", 0, GList.getSize());

        GList.addItem(item1);

        Assert.assertEquals("The size of the List was not as expected.", 1, GList.getSize());
        Assert.assertEquals("The List Item1 returned was not as expected.", item1, GList.getItem(0));
    }

    @Test
    public void TestAddItemWith1Item ()
    {
        Assert.assertEquals("The size of the List was not as expected.", 0, GList.getSize());

        GList.addItem(item1);

        Assert.assertEquals("The size of the List was not as expected.", 1, GList.getSize());
        Assert.assertEquals("The List Item1 returned was not as expected.", item1, GList.getItem(0));

        GList.addItem(item2);

        Assert.assertEquals("The size of the List was not as expected.", 2, GList.getSize());
        Assert.assertEquals("The List Item1 returned was not as expected.", item1, GList.getItem(0));
        Assert.assertEquals("The List Item2 returned was not as expected.", item2, GList.getItem(1));
    }

    @Test
    public void TestAddDuplicateItem () {
        Assert.assertEquals("The size of the List was not as expected.", 0, GList.getSize());

        GList.addItem(item1);

        Assert.assertEquals("The size of the List was not as expected.", 1, GList.getSize());
        Assert.assertEquals("The List Item1 returned was not as expected.", item1, GList.getItem(0));

        int expectedItem1Quantity = item1.getQuantity() + 1;

        GList.addItem(item1);

        Assert.assertEquals("The size of the List was not as expected.", 1, GList.getSize());
        Assert.assertEquals("The quantity of the list item was not as expected.", expectedItem1Quantity, GList.getItem(0).getQuantity());
        Assert.assertEquals("The List Item1 returned was not as expected.", item1, GList.getItem(0));
    }

    @Test
    public void TestDeleteListWith0Items () {
        Assert.assertEquals("The size of the List was not as expected.", 0, GList.getSize());

        GList.deleteItem(item1.getItemName());

        Assert.assertEquals("The size of the List was not as expected.", 0, GList.getSize());
    }

    @Test
    public void TestDeleteListWith1Items () {
        Assert.assertEquals("The size of the List was not as expected.", 0, GList.getSize());

        GList.addItem(item1); //add an item to delete it

        Assert.assertEquals("The size of the List was not as expected.", 1, GList.getSize());
        Assert.assertEquals("The List Item1 returned was not as expected.", item1, GList.getItem(0));

        GList.deleteItem(item1.getItemName());

        Assert.assertEquals("The size of the List was not as expected.", 0, GList.getSize());
        Assert.assertEquals("The List Item1 returned was not as expected.", null, GList.getItem(0));
    }

    @Test
    public void TestDeleteListWith3Items () {
        Assert.assertEquals("The size of the List was not as expected.", 0, GList.getSize());

        GList.addItem(item1); //add an item to delete it
        GList.addItem(item2); //add an item to delete it
        GList.addItem(item3); //add an item to delete it

        Assert.assertEquals("The size of the List was not as expected.", 3, GList.getSize());
        Assert.assertEquals("The List Item1 returned was not as expected.", item1, GList.getItem(0));
        Assert.assertEquals("The List Item1 returned was not as expected.", item2, GList.getItem(1));
        Assert.assertEquals("The List Item1 returned was not as expected.", item3, GList.getItem(2));

        GList.deleteItem(item2.getItemName());

        Assert.assertEquals("The size of the List was not as expected.", 2, GList.getSize());
        Assert.assertEquals("The List Item1 returned was not as expected.", item3, GList.getItem(1));
        Assert.assertEquals("The List Item1 returned was not as expected.", null, GList.getItem(2));
    }

    @Test
    public void TestSortbyName ()
    {
        Assert.assertEquals("The size of the List was not as expected.", 0, GList.getSize());

        GList.addItem(item1); //add an item to delete it
        GList.addItem(item2); //add an item to delete it
        GList.addItem(item3); //add an item to delete it

        Assert.assertEquals("The size of the List was not as expected.", 3, GList.getSize());
        Assert.assertEquals("The List Item1 returned was not as expected.", item1, GList.getItem(0));
        Assert.assertEquals("The List Item2 returned was not as expected.", item2, GList.getItem(1));
        Assert.assertEquals("The List Item3 returned was not as expected.", item3, GList.getItem(2));

        GList.sortListByName();

        Assert.assertEquals("The size of the List was not as expected.", 3, GList.getSize());

        Assert.assertEquals("The List Item1 returned was not as expected.", GList.getItem(0).getItemName(), item3.getItemName());
        Assert.assertEquals("The List Item2 returned was not as expected.", GList.getItem(1).getItemName(), item1.getItemName());
        Assert.assertEquals("The List Item3 returned was not as expected.", GList.getItem(2).getItemName(), item2.getItemName());
    }

}
