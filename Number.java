package cs3500.pyramidsolitaire.model.hw02;

/**
 * represents the card the card number and values in the Solitaire deck.
 */

public enum Number {
  ACE("A", 1), TWO("2", 2), THREE("3", 3), FOUR("4", 4), FIVE("5", 5),
  SIX("6", 6), SEVEN("7", 7), EIGHT("8", 8), NINE("9", 9),
  TEN("10", 10), JACK("J", 11), QUEEN("Q", 12), KING("K", 13);

  private final String face;
  private final int numValue;

  /**
   * Constructs a Number object.
   * @param face - represents the card face of Ace, King, Queen or Jack if the numValue is greater
   *        than 10.
   * @param numValue - integer representing the numberical value of a card from 1 - 13.
   */
  Number(String face, int numValue) {
    this.face = face;
    this.numValue = numValue;
  }

  @Override
  public String toString() {
    return this.face;
  }

  public int getNumValue() {
    return this.numValue;
  }
}
