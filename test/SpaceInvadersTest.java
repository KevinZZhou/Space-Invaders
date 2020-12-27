import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/* Tests for Space Invaders Game
 * These tests do not test SpaceInvaders.java directly, which calls methods 
 * from various other classes and connects them to the GUI aspects of the game.
 * Instead, these tests test the methods directly to ensure that they are 
 * functioning properly by calling the methods in similar situations to the 
 * game without considering the GUI.
 * */

public class SpaceInvadersTest {   
    // Tests if ship movement works correctly by using the velocity of the ship
    @Test
    public void testShipMovement() {
        Ship player = new Ship();
        
        // Ship velocity should start at 0, since no "keys" are being pressed
        player.move();
        assertEquals(500, player.getPositionX());
        
        // "Press right key" and move
        player.setVelocityX(10);
        player.move();
        assertEquals(510, player.getPositionX());
        
        // "Press left key" and move
        player.setVelocityX(-10);
        player.move();
        assertEquals(500, player.getPositionX());
    }
    
    // Tests if ship movement stops at the proper bounds
    @Test
    public void testShipBounds() {
        // Position ship almost at the upper bound
        Ship player = new Ship();
        player.setPositionX(945);
        
        // Ship should stop at 950 (upper-left corner) when it moves
        player.setVelocityX(10);
        player.move();
        assertEquals(950, player.getPositionX());
        player.move();
        assertEquals(950, player.getPositionX());
        
        // Position ship almost at the lower bound
        player.setPositionX(5);
        
        // Ship should stop at 0 (upper-left corner) when it moves
        player.setVelocityX(-10);
        player.move();
        assertEquals(0, player.getPositionX());
        player.move();
        assertEquals(0, player.getPositionX());
    }
    
    // Tests if an alien will bounce off its bounds
    @Test
    public void testAlienBounce() {
        // Create and position alien
        Alien alien = new Alien(940, 0, 50, 50, 10, 0, 150, 950, 0, 1000);
        
        // Alien should bounce off the upper bound
        alien.move();
        alien.bounce(alien.hitBound());
        assertEquals(950, alien.getPositionX());
        assertEquals(-10, alien.getVelocityX());
        alien.move();
        assertEquals(940, alien.getPositionX());
        assertEquals(-10, alien.getVelocityX());
        
        // Position alien at the lower bound, alien should bounce off
        alien.setPositionX(160);
        alien.move();
        alien.bounce(alien.hitBound());
        assertEquals(150, alien.getPositionX());
        assertEquals(10, alien.getVelocityX());
        alien.move();
        assertEquals(160, alien.getPositionX());
        assertEquals(10, alien.getVelocityX());
    }
    
    // Tests how totalAliens increments/decrements
    @Test
    public void testTotalAliens() {
        // Creating aliens will increase totalAliens
        assertEquals(0, Alien.getTotalAliens());
        Alien alien1 = new Alien();
        assertEquals(1, Alien.getTotalAliens());
        Alien alien2 = new Alien();
        assertEquals(2, Alien.getTotalAliens());
        Alien alien3 = new Alien();
        assertEquals(3, Alien.getTotalAliens());
        
        // Defeating aliens will make them inactive and decrement totalAliens
        alien3.defeatedAlien();
        assertFalse(alien3.isActive());
        assertEquals(2, Alien.getTotalAliens());
        alien2.defeatedAlien();
        assertFalse(alien2.isActive());
        assertEquals(1, Alien.getTotalAliens());
        alien1.defeatedAlien();
        assertFalse(alien1.isActive());
        assertEquals(0, Alien.getTotalAliens());
    }
    
