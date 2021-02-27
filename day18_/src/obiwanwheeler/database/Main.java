package obiwanwheeler.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        List<String> input = getInput();

        long sum = 0;

        for (String equation : input) {
            if (equation.equals("9 + (2 * (5 * 7) * (5 * 7 * 7) * 7 * 4) + 7 * (5 * 9 + (9 * 7 * 7 * 2))"))
                System.out.println();
            System.out.println(equation);
            String eq_ = extractBrackets(equation);
            System.out.println(eq_);
            String answer = evaluateExpression(eq_);
            sum += Long.parseLong(answer);
            System.out.println(answer);
        }

        System.out.println(sum);
    }

    private static String extractBrackets(String equation) {
        String eq_ = String.copyValueOf(equation.toCharArray());
        while (eq_.contains("(") || eq_.contains(")")){
            int openBracketIndex = eq_.indexOf('(');
            int closeBracketIndex = findClosingParen(eq_.toCharArray(), openBracketIndex);
            String bracketExpression = eq_.substring(openBracketIndex + 1, closeBracketIndex);

            if (bracketExpression.contains("(") || bracketExpression.contains(")"))
                bracketExpression = extractBrackets(bracketExpression);

            String evaluatedExpression = evaluateExpression(bracketExpression);
            StringBuilder eqBuilder = new StringBuilder(eq_);
            eqBuilder.delete(openBracketIndex + 1, closeBracketIndex);
            eq_ = eqBuilder.toString();
            eq_ = eq_.replaceFirst("\\(", evaluatedExpression);
            eq_ = eq_.replaceFirst("\\)", "");
        }
        return eq_;
    }

    private static String evaluateExpression(String expression){

        expression = expression.replaceAll(" ", "");
        StringBuilder sb = new StringBuilder(expression);
        String temp = "";

        while (sb.toString().contains("+") || sb.toString().contains("*")){
            if (!sb.toString().contains("+")){
                char operator = findOperator(sb);
                long num1 = Long.parseLong(sb.substring(0, sb.indexOf(String.valueOf(operator))));
                long num2 = getSecondNumAhead(sb, operator);

                temp = String.valueOf(num1 * num2);

                sb.replace(0, String.valueOf(num1).length() + String.valueOf(num2).length() + 1, temp);
            }
            else {
                int operatorIndex = sb.indexOf("+");
                char operator = sb.charAt(operatorIndex);
                long num1 = getFirstNumBehind(sb, operator);
                long num2 = getSecondNumAhead(sb, operator);

                temp = String.valueOf(num1 + num2);

                sb.replace(operatorIndex - String.valueOf(num1).length(), operatorIndex + String.valueOf(num2).length() + 1, temp);
            }
        }

        return sb.toString();
    }

    private static long getFirstNumBehind(StringBuilder sb, char operator) {
        StringBuilder nextNumBuilder = new StringBuilder();
        char currentDigit = ' ';
        int count = 1;
        while (sb.indexOf(String.valueOf(operator)) - count >= 0){
            currentDigit = sb.charAt(sb.indexOf(String.valueOf(operator)) - count);
            if (currentDigit == '+' || currentDigit == '-' || currentDigit == '*')
                break;
            nextNumBuilder.insert(0, currentDigit);
            count++;
        }
        return Long.parseLong(nextNumBuilder.toString());
    }

    private static long getSecondNumAhead(StringBuilder sb, char operator) {
        StringBuilder nextNumBuilder = new StringBuilder();
        char currentDigit = ' ';
        int count = 1;
        while (sb.indexOf(String.valueOf(operator)) + count < sb.length()){
            currentDigit = sb.charAt(sb.indexOf(String.valueOf(operator)) + count);
            if (currentDigit == '+' || currentDigit == '-' || currentDigit == '*')
                break;
            nextNumBuilder.append(currentDigit);
            count++;
        }
        return Long.parseLong(nextNumBuilder.toString());
    }

    private static char findOperator(StringBuilder expression) {
        char operator = ' ';
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '+' || expression.charAt(i) == '-' || expression.charAt(i) == '*'){
                operator = expression.charAt(i);
                break;
            }
        }
        return operator;
    }

    public static int findClosingParen(char[] text, int openPos) {
        int closePos = openPos;
        int counter = 1;
        while (counter > 0) {
            char c = text[++closePos];
            if (c == '(') {
                counter++;
            }
            else if (c == ')') {
                counter--;
            }
        }
        return closePos;
    }

    private static List<String> getInput() {
        List<String> input = new ArrayList<>();
        try {
            Scanner reader = new Scanner(new File("src/obiwanwheeler/database/input.txt"));
            while (reader.hasNextLine())
                input.add(reader.nextLine());
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        }
        return input;
    }
}
