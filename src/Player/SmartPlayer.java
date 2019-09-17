package Player;

import BaseClasses.*;

/**
 * SmartPlayer extends Player class that has a strategy of choosing opponent and card.
 *
 * Created by jiaxuan2 on 3/6/17.
 */
public class SmartPlayer extends Player {

    private int targetPlayerNum;
    private int targetRank;

    /**
     * If there is no preference targetRank and targetPlayerNum, choose random opponent
     * and random rank
     *
     * @param hand The current state of the player's hand when they are to act
     * @return the action they choose to take in response to their current hand/game state
     */
    public Play doTurn(Hand hand) {
        Play play;
        if (!hand.hasRank(targetRank)) {
            targetPlayerNum = getRandomPlayer(targetPlayerNum);
            targetRank = getRandomRank(hand);
        }
        play = new Play(targetPlayerNum, targetRank);
        return play;
    }


    /**
     * If the source player from last play get a set a card, then set the target player to that
     * player and set target rank to that card rank. Otherwise, set both targetPlayerNum and target Rank
     * to be -1
     *
     * @param recordedPlay an object representing the information of the play that just occurred and its results.
     */
    public void playOccurred(RecordedPlay recordedPlay) {
        int currentNum = this.getPlayerNumber();
        if (recordedPlay != null && recordedPlay.getSourcePlayer() != currentNum) {
            targetPlayerNum = recordedPlay.getSourcePlayer();
            targetRank = recordedPlay.getRank();
        }
        else {
            targetPlayerNum = -1;
            targetRank = -1;
        }
    }


    /**
     * Randomly generate an opponent that is neither the player itself nor the input
     * targetPlayer
     *
     * @param inputNum int of the current targetPlayer
     */
    public int getRandomPlayer(int inputNum) {
        int targetPlayer = this.getPlayerNumber();
        while (targetPlayer == this.getPlayerNumber() || targetPlayer == inputNum) {
            targetPlayer = (int) (Math.random() * getTotalNumberOfPlayers());
        }
        return targetPlayer;
    }

}
