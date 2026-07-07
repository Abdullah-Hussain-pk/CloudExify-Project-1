import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class Game_GUI {
    public static void main(String[] args) {
        Game game = new Game();
        Random random = new Random();
        final int MAX_ATTEMPTS = 10;

        String[] options = {"Easy (1-50)", "Hard (1-200)"};
        int choice = JOptionPane.showOptionDialog(null, "Choose difficulty:",
                "Difficulty", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
        int range = (choice == 0) ? 50 : 200;

        game.setSecretNumber(random.nextInt(range) + 1);

        JFrame frame = new JFrame("Number Guessing Game");
        frame.setSize(400, 320);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JLabel bestScoreLabel = new JLabel("Best Score: " + game.getBestScore() + " attempts");
        frame.add(bestScoreLabel);

        JLabel messageLabel = new JLabel("Guess a number between 1 and " + range);
        frame.add(messageLabel);

        JLabel attemptsLabel = new JLabel("Attempts left: " + MAX_ATTEMPTS);
        frame.add(attemptsLabel);

        JProgressBar progressBar = new JProgressBar(0, MAX_ATTEMPTS);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setPreferredSize(new Dimension(250, 20));
        frame.add(progressBar);

        JTextField guessField = new JTextField(10);
        frame.add(guessField);

        JButton guessButton = new JButton("Guess");
        frame.add(guessButton);

        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setEnabled(false);
        frame.add(playAgainButton);

        guessButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int guess = Integer.parseInt(guessField.getText());
                    game.incrementAttempts();
                    attemptsLabel.setText("Attempts left: " + (MAX_ATTEMPTS - game.getAttempts()));
                    progressBar.setValue(game.getAttempts());
                    String hint = game.getHint(guess);

                    if (guess < game.getSecretNumber()) {
                        messageLabel.setText("Too LOW!" + hint);
                    } else if (guess > game.getSecretNumber()) {
                        messageLabel.setText("Too HIGH!" + hint);
                    } else {
                        game.updateBestScore();
                        messageLabel.setText("CORRECT! Attempts: " + game.getAttempts());
                        bestScoreLabel.setText("Best Score: " + game.getBestScore() + " attempts");
                        guessButton.setEnabled(false);
                        playAgainButton.setEnabled(true);
                        guessField.setText("");
                        return;
                    }

                    if (game.getAttempts() >= MAX_ATTEMPTS) {
                        messageLabel.setText("Out of attempts! The number was: " + game.getSecretNumber());
                        guessButton.setEnabled(false);
                        playAgainButton.setEnabled(true);
                    }
                } catch (NumberFormatException ex) {
                    messageLabel.setText("Please enter a valid number!");
                }
                guessField.setText("");
            }
        });

        playAgainButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.setSecretNumber(random.nextInt(range) + 1);
                game.resetAttempts();
                messageLabel.setText("Guess a number between 1 and " + range);
                attemptsLabel.setText("Attempts left: " + MAX_ATTEMPTS);
                progressBar.setValue(0);
                guessField.setText("");
                guessButton.setEnabled(true);
                playAgainButton.setEnabled(false);
            }
        });

        frame.setVisible(true);
    }
}