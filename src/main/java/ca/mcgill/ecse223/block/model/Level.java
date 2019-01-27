/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;
import java.util.*;

// line 8 "../../../../../Block223.ump"
public class Level
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Level Attributes
  private int number;

  //Level Associations
  private List<LevelBlock> levelBlocks;
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Level(int aNumber, Game aGame)
  {
    number = aNumber;
    levelBlocks = new ArrayList<LevelBlock>();
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create level due to game");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setNumber(int aNumber)
  {
    boolean wasSet = false;
    number = aNumber;
    wasSet = true;
    return wasSet;
  }

  public int getNumber()
  {
    return number;
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
  public LevelBlock addLevelBlock(int aXPosition, int aYPosition, GameBlock aAssociatedBlock)
  {
    return new LevelBlock(aXPosition, aYPosition, aAssociatedBlock, this);
  }

  public boolean addLevelBlock(LevelBlock aLevelBlock)
  {
    boolean wasAdded = false;
    if (levelBlocks.contains(aLevelBlock)) { return false; }
    Level existingLevel = aLevelBlock.getLevel();
    boolean isNewLevel = existingLevel != null && !this.equals(existingLevel);
    if (isNewLevel)
    {
      aLevelBlock.setLevel(this);
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
    //Unable to remove aLevelBlock, as it must always have a level
    if (!this.equals(aLevelBlock.getLevel()))
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
      existingGame.removeLevel(this);
    }
    game.addLevel(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    while (levelBlocks.size() > 0)
    {
      LevelBlock aLevelBlock = levelBlocks.get(levelBlocks.size() - 1);
      aLevelBlock.delete();
      levelBlocks.remove(aLevelBlock);
    }
    
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removeLevel(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "number" + ":" + getNumber()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null");
  }
}