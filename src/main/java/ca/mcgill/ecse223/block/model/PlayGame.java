/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;
import java.util.*;

// line 4 "../../../../../Block223PlayGame.ump"
public class PlayGame
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextSessionID = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PlayGame Attributes
  private int nrLives;
  private int currScore;
  private int currLevel;
  private double currWaitTime;

  //Autounique Attributes
  private int sessionID;

  //PlayGame Associations
  private PlayPaddle playPaddle;
  private PlayBall playBall;
  private List<PlayBlockAssignment> playBlockAssignments;
  private Block223 block223;
  private Game game;
  private Player player;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PlayGame(int aNrLives, int aCurrScore, int aCurrLevel, double aCurrWaitTime, PlayPaddle aPlayPaddle, PlayBall aPlayBall, Block223 aBlock223, Game aGame, Player aPlayer)
  {
    nrLives = aNrLives;
    currScore = aCurrScore;
    currLevel = aCurrLevel;
    currWaitTime = aCurrWaitTime;
    sessionID = nextSessionID++;
    if (aPlayPaddle == null || aPlayPaddle.getPlayGame() != null)
    {
      throw new RuntimeException("Unable to create PlayGame due to aPlayPaddle");
    }
    playPaddle = aPlayPaddle;
    if (aPlayBall == null || aPlayBall.getPlayGame() != null)
    {
      throw new RuntimeException("Unable to create PlayGame due to aPlayBall");
    }
    playBall = aPlayBall;
    playBlockAssignments = new ArrayList<PlayBlockAssignment>();
    boolean didAddBlock223 = setBlock223(aBlock223);
    if (!didAddBlock223)
    {
      throw new RuntimeException("Unable to create playGame due to block223");
    }
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create playGame due to game");
    }
    boolean didAddPlayer = setPlayer(aPlayer);
    if (!didAddPlayer)
    {
      throw new RuntimeException("Unable to create playGame due to player");
    }
  }

  public PlayGame(int aNrLives, int aCurrScore, int aCurrLevel, double aCurrWaitTime, int aCurrLengthForPlayPaddle, int aPaddlePosXForPlayPaddle, int aCurrSpeedXForPlayBall, int aCurrSpeedYForPlayBall, int aBallPosXForPlayBall, int aBallPosYForPlayBall, Block223 aBlock223, Game aGame, Player aPlayer)
  {
    nrLives = aNrLives;
    currScore = aCurrScore;
    currLevel = aCurrLevel;
    currWaitTime = aCurrWaitTime;
    sessionID = nextSessionID++;
    playPaddle = new PlayPaddle(aCurrLengthForPlayPaddle, aPaddlePosXForPlayPaddle, this);
    playBall = new PlayBall(aCurrSpeedXForPlayBall, aCurrSpeedYForPlayBall, aBallPosXForPlayBall, aBallPosYForPlayBall, this);
    playBlockAssignments = new ArrayList<PlayBlockAssignment>();
    boolean didAddBlock223 = setBlock223(aBlock223);
    if (!didAddBlock223)
    {
      throw new RuntimeException("Unable to create playGame due to block223");
    }
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create playGame due to game");
    }
    boolean didAddPlayer = setPlayer(aPlayer);
    if (!didAddPlayer)
    {
      throw new RuntimeException("Unable to create playGame due to player");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setNrLives(int aNrLives)
  {
    boolean wasSet = false;
    nrLives = aNrLives;
    wasSet = true;
    return wasSet;
  }

  public boolean setCurrScore(int aCurrScore)
  {
    boolean wasSet = false;
    currScore = aCurrScore;
    wasSet = true;
    return wasSet;
  }

  public boolean setCurrLevel(int aCurrLevel)
  {
    boolean wasSet = false;
    currLevel = aCurrLevel;
    wasSet = true;
    return wasSet;
  }

  public boolean setCurrWaitTime(double aCurrWaitTime)
  {
    boolean wasSet = false;
    currWaitTime = aCurrWaitTime;
    wasSet = true;
    return wasSet;
  }

  public int getNrLives()
  {
    return nrLives;
  }

  public int getCurrScore()
  {
    return currScore;
  }

  public int getCurrLevel()
  {
    return currLevel;
  }

  public double getCurrWaitTime()
  {
    return currWaitTime;
  }

  public int getSessionID()
  {
    return sessionID;
  }
  /* Code from template association_GetOne */
  public PlayPaddle getPlayPaddle()
  {
    return playPaddle;
  }
  /* Code from template association_GetOne */
  public PlayBall getPlayBall()
  {
    return playBall;
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
  /* Code from template association_GetOne */
  public Block223 getBlock223()
  {
    return block223;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_GetOne */
  public Player getPlayer()
  {
    return player;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPlayBlockAssignments()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public PlayBlockAssignment addPlayBlockAssignment(int aBlockPosX, int aBlockPosY, Block aBlock)
  {
    return new PlayBlockAssignment(aBlockPosX, aBlockPosY, aBlock, this);
  }

  public boolean addPlayBlockAssignment(PlayBlockAssignment aPlayBlockAssignment)
  {
    boolean wasAdded = false;
    if (playBlockAssignments.contains(aPlayBlockAssignment)) { return false; }
    PlayGame existingPlayGame = aPlayBlockAssignment.getPlayGame();
    boolean isNewPlayGame = existingPlayGame != null && !this.equals(existingPlayGame);
    if (isNewPlayGame)
    {
      aPlayBlockAssignment.setPlayGame(this);
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
    //Unable to remove aPlayBlockAssignment, as it must always have a playGame
    if (!this.equals(aPlayBlockAssignment.getPlayGame()))
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
      existingBlock223.removePlayGame(this);
    }
    block223.addPlayGame(this);
    wasSet = true;
    return wasSet;
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
      existingGame.removePlayGame(this);
    }
    game.addPlayGame(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setPlayer(Player aPlayer)
  {
    boolean wasSet = false;
    if (aPlayer == null)
    {
      return wasSet;
    }

    Player existingPlayer = player;
    player = aPlayer;
    if (existingPlayer != null && !existingPlayer.equals(aPlayer))
    {
      existingPlayer.removePlayGame(this);
    }
    player.addPlayGame(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    PlayPaddle existingPlayPaddle = playPaddle;
    playPaddle = null;
    if (existingPlayPaddle != null)
    {
      existingPlayPaddle.delete();
    }
    PlayBall existingPlayBall = playBall;
    playBall = null;
    if (existingPlayBall != null)
    {
      existingPlayBall.delete();
    }
    while (playBlockAssignments.size() > 0)
    {
      PlayBlockAssignment aPlayBlockAssignment = playBlockAssignments.get(playBlockAssignments.size() - 1);
      aPlayBlockAssignment.delete();
      playBlockAssignments.remove(aPlayBlockAssignment);
    }
    
    Block223 placeholderBlock223 = block223;
    this.block223 = null;
    if(placeholderBlock223 != null)
    {
      placeholderBlock223.removePlayGame(this);
    }
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removePlayGame(this);
    }
    Player placeholderPlayer = player;
    this.player = null;
    if(placeholderPlayer != null)
    {
      placeholderPlayer.removePlayGame(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "sessionID" + ":" + getSessionID()+ "," +
            "nrLives" + ":" + getNrLives()+ "," +
            "currScore" + ":" + getCurrScore()+ "," +
            "currLevel" + ":" + getCurrLevel()+ "," +
            "currWaitTime" + ":" + getCurrWaitTime()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "playPaddle = "+(getPlayPaddle()!=null?Integer.toHexString(System.identityHashCode(getPlayPaddle())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "playBall = "+(getPlayBall()!=null?Integer.toHexString(System.identityHashCode(getPlayBall())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "block223 = "+(getBlock223()!=null?Integer.toHexString(System.identityHashCode(getBlock223())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "player = "+(getPlayer()!=null?Integer.toHexString(System.identityHashCode(getPlayer())):"null");
  }
}