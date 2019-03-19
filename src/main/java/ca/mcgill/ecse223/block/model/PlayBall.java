/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;

// line 25 "../../../../../Block223PlayGame.ump"
public class PlayBall
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PlayBall Attributes
  private int currSpeedX;
  private int currSpeedY;
  private int ballPosX;
  private int ballPosY;

  //PlayBall Associations
  private PlayGame playGame;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PlayBall(int aCurrSpeedX, int aCurrSpeedY, int aBallPosX, int aBallPosY, PlayGame aPlayGame)
  {
    currSpeedX = aCurrSpeedX;
    currSpeedY = aCurrSpeedY;
    ballPosX = aBallPosX;
    ballPosY = aBallPosY;
    if (aPlayGame == null || aPlayGame.getPlayBall() != null)
    {
      throw new RuntimeException("Unable to create PlayBall due to aPlayGame");
    }
    playGame = aPlayGame;
  }

  public PlayBall(int aCurrSpeedX, int aCurrSpeedY, int aBallPosX, int aBallPosY, int aNrLivesForPlayGame, int aCurrScoreForPlayGame, int aCurrLevelForPlayGame, double aCurrWaitTimeForPlayGame, PlayPaddle aPlayPaddleForPlayGame, Block223 aBlock223ForPlayGame, Game aGameForPlayGame, Player aPlayerForPlayGame)
  {
    currSpeedX = aCurrSpeedX;
    currSpeedY = aCurrSpeedY;
    ballPosX = aBallPosX;
    ballPosY = aBallPosY;
    playGame = new PlayGame(aNrLivesForPlayGame, aCurrScoreForPlayGame, aCurrLevelForPlayGame, aCurrWaitTimeForPlayGame, aPlayPaddleForPlayGame, this, aBlock223ForPlayGame, aGameForPlayGame, aPlayerForPlayGame);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCurrSpeedX(int aCurrSpeedX)
  {
    boolean wasSet = false;
    currSpeedX = aCurrSpeedX;
    wasSet = true;
    return wasSet;
  }

  public boolean setCurrSpeedY(int aCurrSpeedY)
  {
    boolean wasSet = false;
    currSpeedY = aCurrSpeedY;
    wasSet = true;
    return wasSet;
  }

  public boolean setBallPosX(int aBallPosX)
  {
    boolean wasSet = false;
    ballPosX = aBallPosX;
    wasSet = true;
    return wasSet;
  }

  public boolean setBallPosY(int aBallPosY)
  {
    boolean wasSet = false;
    ballPosY = aBallPosY;
    wasSet = true;
    return wasSet;
  }

  public int getCurrSpeedX()
  {
    return currSpeedX;
  }

  public int getCurrSpeedY()
  {
    return currSpeedY;
  }

  public int getBallPosX()
  {
    return ballPosX;
  }

  public int getBallPosY()
  {
    return ballPosY;
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
            "currSpeedX" + ":" + getCurrSpeedX()+ "," +
            "currSpeedY" + ":" + getCurrSpeedY()+ "," +
            "ballPosX" + ":" + getBallPosX()+ "," +
            "ballPosY" + ":" + getBallPosY()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "playGame = "+(getPlayGame()!=null?Integer.toHexString(System.identityHashCode(getPlayGame())):"null");
  }
}