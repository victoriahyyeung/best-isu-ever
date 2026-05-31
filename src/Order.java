import java.util.ArrayList;
public class Order  {
	private ArrayList<String> reqIngredients;
	private boolean completed;
	
	public Order(String drinkType) {
        this.reqIngredients = new ArrayList<>();
        this.completed = false;
        
        // Define the recipe based on the drink type
        if (drinkType.equals("mango")) {
            reqIngredients.add("mangoSlush");
            reqIngredients.add("tapioca");
        } 
        else if (drinkType.equals("lychee")) {
            reqIngredients.add("lycheeSlush");
            reqIngredients.add("tapioca");
        }
        else if (drinkType.equals("justMango")) {
        	reqIngredients.add("mango");
        }
        else if (drinkType.equals("justLychee")) {
        	reqIngredients.add("lychee");
        }
        
    }
	
	public boolean matchOrder(ArrayList<String> cupIngredients) {
		return cupIngredients.containsAll(reqIngredients) && cupIngredients.size() == reqIngredients.size();
	}
	
    
}