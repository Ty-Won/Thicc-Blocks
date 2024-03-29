/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4262.30c9ffc7c modeling language!*/

package ca.mcgill.ecse223.block.model;
import java.io.Serializable;

/**
 * this class needs to be specified but the use of it is optional; it is also not added to the persistence file
 * you may implement bounce behavior differently
 */
// line 361 "../../../../../Block223PlayMode.ump"
// line 132 "../../../../../Block223Persistence.ump"
public class BouncePoint implements Serializable
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum BounceDirection { FLIP_X, FLIP_Y, FLIP_BOTH }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //BouncePoint Attributes
  private double x;
  private double y;
  private BounceDirection direction;

  //BouncePoint Associations
  private PlayedBlockAssignment hitBlock;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BouncePoint(double aX, double aY, BounceDirection aDirection)
  {
    x = aX;
    y = aY;
    direction = aDirection;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setX(double aX)
  {
    boolean wasSet = false;
    x = aX;
    wasSet = true;
    return wasSet;
  }

  public boolean setY(double aY)
  {
    boolean wasSet = false;
    y = aY;
    wasSet = true;
    return wasSet;
  }

  public boolean setDirection(BounceDirection aDirection)
  {
    boolean wasSet = false;
    direction = aDirection;
    wasSet = true;
    return wasSet;
  }

  public double getX()
  {
    return x;
  }

  public double getY()
  {
    return y;
  }

  public BounceDirection getDirection()
  {
    return direction;
  }
  /* Code from template association_GetOne */
  public PlayedBlockAssignment getHitBlock()
  {
    return hitBlock;
  }

  public boolean hasHitBlock()
  {
    boolean has = hitBlock != null;
    return has;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setHitBlock(PlayedBlockAssignment aNewHitBlock)
  {
    boolean wasSet = false;
    hitBlock = aNewHitBlock;
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    hitBlock = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "x" + ":" + getX()+ "," +
            "y" + ":" + getY()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "direction" + "=" + (getDirection() != null ? !getDirection().equals(this)  ? getDirection().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "hitBlock = "+(getHitBlock()!=null?Integer.toHexString(System.identityHashCode(getHitBlock())):"null");
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 135 "../../../../../Block223Persistence.ump"
  private static final long serialVersionUID = 6304159361985579849L ;

  
}