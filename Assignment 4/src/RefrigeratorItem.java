public class RefrigeratorItem extends PerishableItem {
    public RefrigeratorItem() { super(); } // Inherits Perishable's zero-constructor which inherits Grocery Item's zero-constructor

    public RefrigeratorItem(String n, float p, float w) { super(n, p, w); } // Inherits Perishable's 3 arg-constructor which inherits GroceryItem's 3 arg-constructor

    public String toString() {
        return super.toString() + " [keep refrigerated]";
    } // Inherits Perishable's toString method and appends " [keep refrigerated]" to it
}