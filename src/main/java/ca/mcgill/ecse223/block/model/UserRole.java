/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4262.30c9ffc7c modeling language!*/

package ca.mcgill.ecse223.block.model;
import java.io.Serializable;

// line 35 "../../../../../Block223Persistence.ump"
// line 56 "../../../../../Block223.ump"
public abstract class UserRole implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //UserRole Attributes
  private String password;

  //UserRole Associations
  private Block223 block223;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public UserRole(String aPassword, Block223 aBlock223)
  {
    password = aPassword;
    boolean didAddBlock223 = setBlock223(aBlock223);
    if (!didAddBlock223)
    {
      throw new RuntimeException("Unable to create role due to block223");
    }
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
  /* Code from template association_GetOne */
  public Block223 getBlock223()
  {
    return block223;
  }
  /* Code from template association_SetOneToMany */
  public boolean setBlock223(Block223 aBlock223)
  {
    boolean wasSet = false;
    if (aBlock223 == null)
    {
      return wasSet;
    }

    Block223 existingBlock223 = block223;
    block223 = aBlock223;
    if (existingBlock223 != null && !existingBlock223.equals(aBlock223))
    {
      existingBlock223.removeRole(this);
    }
    block223.addRole(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Block223 placeholderBlock223 = block223;
    this.block223 = null;
    if(placeholderBlock223 != null)
    {
      placeholderBlock223.removeRole(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "password" + ":" + getPassword()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "block223 = "+(getBlock223()!=null?Integer.toHexString(System.identityHashCode(getBlock223())):"null");
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 38 "../../../../../Block223Persistence.ump"
  private static final long serialVersionUID = 8896099581655989380L ;

  
}