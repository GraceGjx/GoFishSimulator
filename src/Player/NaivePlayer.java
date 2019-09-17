package Player;

import BaseClasses.*;

/**
 * NaivePlayer extends Player class that has no strategy, randomly choosing opponent and card rank.
 * playOccurred in NaivePlayer doesn't do anything.
 *
 * Created by jiaxuan2 on 3/6/17.
 */
public class NaivePlayer extends Player {

    /**
     * The method invoked on a player each time it is their turn to act.
     *
     * @param hand The current state of the player's hand when they are to act
     * @return the action they choose to take in response to their current hand/game state
     */
    public Play doTurn(Hand hand) {
        int targetPlayer = getRandomPlayer();
        int rank = getRandomRank(hand);
        return new Play(targetPlayer, rank);
    }


    public void playOccurred(RecordedPlay recordedPlay) {
    }

}
