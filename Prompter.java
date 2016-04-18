import java.io.Console;

public class Prompter {
  Console console = System.console();
  private Jar mJar;

  public Prompter() {
  }

  public void promptAdmin() {
    int max = 0;
    boolean isValidAmount = false;
    console.printf("Welcome to \"Guess How Many\"\n\n");
    String item = console.readLine("Enter an item (plural) to fill the jar: ");
    while (!isValidAmount || max <= 0) {
      try {
        max = Integer.parseInt(console.readLine("How many can fit in the jar? "));
        isValidAmount = true;
      } catch (IllegalArgumentException ex) {
        console.printf("%s. That's not a number.\n", ex.getMessage());
      }
    }
    mJar = new Jar(item, max);
  }

  public void play() {
    boolean playAgain = true;
    while (playAgain) {
      clearScreen();
      console.printf("\nLet the guessing begin!");
      console.printf("\n-----------------------\n");
      mJar.setPlayer(promptForName());
      mJar.fillJar();
      while (!mJar.isSolved()) {
        displayProgress();
        promptForGuess();
      }

      if (mJar.mAttempts == 1) {
        console.printf("\n***YOU WON!*** It only took you %d guess.\n", mJar.mAttempts);
        console.printf("\nThe current high score is %d guess by %s.\n", mJar.mHighScore, mJar.mTopScorer);
        console.printf("This score cannot be beat!\n");
      } else {
        console.printf("\n----------YOU WON!----------\nIt only took you %d guesses.\n", mJar.mAttempts);
        console.printf("\nThe current high score is %d guesses by %s.\n", mJar.mHighScore, mJar.mTopScorer);
      }
      playAgain = promptForNewGame();
    }
  }

  private boolean promptForNewGame() {
    String newGame = "";
    boolean answer;
    while (!newGame.equalsIgnoreCase("Y") || !newGame.equalsIgnoreCase("N")) {
      newGame = console.readLine("Do you want to play again? (Y or N):  ");
      if (newGame.equalsIgnoreCase("N")) {
        clearScreen();
        return false;
      } else if (newGame.equalsIgnoreCase("Y")) {
          mJar.clearGame();
          return true;
        }
      } return true;
  }

  private void displayProgress() {
    if (mJar.mAttempts > 0) {
      console.printf("\nYour last guess of %d was too %s.\n",mJar.mGuess, mJar.mHighLow);
    }
    console.printf("\nThere is a maximum of %d %s in the jar.\n", mJar.mMaxItems, mJar.mItem);
    if (mJar.mAttempts == 1) {
      console.printf("You have made %d guess so far.\n", mJar.mAttempts);
    } else {
      console.printf("You have made %d guesses so far.\n", mJar.mAttempts);
    }
  }

  private void promptForGuess() {
    int guess = 0;
    boolean isNumber = false;
    while (!isNumber) {
      try {
        guess = Integer.parseInt(console.readLine("Guess how many are in the jar?  "));
        isNumber = true;
      } catch (NumberFormatException ex) {
        console.printf("%s. That's not a number.\n", ex.getMessage());
      }
    }
    try {
      mJar.applyGuess(guess);
    } catch (IllegalArgumentException ex) {
      console.printf("%s. Try again.\n", ex.getMessage());
    }
  }

  private String promptForName() {
    return console.readLine("Please enter your name: ");
  }

  private void clearScreen() {
    System.out.print("\033[H\033[2J");
  }
}
