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

        List<String> playerNames = new ArrayList<>();
        List<Integer> playerPoints = new ArrayList<>();

        System.out.print("Enter the number of players: ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Enter name for Player " + (i + 1) + ": ");
            String playerName = scanner.nextLine();
            playerNames.add(playerName);
            playerPoints.add(0);
        }

        System.out.println("Welcome to Beautiful Guess the Word Game!");
        System.out.println("Try to guess the word.");

        while (!(anyPlayerReached1000(playerPoints))) {
            for (int i = 0; i < numPlayers; i++) {
                clearScreen();
                String secretWord = getUniqueWord(new ArrayList<>(words), random);
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
        System.out.println("Hint: " + hint);

        displayGameInfo(playerNames, playerPoints, currentPlayer);

        System.out.print(playerNames.get(currentPlayer) + ", enter one or more letters or the entire word: ");
        String guess = scanner.nextLine().toLowerCase();

        if (guess.length() == 1) {
            char letter = guess.charAt(0);
            if (isGuessCorrect(letter, secretWord)) {
                clearScreen();
                System.out.println("Correct guess!");
                displayWord(secretWord, playerPoints, currentPlayer);
            } else {
                clearScreen();
                System.out.println("Incorrect guess.");
            }
        } else if (guess.length() == secretWord.length() && guess.equals(secretWord)) {
            int pointsEarned = secretWord.length() * 50;
            playerPoints.set(currentPlayer, playerPoints.get(currentPlayer) + pointsEarned);
            clearScreen();
            System.out.println("Congratulations! " + playerNames.get(currentPlayer) +
                    " guessed the word: " + secretWord);
            displayScores(playerNames, playerPoints);
        } else if (guess.length() >= 2) {
            processMultipleLetters(guess, secretWord, playerPoints, currentPlayer, playerNames);
        } else {
            clearScreen();
            System.out.println("Invalid input. Please enter one or more letters or the entire word.");
        }
    }

    private static void displayGameInfo(List<String> playerNames, List<Integer> playerPoints, int currentPlayer) {
        System.out.println("Current Turn: " + playerNames.get(currentPlayer));
        displayScores(playerNames, playerPoints);
    }

    private static void processMultipleLetters(String guess, String secretWord, List<Integer> playerPoints,
                                               int currentPlayer, List<String> playerNames) {
        if (guess.equals(secretWord)) {
            int pointsEarned = secretWord.length() * 50;
            playerPoints.set(currentPlayer, playerPoints.get(currentPlayer) + pointsEarned);
            clearScreen();
            System.out.println("Congratulations! " + playerNames.get(currentPlayer) +
                    " guessed the word: " + secretWord);
            displayScores(playerNames, playerPoints);
        } else {
            clearScreen();
            System.out.println("Incorrect guess. The correct word was not guessed.");
        }
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

    private static void displayWord(String secretWord, List<Integer> playerPoints, int currentPlayer) {
        System.out.println("Word: " + secretWord.toUpperCase());
        int pointsEarned = secretWord.length() * 50;
        System.out.println("Points for the word: " + pointsEarned + " points");
        System.out.println("Total Points: " + playerPoints.get(currentPlayer) + " points\n");
    }

    private static void clearScreen() {
        // Code to clear the console screen, depending on the environment
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // Handle exceptions if unable to clear the screen
            System.out.println("Unable to clear the screen.");
        }
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

    private static String getUniqueWord(List<String> words, Random random) {
        if (words.isEmpty()) {
            return null;
        }

        int index = random.nextInt(words.size());
        String selectedWord = words.get(index);
        words.remove(index);
        return selectedWord;
    }
}