    // Tests totalAlienText() as aliens are created
    @Test
    public void testTotalAlienText() {
        // Each alien that is created should increment totalAliens, 
    	// which is reflected in the string
        assertEquals(0, Alien.getTotalAliens());
        assertEquals("There are 0 aliens remaining.", Alien.totalAlienText());
        new Alien();
        assertEquals(1, Alien.getTotalAliens());
        assertEquals("There is 1 alien remaining.", Alien.totalAlienText());
        new Alien();
        assertEquals(2, Alien.getTotalAliens());
        assertEquals("There are 2 aliens remaining.", Alien.totalAlienText());
        new Alien();
        assertEquals(3, Alien.getTotalAliens());
        assertEquals("There are 3 aliens remaining.", Alien.totalAlienText());
        Alien.setTotalAliens(0);
    }
    
    
    // Tests how totalShots increments as more shots are taken
    @Test
    public void testTotalShots() {
        // Create ship
        Ship player = new Ship();
        player.setVelocityX(100);
        
        // Get shots as totalShots increments
        assertEquals(0, Ship.getTotalShots());
        player.getShot();
        assertEquals(1, Ship.getTotalShots());
        player.move();
        player.getShot();
        assertEquals(2, Ship.getTotalShots());
        player.move();
        player.getShot();
        assertEquals(3, Ship.getTotalShots());
        Ship.setTotalShots(0);
    }
    
    // Tests totalShotText() as shots are taken
    @Test
    public void testTotalShotsText() {
        // Create ship
        Ship player = new Ship();
        player.setVelocityX(100);
        
        // Get label text as totalShots increments
        assertEquals("You have taken 0 shots in total.", 
        		player.totalShotsText());
        Shot shot1 = player.getShot();
        shot1.setActive(false);
        assertEquals("You have taken 1 shot in total.", 
        		player.totalShotsText());
        player.move();
        Shot shot2 = player.getShot();
        shot2.setActive(false);
        assertEquals("You have taken 2 shots in total.", 
        		player.totalShotsText());
        player.move();
        Shot shot3 = player.getShot();
        shot3.setActive(false);
        assertEquals("You have taken 3 shots in total.",
        		player.totalShotsText());
        Ship.setTotalShots(0);
    }
    
    // Tests if a shot will become inactive when reaching its bound
    @Test
    public void testShotBound() {
        // Create shot
        Shot shot = new Shot(500, 20, -10, 50, 0, -10, 0, 990, 0, 950, true);
        
        // Shot should remain active
        shot.move();
        shot.modifyIfOutOfBounds();
        assertEquals(10, shot.getPositionY());
        assertTrue(shot.isActive());
        
        // Shot should become inactive
        shot.move();
        shot.modifyIfOutOfBounds();
        assertEquals(0, shot.getPositionY());
        assertFalse(shot.isActive());
    }
    
    // Tests if a bomb will become inactive when reaching its bound
    @Test
    public void testBombBound() {
        // Create bomb
        Bomb bomb = new Bomb(500, 960, 20, 20, 0, 10, 0, 980, 0, 980, true);
        
        // Bomb should remain active
        bomb.move();
        bomb.modifyIfOutOfBounds();
        assertEquals(970, bomb.getPositionY());
        assertTrue(bomb.isActive());
        
        // Bomb should become inactive
        bomb.move();
        bomb.modifyIfOutOfBounds();
        assertEquals(980, bomb.getPositionY());
        assertFalse(bomb.isActive());
    }
    
    // Tests if an asteroid will become inactive when reaching its bound
    @Test
    public void testAsteroidBound() {
        // Create asteroid
        Asteroid asteroid = new Asteroid(500, 950, 30, 30, 
        		0, 10, 0, 970, 0, 970, true);
        
        // Asteroid should remain active
        asteroid.move();
        asteroid.modifyIfOutOfBounds();
        assertEquals(960, asteroid.getPositionY());
        assertTrue(asteroid.isActive());
        
        // Asteroid should become inactive
        asteroid.move();
        asteroid.modifyIfOutOfBounds();
        assertEquals(970, asteroid.getPositionY());
        assertFalse(asteroid.isActive());
    }
    
