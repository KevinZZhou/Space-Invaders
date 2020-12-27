import java.awt.Graphics;

// GameObject holds object info - Position, dimensions, velocities, and bounds
public abstract class GameObject {
    
    /***Variables**************************************************************/
    private int positionX;
    private int positionY;
    private int width;
    private int height;
    private int velocityX;
    private int velocityY;
    private int minimumX;
    private int maximumX;
    private int minimumY;
    private int maximumY;
    private boolean active;
    
    /***Constructors***********************************************************/
    // GameObject() with no arguments initializes an object with default values
    public GameObject() {
        this.setValues(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false);
    }
    
    public GameObject(int posX, int posY, int objW, int objH, 
            int velX, int velY, int minX, int maxX, int minY, int maxY, boolean active) {
        this.setValues(posX, posY, objW, objH, velX, velY, minX, maxX, minY, maxY, active);
    }
    
    /***Getters****************************************************************/
    public int getPositionX() {
        return this.positionX;
    }
    
    public int getPositionY() {
        return this.positionY;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public int getVelocityX() {
        return this.velocityX;
    }
    
    public int getVelocityY() {
        return this.velocityY;
    }
    
    public int getMinimumX() {
        return this.minimumX;
    }
    
    public int getMaximumX() {
        return this.maximumX;
    }
    
    public int getMinimumY() {
        return this.minimumY;
    }
    
    public int getMaximumY() {
        return this.maximumY;
    }
    
    public boolean isActive() {
        return active;
    }

    /***Setters****************************************************************/
    public void setPositionX(int posX) {
        this.positionX = posX;
        clip();
    }
    
    public void setPositionY(int posY) {
        this.positionY = posY;
        clip();
    }
    
    public void setWidth(int objW) {
        this.width = objW;
    }
    
    public void setHeight(int objH) {
        this.height = objH;
    }
    
    public void setVelocityX(int velX) {
        this.velocityX = velX;
    }
    
    public void setVelocityY(int velY) {
        this.velocityY = velY;
    }
    
    public void setMinimumX(int minX) {
        this.minimumX = minX;
    }
    
    public void setMaximumX(int maxX) {
        this.maximumX = maxX;
    }
    
    public void setMinimumY(int minY) {
        this.minimumY = minY;
    }
    
    public void setMaximumY(int maxY) {
        this.maximumY = maxY;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    /***Combo Setters**********************************************************/
    private void setPosition(int posX, int posY) {
        this.setPositionX(posX);
        this.setPositionY(posY);
    }
    
    private void setDimensions(int objW, int objH) {
        this.setWidth(objW);
        this.setHeight(objH);
    }
    
    private void setVelocities(int velX, int velY) {
        this.setVelocityX(velX);
        this.setVelocityY(velY);
    }
    
    private void setBounds(int minX, int maxX, int minY, int maxY) {
        this.setMinimumX(minX);
        this.setMaximumX(maxX);
        this.setMinimumY(minY);
        this.setMaximumY(maxY);
    }
    
    private void setValues(int posX, int posY, int objW, int objH, 
            int velX, int velY, int minX, int maxX, int minY, int maxY, boolean active) {
        this.setBounds(minX, maxX, minY, maxY);
        this.setPosition(posX, posY);
        this.setDimensions(objW, objH);
        this.setVelocities(velX, velY);
        this.setActive(active);
    }
    
    /***Other Methods**********************************************************/
    // clip() prevents an object from being positioned out of bounds
    private void clip() {
        this.positionX = Math.min(Math.max(this.positionX, this.minimumX), this.maximumX);
        this.positionY = Math.min(Math.max(this.positionY, this.minimumY), this.maximumY);
    }
    
    public void move() {
        this.positionX += this.velocityX;
        this.positionY += this.velocityY;
        clip();
    }
    
    public boolean intersects(GameObject that) {
        return (this.positionX + this.width >= that.positionX
                && this.positionY + this.height >= that.positionY
                && that.positionX + that.width >= this.positionX 
                && that.positionY + that.height >= this.positionY);
    }
    
    // Note that in this game, no objects bounce vertically.
    public void bounce(Direction d) {
        if (d == null) {
            return;
        }
        
        switch (d) {
            case LEFT:
                this.velocityX = Math.abs(this.velocityX);
                break;
            case RIGHT:
                this.velocityX = -Math.abs(this.velocityX);
                break;
            default:
                break;
        }
    }
    
    public Direction hitBound() {
        if (this.positionX + this.velocityX < minimumX) {
            return Direction.LEFT;
        } else if (this.positionX + this.velocityX > this.maximumX) {
            return Direction.RIGHT;
        } else {
            return null;
        }
    }
    
    public abstract void draw(Graphics g);
}


