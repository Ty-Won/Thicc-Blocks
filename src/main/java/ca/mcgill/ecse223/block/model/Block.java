/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;
import java.io.Serializable;
import java.util.*;

// line 65 "../../../../../Block223Persistence.ump"
// line 67 "../../../../../Block223.ump"
public class Block implements Serializable
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  public static final int MIN_COLOR = 0;
  public static final int MAX_COLOR = 255;
  public static final int MIN_POINTS = 1;
  public static final int MAX_POINTS = 1000;
  public static final int SIZE = 20;
  private static int nextId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Block Attributes
  private int red;
  private int green;
  private int blue;
  private int points;

  //Autounique Attributes
  private int id;

  //Block Associations
  private Game game;
  private List<BlockAssignment> blockAssignments;
  private List<PlayBlockAssignment> playBlockAssignments;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Block(int aRed, int aGreen, int aBlue, int aPoints, Game aGame)
  {
    // line 80 "../../../../../Block223.ump"
    if (aRed < 0 || aRed > 255) {
       			throw new RuntimeException("Red invalid");
       		}
       		if (aGreen < 0 || aGreen > 255) {
       			throw new RuntimeException("Green invalid");
       		}
       		if (aBlue < 0 || aBlue > 255) {
       			throw new RuntimeException("Blue invalid");
       		}
       		if (aPoints < 1 || aPoints > 1000) {
       			throw new RuntimeException("Points invalid");
       		}
    // END OF UMPLE BEFORE INJECTION
    red = aRed;
    green = aGreen;
    blue = aBlue;
    points = aPoints;
    id = nextId++;
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create block due to game");
    }
    blockAssignments = new ArrayList<BlockAssignment>();
    playBlockAssignments = new ArrayList<PlayBlockAssignment>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRed(int aRed)
  {
    boolean wasSet = false;
    red = aRed;
    wasSet = true;
    return wasSet;
  }

  public boolean setGreen(int aGreen)
  {
    boolean wasSet = false;
    green = aGreen;
    wasSet = true;
    return wasSet;
  }

  public boolean setBlue(int aBlue)
  {
    boolean wasSet = false;
    blue = aBlue;
    wasSet = true;
    return wasSet;
  }

  public boolean setPoints(int aPoints)
  {
    boolean wasSet = false;
    points = aPoints;
    wasSet = true;
    return wasSet;
  }

  public int getRed()
  {
    return red;
  }

  public int getGreen()
  {
    return green;
  }

  public int getBlue()
  {
    return blue;
  }

  public int getPoints()
  {
    return points;
  }

  public int getId()
  {
    return id;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_GetMany */
  public BlockAssignment getBlockAssignment(int index)
  {
    BlockAssignment aBlockAssignment = blockAssignments.get(index);
    return aBlockAssignment;
  }

  public List<BlockAssignment> getBlockAssignments()
  {
    List<BlockAssignment> newBlockAssignments = Collections.unmodifiableList(blockAssignments);
    return newBlockAssignments;
  }

  public int numberOfBlockAssignments()
  {
    int number = blockAssignments.size();
    return number;
  }

  public boolean hasBlockAssignments()
  {
    boolean has = blockAssignments.size() > 0;
    return has;
  }

  public int indexOfBlockAssignment(BlockAssignment aBlockAssignment)
  {
    int index = blockAssignments.indexOf(aBlockAssignment);
    return index;
  }
  /* Code from template association_GetMany */
  public PlayBlockAssignment getPlayBlockAssignment(int index)
  {
    PlayBlockAssignment aPlayBlockAssignment = playBlockAssignments.get(index);
    return aPlayBlockAssignment;
  }

  public List<PlayBlockAssignment> getPlayBlockAssignments()
  {
    List<PlayBlockAssignment> newPlayBlockAssignments = Collections.unmodifiableList(playBlockAssignments);
    return newPlayBlockAssignments;
  }

  public int numberOfPlayBlockAssignments()
  {
    int number = playBlockAssignments.size();
    return number;
  }

  public boolean hasPlayBlockAssignments()
  {
    boolean has = playBlockAssignments.size() > 0;
    return has;
  }

  public int indexOfPlayBlockAssignment(PlayBlockAssignment aPlayBlockAssignment)
  {
    int index = playBlockAssignments.indexOf(aPlayBlockAssignment);
    return index;
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
      existingGame.removeBlock(this);
    }
    game.addBlock(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBlockAssignments()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public BlockAssignment addBlockAssignment(int aGridHorizontalPosition, int aGridVerticalPosition, Level aLevel, Game aGame)
  {
    return new BlockAssignment(aGridHorizontalPosition, aGridVerticalPosition, aLevel, this, aGame);
  }

  public boolean addBlockAssignment(BlockAssignment aBlockAssignment)
  {
    boolean wasAdded = false;
    if (blockAssignments.contains(aBlockAssignment)) { return false; }
    Block existingBlock = aBlockAssignment.getBlock();
    boolean isNewBlock = existingBlock != null && !this.equals(existingBlock);
    if (isNewBlock)
    {
      aBlockAssignment.setBlock(this);
    }
    else
    {
      blockAssignments.add(aBlockAssignment);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBlockAssignment(BlockAssignment aBlockAssignment)
  {
    boolean wasRemoved = false;
    //Unable to remove aBlockAssignment, as it must always have a block
    if (!this.equals(aBlockAssignment.getBlock()))
    {
      blockAssignments.remove(aBlockAssignment);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBlockAssignmentAt(BlockAssignment aBlockAssignment, int index)
  {  
    boolean wasAdded = false;
    if(addBlockAssignment(aBlockAssignment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBlockAssignments()) { index = numberOfBlockAssignments() - 1; }
      blockAssignments.remove(aBlockAssignment);
      blockAssignments.add(index, aBlockAssignment);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBlockAssignmentAt(BlockAssignment aBlockAssignment, int index)
  {
    boolean wasAdded = false;
    if(blockAssignments.contains(aBlockAssignment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBlockAssignments()) { index = numberOfBlockAssignments() - 1; }
      blockAssignments.remove(aBlockAssignment);
      blockAssignments.add(index, aBlockAssignment);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBlockAssignmentAt(aBlockAssignment, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPlayBlockAssignments()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public PlayBlockAssignment addPlayBlockAssignment(int aBlockPosX, int aBlockPosY, PlayGame aPlayGame)
  {
    return new PlayBlockAssignment(aBlockPosX, aBlockPosY, this, aPlayGame);
  }

  public boolean addPlayBlockAssignment(PlayBlockAssignment aPlayBlockAssignment)
  {
    boolean wasAdded = false;
    if (playBlockAssignments.contains(aPlayBlockAssignment)) { return false; }
    Block existingBlock = aPlayBlockAssignment.getBlock();
    boolean isNewBlock = existingBlock != null && !this.equals(existingBlock);
    if (isNewBlock)
    {
      aPlayBlockAssignment.setBlock(this);
    }
    else
    {
      playBlockAssignments.add(aPlayBlockAssignment);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePlayBlockAssignment(PlayBlockAssignment aPlayBlockAssignment)
  {
    boolean wasRemoved = false;
    //Unable to remove aPlayBlockAssignment, as it must always have a block
    if (!this.equals(aPlayBlockAssignment.getBlock()))
    {
      playBlockAssignments.remove(aPlayBlockAssignment);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPlayBlockAssignmentAt(PlayBlockAssignment aPlayBlockAssignment, int index)
  {  
    boolean wasAdded = false;
    if(addPlayBlockAssignment(aPlayBlockAssignment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayBlockAssignments()) { index = numberOfPlayBlockAssignments() - 1; }
      playBlockAssignments.remove(aPlayBlockAssignment);
      playBlockAssignments.add(index, aPlayBlockAssignment);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePlayBlockAssignmentAt(PlayBlockAssignment aPlayBlockAssignment, int index)
  {
    boolean wasAdded = false;
    if(playBlockAssignments.contains(aPlayBlockAssignment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayBlockAssignments()) { index = numberOfPlayBlockAssignments() - 1; }
      playBlockAssignments.remove(aPlayBlockAssignment);
      playBlockAssignments.add(index, aPlayBlockAssignment);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPlayBlockAssignmentAt(aPlayBlockAssignment, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removeBlock(this);
    }
    for(int i=blockAssignments.size(); i > 0; i--)
    {
      BlockAssignment aBlockAssignment = blockAssignments.get(i - 1);
      aBlockAssignment.delete();
    }
    for(int i=playBlockAssignments.size(); i > 0; i--)
    {
      PlayBlockAssignment aPlayBlockAssignment = playBlockAssignments.get(i - 1);
      aPlayBlockAssignment.delete();
    }
  }

  // line 71 "../../../../../Block223Persistence.ump"
   public static  void reinitializeAutouniqueID(List<Block> blocks){
    nextId = 0;
  	for(Block block : blocks) {
  		if(block.getId() > nextId) {
  			nextId = block.getId();
  		}
  	}
  	nextId++;
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "red" + ":" + getRed()+ "," +
            "green" + ":" + getGreen()+ "," +
            "blue" + ":" + getBlue()+ "," +
            "points" + ":" + getPoints()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null");
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 68 "../../../../../Block223Persistence.ump"
  private static final long serialVersionUID = -7403802774454467836L ;

  
}