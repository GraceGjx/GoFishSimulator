package Player;

import BaseClasses.*;
import junit.framework.TestCase;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jiaxuan2 on 3/7/17.
 */
public class SmartPlayerTest extends TestCase {

    private SmartPlayer player = new SmartPlayer();
    private Hand hand;
    private List<Card> returnedCard;


    @Before
    public void setUp() throws Exception {
        List<Card> handCard = new ArrayList<>();
        handCard.add(new Card(2, Card.Suit.CLUBS));
        handCard.add(new Card(3, Card.Suit.HEARTS));
        hand = new Hand(handCard);

        returnedCard = new ArrayList<>();
        returnedCard.add(new Card(2, Card.Suit.CLUBS));

        player.initialize(2, 4);
    }

    /***** Below are different cases for doTurn() *****/


    //When there is no recordedPlay
    public void testDoTurn1() throws Exception {
        RecordedPlay nullRecordedPlay = null;
        player.playOccurred(nullRecordedPlay);
        Play expPlay = player.doTurn(hand);

        assertTrue(expPlay.getTargetPlayer() != player.getPlayerNumber());
        assertTrue(expPlay.getTargetPlayer() < 4);

    }

    //When there is recordedPlay, but the cards returned is empty
    public void testDoTurn2() throws Exception {
        RecordedPlay lastRecordedPlay = new RecordedPlay(1, 2, 5, new ArrayList<Card>());
        player.playOccurred(lastRecordedPlay);
        Play expPlay = player.doTurn(hand);

        assertTrue(expPlay.getTargetPlayer() != player.getPlayerNumber());
        assertTrue(expPlay.getRank() != 5);

    }

    //When there is recordedPlay, the current player is last targetPlayer
    public void testDoTurn3() throws Exception {
        RecordedPlay lastRecordedPlay = new RecordedPlay(1, 2, 2, returnedCard);
        player.playOccurred(lastRecordedPlay);
        Play expPlay = player.doTurn(hand);
        int expectTarget = 1;
        int expectRank = 2;

        assertEquals(expectTarget, expPlay.getTargetPlayer());
        assertEquals(expectRank, expPlay.getRank());
    }


    //When there is recordedPlay, the current player is not last targetPlayer
    public void testDoTurn4() throws Exception {
        RecordedPlay lastRecordedPlay = new RecordedPlay(1, 0, 2, returnedCard);
        player.playOccurred(lastRecordedPlay);
        Play expPlay = player.doTurn(hand);
        int expectTarget = 1;
        int expectRank = 2;

        assertEquals(expectTarget, expPlay.getTargetPlayer());
        assertEquals(expectRank, expPlay.getRank());
    }

}