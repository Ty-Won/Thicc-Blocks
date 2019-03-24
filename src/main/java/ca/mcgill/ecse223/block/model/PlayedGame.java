/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;
import ca.mcgill.ecse223.block.model.BouncePoint.BounceDirection;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.*;

// line 11 "../../../../../Block223PlayMode.ump"
// line 106 "../../../../../Block223Persistence.ump"
// line 1 "../../../../../Block223States.ump"
public class PlayedGame implements Serializable
{

  //------------------------
  // STATIC VARIABLES
  //------------------------


  /**
   * at design time, the initial wait time may be adjusted as seen fit
   */
  public static final int INITIAL_WAIT_TIME = 1000;
  private static int nextId = 1;
  public static final int NR_LIVES = 3;

  /**
   * the PlayedBall and PlayedPaddle are not in a separate class to avoid the bug in Umple that occurred for the second constructor of Game
   * no direct link to Ball, because the ball can be found by navigating to PlayedGame, Game, and then Ball
   */
  public static final int BALL_INITIAL_X = Game.PLAY_AREA_SIDE / 2;
  public static final int BALL_INITIAL_Y = Game.PLAY_AREA_SIDE / 2;

  /**
   * no direct link to Paddle, because the paddle can be found by navigating to PlayedGame, Game, and then Paddle
   * pixels moved when right arrow key is pressed
   */
  public static final int PADDLE_MOVE_RIGHT = 1;

  /**
   * pixels moved when left arrow key is pressed
   */
  public static final int PADDLE_MOVE_LEFT = -1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PlayedGame Attributes
  private int score;
  private int lives;
  private int currentLevel;
  private double waitTime;
  private String playername;
  private double ballDirectionX;
  private double ballDirectionY;
  private double currentBallX;
  private double currentBallY;
  private double currentPaddleLength;
  private double currentPaddleX;
  private double currentPaddleY;

  //Autounique Attributes
  private int id;

  //PlayedGame State Machines
  public enum PlayStatus { Ready, Moving, Paused, GameOver }
  private PlayStatus playStatus;

  //PlayedGame Associations
  private Player player;
  private Game game;
  private List<PlayedBlockAssignment> blocks;
  private BouncePoint bounce;
  private Block223 block223;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PlayedGame(String aPlayername, Game aGame, Block223 aBlock223)
  {
    // line 312 "../../../../../Block223PlayMode.ump"
    boolean didAddGameResult = setGame(aGame);
          if (!didAddGameResult)
          {
             throw new RuntimeException("Unable to create playedGame due to game");
          }
    // END OF UMPLE BEFORE INJECTION
    score = 0;
    lives = NR_LIVES;
    currentLevel = 1;
    waitTime = INITIAL_WAIT_TIME;
    playername = aPlayername;
    resetBallDirectionX();
    resetBallDirectionY();
    resetCurrentBallX();
    resetCurrentBallY();
    currentPaddleLength = getGame().getPaddle().getMaxPaddleLength();
    resetCurrentPaddleX();
    currentPaddleY = Game.PLAY_AREA_SIDE - Paddle.VERTICAL_DISTANCE - Paddle.PADDLE_WIDTH;
    id = nextId++;
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create playedGame due to game");
    }
    blocks = new ArrayList<PlayedBlockAssignment>();
    boolean didAddBlock223 = setBlock223(aBlock223);
    if (!didAddBlock223)
    {
      throw new RuntimeException("Unable to create playedGame due to block223");
    }
    setPlayStatus(PlayStatus.Ready);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setScore(int aScore)
  {
    boolean wasSet = false;
    score = aScore;
    wasSet = true;
    return wasSet;
  }

  public boolean setLives(int aLives)
  {
    boolean wasSet = false;
    lives = aLives;
    wasSet = true;
    return wasSet;
  }

  public boolean setCurrentLevel(int aCurrentLevel)
  {
    boolean wasSet = false;
    currentLevel = aCurrentLevel;
    wasSet = true;
    return wasSet;
  }

