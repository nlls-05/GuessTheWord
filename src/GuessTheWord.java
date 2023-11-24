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
        // Add more words as needed

        List<String> hints = new ArrayList<>();
        hints.add("A versatile programming language.");
        hints.add("An activity for entertainment.");
        hints.add("A sweet treat made from cocoa.");
        hints.add("Fun activities with rules.");
        hints.add("A vehicle with wheels.");
        hints.add("The existence of an individual.");
        // Add more hints as needed

        List<String> playerNames = new ArrayList<>();
        List<Integer> playerPoints = new ArrayList<>();
        List<Boolean> wordGuessed = new ArrayList<>();

        System.out.print("Enter the number of players: ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Enter name for Player " + (i + 1) + ": ");
            String playerName = scanner.nextLine();
            playerNames.add(playerName);
            playerPoints.add(0);
            wordGuessed.add(false);
        }

        int currentPlayer = random.nextInt(numPlayers);

        System.out.println("Welcome to Beautiful Guess the Word Game!");
        System.out.println("Try to guess the word.");

        while (!(allWordsGuessed(wordGuessed) || anyPlayerEliminated(playerPoints))) {
            clearScreen();
            String secretWord = words.get(random.nextInt(words.size()));
            String hint = hints.get(words.indexOf(secretWord));

            playRound(scanner, playerNames, playerPoints, wordGuessed, currentPlayer, secretWord, hint);

            currentPlayer = (currentPlayer + 1) % numPlayers; // Switch to the next player
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

    private static boolean anyPlayerEliminated(List<Integer> playerPoints) {
        return false;
    }

    private static void playRound(Scanner scanner, List<String> playerNames, List<Integer> playerPoints,
                                  List<Boolean> wordGuessed, int currentPlayer, String secretWord, String hint) {
        System.out.println("Hint: " + hint);

        while (!wordGuessed.get(currentPlayer)) {
            displayGameInfo(playerNames, playerPoints, currentPlayer);

            System.out.print(playerNames.get(currentPlayer) + ", enter one or more letters or the entire word: ");
            String guess = scanner.nextLine().toLowerCase();

            if (guess.length() == 1) {
                char letter = guess.charAt(0);
                if (isGuessCorrect(letter, secretWord, playerPoints, currentPlayer)) {
                    clearScreen();
                    System.out.println("Correct guess!");
                    displayWord(secretWord, playerPoints.get(currentPlayer));
                    continue; // Player gets another turn
                } else {
                    clearScreen();
                    System.out.println("Incorrect guess.");
                }
            } else if (guess.length() == secretWord.length() && guess.equals(secretWord)) {
                wordGuessed.set(currentPlayer, true);
                playerPoints.set(currentPlayer, playerPoints.get(currentPlayer) + 1000); // Adjust points for guessing the entire word
                clearScreen();
                System.out.println("Congratulations! " + playerNames.get(currentPlayer) +
                        " guessed the word: " + secretWord);
                displayScores(playerNames, playerPoints);
                break; // Player wins and the round ends
            } else if (guess.length() >= 2) {
                processMultipleLetters(guess, secretWord, playerPoints, currentPlayer, playerNames);
            } else {
                clearScreen();
                System.out.println("Invalid input. Please enter one or more letters or the entire word.");
            }
        }
    }

    private static void displayGameInfo(List<String> playerNames, List<Integer> playerPoints, int currentPlayer) {
        System.out.println("Current Turn: " + playerNames.get(currentPlayer));
        displayScores(playerNames, playerPoints);
    }

    private static void processMultipleLetters(String guess, String secretWord, List<Integer> playerPoints, int currentPlayer, List<String> playerNames) {
        if (guess.equals(secretWord)) {
            playerPoints.set(currentPlayer, playerPoints.get(currentPlayer) + 1000); // Adjust points for guessing the entire word
            clearScreen();
            System.out.println("Congratulations! " + playerNames.get(currentPlayer) +
                    " guessed the word: " + secretWord);
            displayScores(playerNames, playerPoints);
        } else {
            clearScreen();
            System.out.println("Incorrect guess. The correct word was not guessed.");
        }
    }

    private static boolean isGuessCorrect(char guess, String secretWord, List<Integer> playerPoints, int currentPlayer) {
        boolean correctGuess = false;

        for (int i = 0; i < secretWord.length(); i++) {
            if (secretWord.charAt(i) == guess) {
                playerPoints.set(currentPlayer, playerPoints.get(currentPlayer) + 10); // Adjust points for correct letter guess
                correctGuess = true;
            }
        }

        return correctGuess;
    }

    private static boolean allWordsGuessed(List<Boolean> wordGuessed) {
        for (boolean guessed : wordGuessed) {
            if (!guessed) {
                return false;
            }
        }
        return true;
    }

    private static void displayScores(List<String> playerNames, List<Integer> playerPoints) {
        System.out.println("Points:");
        for (int i = 0; i < playerNames.size(); i++) {
            System.out.println(playerNames.get(i) + ": " + playerPoints.get(i) + " points");
        }
        System.out.println();
    }

    private static void displayWord(String secretWord, int playerPoints) {
        System.out.println("Word: " + secretWord.toUpperCase());
        System.out.println("Points for the word: 1000 points");
        System.out.println("Total Points: " + playerPoints + " points\n");
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
}
