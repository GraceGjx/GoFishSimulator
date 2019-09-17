package Player;

import BaseClasses.*;

/**
 * An abstract class that has properties of a player which can be extends by different types of player.
 *
 * Created by jiaxuan2 on 3/6/17.
 */
public abstract class Player implements PlayerStrategy {

    private int playerNumber;
    private int totalNumberOfPlayers;

    public int getPlayerNumber() {
        return playerNumber;
    }

    public int getTotalNumberOfPlayers() {
        return totalNumberOfPlayers;
    }

    public void initialize(int yourPlayerNumber, int totalNumberOfPlayers) {
        this.playerNumber = yourPlayerNumber;
        this.totalNumberOfPlayers = totalNumberOfPlayers;
    }

    /**
     * Randomly generates an opponent player
     *
     * @return int represent a player index
     */
    public int getRandomPlayer() {
        int targetPlayer = this.getPlayerNumber();
        while (targetPlayer == this.getPlayerNumber()) {
            targetPlayer = (int) (Math.random() * getTotalNumberOfPlayers());
        }
        return targetPlayer;
    }

    /**
     * Randomly generates a rank from the current hand
     *
     * @param hand the current hand
     * @return int represent a rank
     */
    public int getRandomRank(Hand hand) {
        int cardIndex = (int) (Math.random() * hand.size());
        return hand.getCard(cardIndex).getRank();
    }
}
