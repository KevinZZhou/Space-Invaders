import java.awt.Color;
import java.awt.Graphics;

// Shot creates a shot that will defeat an Alien if they intersect.
public class Shot extends DamageObject {
    
    /***Constructors***********************************************************/
    public Shot() {
        super(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false);
    }
    
    public Shot(int posX, int posY, int objW, int objH, 
            int velX, int velY, int minX, int maxX, int minY, int maxY, boolean active) {
        super(posX, posY, objW, objH, velX, velY, minX, maxX, minY, maxY, active);
    }
    
    /***Methods****************************************************************/
    @Override
    public void hitAttack(GameObject object) {
        if (object.getClass() == Alien.class) {
            if (this.intersects(object) && this.isActive() && object.isActive()) {
                this.setActive(false);
                ((Alien) object).defeatedAlien();
            }
        }
    }
    
    @Override
    public void modifyIfOutOfBounds() {
        if (this.getPositionY() == this.getMinimumY()) {
            this.setActive(false);
        } 
    }
    
    @Override
    public void draw(Graphics g) {
        if (this.isActive()) {
            g.setColor(Color.BLACK);
            g.fillRect(this.getPositionX(), this.getPositionY(), 
                    this.getWidth(), this.getHeight());
        }
    }

    
}