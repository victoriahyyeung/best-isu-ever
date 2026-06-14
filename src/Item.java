import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.*;

abstract class Item {

	// item position and dimensions
	protected int x, y, width, height;
	protected Image img; //item image
	protected boolean isHeld; //true when being dragged

	// whether item is blendable/cuttable/cookable depending on item nature
	protected boolean blendable;
	protected boolean cuttable;
	protected boolean cookable;
	protected String type;//fruit, pearl, cup, etc


	// categorizes item as fruit or topping
	protected boolean isFruit;
	protected boolean isTopping;

	// Description: Constructor for Item class, initializes an item with an image and type
	// Parameters:the image to display for this item,  the type of item (fruit, pearl, cup, etc.)
	// Return: none
	public Item (Image img, String type) {
		this.type = type;
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

	// Description: Sets the position of the item on screen
	// Parameters: int newX - new x coordinate, int newY - new y coordinate
	// Return: void
	public void setPosition(int newX, int newY) {
		this.x = newX;
		this.y = newY;
	}

	// Description: Returns the x coordinate of the item
    // Parameters: None
    // Return: x position
	public int getX() {
		return this.x;
	}

	 // Description: Returns the y coordinate of the item
    // Parameters: None
    // Return: y position
	public int getY() {
		return this.y;
	}
	
	// Description: Returns whether this item is a fruit
    // Parameters: None
    // Return: true if item is fruit, false otherwise
	public boolean isFruit() {
		return isFruit;
	}

	// Description: Returns whether this item is a topping
    // Parameters: None
    // Return:true if item is topping, false otherwise
	public boolean isTopping() {
		return isTopping;
	}

	// Description: Returns whether this item can be blended
    // Parameters: None
    // Return: true if blendable, false otherwise
	public boolean isBlendable() {
		return blendable;
	}

	 // Description: Returns whether this item can be cut
    // Parameters: None
    // Return: true if cuttable, false otherwise
	public boolean isCuttable() {
		return cuttable;
	}

	 // Description: Returns whether this item can be cooked
    // Parameters: None
    // Return:true if cookable, false otherwise
	public boolean isCookable() {
		return cookable;
	}

	 // Description: Returns the bounding rectangle of the item for collision detection
    // Parameters: None
    // Return: the bounding rectangle
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
	 // Description: Checks if a point (mouse click) is within the item's boundaries
    // Parameters:  mouse x coordinate, mouse y coordinate
    // Return: true if point is inside item, false otherwise
	public boolean contains (int mx, int my) {
		return getBounds().contains(mx, my);
	}
	// Description: Draws the item's image at its current position
    // Parameters: graphics object for drawing
    // Return: void
	public void draw(Graphics g) {
		if (img!=null) {
			g.drawImage(img,  x, y, null);
		}

	}


}