package obiwanwheeler.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
	// write your code here
        Scanner scanner = new Scanner(new File("src/obiwanwheeler/database/input.txt"));
        List<String> rules = new ArrayList<>();
        while (scanner.hasNextLine()){
            rules.add(scanner.nextLine());
        }
        List<String> currentBagNames;
        List<String> addedBags = new ArrayList<>();
        addedBags.add("shiny gold");
        List<String> allowedBags = new ArrayList<>();
        boolean stillMoreBags = true;
        while (stillMoreBags){
            stillMoreBags = false;
            currentBagNames = addedBags;
            addedBags = new ArrayList<>();
            //for each of the bag rules
            for (String bagRule : rules) {
                //the bag the rule pertains to
                String bagName = bagRule.split(" bags contain ")[0];
                for (String name : currentBagNames) {
                    //if this bag rule contains one of the bags we're interested in
                    if (bagRule.contains(name) && !bagName.equals(name)) {
                        addedBags.add(bagName);
                        allowedBags.add(bagName);
                        stillMoreBags = true;
                    }
                }
            }
        }
        allowedBags = allowedBags.stream().distinct().collect(Collectors.toList());
        System.out.println(allowedBags.size() + " bags allowed");

        Scanner reader = new Scanner(new File("src/obiwanwheeler/database/input.txt"));
        List<String> input = new ArrayList<>();

        while (reader.hasNextLine())
            input.add(reader.nextLine());

        int bags = 0;
        Stack<String> bagsToProcess = new Stack<>();
        Stack<Integer> bagQuantities = new Stack<>();
        bagsToProcess.add("shiny gold");
        bagQuantities.add(1);

        while (!bagsToProcess.isEmpty()) {
            String bag = bagsToProcess.pop();
            int num = bagQuantities.pop();

            int count = 0;
            while (!input.get(count).startsWith(bag))
                count++;

            String in = input.get(count);
            String[] contents = in.substring(in.indexOf("contain") + 8)
                    .replace(".", "").split(", ");
            if (!contents[0].equals("no other bags")) {
                for (String c : contents) {
                    String b = c.substring(c.indexOf(" ")).replace("bags",
                            "").trim();
                    int n = Integer.parseInt(c.substring(0, c.indexOf(" ")).trim());
                    bagsToProcess.add(b);
                    bagQuantities.add(n * num);
                    bags += n * num;
                }
            }
        }
        System.out.println(bags + " bags required");
    }
}
