import java.util.HashSet;
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
    //keep move counts, base on the goRoom() method in games (back() doesn't count)
    private int movesMade = 0;
    //keep points count to determine if game has been won by => winningPoints value
    private int pointsCollected = 0;
    //keep a new collection set for the collected items of the player
    private HashSet<Item> collection;
    /**
     * Constructor for objects of class Player
     * 
     * @param name is the name of the player 
     * @param startingRoom is the room to start the game at
     */
    public Player(String name, Room startingRoom)
    {
        this.name = name;
        //Set the current room of the player: The game's default is frontHall.
        currentRoom = startingRoom;
        collection = new HashSet<Item>();
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
    
    /**
     * Increments the moves made by the player
     *
     * @param nextRoom is the new current room that the player is located
     */
    public void addToMoves()
    {
       movesMade ++; 
    }
    
    /**
     * Returns the count of the moves made by the player
     *
     * @return returns the current total of moves made by the player
     */
    public int getMovesMade()
    {
       return movesMade; 
    }
    
    /**
     * Returns the count of the points accumulated by the player
     *
     * @return returns the current total points of the player
     */
    public int getPointsCollected()
    {
       return pointsCollected; 
    }
    
    /** 
     * Performs the collection of an item from the room
     *
     * @param itemName the name of the item the player has collected.
     * @return the item object that was collected.
     */
    public Item collectItem(String itemName)
    {
        boolean itemInRoom = true;
        //get the item object from the room
        Item collectedItem = currentRoom.getItem(itemName);
        if (collectedItem == null)
        {
            itemInRoom = false;
            System.out.println("Sorry that item is not in the room");
        }
        else
        {
            //remove the validated item from the room
            currentRoom.removeItem(collectedItem);
            //add the item to the collection of what the player has collected 
            collection.add(collectedItem);
            //add the points of the collected item to the player's total points
            pointsCollected += collectedItem.getWeight();
            
            //ystem.out.println("You have collected items: " + pointsCollected
            // go to game where collectItem ws called and call the new print summary method
        }
        return collectedItem;
    } 
    
    /**
     * Returns a string describing items and points collected by the player
     *
     * @return a string listing the items and points a player has collected
     */
    public String getCollectedString()
    {
       String returnString = "** COLLECTION SUMMARY **\n";
       for (Item item : collection)
       {
           returnString+= " " + item.getName() + " -  " + item.getDescription() + " for " + 
                            item.getWeight() + " points.\n";
       
       }
       return returnString;
    }
    
    /**
     * Returns a  total count of the points collected by the player
     *
     * @return a count of the total points a player has collected
     */
    public int getCollectedCount()
    {
       int totalCollected = 0;
       for (Item item : collection)
       {
           totalCollected += item.getWeight();       
       }
       return totalCollected;
    } 
}    
