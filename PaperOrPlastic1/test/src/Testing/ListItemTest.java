package Testing;

import org.junit.Test;
import lists.ListItem;
import static org.junit.Assert.assertEquals;
/**
 * Created by heyd5159 on 10/27/2015.
 */
public class ListItemTest
{
    @Test
    public void TestSetNotesLessthanMAX ()
    {
        String expectedString = "Hello World!", actualString;
        ListItem listItem = new ListItem("I can not come up with a name");
        listItem.setNotes(expectedString);
        actualString = listItem.getNotes();
        assertEquals (expectedString, actualString);
    }

    @Test
    public void TestSetNotesMorethanMAX ()
    {
        String expectedString = "abcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehiabcdefgehi";
        String inputString = expectedString + "123456789", actualString; //inputString is greater than the max
        ListItem listItem = new ListItem("I can not come up with a name");
        listItem.setNotes(inputString);
        actualString = listItem.getNotes();
        assertEquals (expectedString, actualString);
    }
}
