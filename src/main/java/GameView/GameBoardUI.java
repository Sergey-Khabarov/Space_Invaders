package GameView;

import Game.GameOutcome;
import Game.Player;
import GameController.AudioPlayer;
import GameController.GameBoard;
import GameController.MouseKeyboardControl;
import GameModel.Alien;
import GameModel.Missile;
import GameModel.Spaceship;
import control.Dimension2d;
import control.Point2D;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class GameBoardUI extends Canvas {
    private static final int DEFAULT_WIDTH = 750;
    private static final int DEFAULT_HEIGHT = 500;
    private static final int UPDATE_PERIOD = 40;
    private static final Dimension2d DEFAULT_SIZE = new Dimension2d(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    private static final Paint textColor = Color.RED;
    private Timer gameTimer;
    private GameBoard gameBoard;
    private final Player player;
    private final GameToolBar gameToolBar;
    private HashMap<String, Image> imageCache;
    public static Dimension2d getPreferredSize() {
        return DEFAULT_SIZE;
    }

    public GameBoardUI(GameToolBar gameToolBar, Player player) {
        this.gameToolBar = gameToolBar;
        this.player = player;
        setUp();
    }

	public void setUp(){
        setupGameBoard();
        setupImageCache();
        this.gameToolBar.updateToolBarStatus(false);
        paint();
    }
	public void paintSpaceship(Spaceship spaceship){
        Point2D spaceshipPosition = spaceship.getPosition();

        getGraphicsContext2D().drawImage(this.imageCache.get(spaceship.getIconLocation()), spaceshipPosition.x(),
        		spaceshipPosition.y(), spaceship.getSize().width(), spaceship.getSize().height());

    }
    public void paintAlien(Alien alien){
        Point2D alienPosition = alien.getPosition();

        getGraphicsContext2D().drawImage(this.imageCache.get(alien.getIconLocation()), alienPosition.x(),
                alienPosition.y(), alien.getSize().width(), alien.getSize().height());

    }
    public void paintMissile(Missile missile){
        Point2D missilePosition = missile.getPosition();

        getGraphicsContext2D().drawImage(this.imageCache.get(missile.getIconLocation()), missilePosition.x(),
        		missilePosition.y(), missile.getSize().width(), missile.getSize().height());

    }

    private void setupGameBoard() {
        Dimension2d size = getPreferredSize();
        this.gameBoard = new GameBoard(player, size);
        this.gameBoard.setAudioPlayer(new AudioPlayer());
        widthProperty().set(size.width());
        heightProperty().set(size.height());
        @SuppressWarnings("unused")
        MouseKeyboardControl mouseKeyboard = new MouseKeyboardControl(this, this.gameBoard.getSpaceship());
    }
    private void setupImageCache() {
        this.imageCache = new HashMap<>();
        for (Alien alien : this.gameBoard.getAliens()) {
            String imageLocation = alien.getIconLocation();
            this.imageCache.computeIfAbsent(imageLocation, this::getImage);
        }
        
        for (Missile missile : this.gameBoard.getMissiles()) {
            String imageLocation = missile.getIconLocation();
            this.imageCache.computeIfAbsent(imageLocation, this::getImage);
        }
        String playerImageLocation = this.gameBoard.getSpaceship().getIconLocation();
        this.imageCache.put(playerImageLocation, getImage(playerImageLocation));
    }
    
    private Image getImage(String ImageFilePath) {
        URL ImageUrl = getClass().getClassLoader().getResource(ImageFilePath);
        if (ImageUrl == null) {
            throw new IllegalArgumentException(
                    "Please ensure that your resources folder contains the appropriate files for this exercise.");
        }
        return new Image(ImageUrl.toExternalForm());
    }

    public void startGame() {
        if (!this.gameBoard.isRunning()) {
            // these 2 lines are very important for keyboard events to work
            this.setFocusTraversable(true);
            this.requestFocus();

            this.gameBoard.startGame();
            this.gameToolBar.updateToolBarStatus(true);
            startTimer();
        }
    }

    private void startTimer() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                updateGame();
            }
        };
        if (this.gameTimer != null) {
            this.gameTimer.cancel();
        }
        this.gameTimer = new Timer();
        this.gameTimer.scheduleAtFixedRate(timerTask, UPDATE_PERIOD, UPDATE_PERIOD);
    }

    private void updateGame() {
        if (gameBoard.isRunning()) {
            setupImageCache();
            this.gameBoard.update();
            // when this.gameBoard.getOutcome() is OPEN, do nothing
            if (this.gameBoard.getGameOutcome() == GameOutcome.LOST) {
                showAsyncAlert("Oh.. you lost.");
                this.stopGame();
            } else if (this.gameBoard.getGameOutcome() == GameOutcome.WON) {
            	showAsyncAlert("Congratulations! You won!!");
                this.stopGame();
            }
            paint();
        }
    }

    public void stopGame() {
        if (this.gameBoard.isRunning()) {
            this.gameBoard.stopGame();
            this.gameToolBar.updateToolBarStatus(false);
            this.gameTimer.cancel();
        }
    }
    private void paint() {
        getGraphicsContext2D().drawImage(getImage("space_background.jpg"), 0.0, 0.0, 750.0, 500.0);
        //Score and Highscore
        getGraphicsContext2D().setFill(textColor);
        getGraphicsContext2D().fillText("Score: " + this.gameBoard.getPlayer().getCurrentScore(), 20.0, 20.0);
        getGraphicsContext2D().fillText("Highscore: " + this.gameBoard.getPlayer().getHighScore(), 650.0, 20.0);
        getGraphicsContext2D().fillText("Lives: " + this.gameBoard.getSpaceship().getLives(), 335.0, 20.0);

        for (Alien alien : this.gameBoard.getAliens()) {
            paintAlien(alien);
        }
        for (Missile missile : this.gameBoard.getMissiles()) {
            paintMissile(missile);
        }
        paintSpaceship(this.gameBoard.getSpaceship());
    }

    private void showAsyncAlert(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(message);
            alert.showAndWait();
            this.setUp();
        });
    }
}
