package cs3500.pyramidsolitaire.model.hw02;

import java.util.Objects;

/**
 * ICard.Card class that represents a single card used in a deck, with a Number value
 * and a Suit value.
 */

public class Card implements ICard {

  private final Number num;
  private final Suit suit;

  public Card(Number num, Suit suit) {
    this.num = num;
    this.suit = suit;
  }


  @Override
  public String toString() {

    return this.num.toString() + this.suit.toString();
  }

  @Override
  public String getSuite() {
    return this.suit.toString();

  }

  @Override
  public int getNum() {
    return this.num.getNumValue();

  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }

    if (!(that instanceof Card)) {
      return false;
    }

    return ((Card) that).getSuite().equals(this.getSuite()) && ((Card) that).getNum() == this
        .getNum();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.num, this.suit);
  }
}



