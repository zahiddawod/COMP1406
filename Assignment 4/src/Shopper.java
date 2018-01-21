/**
 * Name: Zahid Dawod
 * Student #: #########
 * Assignment: 4
 *
 */

public class Shopper {
    public static final int   MAX_CART_ITEMS = 100;  // max # items allowed

    private Carryable[]     cart;       // items to be purchased
    private int             numItems;   // #items to be purchased

    private GroceryBag[] pBags; // To store packed bags so that I can access the items in them later in the code.

    public Shopper() {
        cart = new Carryable[MAX_CART_ITEMS];
        numItems = 0;
    }

    public Carryable[] getCart() { return cart; }
    public int getNumItems() { return numItems; }
    public GroceryBag[] getpBags() { return pBags; }

    public String toString() {
        return "Shopper with shopping cart containing " + numItems + " items";
    }

    // Return the total cost of the items in the cart
    public float totalCost() {
        float total = 0;
        for (int i=0; i<numItems; i++) {
            total += cart[i].getPrice();
        }
        return total;
    }

    // Adds Carryable object (bags or items) to the shopper's cart
    public void addItem(Carryable g) {
        if (numItems < MAX_CART_ITEMS)
            cart[numItems++] = g;
    }

    // Removes Carryable object (Item or Bag) from cart
    public void removeItem(Carryable g) {
        for (int i=0; i<numItems; i++) {
            if (cart[i] == g) {
                cart[i] = cart[numItems - 1];
                numItems -= 1;
                return;
            }
        }
    }

    // Go through the shopping cart and pack all packable items into bags
    public void packBags() {
        GroceryBag[] packedBags = new GroceryBag[numItems];
        int   bagCount = 0;

        GroceryBag currentBag = new GroceryBag();
        for (int i=0; i<numItems; i++) {
            GroceryItem item = (GroceryItem) cart[i];
            if (item.getWeight() <= GroceryBag.MAX_WEIGHT) {
                if (!currentBag.canHold(item)) {
                    packedBags[bagCount++] = currentBag;
                    currentBag = new GroceryBag();
                }
                currentBag.addItem(item);
                removeItem(item);
                i--;
            }
        }
        // Check this in case there were no bagged items
        if (currentBag.getWeight() > 0)
            packedBags[bagCount++] = currentBag;

        // Now create a new bag array which is just the right size
        pBags = new GroceryBag[bagCount];
        for (int i=0; i<bagCount; i++) {
            pBags[i] = packedBags[i];
        }

        // Add all grocery bags bag into cart
        for (int i = 0; i < bagCount; i++) {
            addItem(pBags[i]);
        }
    }

    // Display contents of the cart in a specific way depending on what it is (bag or item)
    public void displayCartContents() {
        for (int i = 0; i < numItems; i++) { // Checks all objects in the cart
            if ((cart[i].getContents() != "")) { // If not item
                System.out.println(cart[i].getDescription()); // Display bag description
                System.out.println(cart[i].getContents()); // Display contents of bag
            } else { // Else it must be item
                System.out.println(cart[i].getDescription()); // Display item description
            }
        }
    }

    // Removes perishable items from cart
    public PerishableItem[] removePerishables() {
        // Check how many perishables items there are in total
        int perishableCount = 0;
        for (int i = 0; i < numItems; i++) { // Checks how many perishables in the cart (loose items)
            if (cart[i] instanceof PerishableItem) {
                perishableCount++;
            }
        }
        for (int i = 0; i < pBags.length; i++) { // Checks how many perishables in all the bags in the cart
            for (int j = 0; j < pBags[i].getNumItems(); j++) {
                if (pBags[i].getItems()[j] instanceof PerishableItem)
                    perishableCount++;
            }
        }

        PerishableItem[] perishables = new PerishableItem[perishableCount];
        perishableCount = 0;
        // Assign any perishable item to 'perishables' variable and remove them from cart/packed bags
        for (int i = 0; i < numItems; i++) {
            if (cart[i] instanceof PerishableItem) { // Checks each item if its an instance of PerishableItem
                perishables[perishableCount++] = (PerishableItem) cart[i]; // Adds it to the perishables variable
                removeItem(cart[i]); // Removes it from the cart
                i--; // Checks the same element (Since its different now based on the removeItem function)
            }
        }
        for (int i = 0; i < pBags.length; i++) {
            PerishableItem[] perishablesInBags = pBags[i].unpackPerishables(); // Unpacks perishables from pbags and stores them into a variable
            for (int j = 0; j < perishablesInBags.length; j++) { // Loops through each item in perishablesInBags
                perishables[perishableCount++] = perishablesInBags[j]; // Assigns each item to the perishables variable
            }
        }
        return perishables; // Return the perishables items that were removed
    }

    public float computeFreezerItemCost() {
        float totalPrice = 0f;
        for (int i = 0; i < pBags.length; i++) { // Checks through the packed bags
            for (int j = 0; j < pBags[i].getItems().length; j++) {
                if (pBags[i].getItems()[j] instanceof FreezerItem) // If any item in there is FreezerItem
                    totalPrice += pBags[i].getItems()[j].getPrice(); // Then increase the totalprice by that item's price
            }
        }
        for (int i = 0; i < numItems; i++) { // Goes through each item in the cart
            if (cart[i] instanceof FreezerItem) // Checks if each item is instance of FreezerItem
                totalPrice += cart[i].getPrice(); // Increases totalprice by that item's price
        }
        return totalPrice; // Returns the total price of FreezerItems that is in the cart (including inside each bag)
    }

    public float computeTotalCost() {
        float totalPrice = 0f;
        for (int i = 0; i < numItems; i++) { // For each item in the cart
            totalPrice += cart[i].getPrice(); // Increase the totalprice by that item's price ((total price) if its a bag)
        }
        return totalPrice; // Returns total price of all items in the cart
    }
}
