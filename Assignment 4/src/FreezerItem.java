public class FreezerItem extends PerishableItem {
    public FreezerItem() { super(); } // Inherits Perishable's zero-constructor which inherits GroceryItem's zero-constructor

    public FreezerItem(String n, float p, float w) { super(n, p, w); } // Inherits Perishable's 3 arg-constructor which inherits GroceryItem's 3 arg-constructor

    public String toString() {
        return super.toString() + " [keep frozen]";
    } // Inherits Perishable's toString method and appends " [keep frozen]" to it
}