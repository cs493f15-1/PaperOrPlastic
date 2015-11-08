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
    public void TestAddItemWith0Items ()
    {
        Assert.assertEquals("The size of the List was not as expected.", 0, GList.getSize());

        GList.addItem(item1);

        Assert.assertEquals("The size of the List was not as expected.", 1, GList.getSize());
        Assert.assertEquals("The List Item returned was not as expected.", GList.getItem(0), item1);
    }

    @Test
    public void TestAddItemWith1Item ()
    {
        Assert.assertEquals("The size of the List was not as expected.", 0, GList.getSize());

        GList.addItem(item1);

        Assert.assertEquals("The size of the List was not as expected.", 1, GList.getSize());
        Assert.assertEquals("The List Item returned was not as expected.", GList.getItem(0), item1);

        GList.addItem(item2);

        Assert.assertEquals("The size of the List was not as expected.", 2, GList.getSize());
        Assert.assertEquals("The List Item returned was not as expected.", GList.getItem(0), item1);
        Assert.assertEquals("The List Item returned was not as expected.", GList.getItem(1), item2);
    }

    @Test
    public void TestAddDuplicateItem () {
        Assert.assertEquals("The size of the List was not as expected.", 0, GList.getSize());

        GList.addItem(item1);

        Assert.assertEquals("The size of the List was not as expected.", 1, GList.getSize());
        Assert.assertEquals("The List Item returned was not as expected.", GList.getItem(0), item1);

        int expectedItem1Quantity = item1.getQuantity() + 1;

        GList.addItem(item1);

        Assert.assertEquals("The size of the List was not as expected.", 1, GList.getSize());
        Assert.assertEquals("The quantity of the list item was not as expected.", expectedItem1Quantity, GList.getItem(0).getQuantity());
        Assert.assertEquals("The List Item returned was not as expected.", GList.getItem(0), item1);
    }

    @Test
    public void TestDeleteListWith0Items () {
        Assert.assertEquals("The size of the List was not as expected.", 0, GList.getSize());

        GList.addItem(item1);

        Assert.assertEquals("The size of the List was not as expected.", 1, GList.getSize());
        Assert.assertEquals("The List Item returned was not as expected.", GList.getItem(0), item1);

        int expectedItem1Quantity = item1.getQuantity() + 1;

        GList.addItem(item1);

        Assert.assertEquals("The size of the List was not as expected.", 1, GList.getSize());
        Assert.assertEquals("The quantity of the list item was not as expected.", expectedItem1Quantity, GList.getItem(0).getQuantity());
        Assert.assertEquals("The List Item returned was not as expected.", GList.getItem(0), item1);
    }

    @Test
    public void TestDeleteListWith1Items () {
        Assert.assertEquals("The size of the List was not as expected.", 0, GList.getSize());

        GList.addItem(item1);

        Assert.assertEquals("The size of the List was not as expected.", 1, GList.getSize());
        Assert.assertEquals("The List Item returned was not as expected.", GList.getItem(0), item1);

        int expectedItem1Quantity = item1.getQuantity() + 1;

        GList.addItem(item1);

        Assert.assertEquals("The size of the List was not as expected.", 1, GList.getSize());
        Assert.assertEquals("The quantity of the list item was not as expected.", expectedItem1Quantity, GList.getItem(0).getQuantity());
        Assert.assertEquals("The List Item returned was not as expected.", GList.getItem(0), item1);
    }
}
