/*
 * NAME: Zahid Dawod
 * Student ID: #########
 * Assignment: 2
 * Question: 1
 *
 */

public class CellPhoneTestProgram {
    public static void main(String[] args) {
        CellPhone iPhone = new CellPhone("iPhone 6 Plus", "Apple", 12, 915f);
        CellPhone galaxy = new CellPhone("Galaxy S7", "Samsung", 18, 900f);
        CellPhone priv = new CellPhone("PRIV", "BlackBerry", 24, 890f);

        System.out.println("Here is the Apple phone information:");
        System.out.println(iPhone.getModel());
        System.out.println(iPhone.getManufacturer());
        System.out.println(iPhone.getMonthsWarranty());
        System.out.println(iPhone.getPrice());

        System.out.println("\nHere is the Samsung phone information:");
        System.out.println(galaxy.getModel());
        System.out.println(galaxy.getManufacturer());
        System.out.println(galaxy.getMonthsWarranty());
        System.out.println(galaxy.getPrice());

        System.out.println("\nHere is the BlackBerry phone information:");
        System.out.println(priv.getModel());
        System.out.println(priv.getManufacturer());
        System.out.println(priv.getMonthsWarranty());
        System.out.println(priv.getPrice());

        // Changes the model of the iPhone variable to 'iPhoneSE' and changes the price to $590.00
        iPhone.setModel("iPhoneSE");
        iPhone.setPrice(590f);

        System.out.println("\nHere is the Apple phone's new information:");
        System.out.println(iPhone.getModel());
        System.out.println(iPhone.getManufacturer());
        System.out.println(iPhone.getMonthsWarranty());
        System.out.println(iPhone.getPrice());

        // Adds up the total price of all 3 phones and displays it.
        System.out.println("The total cost of all phones is $" + (iPhone.getPrice() + galaxy.getPrice() + priv.getPrice()));

        // Checks which phone has the longest warranty by comparing them all together.
        if ((iPhone.getMonthsWarranty() > galaxy.getMonthsWarranty()) && (iPhone.getMonthsWarranty() > priv.getMonthsWarranty())) {
            System.out.println("The Apple phone has the longest warranty");
        } else if ((galaxy.getMonthsWarranty() > iPhone.getMonthsWarranty()) && (galaxy.getMonthsWarranty() > priv.getMonthsWarranty())) {
            System.out.println("The Galaxy phone has the longest warranty");
        } else if ((priv.getMonthsWarranty() > galaxy.getMonthsWarranty()) && (priv.getMonthsWarranty() > iPhone.getMonthsWarranty())) {
            System.out.println("The PRIV phone has the longest warranty");
        }
    }
}
