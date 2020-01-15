/* Junit testcase class-1 */
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
public class MyTest1 {
    @Test(expected = GameWinnerException.class)
    public void testSum() throws GameWinnerException {
        System.out.println("For Game Winner");
        new Game();
    }

    @Test(expected = VultureBiteException.class)
    public void testSum2() throws VultureBiteException, GameWinnerException {
        System.out.println("For Vulture Exception");
        new Game();
    }

    @Test(expected = SnakeBiteException.class)
    public void testSum3() throws SnakeBiteException, GameWinnerException {
        System.out.println("For SankeBite Exception");
        new Game();
    }

    @Test(expected = TrampolineException.class)
    public void testSum4() throws TrampolineException, GameWinnerException {
        System.out.println("For Trampoline Exception");
        new Game();
    }

    @Test(expected = CricketBiteException.class)
    public void testSum5() throws CricketBiteException, GameWinnerException {
        System.out.println("For CricketBite Exception");
        new Game();
        System.out.println("----------------------------- End Cricket Exception -----------------------------");
    }

    @Test
    public void testserialize() throws GameWinnerException, IOException, ClassNotFoundException {
        System.out.println("----------------------------- For Seriliaizing -----------------------------");
        GameState g= new Game().returnserilzed();
        GameState t= new Game().deserialize(g.getPlayertobesave().getName());
        System.out.println("----------------------------- For De-Seriliaizing -----------------------------");
        assertEquals(g, t);
    }
    
}