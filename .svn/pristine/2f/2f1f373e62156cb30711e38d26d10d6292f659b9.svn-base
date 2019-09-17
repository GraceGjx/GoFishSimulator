package Player;

import junit.framework.TestCase;
import org.junit.Before;
import BaseClasses.*;

/**
 * Created by jiaxuan2 on 3/7/17.
 */
public class NaivePlayerTest extends TestCase {

    private final Deck deck = new Deck();
    private final int numOfCards = 6;
    private final int playerNum = 1;
    private final int totalPlayers = 10;

    private NaivePlayer player = new NaivePlayer();
    private Hand hand;

    @Before
    public void setUp() throws Exception {
        hand = new Hand(deck.draw(numOfCards));
        player.initialize(playerNum, totalPlayers);
    }

    public void testDoTurn() throws Exception {
        Play expPlay = player.doTurn(hand);
        int lowerBound = 0;
        int upplerBound = 12;

        assertTrue(expPlay.getTargetPlayer() != player.getPlayerNumber());
        assertTrue(lowerBound <= expPlay.getRank());
        assertTrue(expPlay.getRank() <= upplerBound);
        assertTrue(expPlay.getTargetPlayer() < player.getTotalNumberOfPlayers());
    }

}