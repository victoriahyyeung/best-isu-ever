import java.awt.*;

import javax.swing.JProgressBar;
public class Pudding extends Item{

	private Image puddingImg;
	int width;
	int  height;
	JProgressBar cookBar;

	
	// Description: Constructor for Pudding class, creates a pudding topping item
		// Parameters: pudding image
		// Return: none
	public Pudding (Image puddingImg) {
		super (puddingImg, "pudding");
		this.puddingImg = puddingImg;
		width = 50;
		height = 50;
		isTopping = true;
	}

}
