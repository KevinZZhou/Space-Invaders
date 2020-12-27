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
            int initialY = player.getShotY();
            player.setShotX(initialY);
            this.setAvailable(false);
        }
    }

    @Override
    public void stopEffect(Ship player) {
        int alteredX = player.getShotX();
        player.setShotX((int) alteredX / 5);
    }
    
    @Override
    public String classString() {
        return "ShotSizeBoost";
    }
}