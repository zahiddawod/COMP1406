public class CellPhone {
    private String model;
    private String manufacturer;
    private int monthsWarranty;
    private float price;

    // This is the zero-parameter constructor
    public CellPhone() {
        model = "UNKNOWN";
        manufacturer = "UNKNOWN";
        monthsWarranty = 0;
        price = 0f;
    }

    // This is the 4-parameter constructor
    public CellPhone(String m, String mf, int mW, float p) {
        model = m;
        manufacturer = mf;
        monthsWarranty = mW;
        price = p;
    }

    // These are the get methods
    public String getModel() { return model; }
    public String getManufacturer() { return manufacturer; }
    public int getMonthsWarranty() { return monthsWarranty; }
    public float getPrice() { return price; }
    // These are the set methods
    public void setModel(String m) { this.model = m; }
    public void setManufacturer(String mf) { this.manufacturer = mf; }
    public void setMonthsWarranty(int mW) { this.monthsWarranty = mW; }
    public void setPrice(float p) { this.price = p; }
}
