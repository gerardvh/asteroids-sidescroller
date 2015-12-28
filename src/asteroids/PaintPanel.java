package asteroids;

import gameNet.GamePlayer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.swing.JLabel;
import javax.swing.JPanel;

public	class PaintPanel extends JPanel implements ImageObserver
	{
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		Image gOffScreenImage = null;
		BufferedImage bufferedOffScreenImage;
		int lastWidth = -1, lastHeight = -1;
		MyGame myGame = null;
		JLabel statusLabel;
		String myName;
		int playerNumber;
		MyGameInput myGameInput = new MyGameInput();
		GamePlayer myGamePlayer;
		
//		Assets assets = new Assets();
		
		

//		BoardDimensions boardDimensions = new BoardDimensions();

		PaintPanel(JLabel statusLabel, String myName, GamePlayer gamePlayer)
		{
			this.statusLabel = statusLabel;
			this.myName = myName;
			myGameInput.setName(myName);
			myGamePlayer = gamePlayer;
			addMouseListener(new MouseMonitor());
			addMouseMotionListener(new MouseMonitor());
		}
		public void updatePaint(MyGame g)
		{
			myGame = g;
			repaint();
		}
		
	

		public void paint(Graphics g)
		{
			if (myGame == null) return;
			
//			Insets insets = super.getInsets();		 
			Dimension d = getSize();
			if (lastWidth != d.width || lastHeight != d.height)
			{ 
//				gOffScreenImage = createImage(getBounds().width, getBounds().height);
				bufferedOffScreenImage = getGraphicsConfiguration().createCompatibleImage(getBounds().width, getBounds().height);
				lastWidth = d.width;
				lastHeight = d.height;
			}	    
			Graphics2D offScreenGraphics = bufferedOffScreenImage.createGraphics(); 
			
			offScreenGraphics.setColor(Color.white);
			offScreenGraphics.fillRect(0, 0, d.width, d.height);
			
			statusLabel.setText(myName + "'s Screen: " + myGame.getScore());
			
//			boardDimensions.setParms(insets.top, insets.left,
//					d.width - insets.left - insets.right , d.height - insets.top - insets.bottom);
			
			offScreenGraphics.setColor(Color.black);
			
			// Draw Bullets
			drawBullets(offScreenGraphics);
			
			// Draw Cannons
			drawCannons(offScreenGraphics);
			
			// Draw asteroids
			drawAsteroids(offScreenGraphics);
			
			g.drawImage(bufferedOffScreenImage, 0, 0, this); 

		}  
		

		void drawAsteroids(Graphics offScreenGraphics){
			for (int i=0; i < MyGame.NUM_ASTEROIDS; i++)
			{
				Asteroid disk = myGame.disks[i];
				if (disk.isInPlay())
				{
					offScreenGraphics.setColor(disk.color);
//					DPoint dp = boardDimensions.toPaintPoint(disk.dp);
					int x = (int)disk.currentPoint.x;
					int y = (int)disk.currentPoint.y;
//					int r= (int)(boardDimensions.toPaintScaleX(MyGame.asteroidRadius));
					int r = (int)MyGame.asteroidRadius;
					offScreenGraphics.fillOval(x - r, y - r, 2 * r, 2 * r);
				}
			}
		}
		
		void drawCannons(Graphics offScreenGraphics){
			for (int i = 0; i < myGame.players.size(); i++) {
				if (myGame.players.get(i) != null) {
					CannonStraight cannon = myGame.players.get(i).cannon;
//					DPoint cannonMouth = boardDimensions.toPaintPoint(cannon.getCurrentCannonMouth());
//					DPoint originPoint = boardDimensions.toPaintPoint(cannon.getCurrentOriginPoint());
					DPoint cannonMouth = cannon.getCurrentCannonMouth();
					DPoint originPoint = cannon.getCurrentOriginPoint();
						
					if (myGame.players.get(i).playerNumber == 0) {// First player
//						int x1 = (int) (cannonMouth.x);
//						int y1 = (int) (cannonMouth.y);
						int x2 = (int) (originPoint.x);
						int y2 = (int) (originPoint.y - 1);
//						offScreenGraphics.drawLine(x1, y1, x2, y2);
						offScreenGraphics.fillRect(x2, y2, (int)cannon.radius, 3);
						offScreenGraphics.fillOval((int)(originPoint.x - MyGame.baseRadius), 
												   (int)(originPoint.y - MyGame.baseRadius), 
												   (int)(MyGame.baseRadius * 2), 
												   (int)(MyGame.baseRadius * 2));
					} else { // player 2
						int x1 = (int) (cannonMouth.x);
						int y1 = (int) (cannonMouth.y - 1);
//						int x2 = (int) (originPoint.x);
//						int y2 = (int) (originPoint.y);
//						offScreenGraphics.drawLine(x1, y1, x2, y2);
						offScreenGraphics.fillRect(x1, y1, (int)cannon.radius, 3);
						offScreenGraphics.fillOval((int)(originPoint.x - MyGame.baseRadius), 
								   (int)(originPoint.y - MyGame.baseRadius), 
								   (int)(MyGame.baseRadius * 2), 
								   (int)(MyGame.baseRadius * 2));
					}
					
					 //TODO: Phase2, drawImages instead of lines
				}
			}
		}
		
		void drawBullets(Graphics offScreenGraphics){
			for (int i = 0; i < myGame.players.size(); i++) {
				Bullet bullet = myGame.players.get(i).cannon.bullet;
				if (bullet.isExploded == false) {
					int x = (int)bullet.currentPoint.x;
					int y = (int)bullet.currentPoint.y;
//					int r= (int)(boardDimensions.toPaintScaleX(MyGame.asteroidRadius));
					int r = (int)MyGame.bulletRadius;
					offScreenGraphics.setColor(bullet.color);
					offScreenGraphics.fillOval(x - r, y - r, 2 * r, 2 * r);
				}
			}
		}

		//*******************************************
		// Another Inner class of PaintPanel
		//*******************************************
		class MouseMonitor extends MouseAdapter
		{
			public void mousePressed(MouseEvent e)
			{	 
//				DPoint dpoint = boardDimensions.toGenericPoint(e.getPoint());
				DPoint dpoint = new DPoint(e.getPoint().x, e.getPoint().y);
				if (dpoint != null)
				{
					myGameInput.dpoint = dpoint;
					myGameInput.command = MyGameInput.CLICK;
					// Tells our model there was a click and where it was
				    myGamePlayer.sendMessage(myGameInput);
				}
			}
			
			public void mouseMoved(MouseEvent e) //Used to update the angle for the cannons
			{
//				DPoint dpoint = boardDimensions.toGenericPoint(e.getPoint());
//				DPoint dpoint = e.getPoint();
				DPoint dpoint = new DPoint(e.getPoint().x, e.getPoint().y);
				if (dpoint != null)
				{
					myGameInput.dpoint = dpoint;
					myGameInput.command = MyGameInput.MOVE;
				    myGamePlayer.sendMessage(myGameInput);
				}
//				Point p = e.getPoint();
//				if (p != null)
//					{
//						myGameInput.point = p;
//						myGameInput.command = MyGameInput.MOVE;
//					    myGamePlayer.sendMessage(myGameInput);
//					}
			}
			
			public void mouseDragged(MouseEvent e){
				DPoint dpoint = new DPoint(e.getPoint().x, e.getPoint().y);
				if (dpoint != null)
				{
					myGameInput.dpoint = dpoint;
					myGameInput.command = MyGameInput.MOVE;
				    myGamePlayer.sendMessage(myGameInput);
				}
			}

		}



	}
