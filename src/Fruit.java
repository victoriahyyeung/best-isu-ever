import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class Fruit extends Item {

	private String type; //"mango", "lychee"
	// Images for the 3 fruit states
	protected Image freshImg, cutImg, blendedImg;

	protected boolean isBlended, isCut;


	protected int chopCount;//need 4 chops to become cut

	public Fruit (String type) {
		super(Toolkit.getDefaultToolkit().getImage(type + "_fresh.png"));
		this.type=type;
		this.freshImg=this.img;;
		this.cutImg=Toolkit.getDefaultToolkit().getImage(type + "_cut.png");
		this.blendedImg=Toolkit.getDefaultToolkit().getImage(type+"_blended.png");
		//default fruit states
		this.isCut=false;
		this.isBlended=false;
		this.chopCount=0;
		//capabilities inherited from Item class
		this.cuttable=true;
		this.blendable=false;//must be cut first before being blendable
		this.cookable=false;//never cookable
	}

	public void cut() {
		if (!isCut && !isBlended && cuttable) {//check if cut() can be done
			chopCount++;//cut
			if (chopCount>=4) {
				isCut=true;//will no longer be able to go into this method
				this.cuttable=false;
				this.img=cutImg;//change img to cut version
				this.blendable=true;//NOW can be blended
			}
		}
	}

	public void blend(JProgressBar bar, JPanel panel) {
		if (!isBlended && blendable) {//check if blend() can even be done
			isBlended=true;
			this.img=blendedImg;
			this.blendable=false;
		}
	}

	//getters
	public boolean isBlended() {
		return isBlended;
	}
	public boolean isCut() {
		return isCut;
	}
	public String getType() {
		return type;
	}

}
