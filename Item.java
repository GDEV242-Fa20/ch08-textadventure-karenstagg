
/**
 * This class is a part of the "World Of Zuul" application.
 * 
 * An item is represented by an item name, description and a weight.
 *
 * @ author Karen Stagg
 * @ version October 26, 2020
 */
public class Item
{
    private String itemName;
    private String description;
    private int weight;

    /**
     * Constructor for objects of class Item
     * 
     * @param itemName is a short name for the item.
     * @param description is a description of the item.
     * @param weight is the integer weight of the item,
     */
     public Item(String itemName, String description, int weight)
    {
        this.itemName = itemName;
        this.description = description;
        this.weight = weight;
    }
    
    /**
     * Return the name of the item
     *
     * @return the short name of the item
     */
    public String getName()
    {
        return itemName;
    }
    
    /**
     * Return a description of the item
     *
     * @return the description of the item
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Return the integer value of the weight of the item
     *
     * @return the weight of the item
     */
    public int getWeight()
    {
        return weight;
    }
}    
    
 

