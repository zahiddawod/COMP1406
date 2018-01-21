/**
 * Name: Zahid Dawod
 * Student #: #########
 * Assignment: 4
 *
 */

public class GroceryBag implements Carryable {
    public static final float MAX_WEIGHT = 5;  // max weight allowed (kg)
    public static final int MAX_ITEMS = 25;  // max # items allowed

    private GroceryItem[] items;      // actual GroceryItems in bag
    private int numItems;   // # of GroceryItems in bag
    private float weight;     // current weight of bag

    public GroceryBag() {
        items = new GroceryItem[MAX_ITEMS];
        numItems = 0;
        weight = 0;
    }

    public GroceryItem[] getItems() {
        return items;
    }
    public int getNumItems() {
        return numItems;
    }
    public float getWeight() {
        return weight;
    }

    public String toString() {
        if (weight == 0)
            return "An empty grocery bag";
        return ("A " + weight + "kg grocery bag with " + numItems + " items");
    }

    public boolean canHold(GroceryItem g) {
        return (((weight + g.getWeight()) <= MAX_WEIGHT) && (numItems <= MAX_ITEMS));
    }

    public void addItem(GroceryItem g) {
        if (canHold(g)) {
            items[numItems++] = g;
            weight += g.getWeight();
        }
    }

    public void removeItem(GroceryItem item) {
        for (int i = 0; i < numItems; i++) {
            if (items[i] == item) {
                weight -= items[i].getWeight();
                items[i] = items[numItems - 1];
                numItems -= 1;
                return;
            }
        }
    }

    // Finds and returns the heaviest item in the shopping cart
    public GroceryItem heaviestItem() {
        if (numItems == 0)
            return null;
        GroceryItem heaviest = items[0];
        for (int i = 0; i < numItems; i++) {
            if (items[i].getWeight() > heaviest.getWeight()) {
                heaviest = items[i];
            }
        }
        return heaviest;
    }

    // Determines whether or not the given item in the shopping cart
    public boolean has(GroceryItem item) {
        for (int i = 0; i < numItems; i++) {
            if (items[i] == item) {
                return true;
            }
        }
        return false;
    }

    // Removes all perishables from the bag and return an array of them
    public PerishableItem[] unpackPerishables() {
        int perishableCount = 0;
        for (int i = 0; i < numItems; i++) {
            if (items[i] instanceof PerishableItem) {
                perishableCount++;
            }
        }
        PerishableItem[] perishables = new PerishableItem[perishableCount];
        perishableCount = 0;
        for (int i = 0; i < numItems; i++) {
            if (items[i] instanceof PerishableItem) {
                perishables[perishableCount++] = (PerishableItem) items[i];
                removeItem(items[i]);
                i--;
            }
        }
        return perishables;
    }

    public String getContents() { // Get Contents method returns line-by-line inventory of items
        String itemList = "";
        for (int i = 0; i < numItems; i++) { // For every item in the bag
            if (i == (numItems - 1))
                return itemList + String.format("%3s%s", "", items[i]); // Fixes extra new line at the end of every GROCERY BAG display
            itemList += String.format("%3s%s", "", items[i] + "\n"); // Append the itemList String with the item[i] (toString) and a new line
        }
        return itemList; // Return the list of items in the bag
    }

    public String getDescription() {
        return "GROCERY BAG (" + weight + " kg)";
    } // Returns description of bag

    public float getPrice() {
        float totalPrice = 0f;
        for (int i = 0; i < numItems; i++) {
            totalPrice += items[i].getPrice(); // Adds every item's price to totalPrice variable
        }
        return totalPrice; // Returns total price of all items in the bag
    }
}
