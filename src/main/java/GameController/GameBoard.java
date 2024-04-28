package GameController;

import Game.GameOutcome;
import Game.Player;
import GameModel.*;
import control.Dimension2d;
import control.Point2D;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private final List<Alien> aliens = new ArrayList<>();
    private final Player player;
    private final Spaceship spaceship;
    private final List<Missile> missiles = new ArrayList<>();
    private AudioPlayer audioPlayer;
    private boolean running;
    private GameOutcome gameOutcome;
	private Missile newMissile;
    private final Dimension2d size;
    private int timer;
    private int bonus;
    public GameBoard(Player player, Dimension2d size) {
        this.player = player;
        this.size = size;
        this.timer = 0;
        this.bonus = 500;
        player.setCurrentScore(0);
        this.gameOutcome = GameOutcome.OPEN;
        spaceship = new Spaceship(this);
        createAliens();
    }

    public void update() {
        checkBonus();
		moveSpaceship();
		createMissiles();
        moveAliens();
		flyMissiles();
		checkCollision();
        checkBorder();
        checkMissilesCollision();
        timer++;
        createAliens();
        if (spaceship.isDead()) {
            gameOutcome = GameOutcome.LOST;
            running = false;
        } else if (player.getCurrentScore() >= 10000) {
            gameOutcome = GameOutcome.WON;
            running = false;
        }
	}

    public void startGame() {
		playMusic();
		this.running = true;
    }

    public void stopGame() {
		stopMusic();
		this.running = false;
    }

    public void playMusic() {
		this.audioPlayer.playBackgroundMusic();
    }

    public void stopMusic() {
		this.audioPlayer.stopBackgroundMusic();
    }

    public void moveSpaceship() {
		spaceship.move();
    }

    public void createAliens() {
        if (timer % 100 != 0) {
            return;
        }
        for (int i = 0; i < 7; i++) {
            int random = (int) (Math.random() * 10);
            if (random < 6) {
                this.aliens.add(new Beta(new Point2D(50 + i * 100, 30), this));
            } else if (random < 9) {
                this.aliens.add(new Omega(new Point2D(50 + i * 100, 30), this));
            } else {
                this.aliens.add(new Alpha(new Point2D(50 + i * 100, 30), this));
            }
        }
        timer = 0;
    }
    public void addMissile(Missile missile) {
        newMissile = missile;
    }
    public void createMissiles() {
    	if (newMissile != null) {
    		this.missiles.add(newMissile);
    	}
		newMissile = null;
    }
    // missiles fly and disappear when they fly offscreen
    public void flyMissiles() {
    	for (Missile missile : missiles) {
            if (missile.getPosition().y() < 0 || missile.getPosition().y() > size.height()) {
                missiles.remove(missile);
                return;
            }
			missile.move();
		}
    }
    // aliens move and shoot
    public void moveAliens() {
        for (Alien alien : aliens) {
            alien.move();
            if (timer % 50 == 0) {
                alien.fireMissile();
            }
        }
    }
    // checks Collision between a missile and an alien or our ship
    public void checkCollision() {
    	for(Missile missile: missiles) {
            if (missile.getClass() == OurMissile.class) {
                for(Alien target: aliens) {
                    Explosion explosion = new Explosion(target, missile);
                    if (explosion.isHit()) {
                        missiles.remove(missile);
                        target.setLives(target.getLives()-1);
                        this.audioPlayer.playExplosionSound();
                        if(target.getLives()==0) {
                            aliens.remove(target);
                            player.setCurrentScore(player.getCurrentScore() + target.getPoints());
                        }
                        return;
                    }
                }
            } else {
                Explosion explosion = new Explosion(spaceship, missile);
                if (explosion.isHit()) {
                    missiles.remove(missile);
                    this.audioPlayer.playExplosionSound();
                    spaceship.hit();
                    return;
                }
            }
    	}
    }
    private void checkMissilesCollision() {
        for(Missile missile1: missiles) {
            for(Missile missile2: missiles) {
                if (missile1.getClass() == missile2.getClass()) {
                    continue;
                }
                Explosion explosion = new Explosion(missile1, missile2);
                if (explosion.isHit()) {
                    missiles.remove(missile1);
                    missiles.remove(missile2);
                    this.audioPlayer.playExplosionSound();
                    return;
                }
            }
        }
    }
    public void checkBorder() {
        for(Alien alien: aliens) {
            if (alien.check()) {
                audioPlayer.playExplosionSound();
                spaceship.hit();
                aliens.remove(alien);
                return;
            }
        }
    }
    private void checkBonus() {
        if (player.getCurrentScore() >= bonus) {
            bonus += 500;
            spaceship.bonus();
        }
    }
    // getters and setters
    public Spaceship getSpaceship() {
        return spaceship;
    }
	public Player getPlayer() {
		return player;
	}
    public GameOutcome getGameOutcome() {
        return gameOutcome;
    }
    public boolean isRunning() {
        return this.running;
    }
    public List<Alien> getAliens() {
        return aliens;
    }
    public Dimension2d getSize() {
        return size;
    }
    public List<Missile> getMissiles() {
        return missiles;
    }
    public void setAudioPlayer(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
    }
}