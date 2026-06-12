import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
public class Ticket {
	private Customer customer;
	private Order order;
	private int x,y;
	private int width=140;
	private int height=160;
	private boolean isSelected=false;//FOR DRAGGING
	private HashMap<String, Image>icons;
	
	public Ticket(Customer customer,int boardX,int boardY, HashMap<String,Image>icons) {
		this.customer=customer;
		this.order=customer.getOrder();
		this.x=boardX;
		this.y=boardY;//where on tix board
		this.icons=icons;
	}
	
	public Rectangle getBorders() {//to check cliciking
		return new Rectangle(x,y,width,height);
	}
	public boolean contains (int mx,int my) {//mouse coords
		return getBorders().contains(mx,my);
	}
	//GETTERS
	public Order getOrder() {
		return order;
	}
	public Customer getCustomer() {
		return customer;
	}
	//SETTERS
	public void setPosition(int newX,int newY) {
		this.x=newX;
		this.y=newY;
	}
	public void setSelected(boolean s) {//from main
		isSelected=s;
	}
	
	//DRAW
	public void draw(Graphics g) {
		Graphics2D d=(Graphics2D) g;
		d.setColor(Color.WHITE);
		d.fill(new RoundRectangle2D.Float(x,y,width,height,15,15));
		d.setColor(Color.BLACK);
		d.draw(new RoundRectangle2D.Float(x,y,width,height,15,15));
	
		if (isSelected) {//WOWOWOWOW CHANGES COLOR WHEN SELECTED!!!
			d.setColor(Color.PINK);
			d.fill(new RoundRectangle2D.Float(x,y,width,height,15,15));
		}
		
		d.setFont(new Font("Times New Roman",Font.BOLD,10));
		d.setColor(Color.BLACK);
		String orderText="Order: "+order.getDrinks().size()+ " drink(s)";
		d.drawString(orderText,x+10,y+10);
		
		int rowY=y+30;//each row
		int iconSize=20;
		int drinkInd=0;
		int totalDrinks=order.getDrinks().size();
		for(int row=0;row<6;row++) {
			if(drinkInd<totalDrinks) {
				Drink drink=order.getDrinks().get(drinkInd);
				int currentX=x+10;
				//FRUITS
				for(String fruit:drink.getFruits()) {
					Image img=icons.get(fruit);
					if(img!=null) {
						d.drawImage(img, currentX, rowY, iconSize, iconSize, null);
						currentX+=iconSize+5;
					}
				}
				//TOPPINGS
				for(String topping:drink.getToppings()) {
					Image img=icons.get(topping);
					if (img!=null) {
						d.drawImage(img, currentX, rowY, iconSize, iconSize, null);
						currentX+=iconSize+5;
					}
				}
				drinkInd++;
			}
			rowY+=22;
		}
		
		
	}
	
}
