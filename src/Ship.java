import java.awt.Color;
import java.awt.Graphics;

// Ship creates a ship that the player will control in the game.
public class Ship extends GameObject {
    
    /***Variables**************************************************************/
	private int shipSpeedMult = 1;
	private int shotSpeedMult = 1;
	private int shotSizeMult = 1;

    private static int totalShots = 0;
    
    /***Constructors***********************************************************/
    public Ship() {
        super((int) Globals.FIELD_X / 2, Globals.FIELD_Y - Globals.SHIP_Y, 
        		Globals.SHIP_X, Globals.SHIP_Y, 0, 0, 
        		0, Globals.FIELD_X - Globals.SHIP_X, 0, Globals.FIELD_Y, true);
    }
    
    public Ship(int posX, int posY, int objW, int objH, 
            int velX, int velY, int minX, int maxX, int minY, int maxY) {
        super(posX, posY, objW, objH, velX, velY, minX, maxX, minY, maxY, true);
    }
    
    /***Getters****************************************************************/   
    public int getShipSpeedMult() {
		return shipSpeedMult;
	}

	public int getShotSpeedMult() {
		return shotSpeedMult;
	}
	
	public int getShotSizeMult() {
		return shotSizeMult;
	}
	
	public static int getTotalShots() {
        return totalShots;
    }

	/***Setters****************************************************************/
    public void setShipSpeedMult(int shipSpeedMult) {
		this.shipSpeedMult = shipSpeedMult;
	}
	
	public void setShotSpeedMult(int shotSpeedMult) {
		this.shotSpeedMult = shotSpeedMult;
	}

	public void setShotSizeMult(int shotSizeMult) {
		this.shotSizeMult = shotSizeMult;
	}
	
	public static void setTotalShots(int totalShots) {
		Ship.totalShots = totalShots;
	}    
    
    /***Other Methods**********************************************************/
    public Shot getShot() {
        setTotalShots(getTotalShots() + 1);
        return new Shot(this.getPositionX() + ((int) this.getWidth() / 2) - 
                ((int) (this.getShotSizeMult() * Globals.SHOT_X) / 2), 
                this.getPositionY(), this.getShotSizeMult() * Globals.SHOT_X, 
                Globals.SHOT_Y, 0, this.getShotSpeedMult() * Globals.SHOT_VEL_Y,
                0, Globals.FIELD_X - Globals.SHOT_X, 
                0, Globals.FIELD_Y, true);
    }
    
    public String totalShotsText() {
        if (getTotalShots() == 1) {
            return "You have taken " + getTotalShots() + " shot in total.";
        }
        return "You have taken " + getTotalShots() + " shots in total.";
    }
    
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(this.getPositionX(), this.getPositionY(), 
                this.getWidth(), this.getHeight());
    }
}