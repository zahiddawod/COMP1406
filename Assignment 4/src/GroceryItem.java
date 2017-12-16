/**
 * Name: Zahid Dawod
 * Student #: 101041370
 * Assignment: 4
 *
 */

public class GroceryItem implements Carryable {
    private String      name;
    private float       price;
    private float       weight;

    public GroceryItem() {
        name = "?";
        price = 0;
        weight = 0;
    }
    public GroceryItem(String n, float p, float w) {
        name = n;
        price = p;
        weight = w;
    }

    public String getName() { return name; }
    //public float getPrice() { return price; }
    public float getWeight() { return weight; }

    public String toString () {
        return name + " weighing " + weight + "kg with price $" + price;
    }

    public String getContents() {
        return "";
    }
    public String getDescription() {
        return this.name;
    }
    public float getPrice() {
        return this.price;
    }
}