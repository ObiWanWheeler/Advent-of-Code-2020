package obiwanwheeler.database;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        File inputFile = new File("src/obiwanwheeler/database/input.txt");
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        List<String> lines = new LinkedList<>();
        String line;
        while ((line = reader.readLine()) != null){
            lines.add(line);
        }
        System.out.println("this is the answer : " + (doTheThing(1,1, lines) * doTheThing(3 , 1, lines) * doTheThing(5, 1, lines)
                * doTheThing(7 ,1 , lines) * doTheThing(1 , 2 , lines)));
    }

    private static long doTheThing(int xInc, int yInc, List<String> lines){
        int x= 0, y = 0, treesHit = 0;
        while (y < lines.size() - 1) {
            x += xInc;
            y += yInc;
            System.out.println(x);
            System.out.println(y);
            if (lines.get(0).length() <= x) {
                x -= lines.get(0).length();
            }
            if (lines.get(y).charAt(x) == '#') {
                treesHit++;
            }
        }
        System.out.println("this many trees " + treesHit);
        return treesHit;
    }
}
