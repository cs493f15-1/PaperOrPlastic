/**************************************************************************************************
 *   File:     FirebaseUser.java
 *   Author:   Brianna Alcoran - alco8653
 *   Date:     11/19/15
 *   Class:    Capstone/Software Engineering
 *   Project:  PaperOrPlastic Application
 *   Purpose:  This class represents a Firebase User of our application.
 ***************************************************************************************************/

package edu.pacificu.cs493f15_1.paperorplasticjava;

import com.firebase.client.Firebase;
/**
 * Created by alco8653 on 11/19/2015.
*/
public class FirebaseUser
{
  private String    mUID, mProvider, mEmail;
  private Firebase  mMyRef = null;
  private boolean   mbRememberPass;

  private String FIREBASE_URL = "https://boiling-fire-3734.firebaseio.com/";

  public FirebaseUser ( String uid )
  {
    mUID = uid;
    mMyRef = new Firebase(FIREBASE_URL + "users/" + mUID + "/");
    mbRememberPass = false;
  }

  public void setmUID (String uid)
  {
    mUID = uid;
  }

  public String getmUID ()
  {
    return mUID;
  }


  public void setmProvider (String provider)
  {
    mProvider = provider;
  }

  public String getmProvider  ()
  {
    return mProvider;
  }

  public void setmEmail (String email)
  {
    mEmail = email;
  }

  public String getmEmail  ()
  {
    return mEmail;
  }


  public void setmbRememberPass (boolean bRemember)
  {
    mbRememberPass = bRemember;
  }

  public boolean getmbRememberPass()
  {
    return mbRememberPass;
  }

  public Firebase getMyRef()
  {
    return mMyRef;
  }



}
