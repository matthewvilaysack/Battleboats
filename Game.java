import java.util.Scanner;

public class Game {
    private Board playBoard;
    public static int getShipsRemaining(Board playBoard) {
        Boat[] boatsRemaining = playBoard.getBoats();
        Cell[][] board = playBoard.getBoard();
        int count = 0;
        for (int i = 0; i < boatsRemaining.length; i++) {
            for (int j = 0; j < boatsRemaining[i].getBoatLocation().length; j++) {
                if (boatsRemaining[i].getBoatLocation()[j].getStatus() == 'B') { //if the one of cells in a boat has a status of 'B', increment count
                    count++;
                    break;
                }
            }
        }
        return count;
    }
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int rows,cols;
        System.out.println("Would you like to run the game in debug mode? (y or n): ");
        boolean debugMode;
        if (s.next().equals("y")) {
            debugMode = true;
        } else {
            debugMode = false;
        }
        System.out.println("What are going to be the board dimensions? (type in format 'x y'): ");
        rows = s.nextInt();
        cols = s.nextInt();
        Board playBoard = new Board(rows,cols);
        playBoard.placeBoats();
        System.out.println("Number of boats: " + getShipsRemaining(playBoard));


        int numberOfTurns = 0;
        while (getShipsRemaining(playBoard) != 0) {

            System.out.println("Your turn.");
            System.out.println("What power would you like to use? (f, m, d): ");
            playBoard.display();
            if (debugMode) {
                playBoard.print();
            }
            String input = s.next();
            System.out.println(input);
            if (input.equals("f")) {
                System.out.println("Where would you like to fire your single missile?: ");
                int a, b;
                a = s.nextInt();
                b = s.nextInt();
                int initialShipsRemaining = getShipsRemaining(playBoard);
                char fireStatus = playBoard.fire(a,b); // check the status of the boat cell after firing

                numberOfTurns++;
                if (fireStatus == 'H') { //hit
                    playBoard.fire(a, b);
                    int updatedShipsRemaining = getShipsRemaining(playBoard);
                    if (updatedShipsRemaining == 0) {
                        System.out.println("Turn " + numberOfTurns + ": the user selected (" + a + ", " + b + ") and sunk is printed. The game ends because the last boat has sunk");

                    }
                    else if (updatedShipsRemaining < initialShipsRemaining) {
                        System.out.println("Turn " + numberOfTurns + ": the user selected (" + a + ", " + b + ") and sunk is printed.");
                    }
                    else {
                        System.out.println("Turn " + numberOfTurns + ": the user selected (" + a + ", " + b + ") and hit is printed.");
                    }
                }
                else if (fireStatus == 'M') { //miss
                    playBoard.fire(a,b);
                    System.out.println("Turn " + numberOfTurns + ": the user selected (" + a + ", " + b + ") and miss is printed.");
                }
                else if (fireStatus == 'P') { //penalty
                    playBoard.fire(a, b);
                    System.out.println("Turn " + numberOfTurns + ": the user selects (" + a + ", " + b + ") which is either out of bounds or has been selected before. This is a penalty so a turn is skipped.");
                    numberOfTurns++;
                    System.out.println("Turn " + numberOfTurns + ": skipped.");
                }
            }
            else if (input.equals("m")) { // handles missile input
                boolean validCoords = false;
                while (!validCoords) {
                    int initialShipsRemaining = getShipsRemaining(playBoard);
                    System.out.println("Where would you like to fire your missile?: ");
                    int a, b;
                    a = s.nextInt();
                    b = s.nextInt();
                    if ((a >= 0 && a <= rows) && (b >= 0 && b <= cols)) {
                        playBoard.missile(a, b);
                        numberOfTurns++;
                        validCoords = true;
                        int updatedShipsRemaining = getShipsRemaining(playBoard);
                        if (updatedShipsRemaining == 0) {
                            System.out.println("User fired a missile at (" + a + ", " + b + ") and sunk is printed. The game ends because the last boat has sunk");
                        }
                        else if (updatedShipsRemaining < initialShipsRemaining) {
                            System.out.println("User fired a missile at (" + a + ", " + b + ") and sunk is printed.");
                        }
                    }
                    else {
                        System.out.println("Invalid coordinates. Try again.");
                    }
                }
            }
            else if (input.equals("d")) { // handles 'd' (drone) input
                System.out.println("Would you like to scan a row or column?: Type in r for row or c for column. ");
                String direction = s.next();

                if (direction.equals("r")) {
                    System.out.println("Which row or column would you like to scan?");
                    int index = s.nextInt();
                    if (index >=0 && index < rows) {
                        playBoard.drone(direction, index);
                    }
                    else {
                        System.out.println("Invalid input. Please type in a number within the boundaries of the board.");
                    }
                }
                else if (direction.equals("c")) {
                    System.out.println("Which row or column would you like to scan?");
                    int index = s.nextInt();
                    if (index >=0 && index < cols) {
                        playBoard.drone(direction, index);
                    }
                    else {
                        System.out.println("Invalid Input. Please type in a number within the boundaries ofthe board.");
                    }
                }
                else {
                    System.out.println("Invalid Input. Please type in r for row or c for column");
                }
            }
        }
        playBoard.display();
        if (debugMode) {
            playBoard.print();
        }
        System.out.println("It took " + numberOfTurns + " turn(s) to complete the game.");
    }

}