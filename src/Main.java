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
public class Main extends JPanel implements MouseListener, KeyListener, MouseMotionListener, ActionListener{
	private Cup currentCup;
	private ArrayList<Cup> trayDrinks;
	private ArrayList<Customer> customers=new ArrayList<>();
	private Timer gameTimer;//FOR THE GAME REFRESH REPAINTING
	private Timer patienceTimer;
	private Timer customerSpawnTimer;//spawn customer every n seconds
	private JProgressBar actionBar;//progress of cooking/blending
	private int score=0;
	private Item selectedItem=null;
	private boolean spacePressed=false;
	//stations
	//!v- NEEDA FIX COORDIANTES
	private Rectangle chopStation1=new Rectangle (177, 571, 51, 53);
	private Rectangle chopStation2=new Rectangle (231, 571, 51, 53);


	private Rectangle blendStation=new Rectangle (150, 150, 50, 50);
	private Rectangle cupStation=new Rectangle (250, 250, 50, 50);
	private Rectangle trayStation=new Rectangle (350, 350, 50, 50);
	private Rectangle servingStation=new Rectangle(450, 450, 50,50);

	private Fruit blendingFruit;
	private int blendProgress;
	private Timer blendTimer;

	//IMAGES
	private Image mangoFresh,mangoCut,mangoBlended;
	private Image lycheeFresh, lycheeCut,lycheeBlended;
	private Image cupBase, pearlIcon, puddingIcon;
	private Image customerImg;
	private Image pearlUncooked, pearlCooked;




	Image home, instructions1, instructions2, instructions3, instructions4, lockedLevels, unlockedLevels, startImg, gameLevel1, gameLevel2, credits, highScore, victory;
	// Screen States
	// 0 - Home
	// 1 - Instructions (slide 1)
	// 2 – Instructions (slide 2)
	// 3 – Instructions (slide 3)
	// 4 – Instructions (slide 4)
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

	int offsetX;
	int offsetY;

	Fruit selectedFruit;

	public void spawnMango() {
		Fruit m=new Fruit("mango", mangoFresh,mangoCut,mangoBlended);
		m.setPosition(x-32, y-27);
		ingredientsOnScreen.add(m);
		repaint();
	}

	public void spawnLychee() {
		Fruit l=new Fruit("lychee", lycheeFresh,lycheeCut,lycheeBlended);
		l.setPosition(x-32, y-27);
		ingredientsOnScreen.add(l);
		repaint();
	}

	public void spawnPearl() {
		Pearl p = new Pearl(pearlUncooked, pearlCooked);
		p.setPosition(x-31, y - 22);
		ingredientsOnScreen.add(p);
		repaint();
	}

