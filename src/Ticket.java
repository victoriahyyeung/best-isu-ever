import java.awt.*;
import java.awt.geom.RoundRectangle2D;
public class Ticket {
	private Customer customer;
	private Order order;
	private int x,y;
	private int width=120;
	private int height=60;
	private boolean isSelected=false;//FOR DRAGGING
	
	public Ticket(Customer customer,int boardX,int boardY) {
		this.customer=customer;
		this.order=customer.getOrder();
		this.x=boardX;
		this.y=boardY;//where on tix board
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
		d.setFont(new Font("Times New Roman",Font.PLAIN,10));
		String orderText="Order: "+order.getDrinks().size()+ " drink(s)";
		d.drawString(orderText,x+5,y+15);
		if( isSelected) {
			d.setColor(Color.CYAN);
			d.fill(new RoundRectangle2D.Float(x,y,width,height,15,15));
		}
	}
	
}
