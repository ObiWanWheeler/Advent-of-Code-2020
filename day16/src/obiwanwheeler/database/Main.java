package obiwanwheeler.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
	// write your code here
        Scanner reader = new Scanner(new File("src/obiwanwheeler/database/input.txt"));

        List<String> rules = new ArrayList<>();
        List<Range> ruleRanges = new ArrayList<>();

        String line;
        while (!(line = reader.nextLine()).isBlank())
            rules.add(line);

        for (String rule : rules) {
            String name = rule.split(":")[0];
            String[] allRanges = rule.trim().split(":")[1].split("or");

            String[] leftStr = allRanges[0].trim().split("-");
            String[] rightStr = allRanges[1].trim().split("-");

            int leftBottom = Integer.parseInt(leftStr[0]);
            int leftTop = Integer.parseInt(leftStr[1]);
            int rightBottom = Integer.parseInt(rightStr[0]);
            int rightTop = Integer.parseInt(rightStr[1]);

            ruleRanges.add(new Range(leftBottom, leftTop, rightBottom, rightTop, name));
        }

        String myTicket = reader.nextLine();
        reader.nextLine();

        List<String> nearbyTickets = new ArrayList<>();
        while (reader.hasNextLine()) {
            nearbyTickets.add(reader.nextLine());
        }

        int sum = 0;

        for (int i  = 0; i < nearbyTickets.size(); i ++) {
            String[] fields = nearbyTickets.get(i).split(",");

            for (String field : fields) {
                boolean fieldInvalid = true;
                int fieldInt = Integer.parseInt(field);
                for (Range range : ruleRanges) {
                    //isn't valid for any rule
                    if (isInRange(fieldInt, range)){
                        fieldInvalid = false;
                        break;
                    }
                }
                if (fieldInvalid){
                    sum += fieldInt;
                    nearbyTickets.remove(i);
                }
            }
        }
        System.out.println(sum);

        nearbyTickets.add(myTicket);

        List<Integer> possiblePositions = new ArrayList<>();
        for (int i = 0; i < nearbyTickets.get(0).split(",").length; i++)
            possiblePositions.add(i);

        boolean allMapped = false;
        while (!allMapped){
            for (Range ruleRange : ruleRanges) {
                int position;
                List<Integer> temp = new ArrayList<>(possiblePositions);
                List<Integer> invalidPos = new ArrayList<>();
                //checks if field is rule is valid for each position
                for (int i = 0; i < temp.size(); i++) {
                    for (String ticket : nearbyTickets) {
                        String[] fields = ticket.split(",");
                        //if this position doesn't work for this ticket, it won't work for any
                        if (!isInRange(Integer.parseInt(fields[i]), ruleRange)){
                            if (possiblePositions.contains(i)){
                                invalidPos.add(i);
                                break;
                            }
                        }
                    }
                }
                temp.removeAll(invalidPos);
                if (temp.size() == 1){
                    position = temp.get(0);
                    possiblePositions.remove(temp.get(0));
                    ruleRange.mapped = true;
                    ruleRange.position = position;
                }
            }

            if (ruleRanges.stream().allMatch(r -> r.mapped))
                allMapped = true;
        }
        ruleRanges.forEach(r -> System.out.println(r.name + " " + (r.position + 1)));
    }

    private static boolean isInRange(int field, Range range){
        return (field >= range.min1 && field <= range.max1) || (field >= range.min2 && field <= range.max2);
    }

    private static class Range {

        boolean mapped = false;
        int position = 999;
        int min1, max1, min2, max2;
        String name;

        public Range(int min1, int max1, int min2, int max2, String name){
            this.max1 = max1;
            this.min1 = min1;
            this.max2 = max2;
            this.min2 = min2;
            this.name = name;
        }
    }
}
