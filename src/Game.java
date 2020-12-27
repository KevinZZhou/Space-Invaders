import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// Game establishes the frame, widgets, and text that appear in the application.
public class Game implements Runnable {
    public void run() {

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Space Invaders");
        frame.setLocation(0, 0);

        // Message panel - Describes power-ups, game status, and shots taken
        final JPanel message_panel = new JPanel();
        final JPanel power_up_panel = new JPanel();
        message_panel.add(power_up_panel, BorderLayout.WEST);
        final JPanel status_panel = new JPanel();
        message_panel.add(status_panel, BorderLayout.CENTER);
        final JPanel shots_panel = new JPanel();
        message_panel.add(shots_panel, BorderLayout.EAST);
        frame.add(message_panel, BorderLayout.SOUTH);
        
        final JLabel powerUpText = new JLabel();
        power_up_panel.add(powerUpText);
        final JLabel status = new JLabel();
        status_panel.add(status);
        final JLabel shots = new JLabel();
        shots_panel.add(shots);
        
        // Instructions panel
        final JPanel instruction_panel = new JPanel();
        instruction_panel.setPreferredSize(new Dimension(400, Globals.FIELD_Y));
        frame.add(instruction_panel, BorderLayout.WEST);
        final JTextArea instructions = new JTextArea(
                "Welcome to Space Invaders!\n" + 
        
                "In this game, you control a ship (green rectangle) at the "
                + "bottom of the screen.  The objective of the game is to "
                + "defeat the 8 aliens (black circles) before being defeated "
                + "yourself!  If you want to play again, just press the "
                + "Reset button! \n\n" + 
                
                "Win Condition: \n"
                + "Shoot lasers (black rectangles) at the rows of aliens to "
                + "defeat all 8 of them, while avoiding the aliens' fast "
                + "bombs (red circles) and the slow-moving asteroids (grey "
                + "circles).  Touching one of them will end the game, so "
                + "don't get hit! \n\n" + 
                
                "Controls: \n"
                + "Left/Right Arrow Keys - Move the player's ship to the "
                + "left/right - The ship is not allowed to move out of the "
                + "bounds of the screen. \n" + 
                "Space Bar - Fires the ship's shot - Only one shot can be "
                + "fired at an alien at a time. \n\n" + 
                
                "Messages: \n"
                + "You might notice three messages at the bottom of the "
                + "screen.  These messages provide information on whether "
                + "the ship has a power-up, the number of aliens remaining "
                + "on the screen, and the number of shots that the player "
                + "has shot.  These messages will automatically update as "
                + "the game goes on."
                );
        instructions.setFont(new Font("Verdana", Font.PLAIN, 14));
        instructions.setLineWrap(true);
        instructions.setWrapStyleWord(true);
        instructions.setBackground(instruction_panel.getBackground());
        instructions.setPreferredSize(new Dimension(320, Globals.FIELD_Y));
        instruction_panel.add(instructions);
        
        // Main playing area
        final SpaceInvaders space = new SpaceInvaders(
        		status, powerUpText, shots);
        space.setPreferredSize(new Dimension(Globals.FIELD_X, Globals.FIELD_Y));
        frame.add(space, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                space.reset();
            }
        });
        control_panel.add(reset);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        space.reset();
    }

    // Main method used to run the game
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}