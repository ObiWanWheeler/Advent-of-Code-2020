package obiwanwheeler.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
	// write your code here
        List<String> groups = readGroupsFromBatch();
        int sum = 0;
        for (String group : groups){
            List<String> people = List.of(group.split(" "));
            String smallest = people.stream().min(Comparator.comparingInt(String::length)).orElse("");
            char[] lettersToCheck = smallest.toCharArray();
            boolean letterIsAlwaysPresent;
            for (char letter : lettersToCheck){
                letterIsAlwaysPresent = true;
                for (String person : people){
                    if (!person.contains(String.valueOf(letter))){
                        letterIsAlwaysPresent = false;
                        break;
                    }
                }
                if (letterIsAlwaysPresent){
                    sum += 1;
                }
            }
        }
        System.out.println(sum);
    }

    private static List<String> readGroupsFromBatch() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/obiwanwheeler/database/input.txt"));
        List<String> groups = new LinkedList<>();
        while (scanner.hasNextLine()) {
            StringBuilder group = new StringBuilder();
            String temp;
            while(!(temp = scanner.nextLine()).equals("") && scanner.hasNextLine()){
                group.append(temp).append(" ");
            }
            groups.add(group.toString());
        }
        return groups;
    }
}
