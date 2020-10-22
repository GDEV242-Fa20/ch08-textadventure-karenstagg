
/**
 * This class is a part of the "World Of Zuul" application.
 * 
 * An item is represented by a description and a weight.
 *
 * @ author Karen Stagg
 * @ version October 26, 2020
 */
public class Item
{
    private String description;
    private int weight;

    /**
     * Constructor for objects of class Item
     * 
     * @param description is a description of the item.
     * @param weight is the integer weight of the item,
     */
     public Item(String description, int weight)
    {
        this.description = description;
        this.weight = weight;
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
    public int weight()
    {
        return weight;
    }
}    
    
 

