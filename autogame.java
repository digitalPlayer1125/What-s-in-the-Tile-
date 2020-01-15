import java.io.*;
import java.util.*;
public class autogame {
    public static void main(String args[]) throws GameWinnerException{
        new Game();
    }
}

class Game{
    private final String playername;
    private final int nSnakes, nCricket, nVultures, nTrampolines, tiles, nWhites;
    private int faketilecount;
    private String fakeplayername;
    private final Tiles Layout[];
    private final ArrayList<Tiles> arr = new ArrayList<Tiles>(Arrays.asList(new Snake(10), new Vulture(10), new Cricket(10), new White(10), new Trampoline(10)));
    private GameState forchecking;

    //Initial Menu
    GameState Checkforload(){
        System.out.println("Wanna Load?: Y/N");
        try{
            String g= new Scanner(System.in).next();
            if(g.equals("Y")){
                System.out.println("Please enter your name:");
                String ssstemp= new Scanner(System.in).next();
                //If found return 
                GameState Gamestateload= deserialize(ssstemp);
                if(/*Condition for checking it in a file: */Gamestateload!=null){
                    System.out.println("Whohoho");
                    return Gamestateload;
                }
            }
            else if(g.equals("N")){return new GameState(new Player(""), new Tiles[1]);}
            else{return Checkforload();}
        }
        catch(Exception e){
            System.out.println("Saved Games Not Found!");
            return Checkforload();
        }
        return new GameState(new Player(""), new Tiles[1]);
    }

    Game() throws GameWinnerException{
        forchecking= new GameState(new Player("S"), new Tiles[1]);
        GameState checkload= Checkforload();

        //If user found then initialize the variables
        if(!(checkload.getPlayertobesave().getName().equals(""))){
            //Player Config
            Player tempplayer= checkload.getPlayertobesave();
            playername= tempplayer.getName();

            //Layout Config
            Layout= checkload.getLayoutSave();
            tiles= checkload.getLayoutSave().length;
            int temp1= 0, temp2= 0, temp3= 0, temp4= 0, temp5= 0;
            for(int i=0; i<tiles; i++) {
                String temp= Layout[i].getClass().getName();
                if(temp.equals("Snake")){temp1++;}
                else if(temp.equals("Vulture")){temp2++;}
                else if(temp.equals("Cricket")){temp3++;}
                else if(temp.equals("White")){temp4++;}
                else if(temp.equals("Trampoline")){temp5++;}
            }
            System.out.println(">>Setting up the race track.... ");
            nSnakes= temp1;
            nCricket= temp2;
            nVultures= temp3;
            nTrampolines= temp4;
            nWhites= temp5;
            System.out.println("---------------------------------------------------------------------------------");
            System.out.println(">>Safe: There are " + nWhites+" White Tiles on your track.");
            System.out.println(">>Danger: There are " + nSnakes + ", " + nCricket + " and " + nVultures + " numbers of Snakes, Cricket, and Vultures respectively on your track.");
            //Defining Snakes, Cricket and Vultures and then randomly finalizing their attack.
            System.out.println(">>Danger : Each Snake, Cricket and Vulture can throw you back by " + -1*arr.get(0).getPosincordic() + ", " + -1*arr.get(2).getPosincordic() + ", " + -1*arr.get(1).getPosincordic() + " number of Tiles respectively.");
            //Defining Trampoline on the tracka and get their help: 
            System.out.println(">>Good News: There are " + nTrampolines+" Trampolines on your track.");
            System.out.println(">>Good News: Each Trampoline can advance you by " +  arr.get(4).getPosincordic() + " tiles.");
            System.out.println("---------------------------------------------------------------------------------");
            start(tempplayer);
        }

        //When starting a new game
        else{
            //Tiles Input
            System.out.println();
            integerinput();
            tiles= faketilecount;
            System.out.println();

            /*
                Deciding the Layout
            */
            Layout= new Tiles[tiles];
            //Temp variables
            int temp1= 0, temp2= 0, temp3= 0, temp4= 0, temp5= 0;
            //Randomly Assigning
            for(int i=0; i<tiles; i++) Layout[i]= arr.get(new Random().nextInt(arr.size()));
            //Taking the count
            for(int i=0; i<tiles; i++) {
                String temp= Layout[i].getClass().getName();
                if(temp.equals("Snake")){temp1++;}
                else if(temp.equals("Vulture")){temp2++;}
                else if(temp.equals("Cricket")){temp3++;}
                else if(temp.equals("White")){temp4++;}
                else if(temp.equals("Trampoline")){temp5++;}
            }

            System.out.println(">>Setting up the race track.... ");
            nSnakes= temp1;
            nCricket= temp2;
            nVultures= temp3;
            nTrampolines= temp4;
            nWhites= temp5;

            System.out.println("---------------------------------------------------------------------------------");
            System.out.println(">>Safe: There are " + nWhites+" White Tiles on your track.");
            System.out.println(">>Danger: There are " + nSnakes + ", " + nCricket + " and " + nVultures + " numbers of Snakes, Cricket, and Vultures respectively on your track.");
            //Defining Snakes, Cricket and Vultures and then randomly finalizing their attack.
            System.out.println(">>Danger : Each Snake, Cricket and Vulture can throw you back by " + -1*arr.get(0).getPosincordic() + ", " + -1*arr.get(2).getPosincordic() + ", " + -1*arr.get(1).getPosincordic() + " number of Tiles respectively.");
            //Defining Trampoline on the tracka and get their help: 
            System.out.println(">>Good News: There are " + nTrampolines+" Trampolines on your track.");
            System.out.println(">>Good News: Each Trampoline can advance you by " +  arr.get(4).getPosincordic() + " tiles.");
            System.out.println("---------------------------------------------------------------------------------");



            System.out.println();
            nameinput();
            System.out.println();
            playername= fakeplayername;
            Player player= new Player(playername);
            player.setCurrentposition(1);
            start(player);
        }
    }

