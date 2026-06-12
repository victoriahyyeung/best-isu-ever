import java.awt.*;
import java.util.*;

import javax.swing.ImageIcon;

public class Customer {
	private int x,y;
	private Image avatar;
	private Order order;
	private int patienceMax=100;
	private int currentPatience;
	private boolean isActive=true;
	private String customerType;//orangeCat
	private String currentEmotion;//happy, neutral, impatience, angry for now
	private String state;//spawn, ordering, waiting (to be served), served, leaving
	private int targetX, targetY;//where customer is walking towards 
	private boolean moving;//moving or not (t/f)
	private static final int SPEED=2;//2px/50ms (per frame)
	private int waitingSpotIndex=-1;//FOR AFTEr they order and then they are waitng to be served

	private int bubbleFrames;//bubble will show when >0;


	private static final String[]TYPES= {"orangeCat"};
	private static final String[] FRUITS = {"mango", "lychee"};
	private static final String[] TOPPINGS = {"pearls", "pudding"};

	public Customer(int startX, int startY, HashMap<String,HashMap<String,Image>>images, int orderX, int orderY) {
		this.x=startX;
		this.y=startY;
		Random rand=new Random();
		this.customerType=TYPES[rand.nextInt(TYPES.length)];
		this.currentEmotion="neutral";
		this.avatar=images.get(customerType).get(currentEmotion);
		this.state="SPAWN";
		setTarget(orderX,orderY);
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
		if (state==null||!state.equals("WAITING"))
			return;

		else if (!isActive) {
			return;
		}
		if(currentPatience>0) {
			currentPatience--;
		}
		if (currentPatience<=0) {
			isActive=false;
		}
	}

	public void updateEmotion(HashMap<String, HashMap<String,Image>>images) {
		String newEmo;
		if(currentPatience>50)
			newEmo="happy";
		else if(currentPatience>30)
			newEmo="neutral";
		else if (currentPatience>0)
			newEmo="impatient";
		else
			newEmo="angry";
		if(!(newEmo.equals(currentEmotion))) {
			currentEmotion=newEmo;
			this.avatar=images.get(customerType).get(currentEmotion);
		}
	}
	public void updateBubble() {
		if (bubbleFrames>0)
			bubbleFrames--;//evrey frame do this
	}
	public void startBubble() {
		bubbleFrames=30; //for 1.5 seconds its 30 frames, each 50 ms.
	}
	public boolean isBubbleVisible() {
		return bubbleFrames>0;
	}

	public boolean isAngry() {
		return !isActive||currentPatience<=0;
	}

	public boolean hasArrived() {//boolean to easilycheck if still moving/arrived
		return !moving; //false moving = true arrived
	}

	public void updateMovement() {
		if(!moving)
			return;//not moving so done

		int dx=targetX-x;//difference
		int dy=targetY-y;
		if(Math.abs(dx)<=SPEED&&Math.abs(dy)<=SPEED) {//for when its like p close, dont waste more frames on moving so just snap tp the target
			x=targetX;
			y=targetY;
			moving=false;
			System.out.println("Customer arrived at target:("+x+","+y+")");
			return;
		}

		if(dx!=0) {
			if(dx>0) {//Rihgt
				if (dx<SPEED) 
					x=targetX;//smap
				else
					x+=SPEED;//NO snapp!
			}
			else {//L (dx<0) Samenthing jsut differnt dir
				if (-dx<SPEED) 
					x=targetX;

				else
					x-=SPEED;
			}
		}
		if(dy!=0) {
			if(dy>0) {//D
				if(dy<SPEED)
					y=targetY;
				else
					y+=SPEED;
			}
			else {//Up
				if (-dy<SPEED)
					y=targetY;
				else
					y-=SPEED;
			}
		}


	}


	//getters
	public Order getOrder() {
		return order;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getPatiencePercent() {
		return(currentPatience*100)/patienceMax;
	}
	public String getState() {
		return state;
	}
	public int getWaitingSpotIndex() {
		return waitingSpotIndex;
	}

	//setters
	public void setTarget(int tx, int ty) {
		this.targetX=tx;
		this.targetY=ty;
		moving=true;
	}
	public void setState(String s) {
		this.state=s;
	}
	public void setWaitingSpotIndex(int i) {
		this.waitingSpotIndex=i;
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
		if (bubbleFrames>0) {
			g.setColor(Color.WHITE);
			g.fillRoundRect(x-20, y-40, 50, 30, 10, 10);
			g.setColor(Color.BLACK);
			g.drawRoundRect(x-20, y-40, 60, 30, 10, 10);
			g.drawString("Ordering...",x-15,y-20);
		}
	}
}
