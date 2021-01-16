package cs3500.pyramidsolitaire.model.hw02;

/**
 * Enumeration representing different suits for a card, each suit has a string value assiociated
 * with it.
 */

public enum Suit {
  CLUBS("♣"), DIAMONDS("♦"), HEARTS("♥"), SPADES("♠");

  private final String suitValue;


  /**
   * Constructs a Suit object.
   *
   * @param suitValue - String representing the "image" of a suit.
   */
  Suit(String suitValue) {
    this.suitValue = suitValue;
  }

  @Override
  public String toString() {
    return this.suitValue;
  }
}
