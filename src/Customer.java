import java.awt.*;
import javax.swing.*;
import java.util.*;

public class Customer {
	private int x,y;
	private Image avatar;
	private Order order;
	private int patienceMax=100;
	private int currentPatience;
	private boolean isActive=true;

	private static final String[] FRUITS = {"mango", "lychee"};
	private static final String[] TOPPINGS = {"pearls", "pudding"};

	public Customer(int startX, int startY, Image avatarImg) {
		this.x=startX;
		this.y=startY;
		this.avatar=avatarImg;

		int numDrinks=(int)(Math.random()*6)+1;
		
		ArrayList<Drink> drinks=new ArrayList<>();
		for(int i=0; i<numDrinks;i++) {
			//FRUIT GENERATE
			int fruitCount=(int)(Math.random()*2)+1;//1 to 2 for now (later can change
			ArrayList<String> fruits =new ArrayList<>();
			for (int f=0;f<fruitCount;f++) {
				String fruit;
				if (Math.random()<0.5) {
					fruit="mango";
				}
				else {
					fruit="lychee";
				}
				if (!fruits.contains(fruit)) {
					fruits.add(fruit);
				}
			}
			
			//TOPPING GENERATE
			int toppingCount=(int)(Math.random()*3);
			ArrayList<String>toppings=new ArrayList<>();
			for (int t=0; t<toppingCount;t++) {
				String topping;
				if (Math.random()<0.5) {
					topping="pearls";
				}
				else {
					topping="pudding";
				}
				if(!toppings.contains(topping)) {
					toppings.add(topping);
				}
			}
			drinks.add(new Drink(fruits, toppings));
		}
		order=new Order(drinks);
		currentPatience=patienceMax;

	}


	public void decreasePatience() {
		if (!isActive) {
			return;
		}
		if(currentPatience>0) {
			currentPatience--;
		}
		if (currentPatience<=0) {
			isActive=false;
		}
	}
	
	public boolean isAngry() {
		return !isActive||currentPatience<=0;
	}
	
	public Order getOrder() {
		return order;
	}
	
	//getters
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getPatiencePercent() {
		return(currentPatience*100)/patienceMax;
	}

	public void draw(Graphics g) {
		g.drawImage(avatar, x, y, null);
		int barWidth=80;
		int barHeight=8;
		int fillWidth=barWidth*getPatiencePercent()/100;
		g.setColor(Color.RED);
		g.fillRect(x, y-12,  fillWidth, barHeight);
		g.setColor(Color.BLACK);
		g.drawRect(x, y-12, barWidth, barHeight);
	}
}
