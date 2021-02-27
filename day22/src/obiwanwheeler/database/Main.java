package obiwanwheeler.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException
    {
	// write your code here
        Scanner reader = new Scanner(new File("src/obiwanwheeler/database/input.txt"));
        reader.nextLine();
        Queue<Integer> p1Deck = new LinkedList<>();
        Queue<Integer> p2Deck = new LinkedList<>();
        getDecks(reader, p1Deck, p2Deck);

        while (p1Deck.size() > 0 && p2Deck.size() > 0){
            int p1Card = p1Deck.element();
            int p2Card = p2Deck.element();

            if (p1Card > p2Card){
                p1Deck.add(p1Card);
                p1Deck.add(p2Card);
            }
            else{
                p2Deck.add(p2Card);
                p2Deck.add(p1Card);
            }
        }

        int score;
        if (p1Deck.size() > 0){
            score = calculateScore(p1Deck);
        }
        else{
            score = calculateScore(p2Deck);
        }
        System.out.println("Part 1: " + score);
    }

    private static int calculateScore(Queue<Integer> p1Deck)
    {
        int score = 0;
        int multiplier = p1Deck.size();
        while (!p1Deck.isEmpty())
        {
            score += p1Deck.poll() * multiplier;
            multiplier--;
        }
        return score;
    }

    private static void getDecks(Scanner reader, Queue<Integer> p1Deck, Queue<Integer> p2Deck)
    {
        String line;
        while (!(line = reader.nextLine()).isEmpty())
            p1Deck.add(Integer.parseInt(line));
        reader.nextLine();
        while (reader.hasNextLine())
            p2Deck.add(Integer.parseInt(reader.nextLine()));
    }
}
