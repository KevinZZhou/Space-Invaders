// DamageObject is an abstract class dealing with objects that deal damage.
// Includes Shot, Bomb, and Asteroid
public abstract class DamageObject extends GameObject {
    
    /***Constructor************************************************************/
    public DamageObject(int posX, int posY, int objW, int objH, int velX, 
    		int velY, int minX, int maxX, int minY, int maxY, boolean active) {
        super(posX, posY, objW, objH, velX, 
        		velY, minX, maxX, minY, maxY, active);
    }
    
    /***Methods****************************************************************/
    public abstract void hitAttack(GameObject object);
    public abstract void modifyIfOutOfBounds();
}
