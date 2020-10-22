
/**
 * This class is part of the "World of Zuul" application.
 * 
 * The player class establishes a player for the game.  The player has 
 * a name and a current room location, which is set to frontHall.  
 * The frontHall room is the default starting position for the game.
 *
 * @ author Karen Stagg
 * @ version October 26, 2020)
 */
public class Player
{
    private String name;
    private Room currentRoom;
    /**
     * Constructor for objects of class Player
     * @param name is the name of the player 
     */
    public Player(String name, Room startingRoom)
    {
        this.name = name;
        //Set the current room of the player: The game's default is frontHall.
        currentRoom = startingRoom;
    }

    /**
     * Returns the name of the player
     *
     * @return  the name of the player
     */
    public String getName()
    {
       return name; 
    }
    
    /**
     * Returns the current room the player is located
     *
     * @return the current room where the player is located
     */
    public Room getCurrentRoom()
    {
       return currentRoom; 
    }
    
    /**
     * Sets the current room the player is located
     *
     * @param nextRoom is the new current room that the player is located
     */
    public void setCurrentRoom(Room nextRoom)
    {
       currentRoom = nextRoom; 
    }
}
