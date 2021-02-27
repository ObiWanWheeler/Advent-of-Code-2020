package obiwanwheeler.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
	// write your code here
        int rambleSize = 25;
        Scanner scanner = new Scanner(new File("src/obiwanwheeler/database/input.txt"));
        Queue<Long> usableNums = new LinkedList<>();
        for (int i = 0; i < rambleSize; i++) {
            usableNums.add(Long.parseLong(scanner.nextLine()));
        }
        Queue<Long> remainingNums = new LinkedList<>();
        while (scanner.hasNextLine()){
            remainingNums.add(Long.parseLong(scanner.nextLine()));
        }
        long faultyNum;
        while (true){
            long desiredNum = remainingNums.remove();
            if (!canBeMade(desiredNum, usableNums)){
                System.out.println(desiredNum);
                faultyNum = desiredNum;
                break;
            }
            usableNums.remove();
            usableNums.add(desiredNum);
        }

        List<Long> nums = new ArrayList<>();
        Scanner reader = new Scanner(new File("src/obiwanwheeler/database/input.txt"));
        while (reader.hasNextLine()){
            nums.add(Long.parseLong(reader.nextLine()));
        }

        long upper = 0, lower = 0, sum;
        List<Long> contiguousRange;

        for (int startIndex = 0; startIndex < nums.size(); startIndex++){
            contiguousRange = new ArrayList<>();
            long num = nums.get(startIndex);
            contiguousRange.add(num);
            sum = num;
            for (int k = startIndex + 1; k < nums.size(); k++){
                long num2 = nums.get(k);
                sum += num2;
                contiguousRange.add(num2);
                if (sum == faultyNum){
                    System.out.println("line 1: " + (startIndex + 1));
                    System.out.println("line 2 : " + (k + 1));
                    System.out.println("num 1 : " + num);
                    System.out.println("num 2 : " + num2);
                    System.out.println(sum);
                    System.out.println(faultyNum);
                    upper = Collections.max(contiguousRange);
                    lower = Collections.min(contiguousRange);
                    break;
                }
                else if (sum > faultyNum){
                    break;
                }
            }
        }
        System.out.println("answer : " + (upper + lower));
    }

    private static boolean canBeMade(long desiredNum, Queue<Long> availableNums){
        for (long num1 : availableNums){
            for (long num2 : availableNums){
                if (num1 + num2 == desiredNum && num1 != num2){
                    return true;
                }
            }
        }
        return false;
    }
}