	public Main(){
		setPreferredSize (new Dimension (390, 700));
		loadAllImages();//btw this is only for in game images
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

		currentCup=new Cup(cupBase, pearlIcon, puddingIcon);
		trayDrinks=new ArrayList<>();
		customers.add(new Customer(50, 300, this.customerImg));

		//bar for blend/cook/cut
		actionBar= new JProgressBar(0,100);
		actionBar.setVisible(false);//not visible til action is doing
		this.add(actionBar);

		gameTimer=new Timer (50, this);
		gameTimer.start();

		patienceTimer=new Timer( 1000, this);
		patienceTimer.start();

		addKeyListener(this);
		setFocusable(true);
		this.setLayout(null); // Use absolute positioning for the box
		this.add(usernameField);

		blendTimer=new Timer(30, this);
		blendTimer.stop();//not initially running

		customerSpawnTimer=new Timer(8000,this);
		customerSpawnTimer.start();

		try
		{
			tracker.waitForAll ();
		}  
		catch (InterruptedException e){}

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==gameTimer) {//maybe do dif method?
			repaint();
		}
		//CUSOTMER PATIENCE
		else if(e.getSource()==patienceTimer) {
			for (int i=customers.size()-1;i>=0;i--) {
				Customer c=customers.get(i);
				c.decreasePatience();
				if (c.isAngry()) {
					customers.remove(i);
				}
			}
		}
		//BLENDING
		else if (e.getSource()==blendTimer) {
			blendProgress+=5;
			if (blendProgress>=100) {
				blendTimer.stop();
				actionBar.setVisible(false);
				if (blendingFruit != null) {
					blendingFruit.setBlended();
					ingredientsOnScreen.add(blendingFruit);
				}
				blendingFruit=null;
				repaint();
			}
			else
				actionBar.setValue(blendProgress);
		}
		//CUSTOMER SPAWN 
		else if(e.getSource()==customerSpawnTimer) {
			if(screenState==9) {
				customers.add(new Customer(300,10,customerImg));
				repaint();
			}
		}
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
			//score
			g.setColor(Color.BLACK);
			g.setFont(new Font("Times New Roman", Font.BOLD,16));
			g.drawString("Score: "+score,10,10);
			for (Item i : ingredientsOnScreen) {
				if (i.img != null) {
					g.drawImage(i.img, i.x, i.y, this);
				}


				if (i.isFruit()) {
					Fruit f = (Fruit) i;
					if (f.isOnChopStation() && !f.isCut()) {
						int barWidth=40;
						int barHeight=8;
						int fillWidth =barWidth*f.getChopProgress() / 100;
						int barX=f.x+(f.width-barWidth)/2;
						int barY=f.y -12;
						g.setColor(Color.LIGHT_GRAY);
						g.fillRect(barX, barY, barWidth, barHeight);
						g.setColor(Color.RED);//!v-CHANGE!!!
						g.fillRect(barX, barY, fillWidth, barHeight);
						g.setColor(Color.BLACK);
						g.drawRect(barX, barY, barWidth, barHeight);
					}
				}

			}
			if (currentCup!=null && selectedItem!=currentCup) {
				currentCup.drawLayered(g, cupStation.x, cupStation.y);

			}
			if (actionBar.isVisible()) {
				actionBar.paint(g);
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
				System.out.println("spanwed mango");
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

	}

