import java.util.*;
public class Drink {
private ArrayList<String>fruits;
private ArrayList<String> toppings;
 public Drink(ArrayList<String> fruits, ArrayList<String> toppings) {
	 this.fruits=new ArrayList<>(fruits);
	 this.toppings=new ArrayList<>( toppings);
 }
 
 
 //equals
 public boolean equals(Drink d) {
	 if (d==null)
		 return false;
	 return this.fruits.containsAll(d.fruits) && d.fruits.containsAll(this.fruits) && this.toppings.containsAll(d.toppings) && d.toppings.containsAll(this.toppings);
 }
 
 
 //getters
 public ArrayList<String> getFruits(){
	 return fruits;
 }
 public ArrayList<String> getToppings(){
	 return toppings;
 }

}