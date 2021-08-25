import java.util.Random;

public class Board {
    private Cell[][] board; // [ (0,0) (1, 0) (2, 0) ] 1 x 3
    private Boat[] Boats;
    private int totalShots,turns,shipsRemaining; 
    // didn't have to keep track of totalShots.
    // didn't use because we kept track of turns in Game.java.
    // didn't use because we have getShipsRemaining() in Game.java.

    public Board(int rows, int cols) {
        if ((rows >= 3 && cols >= 3) && (rows <= 10 && cols <= 10)) {
            this.board = new Cell[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    this.board[i][j] = new Cell(i, j);
                }
            }
        }
        else {
            System.out.println("Error, incorrect board dimensions.");
        }
    }
    public Cell[][] getBoard() {
        return this.board;
    }

    public Boat[] getBoats() {
        return Boats;
    }

    public void placeBoats() {
        //randomly places boats on board.
        //can't be diagonal, overlap, outside of board

        Random random = new Random();
        int numberOfBoats = 0;
        int height = board[0].length;
        int width = board.length;
        int[] boatSizes = {2, 3, 3, 4, 5};
        if (width == 3 || height == 3) { //gives appropriate # of boats based on board size
            numberOfBoats = 1;
        } else if ((3 < width && width <= 4) || (3 < height && height <= 4))
            numberOfBoats = 2;

        else if (4 < width && width <= 6 || 4 < height && height <= 6)
            numberOfBoats = 3;
        else if (6 < width && width <= 8 || 6 < height && height <= 8)
            numberOfBoats = 4;
        else if (8 < width && width <= 10 || 8 < height && height <= 10)
            numberOfBoats = 5;

        Boats = new Boat[numberOfBoats];

        // accessing a singular boats location: boat.boatLocation[i].row(x)/col(y)
        for (int i = 0; i < numberOfBoats; i++) {
            Random rand = new Random();
            boolean orientation = random.nextBoolean(); //true = boat is vertical, false = boat is horizontal
            boolean complete = true;
            int x = (int) Math.floor(rand.nextInt(width));
            int y = (int) Math.floor(rand.nextInt(height));
            Cell[] boatLocation = new Cell[boatSizes[i]];
            Boat boat = new Boat(orientation, boatSizes[i], boatLocation);
            Boats[i] = boat;

            if (orientation) { //checks if ship can fit in board dimensions
                while (y + boat.getBoatSize() > height) {  // checks if the ship will fit vertically on the bottom side of the grid, subtracts until there can be a boat placed from y + boat.getBoatSize()
                    y -= 1;
                }
                for (int j = y; j < y + boat.getBoatSize(); j++) {
                    if (this.board[x][j].getStatus() == 'B') { //if it equals to a space where a boat is present...
                        complete = false;
                        i--;
                        break;
                    }
                }
                if (complete) {
                    for (int j = y; j < y + boat.getBoatSize(); j++) {
                        this.board[x][j].setStatus('B');
                        boatLocation[j - y] = this.board[x][j]; // adds the cell(coordinate) of the the boat until complete.
                    }
                }
            }
            if (!orientation) {
                while (x + boat.getBoatSize() > width) { // checks if the ship will fit horizontally on the right side of the grid, subtracts until there can be a boat placed from x + boat.getBoatSize()
                    x -= 1;
                }
                for (int j = x; j < x + boat.getBoatSize(); j++) {
                    if (this.board[j][y].getStatus() == 'B') { //if it equals to a space where a boat is present...
                        complete = false;
                        i--;
                        break;
                    }
                }
                if (complete) {
                    for (int j = x; j < x + boat.getBoatSize(); j++) {
                        this.board[j][y].setStatus('B');
                        boatLocation[j - x] = this.board[j][y]; // adds the cell(coordinate) of the the boat until complete.
                    }
                }
            }
        }
    }
    public char fire(int y, int x) { // switched x and y to account for the origin being in the top left
        //handles firing on a coordinate
        int height = board[0].length;
        int width = board.length;
        char fireStatus = ' ';
        System.out.println(x);
        System.out.println(y);
        if ((x >= 0 && x <= height) && (y >= 0 && y <= width)) { // switched y and x associated with width and height
            if (this.board[x][y].getStatus() == '-') {
                this.board[x][y].setStatus('M');
                fireStatus = 'M';
            } else if (this.board[x][y].getStatus() == 'H' || this.board[x][y].getStatus() == 'M') {
                fireStatus = 'P';
            } else if (this.board[x][y].getStatus() == 'B') {
                this.board[x][y].setStatus('H');
                fireStatus = 'H';
            }
        }
        else {
            fireStatus = 'P';
        }
        return fireStatus;
    }

    public void display() {
        //prints out the player board state every turn
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getStatus() == 'B') {
                    System.out.print("-");
                }
                else {
                    System.out.print(board[i][j].getStatus());
                }
            }
            System.out.println();
        }
    }

    public void print() {
        //print out the fully revealead board if a player types in the print command
        System.out.println("\n DEBUG DISPLAY:");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j].getStatus());
            }
            System.out.println();
        }
    }

    public void missile(int x, int y) {
        fire(x-1,y-1);
        fire(x-1,y);
        fire(x-1,y+1);
        fire(x,y-1);
        fire(x,y);
        fire(x,y+1);
        fire(x+1,y-1);
        fire(x+1,y);
        fire(x+1,y+1);
    }
    public void drone(String direction, int index) {
        //scans a specific row or column
        int boatCells = 0;
        if (direction.equals("r")) {
            for (int k = 0; k < board[index].length; k++) {
                if (board[index][k].getStatus() == 'H' || board[index][k].getStatus() == 'B') {
                    boatCells++;
                }
            }
        }
        else if (direction.equals("c")) {
            for (int j = 0; j < board.length; j++) {
                if (board[j][index].getStatus() == 'H' || board[j][index].getStatus() == 'B') {
                    boatCells++;
                }
            }
        }
        System.out.println("Drone has scanned " + boatCells + " targets in the specified area.");
    }
}
