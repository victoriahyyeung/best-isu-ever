import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;

public class Cup extends Item {
	private ArrayList<String> fruits;//(max 2)
	private ArrayList<String> toppings;//(max 2)

	private Image currentCupImg;
	private Image baseCupImg;
	private Image pearlImg;
	private Image puddingImg;
	
	private HashMap <String, Image> cupImages = new HashMap <> ();

	public Cup (Image baseCup) {
		super (baseCup, "cup");
		this.baseCupImg=baseCup;
		fruits=new ArrayList<>();
		toppings=new ArrayList<>();
		//cups cant do any of these actions, its still an item tho so must.
		this.cuttable=false;
		this.blendable=false;
		this.cookable=false;
	}

	public void refreshImage(HashMap <String, Image> cupImages) {
		    if (fruits.contains("mango")) {
		        if (toppings.contains("pearl") && toppings.contains("pudding")) {
		            img = cupImages.get("mangoPearlPudding");
		        } else if (toppings.contains("pearl")) {
		            img = cupImages.get("mangoPearl");
		        } else if (toppings.contains("pudding")) {
		            img = cupImages.get("mangoPudding");
		        } else {
		            img = cupImages.get("mangoJuice");
		        }
		    }

		    if (fruits.contains("lychee")) {
		        if (toppings.contains("pearl") && toppings.contains("pudding")) {
		            img = cupImages.get("lycheePearlPudding");;
		        } else if (toppings.contains("pearl")) {
		            img =  cupImages.get("lycheePearl");;
		        } else if (toppings.contains("pudding")) {
		            img =  cupImages.get("lycheePudding");;
		        } else {
		            img =  cupImages.get("lycheeJuice");;
		        }
		    }
		}
	/*
	public void setJuiceImage(Image juiceImg) {
		this.currentCupImg = juiceImg;
		this.img = juiceImg; // Update the displayed image
		// Update dimensions for the new image
		if (juiceImg != null) {
			this.width = juiceImg.getWidth(null);
			this.height = juiceImg.getHeight(null);
		}
	}
	*/
	
	/*
	public void setToppingImage( HashMap <String, Image> cupImages){
			
			//Image mangoPearl, Image lycheePearl, Image mangoPudding, Image lycheePudding, Image mangoPearlPudding, Image lycheePearlPudding) {
		if (fruits.contains("mango")) {
			if (toppings.contains("pearl") && toppings.contains("pudding")) {
				this.currentCupImg = cupImages.get("mangoPearlPudding");
			}
			else if (toppings.contains("pearl")) {
				this.currentCupImg =cupImages.get("mangoPearl");
			}
			else if (toppings.contains("pudding")) {
				this.currentCupImg = cupImages.get("mangoPudding");
			}
		}
		else if (fruits.contains("lychee")) {
			if (toppings.contains("pearl") && toppings.contains("pudding")) {
				this.currentCupImg = cupImages.get("lycheePearlPudding");
			}
			else if (toppings.contains("pearl")) {
				this.currentCupImg = cupImages.get("lycheePearl");
			}
			else if (toppings.contains("pudding")) {
				this.currentCupImg = cupImages.get("lycheePudding");
			}
		}
	}
	*/

	public boolean addFruit(String fruitType, Image juiceImage) {
		if (fruits.size()>=1)//max 1 fruit can be added
			return false;
		if (!fruits.contains(fruitType)) {//avoid duplicate fruit
			fruits.add(fruitType);
			//setJuiceImage(juiceImage);
			return true;
		}
		return false;
	}
	public boolean addTopping (String toppingType, Image mangoPearl, Image lycheePearl, Image mangoPudding, Image lycheePudding, Image mangoPearlPudding, Image lycheePearlPudding) {
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
