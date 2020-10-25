
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 *  
 *  My version of "World of Zuul" is "Get Me to the Church Ontime":
 *  a story of a bride going room-to-room and trying to collect all the necessary
 *  "something old" "something new" "something borrowed" and "something blue"
 *  items which earn points in order to ultimately get married at the church.  
 *  She will need to collect a required number of points withn a maximum number 
 *  of unique moves (accumulated only from the goRoom() method),
 *  in order to "win" the game and get married.
 * 
 * @author  Karen Stagg
 * @version October 26, 2020
 */

public class Game 
{
    /**
     * The main method.
     */
    public static void main(String args[])
    {
        Game game = new Game();
        game.play();
    }    
    
    private Parser parser;
    private Room currentRoom;
    //add a field for use by the back command
    private Room previousRoom;
    private Player player;
    //set the maximum moves allowed to win the game
    private int maxMoves = 10;
    //set the amount of points needed to win the game
    private int winningPoints = 10;
    
    Room frontHall, dadsMancave, sistersBedroom, myBedroom, upstairsHall, 
             attic, outside, grandmasNursingHome, friendsApt, aptLobby,
             downtown, church, dressStore, hairSalon, nailSalon;
             
    Item borrowedWineGlasses, oldCoin, borrowedNecklace, blueFlower, 
             oldDiamondEarrings, blueHankie, oldDress, oldPin, bluePurse, 
             borrowedWrap, newBracelet, borrowedBike, newHusband, newVail, 
             blueGarter, newHairdo, newManicure, smile;
             
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        gameSetup();
        parser = new Parser();
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished)
        {
            Command command = parser.getCommand();
            finished = processCommand(command);
            if (gameWon())
            {
                System.out.println("\nCongratulations! You win the prize, a new husband!");
                finished = true;
            }
            if (gameLost())
            {
                System.out.println("\nSo Sorry,you lose. No wedding for you...");
                finished = true;
            }
        }    
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to World of Zuul adventure game,");
        System.out.println("featuring GET ME TO THE CHURCH ON TIME!, my cool new game.");
        System.out.println("You'll collect necessary wedding items and earn points.");
        System.out.println("Try and win the game by collecting enough points and");
        System.out.println("making it to the church within a pre-determined amount of steps."); 
        System.out.println("To collect items, type collect followed by the one word item name.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                 System.out.println("I don't know what you mean...");
                 break;

            case HELP:
                 printHelp();
                 break;

            case GO:
                 goRoom(command);
                 break;
                
            case LOOK:
                 look();
                 break;
                
            case EAT:
                 eat();
                 break;   
                
            case BACK:
                 back(command);
                 break;
                 
            case COLLECT:
                 collect(command);
                 break;
                 
            case COLLECTED:
                 collected();
                 break;
                
            case QUIT:
                 wantToQuit = quit(command);
                 break;
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You are ");
        System.out.println("wandering around.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * @param command is the command given by the user that includes 
     * which direction to go.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        //This code is when there is not a player
        //Room nextRoom = currentRoom.getExit(direction);
        //Add new code to handle a player
        Room nextRoom = player.getCurrentRoom().getExit(direction);
        
        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else 
        {
            //maintain the value of the current room by storing it in previous room
            previousRoom = currentRoom;
            currentRoom = nextRoom;
            //This code is when there is not a player.
            //System.out.println(currentRoom.getLongDescription());
            //Add new code to handle a player
            player.setCurrentRoom(nextRoom);
            player.addToMoves();
            System.out.println(player.getCurrentRoom().getLongDescription());
        }
    }
    
    /*
     * Back takes you back to the room you were just in
     * 
     * @param command is the command entered by the user
     */
    private void back(Command command) 
    {
        if(command.hasSecondWord()) 
        {
            // if there is a second word, we don't know where to go...
            System.out.println("Go back where?");
            return;
        }
        
        //Check to see if there is a previous room stored 
        if(previousRoom == null) 
        {
            // There is no previous room to go back to 
            System.out.println("Sorry, there is no previous room to go back to");
            return;
        }
        else
        {
            //make the current room equal to the stored previous room value
            currentRoom = previousRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }
    
    /**
     * The collect command takes an item from the room.
     *  
     *  @param command is the command entered by the player.
     */
    private void collect(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to take...
            System.out.println("Take what?");
            return;
        }
        
        String itemName = command.getSecondWord();
        Item collectedItem = player.collectItem(itemName);
        
        if (collectedItem  == null)
        {
            System.out.println("Sorry there is no item by that name");
        }
        else
        {
            System.out.println(collectedItem.getDescription() + " has been collected\n");
            collected();
        }    
    }
    
    
    /**
     * The look command displays the long description for the current room.
     *     
     */
    private void look() 
    {
        System.out.println(currentRoom.getLongDescription());
    }
    
    /**
     * The eat command displays an eaten message.
     *     
     */
    private void eat() 
    {
        System.out.println("You have eaten now and you re not hungry anymore.");
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @param command is the command entered by the player.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /** 
     * Prints out a string of all the items collected by the player.
     * 
     */
    private void collected() 
    {
        System.out.print(player.getCollectedString());
        System.out.println("TOTAL POINTS COLLECTED =: " + player.getCollectedCount());
    }
    
    /** 
     * Returns whether the game has been won or not.
     * 
     * @return returns true if the game was won, otherwise returns false.
     */
    private boolean gameWon() 
    {
        boolean wonGame = false;
        if (player.getPointsCollected() >= winningPoints && 
                       player.getMovesMade() <= maxMoves &&
                       player.getCurrentRoom() == church)
        { 
            wonGame = true;
        } 
        return wonGame;
    }
    
     /** 
     * Returns whether the game has been lost or not.
     * 
     * @return true if the game was lost, otherwise returns false.
     */
    private boolean gameLost() 
    {
        boolean lostGame = false;
        if (player.getMovesMade() > maxMoves)
        { 
            lostGame = true;
        } 
        return lostGame;
    }
    
    /**
     * Create all the rooms, link room exits together, add room items, and
     * establish default values for player and starting room.
     */
    private void gameSetup()
    {
        // create the rooms
        frontHall = new Room("in the front hall of my house");
        dadsMancave = new Room("in dad's man cave in the basement");
        sistersBedroom = new Room("in my sister's bedroom");
        myBedroom = new Room("in my bedroom");
        upstairsHall = new Room("in the upstairs hallway");
        attic = new Room("in the attic of my house");
        outside = new Room("outside of my house");
        grandmasNursingHome = new Room("in my grandma's nursing home");
        friendsApt = new Room("in my friend's apartment");
        aptLobby = new Room("in the lobby of my friend's apartment");
        downtown = new Room("in the downtown of my neighborhood");
        church = new Room("in the church of where my wedding is");
        dressStore = new Room("in the store where my wedding gown is");
        hairSalon = new Room("in the salon where my hair is being done");
        nailSalon = new Room("in the nail salon getting a maincure");
      
        // create the items
        borrowedWineGlasses = new Item("glasses", "borrowed crystal wine glasses", 2);
        oldCoin = new Item("coin","an old European coin", 1);
        borrowedNecklace = new Item("necklace","a borrowed pearl necklace", 2);
        blueFlower = new Item("flower","a blue forget-me-not from the garden", 1);
        oldDiamondEarrings = new Item("earrings", "pair of old diamond earrings", 2);
        blueHankie = new Item("hankie","a blue hankie", 1);
        oldDress = new Item("dress","my mom's wedding dress", 3);
        oldPin = new Item( "pin", "an old pin from grandma's jewelry box", 1);
        bluePurse = new Item("purse","a little blue change purse", 1);
        borrowedWrap = new Item("wrap","a borrowed wrap to keep warm", 1);
        newBracelet = new Item("bracelet", "a wedding gift of a new pearl bracelet", 2);
        borrowedBike = new Item("bike", "a borrowed bike to get around quickly", 1);
        newHusband = new Item ("fiance", "my fiance to wed on this special day", 5);
        newVail = new Item ("vail", "a new vail to wear", 1);
        blueGarter = new Item ("garter", "a blue garter to throw later", 1); 
        newHairdo = new Item ("hairdo", "the perfect hairdo", 3);
        newManicure = new Item("manicure", "nicely groomed hands for the occasion", 1);
        smile = new Item("smile","a smile for this happy day!", 2);
                
        // initialise room exits
        frontHall.setExit("north", upstairsHall);
        frontHall.setExit("south", dadsMancave);
        frontHall.setExit("east", outside);
                
        dadsMancave.setExit("north", frontHall);
        
        sistersBedroom.setExit("east", upstairsHall);
        
        attic.setExit("south", upstairsHall);
        
        upstairsHall.setExit("north", attic);
        upstairsHall.setExit("south", frontHall);
        upstairsHall.setExit("east", myBedroom);
        upstairsHall.setExit("west", sistersBedroom);
        
        myBedroom.setExit("west", upstairsHall);
        
        grandmasNursingHome.setExit("south", outside);
        
        outside.setExit("north", grandmasNursingHome);
        outside.setExit("south", friendsApt);
        outside.setExit("east", church);
        outside.setExit("west", frontHall);
        
        friendsApt.setExit("north", outside);
        friendsApt.setExit("south", aptLobby);
        
        aptLobby.setExit("north", friendsApt);
        aptLobby.setExit("east", downtown);
        
        church.setExit("south", downtown);
        church.setExit("west", outside);
        
        downtown.setExit("north", church);
        downtown.setExit("south", dressStore);
        downtown.setExit("east", hairSalon);
        downtown.setExit("west", aptLobby);
        
        dressStore.setExit("north", downtown);
        
        hairSalon.setExit("south", nailSalon);
        hairSalon.setExit("west", downtown);
        
        nailSalon.setExit("north", hairSalon);
        
        // initialise room items
        dadsMancave.addItem(borrowedWineGlasses);
        dadsMancave.addItem(oldCoin);
        
        sistersBedroom.addItem(borrowedNecklace);
        sistersBedroom.addItem(blueFlower);
        
        myBedroom.addItem(oldDiamondEarrings);
        myBedroom.addItem(blueHankie);
        
        attic.addItem(oldDress);
        
        grandmasNursingHome.addItem(oldPin);
        grandmasNursingHome.addItem(bluePurse);
        
        friendsApt.addItem(borrowedWrap);
        friendsApt.addItem(newBracelet);
        
        aptLobby.addItem(borrowedBike);
        
        church.addItem(newHusband);
        
        dressStore.addItem(newVail);
        dressStore.addItem(blueGarter);
        
        hairSalon.addItem(newHairdo);
        
        nailSalon.addItem(newManicure);
        
        frontHall.addItem(smile);
        
        //Set the game defaults *******************
        //Start game at frontHall of house
        currentRoom = frontHall;
        //Add a player and set the player's current room to default of frontHall
        player = new Player("Karen", frontHall);
    }
}
