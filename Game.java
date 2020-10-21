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
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room frontHall, dadsMancave, sistersBedroom, myBedroom, upstairsHall, 
             attic, outside, grandmasNursingHome, friendsApt, aptLobby,
             downtown, church, dressStore, hairSalon, nailSalon;
              
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
        
        // start game at front Hall of the house
        currentRoom = frontHall;  
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
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
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
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
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
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
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
}
