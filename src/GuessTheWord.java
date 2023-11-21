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

        char[] guessedWord = new char[secretWord.length()];
        for (int i = 0; i < guessedWord.length; i++) {
            guessedWord[i] = '_';
        }

        int attempts = 0;
        boolean wordGuessed = false;

        System.out.println("Welcome to Guess the Word Game!");
        System.out.println("Try to guess the word.");

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




}