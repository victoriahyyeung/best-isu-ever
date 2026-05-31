import java.awt.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.sound.sampled.FloatControl;
public class Main extends JPanel implements MouseListener {
	Image home, instructions1, instructions2, instructions3, instructions4, instructions5, lockedLevels, unlockedLevels, startImg, gameLevel1, gameLevel2, credits, highScore, victory;
	// Screen States
	// 0 - Home
	// 1 - Instructions (slide 1)
	// 2 – Instructions (slide 2)
	// 3 – Instructions (slide 3)
	// 4 – Instructions (slide 4)
	// 5 – Instructions (slide 5)
	// 6 – Locked Levels
	// 7 – Unlocked Levels
	// 8 – Start Screen
	// 9 – Game Level 1
	// 10 – Game Level 2
	// 11 – Credits
	// 12 – High Score
	// 13 – Victory Screen
	int screenState = 0;

	public Main(){
		setPreferredSize (new Dimension (540, 960));
		
		MediaTracker tracker = new MediaTracker (this);
		home = Toolkit.getDefaultToolkit ().getImage ("home.PNG");
		tracker.addImage (home, 0);
		instructions1 = Toolkit.getDefaultToolkit ().getImage ("instructions1.png");
		tracker.addImage (instructions1, 1);
		instructions2 = Toolkit.getDefaultToolkit ().getImage ("instructions2.png");
		tracker.addImage (instructions2, 2);
		instructions3 = Toolkit.getDefaultToolkit ().getImage ("instructions3.png");
		tracker.addImage (instructions3, 3);
		instructions4 = Toolkit.getDefaultToolkit ().getImage ("instructions4.png");
		tracker.addImage (instructions4, 4);
		instructions5 = Toolkit.getDefaultToolkit ().getImage ("instructions5.png");
		tracker.addImage (instructions5, 5);
		lockedLevels = Toolkit.getDefaultToolkit ().getImage ("lockedLevels.png");
		tracker.addImage (lockedLevels, 6);
		unlockedLevels = Toolkit.getDefaultToolkit ().getImage ("unlockedLevels.png");
		tracker.addImage (unlockedLevels, 7);
		startImg = Toolkit.getDefaultToolkit ().getImage ("startImg.png");
		tracker.addImage (startImg, 8);
		gameLevel1 = Toolkit.getDefaultToolkit ().getImage ("gameLevel1.png");
		tracker.addImage (gameLevel1, 9);
		gameLevel2 = Toolkit.getDefaultToolkit ().getImage ("gameLevel2.png");
		tracker.addImage (gameLevel2, 10);
		credits = Toolkit.getDefaultToolkit ().getImage ("credits.png");
		tracker.addImage (credits, 11);
		highScore = Toolkit.getDefaultToolkit ().getImage ("highScore.png");
		tracker.addImage (highScore, 12);
		victory = Toolkit.getDefaultToolkit ().getImage ("victory.png");
		tracker.addImage (victory, 13);

		try
		{
			tracker.waitForAll ();
		}  
		catch (InterruptedException e){}

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (screenState == 0) {
			Customer customer = new Customer ();
			
		}
		if (screenState == 1) {
		}
		if (screenState == 2) {
		}
		if (screenState == 3) {
		}
		if (screenState == 4) {
		} 
		if (screenState == 5) {
		}
		if (screenState == 6) {
		}
		if (screenState == 7) {
		}
		if (screenState == 8) {
		}
		if (screenState == 9) {
		}
		if (screenState == 10) {
		}
		if (screenState == 11) {
		}
		if (screenState == 12) {
		}
		if (screenState == 13) {
		}
	}

	public void handleAction(int x, int y) {

	}

	public void mouseClicked(MouseEvent e) {
		int x, y;
		x = e.getX ();
		y = e.getY ();
		handleAction (x, y);
	}
	public static void main(String[] args) {
		JFrame frame = new JFrame ("Bubble Cafe");
		Main panel = new Main (); 
		frame.add(panel);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addMouseListener (panel);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
}