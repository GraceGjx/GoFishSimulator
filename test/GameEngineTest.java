import BaseClasses.*;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by jiaxaun2 on 3/7/17.
 */
public class GameEngineTest extends TestCase{

    private GameEngine newGame;
    private Map<Integer, Integer> scoreMap;
    private Map<Integer, Hand> handMap;
    private Hand hand;
    private List<Card> cardList = generateList();


    @Before
    public void setUp() throws Exception {
        newGame = new GameEngine();
        newGame.initialScore(4);
        scoreMap = newGame.scoreMap;
        handMap = newGame.handMap;
        hand = new Hand(cardList);
    }


    /***** Below are different cases for setUpPlayers() *****/


    //One of the player type is invalid
    public void testSetUpPlayers1() throws Exception {
        String[] expArg = {"n", "s", "okay", "1", "2"};
        assertFalse(newGame.setUpPlayers(expArg));
    }

    //The input length is too much
    public void testSetUpPlayers2() throws Exception {
        String[] expArg = new String[60];
        assertFalse(newGame.setUpPlayers(expArg));
    }

    //The length and player types are both valid
    public void testSetUpPlayers3() throws Exception {
        String[] expArg = {"n", "s", "S", "1", "2"};
        assertTrue(newGame.setUpPlayers(expArg));
    }


    /***** Below are different cases for checkCounter() *****/


    //When currentNum is greater than size
    public void testCheckCounter1() throws Exception {
        int currentNum = 7;
        int size = 5;
        int expect = 0;
        int actual = newGame.checkCounter(currentNum, size);

        assertEquals(expect, actual);
    }


    //When currentNum is less than size
    public void testCheckCounter2() throws Exception {
        int currentNum = 2;
        int size = 5;
        int expect = 2;
        int actual = newGame.checkCounter(currentNum, size);

        assertEquals(expect, actual);
    }


    /***** Below are different cases for hasBookIndex() *****/


    //When the hand has less than 4 cards
    public void testHasBookIndex1() throws Exception {
        int expect = -1;
        int actual = newGame.hasBookIndex(hand);

        assertEquals(expect, actual);
    }

    //When the hand has more than 4 cards, doesn't contain a book
    public void testHasBookIndex2() throws Exception {
        cardList.add(new Card(4, Card.Suit.DIAMONDS));
        cardList.add(new Card(8, Card.Suit.CLUBS));
        cardList.add(new Card(1, Card.Suit.SPADES));
        Hand actHand = new Hand(cardList);
        int expect = -1;
        int actual = newGame.hasBookIndex(actHand);

        assertEquals(expect, actual);
    }

    //When the hand has more than 4 cards, contains a book
    public void testHasBookIndex3() throws Exception {
        cardList.add(new Card(3, Card.Suit.DIAMONDS));
        cardList.add(new Card(3, Card.Suit.CLUBS));
        cardList.add(new Card(3, Card.Suit.SPADES));
        Hand actHand = new Hand(cardList);
        int expect = 3;
        int actual = newGame.hasBookIndex(actHand);

        assertEquals(expect, actual);
    }


    /***** Below is the test for handWithoutBook() *****/

    public void testHandWithoutBook() throws Exception {
        handMap.put(1, hand);
        List<Card> newList = new ArrayList<>();
        newList.add(new Card(12, Card.Suit.CLUBS));
        newList.add(new Card(12, Card.Suit.HEARTS));
        Hand expHand = new Hand(newList);
        Hand actHand = newGame.handWithoutBook(1, hand, 3);

        assertEquals(expHand, actHand);

    }

    /***** Below are different cases for addCard() *****/

    //When the new card list is empty
    public void testAddCard1() throws Exception {
        List<Card> expNewCard = new ArrayList<>();
        Hand actualHand = newGame.addCard(hand, expNewCard);
        assertEquals(hand, actualHand);

    }

    //When the new card list has only one card
    public void testAddCard2() throws Exception {
        List<Card> expNewCard = new ArrayList<>();
        Card newCard = new Card(9, Card.Suit.DIAMONDS);
        expNewCard.add(newCard);
        Hand actualHand = newGame.addCard(hand, expNewCard);
        cardList.add(newCard);
        Hand expectHand = new Hand(cardList);

        assertEquals(expectHand, actualHand);

    }

    //When the new card list has multiple cards
    public void testAddCard3() throws Exception {
        List<Card> expNewCard = new ArrayList<>();
        Card newCard1 = new Card(9, Card.Suit.DIAMONDS);
        Card newCard2 = new Card(9, Card.Suit.HEARTS);
        expNewCard.add(newCard1);
        expNewCard.add(newCard2);
        Hand actualHand = newGame.addCard(hand, expNewCard);
        cardList.add(newCard1);
        cardList.add(newCard2);
        Hand expectHand = new Hand(cardList);

        assertEquals(expectHand, actualHand);

    }


    /***** Below is the test for removeCard() *****/


    public void testRemoveCard() throws Exception {
        List<Card> expRemovedCard = new ArrayList<>();
        expRemovedCard.add(new Card(12, Card.Suit.CLUBS));
        expRemovedCard.add(new Card(12, Card.Suit.HEARTS));
        List<Card> actRemovedCard = newGame.removeCard(1, hand, 12);

        assertEquals(expRemovedCard.size(), actRemovedCard.size());
        assertEquals(expRemovedCard.get(0), actRemovedCard.get(0));
        assertFalse(handMap.containsValue(actRemovedCard));
    }


    /***** Below is the test for initialScore() *****/

    public void testInitialScore() throws Exception {
        int totalPlayers = 4;

        assertEquals(totalPlayers, scoreMap.size());
    }

    /***** Below is the test for updateScore() *****/


    //Test updateScore by giving the scoreMap player number
    public void testUpdateScore() throws Exception {
        int playerNum1 = 1;

        newGame.updateScore(playerNum1);
        assertTrue(scoreMap.containsKey(playerNum1));
        assertTrue(scoreMap.get(playerNum1) == 1);
    }


    /***** Below is the test for getWinner() ******/


    public void testGetWinner() throws Exception {
        int playerIndex = 2;
        newGame.updateScore(2);
        String actWinner = newGame.getWinner();
        String expWinner = "Player" + playerIndex;

        assertEquals(expWinner, actWinner);
    }


    /**
     * A test helper method that generate a list of three cards
     * @return a list of cards
     */
    public List<Card> generateList(){
        List<Card> cardList = new ArrayList<>();
        cardList.add(new Card(12, Card.Suit.CLUBS));
        cardList.add(new Card(12, Card.Suit.HEARTS));
        cardList.add(new Card(3, Card.Suit.HEARTS));
        return cardList;
    }
}