  public boolean setWaitTime(double aWaitTime)
  {
    boolean wasSet = false;
    waitTime = aWaitTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setPlayername(String aPlayername)
  {
    boolean wasSet = false;
    playername = aPlayername;
    wasSet = true;
    return wasSet;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setBallDirectionX(double aBallDirectionX)
  {
    boolean wasSet = false;
    ballDirectionX = aBallDirectionX;
    wasSet = true;
    return wasSet;
  }

  public boolean resetBallDirectionX()
  {
    boolean wasReset = false;
    ballDirectionX = getDefaultBallDirectionX();
    wasReset = true;
    return wasReset;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setBallDirectionY(double aBallDirectionY)
  {
    boolean wasSet = false;
    ballDirectionY = aBallDirectionY;
    wasSet = true;
    return wasSet;
  }

  public boolean resetBallDirectionY()
  {
    boolean wasReset = false;
    ballDirectionY = getDefaultBallDirectionY();
    wasReset = true;
    return wasReset;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setCurrentBallX(double aCurrentBallX)
  {
    boolean wasSet = false;
    currentBallX = aCurrentBallX;
    wasSet = true;
    return wasSet;
  }

  public boolean resetCurrentBallX()
  {
    boolean wasReset = false;
    currentBallX = getDefaultCurrentBallX();
    wasReset = true;
    return wasReset;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setCurrentBallY(double aCurrentBallY)
  {
    boolean wasSet = false;
    currentBallY = aCurrentBallY;
    wasSet = true;
    return wasSet;
  }

  public boolean resetCurrentBallY()
  {
    boolean wasReset = false;
    currentBallY = getDefaultCurrentBallY();
    wasReset = true;
    return wasReset;
  }

  public boolean setCurrentPaddleLength(double aCurrentPaddleLength)
  {
    boolean wasSet = false;
    currentPaddleLength = aCurrentPaddleLength;
    wasSet = true;
    return wasSet;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setCurrentPaddleX(double aCurrentPaddleX)
  {
    boolean wasSet = false;
    currentPaddleX = aCurrentPaddleX;
    wasSet = true;
    return wasSet;
  }

  public boolean resetCurrentPaddleX()
  {
    boolean wasReset = false;
    currentPaddleX = getDefaultCurrentPaddleX();
    wasReset = true;
    return wasReset;
  }

  public int getScore()
  {
    return score;
  }

  public int getLives()
  {
    return lives;
  }

  public int getCurrentLevel()
  {
    return currentLevel;
  }

  public double getWaitTime()
  {
    return waitTime;
  }

  /**
   * added here so that it only needs to be determined once
   */
  public String getPlayername()
  {
    return playername;
  }

  /**
   * 0/0 is the top left corner of the play area, i.e., a directionX/Y of 0/1 moves the ball down in a straight line
   */
  public double getBallDirectionX()
  {
    return ballDirectionX;
  }
  /* Code from template attribute_GetDefaulted */
  public double getDefaultBallDirectionX()
  {
    return getGame().getBall().getMinBallSpeedX();
  }

  public double getBallDirectionY()
  {
    return ballDirectionY;
  }
  /* Code from template attribute_GetDefaulted */
  public double getDefaultBallDirectionY()
  {
    return getGame().getBall().getMinBallSpeedY();
  }

  /**
   * the position of the ball is at the center of the ball
   */
  public double getCurrentBallX()
  {
    return currentBallX;
  }
  /* Code from template attribute_GetDefaulted */
  public double getDefaultCurrentBallX()
  {
    return BALL_INITIAL_X;
  }

  public double getCurrentBallY()
  {
    return currentBallY;
  }
  /* Code from template attribute_GetDefaulted */
  public double getDefaultCurrentBallY()
  {
    return BALL_INITIAL_Y;
  }

  public double getCurrentPaddleLength()
  {
    return currentPaddleLength;
  }

  /**
   * the position of the paddle is at its top right corner
   */
  public double getCurrentPaddleX()
  {
    return currentPaddleX;
  }
  /* Code from template attribute_GetDefaulted */
  public double getDefaultCurrentPaddleX()
  {
    return (Game.PLAY_AREA_SIDE - currentPaddleLength) / 2;
  }

  public double getCurrentPaddleY()
  {
    return currentPaddleY;
  }

  public int getId()
  {
    return id;
  }

  public String getPlayStatusFullName()
  {
    String answer = playStatus.toString();
    return answer;
  }

  public PlayStatus getPlayStatus()
  {
    return playStatus;
  }

  public boolean play()
  {
    boolean wasEventProcessed = false;
    
    PlayStatus aPlayStatus = playStatus;
    switch (aPlayStatus)
    {
      case Ready:
        setPlayStatus(PlayStatus.Moving);
        wasEventProcessed = true;
        break;
      case Paused:
        setPlayStatus(PlayStatus.Moving);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean pause()
  {
    boolean wasEventProcessed = false;
    
    PlayStatus aPlayStatus = playStatus;
    switch (aPlayStatus)
    {
      case Moving:
        setPlayStatus(PlayStatus.Paused);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean move()
  {
    boolean wasEventProcessed = false;
    
    PlayStatus aPlayStatus = playStatus;
    switch (aPlayStatus)
    {
      case Moving:
        if (hitPaddle())
        {
        // line 12 "../../../../../Block223States.ump"
          doHitPaddleOrWall();
          setPlayStatus(PlayStatus.Moving);
          wasEventProcessed = true;
          break;
        }
        if (isOutOfBoundsAndLastLife())
        {
        // line 13 "../../../../../Block223States.ump"
          doOutOfBounds();
          setPlayStatus(PlayStatus.GameOver);
          wasEventProcessed = true;
          break;
        }
        if (isOutOfBounds())
        {
        // line 14 "../../../../../Block223States.ump"
          doOutOfBounds();
          setPlayStatus(PlayStatus.Paused);
          wasEventProcessed = true;
          break;
        }
        if (hitLastBlockAndLastLevel())
        {
        // line 15 "../../../../../Block223States.ump"
          doHitBlock();
          setPlayStatus(PlayStatus.GameOver);
          wasEventProcessed = true;
          break;
        }
        if (hitLastBlock())
        {
        // line 16 "../../../../../Block223States.ump"
          doHitBlockNextLevel();
          setPlayStatus(PlayStatus.Ready);
          wasEventProcessed = true;
          break;
        }
        if (hitBlock())
        {
        // line 17 "../../../../../Block223States.ump"
          doHitBlock();
          setPlayStatus(PlayStatus.Moving);
          wasEventProcessed = true;
          break;
        }
        if (hitWall())
        {
        // line 18 "../../../../../Block223States.ump"
          doHitPaddleOrWall();
          setPlayStatus(PlayStatus.Moving);
          wasEventProcessed = true;
          break;
        }
        // line 19 "../../../../../Block223States.ump"
        doHitNothingAndNotOutOfBounds();
        setPlayStatus(PlayStatus.Moving);
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
      case Ready:
        // line 7 "../../../../../Block223States.ump"
        doSetup();
        break;
      case GameOver:
        // line 25 "../../../../../Block223States.ump"
        doGameOver();
        break;
    }
  }
  /* Code from template association_GetOne */
  public Player getPlayer()
  {
    return player;
  }

  public boolean hasPlayer()
  {
    boolean has = player != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_GetMany */
  public PlayedBlockAssignment getBlock(int index)
  {
    PlayedBlockAssignment aBlock = blocks.get(index);
    return aBlock;
  }

  public List<PlayedBlockAssignment> getBlocks()
  {
    List<PlayedBlockAssignment> newBlocks = Collections.unmodifiableList(blocks);
    return newBlocks;
  }

  public int numberOfBlocks()
  {
    int number = blocks.size();
    return number;
  }

  public boolean hasBlocks()
  {
    boolean has = blocks.size() > 0;
    return has;
  }

  public int indexOfBlock(PlayedBlockAssignment aBlock)
  {
    int index = blocks.indexOf(aBlock);
    return index;
  }
  /* Code from template association_GetOne */
  public BouncePoint getBounce()
  {
    return bounce;
  }

  public boolean hasBounce()
  {
    boolean has = bounce != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Block223 getBlock223()
  {
    return block223;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setPlayer(Player aPlayer)
  {
    boolean wasSet = false;
    Player existingPlayer = player;
    player = aPlayer;
    if (existingPlayer != null && !existingPlayer.equals(aPlayer))
    {
      existingPlayer.removePlayedGame(this);
    }
    if (aPlayer != null)
    {
      aPlayer.addPlayedGame(this);
    }
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
      existingGame.removePlayedGame(this);
    }
    game.addPlayedGame(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBlocks()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public PlayedBlockAssignment addBlock(int aX, int aY, Block aBlock)
  {
    return new PlayedBlockAssignment(aX, aY, aBlock, this);
  }

  public boolean addBlock(PlayedBlockAssignment aBlock)
  {
    boolean wasAdded = false;
    if (blocks.contains(aBlock)) { return false; }
    PlayedGame existingPlayedGame = aBlock.getPlayedGame();
    boolean isNewPlayedGame = existingPlayedGame != null && !this.equals(existingPlayedGame);
    if (isNewPlayedGame)
    {
      aBlock.setPlayedGame(this);
    }
    else
    {
      blocks.add(aBlock);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBlock(PlayedBlockAssignment aBlock)
  {
    boolean wasRemoved = false;
    //Unable to remove aBlock, as it must always have a playedGame
    if (!this.equals(aBlock.getPlayedGame()))
    {
      blocks.remove(aBlock);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBlockAt(PlayedBlockAssignment aBlock, int index)
  {  
    boolean wasAdded = false;
    if(addBlock(aBlock))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBlocks()) { index = numberOfBlocks() - 1; }
      blocks.remove(aBlock);
      blocks.add(index, aBlock);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBlockAt(PlayedBlockAssignment aBlock, int index)
  {
    boolean wasAdded = false;
    if(blocks.contains(aBlock))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBlocks()) { index = numberOfBlocks() - 1; }
      blocks.remove(aBlock);
      blocks.add(index, aBlock);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBlockAt(aBlock, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setBounce(BouncePoint aNewBounce)
  {
    boolean wasSet = false;
    bounce = aNewBounce;
    wasSet = true;
    return wasSet;
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
      existingBlock223.removePlayedGame(this);
    }
    block223.addPlayedGame(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    if (player != null)
    {
      Player placeholderPlayer = player;
      this.player = null;
      placeholderPlayer.removePlayedGame(this);
    }
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removePlayedGame(this);
    }
    while (blocks.size() > 0)
    {
      PlayedBlockAssignment aBlock = blocks.get(blocks.size() - 1);
      aBlock.delete();
      blocks.remove(aBlock);
    }
    
    bounce = null;
    Block223 placeholderBlock223 = block223;
    this.block223 = null;
    if(placeholderBlock223 != null)
    {
      placeholderBlock223.removePlayedGame(this);
    }
  }

  // line 50 "../../../../../Block223PlayMode.ump"
   private BouncePoint calculateBouncePointPaddle(){
    //Get all the attributes of the current ball status
	  Double oldBallX = this.currentBallX;
	  Double oldBallY = this.currentBallY;
	  Double oldDirX = this.ballDirectionX;
	  Double oldDirY = this.ballDirectionY;
	  
	  //Set all the attributes of the potential future ball status
	  Double newBallX = oldBallX + oldDirX;
	  Double newBallY = oldBallY + oldDirY;
	  
	  //Get paddle attributes
	  Double paddleX = this.currentPaddleX;
	  Double paddleY = this.currentPaddleY;
	  Double paddleLength = this.currentPaddleLength;
	 
	  Rectangle2D paddleRect = new Rectangle2D.Double(paddleX - 5, paddleY - 5, paddleLength + 10, 10);
	  if (paddleRect.intersectsLine(oldBallX, oldBallY, newBallX, newBallY)) {
		  BouncePoint zoneB = checkLineIntersections(oldBallX, oldBallY, newBallX, newBallY, paddleX - 5, paddleY, paddleX - 5, paddleY + 5, BounceDirection.FLIP_X);
		  BouncePoint zoneC = checkLineIntersections(oldBallX, oldBallY, newBallX, newBallY, paddleX + paddleLength + 5, paddleY, paddleX + paddleLength + 5, paddleY + 5, BounceDirection.FLIP_X);
		  BouncePoint zoneA = checkLineIntersections(oldBallX, oldBallY, newBallX, newBallY, paddleX, paddleY - 5, paddleX + paddleLength, paddleY - 5, BounceDirection.FLIP_Y);
		  //TODO check zones E and F then take BP closest to ball and return that
		  
		  //Zone F
		  //Circle equation: (x-a)^2 + (y-b)^2 = r^2
		  Double a = paddleX + paddleLength;
		  Double b = paddleY;
		  Double r = 5.0;
		  //Line Equation: y = mx + c
		  Double m = (newBallY - oldBallY)/(newBallX - oldBallX);
		  Double c = oldBallY - (m * oldBallX);
		  BouncePoint[] zoneFList = checkCircleLineIntersection(a, b, r, m, c, oldBallX, oldBallY, newBallX, newBallY, oldDirX);
		  
		  //Zone E
		  //Circle equation: (x-a)^2 + (y-b)^2 = r^2
		  a = paddleX;
		  b = paddleY;
		  r = 5.0;
		  BouncePoint[] zoneEList = checkCircleLineIntersection(a, b, r, m, c, oldBallX, oldBallY, newBallX, newBallY, oldDirX);
		  
		  //Find closest intersection point
		  BouncePoint[] allZonesList = {zoneA, zoneB, zoneC, zoneEList[0], zoneEList[1], zoneFList[0], zoneFList[1]};
		  Double minDistance = Double.MAX_VALUE;
		  BouncePoint finalBP = null;
		  for (int i=0; i < allZonesList.length; i++) {
			  if (allZonesList[i] != null) {
				  Double distance = Math.sqrt(Math.pow(Math.abs(oldBallX - allZonesList[i].getX()), 2) + Math.pow(Math.abs(oldBallY - allZonesList[i].getY()), 2));
				  if (distance < minDistance) {
					  minDistance = distance;
					  finalBP = allZonesList[i];
				  }
			  }
		  }
		  return finalBP;
	  }
	  else {
		  return null;
	  }
  }

  // line 110 "../../../../../Block223PlayMode.ump"
   private BouncePoint[] checkCircleLineIntersection(Double a, Double b, Double r, Double m, Double c, Double oldBallX, Double oldBallY, Double newBallX, Double newBallY, Double oldDirX){
    Double distanceToCircle = (Math.pow(Math.abs(m * a - b + c), 2)) / (m * m + 1);
	  //Check for real solutions to intersection
	  BouncePoint[] intersectionList = new BouncePoint[2];
	  if (distanceToCircle <= (r*r)) {
		  //Solve quadratic equation for X, then get Y
		  Double quadA = 1 + (m * m);
		  Double quadB = 2 * (((c - b) * m) - a);
		  Double quadC = ((c - b) * (c - b)) + (a * a) - (r * r);
		  
		  Double solX1 = (-quadB + Math.sqrt((quadB * quadB) - (4 * quadA * quadC))) / (2*quadA);
		  Double solX2 = (-quadB - Math.sqrt((quadB * quadB) - (4 * quadA * quadC))) / (2*quadA);
		  Double solY1 = (m * solX1) + c;
		  Double solY2 = (m * solX2) + c;
		  
		  if (!Double.isNaN(solX1) && !Double.isNaN(solY1) && Math.min(oldBallX, newBallX) <= solX1 && solX1 <= Math.max(oldBallX,  newBallX)
        		  && Math.min(oldBallY, newBallY) <= solY1 && solY1 <= Math.max(oldBallY, newBallY)) {
			  if (oldDirX >= 0) {
				  BouncePoint zoneF1 = new BouncePoint(solX1, solY1, BounceDirection.FLIP_Y);
				  intersectionList[0] = zoneF1;
			  }
			  else {
				  BouncePoint zoneF1 = new BouncePoint(solX1, solY1, BounceDirection.FLIP_X);
				  intersectionList[0] = zoneF1;
			  }
		  }
		  
		  if (!Double.isNaN(solX2) && !Double.isNaN(solY2) && Math.min(oldBallX, newBallX) <= solX2 && solX2 <= Math.max(oldBallX,  newBallX)
        		  && Math.min(oldBallY, newBallY) <= solY2 && solY2 <= Math.max(oldBallY, newBallY)) {
			  if (oldDirX >= 0) {
				  BouncePoint zoneF2 = new BouncePoint(solX2, solY2, BounceDirection.FLIP_Y);
				  intersectionList[1] = zoneF2;
			  }
			  else {
				  BouncePoint zoneF2 = new BouncePoint(solX2, solY2, BounceDirection.FLIP_X);
				  intersectionList[1] = zoneF2;
			  }
		  }
	  }
	  return intersectionList;
  }

  // line 152 "../../../../../Block223PlayMode.ump"
   private BouncePoint calculateBouncePointWall(){
    //Get all the attributes of the current ball status
	  Double oldBallX = this.currentBallX;
	  Double oldBallY = this.currentBallY;
	  Double oldDirX = this.ballDirectionX;
	  Double oldDirY = this.ballDirectionY;
	  
	  //Set all the attributes of the potential future ball status
	  Double newBallX = oldBallX + oldDirX;
	  Double newBallY = oldBallY + oldDirY;
	  
	  //Find intersection points with the 3 wall zones (A = left, B = top, C = right)
	  BouncePoint zoneA = checkLineIntersections(oldBallX, oldBallY, newBallX, newBallY, 5.0, 5.0, 5.0, 385.0, BounceDirection.FLIP_X);
	  BouncePoint zoneC = checkLineIntersections(oldBallX, oldBallY, newBallX, newBallY, 385.0, 5.0, 385.0, 385.0, BounceDirection.FLIP_X);
	  BouncePoint zoneB = checkLineIntersections(oldBallX, oldBallY, newBallX, newBallY, 5.0, 5.0, 385.0, 5.0, BounceDirection.FLIP_Y);
	  
	  //Check if they are null and if not get the distance from the old ball position to the various bounce points
	  Double zoneADistance = null;
	  Double zoneCDistance = null;
	  Double zoneBDistance = null;
	  if (zoneA != null) {
		  zoneADistance = Math.sqrt(Math.pow(Math.abs(oldBallX - zoneA.getX()), 2) + Math.pow(Math.abs(oldBallY - zoneA.getY()), 2));
	  }
	  if (zoneC != null) {
		 zoneCDistance = Math.sqrt(Math.pow(Math.abs(oldBallX - zoneC.getX()), 2) + Math.pow(Math.abs(oldBallY - zoneC.getY()), 2));
	  }
	  if (zoneB != null) {
		  zoneBDistance = Math.sqrt(Math.pow(Math.abs(oldBallX - zoneB.getX()), 2) + Math.pow(Math.abs(oldBallY - zoneB.getY()), 2));
	  }
	  
	  //Find the closest bounce point to the ball location
	  Double distanceArr[] = {zoneADistance, zoneBDistance, zoneCDistance};
	  int minIndex = 10;
	  Double minDistance = Double.MAX_VALUE;
	  
	  for (int i = 0; i < 3; i++) {
		  if (distanceArr[i] != null) {
			  if (distanceArr[i] < minDistance) {
				  minDistance = distanceArr[i];
				  minIndex = i;
			  }
		  }
	  }
	  
	  //Return the closest bounce point but check for the corners
	  if (minIndex == 0) {
		  if (zoneA.getX() == 5 && zoneA.getY() == 5) {
			  zoneA.setDirection(BounceDirection.FLIP_BOTH);
		  }
		  return zoneA;
	  }
	  else if (minIndex == 1) {
		  if ((zoneB.getX() == 5 && zoneB.getY() == 5) || (zoneB.getX() == 385 && zoneB.getY() == 5)) {
			  zoneB.setDirection(BounceDirection.FLIP_BOTH);
		  }
		  return zoneB;
	  }
	  else if (minIndex == 2) {
		  if (zoneC.getX() == 385 && zoneC.getY() == 5) {
			  zoneC.setDirection(BounceDirection.FLIP_BOTH);
		  }
		  return zoneC;
	  }
	  else { //minIndex stayed at 10 meaning all 3 were null (this shouldn't be possible as it was already verified that the ball hits the wall, but this is here for safety)
		  return null;
	  }
  }

  // line 220 "../../../../../Block223PlayMode.ump"
   private BouncePoint checkLineIntersections(Double AX, Double AY, Double BX, Double BY, Double CX, Double CY, Double DX, Double DY, BounceDirection bd){
    // Line AB represented as a1x + b1y = c1 
      double a1 = BY - AY; 
      double b1 = AX - BX; 
      double c1 = a1*(AX) + b1*(AY); 
     
      // Line CD represented as a2x + b2y = c2 
      double a2 = DY - CY; 
      double b2 = CX - DX; 
      double c2 = a2*(CX)+ b2*(CY); 
     
      double determinant = a1*b2 - a2*b1; 
     
      if (determinant == 0) 
      { 
          // The lines are parallel so no intersection
          return null; 
      } 
      else
      { 
          double intersectX = (b2*c1 - b1*c2)/determinant; 
          double intersectY = (a1*c2 - a2*c1)/determinant; 
          if (Math.min(AX, BX) <= intersectX && intersectX <= Math.max(AX,  BX)
        		  && Math.min(AY, BY) <= intersectY && intersectY <= Math.max(AY, BY)) {
        	  BouncePoint bp = new BouncePoint(intersectX, intersectY, bd);
        	  return bp;
          }
          else {
        	  return null;
          }
      }
  }

  // line 253 "../../../../../Block223PlayMode.ump"
   private void bounceBall(){
    BouncePoint bp = this.getBounce();
	  Double ballX = this.getCurrentBallX();
	  Double ballY = this.getCurrentBallY();
	  Double ballDirX = this.getBallDirectionX();
	  Double ballDirY = this.getBallDirectionY();
	  
	  Double incomingX = Math.abs(ballX - bp.getX());
	  Double incomingY = Math.abs(ballY - bp.getY());
	  Double outgoingX = Math.abs(Math.abs(ballDirX) - Math.abs(incomingX));
	  Double outgoingY = Math.abs(Math.abs(ballDirY) - Math.abs(incomingY));
	  
	  Double newBallX;
	  Double newBallY;
	  Double newBallDirX;
	  Double newBallDirY;
	  if (!(outgoingX == 0 && outgoingY == 0)) {
		  //Set new ball direction and position
		  if (bp.getDirection() == BounceDirection.FLIP_Y) {
			  Double sign = Math.signum(ballDirX);
			  	if (sign == 0) {
					sign = 1.0;
			  	}
			  newBallDirX = ballDirX + (sign*0.1*Math.abs(ballDirY));
			  newBallDirY = ballDirY * (-1);
	
			  newBallX = bp.getX() + (outgoingX * Math.signum(newBallDirX)) + (0.1 * outgoingY * Math.signum(newBallDirX));
			  newBallY = bp.getY() + (outgoingY * Math.signum(newBallDirY));
		  }
		  else if (bp.getDirection() == BounceDirection.FLIP_X) {
		  	  Double sign = Math.signum(ballDirY);
			  	if (sign == 0) {
			  		sign = 1.0;
			  	}
			  newBallDirX = ballDirX * (-1);
			  newBallDirY = ballDirY + (sign*0.1*Math.abs(ballDirX));
			  
			  newBallX = bp.getX() + (outgoingX * Math.signum(newBallDirX));
			  newBallY = bp.getY() + (outgoingY * Math.signum(newBallDirY)) + (0.1 * outgoingX * Math.signum(newBallDirY));
		  }
		  else {
			  newBallDirX = ballDirX * (-1);
			  newBallDirY = ballDirY * (-1);
			  
			  newBallX = bp.getX() + (outgoingX * Math.signum(newBallDirX));
			  newBallY = bp.getY() + (outgoingY * Math.signum(newBallDirY));
		  }
		  
		  this.setBallDirectionX(newBallDirX);
		  this.setBallDirectionY(newBallDirY);
		  this.setCurrentBallX(newBallX);
		  this.setCurrentBallY(newBallY);
  	  }
  	  else {
  	  	  this.setCurrentBallX(bp.getX());
  	  	  this.setCurrentBallY(bp.getY());
  	  }
  }


  /**
   * Guards
   */
  // line 32 "../../../../../Block223States.ump"
   private boolean hitPaddle(){
    BouncePoint bp = this.calculateBouncePointPaddle();
    this.setBounce(bp);
    return bp != null;
  }

  // line 38 "../../../../../Block223States.ump"
   private boolean isBallOutOfBounds(){
    return getCurrentPaddleY() >= 390;
  }

  // line 43 "../../../../../Block223States.ump"
   private boolean isOutOfBoundsAndLastLife(){
    boolean outOfBounds = false;
    if(lives == 1) {
    	outOfBounds = isBallOutOfBounds();
    }
    
    return outOfBounds;
  }

  // line 52 "../../../../../Block223States.ump"
   private boolean isOutOfBounds(){
    return isBallOutOfBounds();
  }

  // line 57 "../../../../../Block223States.ump"
   private boolean hitLastBlockAndLastLevel(){
    Game game = getGame();
    
    int nrLevels = game.numberOfLevels();
    
    setBounce(null);
    
    if(nrLevels == currentLevel){
    	int nrBlocks = numberOfBlocks();
    	
    	if(nrBlocks == 1){
    		PlayedBlockAssignment block = getBlock(0);
    		BouncePoint bp = calculateBouncePointBlock(block);
    		setBounce(bp);
    		return bp!=null;
    	}
    }
    return false;
  }

  // line 77 "../../../../../Block223States.ump"
   private BouncePoint calculateBouncePointBlock(PlayedBlockAssignment block){
    int ballRadius = 5;

    Double oldBallX = this.currentBallX;
    Double oldBallY = this.currentBallY;

    Double ballDirX = this.ballDirectionX;
    Double ballDirY = this.ballDirectionY;

    Double newBallX = this.currentBallX + ballDirX;
    Double newBallY = this.currentBallY + ballDirY;

    Double blockX = Double.valueOf(block.getX());
    Double blockY = Double.valueOf(block.getY());
    Double blockSize = Double.valueOf(block.getBlock().SIZE);

    Rectangle2D blockRectangle = new Rectangle2D.Double(blockX - ballRadius, blockY - ballRadius,
        blockSize + 2 * ballRadius, blockSize + 2 * ballRadius);
    if (blockRectangle.intersectsLine(oldBallX,oldBallY,newBallX,newBallY)){
      //top
      BouncePoint zoneA = checkLineIntersections(oldBallX, oldBallY, newBallX, newBallY, blockX, blockY-ballRadius, blockX+blockSize, blockY-ballRadius, BounceDirection.FLIP_Y);

      //left
      BouncePoint zoneB = checkLineIntersections(oldBallX, oldBallY, newBallX, newBallY, blockX-ballRadius, blockY, blockX-ballRadius, blockY+blockSize, BounceDirection.FLIP_X);

      //right
      BouncePoint zoneC = checkLineIntersections(oldBallX, oldBallY, newBallX, newBallY, blockX+ballRadius+blockSize, blockY, blockX+ballRadius+blockSize, blockY+blockSize, BounceDirection.FLIP_X);

      //bottom
      BouncePoint zoneD = checkLineIntersections(oldBallX, oldBallY, newBallX, newBallY, blockX, blockY-blockSize-ballRadius, blockX+blockSize, blockY-blockSize-ballRadius, BounceDirection.FLIP_Y);

      // Circle equation: (x-a)^2 + (y-b)^2 = r^2
      Double a;
      Double b;
      Double r = Double.valueOf(ballRadius);

      // Line Equation: y = mx + c
      Double m = (newBallY-oldBallY)/(newBallX-oldBallX);
      Double c = oldBallY - m*oldBallX;

      //TopLeftCorner
      a=blockX;
      b=blockY;
      BouncePoint [] zoneE = checkCircleLineIntersection(a,b,r,m,c,oldBallX,oldBallY,newBallX,newBallY, ballDirectionX);

      //TopRightCorner
      a=blockX+blockSize;
      b=blockY;
      BouncePoint [] zoneF = checkCircleLineIntersection(a,b,r,m,c,oldBallX,oldBallY,newBallX,newBallY, ballDirectionX);

      //BottomLeftCorner
      a=blockX;
      b=blockY+blockSize;
      BouncePoint [] zoneG = checkCircleLineIntersection(a,b,r,m,c,oldBallX,oldBallY,newBallX,newBallY, ballDirectionX);

      //BottomRightCorner
      a=blockX + blockSize;
      b=blockY + blockSize;
      BouncePoint [] zoneH = checkCircleLineIntersection(a,b,r,m,c,oldBallX,oldBallY,newBallX,newBallY, ballDirectionX);

      // Find closest intersection point
      BouncePoint[] allZonesList = { zoneA, zoneB, zoneC, zoneD, zoneE[0], zoneE[1], zoneF[0], zoneF[1],
                                    zoneG[0], zoneG[1], zoneH[0], zoneH[1] };
      Double minDistance = Double.MAX_VALUE;
      BouncePoint finalBP = null;
      for (int i = 0; i < allZonesList.length; i++) {
        if (allZonesList[i] != null) {
          Double distance = Math.sqrt(Math.pow(Math.abs(oldBallX - allZonesList[i].getX()), 2)
              + Math.pow(Math.abs(oldBallY - allZonesList[i].getY()), 2));
          if (distance < minDistance) {
            minDistance = distance;
            finalBP = allZonesList[i];
          }
        }
      }
      return finalBP;
    } else {
      return null;
    }
  }

  // line 161 "../../../../../Block223States.ump"
   private void doHitBlock(){
    int score = getScore();
    BouncePoint bounce = getBounce();
    
    PlayedBlockAssignment pblock = bounce.getHitBlock();
    
    Block block = pblock.getBlock();
    
    int points = block.getPoints();
    
    setScore(score+points);
    
    pblock.delete();
    
    bounceBall();
  }

  // line 178 "../../../../../Block223States.ump"
   private boolean hitLastBlock(){
    int nrBlocks = numberOfBlocks();

    setBounce(null);

    if(nrBlocks ==1){
        PlayedBlockAssignment block = getBlock(0);

        BouncePoint bp = calculateBouncePointBlock(block);
        
        setBounce(bp);
        return bp!=null;
    }
    return false;
  }

  // line 194 "../../../../../Block223States.ump"
   private void doHitBlockNextLevel(){
    doHitBlock();
  	int level = getCurrentLevel();
  	setCurrentLevel(level+1);
  	
  	setCurrentPaddleLength(getGame().getPaddle().getMaxPaddleLength()-
  	(getGame().getPaddle().getMaxPaddleLength()-getGame().getPaddle().getMinPaddleLength())/
  	(getGame().numberOfLevels()-1)*(getCurrentLevel()-1));
  	
  	setWaitTime(Math.pow(INITIAL_WAIT_TIME * getGame().getBall().getBallSpeedIncreaseFactor(),(getCurrentLevel()-1)));
  }

  // line 207 "../../../../../Block223States.ump"
   private boolean isCloser(BouncePoint first, BouncePoint second){
    if (first == null) {
      return false;
    } else if (second == null) {
      return true;
    } else {
      // ball position
      double xPos = getCurrentBallX();
      double yPos = getCurrentBallY();

      double deltaDistFirst = euclideanDistance(xPos, yPos, first.getX(), first.getY());
      double deltaDistSecond = euclideanDistance(xPos, yPos, second.getX(), second.getY());

      if (deltaDistFirst >= deltaDistSecond) {
        return true;
      } else {
        return false;
      }
    }
  }

  // line 229 "../../../../../Block223States.ump"
   public double euclideanDistance(double x1, double y1, double x2, double y2){
    double deltaX = Math.pow(x1-x2,2);
    double deltaY = Math.pow(y1-y2,2);
    return Math.sqrt(deltaX+deltaY);
  }

  // line 235 "../../../../../Block223States.ump"
   private boolean hitBlock(){
    int nrBlocks = numberOfBlocks();
    setBounce(null);
    
    for(int i =0; i<numberOfBlocks()-1; i++){
    	PlayedBlockAssignment block = getBlock(i);
    	BouncePoint bp = calculateBouncePointBlock(block);
    	BouncePoint bounce = getBounce();
    	boolean closer = isCloser(bp,bounce);
    	
    	if(closer){
    		setBounce(bp);
    	}
    }
    
    
    return getBounce()!=null;
  }

  // line 254 "../../../../../Block223States.ump"
   private boolean hitWall(){
    BouncePoint bp = this.calculateBouncePointWall();
    this.setBounce(bp);
    return bp != null;
  }


  /**
   * Actions
   */
  // line 263 "../../../../../Block223States.ump"
   private void doSetup(){
    resetCurrentBallX();
    resetCurrentBallY();
    resetBallDirectionX();
    resetBallDirectionY();
    resetCurrentPaddleX();
    
    Game game = getGame();

    Level level = game.getLevel(currentLevel - 1);

    List<BlockAssignment> assignments = level.getBlockAssignments();

    for (BlockAssignment a : assignments) {

      PlayedBlockAssignment pBlock = new PlayedBlockAssignment(
        Game.WALL_PADDING + (Block.SIZE + Game.COLUMNS_PADDING) * (a.getGridHorizontalPosition() - 1),
        Game.WALL_PADDING + (Block.SIZE + Game.ROW_PADDING) * (a.getGridVerticalPosition() - 1),
        a.getBlock(), this
      );

    }

    // Get block maximum
    int[] x_y_capacity = Game.getMaxBlockCapacity();

    int xmin = 1;
    int xmax = x_y_capacity[0];

    int ymin = 1;
    int ymax = x_y_capacity[1];

    Random rand = new Random();

    // Fill up the remaining blocks
    while (numberOfBlocks() < game.getNrBlocksPerLevel()) {

      // Generate a random grid horizontal and verticle positions
      int x = xmin + rand.nextInt(xmax - xmin + 1);
      int y = ymin + rand.nextInt(ymax - ymin + 1);

      boolean found = false;

      // Find a suitable x, y location
      while (!found) {
        found = true;

        // Convert to x and y to played block format
        int xa = Game.WALL_PADDING + (Block.SIZE + Game.COLUMNS_PADDING) * (x - 1);
        int ya = Game.WALL_PADDING + (Block.SIZE + Game.ROW_PADDING) * (y - 1);

        for (PlayedBlockAssignment a : blocks) {
          if (a.getX() == xa && a.getY() == ya) {
            found = false;
            break;
          }
        }

        if (!found) {

          // Loop-back to next row
          if (x == xmax) {
            y = (y % ymax) + 1;
          }

          // Advance to next column
          x = (x % xmax) + 1;
        }
        
      }

      PlayedBlockAssignment pBlock = new PlayedBlockAssignment(x, y, game.getRandomBlock(), this);
    }
  }

  // line 339 "../../../../../Block223States.ump"
   private void doHitPaddleOrWall(){
    this.bounceBall();
  }

  // line 343 "../../../../../Block223States.ump"
   private void doOutOfBounds(){
    setLives(lives - 1);
    resetCurrentBallX();
    resetCurrentBallY();
    resetBallDirectionX();
    resetBallDirectionY();
    resetCurrentPaddleX();
  }

  // line 353 "../../../../../Block223States.ump"
   private void doHitNothingAndNotOutOfBounds(){
    setCurrentBallX(currentBallX + ballDirectionX);
    setCurrentBallY(currentBallY + ballDirectionY);
  }

  // line 358 "../../../../../Block223States.ump"
   private void doGameOver(){
    Block223 block223 = getBlock223();
    Player p = getPlayer();
    
    if(p != null) {
    	Game game = getGame();
    	HallOfFameEntry hof = new HallOfFameEntry(score, playername, p, game, block223);
    	game.setMostRecentEntry(hof);
    }
    
    delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "score" + ":" + getScore()+ "," +
            "lives" + ":" + getLives()+ "," +
            "currentLevel" + ":" + getCurrentLevel()+ "," +
            "waitTime" + ":" + getWaitTime()+ "," +
            "playername" + ":" + getPlayername()+ "," +
            "ballDirectionX" + ":" + getBallDirectionX()+ "," +
            "ballDirectionY" + ":" + getBallDirectionY()+ "," +
            "currentBallX" + ":" + getCurrentBallX()+ "," +
            "currentBallY" + ":" + getCurrentBallY()+ "," +
            "currentPaddleLength" + ":" + getCurrentPaddleLength()+ "," +
            "currentPaddleX" + ":" + getCurrentPaddleX()+ "," +
            "currentPaddleY" + ":" + getCurrentPaddleY()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "player = "+(getPlayer()!=null?Integer.toHexString(System.identityHashCode(getPlayer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "bounce = "+(getBounce()!=null?Integer.toHexString(System.identityHashCode(getBounce())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "block223 = "+(getBlock223()!=null?Integer.toHexString(System.identityHashCode(getBlock223())):"null");
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 109 "../../../../../Block223Persistence.ump"
  private static final long serialVersionUID = 8597675110221231714L ;

  
}