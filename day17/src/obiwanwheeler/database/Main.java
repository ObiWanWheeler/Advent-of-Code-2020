package obiwanwheeler.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
	// write your code here
        char[][][] space = new char[20][20][20];
        for (char[][] chars : space) {
            for (char[] aChar : chars) {
                Arrays.fill(aChar, '.' );
            }
        }
        int y = 5;
        int z = 5;

        getInput1(space, y, z);

        doLoop1(space);

        char[][][][] space2 = getInput2();
        doLoop2(space2);

    }

    private static void doLoop1(char[][][] space) {
        for (int cycle = 1; cycle <= 6; cycle++) {
            char[][][] copy = new char[space.length][space.length][space.length];
            for (int z = 0; z < space.length; z++) {
                for (int y = 0; y < space.length; y++) {
                    for (int x = 0; x < space.length; x++) {
                        int activeNeighbors = countNeighbors1(space, z, y, x);
                        if (space[z][y][x] == '#') {
                            if (activeNeighbors == 2 || activeNeighbors == 3) {
                                copy[z][y][x] = '#';
                            } else {
                                copy[z][y][x] = '.';
                            }
                        } else if (space[z][y][x] == '.') {
                            if (activeNeighbors == 3) {
                                copy[z][y][x] = '#';
                            } else {
                                copy[z][y][x] = '.';
                            }
                        }
                    }
                }
            }
            space = copy;
        }

        int active = 0;
        for (char[][] chars : space) {
            for (int y = 0; y < space.length; y++) {
                for (int x = 0; x < space.length; x++) {
                    if (chars[y][x] == '#') {
                        active++;
                    }
                }
            }
        }

        System.out.println("Active cubes: " + active);
    }

    private static void doLoop2(char[][][][] grid){
        for (int cycle = 1; cycle <= 6; cycle++) {
            char[][][][] copy = new char[grid.length][grid.length][grid.length][grid.length];
            for (int w = 0; w < grid.length; w++) {
                for (int z = 0; z < grid.length; z++) {
                    for (int y = 0; y < grid.length; y++) {
                        for (int x = 0; x < grid.length; x++) {
                            int activeNeighbors = countNeighbors2(grid, w, z, y, x);
                            if (grid[w][z][y][x] == '#') {
                                if (activeNeighbors == 2 || activeNeighbors == 3) {
                                    copy[w][z][y][x] = '#';
                                } else {
                                    copy[w][z][y][x] = '.';
                                }
                            } else if (grid[w][z][y][x] == '.') {
                                if (activeNeighbors == 3) {
                                    copy[w][z][y][x] = '#';
                                } else {
                                    copy[w][z][y][x] = '.';
                                }
                            }
                        }
                    }
                }

            }
            grid = copy;
        }

        int active = 0;
        for (char[][][] chars : grid) {
            for (int z = 0; z < grid.length; z++) {
                for (int y = 0; y < grid.length; y++) {
                    for (int x = 0; x < grid.length; x++) {
                        if (chars[z][y][x] == '#') {
                            active++;
                        }
                    }
                }
            }
        }

        System.out.println("Active cubes: " + active);
    }

    private static char[][][][] getInput2() {
        List<String> input;
        Scanner reader = null;
        char[][][][] grid;
        try {
            reader = new Scanner(new File("src/obiwanwheeler/database/input.txt"));
        } catch (Exception e) {
            System.out.println("File not found");
        }
        input = new ArrayList<>();

        while (reader.hasNext()) {
            input.add(reader.nextLine());
        }
        grid = new char[input.size() + 20][input.size() + 20][input.size() + 20][input.size() + 20];
        for (int w = 0; w < grid.length; w++) {
            for (int z = 0; z < grid.length; z++) {
                for (int y = 0; y < grid.length; y++) {
                    for (int x = 0; x < grid.length; x++) {
                        grid[w][z][y][x] = '.';
                    }
                }
            }
        }

        for (int z = 0; z < grid.length; z++) {
            for (int y = 0; y < input.size(); y++) {
                for (int x = 0; x < input.get(0).length(); x++) {
                    char c = input.get(y).charAt(x);
                    grid[grid.length / 2 - input.size() / 2][grid.length / 2
                            - input.size() / 2][grid.length / 2 - input.size() / 2 + y][grid.length / 2
                            + x] = c;
                }
            }
        }
        return grid;
    }

    private static int countNeighbors1(char[][][] grid, int z, int y, int x) {
        int count = 0;

        for (int zi = -1; zi <= 1; zi++) {
            for (int yi = -1; yi <= 1; yi++) {
                for (int xi = -1; xi <= 1; xi++) {
                    if (z + zi >= 0 && y + yi >= 0 && x + xi >= 0) {
                        if (z + zi < grid.length && y + yi < grid.length && x + xi < grid.length) {
                            if (!(zi == 0 && yi == 0 && xi == 0)) {
                                if (grid[z + zi][y + yi][x + xi] == '#') {
                                    count++;
                                }
                            }
                        }
                    }
                }
            }
        }

        return count;
    }

    private static void getInput1(char[][][] space, int y, int z) throws FileNotFoundException {
        Scanner reader = new Scanner(new File("src/obiwanwheeler/database/input.txt"));
        while (reader.hasNextLine()){
            String line = reader.nextLine();
            int x = 5;
            for (char c : line.toCharArray()) {
                space[x][y][z] = c;
                x++;
            }
            y++;
        }
    }

    private static int countNeighbors2(char[][][][] grid, int w, int z, int y, int x) {
        int count = 0;

        for (int wi = -1; wi <= 1; wi++) {
            for (int zi = -1; zi <= 1; zi++) {
                for (int yi = -1; yi <= 1; yi++) {
                    for (int xi = -1; xi <= 1; xi++) {
                        if (w + wi >= 0 && z + zi >= 0 && y + yi >= 0 && x + xi >= 0) {
                            if (w + wi < grid.length && z + zi < grid.length && y + yi < grid.length
                                    && x + xi < grid.length) {
                                if (!(wi == 0 && zi == 0 && yi == 0 && xi == 0)) {
                                    if (grid[w + wi][z + zi][y + yi][x + xi] == '#') {
                                        count++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return count;
    }
}
