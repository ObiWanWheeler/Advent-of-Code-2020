package obiwanwheeler.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    //19,0,5,1,10,13
    public static void main(String[] args) {
	// write your code here
        List<Integer> input = new ArrayList<>(Arrays.asList(19,0,5,1,10,13));
        int[] history = new int[30000000];
        int turn = 1;
        int last = 0;
        //populating history with input
        for (int n : input) {
            last = n;
            history[last] = turn++;
        }
        while (turn <= 30000000) {
            int currentNum = history[last];
            int n = currentNum == 0 ? 0 : turn - currentNum - 1;
            history[last] = turn++ - 1;
            last = n;
        }
        System.out.println(last);
    }
}
