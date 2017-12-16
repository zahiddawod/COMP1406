public abstract class PerishableItem extends GroceryItem {
    public PerishableItem() { super(); } // Inherits GroceryItem's zero-constructor

    public PerishableItem(String n, float p, float w) { super(n, p, w); } // Inherits GroceryItem's 3 arg-constructor

    public String toString() {
        return super.toString() + " (perishable)";
    } // Inherits GroceryItem's toString method and appends " (perishable)" to it
}