    void integerinput(){
        System.out.println(">>Enter the total number of tiles on the race track(length)");
        try{
            faketilecount= new Scanner(System.in).nextInt();
            if(faketilecount>1000000 || faketilecount<=0){
                System.out.println(">>Please limit the tile number to 10,00,000");
                integerinput();
            }
            else if(faketilecount<=0){
                System.out.println(">>Please enter the tile number greater than 0");
                integerinput();
            }
        }
        //Only InputMisMatch Exception 
        catch(InputMismatchException e){
            System.out.println(">>Tiles are supposed to be in Integer, C'mon!");
            integerinput();
        }
    }

    void randomgenerator(){
        return;
    }

    void nameinput(){
        System.out.println(">>Enter the Player Name");
        try{
            fakeplayername= new Scanner(System.in).next();

            //Name Check
            for (int i = 0; i < fakeplayername.length(); i++)
                if (Character.isLetter(fakeplayername.charAt(i))== false)
                    throw new Exception();
        }
        catch(Exception e){
            System.out.println(">>Name is supposed to be String, C'mon!");
            nameinput();
        }
    }

    void serialize(Player player) throws IOException{
        GameState temp=new GameState(player, Layout);
        ObjectOutputStream out = null;
        try{
            out = new ObjectOutputStream(new FileOutputStream(player.getName()+".txt"));
            out.writeObject(temp);
            forchecking= temp;
            System.out.println("S");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            out.close();
        }
    }

    GameState returnserilzed(){
        return forchecking;
    }

    GameState deserialize(String nametobesearched) throws IOException, ClassNotFoundException{
        ObjectInputStream in = null;
        in = new ObjectInputStream(new FileInputStream(nametobesearched+".txt"));
        Object obj = in.readObject();
        in.close();
        System.out.println(((GameState) obj).toString());
        return (GameState) obj;
    }

