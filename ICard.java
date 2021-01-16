package cs3500.pyramidsolitaire.model.hw02;

/**
 * Interface for an ICard object - this creates methods for extracting card information,
 * converting the card to a string and for testing equality.
 */

public interface ICard {

  /**
   * Getter method that returns a card's suite.
   *
   * @returns String representing the image of the suite.
   */

  String getSuite();

  /**
   * Getter method that returns a card's number.
   */
  int getNum();


  @Override
  String toString();

  @Override
  boolean equals(Object o);

  @Override
  int hashCode();


}
