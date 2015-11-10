package edu.pacificu.cs493f15_1.paperorplasticjava;

/**
 * Created by heyd5159 on 11/5/2015.
 */

import org.junit.Assert;
import org.junit.Test;

/**
 *  Tests all the methods of ListItem that are not just regular set/get functions or I/O
 */
public class ListItemTest
{
    ListItem.Comparators comparators;

    /**
     * Tests SetNotes where the Note is less than the Max char limit set
     */
    @Test
    public void TestSetNotesLessthanMAX ()
    {
        String expectedString = "Hello World!", actualString;
        ListItem listItem = new ListItem("I can not come up with a name");
        listItem.setNotes(expectedString);
        actualString = listItem.getNotes();
        Assert.assertEquals("The returned Notes were not as expected", expectedString, actualString);
    }

    /**
     * Tests SetNotes where the Note is greater than the Max char limit set
     */
    @Test
    public void TestSetNotesMorethanMAX ()
    {
        String expectedString = "abcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehi";
        String inputString = expectedString + "123456789", actualString; //inputString is greater than the max
        ListItem listItem = new ListItem("I can not come up with a name");
        listItem.setNotes(inputString);
        actualString = listItem.getNotes();
        Assert.assertEquals("The returned Notes were not as expected",  expectedString, actualString);
    }

    /**
     * Tests that the Comparator NAME compares the name of the listItems
     */
    @Test
    public void TestComparatorNAME ()
    {
        ListItem listItem1 = new ListItem("Banana"), listItem2 = new ListItem("Apple");
        int expectedResult = listItem1.getItemName().compareTo(listItem2.getItemName());

        Assert.assertEquals("The compared results were not as expected.",  expectedResult, comparators.NAME.compare(listItem1, listItem2));
    }
}
