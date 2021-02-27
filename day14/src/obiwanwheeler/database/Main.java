package obiwanwheeler.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
	// write your code here
        part1();
        part2();
    }

    private static void part1() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/obiwanwheeler/database/input.txt"));
        String mask = "";
        char[] maskChars = mask.toCharArray();
        long[] memory = new long[100000];
        Arrays.fill(memory, 0L);

        while (scanner.hasNextLine()) {
            String nextLine = scanner.nextLine();
            String[] splitLine = nextLine.split("=");
            splitLine[0] = splitLine[0].trim();
            splitLine[1] = splitLine[1].trim();

            if (splitLine[0].equals("mask")){
                mask = splitLine[1];
                maskChars = mask.toCharArray();
            }
            else{
                int memoryAddress = Integer.parseInt(splitLine[0].substring(4, splitLine[0].length() - 1));
                long decimal = Long.parseLong(splitLine[1]);
                StringBuilder binary = new StringBuilder(Long.toBinaryString(decimal));
                while (binary.length() != mask.length()) {
                    binary.insert(0, "0");
                }

                for (int i = 0; i < binary.length(); i++) {
                    if (maskChars[i] != 'X') {
                        binary.setCharAt(i, maskChars[i]);
                    }
                }

                decimal = Long.parseLong(binary.toString(), 2);
                memory[memoryAddress] = decimal;
            }
        }

        long sum = 0;
        for (long x : memory) {
            sum += x;
        }
        System.out.println(sum);
    }

    public static final String BINARY_ZERO_LEN_32 = "000000000000000000000000000000000000";

    public static void part2() throws FileNotFoundException {
        List<String> lines = new ArrayList<>();
        Scanner scanner = new Scanner(new File("src/obiwanwheeler/database/input.txt"));
        while (scanner.hasNextLine())
            lines.add(scanner.nextLine());

        HashMap<String, String> memory = new HashMap<>();
        String mask = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
        for (String line : lines) {
            if (line.startsWith("mask = ")) {
                mask = line.replace("mask = ", "");
            } else {
                String[] memoryValue = line.split(" = ");
                String memoryAddress = memoryValue[0].replace("mem[", "").replace("]", "");
                String binaryMemoryAddress = Integer.toBinaryString(Integer.parseInt(memoryAddress));
                String paddedMemoryAddress = BINARY_ZERO_LEN_32.substring(binaryMemoryAddress.length()) + binaryMemoryAddress;
                String memoryAddressesMaskApplied = applyMask(paddedMemoryAddress, mask);
                Set<String> memoryAddresses = getPossibleVariations(memoryAddressesMaskApplied);
                String binaryNumber = Integer.toBinaryString(Integer.parseInt(memoryValue[1]));
                String padded = BINARY_ZERO_LEN_32.substring(binaryNumber.length()) + binaryNumber;
                for (String address : memoryAddresses) {
                    memory.put(address, padded);
                }
            }
        }
        System.out.println(memory.keySet().stream().mapToLong(key -> Long.parseLong(memory.get(key), 2)).sum());
    }

    private static String applyMask(String padded, String mask) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i <= mask.length() - 1; i++) {
            if (mask.charAt(i) == 'X') {
                result.append('X');
            } else if (mask.charAt(i) == '1') {
                result.append(1);
            } else {
                result.append(padded.charAt(i));
            }
        }
        return result.toString();
    }

    private static Set<String> getPossibleVariations(String binaryString) {
        Set<String> result = new HashSet<>();
        result.add(binaryString);
        while (true) {
            Set<String> stringToAddToResult = new HashSet<>();
            Set<String> stringToRemove = new HashSet<>();
            for (String stringToProcess : result) {
                if (stringToProcess.contains("X")) {
                    stringToRemove.add(stringToProcess);
                    stringToAddToResult.add(stringToProcess.replaceFirst("X", "1"));
                    stringToAddToResult.add(stringToProcess.replaceFirst("X", "0"));
                    break;
                }
            }
            result.addAll(stringToAddToResult);
            result.removeAll(stringToRemove);
            int numberOfResultsWithOutX = 0;
            for (String string : result) {
                if (!string.contains("X")) {
                    numberOfResultsWithOutX++;
                }
            }
            if (numberOfResultsWithOutX == result.size()) {
                break;
            }
        }
        return result;
    }

}