import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.*;

abstract class Item {
    
    int itemX;
    int itemY;
    Image itemImg;
    boolean itemClicked;
    boolean isHeld;
    
    boolean blendable;
    boolean cuttable;
    boolean cookable;
    
    
    int width;
    int height;
    
    public Item (Image itemImg) {
    	this.itemImg = itemImg;
    }
    
    public void placeDown(int newX, int newY) {
    	this.itemX = newX;
        this.itemY = newY;
        this.isHeld = false;
    }
    
    public Rectangle itemBounds() {
        return new Rectangle(itemX, itemY, width, height);
    }
    
    public boolean isClicked(int mouseX, int mouseY) {
        return itemBounds().contains(mouseX, mouseY);
    }
    
    
  
    public void mouseClicked(MouseEvent e) {
		int x = e.getX ();
		int y = e.getY ();
	}
    
    


}