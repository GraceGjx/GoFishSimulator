import BaseClasses.*;
import Player.*;

import java.util.*;

/**
 * Created by jiaxuan2 on 3/6/17.
 */
public class GameEngine {

    private final String INVALID_ERROR = "The Player type is invalid";
    private final String EXCEED_BOUNDARY = "The number of players is invalid";

    protected Map<Integer, Player> playerMap = new HashMap<>();
    protected Map<Integer, Hand> handMap = new HashMap<>();
    protected Map<Integer, Integer> scoreMap = new HashMap<>();
    private Deck newDeck = new Deck();
    private RecordedPlay recordedPlay = null;

    /**
     * Set up each player based on the command line, and give a certain number of cards
     * to each player. Put the player name and player type to a hashmap
     *
     * @param args a String array passed from the command line
     * @return true if the players setup correctly, false otherwise
     */
    public boolean setUpPlayers(String[] args) {
        InputHelper newHelper = new InputHelper();

        int totalPlayers = args.length;
        int cardsPerPerson = newHelper.determineNumberPerPlayer(args);

        if (cardsPerPerson != 0) {
            for (int playerIndex = 0; playerIndex < args.length; playerIndex++) {
                Player player = newHelper.determineType(args[playerIndex]);
                if (player == null) {
                    System.out.println(INVALID_ERROR);
                    return false;
                }
                player.initialize(playerIndex, totalPlayers);
                playerMap.put(playerIndex, player);
                List<Card> startCards = newDeck.draw(cardsPerPerson);
                Hand playerHand = new Hand(startCards);
                handMap.put(playerIndex, playerHand);
            }
            return true;
        }
        System.out.println(EXCEED_BOUNDARY);
        return false;
    }


    /**
     * Play a complete round of the goFish game
     * @return string of the winner
     */
    public String playAGame() {
        //Set up for all variables
        int maxBooks = 13;
        int bookNum = 0;
        int currentPlayerNum = 0;
        initialScore(playerMap.size());

        while (bookNum < maxBooks) {
            int totalPlayer = playerMap.size();
            if (totalPlayer < 2) {
                break;
            }

            currentPlayerNum = checkCounter(currentPlayerNum, totalPlayer);
            Player currentPlayer = playerMap.get(currentPlayerNum);
            Hand currentHand = handMap.get(currentPlayerNum);

            //Check whether the current player has a book and update the number of books
            int bookIndex = hasBookIndex(currentHand);
            if (bookIndex >= 0) {
                System.out.println("Player" + currentPlayerNum + " made a book of "
                        + Card.CARD_NAMES[bookIndex] + "s");
                currentHand = handWithoutBook(currentPlayerNum, currentHand, bookIndex);
                bookNum++;
            }

            //When the player has cards in the hand
            if (currentHand.size() > 0) {
                currentPlayer.playOccurred(recordedPlay);

                Play currentPlay = currentPlayer.doTurn(currentHand);
                int targetPlayerNum = currentPlay.getTargetPlayer();
                int targetRank = currentPlay.getRank();

                System.out.print("Player" + currentPlayerNum + " asks Player " + targetPlayerNum
                        + " for " + Card.CARD_NAMES[targetRank] + "s and got ");

                Hand targetHand = handMap.get(targetPlayerNum);
                List<Card> changeCards = new ArrayList<>();

                //When the target has cards with the target rank
                if (targetHand.size() > 0 && targetHand.hasRank(targetRank)) {
                    changeCards = removeCard(targetPlayerNum, targetHand, targetRank);
                    handMap.put(currentPlayerNum, addCard(currentHand, changeCards));
                    recordedPlay = new RecordedPlay(currentPlayerNum, targetPlayerNum, targetRank, changeCards);

                    System.out.println(changeCards.size() + " card(s).");
                    continue;
                } else {
                    System.out.println("0 card(s).");
                    if (!newDeck.isEmpty()) {
                        drawANewCard(currentPlayerNum, currentHand);
                    }
                }
                recordedPlay = new RecordedPlay(currentPlayerNum, targetPlayerNum, targetRank, changeCards);
            }
            //When the player doesn't have any cards
            else {
                if (newDeck.isEmpty()) {
                    playerMap.remove(currentPlayer);
                } else {
                    drawANewCard(currentPlayerNum, currentHand);
                    continue;
                }
            }
            currentPlayerNum++;
        }
        return getWinner();
    }


