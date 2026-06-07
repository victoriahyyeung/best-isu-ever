import java.awt.*;

import javax.swing.JProgressBar;
public class Pudding extends Item{

	private Image puddingImg;
	
	JProgressBar cookBar;
	
	public Pudding (Image puddingImg) {
		super (puddingImg, "pudding");
		this.puddingImg = puddingImg;
		isTopping = true;
	}
	
}
