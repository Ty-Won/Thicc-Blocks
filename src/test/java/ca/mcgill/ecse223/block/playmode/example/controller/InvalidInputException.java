package ca.mcgill.ecse223.block.playmode.example.controller;

public class InvalidInputException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public InvalidInputException(String Error) {
    super(Error);
  }

}
