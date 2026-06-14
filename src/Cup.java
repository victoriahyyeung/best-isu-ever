import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;

public class Cup extends Item {
	// ArrayLists of fruits and toppings
	private ArrayList<String> fruits;//(max 2)
	private ArrayList<String> toppings;//(max 2)


	//private Image currentCupImg;
	private Image baseCupImg;
	//private Image pearlImg;
	//private Image puddingImg;

	// hashmap of variation of drinks/cupp
	private HashMap <String, Image> cupImages = new HashMap <> ();

	// Description: Constructor for Cup class, creates an empty cup item
	// Parameters: the base cup image
	// Return: none
	public Cup (Image baseCup) {
		super (baseCup, "cup"); // calls item's constructor
		this.baseCupImg=baseCup;
		fruits=new ArrayList<>();
		toppings=new ArrayList<>();
		//cups cant do any of these actions, its still an item though so must.
		this.cuttable=false;
		this.blendable=false;
		this.cookable=false;
	}

	// Description: Updates the cup's displayed image based on current fruits and toppings
	// Parameters: mapping of drink combinations to their images
	// Return: void
	public void refreshImage(HashMap <String, Image> cupImages) {
		// checks cup contents
		if (fruits.contains("mango")) {
			if (toppings.contains("pearl") && toppings.contains("pudding")) {
				img = cupImages.get("mangoPearlPudding"); // set image as mango slush with pearls and pudding
			} else if (toppings.contains("pearl")) {
				img = cupImages.get("mangoPearl");// set image as mango slush with pearls
			} else if (toppings.contains("pudding")) {
				img = cupImages.get("mangoPudding"); // set image as mango slush with pudding
			} else {
				img = cupImages.get("mangoJuice"); // set image as mango slush
			}
		}

		if (fruits.contains("lychee")) {
			if (toppings.contains("pearl") && toppings.contains("pudding")) {
				img = cupImages.get("lycheePearlPudding"); // set image as lychee slush with pearls and pudding
			} else if (toppings.contains("pearl")) {
				img =  cupImages.get("lycheePearl"); // set image as lychee slush with pearls
			} else if (toppings.contains("pudding")) {
				img =  cupImages.get("lycheePudding");  // set image as lychee slush with pudding
			} else {
				img =  cupImages.get("lycheeJuice"); // set image as lychee slush
			}
		}
	}

	// Description: Adds a fruit juice to the cup (max 1 fruit per cup)
	// Parameters: type of fruit being added,image for the juice 
	// Return: true if fruit was added successfully, false otherwise
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

	// Description: Adds a topping to the cup (max 2 toppings per cup)
	// Parameters:  type of topping being added, images of mango pearl drink, lychee Pearl drink, mango Pudding drink,  lychee Pudding drink,  mango Pearl Pudding drink,  lychee Pearl Pudding drink
	// Return: true if topping was added successfully, false otherwise
	public boolean addTopping (String toppingType, Image mangoPearl, Image lycheePearl, Image mangoPudding, Image lycheePudding, Image mangoPearlPudding, Image lycheePearlPudding) {
		if (toppings.size()>=2)//max 2 toppings can be added
			return false;
		if (!toppings.contains(toppingType)) {
			toppings.add(toppingType);
			return true;
		}
		return false;

	}

	//getters
	// Description: access list of fruits in the cup
	// Parameters: None
	// Return: list of fruit types
	public ArrayList<String>getFruits(){
		return fruits;
	}

	// Description: acess list of toppings in the cup
	// Parameters: None
	// Return:  list of topping types
	public ArrayList<String>getToppings(){
		return toppings;
	}

	// Description: Checks if the cup contains a specific fruit
	// Parameters: the fruit type to check for
	// Return: true if fruit is present, false otherwise
	public boolean hasFruit(String fruit) {
		return fruits.contains(fruit);
	}

	// Description: Checks if the cup contains a specific topping
	// Parameters:  the topping type to check for
	// Return: true if topping is present, false otherwise
	public boolean hasTopping(String topping) {
		return toppings.contains(topping);
	}



}
