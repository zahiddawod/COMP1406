/**
 * Name: Zahid Dawod
 * Student #: 101041370
 * Assignment: 3
 * Question: 1
 *
 */

public class GroceryItem {
    private String name;
    private float price;
    private float weight;
    private boolean perishable = false; // true; item needs to be refrigerated/frozen

    public String getName() { return name; }
    public float getPrice() { return price; }
    public float getWeight() { return weight; }
    public boolean isPerishable() { return perishable; }

    public GroceryItem() { // Zero-parameter constructor
        name = "UNKNOWN";
        price = 0f;
        weight = 0f;
    }

    public GroceryItem(String n, float p, float w) { // 3-parameter constructor
        name = n;
        price = p;
        weight = w;
    }

    public GroceryItem(String n, float p, float w, boolean pr) { // 4-parameter constructor
        name = n;
        price = p;
        weight = w;
        perishable = pr;
    }

    public String toString() { // toString method
        return name + " weighing " + weight + "kg with price $" + price;
    }
}
