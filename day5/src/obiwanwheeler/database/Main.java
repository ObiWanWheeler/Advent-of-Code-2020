package obiwanwheeler.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        // write your code here
        List<Seat> seats = getSeatsFromBatch();
        seats.sort(Comparator.comparingInt(Seat::getRow));
        int seatColumn, seatRow;
        for (int i = 0; i < 100; i++) {
            int currentRow = i;
            List<Seat> seatsInRowI = seats.stream().filter(s -> s.row == currentRow).collect(Collectors.toList());
            List<Integer> columnsPresent = new ArrayList<>();
            for (Seat seat : seatsInRowI){
                columnsPresent.add(seat.column);
            }
            Collections.sort(columnsPresent);
            int lastColumn = 0;

            for (Integer column : columnsPresent){
                if (column - lastColumn != 1){
                    //a gap has been found between the two columns
                    seatColumn = (column + lastColumn) / 2;
                    seatRow = currentRow;
                    System.out.println("my seat id: " + ((seatRow * 8) + seatColumn));
                    break;
                }
            }
        }

    }

    private static class Seat{
        public int getRow() {
            return row;
        }

        int row, column;

        public Seat(int row, int column){
            this.row = row;
            this.column = column;
        }
    }

    private static List<Seat> getSeatsFromBatch() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/obiwanwheeler/database/input.txt"));
        List<String> seatDescriptions = new ArrayList<>();
        List<Seat> seats = new ArrayList<>();
        while (scanner.hasNextLine()){
            seatDescriptions.add(scanner.nextLine());
        }
        int highestID = 0;
        for (String seatDesc : seatDescriptions){
            int row, column;
            char[] rowInfo = seatDesc.substring(0, 7).toCharArray();
            char[] columnInfo = seatDesc.substring(7, 10).toCharArray();
            int upper = 127, lower = 0, mid;
            for (char info : rowInfo){
                mid = (upper + lower) / 2;
                if (info == 'B'){
                    lower = mid + 1;
                }
                else if (info == 'F') {
                    upper = mid;
                }
            }
            row = lower;

            upper = 7;
            lower = 0;
            for (char info : columnInfo){
                mid = (upper + lower) / 2;
                if (info == 'R'){
                    lower = mid + 1;
                }
                else if (info == 'L'){
                    upper = mid;
                }
            }
            column = lower;
            int id = (row * 8) + column;
            if (id > highestID){
                highestID = id;
            }
            seats.add(new Seat(row, column));
        }
        return seats;
    }
    //6 , 8 : 56
    //70 , 2 : 562
}
