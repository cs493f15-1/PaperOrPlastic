package edu.pacificu.cs493f15_1.paperorplasticapp;

/**
 * Created by alco8653 on 10/28/2015.
 */
public class TestUser
{
  private int birthYear;
  private String fullName;

  public TestUser() {}

  public TestUser (String fullName, int birthYear) {
    this.fullName = fullName;
    this.birthYear = birthYear;
  }

  public long getBirthYear () {
    return birthYear;
  }

  public String getFullName () {
    return fullName;
  }
}
