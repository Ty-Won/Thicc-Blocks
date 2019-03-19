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
        
        setPlayStatus(PlayStatus.Play);
        wasEventProcessed = true;
        break;
      case Pause:
        // line 15 "../../../../../Block223SM.ump"
        
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
        // line 19 "../../../../../Block223SM.ump"
        
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
        // line 20 "../../../../../Block223SM.ump"
          nextLevel();
          setPlayStatus(PlayStatus.Start);
          wasEventProcessed = true;
          break;
        }
        if (lastLevel())
        {
        // line 21 "../../../../../Block223SM.ump"
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
        // line 23 "../../../../../Block223SM.ump"
          collidePaddle();
          setPlayStatus(PlayStatus.Play);
          wasEventProcessed = true;
          break;
        }
        if (wallCollision())
        {
        // line 24 "../../../../../Block223SM.ump"
          collideWall();
          setPlayStatus(PlayStatus.Play);
          wasEventProcessed = true;
          break;
        }
        if (blockCollision())
        {
        // line 25 "../../../../../Block223SM.ump"
          collideBlock();
          setPlayStatus(PlayStatus.Play);
          wasEventProcessed = true;
          break;
        }
        if (outOfBounds()&&!(lastLife()))
        {
        // line 26 "../../../../../Block223SM.ump"
          loseLife();
          setPlayStatus(PlayStatus.Pause);
          wasEventProcessed = true;
          break;
        }
        if (outOfBounds()&&lastLife())
        {
          setPlayStatus(PlayStatus.GameFinished);
          wasEventProcessed = true;
          break;
        }
        if (!(paddleCollision())&&!(wallCollision())&&!(blockCollision())&&!(outOfBounds()))
        {
        // line 28 "../../../../../Block223SM.ump"
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
        // line 33 "../../../../../Block223SM.ump"
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
        // line 34 "../../../../../Block223SM.ump"
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
      case Pause:
        // line 14 "../../../../../Block223SM.ump"
        pauseGame();
        break;
      case GameFinished:
        // line 32 "../../../../../Block223SM.ump"
        updateHallOfFame();
        break;
      case Final:
        delete();
        break;
    }
  }

  public void delete()
  {}

  // line 40 "../../../../../Block223SM.ump"
   private Boolean lastLife(){
    return true;
  }

  // line 44 "../../../../../Block223SM.ump"
   private Boolean lastLevel(){
    return true;
  }

  // line 48 "../../../../../Block223SM.ump"
   private Boolean paddleCollision(){
    return true;
  }

  // line 52 "../../../../../Block223SM.ump"
   private Boolean wallCollision(){
    return true;
  }

  // line 56 "../../../../../Block223SM.ump"
   private Boolean blockCollision(){
    return true;
  }

  // line 60 "../../../../../Block223SM.ump"
   private Boolean outOfBounds(){
    return true;
  }

  // line 65 "../../../../../Block223SM.ump"
   private void setUpLevel(){
    
  }

  // line 69 "../../../../../Block223SM.ump"
   private void updateHallOfFame(){
    
  }

  // line 73 "../../../../../Block223SM.ump"
   private void pauseGame(){
    
  }

  // line 77 "../../../../../Block223SM.ump"
   private void resetGame(){
    
  }

  // line 81 "../../../../../Block223SM.ump"
   private void returnToMenu(){
    
  }

  // line 85 "../../../../../Block223SM.ump"
   private void loseLife(){
    
  }

  // line 89 "../../../../../Block223SM.ump"
   private void loseGame(){
    
  }

  // line 93 "../../../../../Block223SM.ump"
   private void nextLevel(){
    
  }

  // line 97 "../../../../../Block223SM.ump"
   private void winGame(){
    
  }

  // line 101 "../../../../../Block223SM.ump"
   private void collidePaddle(){
    
  }

  // line 105 "../../../../../Block223SM.ump"
   private void collideWall(){
    
  }

  // line 109 "../../../../../Block223SM.ump"
   private void collideBlock(){
    
  }

  // line 113 "../../../../../Block223SM.ump"
   private void noCollision(){
    
  }

}