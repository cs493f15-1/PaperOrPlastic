package edu.pacificu.cs493f15_1.paperorplasticjava;


import org.junit.*;

/**
 * Created by heyd5159 on 11/6/2015.
 */
public class KitchenListsTest
{
    private KitchenLists KLists;
    private String kListName0 = "List 1", kListName1 = "List 2";

    @Before
    public void setUp () {
        KLists = new KitchenLists();
    }

    @After
    public void tearDown ()
    {
        KLists = null;
    }
    @Test
    public void TestAddItemWith0Items ()
    {
        Assert.assertEquals("The number of Lists was not as expected.", 0, KLists.getSize());

        KLists.addList(kListName0);

        Assert.assertEquals("The size of the List was not as expected.", 1, KLists.getSize());
        Assert.assertEquals("The List1 returned was not as expected.", kListName0, KLists.getList(0).getListName());
    }

    @Test
    public void TestAddItemWith1Item ()
    {
        Assert.assertEquals("The number of Lists was not as expected.", 0, KLists.getSize());

        KLists.addList(kListName0);

        Assert.assertEquals("The size of the List was not as expected.", 1, KLists.getSize());
        Assert.assertEquals("The List1 returned was not as expected.", kListName0, KLists.getList(0).getListName());

        KLists.addList(kListName1);

        Assert.assertEquals("The size of the List was not as expected.", 2, KLists.getSize());
        Assert.assertEquals("The List1 returned was not as expected.", kListName0, KLists.getList(0).getListName());
        Assert.assertEquals("The List1 returned was not as expected.", kListName1, KLists.getList(1).getListName());
    }

    @Test
    public void TestDeleteListWith1Items () {
        Assert.assertEquals("The number of Lists was not as expected.", 0, KLists.getSize());

        KLists.addList(kListName0);

        Assert.assertEquals("The size of the List was not as expected.", 1, KLists.getSize());
        Assert.assertEquals("The List1 returned was not as expected.", kListName0, KLists.getList(0).getListName());

        KLists.deleteList(0);

        Assert.assertEquals("The size of the List was not as expected.", 0, KLists.getSize());
    }

}
