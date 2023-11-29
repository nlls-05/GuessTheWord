import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class GuessTheWord {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        List<String> words = new ArrayList<>();
        words.add("java");
        words.add("play");
        words.add("chocolate");
        words.add("games");
        words.add("car");
        words.add("life");
        words.add("mountain");
        words.add("ocean");
        words.add("computer");
        words.add("fridge");
        words.add("football");
        words.add("gym");

        List<String> hints = new ArrayList<>();
        hints.add("A versatile programming language.");
        hints.add("An activity for entertainment.");
        hints.add("A sweet treat made from cocoa.");
        hints.add("Fun activities with rules.");
        hints.add("A vehicle with wheels.");
        hints.add("The existence of an individual.");
        hints.add("A large landform that rises prominently above its surroundings.");
        hints.add("A vast expanse of saltwater.");
        hints.add("An electronic device for processing data.");
        hints.add("a thing that keeps food cool.");
        hints.add("a sport that is played with 2 teams for 90 mins.");
        hints.add("a place you can do your workouts at");

        List<String> playerNames = new ArrayList<>();
        List<Integer> playerPoints = new ArrayList<>();
        List<String> usedWords = new ArrayList<>();

        System.out.print("Enter the number of players: ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Enter name for Player " + (i + 1) + ": ");
            String playerName = scanner.nextLine();
            playerNames.add(playerName);
            playerPoints.add(0);
        }

        System.out.println("Welcome to Guess the Word Game!");

        while (!(anyPlayerReached1000(playerPoints))) {
            for (int i = 0; i < numPlayers; i++) {
                clearScreen();
                String secretWord = getUniqueWord(new ArrayList<>(words), usedWords, random);
                usedWords.add(secretWord);
                String hint = hints.get(words.indexOf(secretWord));

                playRound(scanner, playerNames, playerPoints, secretWord, hint, i);
            }
        }

        clearScreen();
        displayScores(playerNames, playerPoints);
        int winnerIndex = findWinner(playerPoints);
        if (winnerIndex != -1) {
            System.out.println("Congratulations! " + playerNames.get(winnerIndex) + " is the winner!");
        } else {
            System.out.println("It's a tie! No winner.");
        }

        scanner.close();
    }

    private static boolean anyPlayerReached1000(List<Integer> playerPoints) {
        for (int points : playerPoints) {
            if (points >= 1000) {
                return true;
            }
        }
        return false;
    }

    private static void playRound(Scanner scanner, List<String> playerNames, List<Integer> playerPoints,
                                  String secretWord, String hint, int currentPlayer) {
        List<Character> correctGuesses = new ArrayList<>();
        int pointsEarned = 0;
        boolean continueWithSameWord = true;

        while (continueWithSameWord) {
            System.out.println("Hint: " + hint);
            displayGameInfo(playerNames, playerPoints, currentPlayer);
            displayWord(secretWord, correctGuesses, playerPoints, currentPlayer);

            System.out.print(playerNames.get(currentPlayer) + ", enter one or more letters or the entire word: ");
            String guess = scanner.nextLine().toLowerCase();

            if (guess.length() == 1) {
                char letter = guess.charAt(0);
                if (isGuessCorrect(letter, secretWord)) {
                    clearScreen();
                    System.out.println("Correct guess!");
                    correctGuesses.add(letter);
                    pointsEarned = secretWord.length() * 50;
                    playerPoints.set(currentPlayer, playerPoints.get(currentPlayer) + pointsEarned);
                } else {
                    clearScreen();
                    System.out.println("Incorrect guess.");
                    playerPoints.set(currentPlayer, playerPoints.get(currentPlayer) + 10);
                    continueWithSameWord = false;
                }
            } else if (guess.length() == secretWord.length() && guess.equals(secretWord)) {
                pointsEarned = secretWord.length() * 50;
                playerPoints.set(currentPlayer, playerPoints.get(currentPlayer) + pointsEarned);
                clearScreen();
                System.out.println("Congratulations! " + playerNames.get(currentPlayer) +
                        " guessed the word: " + secretWord);
                displayScores(playerNames, playerPoints);
                continueWithSameWord = false;
            } else if (guess.length() >= 2) {
                processMultipleLetters(guess, secretWord, correctGuesses, playerPoints, currentPlayer, playerNames);
            } else {
                clearScreen();
                System.out.println("Invalid input. Please enter one or more letters or the entire word.");
            }

            if (continueWithSameWord) {
                System.out.println("Press Enter to continue with the same word...");
                scanner.nextLine(); // Wait for the player to press Enter
                clearScreen();
            }
        }
    }

    private static void displayGameInfo(List<String> playerNames, List<Integer> playerPoints, int currentPlayer) {
        System.out.println("Current Turn: " + playerNames.get(currentPlayer));
        displayScores(playerNames, playerPoints);
    }

    private static void processMultipleLetters(String guess, String secretWord, List<Character> correctGuesses,
                                               List<Integer> playerPoints, int currentPlayer, List<String> playerNames) {
        for (int i = 0; i < guess.length(); i++) {
            char letter = guess.charAt(i);
            if (isGuessCorrect(letter, secretWord) && !correctGuesses.contains(letter)) {
                correctGuesses.add(letter);
                playerPoints.set(currentPlayer, playerPoints.get(currentPlayer) + 50);
            }
        }

        clearScreen();
        System.out.println("Correct guesses!");
        displayWord(secretWord, correctGuesses, playerPoints, currentPlayer);
    }

    private static boolean isGuessCorrect(char guess, String secretWord) {
        for (int i = 0; i < secretWord.length(); i++) {
            if (secretWord.charAt(i) == guess) {
                return true;
            }
        }
        return false;
    }

    private static void displayScores(List<String> playerNames, List<Integer> playerPoints) {
        System.out.println("Points:");
        for (int i = 0; i < playerNames.size(); i++) {
            System.out.println(playerNames.get(i) + ": " + playerPoints.get(i) + " points");
        }
        System.out.println();
    }

    private static void displayWord(String secretWord, List<Character> correctGuesses,
                                    List<Integer> playerPoints, int currentPlayer) {
        System.out.print("Word: ");
        for (int i = 0; i < secretWord.length(); i++) {
            char currentLetter = secretWord.charAt(i);
            if (correctGuesses.contains(currentLetter)) {
                System.out.print(currentLetter + " ");
            } else {
                System.out.print("_ ");
            }
        }

        int pointsEarned = secretWord.length() * 50;
        System.out.println("\nPoints for the word: " + pointsEarned + " points");
        System.out.println("Total Points: " + playerPoints.get(currentPlayer) + " points\n");
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static int findWinner(List<Integer> playerPoints) {
        int maxPoints = -1;
        int winnerIndex = -1;

        for (int i = 0; i < playerPoints.size(); i++) {
            if (playerPoints.get(i) > maxPoints) {
                maxPoints = playerPoints.get(i);
                winnerIndex = i;
            }
        }

        return winnerIndex;
    }

    private static String getUniqueWord(List<String> words, List<String> usedWords, Random random) {
        if (words.isEmpty()) {
            return null;
        }

        String selectedWord = null;
        do {
            int index = random.nextInt(words.size());
            selectedWord = words.get(index);
        } while (usedWords.contains(selectedWord));

        return selectedWord;
    }
}
