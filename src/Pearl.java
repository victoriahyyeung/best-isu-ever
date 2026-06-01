import java.awt.*;

import javax.swing.JProgressBar;
public class Pearl extends Item{

	Image cookedPearl;
	Image uncookedPearl;
	
	JProgressBar cookBar;
	boolean showCookBar;
	
	
	public Pearl () {
		super (Toolkit.getDefaultToolkit().getImage("uncookedMango.png"));
	}
	
	public void cook() {
		
	}
	
}
