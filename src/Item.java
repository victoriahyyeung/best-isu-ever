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
    String type;//fruit, pearl, cup, etc
    
<<<<<<< HEAD
    public Item (Image img, String type) {
    	this.type=type;
=======
   protected boolean isFruit;
   protected boolean isTopping;
    
    
    public Item (Image img) {
>>>>>>> branch 'main' of https://github.com/victoriahyyeung/best-isu-ever.git
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
    
    public boolean isFruit() {
    	return isFruit;
    }
    
    public boolean isTopping() {
    	return isTopping;
    }
    
    public boolean isBlendable() {
    	return blendable;
    }
    
    public boolean isCuttable() {
    	return cuttable;
    }
    
    public boolean isCookable() {
    	return cookable;
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