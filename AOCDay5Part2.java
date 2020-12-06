import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Arrays.*;

/**
 * AOC Day5
 * Seat Assignments
 * 
 * @author Jeff Fitzgerald
 * @version 12/05/2020
 */

public class AOCDay5Part2 {
    
    /**
     * Reads the input file of Seat Codes.
     * 
     * @param filename name of file to read
     * @return an array of input values
     */
    public static String[] readFile(String fileName) {

        ArrayList<String> boardingPasses = new ArrayList<>();
        Scanner inFile = null;

        try {
            inFile = new Scanner(new File(fileName));
            
            while (inFile.hasNext()) {
                boardingPasses.add(inFile.nextLine());
            }

        } catch (IOException e) {
            System.out.println("Error reading input file: " + fileName);
            System.exit(0);

        } finally {
            if (inFile != null) {
                inFile.close();
            }
        }
        // Convert to Array
        String[] boardingPassArray = new String[boardingPasses.size()]; 
        boardingPassArray = boardingPasses.toArray(boardingPassArray); 
        return boardingPassArray;     
    }

    /**
     * Takes in the subsection of the seat code to find the row assignment recursively
     * 
     * @param rowCode substring of seat code for rows
     * @param minRowNumber lower bounds of rows left to find yours
     * @param maxRowNumber upper bounds of rows left to find yours
     * @return the row number
     */
    public static int getSeatRow(String rowCode, int minRowNumber, int maxRowNumber){
        // Lower half
        if(rowCode.charAt(0) == 'F'){
            if(rowCode.length() == 1){
                return minRowNumber; // Last recursion
            } else {
                return getSeatRow(rowCode.substring(1, rowCode.length()), minRowNumber, (maxRowNumber + minRowNumber) / 2);
            }
        } else { // Upper half
            if(rowCode.length() == 1){
                return maxRowNumber; // Last Recursion
            } else {
                return getSeatRow(rowCode.substring(1, rowCode.length()), (minRowNumber + maxRowNumber + 1) / 2, maxRowNumber);
            }
        }
    }

    /**
     * Takes in the subsection of the seat code to find the column assignment recursively
     * 
     * @param columnCode substring of seat code for columns
     * @param minColumnNumber lower bounds of column left to find yours
     * @param maxColumnNumber upper bounds of column left to find yours
     * @return the column number
     */
    public static int getSeatColumn(String columnCode, int minColumnNumber, int maxColumnNumber){
        // Lower half
        if(columnCode.charAt(0) == 'L'){
            if(columnCode.length() == 1){
                return minColumnNumber; // Last recursion
            } else {
                return getSeatColumn(columnCode.substring(1, columnCode.length()), minColumnNumber, (maxColumnNumber + minColumnNumber) / 2);
            }
        } else { // Upper half
            if(columnCode.length() == 1){
                return maxColumnNumber; // Last recursion
            } else {
                return getSeatColumn(columnCode.substring(1, columnCode.length()), (minColumnNumber + maxColumnNumber + 1) / 2, maxColumnNumber);
            }
        }
   }

    /**
     * Creates a unique seat ID base on a specific formula
     * 
     * @param rowNum row number of your seat
     * @param columnNum column number of your seat
     * @return seat ID
     */
    public static int getSeatID(int rowNum, int columnNum){
        return rowNum * 8 + columnNum;
    }

    public static void main(String[] args) {

        String fileName = "AOCDay5Input.txt"; //ShortList //ShortListPart2
        String[] boardingPasses = readFile(fileName);
        int minRowNumber = 0;
        int maxRowNumber = 127;
        int minColumnNumber = 0;
        int maxColumnNumber = 7;
        int seatID;
        int highestSeatID = 0;
        int[] seatIDs = new int[boardingPasses.length];
        int missingSeatID = 0;
        
        for(int i = 0; i < boardingPasses.length; i++) {
            seatID = getSeatID(getSeatRow(boardingPasses[i].substring(0, 7), minRowNumber, maxRowNumber), getSeatColumn(boardingPasses[i].substring(7, boardingPasses[i].length()), minColumnNumber, maxColumnNumber));
            seatIDs[i] = seatID;
            if(seatID > highestSeatID){
                highestSeatID = seatID;
            }
        }
        System.out.println("The highest SeatID is " + highestSeatID);
        // Stores all Seat IDS, sorts, then searches for the missing number of your seat
        Arrays.sort(seatIDs);
        for(int j = 0; j < seatIDs.length - 1; j++){
            if(seatIDs[j + 1] - seatIDs[j] > 1){
                missingSeatID = seatIDs[j] + 1;
            }
        }
        System.out.println("Your missing seatID number is " + missingSeatID);
    }
}
