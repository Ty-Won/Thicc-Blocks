/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;

// line 5 "../../../../../Block223SM.ump"
public class Block223SM
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Block223SM State Machines
  public enum PlayStatus { Start, Pause, Play, GameFinished, Final }
  private PlayStatus playStatus;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Block223SM()
  {
    setPlayStatus(PlayStatus.Start);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getPlayStatusFullName()
  {
    String answer = playStatus.toString();
    return answer;
  }

  public PlayStatus getPlayStatus()
  {
    return playStatus;
  }

  public boolean pressPlay()
  {
    boolean wasEventProcessed = false;
    
    PlayStatus aPlayStatus = playStatus;
    switch (aPlayStatus)
    {
      case Start:
        // line 10 "../../../../../Block223SM.ump"
        resumeGame();
        setPlayStatus(PlayStatus.Play);
        wasEventProcessed = true;
        break;
      case Pause:
        // line 14 "../../../../../Block223SM.ump"
        resumeGame();
        setPlayStatus(PlayStatus.Play);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean pressPause()
  {
    boolean wasEventProcessed = false;
    
    PlayStatus aPlayStatus = playStatus;
    switch (aPlayStatus)
    {
      case Play:
        // line 18 "../../../../../Block223SM.ump"
        pauseGame();
        setPlayStatus(PlayStatus.Pause);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean beatLevel()
  {
    boolean wasEventProcessed = false;
    
    PlayStatus aPlayStatus = playStatus;
    switch (aPlayStatus)
    {
      case Play:
        if (!(lastLevel()))
        {
        // line 19 "../../../../../Block223SM.ump"
          nextLevel();
          setPlayStatus(PlayStatus.Start);
          wasEventProcessed = true;
          break;
        }
        if (lastLevel())
        {
        // line 20 "../../../../../Block223SM.ump"
          winGame();
          setPlayStatus(PlayStatus.GameFinished);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean moveBall()
  {
    boolean wasEventProcessed = false;
    
    PlayStatus aPlayStatus = playStatus;
    switch (aPlayStatus)
    {
      case Play:
        if (paddleCollision())
        {
        // line 22 "../../../../../Block223SM.ump"
          collidePaddle();
          setPlayStatus(PlayStatus.Play);
          wasEventProcessed = true;
          break;
        }
        if (wallCollision())
        {
        // line 23 "../../../../../Block223SM.ump"
          collideWall();
          setPlayStatus(PlayStatus.Play);
          wasEventProcessed = true;
          break;
        }
        if (blockCollision())
        {
        // line 24 "../../../../../Block223SM.ump"
          collideBlock();
          setPlayStatus(PlayStatus.Play);
          wasEventProcessed = true;
          break;
        }
        if (outOfBounds()&&!(lastLife()))
        {
        // line 25 "../../../../../Block223SM.ump"
          loseLife();
          setPlayStatus(PlayStatus.Pause);
          wasEventProcessed = true;
          break;
        }
        if (outOfBounds()&&lastLife())
        {
        // line 26 "../../../../../Block223SM.ump"
          loseGame();
          setPlayStatus(PlayStatus.GameFinished);
          wasEventProcessed = true;
          break;
        }
        if (!(paddleCollision())&&!(wallCollision())&&!(blockCollision())&&!(outOfBounds()))
        {
        // line 27 "../../../../../Block223SM.ump"
          noCollision();
          setPlayStatus(PlayStatus.Play);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean retryGame()
  {
    boolean wasEventProcessed = false;
    
    PlayStatus aPlayStatus = playStatus;
    switch (aPlayStatus)
    {
      case GameFinished:
        // line 32 "../../../../../Block223SM.ump"
        resetGame();
        setPlayStatus(PlayStatus.Start);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean quitGame()
  {
    boolean wasEventProcessed = false;
    
    PlayStatus aPlayStatus = playStatus;
    switch (aPlayStatus)
    {
      case GameFinished:
        // line 33 "../../../../../Block223SM.ump"
        returnToMenu();
        setPlayStatus(PlayStatus.Final);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void setPlayStatus(PlayStatus aPlayStatus)
  {
    playStatus = aPlayStatus;

    // entry actions and do activities
    switch(playStatus)
    {
      case Start:
        // line 9 "../../../../../Block223SM.ump"
        setUpLevel();
        break;
      case GameFinished:
        // line 31 "../../../../../Block223SM.ump"
        updateHallOfFame();
        break;
      case Final:
        delete();
        break;
    }
  }

  public void delete()
  {}

  // line 39 "../../../../../Block223SM.ump"
   private Boolean lastLife(){
    return true;
  }

  // line 43 "../../../../../Block223SM.ump"
   private Boolean lastLevel(){
    return true;
  }

  // line 47 "../../../../../Block223SM.ump"
   private Boolean paddleCollision(){
    return true;
  }

  // line 51 "../../../../../Block223SM.ump"
   private Boolean wallCollision(){
    return true;
  }

  // line 55 "../../../../../Block223SM.ump"
   private Boolean blockCollision(){
    return true;
  }

  // line 59 "../../../../../Block223SM.ump"
   private Boolean outOfBounds(){
    return true;
  }

  // line 64 "../../../../../Block223SM.ump"
   private void setUpLevel(){
    
  }

  // line 68 "../../../../../Block223SM.ump"
   private void updateHallOfFame(){
    
  }

  // line 72 "../../../../../Block223SM.ump"
   private void pauseGame(){
    
  }

  // line 76 "../../../../../Block223SM.ump"
   private void resumeGame(){
    
  }

  // line 80 "../../../../../Block223SM.ump"
   private void resetGame(){
    
  }

  // line 84 "../../../../../Block223SM.ump"
   private void returnToMenu(){
    
  }

  // line 88 "../../../../../Block223SM.ump"
   private void loseLife(){
    
  }

  // line 92 "../../../../../Block223SM.ump"
   private void loseGame(){
    
  }

  // line 96 "../../../../../Block223SM.ump"
   private void nextLevel(){
    
  }

  // line 100 "../../../../../Block223SM.ump"
   private void winGame(){
    
  }

  // line 104 "../../../../../Block223SM.ump"
   private void collidePaddle(){
    
  }

  // line 108 "../../../../../Block223SM.ump"
   private void collideWall(){
    
  }

  // line 112 "../../../../../Block223SM.ump"
   private void collideBlock(){
    
  }

  // line 116 "../../../../../Block223SM.ump"
   private void noCollision(){
    
  }

}