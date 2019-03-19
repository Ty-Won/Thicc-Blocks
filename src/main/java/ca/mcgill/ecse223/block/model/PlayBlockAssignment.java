/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;

// line 34 "../../../../../Block223PlayGame.ump"
public class PlayBlockAssignment
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PlayBlockAssignment Attributes
  private int blockPosX;
  private int blockPosY;

  //PlayBlockAssignment Associations
  private Block block;
  private PlayGame playGame;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PlayBlockAssignment(int aBlockPosX, int aBlockPosY, Block aBlock, PlayGame aPlayGame)
  {
    blockPosX = aBlockPosX;
    blockPosY = aBlockPosY;
    boolean didAddBlock = setBlock(aBlock);
    if (!didAddBlock)
    {
      throw new RuntimeException("Unable to create playBlockAssignment due to block");
    }
    boolean didAddPlayGame = setPlayGame(aPlayGame);
    if (!didAddPlayGame)
    {
      throw new RuntimeException("Unable to create playBlockAssignment due to playGame");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setBlockPosX(int aBlockPosX)
  {
    boolean wasSet = false;
    blockPosX = aBlockPosX;
    wasSet = true;
    return wasSet;
  }

  public boolean setBlockPosY(int aBlockPosY)
  {
    boolean wasSet = false;
    blockPosY = aBlockPosY;
    wasSet = true;
    return wasSet;
  }

  public int getBlockPosX()
  {
    return blockPosX;
  }

  public int getBlockPosY()
  {
    return blockPosY;
  }
  /* Code from template association_GetOne */
  public Block getBlock()
  {
    return block;
  }
  /* Code from template association_GetOne */
  public PlayGame getPlayGame()
  {
    return playGame;
  }
  /* Code from template association_SetOneToMany */
  public boolean setBlock(Block aBlock)
  {
    boolean wasSet = false;
    if (aBlock == null)
    {
      return wasSet;
    }

    Block existingBlock = block;
    block = aBlock;
    if (existingBlock != null && !existingBlock.equals(aBlock))
    {
      existingBlock.removePlayBlockAssignment(this);
    }
    block.addPlayBlockAssignment(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setPlayGame(PlayGame aPlayGame)
  {
    boolean wasSet = false;
    if (aPlayGame == null)
    {
      return wasSet;
    }

    PlayGame existingPlayGame = playGame;
    playGame = aPlayGame;
    if (existingPlayGame != null && !existingPlayGame.equals(aPlayGame))
    {
      existingPlayGame.removePlayBlockAssignment(this);
    }
    playGame.addPlayBlockAssignment(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Block placeholderBlock = block;
    this.block = null;
    if(placeholderBlock != null)
    {
      placeholderBlock.removePlayBlockAssignment(this);
    }
    PlayGame placeholderPlayGame = playGame;
    this.playGame = null;
    if(placeholderPlayGame != null)
    {
      placeholderPlayGame.removePlayBlockAssignment(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "blockPosX" + ":" + getBlockPosX()+ "," +
            "blockPosY" + ":" + getBlockPosY()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "block = "+(getBlock()!=null?Integer.toHexString(System.identityHashCode(getBlock())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "playGame = "+(getPlayGame()!=null?Integer.toHexString(System.identityHashCode(getPlayGame())):"null");
  }
}