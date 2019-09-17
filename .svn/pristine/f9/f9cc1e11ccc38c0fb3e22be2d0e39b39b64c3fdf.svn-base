package BaseClasses;

import junit.framework.TestCase;
import org.junit.Before;

/**
 * Created by zilles on 2/28/17.
 * Modified by jiaxuan2 on 3/6/17
 */
public class CardTest extends TestCase{

    private static final int RANK = 7;
    private static final Card.Suit SUIT = Card.Suit.CLUBS;

    private Card card;

    @Before
    public void setUp() throws Exception {
        card = new Card(RANK, SUIT);
    }

    public void testGetRank() throws Exception {
        assertEquals(RANK, card.getRank());
    }


    public void testGetSuit() throws Exception {
        assertEquals(SUIT, card.getSuit());
    }


    public void testCheckHashCode() throws Exception {
        assertEquals(13*3 + 7, card.hashCode());
    }


    public void testCheckNames() throws Exception {
        assertEquals("8 of CLUBS", card.getCardName());
        assertEquals("8C", card.getShortName());
    }
}