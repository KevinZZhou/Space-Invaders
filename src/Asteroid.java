import java.awt.Color;
import java.awt.Graphics;
import java.util.SplittableRandom;

// Asteroid creates an asteroid that will defeat the Ship if they intersect.
public class Asteroid extends DamageObject {
    
    /***Variables**************************************************************/
    private static SplittableRandom randomNumber = new SplittableRandom();
    
    /***Constructors***********************************************************/
    public Asteroid() {
        super(randomNumber.nextInt(0, 971), 0, 30, 30, 0, 3, 0, 970, 0, 970, true);
    }
    
    public Asteroid(int posX, int posY, int objW, int objH, 
            int velX, int velY, int minX, int maxX, int minY, int maxY, boolean active) {
        super(posX, posY, objW, objH, velX, velY, minX, maxX, minY, maxY, active);
    }

    /***Methods****************************************************************/
    @Override
    public void hitAttack(GameObject object) {
        if (object.getClass() == Ship.class) {
            if (this.intersects(object) && this.isActive() && object.isActive()) {
                this.setActive(false);
                object.setActive(false);
            }
        }
    }

    @Override
    public void modifyIfOutOfBounds() {
        if (this.getPositionY() == this.getMaximumY()) {
            this.setActive(false);
        } 
    }

    @Override
    public void draw(Graphics g) {
        if (this.isActive()) {
            g.setColor(Color.GRAY);
            g.fillOval(this.getPositionX(), this.getPositionY(), 
                    this.getWidth(), this.getHeight());
        }
    }
}