import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Karen Stagg
 * @version October 26, 2020
 */

public class Room 
{
    private String description;
    // stores exits of this room.
    private HashMap<String, Room> exits;    
    //stores items in this room.
    private HashSet<Item> items;
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * Instantiate the collection set of exits and items.
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<String, Room>();
        items = new HashSet<Item>();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     *  Puts an item to a room      
     * @param item is the item to add to a room.
     */
    public void addItem(Item item) 
    {
        items.add(item);
    }
    
    /**
     *  Remove an item from a room      
     * @param item is the item to remove from to a room.
     */
    public void removeItem(Item item) 
    {
        items.remove(item);
    }
    
    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room, exits and items in the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     *     Items: item name - description
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "\nYou are " + description + ".\n" + getExitString()
                           + "\n" + getItemsString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }
    
    /**
     * Return a string listing and describing items in the room in the form of:
     * item name - description
     * 
     * @return a string listing of the items in the room.
     */
    private String getItemsString()
    {
        String returnString = "Items:";
        for (Item item : items)
        {
            returnString += " " + item.getName() + " - " + 
                         item.getDescription() + "\n      ";
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
     /**
     * Returns the item object of a collected item if found in the list of 
     * items contained in a room, otherwise it returns null.
     * @param itemName is the name of a collected item
     * @return a Item object containing null or the name of the validated 
     * collected item
     */
    public Item getItem(String itemName) 
    {
        Item foundItem = null;
        for (Item item : items)
        {
            if (item.getName().equals(itemName))
            {
                foundItem = item;
            }
        }   
        return foundItem;
    }
}    

