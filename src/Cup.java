import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

public class Cup extends Item {

	private ArrayList<String> cupIngredients = new ArrayList <> ();
	boolean hasMango;
	boolean hasLychee;
	boolean hasPearls;
	
	Image emptyCup;
	Image cupWithPearls;
	Image cupWithMango;
	Image cupWithLychee;
	Image cupWithMangoPearls;
	Image cupWithLycheePearls;
	
	boolean isSubmitted;
	
	public Cup () {
		super ((Toolkit.getDefaultToolkit().getImage("cup.png")));
	}
	
	public static void main(String[] args) {
		
	}

}
