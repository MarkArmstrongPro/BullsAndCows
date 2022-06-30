package bullscows;

import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final String NUMBERS = "0123456789";
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static String SYMBOLS_BUNCH = NUMBERS + ALPHABET;
    private static String currentSymbolsBunch = "";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int lengthOfCode = 0;
        System.out.println("Please, enter the secret code's length:");
        System.out.print("> ");
        try {
            lengthOfCode = Integer.valueOf(scanner.nextLine());
            if (lengthOfCode < 1) {
                System.out.println("Error: minimum number of possible symbols in the secret code is 1.");
                return;
            }
        } catch (Exception e) {
            System.out.println("Error: \"" + lengthOfCode + "\" isn't a valid number.");
            return;
        }

        System.out.println("Input the number of possible symbols in the code:");
        System.out.print("> ");
        String lengthOfSymbols = "";
        try {
            lengthOfSymbols = scanner.nextLine().trim();
            if (Integer.valueOf(lengthOfSymbols) < lengthOfCode || Integer.valueOf(lengthOfSymbols) > 36) {
                System.out.println("Error: it's not possible to generate a code with a length of "
                        + lengthOfSymbols + " with " + lengthOfCode + " unique symbols.");
                return;
            }
        } catch (Exception e) {
            System.out.println("Error: \"" + lengthOfCode + "\" isn't a valid number.");
            return;
        }

        currentSymbolsBunch = SYMBOLS_BUNCH.substring(0, Integer.valueOf(lengthOfSymbols));

        String secretCode = RandomString(lengthOfCode) + "";
        System.out.println("The secret is prepared: " + "*".repeat(lengthOfCode)
                + " (0-9, a-" + currentSymbolsBunch.charAt(currentSymbolsBunch.length() - 1)
                + ").");
        System.out.println("Okay, let's start a game!");

        int turns = 0;
        String userCode = null;
        boolean isGuessedSecretCode = false;
        while (!isGuessedSecretCode) {
            System.out.println("Turn " + ++turns + ":");
            System.out.print("> ");
            userCode = scanner.nextLine();
            int[] bullsCowsArray = new int[2];
            bullsCowsArray = countBullsCows(secretCode, userCode);

            if (bullsCowsArray[0] > 0 && bullsCowsArray[1] > 0) {
                System.out.println("Grade: "
                        + bullsCowsArray[0] + " " + (bullsCowsArray[0] > 1 ? "bulls" : "bull") + " and "
                        + bullsCowsArray[1] + " " + (bullsCowsArray[1] > 1 ? "cows" : "cow"));
            } else if (bullsCowsArray[0] > 0 && bullsCowsArray[1] <= 0) {
                System.out.println("Grade: "
                        + bullsCowsArray[0] + " " + (bullsCowsArray[0] > 1 ? "bulls" : "bull"));
            } else if (bullsCowsArray[0] <= 0 && bullsCowsArray[1] > 0) {
                System.out.println("Grade: "
                        + bullsCowsArray[1] + " " + (bullsCowsArray[1] > 1 ? "cows" : "cow"));
            } else {
                System.out.println("Grade: None.");
            }
            if (userCode.equals(secretCode)) {
                isGuessedSecretCode = true;
                System.out.println("Congratulations! You guessed the secret code.");
            }
        }
    }

    private static String RandomString(int lengthOfSymbols) {
        StringBuilder secretNumber = new StringBuilder();
        Random random = new Random();
        while (secretNumber.length() != lengthOfSymbols) {
            int currentIndex = random.nextInt(lengthOfSymbols);
            if (secretNumber.toString().contains("" + SYMBOLS_BUNCH.charAt(currentIndex))) {
                continue;
            }
            secretNumber.append(SYMBOLS_BUNCH.charAt(currentIndex));
        }
        return secretNumber.toString();
    }

    private static int[] countBullsCows(String secretCode, String guessCode) {
        char[] secretCodeArray = secretCode.toCharArray();
        char[] guessCodeArray = guessCode.toCharArray();

        int[] bullsCows = new int[2];
        int[] tmpArray = new int[2];

        for (int i = 0; i < secretCode.length(); i++) {
            tmpArray = countBullsCowsWithIndex(secretCodeArray, guessCodeArray[i], i);
            bullsCows[0] += tmpArray[0];
            bullsCows[1] += tmpArray[1];
        }

        return bullsCows;
    }

    private static int[] countBullsCowsWithIndex(char[] codeArray, char symbol, int mainIndex) {
        int[] bullsCows = new int[2];
        for (int i = 0; i < codeArray.length; i++) {
            if (codeArray[i] == symbol) {
                if (i == mainIndex) {
                    bullsCows[0]++;
                    continue;
                }
                bullsCows[1]++;
            }
        }
        return bullsCows;
    }
}
