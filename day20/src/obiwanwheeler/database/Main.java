package obiwanwheeler.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
	// write your code here
        Scanner reader = new Scanner(new File("src/obiwanwheeler/database/input.txt"));
        Map<Integer, List<String>> tilesMap = new HashMap<>();
        while (reader.hasNextLine()){
            Integer currentID = Integer.parseInt(reader.nextLine().split(" ")[1].replace(":",""));
            List<String> lines = new ArrayList<>();
            String line;
            while (reader.hasNextLine() && !(line = reader.nextLine()).isBlank()){
                lines.add(line);
            }
            tilesMap.put(currentID, lines);
        }

        List<Integer> cornerIds = new ArrayList<>();

        for (int id1 : tilesMap.keySet()) {
            int sharedCount = 0;
            List<String> sides1 = getSidesWithRotations(tilesMap.get(id1));
            for (int id2 : tilesMap.keySet()) {
                if (id1 == id2)
                    continue;

                List<String> sides2 = getSidesWithRotations(tilesMap.get(id2));

                if (ArraysShareVal(sides1, sides2)){
                    //one side tile can only ever share one side with another
                    sharedCount++;
                }
            }
            if (sharedCount == 2)
                cornerIds.add(id1);
        }

        long product = cornerIds.stream().mapToLong(x -> x).reduce(1, (a, b) -> a * b);
        System.out.println("product of corner id = " + product);
    }

    private static List<String> getSidesWithRotations(List<String> tile){
        StringBuilder top = new StringBuilder(tile.get(0));
        StringBuilder bottom = new StringBuilder(tile.get(tile.size() - 1));
        StringBuilder left = new StringBuilder();
        StringBuilder right = new StringBuilder();
        for (String s : tile) {
            left.append(s.charAt(0));
            right.append(s.charAt(s.length() - 1));
        }
        List<String> sides = new ArrayList<>();
        sides.add(top.toString());
        sides.add(top.reverse().toString());
        sides.add(right.toString());
        sides.add(right.reverse().toString());
        sides.add(bottom.toString());
        sides.add(bottom.reverse().toString());
        sides.add(left.toString());
        sides.add(left.reverse().toString());
        return sides.stream().distinct().collect(Collectors.toList());
    }

    private static boolean ArraysShareVal(List<String> list1, List<String> list2){
        Collection<String> set = new HashSet<>(list1);
        for (String s : list2){
            if (set.contains(s))
                return true;
        }
        return false;
    }

    private static Set<String> getIntersection(List<String> list1, List<String> list2){
        return list1.stream().distinct().filter(list2::contains).collect(Collectors.toSet());
    }
}