    // Tests if a shot will defeat an active alien if they intersect
    @Test 
    public void testShotOnActive() {
        // Create shot, stationary active alien
        Shot shot = new Shot(500, 55, 10, 50, 0, -10, 0, 990, 0, 950, true);
        Alien alien = new Alien(500, 0, 50, 50, 0, 0, 0, 950, 0, 950);
        
        // Before moving, they should not yet intersect - No change in state
        assertFalse(shot.intersects(alien));
        shot.hitAttack(alien);
        assertTrue(shot.isActive());
        assertTrue(alien.isActive());
        
        // After moving, they should intersect - Both become inactive
        shot.move();
        assertTrue(shot.intersects(alien));
        shot.hitAttack(alien);
        assertFalse(shot.isActive());
        assertFalse(alien.isActive());
        Alien.setTotalAliens(0);
    }
    
    // Tests to see that a shot does not affect an inactive alien
    @Test 
    public void testShotOnInactive() {
        // Create shot, stationary inactive alien
        Shot shot = new Shot(500, 55, 10, 50, 0, -10, 0, 990, 0, 950, true);
        Alien alien = new Alien(500, 0, 50, 50, 0, 0, 0, 950, 0, 950);
        alien.setActive(false);
        
        // Before moving, they should not yet intersect
        assertFalse(shot.intersects(alien));
        shot.hitAttack(alien);
        assertTrue(shot.isActive());
        assertFalse(alien.isActive());
        
        // After moving, they should intersect but keep the same state
        shot.move();
        assertTrue(shot.intersects(alien));
        shot.hitAttack(alien);
        assertTrue(shot.isActive());
        assertFalse(alien.isActive());
        Alien.setTotalAliens(0);
    }
    
    // Tests if a bomb will defeat an active ship
    @Test 
    public void testBombOnShip() {
        Bomb bomb = new Bomb(500, 875, 20, 20, 0, 20, 0, 980, 0, 980, true);
        Ship player = new Ship();
        
        // Before moving, they should not yet intersect - No change in state
        assertFalse(bomb.intersects(player));
        bomb.hitAttack(player);
        assertTrue(bomb.isActive());
        assertTrue(player.isActive());
        
        // After moving, they should intersect - Both become inactive
        bomb.move();
        assertTrue(bomb.intersects(player));
        bomb.hitAttack(player);
        assertFalse(bomb.isActive());
        assertFalse(player.isActive());
    }
    
    // Tests if an asteroid will defeat an active ship
    @Test 
    public void testAsteroidOnShip() {
        Asteroid asteroid = new Asteroid(500, 865, 30, 30, 
        		0, 20, 0, 980, 0, 980, true);
        Ship player = new Ship();
        
        // Before moving, they should not yet intersect - No change in state
        assertFalse(asteroid.intersects(player));
        asteroid.hitAttack(player);
        assertTrue(asteroid.isActive());
        assertTrue(player.isActive());
        
        // After moving, they should intersect - Both become inactive
        asteroid.move();
        assertTrue(asteroid.intersects(player));
        asteroid.hitAttack(player);
        assertFalse(asteroid.isActive());
        assertFalse(player.isActive());
    }
    
    // Tests dynamic dispatch of ShipSpeedBoost effect(), stopEffect()
    @Test
    public void testShipSpeedBoostEffect() {
        PowerUp boost = new ShipSpeedBoost(true);
        Ship player = new Ship();
        
        // Check that initial movement is as expected
        player.setVelocityX(player.getSpeed());
        player.move();
        assertEquals(510, player.getPositionX());
        
        // Apply power-up, movement changes
        boost.effect(player);
        player.setVelocityX(player.getSpeed());
        player.move();
        assertEquals(530, player.getPositionX());
        
        // Power-up should be inactive after applying it
        assertFalse(boost.isAvailable());
        
        // Stop the power-up, returns back to initial
        boost.stopEffect(player);
        player.setVelocityX(player.getSpeed());
        player.move();
        assertEquals(540, player.getPositionX());
    }
    
