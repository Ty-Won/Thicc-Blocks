/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;

// line 19 "../../../../../Block223PlayGame.ump"
public class PlayPaddle
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PlayPaddle Attributes
  private int currLength;
  private int paddlePosX;

  //PlayPaddle Associations
  private PlayGame playGame;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PlayPaddle(int aCurrLength, int aPaddlePosX, PlayGame aPlayGame)
  {
    currLength = aCurrLength;
    paddlePosX = aPaddlePosX;
    if (aPlayGame == null || aPlayGame.getPlayPaddle() != null)
    {
      throw new RuntimeException("Unable to create PlayPaddle due to aPlayGame");
    }
    playGame = aPlayGame;
  }

  public PlayPaddle(int aCurrLength, int aPaddlePosX, int aNrLivesForPlayGame, int aCurrScoreForPlayGame, int aCurrLevelForPlayGame, double aCurrWaitTimeForPlayGame, PlayBall aPlayBallForPlayGame, Block223 aBlock223ForPlayGame, Game aGameForPlayGame, Player aPlayerForPlayGame)
  {
    currLength = aCurrLength;
    paddlePosX = aPaddlePosX;
    playGame = new PlayGame(aNrLivesForPlayGame, aCurrScoreForPlayGame, aCurrLevelForPlayGame, aCurrWaitTimeForPlayGame, this, aPlayBallForPlayGame, aBlock223ForPlayGame, aGameForPlayGame, aPlayerForPlayGame);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCurrLength(int aCurrLength)
  {
    boolean wasSet = false;
    currLength = aCurrLength;
    wasSet = true;
    return wasSet;
  }

  public boolean setPaddlePosX(int aPaddlePosX)
  {
    boolean wasSet = false;
    paddlePosX = aPaddlePosX;
    wasSet = true;
    return wasSet;
  }

  public int getCurrLength()
  {
    return currLength;
  }

  public int getPaddlePosX()
  {
    return paddlePosX;
  }
  /* Code from template association_GetOne */
  public PlayGame getPlayGame()
  {
    return playGame;
  }

  public void delete()
  {
    PlayGame existingPlayGame = playGame;
    playGame = null;
    if (existingPlayGame != null)
    {
      existingPlayGame.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "currLength" + ":" + getCurrLength()+ "," +
            "paddlePosX" + ":" + getPaddlePosX()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "playGame = "+(getPlayGame()!=null?Integer.toHexString(System.identityHashCode(getPlayGame())):"null");
  }
}