    int checkpointsaver(Player player1, boolean save1, boolean save2, boolean save3, Tiles[] layouttemp ){
        // System.out.println(  player1.getCurrentposition() + "  "  + tiles + "  "  + (int) 0.25*tiles + " " +   );
        //25% checkpoint
        if ( ( player1.getCurrentposition()>=(Math.round(0.25*tiles)) && (!save1) ) || ( player1.getCurrentposition()>=(Math.round(0.50*tiles)) && (!save2) ) || ( player1.getCurrentposition()>=(Math.round(0.75*tiles)) && (!save3) ) )  {
            System.out.println("Press 1 -> Save");
            System.out.println("Press 2 -> Continue");

            try{
                int temptemptemp= new Scanner(System.in).nextInt();
                if(temptemptemp==1){
                    //Do save
                    // System.out.println("This is temp: " + temptemptemp);
                    serialize(player1);
                    System.out.println("Saved!");
                    System.out.println("Exiting!");
                    System.exit(0);
                }
                else{
                    if( ( player1.getCurrentposition()>=(Math.round(0.25*tiles)) && (!save1) ) ){
                        return 25;
                    }
                    else if( ( player1.getCurrentposition()>=(Math.round(0.50*tiles)) && (!save2) ) ){
                        return 50;
                    }
                    else if( ( player1.getCurrentposition()>=(Math.round(0.75*tiles)) && (!save3) ) ){
                        return 75;
                    }
                }
            }
            catch(Exception e){
                // System.out.println(e.getMessage());
                System.out.println("Please input 1 or 2...");
                checkpointsaver(player1, save1, save2, save3, layouttemp);
            }
        }
        return 0;
    }


    void start(Player player) throws GameWinnerException{
        System.out.println(">>Starting the game with " + player.getName() + " at Tile-"  + player.getCurrentposition());
        System.out.println("");
        System.out.println(">>Control transferred to Computer for rolling the Dice for " + player.getName());

        //Hit enter to run
        System.out.println(">>Hit enter to start the game");
        String jj= new Scanner(System.in).nextLine();
        while(!jj.equals("")){
            System.out.println("Please just press enter!");
            jj= new Scanner(System.in).nextLine();
            if(jj.equals("")) break;
        }

        System.out.println(">>Game Started ======================>");

        //Dice Object
        Dice dice = new Dice();
        int noofmoves= player.getMoves();
        boolean initial= false;
        if(player.getCurrentposition()>1){initial=true;}
        faketilecount= 1;


        boolean save1=false, save2=false, save3=false;
        while(player.getCurrentposition()<tiles){

            System.out.println(player.getCurrentposition());
            //Check if anytime it wants to save
            int ggcheckpointiout= checkpointsaver(player, save1, save2, save3, Layout);

            //If it returns any of this, then that state can't be saved in the next roll;
            if(ggcheckpointiout==25){
                save1= true;
            }
            else if(ggcheckpointiout==50){
                save2= true;
            }
            else if(ggcheckpointiout==75){
                save3= true;
            }


            int a= dice.roll();
            String rollindicator= "[Roll-" + noofmoves + "]: " + player.getName() + " rolled " + a + " at Tile-" + player.getCurrentposition();
            if(initial){
                System.out.println(rollindicator);
                if(player.getCurrentposition()+a<tiles){

                    player.setCurrentposition(player.getCurrentposition()+a);
                    System.out.print("                Landed on Tile-"+player.getCurrentposition());
                    System.out.println();

                    //Shaking the tiles: 
                    System.out.print(">>              Trying to shake the Tile-" + player.getCurrentposition());
                    System.out.println();
                    Layout[player.getCurrentposition()-1].shake(player);
                    try{
                        int temps= (Layout[player.getCurrentposition()-1].getPosincordic() + player.getCurrentposition());
                        boolean ss= temps>tiles;
                        Layout[player.getCurrentposition()-1].throwException(player, temps, ss);
                    }
                    catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                    finally{
                        player.setMoves(player.getMoves()+1);
                        System.out.println();
                    }
                }
                else{
                    if(player.getCurrentposition()+a==tiles){
                        try{
                            player.setCurrentposition(player.getCurrentposition() + a);
                            System.out.println("                Landed on Tile-"+player.getCurrentposition());
                            throw new GameWinnerException(player);
                        }
                        catch(GameWinnerException e){
                            System.out.println(e.getMessage());
                            e.printInfo(player, player.getMoves());
                        }
                        finally{
//                            System.out.println("JI");
                            throw new GameWinnerException(player);
//                            System.exit(0);
                        }
                    }
                }
            }
            else{
                if(a==6){
                    initial= true;
                    System.out.println(rollindicator + ". You are out of the cage! You get a free roll.");
                }
                else System.out.println(rollindicator + ", Oops you need 6 to start.");
            }
        }
        player.setMoves(player.getMoves()-1);
        try{
            System.out.println("                Landed on Tile-"+player.getCurrentposition());
            throw new GameWinnerException(player);
        }
        catch(GameWinnerException e){
            System.out.println(e.getMessage());
            e.printInfo(player, player.getMoves());
        }
        finally{
            System.out.println("true");
            System.exit(0);
        }
    }


}

