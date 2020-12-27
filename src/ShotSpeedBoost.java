// ShotSpeedBoost doubles the speed of Shots for a period of time.
public class ShotSpeedBoost extends PowerUp {
    
    /***Constructor********************************************************************************/
    public ShotSpeedBoost(boolean bool) {
        super(bool);
    }
    
    /***Methods************************************************************************************/
    @Override
    public void effect(Ship player) {
        if (this.isAvailable()) {
            int initialSpeed = player.getShotSpeed();
            player.setShotSpeed(2 * initialSpeed);
            this.setAvailable(false);
        }
    }

    @Override
    public void stopEffect(Ship player) {
        int alteredSpeed = player.getShotSpeed();
        player.setShotSpeed((int) alteredSpeed / 2);
    }
    
    @Override
    public String classString() {
        return "ShotSpeedBoost";
    }
}