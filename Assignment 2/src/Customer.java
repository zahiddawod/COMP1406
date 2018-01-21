/*
 * NAME: Zahid Dawod
 * Student ID: #########
 * Assignment: 2
 * Question: 4 & 5
 *
 */

public class Customer {
    private String name;
    private CellPhone cellPhone;
    private PhonePlan phonePlan;
    private int callsMade;

    // Variables to store additional charges when printing out the monthly summary
    private float monthlyCharges;
    private float voiceOvertimeCharges = 0f;
    private float dataOverusageCharges = 0f;
    private float balance = 0f;

    public Customer(String n, CellPhone c, PhonePlan p) {
        name = n;
        cellPhone = c;
        phonePlan = p;
        balance = (float) (this.phonePlan.getMinutesAllowed() * 0.4); // Set initial balance owed for pay-as-you-go customers to $0.40 per minute (balance for regular customer won't be used).
    }

    // Get methods
    public PhonePlan getPlan() { return phonePlan; }

    // toString method if the object is being called instead of a specific attribute
    public String toString() {
        return name + " with a " + cellPhone.getManufacturer() + " " + cellPhone.getModel() + " on a " + phonePlan;
    }

    public void phone(Customer customer, int callLength) {
        if (callLength >= 1) { // Checks if the call length is at least 1 minute long
            // if first customer is not pay-as-you-go or call length is less than the this.minutesremaining and second customer is not pay-as-you-go or call length is less than customer.minutesremaining then call is succesful.
            if (!((this.phonePlan.getPlanType() && callLength > this.phonePlan.getMinutesRemaining()) || (customer.phonePlan.getPlanType() && callLength > customer.phonePlan.getMinutesRemaining()))) { // If call was succesful ..
                this.phonePlan.setMinutesUsed(callLength); // Increases the minutes used for the customer calling by the call length.
                customer.phonePlan.setMinutesUsed(callLength); // Increases the minutes used for the customer getting called by the call length.
                this.callsMade += 1; // Increments the number of calls made for both customers.
                customer.callsMade += 1;
            }
        }
    }

    public void buyMinutes(int minutesPurchased) {
        if (this.phonePlan.getPlanType()) { // Checks if the plan type is pay-as-you-go
            this.phonePlan.setMinutesAllowed(minutesPurchased); // Adds minutes to the customers plan
            this.balance += (minutesPurchased * 0.4); // Charges the customer $0.40 for each minute they purchased
        }
    }

    public void accessInternet(int kbUsed) {
        if (this.phonePlan.getDataRemaining() >= kbUsed) {
            this.phonePlan.setDataUsed(kbUsed); // If the current data remaining in the customers plan is greater than number of KB wanted to be used then internet access is succesful.
        } else {
            if (this.phonePlan.getPlanType())
                this.phonePlan.setDataUsed(this.phonePlan.getDataRemaining()); // If plan type is pay-as-you-go then only use the remaining data.
            else
                this.phonePlan.setDataUsed(kbUsed); // Else use the amount of KB they want to use to access the internet (even if it goes over).
        }
    }

    private void calculateAdditionalCharges() { // Private method to calculate regular customer's charges.
        // Calculate monthly charges
        if (this.phonePlan.getMinutesAllowed() == 200)
            monthlyCharges = 25;
        else if (this.phonePlan.getMinutesAllowed() == 100)
            monthlyCharges = 15;
        monthlyCharges += 10 * (this.phonePlan.getDataAllowed() / Math.pow(1000, 2));

        // Calculate voice-overtime charges
        if (this.phonePlan.getMinutesUsed() > this.phonePlan.getMinutesAllowed())
            voiceOvertimeCharges = (float) (0.15 * (this.phonePlan.getMinutesUsed() - this.phonePlan.getMinutesAllowed()));

        // Calculate data-overusage charges
        if (this.phonePlan.getDataUsed() > this.phonePlan.getDataAllowed())
            dataOverusageCharges = (float) (0.00005 * (this.phonePlan.getDataUsed() - this.phonePlan.getDataAllowed()));
    }

    public void printMonthlyStatement() {
        System.out.println(String.format("%-30s%-30s", "Name:", this.name)); // Displays customers name.

        if (this.phonePlan.getPlanType()) { // Checks if the customer has pay-as-you-go or regular plan.
            System.out.println(String.format("%-30s%-30s", "Plan Type:", "Pay-as-you-go")); // Displays type of plan the customer has.
            System.out.println(String.format("%-30s%-30s", "Minutes Used:", this.phonePlan.getMinutesUsed())); // Displays minutes used to call or get called.
            System.out.println(String.format("%-30s%-30s", "Minutes Remaining:", this.phonePlan.getMinutesRemaining())); // Displays minutes remaining on the customers plan.
            System.out.println(String.format("%-30s%-30s", "Data Used:", this.phonePlan.getDataUsed())); // Displays amount of data used on the customers plan.
            System.out.println(String.format("%-30s%-30s", "Data Remaining:", this.phonePlan.getDataRemaining())); // Displays the amount of data on the customers plan left.
            System.out.println(String.format("%-30s%-30s", "Calls Made:", this.callsMade)); // Displays number of calls made (to and from this customer).
            System.out.println(String.format("%-30s%-30s", "Monthly Charges:", String.format("$%1.2f", balance))); // Displays monthly charges for Pay-as-you-go customers (which is balance).
            System.out.println(String.format("%-30s%-30s", "HST:", String.format("$%1.2f", 0.13 * balance))); // Displays tax: 13% of 'balance'
            System.out.println(String.format("%-30s%-30s", "Total Due:", String.format("$%1.2f", 1.13 * balance))); // Displays total including tax.
        } else {
            System.out.println(String.format("%-30s%-30s", "Plan Type:", "Regular Monthly (" + this.phonePlan.getMinutesAllowed() + " minutes, " + String.format("%1.2f",(this.phonePlan.getDataAllowed() / Math.pow(1000, 2))) + "GB data)")); // Displays type of plan the  customer has.
            System.out.println(String.format("%-30s%-30s", "Minutes Used:", this.phonePlan.getMinutesUsed())); // Displays number of calling minutes used on the customers plan.
            System.out.println(String.format("%-30s%-30s", "Data Used:", this.phonePlan.getDataUsed())); // Displays amount of data used on the customers plan.
            System.out.println(String.format("%-30s%-30s", "Calls Made:", this.callsMade)); // Displays number of calls made (to and from this customer).

            this.calculateAdditionalCharges(); // Calls private method to calculate addition charges for each customer.
            System.out.println(String.format("%-30s%-30s", "Monthly Charges:", String.format("$%1.2f", monthlyCharges))); // Displays monthly charges.
            System.out.println(String.format("%-30s%-30s", "Voice Overtime Charges:", String.format("$%1.2f", voiceOvertimeCharges))); // Displays voice overtime charges. (if any)
            System.out.println(String.format("%-30s%-30s", "Data Overusage Charges:", String.format("$%1.2f", dataOverusageCharges))); // Displays data overusage charges. (if any)

            float total = (monthlyCharges + voiceOvertimeCharges + dataOverusageCharges); // Sums up all the additional charges.
            System.out.println(String.format("%-30s%-30s", "HST:", String.format("$%1.2f", 0.13 * total))); // Displays tax: 13% of 'total'
            System.out.println(String.format("%-30s%-30s", "Total Due:", String.format("$%1.2f", 1.13 * total))); // Displays total including tax.
        }

        System.out.println("\n"); // Double space between each customers statment.
    }
}