class Dice{
    private final int faces;

    Dice(){
        faces= 6;
    }

    int roll(){
        return 1 + new Random().nextInt(faces);
    }

}

class Player implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 2L;
    private final String name;
    private int currentposition;
    private int moves;
    private HashMap<String, Integer> listofbites;

    Player(String a){
        name= a;
        currentposition= 1;
        listofbites= new HashMap<>();
        listofbites.put("Snake", 0);
        listofbites.put("Vulture", 0);
        listofbites.put("Cricket", 0);
        listofbites.put("Trampoline", 0);
        moves= 1;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }
    public HashMap<String, Integer> getListofbites() {
        return listofbites;
    }

    int getCurrentposition() {
        return currentposition;
    }

    int getMoves() {
        return moves;
    }

    void setCurrentposition(int currentposition) {
        this.currentposition = currentposition;
    }

    String getName() {
        return name;
    }
}

abstract  class Tiles implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final int posincordic;

    Tiles(int j, int k){
        posincordic=  j*(1 + new Random().nextInt(k));
    }

    public int getPosincordic() {
        return posincordic;
    }

    abstract void shake(Player player);
    abstract void throwException(Player player, int temps, boolean ss) throws SnakeBiteException, TrampolineException, VultureBiteException, CricketBiteException, NoWaterException;

}

class Snake extends Tiles{

    Snake(int c){
        super(-1, c);
    }

    @Override
    void shake(Player player) {
        System.out.println(">>              Hiss..! I am a Snake, you go back " + -1*getPosincordic() + " tiles.");
    }

    @Override
    void throwException(Player player, int a, boolean b) throws SnakeBiteException {
        throw new SnakeBiteException(player, a, b);
    }

}

class Vulture extends Tiles{

    Vulture(int c){
        super(-1, c);
    }

    @Override
    void shake(Player player){
        System.out.println(">>              Yapping..! I am a Vulture, you go back " + -1*getPosincordic() + " tiles.");
    }

    @Override
    void throwException(Player player, int a, boolean b) throws VultureBiteException {
        throw new VultureBiteException(player, a, b);
    }

}

class Cricket extends Tiles{

    Cricket(int c){
        super(-1, c);
    }

    @Override
    void shake(Player player){
        System.out.println(">>              Chirp..! I am a Vulture, you go back " + -1*getPosincordic() + " tiles.");
    }

    @Override
    void throwException(Player player, int a, boolean b) throws CricketBiteException {
        throw new CricketBiteException(player, a, b);
    }


}

class Trampoline extends Tiles{

    Trampoline(int c){
        super(1, c);
    }

    @Override
    void shake(Player player){
        System.out.println(">>              PingPong..! I am a Trampoline, you go advance " + getPosincordic() + " tiles.");
    }

