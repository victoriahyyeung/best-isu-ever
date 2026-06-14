import java.util.*;
public class Drink {
	private ArrayList<String>fruits;
	private ArrayList<String> toppings;


	//Description: Constructor for Drink class, creates a drink with specified fruits and toppings
	// Parameters: list of fruit types in the drink, list of topping types in the drink
	// Return: none
	public Drink(ArrayList<String> fruits, ArrayList<String> toppings) {
		this.fruits=new ArrayList<>(fruits);
		this.toppings=new ArrayList<>( toppings);
	}


	//Description: Compares this drink to another object for equality based on fruits and toppings (order independent)
	// Parameters:  the object to compare against
	// Return: true if drinks have same fruits and toppings (order doesn't matter), false otherwise
	public boolean equals(Object o) {
		Drink d = (Drink) o;
		if (d==null)
			return false;
		return this.fruits.containsAll(d.fruits) && d.fruits.containsAll(this.fruits) && this.toppings.containsAll(d.toppings) && d.toppings.containsAll(this.toppings);
	}


	//getters
	//Description: Returns the list of fruits in this drink
	// Parameters: None
	// Return: list of fruit types
	public ArrayList<String> getFruits(){
		return fruits;
	}

	//Description: Returns the list of toppings in this drink
	// Parameters: None
	// Return:  list of topping types
	public ArrayList<String> getToppings(){
		return toppings;
	}

}