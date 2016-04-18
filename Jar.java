import java.util.Random;

public class Jar {
  public String mItem;
  public int mMaxItems;
  public int mGuess = -1;
  public int mAttempts = 0;
  public String mHighLow = "";
  public int mHighScore = 999999999;
  public String mTopScorer = "Parzival";
  private String mPlayer = "";
  private int randomItemCount = -1;


  public Jar (String item, int max) {
    mItem = item;
    mMaxItems = max;
  }

  public void setPlayer(String player) {
    mPlayer = player;
  }

  public boolean isSolved() {
    return mGuess == randomItemCount;
  }

  public void fillJar() {
    Random random = new Random();
    randomItemCount = random.nextInt(mMaxItems) + 1;
  }

  public void applyGuess(int guess) {
    if (validateGuess(guess)) {
      setGuess(guess);
      setHighLow();
      if (isSolved()) {
        checkHighScore();
      }
    }
  }
  
  public void clearGame() {
    randomItemCount = -1;
    mAttempts = 0;
    mGuess = -1;
  }

  private void setGuess(int guess) {
    mGuess = guess;
    mAttempts++;
  }
  
  private boolean validateGuess(int guess) {
    if (guess >= 0 && guess <= mMaxItems) {
      return true;
    } else throw new IllegalArgumentException(guess + " is not a valid choice");
  }

  private void setHighLow() {
    if (mGuess > randomItemCount) {
      mHighLow = "high";
    } else mHighLow = "low";
  }
  
  private void checkHighScore() {
    if (mAttempts < mHighScore) {
      mHighScore = mAttempts;
      mTopScorer = mPlayer;
    }
  }

}
