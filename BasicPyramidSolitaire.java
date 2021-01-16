package cs3500.pyramidsolitaire.model.hw02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * An instance of a BasicPyramidSolitaireGame where there is a pyramid of cards, a draw pile
 * a deck and pile for removed cards.
 * The methods in this class allow users to get information about the game model,
 * starting the game, and removing cards within the game.
 *
 */
public class BasicPyramidSolitaire implements PyramidSolitaireModel<ICard> {

  private final List<ICard> stock;
  private final List<List<ICard>> pyramid;
  private final List<ICard> drawPile;
  private final List<ICard> removePile;

  /**
   * Constructor that creates a deck of Cards, a draw Pile a 2d pyramid of cards and a remove pile
   * and sets them as empty ArrayLists.
   */
  public BasicPyramidSolitaire() {
    this.stock = new ArrayList<ICard>();
    this.drawPile = new ArrayList<ICard>();
    this.pyramid = new ArrayList<List<ICard>>();
    this.removePile = new ArrayList<ICard>();
  }


  @Override
  public List<ICard> getDeck() {
    List<ICard> newDeck = new ArrayList<ICard>();
    Number[] nums = Number.values();
    Suit[] suits = Suit.values();

    for (int i = 0; i < 52; i++) {
      newDeck.add(new Card(nums[i % 13], suits[i % 4]));
    }
    return newDeck;
  }


  @Override
  public void startGame(List<ICard> deck, boolean shuffle, int numRows, int numDraw)
      throws IllegalArgumentException {

    if (deck == null || !this.isValidDeck(deck)) {
      throw new IllegalArgumentException("Deck cannot be null");
    }

    if (numRows < 1) {
      throw new IllegalArgumentException(("rows cannot be non-positive. "));
    }

    if (numDraw < 0) {
      throw new IllegalArgumentException("draws cannot be negative");
    }

    if (!this.enoughCards(numRows, numDraw)) {
      throw new IllegalArgumentException("Not enough cards to play");
    }
    List<ICard> deckCopy = new ArrayList<ICard>(deck);

    if (shuffle) {
      Collections.shuffle(deckCopy);
    }



    this.stock.addAll(deckCopy);

    for (int i = 0; i < numRows; i++) {
      pyramid.add(new ArrayList<ICard>());
      for (int k = 0; k <= i; k++) {
        pyramid.get(i).add(stock.remove(0));
      }
    }

    for (int i = 0; i < numDraw; i++) {
      this.drawPile.add(stock.remove(0));
    }


  }

  private boolean enoughCards(int numRows, int numDraw) {
    int counter = 0;
    for (int i = 0; i <= numRows; i++) {
      counter += i;

    }
    return numDraw + counter <= 52;
  }


  private boolean isValidDeck(List<ICard> deck) {

    Set<ICard> set = new HashSet<ICard>(deck);

    return set.size() == 52;

  }


  @Override
  public void remove(int row1, int card1, int row2, int card2)
      throws IllegalArgumentException, IllegalStateException {
    if (pyramid.size() == 0) {
      throw new IllegalStateException("Game has not started yet");
    }
    if (outOfBounds(row1, card1) || outOfBounds(row2, card2)) {
      throw new IllegalArgumentException("Row or card length is out of bounds.");
    }
    if (cardIsAvailable(row1, card1)
        && cardIsAvailable(row2, card2)
        && getCardAt(row1, card1).getNum() + getCardAt(row2, card2).getNum() == 13) {
      removePile.add(pyramid.get(row1).remove(card1));
      pyramid.get(row1).add(card1, null);
      removePile.add(pyramid.get(row2).remove(card2));
      pyramid.get(row2).add(card2, null);
    } else {
      throw new IllegalArgumentException("THIS MOVE IS INVALID!");
    }


  }


  @Override
  public void remove(int row, int card) throws IllegalArgumentException, IllegalStateException {

    if (pyramid.size() == 0) {
      throw new IllegalStateException("Game has not started yet");
    }

    if (outOfBounds(row, card)) {
      throw new IllegalArgumentException("Row or card length is out of bounds.");
    }

    if (getCardAt(row, card).getNum() == 13 && cardIsAvailable(row, card)) {
      removePile.add(pyramid.get(row).remove(card));
      pyramid.get(row).add(card, null);

    } else {
      throw new IllegalArgumentException("THIS MOVE IS INVALID");
    }
  }

  private boolean cardIsAvailable(int row, int card) {
    return row == pyramid.size() - 1
        || (getCardAt(row + 1, card) == null && getCardAt(row + 1, card + 1) == null);
  }


  private boolean outOfBounds(int row, int card) {
    return row >= pyramid.size() || row < 0 || card < 0 || card >= pyramid.get(row).size();
  }


