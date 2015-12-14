/**************************************************************************************************
 *   File:     FirebaseUser.java
 *   Author:   Brianna Alcoran - alco8653
 *   Date:     11/19/15
 *   Class:    Capstone/Software Engineering
 *   Project:  PaperOrPlastic Application
 *   Purpose:  This class represents a Firebase User of our application.
 ***************************************************************************************************/

package edu.pacificu.cs493f15_1.paperorplasticjava;

import android.os.Parcel;
import android.os.Parcelable;

import com.firebase.client.Firebase;
/**
 * Created by alco8653 on 11/19/2015.
*/
public class FirebaseUser implements Parcelable
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

  public FirebaseUser (Parcel in)
  {
    String[] data = new String[5];

    in.readStringArray(data);
    this.mUID = data[0];
    this.mProvider = data[1];
    this.mEmail = data[2];
    this.mMyRef = new Firebase(data[3]); //TODO: this line might not work right
    this.mbRememberPass = Boolean.parseBoolean(data[4]);
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

  public void setMyRefNull()
  {
    mMyRef = null;
  }


  public Firebase getMyRef()
  {
    return mMyRef;
  }


  @Override
  public int describeContents()
  {     // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags)
  {// TODO Auto-generated method stub
    dest.writeStringArray (new String[]
            {
                    this.mUID,
                    this.mProvider,
                    this.mEmail,
                    this.mMyRef.toString(),
                    String.valueOf(this.mbRememberPass)
            });
  }

  public static final Parcelable.Creator<FirebaseUser> CREATOR= new Parcelable.Creator<FirebaseUser>()
  {

    @Override
    public FirebaseUser createFromParcel(Parcel source)
    {   // TODO Auto-generated method stub
      return new FirebaseUser(source);  //using parcelable constructor
    }

    @Override
    public FirebaseUser[] newArray(int size)
    { // TODO Auto-generated method stub
      return new FirebaseUser[size];
    }
  };


}
