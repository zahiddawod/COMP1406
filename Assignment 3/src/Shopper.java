/**
 * Name: Zahid Dawod
 * Student #: 101041370
 * Assignment: 3
 * Question: 3
 *
 */

public class Shopper {
    private static final int MAX_CART_ITEMS = 100; // Maximum number of items allowed in a cart.

    private GroceryItem[] cart = new GroceryItem[MAX_CART_ITEMS];
    private int numItems;

    // Get methods for cart and numItems attributes.
    public GroceryItem[] getCart() { return cart; }
    public int getNumItems() { return numItems; }

    public Shopper() { // Zero-constructor.
        numItems = 0;
    }

    public String toString() { // toString method.
        return "Shopper with shopping cart containing " + this.numItems + " items";
    }

    public void addItem(GroceryItem item) { // Add items to cart method.
        if (this.numItems <= MAX_CART_ITEMS) { // Checks if current number of items is less than maximum allowed.
            this.cart[numItems] = item; // Adds item to cart.
            this.numItems++; // Increments number of items.
        }
    }

    public void removeItem(GroceryItem item) { // Remove items from cart method.
        for (int i = 0; i < this.numItems; i++) { // Loops through all the items in the cart.
            if (this.cart[i] == item) { // Checks if item that wants to be removed exists in cart.
                for (int j = i; j < (this.numItems - 1); j++) { // Loops from the position that the specified item will be removed to second last item.
                    this.cart[j] = this.cart[j + 1]; // Shifts everything in the array after the item that will be removed to the left by 1 element.
                }
                this.cart[this.numItems] = null; // Last current item will become null.
                this.numItems --; // Number of item count will decrease.
                break; // Exits loop so that it doesn't happen for the next same item.
            }
        }
    }

    public GroceryBag[] packBags() {
        float totalWeightofCart = 0f; // Variable to store total weight of cart (excluding any item that is greater than 5kg (max weight for a bag)).
        int numOfBags = 0, numOfItemsOverWeightLimit = 0; // Variable 'numOfBags' to store number of bags needed.

        //------------------------------ Figure out number of bags that is needed to pack all items ------------------------------//
        for (int i = 0; i < this.numItems; i++) {
            if (this.cart[i].getWeight() <= 5) { // Checks if the weight of the item is less than 5kg ..
                totalWeightofCart += this.cart[i].getWeight(); // Increase the total weight of cart if it is.
            } else {
                numOfItemsOverWeightLimit += 1; // Increase numberOfItemsOverWeightLimit integer (for per bag (5kg)) by 1 if it isn't.
            }
        }
        if (totalWeightofCart == (int) totalWeightofCart) // Checks if totalWeightofCart is rational number (no decimals).
            numOfBags = (int) (totalWeightofCart / 5); // Divide by 5 (kg).
        else
            numOfBags = (int) (totalWeightofCart / 5) + 1; // Divide by 5 (kg) and + 1 (because you cannot have for example 5.65 bags)
        GroceryBag[] bag = new GroceryBag[numOfBags]; // Create variable 'bag' to store array of packed bags.
        for (int i = 0; i < numOfBags; i++) {
            bag[i] = new GroceryBag(); // Initialize all (numOfBags) bags.
        }

        //------------------------------ PACKS AS MUCH ITEMS AS IT CAN INTO EACH BAG ------------------------------//
        for (int cBag = 0; cBag < numOfBags; cBag++) { // for each bag
            for (int cItem = 0; cItem < this.numItems; cItem++) { // for each item
                if (this.cart[cItem].getWeight() <= bag[cBag].getMaxWeight()) { // if item by itself is <= 5kg (max a bag can hold)
                    if ((bag[cBag].getWeight() + this.cart[cItem].getWeight()) <= bag[cBag].getMaxWeight()) { // if current item weight + current weight of current bag <= 5kg
                        bag[cBag].addItem(this.cart[cItem]); // Add item to current bag.
                        this.removeItem(this.cart[cItem]); // Remove item from cart.
                        //System.out.println(bag[cBag].getWeight()); //--------------- FOR-DEBUGGING ---------------//
                        cItem --; // (cItem - 1) so it starts at the position of the item that was just removed.
                    }
                }
            }
        }
        return bag; // Returns the array of packed grocery bags.
    }
}
