import java.awt.Color;
import java.awt.Graphics;

// Bomb creates a bomb that will defeat the Ship if they intersect.
public class Bomb extends DamageObject {
    
    /***Constructors*******************************************************************************/
    public Bomb() {
        super(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false);
    }
    
    public Bomb(int posX, int posY, int objW, int objH, 
            int velX, int velY, int minX, int maxX, int minY, int maxY, boolean active) {
        super(posX, posY, objW, objH, velX, velY, minX, maxX, minY, maxY, active);
    }
    
    /***Methods************************************************************************************/
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
            g.setColor(Color.RED);
            g.fillOval(this.getPositionX(), this.getPositionY(), 
                    this.getWidth(), this.getHeight());
        }
    }
}