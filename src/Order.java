import java.util.ArrayList;

// WHAT THE ORDER IS ASKING FOR
public class Order {
	private ArrayList<Drink> drinks; //each drink has fruits and toppings

	// Description: Constructor for Order class
	// Parameters:  the list of drinks that make up this order
	// Return: void none
	public Order(ArrayList<Drink> drinks) {
		this.drinks= new ArrayList<>(drinks);
	}

	// Description: Compares the order's drinks against cups on a tray to check if they match
	// Parameters: the list of cups currently on a tray
	// Return:true if the tray drinks perfectly match the order, false otherwise
	public boolean matches(ArrayList<Cup> trayCups) {

		System.out.println("\n=== MATCHING ORDER ===");
		System.out.println("Expected drinks: " + drinks.size());
		System.out.println("Actual cups: " + trayCups.size());


		if (trayCups.size() != drinks.size()) {
			System.out.println("wrong num");
			return false; //first check correct number of drinks
		}

		for(int i = 0; i < drinks.size(); i++) {
			System.out.println("Order drink " + i + ": fruits=" + drinks.get(i).getFruits() + ", toppings=" + drinks.get(i).getToppings());
			System.out.println("Tray cup " + i + ": fruits=" + trayCups.get(i).getFruits() + ", toppings=" + trayCups.get(i).getToppings());
		}

		// Make a copy of the drinks list to match against
		ArrayList<Drink> remainingDrinks = new ArrayList<>(drinks);

		for (Cup cup:trayCups) {
			Drink cupDrink=cupToDrink(cup);
			boolean found=false;
			for (int i=0;i<remainingDrinks.size();i++) {
				if (cupDrink.equals(remainingDrinks.get(i))) {
					remainingDrinks.remove(i);
					found = true;
					break;
				}
			}
			if (!found)
				return false;
		}
		return remainingDrinks.isEmpty();
	}

	// Description: Converts a Cup object into a Drink object for comparison
	// Parameters: The cup to convert
	// Return: Drink containing the cup's fruits and toppings
	private Drink cupToDrink(Cup cup) {
		return new Drink(cup.getFruits(), cup.getToppings());
	}

	// Description: Returns the list of drinks in this order
	// Parameters: None
	// Return: the list of drinks
	public ArrayList<Drink> getDrinks() {
		return drinks;
	}
}