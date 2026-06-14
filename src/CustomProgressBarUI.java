import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;

public class CustomProgressBarUI extends BasicProgressBarUI {
    private Color color;

    // Description: Constructor for CustomProgressBarUI, creates a custom progress bar UI with specified color
    // Parameters:  the color to use for the progress bar fill
    // Return: none
    public CustomProgressBarUI(Color color) {
        this.color = color;
    }

    // Description: Returns the foreground color for selected text within the progress bar
    // Parameters: None
    // Return:  black color for text
    @Override
    protected Color getSelectionForeground() {
    	return Color.BLACK; 
    	}
    
 // Description: Returns the background color for selected text within the progress bar
    // Parameters: None
    // Return:  black color for text
    @Override
    protected Color getSelectionBackground() {
    	return Color.BLACK; 
    	}

    
 // Description: Paints the determinate progress bar with custom colors and progress fill
    // Parameters:  graphics object for drawing, the progress bar component
    // Return: void
    @Override
    public void paintDeterminate(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D) g;
        int width = c.getWidth();
        int height = c.getHeight();
        int progress = (int) (width * progressBar.getPercentComplete());

        // Draw the background
        g2.setColor(new Color(255, 240, 200)); 
        g2.fillRect(0, 0, width, height);

        // Draw the custom progress color
        g2.setColor(color);
        g2.fillRect(0, 0, progress, height);
    }
}