import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Arrays;

public class Maze {
    private static final byte  OPEN = 0;
    private static final byte  WALL = 1;
    private static final byte  VISITED = 2;

    private int         rows, columns;
    private byte[][]    grid;

    // A constructor that makes a maze of the given size
    public Maze(int r, int c) {
        rows = r;
        columns = c;
        grid = new byte[r][c];
        for(r=0; r<rows; r++) {
            for (c = 0; c<columns; c++) {
                grid[r][c] = WALL;
            }
        }
    }

    public int getRows() { return rows; }
    public int getColumns() { return columns; }

    // Return true if a wall is at the given location, otherwise false
    public boolean wallAt(int r, int c) {
        return grid[r][c] == WALL;
    }

    // Return true if this location has been visited, otherwise false
    public boolean visitedAt(int r, int c) {
        return grid[r][c] == VISITED;
    }

    // Put a visit marker at the given location
    public void placeVisitAt(int r, int c) {
        grid[r][c] = VISITED;
    }

    // Remove a visit marker from the given location
    public void removeVisitAt(int r, int c) {
        grid[r][c] = OPEN;
    }

    // Put a wall at the given location
    public void placeWallAt(int r, int c) {
        grid[r][c] = WALL;
    }

    // Remove a wall from the given location
    public void removeWallAt(int r, int c) {
        grid[r][c] = 0;
    }

    // Carve out a maze
    public void carve() {
        int startRow = (int)(Math.random()*(rows-2))+1;
        int startCol = (int)(Math.random()*(columns-2))+1;
        carve(startRow, startCol);
    }

    // Directly recursive method to carve out the maze
    public void carve(int r, int c) {
        // If r and c is not part of the border and there is a wall at this location
        if ((r > 0 && r < rows-1) && (c > 0 && c < columns-1) && wallAt(r, c)) {
            //                                                                                          0      1      2      3
            ArrayList<Integer> rowOffsets = new ArrayList<Integer>(Arrays.asList(-1, 1, 0, 0)); // (|  UP  | DOWN | SAME | SAME  |)
            ArrayList<Integer> colOffsets = new ArrayList<Integer>(Arrays.asList(0, 0, -1, 1)); // (| SAME | SAME | LEFT | RIGHT |)

            int numOfWalls = 0;
            for (int i = 0; i < 4; i++) {
                if (wallAt(r + rowOffsets.get(i), c + colOffsets.get(i))) {
                    numOfWalls++; // Find how many walls around this point
                    //System.out.println((numOfWalls+1) + " walls around point " + r + " " + c);
                }
            }

            if (numOfWalls >= 3) { // If number of walls around this point are 3 or more...
                removeWallAt(r, c); // remove the wall at this location
                //System.out.println("Removed wall at " + r + " " + c);

                // Decide what order the carve(r, c) will be called based upon what directions we currently are allowed to go to
                int randNum = (int) (Math.random() * 4); // Generate a random number (which direction)
                ArrayList<Integer> randDirection = new ArrayList<>(); // Create an arraylist that stores the order in which we generated the random direction
                while(randDirection.size() != 4) { // While the size of the arraylist does not equal 4 (Since there are 4 directions)
                    if (!randDirection.contains(randNum)) // If the arraylist doesn't already contain the random number that was generated
                        randDirection.add(randNum); // Then add it to the array list
                    randNum = (int) (Math.random() * 4); // Generate a new random number until the arraylist has 4 different random entries (ranging from 0-3)
                }

                for (int i = 0; i < 4; i++) // Recursively run each scenario (direction) in random order that we stored in the arraylist
                    carve(r + rowOffsets.get(randDirection.get(i)), c + colOffsets.get(randDirection.get(i)));
            }
        }
    }

    // Determine the longest path in the maze from the given start location
    public ArrayList<Point2D> longestPath() {
        ArrayList<ArrayList<Point2D>> paths = new ArrayList<ArrayList<Point2D>>();
        ArrayList<Point2D> open = new ArrayList<Point2D>();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (!wallAt(r, c))
                    open.add(new Point2D(r, c)); // Store all the possible starting locations for each path
            }
        }

        for (int i = 0; i < open.size(); i++)
            paths.add(longestPathFrom((int)open.get(i).getX(), (int)open.get(i).getY())); // Store all the possible paths

        ArrayList<Point2D> longestPath = paths.get(0); // Longest equals the first path
        for (int i = 0; i < paths.size(); i++) {
            if (paths.get(i).size() > longestPath.size())
                longestPath = paths.get(i); // Find the longest paths of all the possible paths
        }

        return longestPath; // Return longest path of all the paths that exists
    }

    // Determine the longest path in the maze from the given start location
    public ArrayList<Point2D> longestPathFrom(int r, int c) {
        ArrayList<Point2D> path = new ArrayList<Point2D>();

        if (!wallAt(r, c)) { // Makes sure there is no wall at (1, 1)
            placeVisitAt(r, c); // Make location marked as visited
            ArrayList<Point2D> up = new ArrayList<>(), down = new ArrayList<>(), left = new ArrayList<>(), right = new ArrayList<>();

            if (!wallAt(r - 1, c) && !visitedAt(r - 1, c)) // Check ahead of time
                up = longestPathFrom(r - 1, c); // Recursively go UP
            if (!wallAt(r, c + 1) && !visitedAt(r, c + 1))
                right = longestPathFrom(r, c + 1); // Recursively go RIGHT
            if (!wallAt(r + 1, c) && !visitedAt(r + 1, c))
                down = longestPathFrom(r + 1, c); // Recursively go DOWN
            if (!wallAt(r, c - 1) && !visitedAt(r, c - 1))
                left = longestPathFrom(r, c - 1); // Recursively go LEFT

            if (up.size() >= right.size() && up.size() >= down.size() && up.size() >= left.size()) // Up
                path = up; // If the up arraylist is greater size than the other directions then set path to equal to up
            else if (right.size() >= up.size() && right.size() >= down.size() && right.size() >= left.size()) // Right
                path = right; // If the right arraylist is greater size than the other directions then set path to equal to right
            else if (down.size() >= up.size() && down.size() >= right.size() && down.size() >= left.size()) // Down
                path = down; // If the down arraylist is greater size than the other directions then set path to equal to down
            else if (left.size() >= up.size() && left.size() >= down.size() && left.size() >= right.size()) // Left
                path = left; // If the left arraylist is greater size than the other directions then set path to equal to left

            removeVisitAt(r, c); // Mark the current (r, c) as open
            path.add(new Point2D(r, c)); // Add the current point into the path variable
        }

        return path;
    }
}