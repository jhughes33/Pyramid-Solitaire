package cs3500.pyramidsolitaire.model.hw02;

import java.util.List;

/**
 * The model for playing a game of Pyramid Solitaire: this maintains the state and enforces the
 * rules of gameplay.
 *
 * @param <K> the type of cards this model uses
 */
public interface PyramidSolitaireModel<K> {

  /**
   * Return a valid and complete deck of cards for a game of Pyramid Solitaire. There is no
   * restriction imposed on the ordering of these cards in the deck. The validity of the deck is
   * determined by the rules of the specific game in the classes implementing this interface.
   *
   * @return the deck of cards as a list
   */
  List<K> getDeck();

  /**
   * <p>Deal a new game of Pyramid Solitaire.
   * The cards to be used and their order are specified by the the given deck, unless the {@code
   * shuffle} parameter indicates the order should be ignored.</p>
   *
   * <p>This method first verifies that the deck is valid. It deals cards in rows
   * (left-to-right, top-to-bottom) into the characteristic pyramid shape with the specified number
   * of rows, followed by the specified number of draw cards. When {@code shuffle} is {@code false},
   * the 0th card in {@code deck} is used as the first card dealt.</p>
   *
   * <p>This method should have no other side effects, and should work for any valid arguments.</p>
   *
   * @param deck the deck to be dealt
   * @param shuffle if {@code false}, use the order as given by {@code deck}, otherwise use a
   *        randomly shuffled order
   * @param numRows number of rows in the pyramid
   * @param numDraw number of draw cards available at a time
   * @throws IllegalArgumentException if the deck is null or invalid, the number of pyramid rows is
   *         non-positive, the number of draw cards available at a time is negative, or a full
   *         pyramid and draw pile cannot be dealt with the number of given cards in deck
   */

  void startGame(List<K> deck, boolean shuffle, int numRows, int numDraw)
      throws IllegalArgumentException;

  /**
   * Remove two exposed cards on the pyramid, using the two specified card positions.
   *
   * @param row1 row of first card position, numbered from 0 from the top of the pyramid
   * @param card1 card of first card position, numbered from 0 from left
   * @param row2 row of second card position
   * @param card2 card of second card position
   * @throws IllegalArgumentException if the attempted remove is invalid
   * @throws IllegalStateException if the game has not yet been started
   */
  void remove(int row1, int card1, int row2, int card2)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * Remove a single card on the pyramid, using the specified card position.
   *
   * @param row row of the desired card position, numbered from 0 from the top of the pyramid
   * @param card card of the desired card position, numbered from 0 from left
   * @throws IllegalArgumentException if the attempted remove is invalid
   * @throws IllegalStateException if the game has not yet been started
   */
  void remove(int row, int card) throws IllegalArgumentException, IllegalStateException;

  /**
   * Remove two cards, one from the draw pile and one from the pyramid.
   *
   * @param drawIndex the card from the draw pile, numbered from 0 from left
   * @param row row of the desired card position, numbered from 0 from the top of the pyramid
   * @param card card of the desired card position, numbered from 0 from left
   * @throws IllegalArgumentException if the attempted remove is invalid
   * @throws IllegalStateException if the game has not yet been started
   */
  void removeUsingDraw(int drawIndex, int row, int card)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * Discards an individual card from the draw pile.
   *
   * @param drawIndex the card from the draw pile to be discarded
   * @throws IllegalArgumentException if the index is invalid or no card is present there.
   * @throws IllegalStateException if the game has not yet been started
   */
  void discardDraw(int drawIndex) throws IllegalArgumentException, IllegalStateException;

  /**
   * Returns the number of rows originally in the pyramid, or -1 if the game hasn't been started.
   *
   * @return the height of the pyramid, or -1
   */
  int getNumRows();

  /**
   * Returns the maximum number of visible cards in the draw pile, or -1 if the game hasn't been
   * started.
   *
   * @return the number of visible cards in the draw pile, or -1
   */
  int getNumDraw();

  /**
   * Returns the width of the requested row, measured from the leftmost card to the rightmost card
   * (inclusive) as the game is initially dealt.
   *
   * @param row the desired row (0-indexed)
   * @return the number of spaces needed to deal out that row
   * @throws IllegalArgumentException if the row is invalid
   * @throws IllegalStateException if the game has not yet been started
   */
  int getRowWidth(int row);

  /**
   * Signal if the game is over or not. A game is said to be over if there are no possible removes
   * or discards.
   *
   * @return true if game is over, false otherwise
   * @throws IllegalStateException if the game hasn't been started yet
   */
  boolean isGameOver() throws IllegalStateException;

  /**
   * Return the current score, which is the sum of the values of the cards remaining in the
   * pyramid.
   *
   * @return the score
   * @throws IllegalStateException if the game hasn't been started yet
   */
  int getScore() throws IllegalStateException;

  /**
   * Returns the card at the specified coordinates.
   *
   * @param row row of the desired card (0-indexed from the top)
   * @param card column of the desired card (0-indexed from the left)
   * @return the card at the given position, or <code>null</code> if no card is there
   * @throws IllegalArgumentException if the coordinates are invalid
   * @throws IllegalStateException if the game hasn't been started yet
   */
  K getCardAt(int row, int card) throws IllegalStateException;

  /**
   * Returns the currently available draw cards. There should be at most {@link
   * PyramidSolitaireModel#getNumDraw} cards (the number specified when the game started) -- there
   * may be fewer, if cards have been removed.
   *
   * @return the ordered list of available draw cards
   * @throws IllegalStateException if the game hasn't been started yet
   */
  List<K> getDrawCards() throws IllegalStateException;
}
