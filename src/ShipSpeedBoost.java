// ShipSpeedBoost doubles the speed of the Ship for a period of time.
public class ShipSpeedBoost extends PowerUp {
    
    /***Constructor************************************************************/
    public ShipSpeedBoost(boolean bool) {
        super(bool);
    }
    
    /***Methods****************************************************************/
    @Override
    public void effect(Ship player) {
        if (this.isAvailable()) {
            player.setShipSpeedMult(2);
            this.setAvailable(false);
        }
    }

    @Override
    public void stopEffect(Ship player) {
        player.setShipSpeedMult(1);
    }
    
    @Override
    public String classString() {
        return "ShipSpeedBoost";
    }
}