import java.awt.*;

import javax.swing.JProgressBar;
public class Pearl extends Item{

	private Image uncookedImg, cookedImg;
	private boolean isCooked;	
	
	JProgressBar cookBar;
	
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
	
	public void cook() {
		if (!isCooked&& cookable) {//checks if possible
			isCooked=true;//so that smth cant be cooked more than once
			this.img=cookedImg;//update image
			this.cookable=false;//update ability
		}	
	}
	
	//getter
	public boolean isCooked() {
		return isCooked;
	}
	
}
