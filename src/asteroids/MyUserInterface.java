package asteroids;

import gameNet.GameNet_UserInterface;
import gameNet.GamePlayer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;




public class MyUserInterface extends JFrame implements  GameNet_UserInterface, Serializable
{   
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	GamePlayer  myGamePlayer = null;
	int playerNumber;

	String myName = null;
	JLabel statusLabel = new JLabel("");
	MyGameInput myGameInput = new MyGameInput();
	PaintPanel paintPanel = null;
	// Images
//	String cannonLeftImage = "images/cannonLeft.png";
//	String cannonRightImage = "images/cannonRight.png";

//	static BufferedImage cannonLeft;
//	static BufferedImage cannonRight;
	

	public MyUserInterface()
	{
		super("Don't Get Cocky, Kid.");

	}

	public void receivedMessage(Object ob)
	{
//		System.out.println("Got to receivedMessage");//TODO
		MyGameOutput myGameOutput = (MyGameOutput)ob;
		if (paintPanel != null)
			paintPanel.updatePaint(myGameOutput.myGame);
		else
			System.out.println("Thread is already sending  stuff before I am ready");
	}

	
	void sendMessage(int command)
	{		
		myGameInput.command = command;
		myGamePlayer.sendMessage(myGameInput);
	}


	public void startUserInterface (GamePlayer gamePlayer) {    
		myGamePlayer = gamePlayer; 
		myName = gamePlayer.getPlayerName();
		myGameInput.setName(myName);
//		myGameInput.setPlayerNumber();
		
		mylayout(); 
		
		sendMessage(MyGameInput.CONNECTING);

		addWindowListener(new Termination());

		setVisible(true); 
	} 
	private void mylayout()
	{
		setLayout(new BorderLayout());
		paintPanel = new PaintPanel( statusLabel,  myName, myGamePlayer);
		add(paintPanel, BorderLayout.CENTER);

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());     
		JButton b = new JButton("Reset");
		topPanel.add(b);
		b.addActionListener(new ActionMonitor()); 
		statusLabel.setText(myName);
		topPanel.add(statusLabel);
		add(topPanel, BorderLayout.NORTH);


		setSize(900,600);  
	}


	//*******************************************
	// Another Inner class
	//*******************************************
	class ActionMonitor implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{            
			sendMessage(MyGameInput.RESETTING); 
		}
	}

	//*******************************************************
	// Inner Class
	//*******************************************************

	class Termination extends WindowAdapter
	{
		public void windowClosing(WindowEvent e)
		{
			sendMessage(MyGameInput.DISCONNECTING);

			myGamePlayer.doneWithGame();
			System.exit(0);
		}
	}



}

