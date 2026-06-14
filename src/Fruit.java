import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class Fruit extends Item {

	private String fruitType; //"mango", "lychee"
	// Images for the 3 fruit states
	protected Image freshImg, cutImg, blendedImg;

	// boolean to check if fruit has been cut/blended
	protected boolean isBlended, isCut;
	protected int chopCount;//need 4 chops to become cut
	private boolean onChopStation;// track if fruit is on chop station

	// Description: Constructor for Fruit class, creates a new fruit item with specified type and images
	// Parameters: type of fruit ("mango" or "lychee"), image for fresh fruit, image for cut fruit, image for blended fruit
	// return: none
	public Fruit (String fruitType, Image fresh, Image cut, Image blended) {
		super(fresh, "fruit");
		this.fruitType=fruitType;
		this.freshImg=fresh;
		this.cutImg=cut;
		this.blendedImg=blended;
		//default fruit states
		this.isCut=false;
		this.isBlended=false;

		// default 0 chop counts and NOT on chopping table
		this.chopCount=0;
		this.onChopStation=false;
		// categorizes it to fruit
		isFruit = true;
		isTopping = false;
		//capabilities inherited from Item class
		this.cuttable=true;
		this.blendable=false;//must be cut first before being blendable
		this.cookable=false;//never cookable
	}

	// Description: Performs one chop action on the fruit, requires 4 chops to fully cut the fruit
		// Parameters: None
		// Return: void
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
	
	// Description: Marks the fruit as blended and updates its image to the blended version
		// Parameters: None
		// Return: void
	public void setBlended() {
		if (!isBlended && blendable) {//check if blend() can even be done
			isBlended=true;
			this.img=blendedImg;
			this.blendable=false;
		}
	}
	
	// Description: Sets whether the fruit is currently on a chop station
		// Parameters: boolean b - true if on chop station, false otherwise
		// Return: void
	public void setOnChopStation(boolean b) {
		this.onChopStation=b;
	}

	//getters
	
	// Description: Returns whether the fruit has been blended
		// Parameters: None
		// Return:true if blended, false otherwise
	public boolean isBlended() {
		return isBlended;
	}
	
	// Description: Returns whether the fruit has been cut
		// Parameters: None
		// Return:true if cut, false otherwise
	public boolean isCut() {
		return isCut;
	}
	
	// Description: Returns whether the fruit is on a chop station
		// Parameters: None
		// Return:true if on chop station, false otherwise
	public boolean isOnChopStation() {
		return onChopStation;
	}
	
	// Description: Returns the type of fruit (mango or lychee)
		// Parameters: None
		// Return: fruit type
	public String getFruitType() {
		return fruitType;
	}

	// Description: Returns the chop progress as a percentage (0-100)
		// Parameters: None
		// Return: chop progress percentage
	public int getChopProgress() {//values til 100 for consistency
		return chopCount*25;
	}
	
	// Description: Returns the number of chops performed on the fruit
	// Parameters: None
	// Return: int - chop count (0-4)
	public int getChopCount() {
		return chopCount;
	}
}
