import javax.swing.*;

public class Customer {
h
	private JProgressBar patienceBar;
    private int patienceTime = 100; 
    private int currentTime = 100;
	
    String[] posOrder = {"mango", "lychee", "justMango", "justLychee"};
	String randomOrder = posOrder[(int)(Math.random() * (3-0+1)) + 0];
	Order cusOrder = new Order (randomOrder);
	
	public Customer() {
		
		patienceBar = new JProgressBar(0, patienceTime);
        patienceBar.setValue(patienceTime);
       // patienceBar.setStringPainted(false);
		
	}
	
	
	public void decreasePatience() {
		if (currentTime > 0) {
            currentTime--; 
            patienceBar.setValue(currentTime);
        } else {
            // customer leaves
        }
	}
	
}
