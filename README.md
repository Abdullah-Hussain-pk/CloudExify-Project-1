# CloudExify-Project-1
Number Guessing Game
## Description
A number guessing game built in Java, available in two versions:
- **Console version** (`Game.java`) — text-based, run via terminal
- **GUI version** (`Game_GUI.java`) — Swing-based graphical interface

Both share the same core game logic via the `Game` class.

## How to Run

**Console version:**
javac Game.java
java Game

**GUI version:**
javac Game_GUI.java
java Game_GUI
(Requires `Game.java` compiled in the same folder, since `Game_GUI` uses the `Game` class.)

## Features
- Difficulty selection (Easy: 1–50, Hard: 1–200)
- Too High / Too Low feedback
- Attempt counting
- Best score tracking, saved to `bestscore.txt`
- Play again option
- Attempt limit (max 10 guesses)
- Warmer/Colder hints based on guess proximity
- Input validation (handles non-numeric input gracefully in both versions)
- GUI-only: live progress bar showing attempts used

## Known Design Notes
- The GUI's "Play Again" button replaces the console's y/n replay prompt; closing the window (X button) replaces "n."
- Best score is shared between console and GUI runs via the same `bestscore.txt` file.

## Files Included
- `Game.java`
- `Game_GUI.java`
- `bestscore.txt` (sample, generated after first win)
- `README.md`
