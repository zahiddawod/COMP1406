/*
 * NAME: Zahid Dawod
 * Student ID: 101041370
 * Assignment: 2
 * Question: 3
 *
 */

public class PhonePlan {
    private int minutesAllowed; // Stores number of minutes allowed to be used to call.
    private int minutesUsed; // Stores minutes used.
    private int dataAllowed; // Stores data allowed to be used in KB.
    private int dataUsed; // Stores data used in KB.
    private boolean planType; // true; pay-as-you-go & false; regular

    // Constructor with 3-parameters (minutes allowed, data allowed, and plan type)
    public PhonePlan(int mA, int dA, boolean pT) {
        minutesAllowed = mA;
        dataAllowed = dA;
        planType = pT;
    }

    // Get methods
    public int getMinutesRemaining() { return minutesAllowed - minutesUsed; } // Get method for minutes remaining.
    public int getMinutesAllowed() { return minutesAllowed; }
    public int getMinutesUsed() { return minutesUsed; }
    public int getDataRemaining() { return dataAllowed - dataUsed; } // Get method for data remaining.
    public int getDataAllowed() { return dataAllowed; }
    public int getDataUsed() { return dataUsed; }
    public boolean getPlanType() { return planType; } // Get method for plant type.

    // Set methods
    public void setMinutesAllowed(int x) { minutesAllowed += x; } // Set method to increase minutes allowed for pay-as-you-go customers.
    public void setMinutesUsed(int x) { minutesUsed += x; } // Set method to increase minutes used for all customers.
    public void setDataUsed(int x) { dataUsed += x; } // Set method to increase data used.

    // toString method to return a string if the object is being called by itself
    public String toString() {
        if (planType) { // Checks if the object's (customer's) plan type is pay-as-you-go.
            return "Pay-as-you-go Plan with " + getMinutesRemaining() + " minutes and " + getDataRemaining() + "KB remaining";
        } else {
            return "Regular (" + minutesAllowed + " minute, " + String.format("%1.2f",(dataAllowed / Math.pow(1000, 2))) + "GB data) Monthly Plan with " + getMinutesRemaining() + " minutes and " + getDataRemaining() + "KB remaining";
        }
    }
}
