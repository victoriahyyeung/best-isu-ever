import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class Fruit extends Item {

	// Images for the 3 fruit states
	protected Image freshImg;
    protected Image cutImg;
    protected Image blendedImg;
    
    protected boolean isBlended;
    protected boolean isCut;
    
    protected long blendStartTime;
	protected long cutStartTime;
	
	int chopCount;
	boolean isReady;
	
	JProgressBar blendBar;
	boolean showBlendBar;
	
	JProgressBar cutBar;
	boolean showCutBar;
	
	public Fruit (Image FruitImg) {
		super (FruitImg);
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key==KeyEvent.VK_SPACE)
			cut();
	}
	
	public void cut() {
		
	}
	
	public void blend() {
		
	}
	
    public Image currentImage() {
        if (isBlended) 
        	return blendedImg;
        if (isCut) 
        	return cutImg;
        return freshImg;
    }
    
 
    
    public void draw(Graphics g) {
        g.drawImage(currentImage(), itemX, itemY, null);
    }
    
}
