import java.util.Scanner;
import java.util.Random;
public class GuessTheWord {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        String[] words = {"java", "programming", "chocolate", "games"};
        String secretWord = words[random.nextInt(words.length)];
        int numPlayers;
        do {
            System.out.print("Enter the number of players : ");
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid input. Please enter a valid number: ");
                scanner.next();
            }
            numPlayers = scanner.nextInt();
        }while (numPlayers < 2);

        char[][] guessedWords = new char[numPlayers][secretWord.length()];
        for (int i = 0; i < numPlayers; i++) {
            for (int j = 0; j < guessedWords[i].length; j++) {
                guessedWords[i][j] = '_';
            }
        }

        boolean[] wordGuessed = new boolean[numPlayers];

        System.out.println("Welcome to Guess the Word Game!");
        System.out.println("Try to guess the word.");
        int currentPlayer = 0;
        while (!anyPlayerGuessedWord(wordGuessed)) {
            displayWord(guessedWords[currentPlayer]);

            System.out.print("Player " + (currentPlayer + 1) + ", enter one or more letters or the entire word: ");
            String guess = scanner.next().toLowerCase();

            if (guess.length() == 1) {
                char letter = guess.charAt(0);
                if (isGuessCorrect(letter, secretWord, guessedWords[currentPlayer])) {
                    System.out.println("Correct guess!");
                } else {
                    System.out.println("Incorrect guess.");
                }
            } else if (guess.length() == secretWord.length() && guess.equals(secretWord)) {
                wordGuessed[currentPlayer] = true;
                System.out.println("Congratulations! Player " + (currentPlayer + 1) + " guessed the word: " + secretWord);
            } else if (guess.length() >= 2) {
                processMultipleLetters(guess, secretWord, guessedWords[currentPlayer]);
            } else {
                System.out.println("Invalid input. Please enter one or more letters or the entire word.");
            }

            if (isWordGuessed(guessedWords[currentPlayer])) {
                wordGuessed[currentPlayer] = true;
                System.out.println("Congratulations! Player " + (currentPlayer + 1) + " guessed the word: " + secretWord);
            }

            currentPlayer = (currentPlayer + 1) % numPlayers; // Switch to the next player
        }

        scanner.close();
    }


    private static void displayWord(char[] word){
        System.out.print("Word: ");
        for (char letter : word) {
            if (letter == '_') {
                System.out.print("_ ");
            } else {
                System.out.print(Character.toUpperCase(letter) + " ");
            }
        }
        System.out.println();
    }
        private static void processMultipleLetters(String guess, String secretWord, char[] guessedWord) {
            for (int i = 0; i < secretWord.length(); i++) {
                if (guess.contains(String.valueOf(secretWord.charAt(i)))) {
                    guessedWord[i] = secretWord.charAt(i);
                }
            }
            System.out.println("Partial correct guess!");
        }
        private static boolean isGuessCorrect(char guess, String secretWord, char[] guessedWord) {
            boolean correctGuess = false;

            for (int i = 0; i < secretWord.length(); i++) {
                if (secretWord.charAt(i) == guess) {
                    guessedWord[i] = guess;
                    correctGuess = true;
                }
            }

            return correctGuess;
        }
        private static boolean isWordGuessed(char[] guessedWord) {
            for (char letter : guessedWord) {
                if (letter == '_') {
                    return false;
                }
            }
            return true;
        }



        private static boolean anyPlayerGuessedWord(boolean[] wordGuessed) {
            for (boolean guessed : wordGuessed) {
                if (guessed) {
                    return true;
                }
            }
            return false;
        }
}