    @Override
    void throwException(Player player, int a, boolean b) throws TrampolineException {
        throw new TrampolineException(player, a, b);
    }

}

class White extends Tiles{

    White(int c){
        super(0, c);
    }

    @Override
    void shake(Player player){
        System.out.println(">>              I am a white tile!");
    }

    @Override
    void throwException(Player player, int a, boolean b) throws NoWaterException {
        throw new NoWaterException();
    }

}

class NoWaterException extends Exception{

    private static final long serialVersionUID = 1L;

    public NoWaterException() {
        super("==================White Tile==================");
    }

}

class SnakeBiteException extends Exception{

    private static final long serialVersionUID = 1L;

    public SnakeBiteException(Player player, int a, boolean b) {
        super("==================SnakeBiteExceptionThrown==================");
        if(!b){
            if(a<0) a=1;
            player.setCurrentposition(a);
            System.out.println(player.getName()+" landed at " + player.getCurrentposition());
            player.getListofbites().put("Snake", player.getListofbites().get("Snake")+1);
        }
    }

}


class CricketBiteException extends Exception{

    private static final long serialVersionUID = 1L;

    public CricketBiteException(Player player, int a, boolean b) {
        super("==================CricketBiteException==================");
        if(!b){
            if(a<0) a=1;
            player.setCurrentposition(a);
            System.out.println(player.getName()+" landed at " + player.getCurrentposition());
            player.getListofbites().put("Cricket", player.getListofbites().get("Cricket")+1);
        }
    }

}


class VultureBiteException extends Exception{

    private static final long serialVersionUID = 1L;

    public VultureBiteException(Player player, int a, boolean b) {
        super("==================VultureBiteException==================");
        if(!b){
            if(a<0) a=1;
            player.setCurrentposition(a);
            System.out.println(player.getName()+" landed at " + player.getCurrentposition());
            player.getListofbites().put("Vulture", player.getListofbites().get("Vulture")+1);
        }
    }

}


class TrampolineException extends Exception{

    private static final long serialVersionUID = 1L;

    public TrampolineException(Player player, int a, boolean b) {
        super("==================TrampolineException==================");
        if(!b){
            if(a<0) a=1;
            player.setCurrentposition(a);
            System.out.println(player.getName()+" landed at " + player.getCurrentposition());
            player.getListofbites().put("Trampoline", player.getListofbites().get("Trampoline")+1);
        }
    }

}


class GameWinnerException extends Exception{

    private static final long serialVersionUID = 1L;

    public GameWinnerException(Player player) {
        super("==================GameWinnerException==================");
    }

    void printInfo(Player player, int a){
        System.out.println(">>              " + player.getName() + " wins the race in " + a + " rolls!");
        System.out.println(">>              " + "Total Snake bites: " +player.getListofbites().get("Snake"));
        System.out.println(">>              " + "Total Vulture bites: " +player.getListofbites().get("Vulture"));
        System.out.println(">>              " + "Total Cricket bites: " +player.getListofbites().get("Cricket"));
        System.out.println(">>              " + "Total Trampoline: " +player.getListofbites().get("Trampoline"));
    }

}

class GameState implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 3L;
    private final Tiles LayoutSave[];
    private final Player playertobesave;
    GameState(Player b, Tiles[] lay){
        LayoutSave= lay;
        playertobesave= b;
    }

    /**
     * @return the layoutSave
     */
    public Tiles[] getLayoutSave() {
        return LayoutSave;
    }

    /**
     * @return the playertobesave
     */
    public Player getPlayertobesave() {
        return playertobesave;
    }

    @Override
    public String toString() {
        return ("Player Name: " + playertobesave.getName() + ". \n The number of tiles are: " + LayoutSave.length);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof  GameState){
            GameState o= (GameState) obj;
            if(o.getPlayertobesave().getName().equals(this.getPlayertobesave().getName()) && (this.getLayoutSave().equals(o.getLayoutSave()))){
                return true;
            }
            return false;
        }
        return false;
    }
}