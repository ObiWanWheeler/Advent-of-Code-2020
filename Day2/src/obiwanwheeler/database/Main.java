package obiwanwheeler.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        // write your code here
        File input = new File("src/obiwanwheeler/database/input.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(input));
        int numberOfValidPasswords = 0;
        String line;
        while((line = bufferedReader.readLine()) != null){
            String[] splitLine = line.split(" ");
            char specifiedChar = splitLine[1].toCharArray()[0];
            //long occurrencesOfChar = (splitLine[2].chars().filter(ch -> ch == specifiedChar).count());
            int[] indices = Stream.of(splitLine[0].split("-")).mapToInt(Integer :: parseInt).toArray();
            int lowerIndex = indices[0] - 1;
            int upperIndex = indices[1] - 1;
            char[] passwordChars = splitLine[2].toCharArray();
            if (passwordChars[lowerIndex] == specifiedChar && passwordChars[upperIndex] != specifiedChar
                || passwordChars[lowerIndex] != specifiedChar && passwordChars[upperIndex] == specifiedChar){
                numberOfValidPasswords++;
            }
            //if(occurrencesOfChar >= lowerBound && occurrencesOfChar <= upperBound){
            //    numberOfValidPasswords++;
            //}
        }
        System.out.println(numberOfValidPasswords);
    }
}
