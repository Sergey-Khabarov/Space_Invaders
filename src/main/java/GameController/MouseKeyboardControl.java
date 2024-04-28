package GameController;

import GameModel.Spaceship;
import GameView.GameBoardUI;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class MouseKeyboardControl {

    private final Spaceship spaceship;
    private static final int ANGLE_90_DEGREES = 90;
    private static final int ANGLE_270_DEGREES = 270;
    
    public MouseKeyboardControl(GameBoardUI gameBoardUI, Spaceship spaceship) {
        this.spaceship = spaceship;
        gameBoardUI.addEventHandler(KeyEvent.KEY_PRESSED, this::keyPressed);
        gameBoardUI.addEventHandler(KeyEvent.KEY_RELEASED, this::keyReleased);

        gameBoardUI.addEventHandler(MouseEvent.MOUSE_PRESSED, this::mousePressed);
        gameBoardUI.addEventHandler(MouseEvent.MOUSE_RELEASED, this::mouseReleased);
    }
    private void keyPressed(KeyEvent clickEvent) {
        if (clickEvent.getCode() == KeyCode.A) {
            move(ANGLE_270_DEGREES);
        }
        if (clickEvent.getCode() == KeyCode.LEFT) {
            move(ANGLE_270_DEGREES);
        }
        if (clickEvent.getCode() == KeyCode.D) {
            move(ANGLE_90_DEGREES);
        }
        if (clickEvent.getCode() == KeyCode.RIGHT) {
            move(ANGLE_90_DEGREES);
        }
    }
    private void keyReleased(KeyEvent clickEvent) {
        if (clickEvent.getCode() == KeyCode.W) {
            shoot();
        }
        stop();
    }
    private void mousePressed(MouseEvent clickEvent) {
    	if (clickEvent.isPrimaryButtonDown()) {
            move(ANGLE_270_DEGREES);
    	}
        if (clickEvent.isSecondaryButtonDown()) {
            move(ANGLE_90_DEGREES);
        }
        if (clickEvent.isMiddleButtonDown()) {
            shoot();
        }
    }
    private void mouseReleased(MouseEvent clickEvent) {
    	stop();
    }
    private void move(int degree) {
        spaceship.setMoving(true);
        spaceship.setDirection(degree);
    }
    private void stop() {
        spaceship.setMoving(false);
    }
    private void shoot() {
        spaceship.fireMissile();
    }
}
