/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;

// line 46 "../../../../../Block223.ump"
public abstract class Role
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Role Attributes
  private String password;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Role(String aPassword)
  {
    password = aPassword;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public String getPassword()
  {
    return password;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "password" + ":" + getPassword()+ "]";
  }
}