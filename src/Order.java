import java.util.ArrayList;

// WHAT THE ORDER IS ASKING FOR
public class Order {
    private ArrayList<Drink> drinks; //each drink has fruits and toppings

    public Order(ArrayList<Drink> drinks) {
        this.drinks= new ArrayList<>(drinks);
    }

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

    private Drink cupToDrink(Cup cup) {
        return new Drink(cup.getFruits(), cup.getToppings());
    }

    public ArrayList<Drink> getDrinks() {
        return drinks;
    }
}