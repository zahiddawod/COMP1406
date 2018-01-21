import java.util.Scanner; // importing Scanner object

/**
 * Name: Zahid Dawod
 * Student ID: #########
 * Assignment: 1
 * Question: 1
 **/

public class WaterLevelProgram {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in); // Declaring 'input' as a new scanner object.

        int counter = 0; // Declaring counter to check if water is subsiding
        int waterLevel; // Declaring waterLevel to store user input
        int prevWaterLevel = 0; // Declaring prevWaterLevel to compare to the previous waterLevel.

        while(counter < 3){ // While the water is not subsiding.
            // Getting input from the user.
            System.out.print("What is the water level at now (in mm): ");
            waterLevel = input.nextInt();
            if(waterLevel < prevWaterLevel){ // Checking if the current water level is lower than the previous.
                counter += 1; // Increasing counter as each water level becomes lower.
            } else {
                counter = 0; // Resetting the counter since the current water is rising.
            }
            prevWaterLevel = waterLevel; // Storing current water level to compare it in the next loop.
        }
        System.out.println("\nIt appears that the flood is subsiding.");
    }
}
