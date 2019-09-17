import Player.*;
import junit.framework.TestCase;

/**
 * Created by jiaxuan2 on 3/7/17.
 */
public class InputHelperTest extends TestCase {

    private String input;
    private String[] expArgs;
    InputHelper newHelper = new InputHelper();


    /***** Below are the tests for determineType() *****/


    //The input is "naive" in lowercase
    public void testDetermineTypeNaive1() throws Exception {
        input = "naive";
        assertTrue(newHelper.determineType(input) instanceof NaivePlayer);
    }

    //The input is "n"
    public void testDetermineTypeNaive2() throws Exception {
        input = "n";
        assertTrue(newHelper.determineType(input) instanceof NaivePlayer);
    }

    //The input is "1"
    public void testDetermineTypeNaive3() throws Exception {
        input = "1";
        assertTrue(newHelper.determineType(input) instanceof NaivePlayer);
    }

    //The input is "naive" in mix case
    public void testDetermineTypeNaive4() throws Exception {
        input = "nAIvE";
        assertTrue(newHelper.determineType(input) instanceof NaivePlayer);
    }

    //The input is "smart" in lowercase
    public void testDetermineTypeSmart1() throws Exception {
        input = "smart";
        assertTrue(newHelper.determineType(input) instanceof SmartPlayer);
    }

    //The input is "s"
    public void testDetermineTypeSmart2() throws Exception {
        input = "s";
        assertTrue(newHelper.determineType(input) instanceof SmartPlayer);
    }

    //The input is "2" in lowercase
    public void testDetermineTypeSmart3() throws Exception {
        input = "2";
        assertTrue(newHelper.determineType(input) instanceof SmartPlayer);
    }

    //The input is "smart" in mix case
    public void testDetermineTypeSmart4() throws Exception {
        input = "SmArT";
        assertTrue(newHelper.determineType(input) instanceof SmartPlayer);
    }

    //The input neither "naive" nor "smart"
    public void testDetermineType() throws Exception {
        input = "different";
        assertNull(newHelper.determineType(input));
    }


    /***** Below are different cases for determineNumberPerPlayer  *****/


    //When the number of player exceeds the upper boundary, 52
    public void testDetermineNumberPerPlayer1() throws Exception {
        expArgs = new String[60];
        int expect = 0;
        int actual = newHelper.determineNumberPerPlayer(expArgs);

        assertEquals(expect, actual);
    }


    //When the number of player less then lower boundary, 2
    public void testDetermineNumberPerPlayer2() throws Exception {
        expArgs = new String[1];
        int expect = 0;
        int actual = newHelper.determineNumberPerPlayer(expArgs);

        assertEquals(expect, actual);
    }


    //When the number of player less than equal to 3
    public void testDetermineNumberPerPlayer3() throws Exception {
        expArgs = new String[2];
        int expect = 7;
        int actual = newHelper.determineNumberPerPlayer(expArgs);

        assertEquals(expect, actual);
    }

    //When the number of player less than equal to 10
    public void testDetermineNumberPerPlayer4() throws Exception {
        expArgs = new String[8];
        int expect = 5;
        int actual = newHelper.determineNumberPerPlayer(expArgs);

        assertEquals(expect, actual);
    }

    //When the number of player greater than 10
    public void testDetermineNumberPerPlayer5() throws Exception {
        expArgs = new String[20];
        int expect = 2;
        int actual = newHelper.determineNumberPerPlayer(expArgs);

        assertEquals(expect, actual);
    }

}