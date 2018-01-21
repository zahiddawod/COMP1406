/**
 * Name: Zahid Dawod
 * Student ID: #########
 * Assignment: 1
 * Question: 2
 **/

public class HospitalBuilderProgram {
    private static byte[][][] maps = {{{2, 2}, {2, 8}, {5, 15}, {19, 1}, {17, 17}},
            {{1, 1}, {7, 19}, {13, 19}, {19, 7}, {19, 13}},
            {{0, 19}, {2, 19}, {4, 19}, {6, 19}, {18, 19}},
            {{7, 19}, {13, 19}, {19, 19}, {19, 13}, {19, 7}}};

    public static void main(String[] args) {
        float distance[][][][] = new float[4][5][20][20]; // 4D Array storing information about the distances calculated for each map(town(x(y))) using distance formula.
        float furthestTown[][][] = new float[4][20][20]; // 3D Array storing the furthest distance out of all the towns that was calculated for each x-y point (cell).
        float lowestDistance[] = new float[4]; // Array to find and store the lowest distance calculated from the furthestTown array.

        for (int i = 0; i < 4; i++) {
            lowestDistance[i] = 100f; // Sets lowest distance for all maps to 100.0
        }

        // ------------------------------------------------- CALCULATING FURTHEST DISTANCE (FINDING FURTHEST TOWN FROM EACH POINT) ------------------------------------------------- //
        for (int map = 0; map < 4; map++) { // Loops through the number of maps there are.
            for (int x = 0; x < 20; x++) { // Loops through the number of x's to check (radius).
                for (int y = 0; y < 20; y++) { // Loops through the number of y's to check (radius).
                    for (int town = 0; town < 5; town++) { // Loops through the number of towns there are.
                        distance[map][town][x][y] = (float) Math.sqrt(Math.pow((maps[map][town][0] - x), 2) + Math.pow((maps[map][town][1] - y), 2)); // Calculates distance between current (x, y) and current town for current map.
                        if (distance[map][town][x][y] > furthestTown[map][x][y]) { // Checks if the current distance that is being calculated is greater than the current furthest distance (town).
                            furthestTown[map][x][y] = distance[map][town][x][y]; // Sets the newest furthest distance (town) for this (x, y) point.
                        }
                    }
                }
            }
        }

        // ------------------------------------------------- FINDING THE LOWEST FURTHEST DISTANCE OF ALL POINTS FOR EACH MAP ------------------------------------------------- //
        byte p[][] = new byte[4][2]; // Array variable 'p' stores the x & y (answer (optimal location)) for each map.
        for (int map = 0; map < 4; map++) { // Loops through number of maps there are.
            for (int x = 0; x < 20; x++) { // Loops through number of x's that was checked (radius).
                for (int y = 0; y < 20; y++) { // Loops through number of y's that was checked (radius).
                    if (furthestTown[map][x][y] < lowestDistance[map]) { // Checks if the current furthest distance (town) is less than the current lowest distance.
                        lowestDistance[map] = furthestTown[map][x][y]; // Sets the current lowest distance for the current map that is being checked to the current lowest-furthest distance for that map.
                        p[map][0] = (byte) x; // Stores the x location for the current best optimal location.
                        p[map][1] = (byte) y; // Stores the y location for the current best optimal location.
                    }
                }
            }
            System.out.println("The optimal location to build a hospital for MAP " + (map + 1) + " is (" + p[map][0] + ", " + p[map][1] + ")"); // Prints out the answer (The best optimal location to build a hospital) for each map.
        }
    }
}
