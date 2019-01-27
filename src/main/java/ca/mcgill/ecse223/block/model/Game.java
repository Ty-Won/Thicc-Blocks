/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;
import java.util.*;

// line 27 "../../../../../Block223.ump"
public class Game
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, Game> gamesByName = new HashMap<String, Game>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Game Attributes
  private int minSpeed;
  private int numberOfLevels;
  private int speedIncreaseFactor;
  private String name;
  private int minPaddleLength;
  private int maxPaddleLength;
  private int gameWidth;
  private int gameHeight;

  //Game Associations
  private List<GameBlock> gameBlocks;
  private List<Level> levels;
  private Block223 block223;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Game(int aMinSpeed, int aNumberOfLevels, int aSpeedIncreaseFactor, String aName, int aMinPaddleLength, int aMaxPaddleLength, int aGameWidth, int aGameHeight, Block223 aBlock223)
  {
    minSpeed = aMinSpeed;
    numberOfLevels = aNumberOfLevels;
    speedIncreaseFactor = aSpeedIncreaseFactor;
    minPaddleLength = aMinPaddleLength;
    maxPaddleLength = aMaxPaddleLength;
    gameWidth = aGameWidth;
    gameHeight = aGameHeight;
    if (!setName(aName))
    {
      throw new RuntimeException("Cannot create due to duplicate name");
    }
    gameBlocks = new ArrayList<GameBlock>();
    levels = new ArrayList<Level>();
    boolean didAddBlock223 = setBlock223(aBlock223);
    if (!didAddBlock223)
    {
      throw new RuntimeException("Unable to create game due to block223");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setMinSpeed(int aMinSpeed)
  {
    boolean wasSet = false;
    minSpeed = aMinSpeed;
    wasSet = true;
    return wasSet;
  }

  public boolean setNumberOfLevels(int aNumberOfLevels)
  {
    boolean wasSet = false;
    numberOfLevels = aNumberOfLevels;
    wasSet = true;
    return wasSet;
  }

  public boolean setSpeedIncreaseFactor(int aSpeedIncreaseFactor)
  {
    boolean wasSet = false;
    speedIncreaseFactor = aSpeedIncreaseFactor;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    String anOldName = getName();
    if (hasWithName(aName)) {
      return wasSet;
    }
    name = aName;
    wasSet = true;
    if (anOldName != null) {
      gamesByName.remove(anOldName);
    }
    gamesByName.put(aName, this);
    return wasSet;
  }

  public boolean setMinPaddleLength(int aMinPaddleLength)
  {
    boolean wasSet = false;
    minPaddleLength = aMinPaddleLength;
    wasSet = true;
    return wasSet;
  }

  public boolean setMaxPaddleLength(int aMaxPaddleLength)
  {
    boolean wasSet = false;
    maxPaddleLength = aMaxPaddleLength;
    wasSet = true;
    return wasSet;
  }

  public boolean setGameWidth(int aGameWidth)
  {
    boolean wasSet = false;
    gameWidth = aGameWidth;
    wasSet = true;
    return wasSet;
  }

  public boolean setGameHeight(int aGameHeight)
  {
    boolean wasSet = false;
    gameHeight = aGameHeight;
    wasSet = true;
    return wasSet;
  }

  public int getMinSpeed()
  {
    return minSpeed;
  }

  public int getNumberOfLevels()
  {
    return numberOfLevels;
  }

  public int getSpeedIncreaseFactor()
  {
    return speedIncreaseFactor;
  }

  public String getName()
  {
    return name;
  }
  /* Code from template attribute_GetUnique */
  public static Game getWithName(String aName)
  {
    return gamesByName.get(aName);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithName(String aName)
  {
    return getWithName(aName) != null;
  }

  public int getMinPaddleLength()
  {
    return minPaddleLength;
  }

  public int getMaxPaddleLength()
  {
    return maxPaddleLength;
  }

  public int getGameWidth()
  {
    return gameWidth;
  }

  public int getGameHeight()
  {
    return gameHeight;
  }
  /* Code from template association_GetMany */
  public GameBlock getGameBlock(int index)
  {
    GameBlock aGameBlock = gameBlocks.get(index);
    return aGameBlock;
  }

  public List<GameBlock> getGameBlocks()
  {
    List<GameBlock> newGameBlocks = Collections.unmodifiableList(gameBlocks);
    return newGameBlocks;
  }

  public int numberOfGameBlocks()
  {
    int number = gameBlocks.size();
    return number;
  }

  public boolean hasGameBlocks()
  {
    boolean has = gameBlocks.size() > 0;
    return has;
  }

  public int indexOfGameBlock(GameBlock aGameBlock)
  {
    int index = gameBlocks.indexOf(aGameBlock);
    return index;
  }
  /* Code from template association_GetMany */
  public Level getLevel(int index)
  {
    Level aLevel = levels.get(index);
    return aLevel;
  }

  public List<Level> getLevels()
  {
    List<Level> newLevels = Collections.unmodifiableList(levels);
    return newLevels;
  }

  public int numberOfLevels()
  {
    int number = levels.size();
    return number;
  }

  public boolean hasLevels()
  {
    boolean has = levels.size() > 0;
    return has;
  }

  public int indexOfLevel(Level aLevel)
  {
    int index = levels.indexOf(aLevel);
    return index;
  }
  /* Code from template association_GetOne */
  public Block223 getBlock223()
  {
    return block223;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGameBlocks()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public GameBlock addGameBlock(String aHexColor, int aPointValue)
  {
    return new GameBlock(aHexColor, aPointValue, this);
  }

  public boolean addGameBlock(GameBlock aGameBlock)
  {
    boolean wasAdded = false;
    if (gameBlocks.contains(aGameBlock)) { return false; }
    Game existingGame = aGameBlock.getGame();
    boolean isNewGame = existingGame != null && !this.equals(existingGame);
    if (isNewGame)
    {
      aGameBlock.setGame(this);
    }
    else
    {
      gameBlocks.add(aGameBlock);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGameBlock(GameBlock aGameBlock)
  {
    boolean wasRemoved = false;
    //Unable to remove aGameBlock, as it must always have a game
    if (!this.equals(aGameBlock.getGame()))
    {
      gameBlocks.remove(aGameBlock);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addGameBlockAt(GameBlock aGameBlock, int index)
  {  
    boolean wasAdded = false;
    if(addGameBlock(aGameBlock))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGameBlocks()) { index = numberOfGameBlocks() - 1; }
      gameBlocks.remove(aGameBlock);
      gameBlocks.add(index, aGameBlock);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveGameBlockAt(GameBlock aGameBlock, int index)
  {
    boolean wasAdded = false;
    if(gameBlocks.contains(aGameBlock))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGameBlocks()) { index = numberOfGameBlocks() - 1; }
      gameBlocks.remove(aGameBlock);
      gameBlocks.add(index, aGameBlock);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addGameBlockAt(aGameBlock, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfLevels()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Level addLevel(int aNumber)
  {
    return new Level(aNumber, this);
  }

  public boolean addLevel(Level aLevel)
  {
    boolean wasAdded = false;
    if (levels.contains(aLevel)) { return false; }
    Game existingGame = aLevel.getGame();
    boolean isNewGame = existingGame != null && !this.equals(existingGame);
    if (isNewGame)
    {
      aLevel.setGame(this);
    }
    else
    {
      levels.add(aLevel);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeLevel(Level aLevel)
  {
    boolean wasRemoved = false;
    //Unable to remove aLevel, as it must always have a game
    if (!this.equals(aLevel.getGame()))
    {
      levels.remove(aLevel);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addLevelAt(Level aLevel, int index)
  {  
    boolean wasAdded = false;
    if(addLevel(aLevel))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLevels()) { index = numberOfLevels() - 1; }
      levels.remove(aLevel);
      levels.add(index, aLevel);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveLevelAt(Level aLevel, int index)
  {
    boolean wasAdded = false;
    if(levels.contains(aLevel))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLevels()) { index = numberOfLevels() - 1; }
      levels.remove(aLevel);
      levels.add(index, aLevel);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addLevelAt(aLevel, index);
    }
    return wasAdded;
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
      existingBlock223.removeGame(this);
    }
    block223.addGame(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    gamesByName.remove(getName());
    while (gameBlocks.size() > 0)
    {
      GameBlock aGameBlock = gameBlocks.get(gameBlocks.size() - 1);
      aGameBlock.delete();
      gameBlocks.remove(aGameBlock);
    }
    
    while (levels.size() > 0)
    {
      Level aLevel = levels.get(levels.size() - 1);
      aLevel.delete();
      levels.remove(aLevel);
    }
    
    Block223 placeholderBlock223 = block223;
    this.block223 = null;
    if(placeholderBlock223 != null)
    {
      placeholderBlock223.removeGame(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "minSpeed" + ":" + getMinSpeed()+ "," +
            "numberOfLevels" + ":" + getNumberOfLevels()+ "," +
            "speedIncreaseFactor" + ":" + getSpeedIncreaseFactor()+ "," +
            "name" + ":" + getName()+ "," +
            "minPaddleLength" + ":" + getMinPaddleLength()+ "," +
            "maxPaddleLength" + ":" + getMaxPaddleLength()+ "," +
            "gameWidth" + ":" + getGameWidth()+ "," +
            "gameHeight" + ":" + getGameHeight()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "block223 = "+(getBlock223()!=null?Integer.toHexString(System.identityHashCode(getBlock223())):"null");
  }
}