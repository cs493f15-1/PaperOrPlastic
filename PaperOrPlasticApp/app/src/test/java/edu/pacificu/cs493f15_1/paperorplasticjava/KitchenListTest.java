package edu.pacificu.cs493f15_1.paperorplasticjava;


import org.junit.*;

/**
 * Created by heyd5159 on 11/6/2015.
 */
public class KitchenListTest
{
    private KitchenList KList;
    private ListItem item1, item2, item3;

    /**
     * Creates the List and List items that may be added each test.
     */
    @Before
    public void setUp () {
        KList = new KitchenList("Temp List");
        item1 = new ListItem("A");
        item2 = new ListItem("B");
        item3 = new ListItem("3");
    }

    /**
     * Deletes all of the items in case they were changed in the test.
     */
    @After
    public void tearDown ()
    {
        KList = null;
        item1 = null;
        item2 = null;
        item3 = null;
    }

    /**
     * Tests that returnItem returns an item if the index is within bounds.
     */
    @Test
    public void TestGetItemReturnItem ()
    {
        Assert.assertEquals("The size of the List was not as expected.", 0, KList.returnSize());

        KList.addItem(item1);

        Assert.assertEquals("The size of the List was not as expected.", 1, KList.returnSize());
        Assert.assertEquals("The List Item returned was not as expected.", item1, KList.returnItem(0));
    }

    /**
     * Tests that returnItem returns null if the index is out of bounds.
     */
    @Test
    public void TestGetItemReturnNull ()
    {
        Assert.assertEquals("The size of the List was not as expected.", 0, KList.returnSize());
        Assert.assertEquals("The List Item returned was not as expected.", null, KList.returnItem(0));
    }

    /**
     * Tests that an item can be added to a List with 0 items.
     */
    @Test
    public void TestAddItemWith0Items ()
    {
        Assert.assertEquals("The size of the List was not as expected.", 0, KList.returnSize());

        KList.addItem(item1);

        Assert.assertEquals("The size of the List was not as expected.", 1, KList.returnSize());
        Assert.assertEquals("The List Item1 returned was not as expected.", item1, KList.returnItem(0));
    }

    /**
     * Tests that an item can be added to a List with 1 item.
     */
    @Test
    public void TestAddItemWith1Item ()
    {
        Assert.assertEquals("The size of the List was not as expected.", 0, KList.returnSize());

        KList.addItem(item1);

        Assert.assertEquals("The size of the List was not as expected.", 1, KList.returnSize());
        Assert.assertEquals("The List Item1 returned was not as expected.", item1, KList.returnItem(0));

        KList.addItem(item2);

        Assert.assertEquals("The size of the List was not as expected.", 2, KList.returnSize());
        Assert.assertEquals("The List Item1 returned was not as expected.", item1, KList.returnItem(0));
        Assert.assertEquals("The List Item2 returned was not as expected.", item2, KList.returnItem(1));
    }

    /**
     * Tests that adding a duplicate item increases the quantity of that item.
     */
    @Test
    public void TestAddDuplicateItem () {
        Assert.assertEquals("The size of the List was not as expected.", 0, KList.returnSize());

        KList.addItem(item1);

        Assert.assertEquals("The size of the List was not as expected.", 1, KList.returnSize());
        Assert.assertEquals("The List Item1 returned was not as expected.", item1, KList.returnItem(0));

        int expectedItem1Quantity = item1.getQuantity() + 1;

        KList.addItem(item1);

        Assert.assertEquals("The size of the List was not as expected.", 1, KList.returnSize());
        Assert.assertEquals("The quantity of the list item was not as expected.", expectedItem1Quantity, KList.returnItem(0).getQuantity());
        Assert.assertEquals("The List Item1 returned was not as expected.", item1, KList.returnItem(0));
    }

    /**
     * Tests that a list can be deleted from if there is no items in the list.
     */
    @Test
    public void TestDeleteListWith0Items () {
        Assert.assertEquals("The size of the List was not as expected.", 0, KList.returnSize());

        KList.deleteItem(item1.getItemName());

        Assert.assertEquals("The size of the List was not as expected.", 0, KList.returnSize());
    }

    /**
     * Tests that deleting an item from a list reduces the size of the list.
     */
    @Test
    public void TestDeleteListWith1Items () {
        Assert.assertEquals("The size of the List was not as expected.", 0, KList.returnSize());

        KList.addItem(item1); //add an item to delete it

        Assert.assertEquals("The size of the List was not as expected.", 1, KList.returnSize());
        Assert.assertEquals("The List Item1 returned was not as expected.", item1, KList.returnItem(0));

        KList.deleteItem(item1.getItemName());

        Assert.assertEquals("The size of the List was not as expected.", 0, KList.returnSize());
        Assert.assertEquals("The List Item1 returned was not as expected.", null, KList.returnItem(0));
    }


    /**
     * Tests that deleting an item from a list reduces the size of the list and deletes the correct one.
     */
    @Test
    public void TestDeleteListWith3Items () {
        Assert.assertEquals("The size of the List was not as expected.", 0, KList.returnSize());

        KList.addItem(item1); //add an item to delete it
        KList.addItem(item2); //add an item to delete it
        KList.addItem(item3); //add an item to delete it

        Assert.assertEquals("The size of the List was not as expected.", 3, KList.returnSize());
        Assert.assertEquals("The List Item1 returned was not as expected.", item1, KList.returnItem(0));
        Assert.assertEquals("The List Item1 returned was not as expected.", item2, KList.returnItem(1));
        Assert.assertEquals("The List Item1 returned was not as expected.", item3, KList.returnItem(2));

        KList.deleteItem(item2.getItemName());

        Assert.assertEquals("The size of the List was not as expected.", 2, KList.returnSize());
        Assert.assertEquals("The List Item1 returned was not as expected.", item3, KList.returnItem(1));
        Assert.assertEquals("The List Item1 returned was not as expected.", null, KList.returnItem(2));
    }

    /**
     * Tests that sortByName sorts correctly on a list.
     */
    @Test
    public void TestSortbyName ()
    {
        Assert.assertEquals("The size of the List was not as expected.", 0, KList.returnSize());

        KList.addItem(item1); //add an item to delete it
        KList.addItem(item2); //add an item to delete it
        KList.addItem(item3); //add an item to delete it

        Assert.assertEquals("The size of the List was not as expected.", 3, KList.returnSize());
        Assert.assertEquals("The List Item1 returned was not as expected.", item1, KList.returnItem(0));
        Assert.assertEquals("The List Item2 returned was not as expected.", item2, KList.returnItem(1));
        Assert.assertEquals("The List Item3 returned was not as expected.", item3, KList.returnItem(2));

        KList.sortListByName();

        Assert.assertEquals("The size of the List was not as expected.", 3, KList.returnSize());

        Assert.assertEquals("The List Item1 returned was not as expected.", KList.returnItem(0).getItemName(), item3.getItemName());
        Assert.assertEquals("The List Item2 returned was not as expected.", KList.returnItem(1).getItemName(), item1.getItemName());
        Assert.assertEquals("The List Item3 returned was not as expected.", KList.returnItem(2).getItemName(), item2.getItemName());
    }
}
