import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.*;

abstract class Item {
    
    int x, y, width, height;
    Image img;
    boolean isHeld; //true when being dragged
    
    boolean blendable;
    boolean cuttable;
    boolean cookable;
    
    
    public Item (Image img) {
    	this.img = img;
    	if (img!=null) {//getting width and height
    		this.width=img.getWidth(null);
    		this.height=img.getHeight(null);
    	}
    	else
    		System.out.print("ITEM IMAGE IS NULL!!!!");
    	//default, subclasses must override
    	this.blendable=false;
    	this.cuttable=false;
    	this.cookable=false;
    }
    
    public void setPosition(int newX, int newY) {
    	this.x = newX;
        this.y = newY;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    //simplifies the main
    public boolean contains (int mx, int my) {
    	return getBounds().contains(mx, my);
    }
    //for organizion and convenience when drawing
    public void draw(Graphics g) {
    	if (img!=null) {
    		g.drawImage(img,  x, y, null);
    	}
    	
    }
    
}