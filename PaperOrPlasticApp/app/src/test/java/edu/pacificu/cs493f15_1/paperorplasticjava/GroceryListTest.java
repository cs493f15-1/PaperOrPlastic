package edu.pacificu.cs493f15_1.paperorplasticjava;

import static org.mockito.Mockito.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by heyd5159 on 11/6/2015.
 */
public class GroceryListTest
{
    ListItem.Comparators comparators;


    @Test
    public void TestSetNotesLessthanMAX ()
    {
        String expectedString = "Hello World!", actualString;
        ListItem listItem = new ListItem("I can not come up with a name");
        listItem.setNotes(expectedString);
        actualString = listItem.getNotes();
        Assert.assertEquals(expectedString, actualString);
    }

    @Test
    public void TestSetNotesMorethanMAX ()
    {
        String expectedString = "abcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehi";
        String inputString = expectedString + "123456789", actualString; //inputString is greater than the max
        ListItem listItem = new ListItem("I can not come up with a name");
        listItem.setNotes(inputString);
        actualString = listItem.getNotes();
        Assert.assertEquals(expectedString, actualString);
    }

    @Test
    public void TestComparatorNAME ()
    {
        ListItem listItem1 = new ListItem("Banana"), listItem2 = new ListItem("Apple");
        int expectedResult = listItem1.getItemName().compareTo(listItem2.getItemName());

        Assert.assertEquals(expectedResult, comparators.NAME.compare(listItem1, listItem2));
    }
}
