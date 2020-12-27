// ShotSpeedBoost doubles the speed of Shots for a period of time.
public class ShotSpeedBoost extends PowerUp {
    
    /***Constructor************************************************************/
    public ShotSpeedBoost(boolean bool) {
        super(bool);
    }
    
    /***Methods****************************************************************/
    @Override
    public void effect(Ship player) {
        if (this.isAvailable()) {
        	player.setShotSpeedMult(3);
            this.setAvailable(false);
        }
    }

    @Override
    public void stopEffect(Ship player) {
    	player.setShotSpeedMult(1);
    }
    
    @Override
    public String classString() {
        return "ShotSpeedBoost";
    }
}