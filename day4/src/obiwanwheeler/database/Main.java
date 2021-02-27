package obiwanwheeler.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
	// write your code here
        List<String> passports = readPassportsFromBatch();

        int numberOfValidPassports = 0;
        for (String passport : passports){
            if (isValid(passport)){
                numberOfValidPassports++;
            }
            else{
                System.out.println(passport);
                System.out.println("invalid");
            }
        }
        System.out.println(numberOfValidPassports + " valid passports");
    }

    private static List<String> readPassportsFromBatch() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/obiwanwheeler/database/input.txt"));
        List<String> passports = new LinkedList<>();
        while (scanner.hasNextLine()) {
            StringBuilder infoLine = new StringBuilder();
            String temp;
            while(!(temp = scanner.nextLine()).equals("") && scanner.hasNextLine()){
                infoLine.append(temp).append(" ");
            }
            passports.add(infoLine.toString());
        }
        return passports;
    }

    private static boolean isValid(String passport){
        boolean initialCheck = passport.contains("byr:") && passport.contains("iyr:") && passport.contains("eyr:") && passport.contains("hgt:") &&
                passport.contains("hcl:") && passport.contains("ecl:") && passport.contains("pid:");
        if (!initialCheck){
            return false;
        }
        String[] components = passport.split(" ");
        Map<String, String> componentsMap = new HashMap<>();
        for (String component : components){
            String[] pair = component.split(":");
            componentsMap.put(pair[0], pair[1]);
        }
        boolean byrCheck = componentsMap.get("byr").length() == 4 &&
                (Integer.parseInt(componentsMap.get("byr")) >= 1920 && Integer.parseInt(componentsMap.get("byr")) <= 2020);
        boolean iyrCheck = componentsMap.get("iyr").length() == 4 &&
                (Integer.parseInt(componentsMap.get("iyr")) >= 2010 && Integer.parseInt(componentsMap.get("iyr")) <= 2020);
        boolean eyrCheck = componentsMap.get("eyr").length() == 4 &&
                (Integer.parseInt(componentsMap.get("eyr")) >= 2020 && Integer.parseInt(componentsMap.get("eyr")) <= 2030);

        boolean hgtCheck;
        String hgtComponent = componentsMap.get("hgt");
        if (hgtComponent.contains("cm")){
            int hgt = Integer.parseInt(hgtComponent.replace("cm", ""));
            hgtCheck = hgt >= 150 && hgt <= 193;
        }
        else if (hgtComponent.contains("in")){
            int hgt = Integer.parseInt(hgtComponent.replace("in",""));
            hgtCheck = hgt >= 59 && hgt <= 76;
        }
        else{
            hgtCheck = false;
        }

        boolean hclCheck;
        String hclComponent = componentsMap.get("hcl");
        if (hclComponent.charAt(0) != '#'){
            hclCheck = false;
        }else{
            hclComponent = hclComponent.replace("#", "");
            if (hclComponent.length() != 6){
                hclCheck = false;
            }else hclCheck = hclComponent.matches("[abcdef0123456789]+");
        }

        List<String> allowedECLs = List.of("amb","blu","brn","gry","grn","hzl","oth");
        boolean eclCheck = allowedECLs.contains(componentsMap.get("ecl"));

        boolean pidCheck = componentsMap.get("pid").length() == 9;

        return byrCheck && iyrCheck && eyrCheck && hgtCheck && hclCheck && eclCheck && pidCheck;
    }
}
