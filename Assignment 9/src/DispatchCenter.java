import java.util.*;

public class DispatchCenter {
    public static String[] AREA_NAMES = {"Downtown", "Airport", "North", "South", "East", "West"};

    private int[][]  stats; // You'll need this for the last part of the assignment

    HashMap<Integer, Taxi> taxis;
    HashMap<String, ArrayList<Taxi>> areas;

    // Constructor
    public DispatchCenter() {
        // You'll need this for the last part of the assignment
        stats = new int[AREA_NAMES.length][AREA_NAMES.length];

        areas = new HashMap<>();
        for (int i = 0; i < AREA_NAMES.length; i++)
            areas.put(AREA_NAMES[i], new ArrayList<>());

        taxis = new HashMap<>();
        for (int i = 0; i < 50; i++) {
            int randomNumber = new Random().nextInt(1000); // Generates random number between 0 to 999
            while (randomNumber < 100) // Makes sure the random number between 0 to 999 is above or equal to 100
                randomNumber = new Random().nextInt(1000);
            Taxi taxi = new Taxi(randomNumber);
            randomNumber = new Random().nextInt(AREA_NAMES.length); // Generates random number between 0 to 5
            addTaxi(taxi, AREA_NAMES[randomNumber]);
        }
    }

    public HashMap<String, ArrayList<Taxi>> getAreas() { return areas; }

    // You'll need this for the last part of the assignment
    public int[][]   getStats() { return stats; }


    // Update the statistics for a taxi going from the pickup location to the dropoff location
    public void updateStats(String pickup, String dropOff) {
        int col = 0, row = 0;
        for (int i = 0; i < AREA_NAMES[i].length(); i++)
            if (pickup.equals(AREA_NAMES[i]))
                col = i;
        for (int i = 0; i < AREA_NAMES[i].length(); i++)
            if (dropOff.equals(AREA_NAMES[i]))
                row = i;

        stats[col][row] += 1;
    }

    // Determine the travel times from one area to another
    public static int computeTravelTimeFrom(String pickup, String dropOff) {
        int points[][] = {{10, 40, 20, 20, 20, 20}, // Downtown
                          {40, 10, 40, 40, 20, 60}, // Airport
                          {20, 40, 10, 40, 20, 20}, // North
                          {20, 40, 40, 10, 20, 20}, // South
                          {20, 20, 20, 20, 10, 40}, // East
                          {20, 60, 20, 20, 40, 10}}; // West
            //     {Downtown, Airport, North, South, East, West}

        int col = 0, row = 0;
        for (int i = 0; i < AREA_NAMES[i].length(); i++)
            if (pickup.equals(AREA_NAMES[i]))
                col = i;
        for (int i = 0; i < AREA_NAMES[i].length(); i++)
            if (dropOff.equals(AREA_NAMES[i]))
                row = i;

        return points[col][row];
    }

    // Add a taxi to the hashmaps
    public void addTaxi(Taxi aTaxi, String area) {
        if (!areas.containsKey(area))
            areas.put(area, new ArrayList<>());
        areas.get(area).add(aTaxi);
        taxis.put(aTaxi.getPlateNumber(), aTaxi);
    }

    // Return a list of all available taxis within a certain area
    private ArrayList<Taxi> availableTaxisInArea(String s) {
        ArrayList<Taxi> result = new ArrayList<Taxi>();

        for (int i = 0; i < areas.get(s).size(); i++)
            if (areas.get(s).get(i).getAvailable())
                result.add(areas.get(s).get(i));

        return result;
    }

    // Return a list of all busy taxis
    public ArrayList<Taxi> getBusyTaxis() {
        ArrayList<Taxi> result = new ArrayList<Taxi>();

        for (int i = 0; i < AREA_NAMES.length; i++)
            for (int j = 0; j < areas.get(AREA_NAMES[i]).size(); j++)
                if (!areas.get(AREA_NAMES[i]).get(j).getAvailable())
                    result.add(areas.get(AREA_NAMES[i]).get(j));

        return result;
    }

    // Find a taxi to satisfy the given request
    public Taxi sendTaxiForRequest(ClientRequest request) {
        ArrayList<Taxi> availableTaxisInArea = availableTaxisInArea(request.getPickupLocation());

        if (!availableTaxisInArea.isEmpty()) { // If there are taxis in area
            areas.get(request.getDropoffLocation()).add(availableTaxisInArea.get(0)); // Add first taxi in list to destination area list
            availableTaxisInArea.get(0).setAvailable(false); // set availability to false
            availableTaxisInArea.get(0).setEstimatedTimeToDest(computeTravelTimeFrom(
                    request.getPickupLocation(),
                    request.getDropoffLocation())); // Calculate how long it will take to get to destination

            areas.get(request.getPickupLocation()).remove(0); // Remove first taxi from pickup location
            updateStats(request.getPickupLocation(),
                    request.getDropoffLocation());
            return availableTaxisInArea.get(0); // Return the taxi that is used for this request
        } else { // Else if no taxis in the area of the client request
            for (int i = 0; i < AREA_NAMES.length; i++) { // Checks available taxis in other areas
                availableTaxisInArea = availableTaxisInArea(AREA_NAMES[i]);
                if (!availableTaxisInArea.isEmpty()) { // Checks if current area that is being checked has taxis
                    areas.get(request.getDropoffLocation()).add(availableTaxisInArea.get(0)); // Add first taxi in the list to the destination area list
                    availableTaxisInArea.get(0).setAvailable(false); // set availability to false
                    availableTaxisInArea.get(0).setEstimatedTimeToDest( // Calculate how long it will take to get to destination
                            computeTravelTimeFrom(request.getPickupLocation(), request.getDropoffLocation() // From pickup location to drop off location
                            + computeTravelTimeFrom(AREA_NAMES[i], request.getPickupLocation()))); // Plus from taxi location to pickup location

                    areas.get(AREA_NAMES[i]).remove(0); // Removes the taxi that went to pickup location
                    updateStats(request.getPickupLocation(),
                            request.getDropoffLocation());
                    return availableTaxisInArea.get(0); // Returns the taxi that is used for this request
                }
            }
        }
        // Else there are no taxis available at all
        return null;
    }
}