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
import java.util.*;
import javax.swing.Timer;
import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.sound.sampled.FloatControl;
public class Main extends JPanel implements MouseListener, KeyListener, MouseMotionListener, ActionListener{

	int x, y;


	private ArrayList<Score> scoreList=new ArrayList<>();
	private JList<String> scoreDisplayList;
	private DefaultListModel<String> scoreListModel;
	private JScrollPane scoreScrollPane;
	private String scoreFile="highscores.txt";
	private Cup currentCup;
	private ArrayList<Cup> trayDrinks;
	private ArrayList<Customer> customers=new ArrayList<>();
	private Timer gameTimer;//for REPAINT
	private Timer roundTimer;//timer for each ROUND
	private int timeLeft=120; //120 seconds = 2 mins
	private boolean gameOn=false;
	private Timer patienceTimer;
	private Timer customerSpawnTimer;//spawn customer every n seconds
	private JProgressBar actionBar;//progress of cooking/blending
	private JProgressBar cookBar;
	private int score=0;
	private Item selectedItem=null;
	private boolean spacePressed=false;
	//stations
	//!v- NEEDA FIX COORDIANTES
	private Rectangle chopStation1=new Rectangle (177, 571, 51, 53);
	private Rectangle chopStation2=new Rectangle (231, 571, 51, 53);

	private HashMap <String, Image> cupImages;

	private Image emptyBlender1, emptyBlender2, blendingMango1, blendingMango2, blendingLychee1, blendingLychee2;
	private Image emptyPot, uncookedPearl1, uncookedPearl2, pearlPot;

	private Image emptyCup, lycheeJuiceCup, mangoJuiceCup, mangoPearlCup, lycheePearlCup, mangoPuddingCup, lycheePuddingCup, mangoPearlPuddingCup, lycheePearlPuddingCup;
	private Image pudding;

	private String potState = "empty";
	private Image mangoBlender;
	private Image lycheeBlender;

	private Fruit previousBlended1, previousBlended2;

	private Image blendedMango, blendedLychee;

	private Image orangeCatHappy, orangeCatImpatient, orangeCatNeutral, orangeCatAngry;

	private int activeBlender = 0; 

	private String blend1State = "empty";
	private String blend2State = "empty";

	private Rectangle blendStation1=new Rectangle (69, 571, 50, 50);
	private Rectangle blendStation2 = new Rectangle(124, 571, 50, 50);

	private Rectangle cookingStation = new Rectangle (284, 571, 50, 50);

	private Rectangle cupStation=new Rectangle (250, 250, 50, 50);
	private Rectangle trayStation=new Rectangle (350, 350, 50, 50);
	private Rectangle servingStation=new Rectangle(450, 450, 50,50);

	private Fruit blender1Fruit = null;
	private Fruit blender2Fruit = null;

	private Pearl cookingPearl = null;
	private int cookingProgress = 0;


	private int blender1Progress = 0;
	private int blender2Progress = 0;

	private Timer blend1Timer;
	private Timer blend2Timer;

	private String blender1FinishedFruit = "";
	private String blender2FinishedFruit = "";

	boolean level1Passed = false;

	private Timer cookTimer;
	private String cookFinishPearl = "";

	//IMAGES
	private Image mangoFresh,mangoCut,mangoBlended;
	private Image lycheeFresh, lycheeCut,lycheeBlended;
	private Image cupBase, pearlIcon, puddingIcon;
	private Image customerImg;
	private Image pearlUncooked, pearlCooked;
	private HashMap<String,HashMap<String, Image>>customerImages=new HashMap<>();


	private Queue<Customer>orderLine=new LinkedList<>();//FIFO queue for waiting to order
	private Customer orderingCustomer=null;
	private Point[] lineSpots;//Point stores x and y coordinates
	private int lineSpotsCount=5;//max # of customers in line
	private Rectangle orderingStation=new Rectangle(200,400,50,50);//!v-NEEDA CHANGE COORDS!!!!!!
	private int orderingFrames=0;//will be >0 when bubble visible

	private Point[]waitingSpots;//waiting spots
	private boolean[]spotOccupied;//true is occupied false is free
	private HashSet<Integer>occupiedSpots=new HashSet<>();


	Image home, instructions1, instructions2, instructions3, instructions4, lockedLevels, unlockedLevels, startImg, gameLevel1, gameLevel2, credits1, credits2, highScore, victory, highScoreBg;
	// Screen States
	// 0 - Home
	// 1 - Instructions (slide 1)
	// 2 – Instructions (slide 2)
	// 3 – Instructions (slide 3)
	// 4 – Instructions (slide 4)
	// 6 – Credits Slide 2
	// 7 – Unlocked Levels
	// 8 – Start Screen
	// 9 – Game Level 1
	// 10 – Game Level 2
	// 11 – Credits Slide 1
	// 12 – High Score
	// 13 – Victory Screen
	// 14 - Locked Levels
	int screenState = 0;

	JTextField usernameField;

	ArrayList<Item> ingredientsOnScreen = new ArrayList<>();

	int offsetX;
	int offsetY;

	Fruit selectedFruit;

	Polygon slantedCreditsButton = new Polygon();
	Polygon slantedScoreButton = new Polygon();


	public void spawnMango() {
		Fruit m=new Fruit("mango", mangoFresh,mangoCut,blendedMango);
		m.setPosition(x-32, y-27);
		ingredientsOnScreen.add(m);
		repaint();
	}

