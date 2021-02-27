package obiwanwheeler.database;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        File input = new File("src/obiwanwheeler/database/input.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(input));
        List<Integer>  numbers = new ArrayList<>();
        String line;
        while((line = bufferedReader.readLine()) != null){
            numbers.add(Integer.parseInt(line));
        }
        int answer = 0;
        for (int number1 : numbers){
            for (int number2 : numbers){
                for (int number3 : numbers){
                    if (number1 + number2 + number3 == 2020){
                        answer = number1 * number2 * number3;
                    }
                }
            }
        }
        System.out.println(answer);
    }
}
