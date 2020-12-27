// ShipSpeedBoost doubles the speed of the Ship for a period of time.
public class ShipSpeedBoost extends PowerUp {
    
    /***Constructor********************************************************************************/
    public ShipSpeedBoost(boolean bool) {
        super(bool);
    }
    
    /***Methods************************************************************************************/
    @Override
    public void effect(Ship player) {
        if (this.isAvailable()) {
            int initialSpeed = player.getSpeed();
            player.setSpeed(2 * initialSpeed);
            this.setAvailable(false);
        }
    }

    @Override
    public void stopEffect(Ship player) {
        int alteredSpeed = player.getSpeed();
        player.setSpeed((int) alteredSpeed / 2);
    }
    
    @Override
    public String classString() {
        return "ShipSpeedBoost";
    }
}