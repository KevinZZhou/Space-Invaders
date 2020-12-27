import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.SplittableRandom;

import javax.swing.*;

// SpaceInvaders combines various methods from different classes and links them 
// to the actual game (i.e. use of key listeners).
@SuppressWarnings("serial")
public class SpaceInvaders extends JPanel {
    
    /***Variables**************************************************************/
    private Alien[][] alienArray = 
    		new Alien[Globals.ALIEN_ARRAY_ROWS][Globals.ALIEN_ARRAY_COLS];
    private Ship player;
    private Shot shot;
    private Bomb bomb;
    private LinkedList<Asteroid> asteroidList = new LinkedList<Asteroid>();
    private SplittableRandom randomNumber = new SplittableRandom();
    private PowerUp[] powerUps = new PowerUp[3];
    private int powerUpIndex;
    
    private boolean playing = false;
    private JLabel status;
    private JLabel powerUpText;
    private JLabel shots;
    
    private int timeElapsed;
    private static final int INTERVAL = 25;
    
    /***Constructor************************************************************/
    public SpaceInvaders(JLabel status, JLabel powerUpText, JLabel shots) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        Timer gameTimer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        gameTimer.start();
        
        setFocusable(true);
        
        // Key listeners
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    player.setVelocityX(-1 * 
                    		player.getShipSpeedMult() * Globals.SHIP_VEL_X);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    player.setVelocityX(
                    		player.getShipSpeedMult() * Globals.SHIP_VEL_X);
                } 
            }

            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT 
                		|| e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    player.setVelocityX(0);
                } 
            }
        });
        
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (!(shot.isActive()) && playing) {
                        shot = player.getShot();
                        shots.setText(player.totalShotsText());
                    }
                }
            }
        });
        
        this.status = status;
        this.powerUpText = powerUpText;
        this.shots = shots;
    }
    
    /***Main Methods***********************************************************/
    public void reset() {
        setTimeElapsed(0);
        Ship.setTotalShots(0);
        Alien.setTotalAliens(0);
        
        for (int row = 0; row < alienArray.length; row++) {
            for (int col = 0; col < alienArray[row].length; col++) {
                Alien alien = new Alien(Globals.ALIEN_X * col, 
                		Globals.ALIEN_Y * row, Globals.ALIEN_X, 
                		Globals.ALIEN_Y, Globals.ALIEN_VEL_X, 
                		Globals.ALIEN_VEL_Y, Globals.ALIEN_X * col, 
                		Globals.FIELD_X - Globals.ALIEN_X * 
                		(Globals.ALIEN_ARRAY_COLS - col), 
                		0, Globals.ALIEN_Y * (row + 1));
                alienArray[row][col] = alien;
            }
        }
        player = new Ship();
        shot = new Shot();
        bomb = new Bomb();
        asteroidList = new LinkedList<Asteroid>();
        
        PowerUp power0 = new ShipSpeedBoost(false);
        PowerUp power1 = new ShotSpeedBoost(true);
        PowerUp power2 = new ShotSizeBoost(false);
        powerUps[0] = power0;
        powerUps[1] = power1;
        powerUps[2] = power2;
        powerUpIndex = 0;
        
        powerUpText.setText("There are currently no power-ups on the ship.");
        shots.setText("You have taken 0 shots in total.");
        
        playing = true;
        
        requestFocusInWindow();
    }
    
    void tick() {
        if (playing) {
            setTimeElapsed(getTimeElapsed() + INTERVAL);
            
            player.move();
            
            if (shot.isActive()) {
                shot.move();
                shot.modifyIfOutOfBounds();
            }
            
            // Handle Alien movement, potential collisions with a Shot
            for (int row = 0; row < alienArray.length; row++) {
                for (int col = 0; col < alienArray[row].length; col++) {
                    alienArray[row][col].move();
                    shot.hitAttack((alienArray[row][col]));
                    status.setText(Alien.totalAlienText());
                }
            }
            
            // Handle Bombs, which appear at regular intervals
            if (timeElapsed % 1200 == 0) {
                // The position of the shot is randomized relative to the Alien
                int randomShotX = randomNumber.nextInt(
                		alienArray[0][0].getPositionX(), 
                        alienArray[0]
                        		[Globals.ALIEN_ARRAY_COLS - 1].getPositionX() + 
                        alienArray[0][Globals.ALIEN_ARRAY_COLS - 1].getWidth());
                bomb = alienArray[Globals.ALIEN_ARRAY_ROWS - 1]
                		[0].getBomb(randomShotX);
            }
            if (bomb.isActive()) {
                bomb.move();
                bomb.modifyIfOutOfBounds();
                bomb.hitAttack(player);
            } 
            
            // Handle Asteroids, which appear at regular intervals
            if (timeElapsed % 2000 == 0) {
                Asteroid asteroid = new Asteroid();
                asteroidList.add(asteroid);
            }
            if (!(asteroidList.isEmpty())) {
                for (Asteroid asteroid : asteroidList) {
                    asteroid.move();
                }
                
                // The Asteroid speed is such that only the first Asteroid in 
                // the LinkedList can hit the Ship or bottom of the screen
                Asteroid firstAsteroid = asteroidList.getFirst();
                firstAsteroid.modifyIfOutOfBounds();
                firstAsteroid.hitAttack(player);
                if (!(firstAsteroid.isActive())) {
                    asteroidList.removeFirst();
                }
            }
            
            // Handle power-ups, which appear/disappear regularly
            if (timeElapsed % 20000 == 5000) {
                if (powerUps[powerUpIndex].isAvailable()) {
                    powerUps[powerUpIndex].effect(player);
                    powerUpText.setText("You have the following power-up: " + 
                            powerUps[powerUpIndex].classString());
                } else if (!(powerUps[powerUpIndex].isAvailable())) {
                    powerUps[powerUpIndex].switchAvailable();
                    // This value uses modulo to work with the array indices
                    powerUpIndex = (powerUpIndex + 1) % 3;
                }
            } 
            if (timeElapsed % 20000 == 15000) {
                if (!(powerUps[powerUpIndex].isAvailable())) {
                    powerUps[powerUpIndex].stopEffect(player);
                    powerUpText.setText(
                    		"There are currently no power-ups on the ship.");
                    powerUpIndex = (powerUpIndex + 1) % 3;
                }
            }
            
        }
        
        // Check win condition
        if (Alien.getTotalAliens() == 0) {
            playing = false;
            status.setText("You won!  Congratulations!");
        }
        
        // Check loss condition
        if (!(player.isActive())) {
            playing = false;
            status.setText("You lost...  Better luck next time.");
        }
        
        repaint();
    }
    
    /***Other Methods**********************************************************/
    public int getTimeElapsed() {
        return timeElapsed;
    }

    public void setTimeElapsed(int timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        player.draw(g);
        for (int row = 0; row < alienArray.length; row++) {
            for (int col = 0; col < alienArray[row].length; col++) {
                alienArray[row][col].draw(g);
                alienArray[row][col].bounce(alienArray[row][col].hitBound());
            }
        }
        for (Asteroid asteroid : asteroidList) {
            asteroid.draw(g);
        }
        shot.draw(g);
        bomb.draw(g);
    }
}