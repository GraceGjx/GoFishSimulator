package BaseClasses;

import junit.framework.TestCase;
import org.junit.Before;

import java.util.HashSet;
import java.util.Set;


/**
 * Created by zilles on 2/28/17.
 * Modified by jiaxuan2 on 3/6/17.
 */
public class DeckTest extends TestCase{

    private Deck deck;

    @Before
    public void setUp() throws Exception {
        deck = new Deck();
    }

    public void testNewDeckIsNotEmpty() throws Exception {
        assertFalse(deck.isEmpty());
    }

    public void testNewDeckHas52Cards() throws Exception {
        assertEquals(52, getRemainingCardCount());
    }

    private int getRemainingCardCount() {
        int cardCount = 0;
        while(!deck.isEmpty()) {
            deck.draw();
            cardCount++;
        }
        return cardCount;
    }


    public void testNoRepeatedCards() throws Exception {
        Set<Integer> cardSet = new HashSet<>();

        while(!deck.isEmpty()) {
            Card card = deck.draw();
            int hashCode = card.hashCode();
            assertFalse(cardSet.contains(hashCode));
            cardSet.add(hashCode);
        }
    }


    public void testDrawMultiple() throws Exception {
        assertEquals(17, deck.draw(17).size());
        assertEquals(52 - 17, getRemainingCardCount());

    }
}