package GameView;

import javafx.scene.control.*;

import java.util.Optional;

public class GameToolBar extends ToolBar {
    private final Button start;
    private final Button stop;
    private final Button howToPlay;

    public GameToolBar() {
        this.start = new Button("Start");
        this.stop = new Button("Stop");
        this.howToPlay = new Button("How to play");
        // the game is stopped initially
        updateToolBarStatus(false);
        getItems().addAll(this.start, new Separator(), this.stop, new Separator(), this.howToPlay);
    }

    /**
     * Initializes the actions of the toolbar buttons.
     */
    public void initializeActions(GameView.GameBoardUI gameBoardUI) {
    	
    	
    	
        this.start.setOnAction(event -> gameBoardUI.startGame());

        this.stop.setOnAction(event -> {
            // stop the game while the alert is shown
            gameBoardUI.stopGame();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you really want to stop the game?", ButtonType.YES,
                    ButtonType.NO);
            alert.setTitle("Stop Game Confirmation");
            // By default, the header additionally shows the Alert Type (Confirmation)
            // but we want to disable this to only show the question
            alert.setHeaderText("");

            Optional<ButtonType> result = alert.showAndWait();
            // reference equality check is OK here because the result will return the same
            // instance of the ButtonType
            if (result.isPresent() && result.get() == ButtonType.YES) {
                // reset the game board to prepare the new game
                gameBoardUI.setUp();
            } else {
                // continue running
                gameBoardUI.startGame();
            }
        });
        
        this.howToPlay.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    """
                            Mouse controls: hold left and right buttons on your mouse to move left and right.
                            Press your mouse-wheel to shoot.
                            Keyboard controls: hold A and D to move left and right.
                            Press W to shoot.
                            Watch out for enemy rockets! You can shoot them, but it's hard!
                            You need 10000 points to win! Every 500 points you will get a bonus life.
                            You are all set! Have fun! 😊""", ButtonType.CLOSE);
        	alert.setTitle("Game Instructions");
            alert.setHeaderText("🚀🚀🚀");
        	Optional<ButtonType> result = alert.showAndWait();
            // reference equality check is OK here because the result will return the same
            // instance of the ButtonType
            if (result.isPresent() && result.get() == ButtonType.CLOSE) {
                // reset the game board to prepare the new game
                gameBoardUI.setUp();
            }
        });
    }
    public void updateToolBarStatus(boolean running) {
        this.start.setDisable(running);
        this.stop.setDisable(!running);
    }
}