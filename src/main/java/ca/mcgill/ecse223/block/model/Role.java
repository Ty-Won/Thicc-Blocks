/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;

/**
 * I added the abstract getPassword() method here because
 * umple requires an abstract method in order to make the class
 * abstract. We should ask if having this is necessary - Michael S
 */
// line 49 "../../../../../Block223.ump"
public abstract class Role
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Role()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  public abstract String getPassword();
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 50 "../../../../../Block223.ump"
  protected String password ;

  
}