    // Tests dynamic dispatch of ShotSpeedBoost effect(), stopEffect()
    @Test
    public void testShotSpeedBoostEffect() {
        PowerUp boost = new ShotSpeedBoost(true);
        Ship player = new Ship();
        
        // Check that initial shot velocity is as expected
        Shot shot1 = player.getShot();
        assertEquals(-10, shot1.getVelocityY());
        
        // Apply power-up, shot velocity changes
        boost.effect(player);
        Shot shot2 = player.getShot();
        assertEquals(-20, shot2.getVelocityY());
        
        // Power-up should be inactive after applying it
        assertFalse(boost.isAvailable());
        
        // Stop the power-up, returns back to initial
        boost.stopEffect(player);
        Shot shot3 = player.getShot();
        assertEquals(-10, shot3.getVelocityY());
        
        Ship.setTotalShots(0);
    }
    
    // Tests dynamic dispatch of ShotSizeBoost effect(), stopEffect()
    @Test
    public void testShotSizeBoostEffect() {
        PowerUp boost = new ShotSizeBoost(true);
        Ship player = new Ship();
        
        // Check that initial shot size is as expected
        Shot shot1 = player.getShot();
        assertEquals(10, shot1.getWidth());
        
        // Apply power-up, shot size changes
        boost.effect(player);
        Shot shot2 = player.getShot();
        assertEquals(50, shot2.getWidth());
        
        // Power-up should be inactive after applying it
        assertFalse(boost.isAvailable());
        
        // Stop the power-up, returns back to initial
        boost.stopEffect(player);
        Shot shot3 = player.getShot();
        assertEquals(10, shot3.getWidth());
        
        Ship.setTotalShots(0);
    }
    
    // Tests ShipSpeedBoost when it is unavailable
    @Test
    public void testShipSpeedBoostUnavailable() {
        PowerUp boost = new ShipSpeedBoost(false);
        Ship player = new Ship();
        
        // Check that initial movement is as expected
        player.setVelocityX(player.getSpeed());
        player.move();
        assertEquals(510, player.getPositionX());
        
        // Try to apply power-up, nothing changes
        boost.effect(player);
        player.setVelocityX(player.getSpeed());
        player.move();
        assertEquals(520, player.getPositionX());
        
        // Power-up should still be inactive
        assertFalse(boost.isAvailable());
    }
    
    // Tests ShotSpeedBoost when it is unavailable
    @Test
    public void testShotSpeedBoostUnavailable() {
        PowerUp boost = new ShotSpeedBoost(false);
        Ship player = new Ship();
        
        // Check that initial shot velocity is as expected
        Shot shot1 = player.getShot();
        assertEquals(-10, shot1.getVelocityY());
        
        // Try to apply power-up, nothing changes
        boost.effect(player);
        Shot shot2 = player.getShot();
        assertEquals(-10, shot2.getVelocityY());
        
        // Power-up should still be inactive
        assertFalse(boost.isAvailable());
        
        Ship.setTotalShots(0);
    }
    
    
    // Tests ShotSizeBoost when it is unavailable
    @Test
    public void testShotSizeBoostUnavailable() {
        PowerUp boost = new ShotSizeBoost(false);
        Ship player = new Ship();
        
        // Check that initial shot size is as expected
        Shot shot1 = player.getShot();
        assertEquals(10, shot1.getWidth());
        
        // Try to apply power-up, nothing changes
        boost.effect(player);
        Shot shot2 = player.getShot();
        assertEquals(10, shot2.getWidth());
        
        // Power-up should still be inactive
        assertFalse(boost.isAvailable());
        
        Ship.setTotalShots(0);
    }
    
    // Tests dynamic dispatch of ShipSpeedBoost classString()
    @Test
    public void testShipSpeedBoostString() {
        PowerUp boost = new ShipSpeedBoost(true);
        assertEquals("ShipSpeedBoost", boost.classString());
    }
    
    // Tests dynamic dispatch of ShotSpeedBoost classString()
    @Test
    public void testShotSpeedBoostString() {
        PowerUp boost = new ShotSpeedBoost(true);
        assertEquals("ShotSpeedBoost", boost.classString());
    }
    
    // Tests dynamic dispatch of ShotSizeBoost classString()
    @Test
    public void testShotSizeBoostString() {
        PowerUp boost = new ShotSizeBoost(true);
        assertEquals("ShotSizeBoost", boost.classString());
    }
}