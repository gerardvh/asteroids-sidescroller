package asteroids;

import gameNet.GameControl;
import gameNet.GameNet_CoreGame;


import java.io.Serializable;
import java.util.ArrayList;

class Player implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int score = 0;
	String name;
	int playerNumber;
	CannonStraight cannon;
	Player(String n, int pNumber)
	{
		name = n;
		playerNumber = pNumber;
		cannon = new CannonStraight(this);
	}
	public String toString()
	{
		return name + " = "+ score;
	}
	public int getPlayerNumber(){
		return playerNumber;
	}
}


public class MyGame extends GameNet_CoreGame implements Serializable, Runnable
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	transient GameControl gameControl ;
	static final int NUM_ASTEROIDS = 20;
	static final int NUM_PLAYERS = 2;

	static final double fieldHeight = 600.0;
	static final double fieldWidth = 900.0;
	static final double asteroidRadius = 30.0;
	static final double bulletRadius = 3.0;
	static final double baseRadius = 15.0;
	
	static final int sleepTime = 30;
	static final double OUT_OF_BOUNDS = -fieldWidth;

	Asteroid[] disks =  new Asteroid[NUM_ASTEROIDS];
//	Cannon[] cannons = new Cannon[NUM_PLAYERS];
	Bullet[] bullets = new Bullet[NUM_PLAYERS];//One bullet per player at a time
//	Player[] players = new Player[NUM_PLAYERS];
//	ArrayList<Bullet>bullets = new ArrayList<Bullet>();

	boolean continueToPlay = true;

//	ArrayList<Cannon>cannons = new ArrayList<Cannon>();
	public ArrayList<Player>players = new ArrayList<Player>();

	public int getPlayerNumber(String name){
		int number = -1;
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).name == name) {
				number = players.get(i).playerNumber;
			}
		}
		return number;
	}

	public void startGame(GameControl g)
	{
		this.gameControl = g;
		Thread t = new Thread(this);
		restartGame();
		t.start();
	}
	
	public void run()
	{
		while(continueToPlay)
		{
			try
			{
				Thread.sleep(sleepTime);
				for (int i=0; i < NUM_ASTEROIDS; i++)
				{
					disks[i].updateAsteroid();
				}
				for (int i = 0; i < players.size(); i++) {
					players.get(i).cannon.bullet.updateBullet();
				}
				for (int i=0; i < NUM_ASTEROIDS; i++)
				{
					for (int j = 0; j < players.size(); j++) {
						if (players.get(j).cannon.bullet.isExploded == false) {
							Bullet bullet = players.get(j).cannon.bullet;
							if (disks[i].insideDisk(bullet.currentPoint, bulletRadius, asteroidRadius)){
								// Test if bullet center enters the asteroid
								players.get(j).score += 1;
								bullet.explode();
								break; // Only get one ball per click
							}
						}
					}
				}

				MyGameOutput myGameOutput = new MyGameOutput(this);
				gameControl.putMsgs(myGameOutput);
			} catch (InterruptedException e){}
		}
	}

	// Process commands from each of the game players

	public MyGameOutput process(Object inputs)
	{
		MyGameInput myGameInput = (MyGameInput)inputs;
		switch (myGameInput.command)
		{
		case MyGameInput.CONNECTING:
			// This routine will add a new user if the 
			//  player doesn't already exist
			getClientIndex(myGameInput.myName);
			int tempIndex = getClientIndex(myGameInput.myName);
			myGameInput.setPlayerNumber(tempIndex);
			break;
		case MyGameInput.CLICK: //TODO: Change this to point the cannon and shoot
			System.out.println("checking x="+ myGameInput.dpoint.x + " y="+ myGameInput.dpoint.y);

//			int dex = getClientIndex(myGameInput.myName);
//			System.out.println("Past index");
//			CannonStraight cannon = players.get(dex).cannon;
//			System.out.println("Got my cannon");
//			cannon.shootCannon(myGameInput.dpoint);
//			System.out.println("Shot my cannon");
			
//			for (int i = 0; i < players.size(); i++) {
//				players.get(i).cannon.shootCannon(myGameInput.dpoint);
//			}
			for (int i = 0; i < players.size(); i++) {
				if (players.get(i).name.equals(myGameInput.myName)) {
					players.get(i).cannon.shootCannon(myGameInput.dpoint);
				}
			}
			break;
		case MyGameInput.MOVE:
//			System.out.println("Receiving move command");
//			for (int i = 0; i < players.size(); i++) {
//				players.get(i).cannon.updateCannon(myGameInput.dpoint);
//			}
			for (int i = 0; i < players.size(); i++) {
				if (players.get(i).name.equals(myGameInput.myName)) {
					players.get(i).cannon.updateCannon(myGameInput.dpoint);
				}
			}
			break;
		case MyGameInput.DISCONNECTING:
			int index = getClientIndex(myGameInput.myName);
			if (index >= 0)
				players.remove(index);
			break;
		case MyGameInput.RESETTING:
			restartGame();
			break;
		default: /* ignore */
		}
		MyGameOutput myGameOutput = new MyGameOutput();
		myGameOutput.myGame = this;
		return myGameOutput;
	}

	

	

	// Find index of existing player.  If he doesn't exist add him.
	// There is no limit to the number of clients to this game
	// TODO: Limit players to 2
	private int getClientIndex(String name)
	{
		for (int i=0; i < players.size(); i++)
		{
			if (name.equals(players.get(i).name) )
			{
				return i;
			}
		}
		int playerNumber = players.size(); //hoping that this returns 0, then 1?
		players.add(new Player(name, playerNumber));


		return players.size() -1;
	}


	

	private void restartGame()
	{
		for (int i = 0; i < players.size(); i++)
		{
			players.get(i).score = 0;
		}
		for (int i=0; i < NUM_ASTEROIDS; i++)
		{
			disks[i] = new Asteroid(); // Generates Random Disks
		}
//		for (int i=0; i < players.size(); i++)
//		{
//			if (players.get(i) != null) {
//				cannons.add(new Cannon(players.get(i)));
//			}
//		}
	}
	
	




	public String getScore()
	{
		String retStr="";
		for (int i=0; i < players.size(); i++)
			retStr += players.get(i).toString()+"   ";

		return retStr;
	}
}



