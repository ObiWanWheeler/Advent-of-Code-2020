package obiwanwheeler.database;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
	// write your code here
        String input = "925176834";
        List<Integer> cups = new LinkedList<>();
        for (char c : input.toCharArray())
        {
            cups.add(Character.getNumericValue(c));
        }
        int currentMax = cups.stream().max(Comparator.naturalOrder()).get();
        for (int i = cups.size(); i < 1000000; i++)
        {
            cups.add(++currentMax);
        }

        int currentCupIndex = 0;
        for (int i = 0; i < 10000000; i++)
        {
            int currentCupLabel = cups.get(currentCupIndex);

            List<Integer> nextCups;
            int start = (currentCupIndex + 1) % cups.size();
            int end = (start + 3) % cups.size();
            if (end > start){
                nextCups = cups.subList(start, end);
            }
            else{
                nextCups = cups.subList(start, cups.size());
                nextCups.addAll(cups.subList(0, end));
            }

            int destinationCupLabel = cups.get(currentCupIndex) - 1;
            while ((nextCups.contains(destinationCupLabel) || !cups.contains(destinationCupLabel)) && destinationCupLabel != cups.get(currentCupIndex)){
                destinationCupLabel--;
                if (destinationCupLabel < 1)
                    destinationCupLabel = 9;
            }

            cups = cups.stream().filter(x -> !nextCups.contains(x)).collect(Collectors.toList());
            int destinationCupIndex = cups.indexOf(destinationCupLabel);
            cups.addAll(destinationCupIndex + 1, nextCups);

            currentCupIndex = cups.indexOf(currentCupLabel) + 1;
            if (currentCupIndex >= input.length())
                currentCupIndex = 0;

            System.out.println(i + 1);
        }

        String endCupsStr = Arrays.toString(cups.toArray());
        String part1 = "";
        part1 += endCupsStr.substring(endCupsStr.indexOf('1') + 1);
        part1 += endCupsStr.substring(0, endCupsStr.indexOf('1'));
        part1 = part1.replace("[", "").replace("]", "").replace(" ", "").replace(",", "");
        System.out.println(part1);

        int part2Cup1 = cups.get(cups.indexOf(1) + 1);
        int part2Cup2 = cups.get(cups.indexOf(1) + 2);
        System.out.println("Part 2: " + (part2Cup1 * part2Cup2));
    }
}
