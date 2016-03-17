package edu.pacificu.cs493f15_1.paperorplasticjava;


import org.junit.*;

/**
 * Created by heyd5159 on 11/6/2015.
 */
public class KitchenInventoriesTest
{
    private KitchenInventories kInvs;
    private String kInvsName0 = "List 1", kInvsName1 = "List 2";

    /**
     * Creates a new KitchenInventories in case it was changed in the function
     */
    @Before
    public void setUp () {
        kInvs = new KitchenLists();
    }

    /**
     * Deletes the KitchenInventories
     */
    @After
    public void tearDown ()
    {
        kInvs = null;
    }

    /**
     * Tests adding a list can be done to a KitchenInventories with 0 lists
     */
    @Test
    public void TestAddItemWith0Items ()
    {
        Assert.assertEquals("The number of Lists was not as expected.", 0, kInvs.getSize());

        kInvs.addList(kInvsName0);

        Assert.assertEquals("The size of the List was not as expected.", 1, kInvs.getSize());
        Assert.assertEquals("The List1 returned was not as expected.", kInvsName0, kInvs.getList(0).getListName());
    }

    /**
     * Tests adding a list can be done to a KitchenInventories with 1 list
     */
    @Test
    public void TestAddItemWith1Item ()
    {
        Assert.assertEquals("The number of Lists was not as expected.", 0, kInvs.getSize());

        kInvs.addList(kInvsName0);

        Assert.assertEquals("The size of the List was not as expected.", 1, kInvs.getSize());
        Assert.assertEquals("The List1 returned was not as expected.", kInvsName0, kInvs.getList(0).getListName());

        kInvs.addList(kInvsName1);

        Assert.assertEquals("The size of the List was not as expected.", 2, kInvs.getSize());
        Assert.assertEquals("The List1 returned was not as expected.", kInvsName0, kInvs.getList(0).getListName());
        Assert.assertEquals("The List1 returned was not as expected.", kInvsName1, kInvs.getList(1).getListName());
    }

    /**
     * Tests a list can be deleted from a KitchenInventories
     */
    @Test
    public void TestDeleteListWith1Items () {
        Assert.assertEquals("The number of Lists was not as expected.", 0, kInvs.getSize());

        kInvs.addList(kInvsName0);

        Assert.assertEquals("The size of the List was not as expected.", 1, kInvs.getSize());
        Assert.assertEquals("The List1 returned was not as expected.", kInvsName0, kInvs.getList(0).getListName());

        kInvs.deleteList(0);

        Assert.assertEquals("The size of the List was not as expected.", 0, kInvs.getSize());
    }

}
