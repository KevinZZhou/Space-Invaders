import java.awt.Color;
import java.awt.Graphics;

// Ship creates a ship that the player will control in the game.
public class Ship extends GameObject {
    
    /***Variables**************************************************************/
    private int speed = 10;
    private int shotX = 10;
    private int shotY = 50;
    private int shotSpeed = -10;
    private static int totalShots = 0;
    
    /***Constructors***********************************************************/
    public Ship() {
        super(500, 900, 50, 100, 0, 0, 0, 950, 0, 1000, true);
    }
    
    public Ship(int posX, int posY, int objW, int objH, 
            int velX, int velY, int minX, int maxX, int minY, int maxY) {
        super(posX, posY, objW, objH, velX, velY, minX, maxX, minY, maxY, true);
    }
    
    /***Getters****************************************************************/
    public int getSpeed() {
        return speed;
    }
    
    public int getShotX() {
        return shotX;
    }
    
    public int getShotY() {
        return shotY;
    }
    
    public int getShotSpeed() {
        return shotSpeed;
    }
    
    public static int getTotalShots() {
        return totalShots;
    }
    
    /***Setters****************************************************************/
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    public void setShotX(int shotX) {
        this.shotX = shotX;
    }
    
    public void setShotY(int shotY) {
        this.shotY = shotY;
    }
    
    public void setShotSpeed(int shotSpeed) {
        this.shotSpeed = shotSpeed;
    }
    
    public static void setTotalShots(int totalShots) {
        Ship.totalShots = totalShots;
    }
    
    /***Other Methods**********************************************************/
    public Shot getShot() {
        setTotalShots(getTotalShots() + 1);
        return new Shot(this.getPositionX() + ((int) this.getWidth() / 2) - 
                ((int) this.getShotX() / 2), this.getPositionY(), this.getShotX(), this.getShotY(), 
                0, this.getShotSpeed(), 0, 1000, 0, 1000, true);
    }
    
    public String totalShotsText() {
        if (getTotalShots() == 1) {
            return "You have taken " + getTotalShots() + " shot in total.";
        }
        return "You have taken " + getTotalShots() + " shots in total.";
    }
    
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(this.getPositionX(), this.getPositionY(), 
                this.getWidth(), this.getHeight());
    }
}