import Player.*;

/**
 * A helper class to GameEngine.
 * Created by jiaxuan2 on 3/6/17.
 */
public class InputHelper {
    /**
     * Determine the type of the Player, either smart or naive
     *
     * @param input a string from the string array passed from command line
     * @return Player if is one of those two types state above, null otherwise
     */
    public Player determineType(String input) {
        input = input.toLowerCase();
        if (input.equals("naive") || input.equals("n") || input.equals("1")) {
            return new NaivePlayer();
        } else if (input.equals("smart") || input.equals("s") || input.equals("2")) {
            return new SmartPlayer();
        }
        return null;
    }



    /**
     * Determine how many cards each player will initially have
     *
     * @param args a String array passed from command line
     * @return int of the number of cards
     */
    public int determineNumberPerPlayer(String[] args) {
        int totalPlayerNum = args.length;
        int upperLimit = 52;
        int lowerLimit = 2;
        if (totalPlayerNum > upperLimit || totalPlayerNum < lowerLimit) {
            return 0;
        } else if (totalPlayerNum <= 3) {
            return 7;
        } else if (totalPlayerNum <= 10) {
            return 5;
        }
        return upperLimit / totalPlayerNum;
    }

}