	public void keyPressed(KeyEvent e) {//for some variety ig we do SPACE
		if (e.getKeyCode()==KeyEvent.VK_SPACE)	{
			if (!spacePressed) {
				spacePressed=true;
				if(selectedItem !=null&& selectedItem.type.equals("fruit")) {
					Fruit f=(Fruit) selectedItem;
					if (f.isOnChopStation() && !f.isCut()) {
						f.cut();
						repaint();
					}
				}
			}
		}

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
		panel.addKeyListener(panel);
		panel.setFocusable(true);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_SPACE) {
			spacePressed=false;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		if (screenState == 9) {
			boolean itemSelected=false;//default
			for (int i = ingredientsOnScreen.size() - 1; i >= 0; i--) {
				Item item = ingredientsOnScreen.get(i);
				if (item.contains(x, y)) {
					selectedItem = item;
					offsetX=x- item.x;
					offsetY =y - item.y;
					if(selectedItem.isFruit()) {
						Fruit f=(Fruit) selectedItem;
						if(f.isOnChopStation()&& !f.isCut()) {
							f.cut();
							repaint();
						}
					}
					itemSelected=true;
					return; 
				}
			}
			if (!itemSelected) 
				handleAction(x,y);
		}
		else
			handleAction(x,y);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (selectedItem==null)
			return;

		int mx=e.getX();
		int my=e.getY();

		//!v- MIKA WHAT IS THIS BROTHER
		int centerX = selectedItem.x + (int) Math.round(selectedItem.width * 0.5);
		int centerY = selectedItem.y + (int) Math.round(selectedItem.height * 0.5);


		if (selectedItem.type.equals("fruit")){
			Fruit f=(Fruit) selectedItem;
			f.setOnChopStation(false);//default

			//chopboard
			if (chopStation1.contains(mx,my) || chopStation2.contains(mx, my)) {
				f.setOnChopStation(true);
				//if (!f.isCut()) {//NOT CALLING cut() here, do in keyPressed so that it doesnt auto cut for placign down ykwim!????
				ingredientsOnScreen.add(f);
				selectedItem=f;//keep selected
				repaint();
				return;
			}
			//blender
			else if (blendStation.contains(mx,my)) {
				if (f.isCut()&& !f.isBlended()) {
					startBlendingAnimation(f);
				}
				else {
					JOptionPane.showMessageDialog(this, "Chop the fruit first!");
					ingredientsOnScreen.add(f);
				}
			}
			//cup station (add components to cup)
			else if (cupStation.contains (mx,my)) {
				if (f.isBlended()) {
					if (currentCup==null) {
						currentCup=new Cup (cupBase,pearlIcon,puddingIcon);
					}
					currentCup.addFruit(f.getFruitType());
				}else {
					JOptionPane.showMessageDialog(this, "Blend the fruit first!!!!");
					ingredientsOnScreen.add(f);
				}
			}
			else {
				ingredientsOnScreen.add(f);
			}
		}
		else if(selectedItem.type.equals("pearl")) {
			Pearl p=(Pearl) selectedItem;
			if (cupStation.contains(mx,my)) {
				if (currentCup==null) {
					currentCup=new Cup(cupBase,pearlIcon,puddingIcon);
				}
				currentCup.addTopping("pearls");//good
			}
			else {
				ingredientsOnScreen.add(p);
			}
		}
		else if (selectedItem.type.equals("cup")) {
			Cup cup=(Cup) selectedItem;
			if (trayStation.contains(mx,my)) {
				if (!cup.getFruits().isEmpty()|| !cup.getToppings().isEmpty()) {
					trayDrinks.add(cup);
					currentCup=new Cup(cupBase,pearlIcon,puddingIcon);
					JOptionPane.showMessageDialog(this, "Drink added to tray.");;

				}
				else {
					JOptionPane.showMessageDialog(this, "empty cup...");
					currentCup=cup;
				}
			}
			else if(servingStation.contains(mx,my)) {
				boolean served=false;//default
				for (int i=0;i<customers.size();i++) {//runs through each customer til correct order found, or if not found
					Customer c=customers.get(i);
					if(c.getOrder().matches(trayDrinks)) {
						customers.remove(i);
						trayDrinks.clear(); //next tray!
						JOptionPane.showMessageDialog(this, "served! +100 points");
						served=true;
						score+=100;
						break;
					}
				}
				if(!served) {//not matching
					JOptionPane.showMessageDialog(this,"this tray doesn't match any order!!");
					currentCup=cup;//put last cup back into hand
				}
			}
			else
				currentCup=cup;
		}


		selectedItem=null;
		repaint();

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

	private void loadAllImages() {
		MediaTracker tracker=new MediaTracker(this);
		//////////MANGO
		mangoFresh=Toolkit.getDefaultToolkit().getImage("mango_fresh.png");
		tracker.addImage(mangoFresh, 0);
		mangoCut=Toolkit.getDefaultToolkit().getImage("mango_cut.png");
		tracker.addImage(mangoCut, 1);
		mangoBlended=Toolkit.getDefaultToolkit().getImage("mango_blended.png");
		tracker.addImage(mangoBlended, 2);
		//////////LYCHEE
		lycheeFresh=Toolkit.getDefaultToolkit().getImage("lychee_fresh.png");
		tracker.addImage(lycheeFresh, 3);
		lycheeCut=Toolkit.getDefaultToolkit().getImage("lychee_cut.png");
		tracker.addImage(lycheeCut, 4);
		lycheeBlended=Toolkit.getDefaultToolkit().getImage("lychee_blended.png");
		tracker.addImage(lycheeBlended, 5);
		//CUP and TOPPINGS
		cupBase=Toolkit.getDefaultToolkit().getImage("cup_base.png");
		tracker.addImage(cupBase, 6);
		pearlIcon=Toolkit.getDefaultToolkit().getImage("pearl_icon.png");
		tracker.addImage(pearlIcon, 7);
		puddingIcon=Toolkit.getDefaultToolkit().getImage("pudding_icon.png");
		tracker.addImage(puddingIcon, 8);

		//////////     CUSTOMER
		customerImg=Toolkit.getDefaultToolkit().getImage("customer.png");
		tracker.addImage(customerImg, 9);

		///PEARLZ
		pearlUncooked=Toolkit.getDefaultToolkit().getImage("pearl_uncooked.png");
		tracker.addImage(pearlUncooked, 10);
		pearlCooked=Toolkit.getDefaultToolkit().getImage("pearl_cooked.png");
		tracker.addImage(pearlCooked, 11);



		try {
			tracker.waitForAll();

		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}

	}

	private void startBlendingAnimation(Fruit f) {
		blendingFruit=f;
		blendProgress=0;
		actionBar.setValue(0);
		actionBar.setVisible(true);
		blendTimer.start();
	}


}