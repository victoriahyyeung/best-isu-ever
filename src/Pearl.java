import java.awt.*;

import javax.swing.JProgressBar;
public class Pearl extends Item{

	private Image uncookedImg, cookedImg;
	private boolean isCooked;	
	
	public Pearl () {
		super (Toolkit.getDefaultToolkit().getImage("pearl_uncooked.png"));
		this.uncookedImg=this.img;
		this.cookedImg=Toolkit.getDefaultToolkit().getImage("pearl_cooked.png");
		this.isCooked=false;
		//abilities default
		this.cookable=true;
		this.cuttable=false;
		this.blendable=false;
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
