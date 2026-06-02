import java.awt.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.sound.sampled.FloatControl;
public class Main extends JPanel implements MouseListener, KeyListener, MouseMotionListener {
	Image home, instructions1, instructions2, instructions3, instructions4, lockedLevels, unlockedLevels, startImg, gameLevel1, gameLevel2, credits, highScore, victory;
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

	JTextField usernameField;

	ArrayList<Item> ingredientsOnScreen = new ArrayList<>();
	
	private Item selectedItem = null;
	
	int offsetX;
	int offsetY;

	public void spawnMango() {
		Item m = new Item("mango"); 
		m.setPosition(x-32, y-27);
		ingredientsOnScreen.add(m);
		repaint();
	}
	
	public void spawnLychee() {
		Lychee l = new Lychee(); 
		l.setPosition(x-32, y-27);
		ingredientsOnScreen.add(l);
		repaint();
	}
	
	public void spawnPearl() {
		Pearl p = new Pearl();
		p.setPosition(x-31, y - 22);
		ingredientsOnScreen.add(p);
		repaint();
	}

	public Main(){
		setPreferredSize (new Dimension (390, 700));

		MediaTracker tracker = new MediaTracker (this);
		home = Toolkit.getDefaultToolkit ().getImage ("home.png");
		tracker.addImage (home, 0);
		instructions1 = Toolkit.getDefaultToolkit ().getImage ("instructions1.png");
		tracker.addImage (instructions1, 1);
		instructions2 = Toolkit.getDefaultToolkit ().getImage ("instructions2.png");
		tracker.addImage (instructions2, 2);
		instructions3 = Toolkit.getDefaultToolkit ().getImage ("instructions3.png");
		tracker.addImage (instructions3, 3);
		instructions4 = Toolkit.getDefaultToolkit ().getImage ("instructions4.png");
		tracker.addImage (instructions4, 4);
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

		usernameField = new JTextField(10); 
		usernameField.setBounds(66, 300, 258, 21); 
		usernameField.setText("user xxxxxxx");
		usernameField.setForeground(Color.BLACK);
		usernameField.setVisible(false); 



		this.setLayout(null); // Use absolute positioning for the box
		this.add(usernameField);

		try
		{
			tracker.waitForAll ();
		}  
		catch (InterruptedException e){}

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (screenState == 0) {
			g.drawImage(home, 0, 0, 390, 700, this);
			usernameField.setVisible(true);
		}

		else {
			usernameField.setVisible(false);
		}
		if (screenState == 1) {
			g.drawImage(instructions1, 0, 0, 390, 700, this);

		}
		if (screenState == 2) {
			g.drawImage(instructions2, 0, 0, 390, 700, this);

		}
		if (screenState == 3) {
			g.drawImage(instructions3, 0, 0, 390, 700, this);

		}
		if (screenState == 4) {
			g.drawImage(instructions4, 0, 0, 390, 700, this);

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
			
			g.drawImage(gameLevel1, 0, 0, 390, 700, this);
			for (Item i : ingredientsOnScreen) {
				if (i.img != null) {
					g.drawImage(i.img, i.x, i.y, this);
				}
			}

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
		System.out.println("x: " + x + " y: " + y);

		// Home screen
		if (screenState == 0) {

			// Level 1 Play screen
			if (x >= 126 && x <= 266 && y >= 338 && y <= 394) {
				screenState = 9;
			}

			// Instructions slide 1
			else if (x >= 126 && x <= 266 && y >= 413 && y <= 469) {
				screenState = 1;
			}

		}

		// Instructions slide 1
		else if (screenState == 1) {

			// Home screen
			if (x >= 20 && x <= 95 && y >= 18 && y <= 52) {
				screenState = 0;
			}

			// Instructions slide 2
			else if (x >= 357 && x <= 382 && y >= 337 && y <= 364) {
				screenState = 2;
			}
		}

		// Instructions slide 2
		else if (screenState == 2) {

			// Home screen
			if (x >= 20 && x <= 95 && y >= 18 && y <= 52) {
				screenState = 0;
			}

			// Instructions slide 1
			else if (x >= 9 && x <= 34 && y >= 333 && y <= 365) {
				screenState = 1;
			}

			// Instructions slide 3
			else if (x >= 357 && x <= 382 && y >= 337 && y <= 364) {
				screenState = 3;
			}
		}

		// Instructions slide 3
		else if (screenState == 3) {
			// Home screen
			if (x >= 20 && x <= 95 && y >= 18 && y <= 52) {
				screenState = 0;
			}

			// Instructions slide 2
			else if (x >= 9 && x <= 34 && y >= 333 && y <= 365) {
				screenState = 2;
			}

			// Instructions slide 4
			else if (x >= 357 && x <= 382 && y >= 337 && y <= 364) {
				screenState = 4;
			}
		}

		// Instructions slide 4
		else if (screenState == 4) {
			// Home screen
			if (x >= 20 && x <= 95 && y >= 18 && y <= 52) {
				screenState = 0;
			}

			// Instructions slide 3
			else if (x >= 9 && x <= 34 && y >= 333 && y <= 365) {
				screenState = 3;
			}

		}

		else if (screenState == 9) {
			if (x >= 16 && x <= 71 && y >= 386 && y <= 444) {
				spawnMango();
			}
			
			if (x>= 16 && x <= 72 && y >= 448 && y <= 505) {
				spawnLychee();
			}
			
			if (x >= 16 && x <= 71 && y >= 510 && y <= 566) {
				spawnPearl();
			}
			
		}
		repaint(); 
	}
	int x, y;
	public void mouseClicked(MouseEvent e) {
		x = e.getX ();
		y = e.getY ();
		handleAction (x, y);
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
	}


	public static void main(String[] args) {
		JFrame frame = new JFrame ("Bubble Cafe");
		Main panel = new Main (); 
		frame.add(panel);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.addMouseListener(panel);
		panel.addMouseMotionListener(panel);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (int i = ingredientsOnScreen.size() - 1; i >= 0; i--) {
	        Item item = ingredientsOnScreen.get(i);
	        if (item.contains(e.getX(), e.getY())) {
	            selectedItem = item;
	            offsetX = e.getX() - item.x;
	            offsetY = e.getY() - item.y;
	            return; 
	        }
	    }
	    
	    
	    if (screenState == 9) {
	        handleAction(e.getX(), e.getY());
	    }

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		selectedItem = null;

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (selectedItem != null) {
	        selectedItem.x = e.getX() - offsetX;
	        selectedItem.y = e.getY() - offsetY;
	        repaint(); 
	    }

		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}