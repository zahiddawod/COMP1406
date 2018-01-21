/**
 * Name: Zahid Dawod
 * Student #: #########
 * Assignment: 3
 * Question: 2
 *
 */

public class GroceryBag {
    private static double MAX_WEIGHT = 5; // Max weight that each bag can carry (5 Kg).
    private static int MAX_ITEMS = 25; // Max items that each bag can carry (25 items).

    private GroceryItem[] items = new GroceryItem[MAX_ITEMS];
    private int numItems;
    private float totalWeight;

    public double getMaxWeight() { return MAX_WEIGHT; }
    public int getMaxItems() { return MAX_ITEMS; }
    public GroceryItem[] getItems() { return items; }
    public int getNumItems() { return numItems; }
    public float getWeight() { return totalWeight; }

    public GroceryBag() { // Zero-parameter constructor.
        numItems = 0;
        totalWeight = 0f;
    }

    public String toString() { // toString method
        if (numItems > 0) // Returns information about grocery bag if there are items in it.
            return "A " + totalWeight + "kg grocery bag with " + numItems + " items";
        return "An empty grocery bag";
    }

    public void addItem(GroceryItem item) { // addItem method, adds items into grocery bag.
        if (((this.totalWeight + item.getWeight()) <= MAX_WEIGHT) && ((this.numItems + 1) <= MAX_ITEMS)) { // Checks if weight of bag + weight of item is less than max weight and if adding this item will be less than max items allowed.
            this.items[numItems] = item; // Adds item into grocery bag array.
            this.totalWeight += item.getWeight(); // Increases the weight of the bag.
            this.numItems++; // Increments the number of items there are in the bag.
        }
    }

    public void removeItem(GroceryItem item) { // remove method, removes items from grocery bag.
        for (int i = 0; i < this.numItems; i++) { // Loop through all the items in the bag.
            if (this.items[i] == item) { // Check if the item that wants to be removed exists in the bag.
                for (int j = i; j < (this.numItems - 1); j++) {
                    this.items[j] = this.items[j + 1]; // Shift everything in the array after the position of the item that will be removed to the left.
                }
                this.items[this.numItems] = null; // Set last item to null
                this.numItems--; // Decrease number of items by 1
                this.totalWeight -= item.getWeight(); // Decrease total weight of the bag by the weight of the item that was removed.
            }
        }
    }

    public GroceryItem heaviestItem() { // Method that checks for the heaviest item in the bag.
        if (this.numItems > 0) { // Checks if there are any items in the bag.
            GroceryItem heaviest_Item = this.items[0]; // Current heaviest item is set to the first item in the bag.
            for (int i = 0; i < this.numItems; i++) { // Loops through all the items in the bag.
                if (this.items[i].getWeight() > heaviest_Item.getWeight()) // Checks if the weight of the current item in the bag is greater than the current heaviest ..
                    heaviest_Item = this.items[i]; // Sets the new heaviest item if it is.
            }
            return heaviest_Item; // returns the heaviest item that is in the bag.
        }
        return null; // returns null if no items were found.
    }

    public boolean has(GroceryItem item) { // Method that checks if the item exists in the bag.
        if (this.numItems > 0) { // Checks if there are any items in the bag.
            for (int i = 0; i < this.numItems; i++) { // Loops through all the items in the bag.
                if (this.items[i].getName() == item.getName()) // Compares each item to the item that is being checked.
                    return true; // returns true if the item exists in the bag.
            }
        }
        return false; // returns false if no items were found.
    }

    public GroceryItem[] unpackPerishables() {
        int numOfPerishableItemsInBag = 0; // Variable to store number of items in the bag that are perishable
        for (int i = 0; i < this.numItems; i++) { // Loops through all items in the bag.
            if (this.items[i].isPerishable()) { // Checks if current item begin checked is perishable.
                numOfPerishableItemsInBag ++; // Increases value of number of items in bag that are perishable.
            }
        }
        GroceryItem[] perishableItems = new GroceryItem[numOfPerishableItemsInBag]; // Create array of items to store all perishable items from this bag.
        int counter = 0; // Counter to keep track how many perishable items have been stored into the array.
        for (int i = 0; i < this.numItems; i++) { // Loops through all items in the bag.
            if (this.items[i].isPerishable()) { // Checks if current item is perishable.
                perishableItems[counter] = this.items[i]; // Stores item into array that was created earlier.
                counter ++; // increases counter each time a perishable item was stored.
            }
        }
        for (int i = 0; i < this.numItems; i++) { // Remove all perishable items from each bag
            if (this.items[i].isPerishable()) { // Checks if item is perishable
                this.removeItem(this.items[i]); // Removes item
                i --; // Starts from the position that the recent perishable item was removed.
            }
        }
        return perishableItems; // Return the array of perishable items in this bag.
    }
}
