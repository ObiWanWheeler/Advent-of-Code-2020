package obiwanwheeler.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
	// write your code here
        Scanner scanner = new Scanner(new File("src/obiwanwheeler/database/input.txt"));
        List<String> input = new ArrayList<>();
        while (scanner.hasNextLine()){
            input.add(scanner.nextLine());
        }
        Vector2 ship = new Vector2(0 ,0);
        Vector2 waypoint = new Vector2(10, 1);
        for (String s : input) {
            if (s.isEmpty())
                continue;

            char command = s.charAt(0);
            int value = Integer.parseInt(s.substring(1));
            System.out.println("command : " + s);

            switch (command){
                case 'N' : waypoint.y += value;
                    break;
                case 'E' : waypoint.x += value;
                    break;
                case 'S' : waypoint.y -= value;
                    break;
                case 'W': waypoint.x -= value;
                    break;
                case 'F':
                    for (int i = 0; i < value; i++) {
                        Vector2 shipToWP = new Vector2(waypoint.x - ship.x, waypoint.y - ship.y);
                        ship.x = waypoint.x;
                        ship.y = waypoint.y;
                        waypoint.x = ship.x + shipToWP.x;
                        waypoint.y = ship.y + shipToWP.y;
                    }
                    break;
                case 'R':
/*
                    int turnAmount = value / 90;
                    int nextIndex = directions.indexOf(currentQuadrant) + turnAmount;
                    if (nextIndex >= directions.size()){
                        nextIndex = turnAmount - ((directions.size() - 1) - directions.indexOf(currentQuadrant)) - 1;
                    }
                    currentQuadrant = directions.get(nextIndex);
*/
                    value *= -1;
                    Vector2 translatedWP = new Vector2(waypoint.x - ship.x, waypoint.y - ship.y);
                    int cosine = (int)Math.round(Math.cos(Math.toRadians(value)));
                    int sine = (int)Math.round(Math.sin(Math.toRadians(value)));
                    waypoint.x = ((translatedWP.x * cosine) - (translatedWP.y * sine)) + ship.x;
                    waypoint.y = ((translatedWP.y * cosine) + (translatedWP.x * sine)) + ship.y;

                    break;
                case 'L':
/*
                    turnAmount = value / 90;
                    nextIndex = directions.indexOf(currentQuadrant) - turnAmount;
                    if (nextIndex < 0){
                        nextIndex =  directions.size() - (turnAmount - directions.indexOf(currentQuadrant));
                    }
                    currentQuadrant = directions.get(nextIndex);
*/
                    translatedWP = new Vector2(waypoint.x - ship.x, waypoint.y - ship.y);
                    cosine = (int)Math.round(Math.cos(Math.toRadians(value)));
                    sine = (int)Math.round(Math.sin(Math.toRadians(value)));
                    waypoint.x = ((translatedWP.x) * cosine - (translatedWP.y * sine)) + ship.x;
                    waypoint.y = ((translatedWP.y) * cosine + (translatedWP.x * sine)) + ship.y;
                    
                    break;
            }
            System.out.println("waypoint : ");
            System.out.println("x : " + waypoint.x + " y : " + waypoint.y);
            System.out.println("ship : ");
            System.out.println("x : " + ship.x + " y : " + ship.y);
        }
        System.out.println(Math.abs(ship.y) + Math.abs(ship.x));
    }
}

class Vector2 {
    public Vector2(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public long x;
    public long y;
}
