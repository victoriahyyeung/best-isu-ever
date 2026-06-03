import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;

public class CustomProgressBarUI extends BasicProgressBarUI {
    private Color color;

    public CustomProgressBarUI(Color color) {
        this.color = color;
    }

    @Override
    protected Color getSelectionForeground() {
    	return Color.BLACK; 
    	}
    
    @Override
    protected Color getSelectionBackground() {
    	return Color.BLACK; 
    	}

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