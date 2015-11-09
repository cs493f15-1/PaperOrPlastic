package edu.pacificu.cs493f15_1.paperorplasticjava;


import org.junit.*;

/**
 * Created by heyd5159 on 11/6/2015.
 */
public class GroceryListsTest
{
    private GroceryLists GLists;
    private String gListName0 = "List 1", gListName1 = "List 2";

    @Before
    public void setUp () {
        GLists = new GroceryLists();
    }

    @After
    public void tearDown ()
    {
        GLists = null;
    }
    @Test
    public void TestAddItemWith0Items ()
    {
        Assert.assertEquals("The number of Lists was not as expected.", 0, GLists.getSize());

        GLists.addList(gListName0);

        Assert.assertEquals("The size of the List was not as expected.", 1, GLists.getSize());
        Assert.assertEquals("The List1 returned was not as expected.", gListName0, GLists.getList(0).getListName());
    }

    @Test
    public void TestAddItemWith1Item ()
    {
        Assert.assertEquals("The number of Lists was not as expected.", 0, GLists.getSize());

        GLists.addList(gListName0);

        Assert.assertEquals("The size of the List was not as expected.", 1, GLists.getSize());
        Assert.assertEquals("The List1 returned was not as expected.", gListName0, GLists.getList(0).getListName());

        GLists.addList(gListName1);

        Assert.assertEquals("The size of the List was not as expected.", 2, GLists.getSize());
        Assert.assertEquals("The List1 returned was not as expected.", gListName0, GLists.getList(0).getListName());
        Assert.assertEquals("The List1 returned was not as expected.", gListName1, GLists.getList(1).getListName());
    }

    @Test
    public void TestDeleteListWith1Items () {
        Assert.assertEquals("The number of Lists was not as expected.", 0, GLists.getSize());

        GLists.addList(gListName0);

        Assert.assertEquals("The size of the List was not as expected.", 1, GLists.getSize());
        Assert.assertEquals("The List1 returned was not as expected.", gListName0, GLists.getList(0).getListName());

        GLists.deleteList(0);

        Assert.assertEquals("The size of the List was not as expected.", 0, GLists.getSize());
    }

}
