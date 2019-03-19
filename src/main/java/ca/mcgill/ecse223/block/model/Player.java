/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;
import java.io.Serializable;
import java.util.*;

// line 46 "../../../../../Block223Persistence.ump"
// line 37 "../../../../../Block223.ump"
public class Player extends UserRole implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Player Associations
  private List<PlayGame> playGames;
  private List<HallOfFame> hallOfFames;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(String aPassword, Block223 aBlock223)
  {
    super(aPassword, aBlock223);
    playGames = new ArrayList<PlayGame>();
    hallOfFames = new ArrayList<HallOfFame>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public PlayGame getPlayGame(int index)
  {
    PlayGame aPlayGame = playGames.get(index);
    return aPlayGame;
  }

  public List<PlayGame> getPlayGames()
  {
    List<PlayGame> newPlayGames = Collections.unmodifiableList(playGames);
    return newPlayGames;
  }

  public int numberOfPlayGames()
  {
    int number = playGames.size();
    return number;
  }

  public boolean hasPlayGames()
  {
    boolean has = playGames.size() > 0;
    return has;
  }

  public int indexOfPlayGame(PlayGame aPlayGame)
  {
    int index = playGames.indexOf(aPlayGame);
    return index;
  }
  /* Code from template association_GetMany */
  public HallOfFame getHallOfFame(int index)
  {
    HallOfFame aHallOfFame = hallOfFames.get(index);
    return aHallOfFame;
  }

  public List<HallOfFame> getHallOfFames()
  {
    List<HallOfFame> newHallOfFames = Collections.unmodifiableList(hallOfFames);
    return newHallOfFames;
  }

  public int numberOfHallOfFames()
  {
    int number = hallOfFames.size();
    return number;
  }

  public boolean hasHallOfFames()
  {
    boolean has = hallOfFames.size() > 0;
    return has;
  }

  public int indexOfHallOfFame(HallOfFame aHallOfFame)
  {
    int index = hallOfFames.indexOf(aHallOfFame);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPlayGames()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public PlayGame addPlayGame(int aNrLives, int aCurrScore, int aCurrLevel, double aCurrWaitTime, PlayPaddle aPlayPaddle, PlayBall aPlayBall, Block223 aBlock223, Game aGame)
  {
    return new PlayGame(aNrLives, aCurrScore, aCurrLevel, aCurrWaitTime, aPlayPaddle, aPlayBall, aBlock223, aGame, this);
  }

  public boolean addPlayGame(PlayGame aPlayGame)
  {
    boolean wasAdded = false;
    if (playGames.contains(aPlayGame)) { return false; }
    Player existingPlayer = aPlayGame.getPlayer();
    boolean isNewPlayer = existingPlayer != null && !this.equals(existingPlayer);
    if (isNewPlayer)
    {
      aPlayGame.setPlayer(this);
    }
    else
    {
      playGames.add(aPlayGame);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePlayGame(PlayGame aPlayGame)
  {
    boolean wasRemoved = false;
    //Unable to remove aPlayGame, as it must always have a player
    if (!this.equals(aPlayGame.getPlayer()))
    {
      playGames.remove(aPlayGame);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPlayGameAt(PlayGame aPlayGame, int index)
  {  
    boolean wasAdded = false;
    if(addPlayGame(aPlayGame))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayGames()) { index = numberOfPlayGames() - 1; }
      playGames.remove(aPlayGame);
      playGames.add(index, aPlayGame);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePlayGameAt(PlayGame aPlayGame, int index)
  {
    boolean wasAdded = false;
    if(playGames.contains(aPlayGame))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayGames()) { index = numberOfPlayGames() - 1; }
      playGames.remove(aPlayGame);
      playGames.add(index, aPlayGame);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPlayGameAt(aPlayGame, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfHallOfFames()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public HallOfFame addHallOfFame(int aFinalScore, Game aGame)
  {
    return new HallOfFame(aFinalScore, aGame, this);
  }

  public boolean addHallOfFame(HallOfFame aHallOfFame)
  {
    boolean wasAdded = false;
    if (hallOfFames.contains(aHallOfFame)) { return false; }
    Player existingPlayer = aHallOfFame.getPlayer();
    boolean isNewPlayer = existingPlayer != null && !this.equals(existingPlayer);
    if (isNewPlayer)
    {
      aHallOfFame.setPlayer(this);
    }
    else
    {
      hallOfFames.add(aHallOfFame);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeHallOfFame(HallOfFame aHallOfFame)
  {
    boolean wasRemoved = false;
    //Unable to remove aHallOfFame, as it must always have a player
    if (!this.equals(aHallOfFame.getPlayer()))
    {
      hallOfFames.remove(aHallOfFame);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addHallOfFameAt(HallOfFame aHallOfFame, int index)
  {  
    boolean wasAdded = false;
    if(addHallOfFame(aHallOfFame))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfHallOfFames()) { index = numberOfHallOfFames() - 1; }
      hallOfFames.remove(aHallOfFame);
      hallOfFames.add(index, aHallOfFame);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveHallOfFameAt(HallOfFame aHallOfFame, int index)
  {
    boolean wasAdded = false;
    if(hallOfFames.contains(aHallOfFame))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfHallOfFames()) { index = numberOfHallOfFames() - 1; }
      hallOfFames.remove(aHallOfFame);
      hallOfFames.add(index, aHallOfFame);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addHallOfFameAt(aHallOfFame, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=playGames.size(); i > 0; i--)
    {
      PlayGame aPlayGame = playGames.get(i - 1);
      aPlayGame.delete();
    }
    for(int i=hallOfFames.size(); i > 0; i--)
    {
      HallOfFame aHallOfFame = hallOfFames.get(i - 1);
      aHallOfFame.delete();
    }
    super.delete();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 49 "../../../../../Block223Persistence.ump"
  private static final long serialVersionUID = -3900912597282882073L ;

  
}