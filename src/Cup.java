import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

public class Cup extends Item {
	private ArrayList<String> fruits;//(max 2)
	private ArrayList<String> toppings;//(max 2)

	private Image baseCupImg;
	private Image pearlImg;
	private Image puddingImg;

	public Cup (Image baseCup, Image pearl, Image pudding) {
		super (baseCup, "cup");
		this.baseCupImg=baseCup;
		this.pearlImg=pearl;
		this.puddingImg =pudding;
		fruits=new ArrayList<>();
		toppings=new ArrayList<>();
		//cups cant do any of these actions, its still an item tho.
		this.cuttable=false;
		this.blendable=false;
		this.cookable=false;
	}

	public boolean addFruit(String fruitType) {
		if (fruits.size()>=2)//max 2 fruits can be added
			return false;
		if (!fruits.contains(fruitType)) {//avoid duplicate fruit
			fruits.add(fruitType);
			return true;
		}
		return false;
	}
	public boolean addTopping (String toppingType) {
		if (toppings.size()>=2)//max 2 toppings can be added
			return false;
		if (!toppings.contains(toppingType)) {
			toppings.add(toppingType);
			return true;
		}
		return false;
	}


	public void drawLayered(Graphics g, int x, int y) {		
		g.drawImage(baseCupImg, x, y, null);
		//TODO: DRAW LIQUID AND TOPPINGS 
		//	idk how to draw liquid or show the image but have it change colours accordingly...

	}
	//getters
	public ArrayList<String>getFruits(){
		return fruits;
	}
	public ArrayList<String>getToppings(){
		return toppings;
	}
	public boolean hasFruit(String fruit) {
		return fruits.contains(fruit);
	}
	public boolean hasTopping(String topping) {
		return toppings.contains(topping);
	}


}
