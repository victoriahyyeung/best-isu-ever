import java.awt.*;

import javax.swing.JProgressBar;
public class Pearl extends Item{

	private Image uncookedImg, cookedImg;
	private boolean isCooked;	
	
	JProgressBar cookBar;
	
	// Description: Constructor for Pearl class, creates an uncooked pearl topping item
		// Parameters: image for uncooked pearl, image for cooked pearl
		// Return: void
	public Pearl (Image uncooked, Image cooked) {
		super (uncooked, "pearl");
		this.uncookedImg=uncooked;
		this.cookedImg=cooked;
		this.isCooked=false;
		//abilities default
		this.cookable=true;
		this.cuttable=false;
		this.blendable=false;
		isFruit = false;
		isTopping = true;
	}
	
	// Description: Cooks the pearl, changing its image to cooked version and making it non-cookable
		// Parameters: None
		// Return: void
	public void cook() {
		if (!isCooked&& cookable) {//checks if possible
			isCooked=true;//so that smth cant be cooked more than once
			this.img=cookedImg;//update image
			this.cookable=false;//update ability
		}	
	}
	
	// Description: Sets the pearl as cooked and updates its image (alternative to cook method)
		// Parameters: None
		// Return: void
	public void setCooked() {
		if (cookable) {//check if cook() can even be done
			isCooked=true;
			this.img=cookedImg;
			this.cookable=false;
		}
	}
	
	//getter
	// Description: Returns whether the pearl has been cooked
		// Parameters: None
		// Return:  true if cooked, false if uncooked
	public boolean isCooked() {
		return isCooked;
	}
	
}
