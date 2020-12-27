// ShotSizeBoost increases the size of Shots for a period of time.
public class ShotSizeBoost extends PowerUp {
    
    /***Constructor************************************************************/
    public ShotSizeBoost(boolean bool) {
        super(bool);
    }
    
    /***Methods****************************************************************/
    @Override
    public void effect(Ship player) {
        if (this.isAvailable()) {
            player.setShotSizeMult(5);
            this.setAvailable(false);
        }
    }

    @Override
    public void stopEffect(Ship player) {
    	player.setShotSizeMult(1);
    }
    
    @Override
    public String classString() {
        return "ShotSizeBoost";
    }
}