    /**
     * Draw a new card from a deck
     *
     * @param currentPlayerNum int of the current player number
     * @param currentHand      hand of the current player
     */
    public void drawANewCard(int currentPlayerNum, Hand currentHand) {
        List<Card> card = new ArrayList<>();
        card.add(newDeck.draw());
        handMap.put(currentPlayerNum, addCard(currentHand, card));
    }


    /**
     * Check whether there has been through one loop of all players
     *
     * @param currentNum int for current player
     * @param size       total number of players
     * @return 0 if it has been one loop, otherwise currentNum
     */
    public int checkCounter(int currentNum, int size) {
        if (currentNum >= size) {
            return 0;
        }
        return currentNum;
    }


    /**
     * Determine whether hand has a book
     *
     * @param hand current player's hand
     * @return -1 if there is no book found, int between 0 - 12 if a corresponding book
     * is found
     */
    public int hasBookIndex(Hand hand) {
        if (hand.size() < 4) {
            return -1;
        } else {
            HashMap<Integer, Integer> rankOccurrence = new HashMap<>();
            for (Card card : hand) {
                int currentRank = card.getRank();
                if (!rankOccurrence.containsKey(currentRank)) {
                    rankOccurrence.put(currentRank, 1);
                } else {
                    int occurrence = rankOccurrence.get(currentRank) + 1;
                    rankOccurrence.put(currentRank, occurrence);
                }

                if (rankOccurrence.get(currentRank) == 4) {
                    return currentRank;
                }
            }
        }
        return -1;
    }


    /**
     * Remove the book from the current player's hand, update the player's score, and
     * return a new hand
     *
     * @param currentNum  int of current player number
     * @param currentHand int of current player's hand
     * @param bookIndex   int of the rank of cards that makes a book
     * @return new hand of the current player without the book
     */
    public Hand handWithoutBook(int currentNum, Hand currentHand, int bookIndex) {
        removeCard(currentNum, currentHand, bookIndex);
        updateScore(currentNum);
        return handMap.get(currentNum);
    }


    /**
     * Initial each player's score at the beginning
     *
     * @param totalPlayer int of total of players
     */
    public void initialScore(int totalPlayer) {
        for (int playerIndex = 0; playerIndex < totalPlayer; playerIndex++) {
            scoreMap.put(playerIndex, 0);
        }
    }


    /**
     * Update each player's score with the discover of one more book
     *
     * @param playerNumber int of total of players
     */
    public void updateScore(int playerNumber) {
        if (scoreMap.containsKey(playerNumber)) {
            int updateScore = scoreMap.get(playerNumber) + 1;
            scoreMap.put(playerNumber, updateScore);
        }
    }


    /**
     * Update handList with the new cards
     *
     * @param hand current player's hand
     * @param card new card that just received
     * @return Hand with the original Hand and a new card
     */
    public Hand addCard(Hand hand, List<Card> card) {
        List<Card> cardsList = new ArrayList<>();
        for (Iterator iterator = hand.iterator(); iterator.hasNext(); ) {
            cardsList.add((Card) iterator.next());
        }
        cardsList.addAll(card);
        return new Hand(cardsList);
    }


    /**
     * Remove cards from hand with corresponding rank, and update hand with the corresponding player.
     *
     * @param targetNum  int of targetPlayerNum
     * @param hand       hand of the target player
     * @param targetRank int of the targetRank
     * @return a list of cards that has been removed from the opponent
     */
    public List<Card> removeCard(int targetNum, Hand hand, int targetRank) {
        List<Card> removeList = new ArrayList<>();
        List<Card> newHandList = new ArrayList<>();
        for (Iterator iterator = hand.iterator(); iterator.hasNext(); ) {
            Card card = (Card) iterator.next();
            if (card.getRank() == (targetRank)) {
                removeList.add(card);
            } else {
                newHandList.add(card);
            }
        }
        handMap.put(targetNum, new Hand(newHandList));
        return removeList;
    }


    /**
     * Put each player's score into a string list, the last element contains the winner.
     *
     * @return a string list of the game result
     */
    public String getWinner() {
        ArrayList<String> result = new ArrayList<>();
        int winnerIndex = 0;
        for (int playerIndex = 0; playerIndex < scoreMap.size(); playerIndex++) {
            int score = scoreMap.get(playerIndex);
            result.add("Player" + playerIndex + " had " + score + " points.");
            if (score > scoreMap.get(winnerIndex)) {
                winnerIndex = playerIndex;
            }
        }
        result.add("Player" + winnerIndex + " won!");
        for (String message : result) {
            System.out.println(message);
        }
        return "Player" + winnerIndex;
    }

}
