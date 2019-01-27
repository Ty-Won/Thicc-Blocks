/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;
import java.util.*;

// line 13 "../../../../../Block223.ump"
public class GameBlock
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GameBlock Attributes
  private String hexColor;
  private int pointValue;

  //GameBlock Associations
  private List<LevelBlock> levelBlocks;
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public GameBlock(String aHexColor, int aPointValue, Game aGame)
  {
    hexColor = aHexColor;
    pointValue = aPointValue;
    levelBlocks = new ArrayList<LevelBlock>();
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create gameBlock due to game");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setHexColor(String aHexColor)
  {
    boolean wasSet = false;
    hexColor = aHexColor;
    wasSet = true;
    return wasSet;
  }

  public boolean setPointValue(int aPointValue)
  {
    boolean wasSet = false;
    pointValue = aPointValue;
    wasSet = true;
    return wasSet;
  }

  /**
   * Color represented by a hex
   */
  public String getHexColor()
  {
    return hexColor;
  }

  public int getPointValue()
  {
    return pointValue;
  }
  /* Code from template association_GetMany */
  public LevelBlock getLevelBlock(int index)
  {
    LevelBlock aLevelBlock = levelBlocks.get(index);
    return aLevelBlock;
  }

  public List<LevelBlock> getLevelBlocks()
  {
    List<LevelBlock> newLevelBlocks = Collections.unmodifiableList(levelBlocks);
    return newLevelBlocks;
  }

  public int numberOfLevelBlocks()
  {
    int number = levelBlocks.size();
    return number;
  }

  public boolean hasLevelBlocks()
  {
    boolean has = levelBlocks.size() > 0;
    return has;
  }

  public int indexOfLevelBlock(LevelBlock aLevelBlock)
  {
    int index = levelBlocks.indexOf(aLevelBlock);
    return index;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfLevelBlocks()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public LevelBlock addLevelBlock(int aXPosition, int aYPosition, Level aLevel)
  {
    return new LevelBlock(aXPosition, aYPosition, this, aLevel);
  }

  public boolean addLevelBlock(LevelBlock aLevelBlock)
  {
    boolean wasAdded = false;
    if (levelBlocks.contains(aLevelBlock)) { return false; }
    GameBlock existingAssociatedBlock = aLevelBlock.getAssociatedBlock();
    boolean isNewAssociatedBlock = existingAssociatedBlock != null && !this.equals(existingAssociatedBlock);
    if (isNewAssociatedBlock)
    {
      aLevelBlock.setAssociatedBlock(this);
    }
    else
    {
      levelBlocks.add(aLevelBlock);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeLevelBlock(LevelBlock aLevelBlock)
  {
    boolean wasRemoved = false;
    //Unable to remove aLevelBlock, as it must always have a associatedBlock
    if (!this.equals(aLevelBlock.getAssociatedBlock()))
    {
      levelBlocks.remove(aLevelBlock);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addLevelBlockAt(LevelBlock aLevelBlock, int index)
  {  
    boolean wasAdded = false;
    if(addLevelBlock(aLevelBlock))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLevelBlocks()) { index = numberOfLevelBlocks() - 1; }
      levelBlocks.remove(aLevelBlock);
      levelBlocks.add(index, aLevelBlock);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveLevelBlockAt(LevelBlock aLevelBlock, int index)
  {
    boolean wasAdded = false;
    if(levelBlocks.contains(aLevelBlock))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLevelBlocks()) { index = numberOfLevelBlocks() - 1; }
      levelBlocks.remove(aLevelBlock);
      levelBlocks.add(index, aLevelBlock);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addLevelBlockAt(aLevelBlock, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    if (aGame == null)
    {
      return wasSet;
    }

    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      existingGame.removeGameBlock(this);
    }
    game.addGameBlock(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(int i=levelBlocks.size(); i > 0; i--)
    {
      LevelBlock aLevelBlock = levelBlocks.get(i - 1);
      aLevelBlock.delete();
    }
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removeGameBlock(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "hexColor" + ":" + getHexColor()+ "," +
            "pointValue" + ":" + getPointValue()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null");
  }
}