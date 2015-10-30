package edu.pacificu.cs493f15_1.paperorplasticjava;

/**
 * Created by sull0678 on 10/12/2015.
 */
public class KitchenList extends PoPList
{
    boolean bIsLinked;
    boolean bIsShared;

    public KitchenList (String name)
    {
        mListName = name;
        mSize = 0;
    }
    /*Functions
        Eat ()
        ThrowAway () (these two functions matter if we are watching the users diet
        LinkListToGrocery (groceryList);
        unLinkList ();
        shareList (); (with permissions?)
        unShareList ();

     */
}