  @Override
  public void removeUsingDraw(int drawIndex, int row, int card)
      throws IllegalArgumentException, IllegalStateException {
    if (pyramid.size() == 0) {
      throw new IllegalStateException("Game has not started yet");
    }
    if (outOfBounds(row, card) || drawIndex < 0 || drawIndex > drawPile.size()) {
      throw new IllegalArgumentException("Row or card length is out of bounds.");
    }

    if (cardIsAvailable(row, card)
        && drawPile.get(drawIndex).getNum() + getCardAt(row, card).getNum() == 13) {
      removePile.add(pyramid.get(row).remove(card));
      pyramid.get(row).add(card, null);
      this.discardDraw(drawIndex);
    } else {
      throw new IllegalArgumentException("MOVE IS INVALID!");
    }


  }


  @Override
  public void discardDraw(int drawIndex) throws IllegalArgumentException, IllegalStateException {
    if (drawPile.isEmpty()) {
      throw new IllegalArgumentException("Draw Pile must contain a card.");

    }
    if (drawIndex < 0 || drawIndex >= drawPile.size()) {
      throw new IllegalArgumentException("drawIndex is out of bounds");
    }
    if (pyramid.size() == 0) {
      throw new IllegalStateException("Game has not started yet.");
    }
    removePile.add(drawPile.remove(drawIndex));

    if (!stock.isEmpty()) {
      drawPile.add(drawIndex, stock.remove(0));
    }

  }


  @Override
  public int getNumRows() {
    if (pyramid.size() == 0) {
      return -1;
    }
    return pyramid.size();
  }


  @Override
  public int getNumDraw() {
    if (pyramid.size() == 0) {
      return -1;
    }

    return drawPile.size();


  }


  @Override
  public int getRowWidth(int row) {
    if (row < 0 || row > pyramid.size()) {
      throw new IllegalArgumentException("row must be positive");
    }
    if (pyramid.size() == 0) {
      throw new IllegalStateException("Game has not started yet");
    }

    //IE - row 0 = 1 card, row 1 = 2 cards, row 2 = 3 cards, etc.
    return row + 1;


  }


  @Override
  public boolean isGameOver() throws IllegalStateException {

    if (pyramid.size() == 0) {
      throw new IllegalStateException("Game has not started yet.");
    }

    return !(anyDrawCardRemoves() || anyOneCardRemoves()
        || anyStockRemoves() || anyPyramidRemoves());
  }

  private boolean anyOneCardRemoves() {
    for (int i = 0; i < availableCards().size(); i++) {
      if (availableCards().get(i).getNum() == 13) {
        return true;
      }
    }
    return false;
  }

  private boolean anyDrawCardRemoves() {
    List<ICard> availableCards = this.availableCards();

    for (int i = 0; i < availableCards.size(); i++) {
      for (int k = 0; k < drawPile.size(); k++) {
        if (drawPile.get(k).getNum() + availableCards.get(i).getNum() == 13) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean anyStockRemoves() {
    List<ICard> availableCards = this.availableCards();
    if (this.drawPile.size() == 0) {
      return false;
    }

    for (int i = 0; i < availableCards.size(); i++) {
      for (int k = 0; k < stock.size(); k++) {
        if (stock.get(k).getNum() + availableCards.get(i).getNum() == 13) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean anyPyramidRemoves() {
    List<ICard> availableCards = this.availableCards();

    for (int i = 0; i < availableCards.size(); i++) {
      for (int k = 0; k < availableCards().size(); k++) {
        if (availableCards.get(i).getNum() + availableCards.get(k).getNum() == 13) {
          return true;
        }
      }
    }
    return false;
  }

  private List<ICard> availableCards() {
    List<ICard> temp = new ArrayList<>();
    for (int i = 0; i < pyramid.size(); i++) {
      for (int k = 0; k < getRowWidth(i); k++) {
        if ((getCardAt(i, k) != null) && cardIsAvailable(i, k)) {
          temp.add(pyramid.get(i).get(k));
        }
      }
    }
    return temp;
  }


  @Override
  public int getScore() throws IllegalStateException {
    if (pyramid.size() == 0) {
      throw new IllegalStateException("Game has not started yet!");
    }

    int counter = 0;
    for (List<ICard> row : pyramid) {
      for (ICard card : row) {
        if (card != null) {
          counter += card.getNum();
        }

      }

    }

    return counter;
  }

  @Override
  public ICard getCardAt(int row, int card) throws IllegalStateException {

    if (pyramid.size() == 0) {
      throw new IllegalStateException("Game has not started yet!");
    }

    if (this.outOfBounds(row, card)) {
      throw new IllegalArgumentException("card position must be in bounds with the Pyramid");
    }

    return pyramid.get(row).get(card);
  }


  @Override
  public List<ICard> getDrawCards() throws IllegalStateException {
    if (pyramid.size() == 0) {
      throw new IllegalStateException("Game has not started yet!");
    }
    List<ICard> temp = new ArrayList<>();
    for (int i = 0; i < getNumDraw(); i++) {
      temp.add(drawPile.get(i));
    }

    List<ICard> test = Collections.emptyList();
    return temp;


  }
}

