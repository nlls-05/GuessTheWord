import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GuessTheWord {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> words = new ArrayList<>();
        words.add("java");
        words.add("computer");
        Random random = new Random();
        String secretWord = words.get(random.nextInt(words.size()));
        char[] guessedWord = new char[secretWord.length()];
        String[] hints = new String[secretWord.length()];
        List<Character> correctLetters = new ArrayList<>();
        List<Character> incorrectLetters = new ArrayList<>();
        for (int i = 0; i < secretWord.length(); i++) {
            guessedWord[i] = '_';
        }
    }
}