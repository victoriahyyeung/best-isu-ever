import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class Fruit extends Item {

	private String fruitType; //"mango", "lychee"
	// Images for the 3 fruit states
	protected Image freshImg, cutImg, blendedImg;

	protected boolean isBlended, isCut;
	boolean chopping;
	JProgressBar chopBar;
	protected int chopCount;//need 4 chops to become cut



	public Fruit (String fruitType) {
		super(Toolkit.getDefaultToolkit().getImage(fruitType + "_fresh.png"), "fruit");
		this.fruitType=fruitType;
		this.freshImg=this.img;
		this.cutImg=Toolkit.getDefaultToolkit().getImage(fruitType + "_cut.png");
		this.blendedImg=Toolkit.getDefaultToolkit().getImage(fruitType+"_blended.png");
		//default fruit states
		this.isCut=false;
		this.isBlended=false;

		

		this.chopCount=0;
		chopping = false;
		
		chopBar = new JProgressBar(0, 4);
		chopBar.setValue(0);
		chopBar.setForeground(new Color(255, 204, 51));
		chopBar.setBackground(new Color (255, 240, 200));
		chopBar.setBorderPainted(false);
		
		isFruit = true;
		isTopping = false;
		//capabilities inherited from Item class
		this.cuttable=true;
		this.blendable=false;//must be cut first before being blendable
		this.cookable=false;//never cookable
	}

	public void cut() {
		if (!isCut && !isBlended && cuttable) {//check if cut() can be done
			chopCount++;//cut
			chopBar.setValue(chopCount);
			if (chopCount>=4) {
				isCut=true;//will no longer be able to go into this method
				this.cuttable=false;
				this.img=cutImg;//change img to cut version
				this.blendable=true;//NOW can be blended
				chopping = false;
			}
		}
	}

	public void setBlended() {
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
	public String getFruitType() {
		return fruitType;
	}

}
