import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private int secretNumber;
    private int attempts;
    private int bestScore;
    private Scanner input;
    private Random random;
    private int lastGuess;
    private boolean guessedBefore;

    public Game() {
        input = new Scanner(System.in);
        random = new Random();
        bestScore = loadBestScore();
        guessedBefore = false;
    }

    public int loadBestScore() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("bestscore.txt"));
            String line = reader.readLine();
            reader.close();
            if (line != null) {
                return Integer.parseInt(line);
            }
        } catch (IOException e) {
            // File doesn't exist yet
        }
        return Integer.MAX_VALUE;
    }

    public void saveBestScore() {
        try {
            FileWriter writer = new FileWriter("bestscore.txt");
            writer.write(String.valueOf(attempts));
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving score!");
        }
    }

    public int getAttempts() {
        return attempts;
    }

    public int getBestScore() {
        return bestScore;
    }

    public int getSecretNumber() {
        return secretNumber;
    }

    public void setSecretNumber(int secretNumber) {
        this.secretNumber = secretNumber;
    }

    public void incrementAttempts() {
        attempts++;
    }

    public void resetAttempts() {
        attempts = 0;
        guessedBefore = false;
    }

    public void updateBestScore() {
        if (attempts < bestScore) {
            bestScore = attempts;
            saveBestScore();
        }
    }

    // Returns a hint string based on whether this guess is closer or farther than the last one
    public String getHint(int guess) {
        String hint = "";
        if (guessedBefore) {
            int prevDistance = Math.abs(lastGuess - secretNumber);
            int currDistance = Math.abs(guess - secretNumber);
            if (currDistance < prevDistance) {
                hint = " (Warmer!)";
            } else if (currDistance > prevDistance) {
                hint = " (Colder!)";
            } else {
                hint = " (Same distance)";
            }
        }
        lastGuess = guess;
        guessedBefore = true;
        return hint;
    }

    public void play() {
        System.out.println("\n========================");
        System.out.println(" CLOUDEXIFY GUESS GAME");
        System.out.println("========================");
        System.out.println("Best Score: " + bestScore + " attempts");

        System.out.println("\nDifficulty (1-Easy, 2-Hard): ");
        int diff = readValidInt();
        int range = (diff == 1) ? 50 : 200;

        secretNumber = random.nextInt(range) + 1;
        attempts = 0;
        guessedBefore = false;
        final int MAX_ATTEMPTS = 10;
        boolean guessed = false;

        while (!guessed && attempts < MAX_ATTEMPTS) {
            System.out.print("Guess (1-" + range + "): ");
            int guess = readValidInt();
            attempts++;
            String hint = getHint(guess);

            if (guess < secretNumber) {
                System.out.println("Too LOW!" + hint);
            } else if (guess > secretNumber) {
                System.out.println("Too HIGH!" + hint);
            } else {
                System.out.println("CORRECT! Attempts: " + attempts);
                guessed = true;
                if (attempts < bestScore) {
                    bestScore = attempts;
                    saveBestScore();
                }
            }
        }

        if (!guessed) {
            System.out.println("Out of attempts! The number was: " + secretNumber);
        }
    }

    // Keeps prompting until the user enters a valid integer
    private int readValidInt() {
        while (!input.hasNextInt()) {
            System.out.print("That's not a valid number. Try again: ");
            input.next(); // discard the invalid token
        }
        return input.nextInt();
    }

    public static void main(String[] args) {
        Game game = new Game();
        Scanner input = new Scanner(System.in);
        boolean playMore = true;
        while (playMore) {
            game = new Game();
            game.play();
            System.out.print("\nPlay again? (y/n): ");
            String answer = input.next();
            playMore = answer.equalsIgnoreCase("y");
        }
        System.out.println("Thanks for playing!");
        input.close();
    }
}
