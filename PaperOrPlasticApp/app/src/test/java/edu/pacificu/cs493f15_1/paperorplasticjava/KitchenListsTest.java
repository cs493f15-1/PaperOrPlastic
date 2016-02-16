package edu.pacificu.cs493f15_1.paperorplasticjava;


import org.junit.*;

/**
 * Created by heyd5159 on 11/6/2015.
 */
public class KitchenListsTest
{
    private KitchenLists KLists;
    private String kListName0 = "List 1", kListName1 = "List 2";

    /**
     * Creates a new KitchenLists in case it was changed in the function
     */
    @Before
    public void setUp () {
        KLists = new KitchenLists();
    }

    /**
     * Deletes the KitchenLists
     */
    @After
    public void tearDown ()
    {
        KLists = null;
    }

    /**
     * Tests adding a list can be done to a KitchenLists with 0 lists
     */
    @Test
    public void TestAddItemWith0Items ()
    {
        Assert.assertEquals("The number of Lists was not as expected.", 0, KLists.returnSize());

        KLists.addList(kListName0);

        Assert.assertEquals("The size of the List was not as expected.", 1, KLists.returnSize());
        Assert.assertEquals("The List1 returned was not as expected.", kListName0, KLists.getList(0).getmListName());
    }

    /**
     * Tests adding a list can be done to a KitchenLists with 1 list
     */
    @Test
    public void TestAddItemWith1Item ()
    {
        Assert.assertEquals("The number of Lists was not as expected.", 0, KLists.returnSize());

        KLists.addList(kListName0);

        Assert.assertEquals("The size of the List was not as expected.", 1, KLists.returnSize());
        Assert.assertEquals("The List1 returned was not as expected.", kListName0, KLists.getList(0).getmListName());

        KLists.addList(kListName1);

        Assert.assertEquals("The size of the List was not as expected.", 2, KLists.returnSize());
        Assert.assertEquals("The List1 returned was not as expected.", kListName0, KLists.getList(0).getmListName());
        Assert.assertEquals("The List1 returned was not as expected.", kListName1, KLists.getList(1).getmListName());
    }

    /**
     * Tests a list can be deleted from a KitchenLists
     */
    @Test
    public void TestDeleteListWith1Items () {
        Assert.assertEquals("The number of Lists was not as expected.", 0, KLists.returnSize());

        KLists.addList(kListName0);

        Assert.assertEquals("The size of the List was not as expected.", 1, KLists.returnSize());
        Assert.assertEquals("The List1 returned was not as expected.", kListName0, KLists.getList(0).getmListName());

        KLists.deleteList(0);

        Assert.assertEquals("The size of the List was not as expected.", 0, KLists.returnSize());
    }

}
