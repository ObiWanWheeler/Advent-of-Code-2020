package obiwanwheeler.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws FileNotFoundException
    {
	// write your code here
        List<String> foods = new ArrayList<>();
        List<List<String>> allIngredients = new ArrayList<>();
        Map<String, List<String>> allergenList = new HashMap<>();
        Scanner reader = new Scanner(new File("src/obiwanwheeler/database/input.txt"));
        while (reader.hasNextLine())
            foods.add(reader.nextLine());

        Set<String> uniqueIngredients = new HashSet<>();
        for (String f : foods)
        {
            List<String> ingredients = Arrays.asList(f.substring(0, f.indexOf("(")).trim().split(" "));
            allIngredients.add(ingredients);
            uniqueIngredients.addAll(ingredients);
            String[] allergens = f.substring(f.indexOf("(") + 1, f.length() - 1).replace("contains ", "").trim().split(", ");

            for (String allergen : allergens)
            {
                if (allergenList.containsKey(allergen)){
                    List<String> possibleAllergens = allergenList.get(allergen);
                    for (int i = possibleAllergens.size() - 1; i >= 0; i--)
                    {
                        if (!ingredients.contains(possibleAllergens.get(i)))
                        {
                            possibleAllergens.remove(i);
                        }
                    }
                }
                else{
                    List<String> possibleContains = new ArrayList<>(ingredients);
                    allergenList.put(allergen, possibleContains);
                }
            }
        }

        Map<String, String> allergenMapping = new HashMap<>();

        while (allergenList.size() > 0)
        {
            String toRemove = "";
            for (String allergen : allergenList.keySet())
            {
                List<String> ingredients = allergenList.get(allergen);
                if (ingredients.size() == 1){
                    String ing = ingredients.get(0);
                    allergenMapping.put(allergen, ing);
                    toRemove = allergen;
                    for (String allergen2 : allergenList.keySet())
                    {
                        allergenList.get(allergen2).remove(ing);
                    }
                    break;
                }
            }
            if (!toRemove.isEmpty())
                allergenList.remove(toRemove);
            else
                System.out.println("empty");
        }

        List<String> hasNoAllergens = new ArrayList<>(uniqueIngredients);
        for (String allergen : allergenMapping.values())
        {
            hasNoAllergens.remove(allergen);
        }

        int sum = 0;
        for (List<String> ingredients : allIngredients)
        {
            for (String ing : hasNoAllergens)
            {
                if (ingredients.contains(ing))
                    sum++;
            }
        }

        List<String> sortedAs = new ArrayList<>(allergenMapping.keySet());
        sortedAs.sort(String::compareTo);
        StringBuilder sb = new StringBuilder();
        for (String a : sortedAs)
        {
            sb.append(allergenMapping.get(a)).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);

        System.out.println(sb.toString());
        System.out.println(sum);
    }
}
