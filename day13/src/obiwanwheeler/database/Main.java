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
        int earliestAvailable = Integer.parseInt(scanner.nextLine());
        List<Integer> busIDs = new ArrayList<>();
        String input = scanner.nextLine();
        String[] ids = input.replaceAll("x,", "").split(",");
        List.of(ids).forEach(s -> busIDs.add(Integer.parseInt(s)));

        int earliestBusID = 0, earliestBusTS = 0, minutesToWait = 999999;

        for (Integer busID : busIDs) {
            int sum = 0;
            //as soon as sum is greater than the earliest we are available, we can get that bus
            while (sum < earliestAvailable) {
                sum += busID;
            }
            int earliestTimeForThisBus = sum;
            int difference = earliestTimeForThisBus - earliestAvailable;
            if (difference < minutesToWait) {
                earliestBusID = busID;
                earliestBusTS = earliestTimeForThisBus;
                minutesToWait = difference;
            }
            System.out.println(earliestAvailable);
            System.out.println("busID " + busID + " arrives at " + earliestTimeForThisBus);
        }

        System.out.println("the earliest bus is " + earliestBusID + " which arrives at " + earliestBusTS +
                " which is " + minutesToWait + " minutes after you can arrive");
        System.out.println(minutesToWait * earliestBusID);

        //part 2

        Scanner reader = new Scanner(new File("src/obiwanwheeler/database/input.txt"));
        reader.nextLine();
        String[] buses = reader.nextLine().split(",");

        ArrayList<Integer> smallNs = new ArrayList<>();
        ArrayList<Integer> smallBs = new ArrayList<>();
        ArrayList<Long> bigNs = new ArrayList<>();
        ArrayList<Integer> xs = new ArrayList<>();

        long N = 1;
        N = findN(buses, smallNs, smallBs, N);

        populateColumns(smallNs, bigNs, xs, N);

        long sum = 0;
        sum = getAnswer(smallNs, smallBs, bigNs, xs, N, sum);
        System.out.println(sum);
    }

    private static long getAnswer(ArrayList<Integer> smallNs, ArrayList<Integer> smallBs, ArrayList<Long> bigNs, ArrayList<Integer> xs, long n, long sum) {
        for (int i = 0; i < smallNs.size(); i++) {
            sum += smallBs.get(i) * bigNs.get(i) * xs.get(i);
        }
        sum %= n;
        return sum;
    }

    private static void populateColumns(ArrayList<Integer> smallNs, ArrayList<Long> bigNs, ArrayList<Integer> xs, long n) {
        for (int i = 0; i < smallNs.size(); i++) {
            bigNs.add(n / smallNs.get(i));
            xs.add(invertMod(bigNs.get(i), smallNs.get(i)));
        }
    }

    private static long findN(String[] buses, ArrayList<Integer> smallNs, ArrayList<Integer> smallBs, long n) {
        for (int i = 0; i < buses.length; i++) {
            if (!buses[i].equals("x")) {
                smallNs.add(Integer.parseInt(buses[i]));
                smallBs.add((-1 * i) % Integer.parseInt(buses[i]) + Integer.parseInt(buses[i]));
                n *= Integer.parseInt(buses[i]);
            }
        }
        return n;
    }

    private static int invertMod(long Ni, int ni) {
        int xi = 1;
        while ((Ni * xi) % ni != 1) {
            xi++;
        }
        return xi;
    }

}
