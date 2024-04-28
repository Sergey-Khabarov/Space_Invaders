package Game;

public class Player {

    private int currentScore = 0;
    private int highScore = 0;

	public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
        if (highScore < currentScore) {
            setHigScore(currentScore);
        }
    }

    public void setHigScore(int highScore) {
        this.highScore = highScore;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public int getHighScore() {
        return highScore;
    }
}