	public void spawnLychee() {
		Fruit l=new Fruit("lychee", lycheeFresh,lycheeCut,blendedLychee);
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

	public void spawnPudding() {
		Pudding pu = new Pudding (pudding);
		pu.setPosition(x - (pu.width / 2), y - (pu.height / 2));
		ingredientsOnScreen.add(pu);
		repaint();
	}

	public void spawnCup () {
		Cup c = new Cup (emptyCup);
		c.setPosition(x - (c.width / 2), y - (c.height / 2));
		ingredientsOnScreen.add(c);
		repaint();
	}

	/*
	private void resetGame() {
		// stop all timers
		if (roundTimer != null) 
			roundTimer.stop();
		if (blend1Timer != null) 
			blend1Timer.stop();
		if (blend2Timer != null) 
			blend2Timer.stop();
		if (cookTimer != null) 
			cookTimer.stop();
		if (customerSpawnTimer != null) 
			customerSpawnTimer.stop();


		gameOn = false;
		timeLeft = 120;
		score = 0;

		// remove everything on screen
		ingredientsOnScreen.clear();
		trayDrinks.clear();
		customers.clear();
		customers.add(new Customer(50, 300, customerImg));

		// reset blenders
		blender1Fruit = null;
		blender2Fruit = null;
		blender1Progress = 0;
		blender2Progress = 0;
		blend1State = "empty";
		blend2State = "empty";
		blender1FinishedFruit = "";
		blender2FinishedFruit = "";
		previousBlended1 = null;
		previousBlended2 = null;
		activeBlender = 0;
		actionBar.setVisible(false);

		// reset cooking
		cookingPearl = null;
		cookingProgress = 0;
		potState = "empty";
		cookBar.setVisible(false);

		// reset selected objects
		selectedItem = null;
		selectedFruit = null;
		spacePressed = false;

		//restart spawning timer
		customerSpawnTimer = new Timer(8000, this);
		customerSpawnTimer.start();
		repaint();
	}
	 */

	private boolean checkFruitCupCollision(Fruit fruit, int mouseX, int mouseY) {
		for (int i = 0; i < ingredientsOnScreen.size(); i++) {
			Item item = ingredientsOnScreen.get(i);
			if (item.type.equals("cup") && item.contains(mouseX, mouseY)) {
				Cup cup = (Cup) item;

				// Check if there is already fruit in cup
				if (cup.getFruits().size() == 0) {
					// Check if fruit is blended
					if (fruit.isBlended()) {
						// Add fruit to cup
						if (fruit.getFruitType().equals("mango") && !cup.getFruits().contains("mango")) {
							cup.getFruits().add("mango");
							cup.addFruit("mango", mangoJuiceCup);
							cup.refreshImage(cupImages);
							//cup.setJuiceImage(mangoJuiceCup);

						} else if (fruit.getFruitType().equals("lychee")  && !cup.getFruits().contains("lychee")) {
							cup.getFruits().add("lychee");
							cup.addFruit("lychee", lycheeJuiceCup);
							cup.refreshImage(cupImages);
							//cup.setJuiceImage(lycheeJuiceCup);

						}

						// Remove the fruit from screen
						ingredientsOnScreen.remove(fruit);
						selectedItem = null;
						return true;
					} else {
						JOptionPane.showMessageDialog(this, "Blend the fruit first!");
						return true;
					}
				}
				else {
					JOptionPane.showMessageDialog(this, "There is already a fruit in this cup");
					return true;
				}
			}
		}
		repaint();
		return false;
	}

	private boolean checkPearlCupCollision(Pearl pearl, int mouseX, int mouseY) {
		for (int i = 0; i < ingredientsOnScreen.size(); i++) {
			Item item = ingredientsOnScreen.get(i);

			// check if dropped on a cup
			if (item.type.equals("cup") && item.contains(mouseX, mouseY)) {
				Cup cup = (Cup) item;
				// must be cooked first
				if (!pearl.isCooked()) {
					JOptionPane.showMessageDialog(this, "Cook the pearls first!");
					return true;
				}
				// cup must already contain juice
				if (cup.getFruits().isEmpty()) {
					JOptionPane.showMessageDialog(this, "Add juice to the cup first!");
					return true;
				}
				// prevent duplicate pearls if you want
				if (!cup.getToppings().contains("pearl")) {
					cup.getToppings().add("pearl");
					cup.addTopping("pearl", mangoPearlCup, lycheePearlCup, mangoPuddingCup, lycheePuddingCup, mangoPearlPuddingCup, lycheePearlPuddingCup);
					cup.refreshImage(cupImages);
					//cup.setToppingImage(mangoPearlCup, lycheePearlCup, mangoPuddingCup, lycheePuddingCup, mangoPearlPuddingCup, lycheePearlPuddingCup);
				}
				else {
					JOptionPane.showMessageDialog(this, "You already added pearls to cup!");
					return true;
				}
				// remove pearl from screen
				ingredientsOnScreen.remove(pearl);
				selectedItem = null;
				repaint();
				return true;
			}
		}

		return false;
	}

	private boolean checkPuddingCupCollision(Pudding pudding, int mouseX, int mouseY) {
		for (int i = 0; i < ingredientsOnScreen.size(); i++) {
			Item item = ingredientsOnScreen.get(i);

			// check if dropped on a cup
			if (item.type.equals("cup") && item.contains(mouseX, mouseY)) {
				Cup cup = (Cup) item;
				// cup must already contain juice
				if (cup.getFruits().isEmpty()) {
					JOptionPane.showMessageDialog(this, "Add juice to the cup first!");
					return true;
				}
				// prevent duplicate puddings
				if (!cup.getToppings().contains("pudding")) {
					cup.getToppings().add("pudding");
					cup.addTopping("pudding", mangoPearlCup, lycheePearlCup, mangoPuddingCup, lycheePuddingCup, mangoPearlPuddingCup, lycheePearlPuddingCup);
					cup.refreshImage(cupImages);
					//cup.setToppingImage(mangoPearlCup, lycheePearlCup, mangoPuddingCup, lycheePuddingCup, mangoPearlPuddingCup, lycheePearlPuddingCup);
				}
				else {
					JOptionPane.showMessageDialog(this, "You already added pudding to cup!");
					return true;
				}
				// remove pudding from screen
				ingredientsOnScreen.remove(pudding);
				selectedItem = null;
				repaint();
				return true;
			}
		}

		return false;
	}


	public Main(){
		setPreferredSize (new Dimension (390, 700));
		cupImages = new HashMap<>();
		loadAllImages();//btw this is only for in game images



		usernameField = new JTextField(10); 
		usernameField.setBounds(66, 300, 258, 21); 
		usernameField.setText("Enter username: ");
		usernameField.setForeground(Color.BLACK);
		usernameField.setVisible(false);

		scoreListModel=new DefaultListModel<>();
		scoreDisplayList=new JList<>(scoreListModel);
		scoreScrollPane=new JScrollPane(scoreDisplayList);
		scoreScrollPane.setBounds(50,150,290,400);
		scoreScrollPane.setVisible(false);
		this.add(scoreScrollPane);

		loadHighScore();


		//currentCup=new Cup(cupBase, pearlIcon, puddingIcon);
		trayDrinks=new ArrayList<>();

		Customer firstCust=new Customer(50,300,customerImages,orderingStation.x,orderingStation.y);
		firstCust.setState("IN_LINE");
		customers.add(firstCust);
		orderLine.add(firstCust);
		System.out.println("First customer created at: " + firstCust.getX() + ", " + firstCust.getY());
		System.out.println("Customer images map: " + customerImages);
		System.out.println("Orange cat images: " + customerImages.get("orangeCat"));
		System.out.println("Neutral image: " + customerImages.get("orangeCat").get("neutral"));
		//bar for blend/cook/cut

		//customers.add(new Customer(50, 300, customerImages,orderingStation.x, orderingStation.y));

		//bar for blend
		actionBar= new JProgressBar(0,100);
		//actionBar.setBounds(144, 580, 100, 15);
		actionBar.setVisible(false);//not visible til action is doing
		this.add(actionBar);

		// bar for cooking
		cookBar = new JProgressBar (0, 100);
		cookBar.setVisible(false);
		this.add(cookBar);

		addKeyListener(this);
		setFocusable(true);
		this.setLayout(null); // Use absolute positioning for the box
		this.add(usernameField);
		System.out.print("DELETE THIS");
		gameTimer=new Timer (50, this);
		gameTimer.start();

		patienceTimer=new Timer( 1000, this);
		patienceTimer.start();

		lineSpots=new Point[lineSpotsCount];
		lineSpots[0]=new Point(orderingStation.x,orderingStation.y);
		for(int i=1;i<lineSpotsCount;i++) {
			lineSpots[i]=new Point(orderingStation.x-(i*40),orderingStation.y);
		}
		//AFTER orderingg
		waitingSpots=new Point[3];
		waitingSpots[0]=new Point(100,500);
		waitingSpots[1]=new Point(150,500);
		waitingSpots[2]=new Point(200,500);
		spotOccupied=new boolean[waitingSpots.length];

		blend1Timer= new Timer(30, this);
		blend1Timer.stop();//not initially running


		cookTimer = new Timer (30, this);
		cookTimer.stop();

		blend2Timer = new Timer(30, this);
		blend2Timer.stop();
		customerSpawnTimer=new Timer(8000,this);
		customerSpawnTimer.start();

		// Credits button slanted rectangle
		slantedCreditsButton.addPoint(36, 601);
		slantedCreditsButton.addPoint(110, 564);
		slantedCreditsButton.addPoint(125, 591);
		slantedCreditsButton.addPoint(45, 627);

		// Scoreboard button slanted rectangle
		slantedScoreButton.addPoint(246, 622);
		slantedScoreButton.addPoint(285, 625);
		slantedScoreButton.addPoint(252, 680);
		slantedScoreButton.addPoint(294, 675);



	}

	public boolean remainsInBlender(Fruit f, int blender) {
		Rectangle fruitRect =new Rectangle(f.getX(), f.getY(),f.width, f.height);

		if (blender == 1) {
			return blendStation1.intersects(fruitRect);
		} else {
			return blendStation2.intersects(fruitRect);
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==gameTimer) {//maybe do dif method?
			for(int i=customers.size()-1;i>=0;i--) {
				Customer c=customers.get(i);
				c.updateMovement();
				if(c.hasArrived()) {
					String state=c.getState();
					if(state.equals("SERVED")) {
						if(c.getWaitingSpotIndex()!=-1) {
							spotOccupied[c.getWaitingSpotIndex()]=false;
						}
						customers.remove(i);
					}
					else if(state.equals("LEAVING")) 
						customers.remove(i);

				}
			}
			if(orderingCustomer!=null&&orderingFrames>0) {
				orderingFrames--;
				orderingCustomer.updateBubble();
				if(orderingFrames==0) {
					orderingCustomer.setState("WAITING");
					orderLine.poll();
					shiftLineForward();
					int freeSpot=getFreeWaitingSpot();
					if(freeSpot!=-1) {
						Point spot=waitingSpots[freeSpot];
						orderingCustomer.setTarget(spot.x, spot.y);
						orderingCustomer.setWaitingSpotIndex(freeSpot);
						spotOccupied[freeSpot]=true;
					}
					else
						orderingCustomer.setTarget(100, 500);
					orderingCustomer=null;
				}
			}
			repaint();
		}
		//CUSOTMER PATIENCE
		else if(e.getSource()==patienceTimer) {
			for (int i=customers.size()-1;i>=0;i--) {
				Customer c=customers.get(i);
				c.decreasePatience();
				c.updateEmotion(customerImages);
				if (c.isAngry()) {
					customers.remove(i);
				}
			}
		}
		//BLENDING
		else if (e.getSource() == blend1Timer) {
			blender1Progress += 5;
			actionBar.setValue(blender1Progress);

			if (blender1Progress >= 100) {
				blend1Timer.stop();
				actionBar.setVisible(false);
				if (blender1Fruit != null) {
					blender1Fruit.setBlended();
					blender1Fruit.setPosition(45, 544);
					ingredientsOnScreen.add(blender1Fruit);
					blender1FinishedFruit = blender1Fruit.getFruitType();
					blend1State = "empty";
					previousBlended1 = blender1Fruit;
				}
				blender1Fruit = null;
			}

			else if (blender1Progress >= 66) {
				blend1State = "unblended2";
			}
			else if (blender1Progress >= 33) {
				blend1State = "unblended1";
			}
			repaint();
		}

		else if (e.getSource() == blend2Timer) {
			blender2Progress += 5;
			actionBar.setValue(blender2Progress);
			if (blender2Progress >= 100) {
				blend2Timer.stop();
				actionBar.setVisible(false);
				if (blender2Fruit != null) {
					blender2Fruit.setBlended();
					blender2Fruit.setPosition(100, 544);
					ingredientsOnScreen.add(blender2Fruit);
					blender2FinishedFruit = blender2Fruit.getFruitType();
					blend2State = "empty";
					previousBlended2 = blender2Fruit;
				}

				blender2Fruit = null;

			}

			else if (blender2Progress >= 66) {
				blend2State = "unblended2";
			}
			else if (blender2Progress >= 33) {
				blend2State = "unblended1";
			}
			repaint();
		}

		else if (e.getSource() == cookTimer){
			cookingProgress += 3;
			cookBar.setValue(cookingProgress);


			if (cookingProgress >= 150) {
				cookTimer.stop();
				cookBar.setVisible(false);
				if (cookingPearl != null) {
					cookingPearl.setCooked();
					cookingPearl.setPosition(283,  568);
					ingredientsOnScreen.add(cookingPearl);
					cookFinishPearl = "cooked";
					potState = "empty";
				}
				cookingPearl = null;
			}


			else if (cookingProgress >= 75) {
				potState = "uncooked2";
			}

			else if (cookingProgress >= 50) {
				potState = "uncooked1";
			}


			repaint();

		}


		//CUSTOMER SPAWN 
		else if(e.getSource()==customerSpawnTimer) {
			if(screenState==9) {
				if(orderLine.size()<lineSpotsCount) {
					int backInd=orderLine.size();
					Point backSpot=lineSpots[backInd];
					Customer newC=new Customer(300,10,customerImages,backSpot.x,backSpot.y);
					newC.setState("IN_LINE");
					customers.add(newC);
					orderLine.add(newC);
					repaint();
				}
			}
		}

		else if(e.getSource()==roundTimer&&gameOn) {
			timeLeft--;
			if(timeLeft<=0) {
				endGame();
			}
			repaint();
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
			g.drawImage(credits2, 0, 0, 390, 700, this);
		}
		if (screenState == 7) {
			g.drawImage(unlockedLevels,  0, 0, 390, 700, this);
		}

		if (screenState == 14) {
			g.drawImage(lockedLevels,  0, 0, 390, 700, this);
		}
		if (screenState == 8) {
		}
		if (screenState == 9) {

			// game screen
			g.drawImage(gameLevel1, 0, 0, 390, 700, this);

			// draw customers
			for (Customer c : customers) {
				c.draw(g);
			}

			// Countdown timer
			int minutes = timeLeft / 60;
			int seconds = timeLeft % 60;

			g.setFont(new Font("Arial", Font.BOLD, 18));
			g.setColor(Color.BLACK);

			// bottom-right corner
			String timerText = String.format("%02d:%02d", minutes, seconds);
			FontMetrics fm = g.getFontMetrics();

			int x = getWidth() - fm.stringWidth(timerText) - 10;
			int y = getHeight() - 10;

			if (timeLeft <= 10) 
				g.setColor(Color.RED);
			g.drawString(timerText, 323, 680);

			// Cooking pot
			if (potState.equals("empty")) {
				g.drawImage(emptyPot, 247, 512, 150, 150, this);
			}
			else if (potState.equals("uncooked1")) {
				g.drawImage(uncookedPearl1, 247, 512, 150, 150, this);
			}
			else if (potState.equals("uncooked2")) {
				g.drawImage(uncookedPearl2, 247, 512, 150, 150, this);
			}
			else if (potState.equals("cooked")) {
				g.drawImage(pearlPot, 247, 512, 150, 150, this);
			}


			// Blender 1
			if (blend1State.equals("empty")) {
				g.drawImage(emptyBlender1, 45, 544, 100, 100, this);
			}
			//<<<<<<< HEAD
			else if (blend1State.equals("unblended1")) {
				if ("mango".equals(blender1FinishedFruit)) {
					g.drawImage(blendingMango1, 45, 544, 100, 100, this);

				}
				else if ("lychee".equals(blender1FinishedFruit)) {
					g.drawImage(blendingLychee1, 45, 544, 100, 100, this);
				}
			}

			else if (blend1State.equals("unblended2")) {
				if ("mango".equals(blender1FinishedFruit)) {
					g.drawImage(blendingMango2, 45, 544, 100, 100, this);
				}
				else if ("lychee".equals(blender1FinishedFruit)) {
					g.drawImage(blendingLychee2, 45, 544, 100, 100, this);
				}
			}
			else if (blend1State.equals("blended")) {
				if ("mango".equals(blender1FinishedFruit)) {
					g.drawImage(mangoBlender, 45, 544, 100, 100, this);
				}
				else if ("lychee".equals(blender1FinishedFruit)) {
					g.drawImage(lycheeBlender, 45, 544, 100, 100, this);
				}
			}


			// Blender 2
			if (blend2State.equals("empty")) {
				g.drawImage(emptyBlender2, 100, 544, 100, 100, this);
			}
			else if (blend2State.equals("unblended1")) {
				if ("mango".equals(blender2FinishedFruit)) {
					g.drawImage(blendingMango1, 100, 544, 100, 100, this);
				}
				else if ("lychee".equals(blender2FinishedFruit)) {
					g.drawImage(blendingLychee1, 100, 544, 100, 100, this);
				}
			}

			else if (blend2State.equals("unblended2")) {
				if ("mango".equals(blender2FinishedFruit)) {
					g.drawImage(blendingMango2, 100, 544, 100, 100, this);
				}
				else if ("lychee".equals(blender2FinishedFruit)) {
					g.drawImage(blendingLychee2, 100, 544, 100, 100, this);
				}
			}
			else if (blend2State.equals("blended")) {
				if ("mango".equals(blender2FinishedFruit)) {
					g.drawImage(mangoBlender, 100, 544, 100, 100, this);
				}
				else if ("lychee".equals(blender2FinishedFruit)) {
					g.drawImage(lycheeBlender, 100, 544, 100, 100, this);
				}
			}




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
			//	if (currentCup!=null && selectedItem!=currentCup) {
			//		currentCup.drawLayered(g, cupStation.x, cupStation.y);

			//	}

		}



		if (screenState == 10) {
		}
		if (screenState == 11) {
			g.drawImage(credits1,0, 0, 390, 700, this);
		}
		if (screenState == 12) {
			g.drawImage(highScoreBg, 0, 0, 390, 700, this);
			scoreScrollPane.setVisible(true);
			usernameField.setVisible(false);
			return;
		}
		if (screenState == 13) {
		}
	}


	public void handleAction(int x, int y) {
		System.out.println("x: " + x + " y: " + y);

		// Home screen
		if (screenState == 0) {

			// Level 1 Play screen
			if (x >= 95 && x <= 298 && y >= 343 && y <= 401) {

				if (!level1Passed) {
					// locked levels screen
					screenState = 14;
				}
				else {
					// unlocked levels screen
					screenState = 7;
				}
				// SUPER IMPORTANT FOR LATER
				/*
				screenState = 9;
				if(!gameOn) {
					gameOn=true;
					roundTimer=new Timer(1000,this);
					roundTimer.start();
				}
				 */
			}

			// Instructions slide 1
			else if (x >= 332 && x <= 384 && y >= 434 && y <= 503) {
				screenState = 1;
			}

			// Credits slide 1
			else if (slantedCreditsButton.contains(x, y)) {
				screenState = 11;
			}

			// High score
			else if (slantedScoreButton.contains(x, y)) {
				screenState = 12;
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

		// Locked Levels Screen
		else if (screenState == 14) {
			// Home screen
			if (x >= 20 && x <= 95 && y >= 18 && y <= 52) {
				screenState = 0;
			}
			// Level 2 Selection
			else if (x >= 204 && x<= 359 && y >= 165 && y <= 608) {
				JOptionPane.showMessageDialog(this, "Pass Level 1 first to unlock level 2!");
			}
			// Level 1 Selection
			else if (x >= 34 && x <= 187 && y >= 165 && y <= 610) {
				screenState = 9;
				if(!gameOn) {
					gameOn=true;
					roundTimer=new Timer(1000,this);
					roundTimer.start();
				}
			}

		}
		// Unlocked Levels Screen
		else if (screenState == 7) {
			// Home screen
			if (x >= 20 && x <= 95 && y >= 18 && y <= 52) {
				screenState = 0;
			}
			// Level 2 Selection
			else if (x >= 204 && x<= 359 && y >= 165 && y <= 608) {
				screenState = 10;
				if(!gameOn) {
					gameOn=true;
					roundTimer=new Timer(1000,this);
					roundTimer.start();
				}
			}

			else if (x >= 34 && x <= 187 && y >= 165 && y <= 610) {
				screenState = 9;
				if(!gameOn) {
					gameOn=true;
					roundTimer=new Timer(1000,this);
					roundTimer.start();
				}
			}
		}

		// Game Screen
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

			if (x>= 230 && x <= 283 && y >= 439 && y<= 494) {
				spawnPudding();
			}

			if (x >= 175 && x <= 226 && y >= 439 && y <= 493) {
				spawnCup();
			}

		}

		// Credits Slide 1
		else if (screenState == 11) {
			// home
			if (x >= 11 && x <= 84 && y >= 14 && y <= 47) {
				screenState = 0;
			}
			// credits slide 2
			else if (x >= 352 && x <= 377 && y  >= 295 && y <= 323) {
				screenState = 6;
			}
		}

		// Credits Slide 2
		else if (screenState == 6) {
			// home
			if (x >= 11 && x <= 84 && y >= 14 && y <= 47) {
				screenState = 0;
			}
			// credits slide 1
			else if (x >= 19 && x <= 47 && y >= 297 && y <= 324) {
				screenState = 11;
			}
		}

		// High Score Screen
		else if (screenState == 12) {
			// home
			if (x >= 10 && x <= 85 && y >= 13 && y <= 46) {
				scoreScrollPane.setVisible(false);
				screenState = 0;
			}
		}
		repaint(); 
	}


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

		if (e.getKeyCode() == KeyEvent.VK_E) {
			endGame();
			screenState = 12; 
			repaint();
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

		//<<<<<<< HEAD
		if(orderingStation.contains(mx,my)) {
			if(!orderLine.isEmpty()) {
				Customer front=orderLine.peek();
				if(front.getState().equals("IN_LINE")&&front.hasArrived()) {
					orderingCustomer=front;
					orderingFrames=30;//1.5 secs
					front.startBubble();
					front.setState("ORDERING");
				}
				else
					JOptionPane.showMessageDialog(this, "No customer at front of line.");
			}
			else
				JOptionPane.showMessageDialog(this, "No customers in line.");
			return;//so no multiple actions on same click.
		}

		/*
		//COOKING PEARL
		if (selectedItem.type.equals("pearl")) {
			Pearl p = (Pearl) selectedItem;
			// cook
			if (cookingStation.contains(mx, my)){
				if (!p.isCooked()) {
					p.cook();
					repaint();
					return;//stop from going further.
				}

				if (selectedItem != null && selectedItem.type.equals("pudding")) {
					Pudding pu = (Pudding) selectedItem;
					if (checkPuddingCupCollision(pu, mx, my)) {
						return;
					}
				}
		 */

		//COOKING PEARL
		if (selectedItem != null && selectedItem.type.equals("pearl")) {
			Pearl p = (Pearl) selectedItem;
			if (checkPearlCupCollision(p, mx, my)) {
				return;
			}

			// cook
			if (cookingStation.contains(mx, my)){
				if (!p.isCooked() && cookingPearl == null) {
					ingredientsOnScreen.remove(p);
					cookingPearl = p;
					cookingProgress = 0;
					potState = "uncooked1";
					cookBar.setValue(0);
					cookBar.setBounds(cookingStation.x, cookingStation.y - 15, cookingStation.width, 10);
					cookBar.setVisible(true);
					cookTimer.start();
					selectedItem = null;
					repaint();
					return;
				}
				else if (cookingPearl != null) {
					JOptionPane.showMessageDialog(this, "Already cooking!");
					ingredientsOnScreen.add(p);
					return;
				}
			}

			else if (cupStation.contains(mx, my)) {
				//if (currentCup == null) {
				//	currentCup = new Cup(cupBase);
				//}
				//currentCup.addTopping("pearls", mangoPearlCup, lycheePearlCup, mangoPuddingCup, lycheePuddingCup, mangoPearlPuddingCup, lycheePearlPuddingCup);
				cookFinishPearl = ""; // reset pot image
			}

			else {
				ingredientsOnScreen.add(p);
			}
		}


		// PROCESS FOOD
		else if (selectedItem != null && selectedItem.type.equals("fruit")){
			Fruit f=(Fruit) selectedItem;
			f.setOnChopStation(false);//default

			if (checkFruitCupCollision(f, mx, my)) {
				return; 
			}

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
			else if (blendStation1.contains(mx,my)) {
				if (blender1Fruit != null || (previousBlended1 != null && remainsInBlender(previousBlended1, 1))) {
					JOptionPane.showMessageDialog(this, "Blender is already in use!");
					ingredientsOnScreen.add(f);
					return;
				}


				if (f.isCut()&& !f.isBlended()) {
					ingredientsOnScreen.remove(f);
					selectedItem = null;
					activeBlender = 1;
					actionBar.setBounds(blendStation1.x, blendStation1.y - 15, blendStation1.width, 10);
					startBlendingAnimation(f);
					repaint();
					return;

				}
				else {
					JOptionPane.showMessageDialog(this, "Chop the fruit first!");
					ingredientsOnScreen.add(f);
					return;

				}
			}
			else if (blendStation2.contains(mx,my)) {
				if (blender2Fruit != null ||  (previousBlended2 != null && remainsInBlender(previousBlended2, 2))) {
					JOptionPane.showMessageDialog(this, "Blender is already in use!");
					ingredientsOnScreen.add(f);
					return;
				}

				if (f.isCut()&& !f.isBlended()) {
					ingredientsOnScreen.remove(f);
					selectedItem = null;
					activeBlender = 2;
					actionBar.setBounds(blendStation2.x, blendStation2.y - 15, blendStation2.width, 10);
					startBlendingAnimation(f);
					repaint();

				}
				else {
					JOptionPane.showMessageDialog(this, "Chop the fruit first!");
					ingredientsOnScreen.add(f);
					return;

				}

				// Collision with cup
			}



			//cup station (add components to cup)
			else if (cupStation.contains (mx,my)) {
				if (f.isBlended()) {
					//	if (currentCup==null) {
					//		currentCup=new Cup (cupBase);
					//	}
					//currentCup.addFruit(f.getFruitType());
				}else {
					JOptionPane.showMessageDialog(this, "Blend the fruit first!!!!");
					ingredientsOnScreen.add(f);
				}
			}
			else {
				ingredientsOnScreen.add(f);
			}
		}

		/*
				else if(selectedItem != null && selectedItem.type.equals("pearl")) {
					//Pearl p =(Pearl) selectedItem;
					if (cupStation.contains(mx,my)) {
						//if (currentCup==null) {
						//	currentCup=new Cup(cupBase);
						//}
						//currentCup.addTopping("pearls", mangoPearlCup, lycheePearlCup, mangoPuddingCup, lycheePuddingCup, mangoPearlPuddingCup, lycheePearlPuddingCup);
					}
					else {
						ingredientsOnScreen.add(p);
					}
				}*/
		else if (selectedItem != null && selectedItem.type.equals("cup")) {
			Cup cup=(Cup) selectedItem;
			if (trayStation.contains(mx,my)) {
				if (!cup.getFruits().isEmpty()|| !cup.getToppings().isEmpty()) {
					trayDrinks.add(cup);
					//	currentCup=new Cup(cupBase);
					JOptionPane.showMessageDialog(this, "Drink added to tray.");;

				}
				else {
					JOptionPane.showMessageDialog(this, "empty cup...");
					//	currentCup=cup;
				}
			}
			else if(servingStation.contains(mx,my)) {
				boolean served=false;//default
				for (int i=0;i<customers.size();i++) {//runs through each customer til correct order found, or if not found
					Customer c=customers.get(i);
					if(c.getOrder().matches(trayDrinks)) {
						c.setState("SERVED");
						c.setTarget(servingStation.x, servingStation.y);
						if(c.getWaitingSpotIndex()!=-1) 
							spotOccupied[c.getWaitingSpotIndex()]=false;
						trayDrinks.clear();
						JOptionPane.showMessageDialog(this, "served!!!!!!!! +100 pts");
						served=true;
						score+=100;
						break;
					}
				}
				if(!served) {//not matching
					JOptionPane.showMessageDialog(this,"this tray doesn't match any order!!");
					//currentCup=cup;//put last cup back into hand
				}
			}
			//else
			//	currentCup=cup;
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

		// check if blender 1 emptied
		if (selectedItem == blender1Fruit && !blendStation1.intersects(new Rectangle(selectedItem.x,selectedItem.y,selectedItem.width,selectedItem.height))) {
			blender1Fruit = null;
			blender1FinishedFruit = "";
		}

		// check if blender 2 emptied
		if (selectedItem == blender2Fruit &&!blendStation2.intersects(new Rectangle(selectedItem.x, selectedItem.y,selectedItem.width,selectedItem.height))) {
			blender2Fruit = null;
			blender2FinishedFruit = "";
		}

		repaint();
	}


}

@Override
public void mouseMoved(MouseEvent e) {
	// TODO Auto-generated method stub

}

private void loadAllImages() {

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
	highScore = Toolkit.getDefaultToolkit ().getImage ("highScore.png");
	tracker.addImage (highScore, 12);
	victory = Toolkit.getDefaultToolkit ().getImage ("victory.png");
	tracker.addImage (victory, 13);

	//////////MANGO
	mangoFresh=Toolkit.getDefaultToolkit().getImage("mango_fresh.png");
	tracker.addImage(mangoFresh, 14);
	mangoCut=Toolkit.getDefaultToolkit().getImage("mango_cut.png");
	tracker.addImage(mangoCut, 15);
	mangoBlended=Toolkit.getDefaultToolkit().getImage("mango_blended.png");
	tracker.addImage(mangoBlended, 16);
	//////////LYCHEE
	lycheeFresh=Toolkit.getDefaultToolkit().getImage("lychee_fresh.png");
	tracker.addImage(lycheeFresh, 17);
	lycheeCut=Toolkit.getDefaultToolkit().getImage("lychee_cut.png");
	tracker.addImage(lycheeCut, 18);
	lycheeBlended=Toolkit.getDefaultToolkit().getImage("lychee_blended.png");
	tracker.addImage(lycheeBlended, 19);
	//CUP and TOPPINGS
	cupBase=Toolkit.getDefaultToolkit().getImage("cup_base.png");
	tracker.addImage(cupBase, 20);
	pearlIcon=Toolkit.getDefaultToolkit().getImage("pearl_icon.png");
	tracker.addImage(pearlIcon, 21);
	puddingIcon=Toolkit.getDefaultToolkit().getImage("pudding_icon.png");
	tracker.addImage(puddingIcon, 22);

	//////////     CUSTOMER
	customerImg=Toolkit.getDefaultToolkit().getImage("customer.png");
	tracker.addImage(customerImg, 23);

	///PEARLZ
	pearlUncooked=Toolkit.getDefaultToolkit().getImage("pearl_uncooked.png");
	tracker.addImage(pearlUncooked, 24);
	pearlCooked=Toolkit.getDefaultToolkit().getImage("pearl_cooked.png");
	tracker.addImage(pearlCooked, 25);

	// blender
	emptyBlender1 = Toolkit.getDefaultToolkit().getImage("emptyBlender.png");
	tracker.addImage(emptyBlender1, 26);

	emptyBlender2 = Toolkit.getDefaultToolkit().getImage("emptyBlender.png");
	tracker.addImage(emptyBlender2, 27);

	mangoBlender = Toolkit.getDefaultToolkit().getImage("mango_blender.png");
	tracker.addImage(mangoBlender, 28);

	lycheeBlender = Toolkit.getDefaultToolkit().getImage("lychee_blender.png");
	tracker.addImage(lycheeBlender, 29);

	blendingMango1 = Toolkit.getDefaultToolkit().getImage("blending_Mango1.png");
	tracker.addImage(blendingMango1, 30);

	blendingMango2 = Toolkit.getDefaultToolkit().getImage("blending_Mango2.png");
	tracker.addImage(blendingMango2, 31);

	blendingLychee1 = Toolkit.getDefaultToolkit().getImage("blending_Lychee1.png");
	tracker.addImage(blendingLychee1, 32);

	blendingLychee2 = Toolkit.getDefaultToolkit().getImage("blending_Lychee2.png");
	tracker.addImage(blendingLychee2, 33);

	blendedMango =  Toolkit.getDefaultToolkit().getImage("blendedMango.png");
	blendedMango = blendedMango.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
	tracker.addImage(blendedMango, 34);


	blendedLychee =  Toolkit.getDefaultToolkit().getImage("blendedLychee.png");
	blendedLychee = blendedLychee.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
	tracker.addImage(blendedLychee, 35);


	//Pot
	emptyPot = Toolkit.getDefaultToolkit().getImage("emptyPot.png");
	tracker.addImage(emptyPot, 36);

	pearlPot = Toolkit.getDefaultToolkit().getImage("pearl_Pot.png");
	tracker.addImage(pearlPot, 37);

	uncookedPearl1 = Toolkit.getDefaultToolkit().getImage("uncookedPearl1_Pot.png");
	tracker.addImage(uncookedPearl1, 38);

	uncookedPearl2 = Toolkit.getDefaultToolkit().getImage("uncookedPearl2_Pot.png");
	tracker.addImage(uncookedPearl2, 39);

	// high score background
	highScoreBg = Toolkit.getDefaultToolkit().getImage("highScoreBg.png");
	tracker.addImage(highScoreBg, 40);

	// pudding
	pudding = Toolkit.getDefaultToolkit().getImage("pudding.png");
	tracker.addImage(pudding, 41);

	// All cups + add to HashMap
	emptyCup = Toolkit.getDefaultToolkit().getImage("emptyCup.png");
	tracker.addImage(emptyCup, 42);

	mangoJuiceCup = Toolkit.getDefaultToolkit().getImage("mangoJuiceCup.png");
	tracker.addImage(mangoJuiceCup, 43);
	cupImages.put("mangoJuice", mangoJuiceCup);

	lycheeJuiceCup = Toolkit.getDefaultToolkit().getImage("lycheeJuiceCup.png");
	tracker.addImage(lycheeJuiceCup, 44);
	cupImages.put("lycheeJuice", lycheeJuiceCup);

	mangoPearlCup = Toolkit.getDefaultToolkit().getImage("mangoPearlCup.png");
	tracker.addImage(mangoPearlCup, 45);
	cupImages.put("mangoPearl", mangoPearlCup);

	lycheePearlCup = Toolkit.getDefaultToolkit().getImage("lycheePearlCup.png");
	tracker.addImage(lycheePearlCup, 46);
	cupImages.put("lycheePearl", lycheePearlCup);

	mangoPuddingCup = Toolkit.getDefaultToolkit().getImage("mangoPuddingCup.png");
	tracker.addImage(mangoPuddingCup, 47);
	cupImages.put("mangoPudding", mangoPuddingCup);

	lycheePuddingCup = Toolkit.getDefaultToolkit().getImage("lycheePuddingCup.png");
	tracker.addImage(lycheePuddingCup, 48);
	cupImages.put("lycheePudding", lycheePuddingCup);

	mangoPearlPuddingCup = Toolkit.getDefaultToolkit().getImage("mangoPearlPuddingCup.png");
	tracker.addImage(mangoPearlPuddingCup, 49);
	cupImages.put("mangoPearlPudding", mangoPearlPuddingCup);


	lycheePearlPuddingCup = Toolkit.getDefaultToolkit().getImage("lycheePearlPuddingCup.png");
	tracker.addImage(lycheePearlPuddingCup, 50);
	cupImages.put("lycheePearlPudding", lycheePearlPuddingCup);


	// Credits screen
	credits1 = Toolkit.getDefaultToolkit().getImage("credits1.png");
	tracker.addImage(credits1, 51);
	credits2 = Toolkit.getDefaultToolkit().getImage("credits2.png");
	tracker.addImage(credits2, 52);

	// Levels screens
	lockedLevels = Toolkit.getDefaultToolkit().getImage("lockedLevels.png");
	tracker.addImage(lockedLevels, 53);
	unlockedLevels = Toolkit.getDefaultToolkit().getImage("unlockedLevels.png");
	tracker.addImage(unlockedLevels, 54);

	// Customers (Orange Cat)
	orangeCatHappy = Toolkit.getDefaultToolkit().getImage("orangeCat_happy.png");
	tracker.addImage(orangeCatHappy, 55);

	orangeCatNeutral = Toolkit.getDefaultToolkit().getImage("orangeCat_neutral.png");
	tracker.addImage(orangeCatNeutral, 56);

	orangeCatImpatient = Toolkit.getDefaultToolkit().getImage("orangeCat_impatient.png");
	tracker.addImage(orangeCatImpatient, 57);

	orangeCatAngry = Toolkit.getDefaultToolkit().getImage("orangeCat_angry.png");
	tracker.addImage(orangeCatAngry, 58);

	try {
		tracker.waitForAll();
	}
	catch(InterruptedException e) {
		e.printStackTrace();
	}

	HashMap<String,Image>orangeCatEmotions=new HashMap<>();//ORNAGE CAT
	orangeCatEmotions.put("happy", orangeCatHappy);
	orangeCatEmotions.put("neutral", orangeCatNeutral);
	orangeCatEmotions.put("impatient", orangeCatImpatient);
	orangeCatEmotions.put("angry", orangeCatAngry);
	customerImages.put("orangeCat", orangeCatEmotions);

	System.out.println("Customer images loaded: " + customerImages);
	System.out.println("Orange cat emotions: " + customerImages.get("orangeCat").keySet());
	System.out.println("Neutral image: " + customerImages.get("orangeCat").get("neutral"));

}

private void startBlendingAnimation(Fruit f) {

	actionBar.setVisible(true);

	ingredientsOnScreen.remove(f);
	//selectedItem = null;

	if (activeBlender == 1) {
		blender1Fruit = f;
		blender1FinishedFruit = f.getFruitType();
		blender1Progress = 0;
		//ingredientsOnScreen.remove(f);

		blend1State = "unblended1";
		actionBar.setValue(0);
		actionBar.setVisible(true);
		blend1Timer.start();
		repaint();
	}

	if (activeBlender == 2) {
		blender2Fruit = f;
		blender2FinishedFruit = f.getFruitType();
		blender2Progress = 0;
		blend2State = "unblended1";

		actionBar.setValue(0);
		blend2Timer.start();
		repaint();
	}
}


private void loadHighScore() {
	try(Scanner scanner=new Scanner(new File(scoreFile))){
		while(scanner.hasNextLine()) {
			String line=scanner.nextLine();
			String[]parts=line.split(",");
			if (parts.length==2)   //usrname ////////points
				scoreList.add(new Score(parts[0],Integer.parseInt(parts[1])));
		}
	}
	catch(FileNotFoundException e) {
	}
	//	Collections.sort(scoreList);
	//	refreshScoreList();
}
void saveScore() {
	try(PrintWriter inFile=new PrintWriter(new File(scoreFile))){
		for (Score s: scoreList) {
			inFile.println(s.username+","+s.points);
		}
	}
	catch(FileNotFoundException e) {
	}
}

private void endGame() {
	if (!gameOn)//if the game has alr ended (endGame() accidently called or smth)
		return;
	gameOn=false;//so no more gaming can happen
	// stop all timers
	if (roundTimer != null) 
		roundTimer.stop();
	if (blend1Timer != null) 
		blend1Timer.stop();
	if (blend2Timer != null) 
		blend2Timer.stop();
	if (cookTimer != null) 
		cookTimer.stop();
	if (customerSpawnTimer != null) 
		customerSpawnTimer.stop();

	gameOn = false;
	timeLeft = 120;
	score = 0;

	// remove everything on screen
	ingredientsOnScreen.clear();
	trayDrinks.clear();
	customers.clear();
	//customers.add(new Customer(50, 300, customerImg));

	// reset blenders
	blender1Fruit = null;
	blender2Fruit = null;
	blender1Progress = 0;
	blender2Progress = 0;
	blend1State = "empty";
	blend2State = "empty";
	blender1FinishedFruit = "";
	blender2FinishedFruit = "";
	previousBlended1 = null;
	previousBlended2 = null;
	activeBlender = 0;
	actionBar.setVisible(false);

	// reset cooking
	cookingPearl = null;
	cookingProgress = 0;
	potState = "empty";
	cookBar.setVisible(false);

	// reset selected objects
	selectedItem = null;
	selectedFruit = null;
	spacePressed = false;

	//restart spawning timer
	customerSpawnTimer = new Timer(8000, this);
	customerSpawnTimer.start();


	//add a score to scores
	String name=usernameField.getText().trim();
	if(name.equals("Enter username:"))
		name="Anonymous";
	scoreList.add(new Score(name,score));
	Collections.sort(scoreList);

	//saveScore();

	refreshScoreList();
	screenState=12;
	repaint();
}

private int getFreeWaitingSpot() {//find free waiting spots
	for(int i=0;i<spotOccupied.length;i++) {
		if (!spotOccupied[i])//if unoccupied, can be occupied!
			return i;
	}
	return -1;//if no spots avail
}
private void shiftLineForward() {
	int ind=0;
	for(Customer c:orderLine) {//do each customer in the line
		if(ind<lineSpotsCount) {
			c.setTarget(lineSpots[ind].x, lineSpots[ind].y);
		}
		ind++;//next
	}
}

private void refreshScoreList() {
	if(scoreListModel==null)
		return;
	scoreListModel.clear();
	for(Score s: scoreList) {
		scoreListModel.addElement(String.format("%-15s%45d", s.username, s.points));
	}
}


}