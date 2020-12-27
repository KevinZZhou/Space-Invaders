import java.awt.Color;
import java.awt.Graphics;

// Alien creates an alien that will serve as the opponent in the game.
public class Alien extends GameObject {
    
    /***Variables**********************************************************************************/
    private static int totalAliens = 0;
    
    /***Constructors*******************************************************************************/
    public Alien() {
        super(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, true);
        setTotalAliens(getTotalAliens() + 1);
    }
    
    public Alien(int posX, int posY, int objW, int objH, 
            int velX, int velY, int minX, int maxX, int minY, int maxY) {
        super(posX, posY, objW, objH, velX, velY, minX, maxX, minY, maxY, true);
        setTotalAliens(getTotalAliens() + 1);
    }
    
    /***Getters and Setters************************************************************************/
    public static int getTotalAliens() {
        return totalAliens;
    }

    public static void setTotalAliens(int totalAliens) {
        Alien.totalAliens = totalAliens;
    }
    
    /***Other Methods******************************************************************************/
    public Bomb getBomb(int randomX) {
        return new Bomb(randomX, this.getPositionY() + this.getHeight(), 
                20, 20, 0, 20, 0, 980, 0, 980, true);
    }
    
    public void defeatedAlien() {
        this.setActive(false);
        setTotalAliens(getTotalAliens() - 1);
    }
    
    public static String totalAlienText() {
        if (getTotalAliens() == 1) {
            return "There is " + totalAliens + " alien remaining.";
        }
        return "There are " + totalAliens + " aliens remaining.";
    }
    
    @Override
    public void draw(Graphics g) {
        if (this.isActive()) {
            g.setColor(Color.BLACK);
            g.fillOval(this.getPositionX(), this.getPositionY(), 
                    this.getWidth(), this.getHeight());
        }
    }
}