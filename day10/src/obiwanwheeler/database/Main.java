package obiwanwheeler.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
	// write your code here
        Scanner scanner = new Scanner(new File("src/obiwanwheeler/database/input.txt"));
        Set<Integer> input = new TreeSet<>();
        while (scanner.hasNextLine()){
            input.add(Integer.parseInt(scanner.nextLine()));
        }
        input.add(0);
        input.add(input.stream().max(Comparator.naturalOrder()).isPresent() ? input.stream().max(Comparator.naturalOrder()).get() + 3 : 141);

        Object[] adapters = input.toArray();

        int[] sequences = new int[6];
        int currentSequence = 1;
        int count = 0;
        while (count <= adapters.length - 2) {
            int currentAdapter = (int) adapters[count];
            int nextAdapter = (int) adapters[count + 1];
            if (nextAdapter - currentAdapter == 1) {
                currentSequence++;
            } else {
                sequences[currentSequence]++;
                currentSequence = 1;
            }
            count++;
        }
        sequences[currentSequence]++;

        long answer = 1;
        answer *= Math.pow(2, sequences[3]);
        answer *= Math.pow(4, sequences[4]);
        answer *= Math.pow(7, sequences[5]);

        System.out.println("answer : " + answer);
    }

    private static boolean adapterExists(List<Integer> adapters, int adapter){
        return adapters.stream().anyMatch(x -> x == adapter);
    }

    private static boolean routeIsUnique(List<List<Integer>> routesTaken, List<Integer> currentRoute){
        return routesTaken.stream().noneMatch(l -> l.equals(currentRoute));
    }
}
