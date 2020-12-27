// PowerUp is an abstract class for ship/shot power-ups 
// Includes ShipSpeedBoost, ShotSpeedBoost, and ShotSizeBoost
public abstract class PowerUp {
    
    /***Variables**************************************************************/
    private boolean available;
    
    /***Constructor************************************************************/
    public PowerUp(boolean bool) {
        this.setAvailable(bool);
    }
    
    /***Getters and Setters****************************************************/
    public boolean isAvailable() {
        return available;
    }
    
    public void setAvailable(boolean available) {
        this.available = available;
    }
    
    public void switchAvailable() {
        this.available = !(this.available);
    }
    
    /***Other Methods**********************************************************/
    public abstract void effect(Ship player);
    public abstract void stopEffect(Ship player);
    public abstract String classString();
}
