package cinema;

import java.util.Scanner;

public class Cinema {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.println("You must enter a number!");
        }
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.println("You must enter a number!");
        }
        int seatsPerRow = scanner.nextInt();

        menu(scanner, createSeatingArray(rows, seatsPerRow));

        scanner.close();
    }

    public static void menu(Scanner scanner, char[][] currentSeatingArrangement) {
        int exitCondition = 1;
        do {
            System.out.println("\n1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    printSeatingArrangement(currentSeatingArrangement);
                    break;
                case 2:
                    getTicketPrice(currentSeatingArrangement, scanner);
                    break;
                case 3:
                    statistics(currentSeatingArrangement);
                    break;
                case 0:
                    System.out.println("Good bye!");
                    exitCondition = 0;
                    break;
                default:
                    System.out.println("Wrong choice!");
                    break;
            }
        } while (exitCondition != 0);
    }

    static void getTicketPrice(char [][] seatingArrangement, Scanner scanner) {
        int rowNumber;
        int seatNumber;
        boolean successful = false;

        do {
            System.out.println("\nEnter a row number:");
            while (!scanner.hasNextInt()) {
                scanner.next();
                System.out.println("Enter a row \"number\": ");
            }
            rowNumber = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            while (!scanner.hasNextInt()) {
                scanner.next();
                System.out.println("Enter a seat \"number\": ");
            }
            seatNumber = scanner.nextInt();

            if (rowNumber > seatingArrangement.length || seatNumber > seatingArrangement[0].length) {
                System.out.println("Wrong input!");

            } else if (seatingArrangement[rowNumber - 1][seatNumber - 1] == 'B') {
                System.out.println("That ticket has already been purchased!");

            } else {
                successful = true;
            }
        } while (!successful);


        int totalSeats = seatingArrangement.length * seatingArrangement[0].length;
        int totalRows = seatingArrangement.length;
        int ticketPrice;

        if (totalSeats <= 60) {
            ticketPrice = 10;
        } else {
            int frontHalfRows = totalRows / 2;
            ticketPrice = (rowNumber <= frontHalfRows) ? 10 : 8;
        }

        System.out.println("\nTicket price: $" + ticketPrice);
        //Update seat in array to show 'B' for booked
        seatingArrangement[rowNumber - 1][seatNumber - 1] = 'B';
    }

    static char[][] createSeatingArray(int rows, int seatsPerRow) {

        char[][] seatingArrangement = new char[rows][seatsPerRow];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seatsPerRow; j++) {
                seatingArrangement[i][j] = 'S';
            }
        }

        // Print the seating arrangement
        System.out.println("\nCinema:");
        System.out.print("  ");
        for (int i = 1; i <= seatsPerRow; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < rows; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < seatsPerRow; j++) {
                System.out.print(seatingArrangement[i][j] + " ");
            }
            System.out.println();
        }
        return seatingArrangement;
    }

    public static void printSeatingArrangement(char[][] seatingArrangement) {
        // Print the seating arrangement
        System.out.println("\nCinema:");
        System.out.print("  ");
        for (int i = 1; i <= seatingArrangement[0].length; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < seatingArrangement.length; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < seatingArrangement[i].length; j++) {
                System.out.print(seatingArrangement[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void statistics(char [][] seatingArrangement) {
        //Display current income, total income, number of available seats, and the percentage of occupancy.

        //Current income = # of tickets sold * ticket price
        //Total income = # of rows * # of seats per row * ticket price
        //Total possible seats = # of rows * # of seats per row
        //Number of available seats = # of rows * # of seats per row - # of tickets
        //Percentage of occupancy = # of tickets sold / (# of rows * # of seats per row) * 100



        /* ### Variable Definitions ### */
        int totalSeats = seatingArrangement.length * seatingArrangement[0].length;
        int fullTicketPrice = 10;
        int takenSeats = 0;
        int currentIncome = 0;
        int totalIncome;
        float percentageOfOccupancy = 0;
        /* ### End Variable Definitions ### */

        /* ### Calculations ### */

        //Calculate and update variable to store amount of taken seats.
        for (int i = 0; i < seatingArrangement.length; i++) {
            for (int j = 0; j < seatingArrangement[i].length; j++) {
                if (seatingArrangement[i][j] == 'B') {
                    takenSeats++;
                }
            }
        }
        //Calculate percentage of occupancy, using takenSeats and totalSeats.
        percentageOfOccupancy = (takenSeats / (float) totalSeats) * 100;

        //Calculate current income, using fullPriceSeats, discountedPriceSeats, takenFullPriceSeats
        int ticketPrice;
        int frontHalfRows = seatingArrangement.length / 2;
        for (int i = 0; i < seatingArrangement.length; i++) {
            for (int j = 0; j < seatingArrangement[i].length; j++) {
                if (seatingArrangement[i][j] == 'B') {
                    if (totalSeats <= 60) {
                        ticketPrice = fullTicketPrice;
                    } else {
                        ticketPrice = (i < frontHalfRows) ? 10 : 8;
                    }
                    currentIncome += ticketPrice;
                }
            }
        }

        //Calculate total income, using fullPriceSeats, discountedPriceSeats, takenFullPriceSeats, and takenDiscountedPriceSeats, and ticket price.
        if (totalSeats <= 60) {
            totalIncome = totalSeats * 10;
        } else {
            frontHalfRows = seatingArrangement.length / 2;
            int backHalfRows = seatingArrangement.length - frontHalfRows;
            int frontHalfIncome = frontHalfRows * seatingArrangement[0].length * 10;
            int backHalfIncome = backHalfRows * seatingArrangement[0].length * 8;
            totalIncome = frontHalfIncome + backHalfIncome;
        }
        /* ### End Calculations ### */

        /* ### Print Statistics ### */
        System.out.println("\nStatistics:");
        System.out.printf("Number of purchased tickets: %d\n", takenSeats);
        System.out.printf("Percentage: %.2f%%\n", percentageOfOccupancy);
        System.out.printf("Current income: $%d\n", currentIncome);
        System.out.printf("Total income: $%d\n", totalIncome);
        /* ### End Print Statistics ### */
    }


}