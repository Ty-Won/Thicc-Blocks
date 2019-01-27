/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;

// line 20 "../../../../../Block223.ump"
public class LevelBlock
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //LevelBlock Attributes
  private int xPosition;
  private int yPosition;

  //LevelBlock Associations
  private GameBlock associatedBlock;
  private Level level;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LevelBlock(int aXPosition, int aYPosition, GameBlock aAssociatedBlock, Level aLevel)
  {
    xPosition = aXPosition;
    yPosition = aYPosition;
    boolean didAddAssociatedBlock = setAssociatedBlock(aAssociatedBlock);
    if (!didAddAssociatedBlock)
    {
      throw new RuntimeException("Unable to create levelBlock due to associatedBlock");
    }
    boolean didAddLevel = setLevel(aLevel);
    if (!didAddLevel)
    {
      throw new RuntimeException("Unable to create levelBlock due to level");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setXPosition(int aXPosition)
  {
    boolean wasSet = false;
    xPosition = aXPosition;
    wasSet = true;
    return wasSet;
  }

  public boolean setYPosition(int aYPosition)
  {
    boolean wasSet = false;
    yPosition = aYPosition;
    wasSet = true;
    return wasSet;
  }

  public int getXPosition()
  {
    return xPosition;
  }

  public int getYPosition()
  {
    return yPosition;
  }
  /* Code from template association_GetOne */
  public GameBlock getAssociatedBlock()
  {
    return associatedBlock;
  }
  /* Code from template association_GetOne */
  public Level getLevel()
  {
    return level;
  }
  /* Code from template association_SetOneToMany */
  public boolean setAssociatedBlock(GameBlock aAssociatedBlock)
  {
    boolean wasSet = false;
    if (aAssociatedBlock == null)
    {
      return wasSet;
    }

    GameBlock existingAssociatedBlock = associatedBlock;
    associatedBlock = aAssociatedBlock;
    if (existingAssociatedBlock != null && !existingAssociatedBlock.equals(aAssociatedBlock))
    {
      existingAssociatedBlock.removeLevelBlock(this);
    }
    associatedBlock.addLevelBlock(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setLevel(Level aLevel)
  {
    boolean wasSet = false;
    if (aLevel == null)
    {
      return wasSet;
    }

    Level existingLevel = level;
    level = aLevel;
    if (existingLevel != null && !existingLevel.equals(aLevel))
    {
      existingLevel.removeLevelBlock(this);
    }
    level.addLevelBlock(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    GameBlock placeholderAssociatedBlock = associatedBlock;
    this.associatedBlock = null;
    if(placeholderAssociatedBlock != null)
    {
      placeholderAssociatedBlock.removeLevelBlock(this);
    }
    Level placeholderLevel = level;
    this.level = null;
    if(placeholderLevel != null)
    {
      placeholderLevel.removeLevelBlock(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "xPosition" + ":" + getXPosition()+ "," +
            "yPosition" + ":" + getYPosition()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "associatedBlock = "+(getAssociatedBlock()!=null?Integer.toHexString(System.identityHashCode(getAssociatedBlock())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "level = "+(getLevel()!=null?Integer.toHexString(System.identityHashCode(getLevel())):"null");
  }
}