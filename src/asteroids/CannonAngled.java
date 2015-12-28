//package asteroids;
//
//import java.awt.Color;
//import java.awt.Image;
//import java.awt.Point;
//import java.io.Serializable;
//
////import javax.swing.ImageIcon;
//
//public class CannonAngled implements Serializable {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	
////	private String imagePath = "/images/cannon";
//	Image cannonImage;
//	DPoint cannonMouth;
//	DPoint originPoint;
//	double radius = 30.0; //Hard coded for now, can change in future
//	double angleInRadians;
//	Bullet bullet;
//	Color color = Color.gray;
//	
//	
////	BoardDimensions boardDimensions = new BoardDimensions();
//	
//	
//	CannonAngled(Player player){
//		originPoint = getOriginPoint(player);
//		angleInRadians = Math.toRadians(getInitialAngleInDegrees(player));
//		cannonMouth = getCannonMouth();
//		bullet = new Bullet();//Each cannon only has one bullet to shoot
//	}
//	
//	void updateCannon(DPoint mousePosition){
//		//TODO: logic to update cannonMouth
////		BulletPoint bp = new BulletPoint(mousePosition);
//		angleInRadians = getAngleInRadians(mousePosition);
//		cannonMouth = getCannonMouth();
//	}
//	
//	void shootCannon(DPoint mousePosition){
//		bullet.currentPoint = cannonMouth;//Starts the bullet at the mouth of the cannon
//		bullet.vx = bullet.calculateXVelocity(angleInRadians);//Setting horizontal velocity with current angle
//		bullet.vy = bullet.calculateYVelocity(angleInRadians);//Setting vertical velocity with current angle
//		bullet.isExploded = false;//This is our cue to draw the bullet
//	}
//	
//	DPoint getCannonMouth(){
//		BulletPoint tempCannonMouth = new BulletPoint();
////		DPoint originPoint = getOriginPoint();
//		tempCannonMouth.x = /*originPoint.x + */(radius * Math.sin(angleInRadians));
//		tempCannonMouth.y = /*originPoint.y - */(radius * Math.cos(angleInRadians));
//		return tempCannonMouth.toGenericPoint(tempCannonMouth);
//	}
//	
//	double getAngleInRadians(DPoint mousePosition){//Need to get angle without cannonMouth
//		double tempX = mousePosition.x - originPoint.x;
//		double tempY = originPoint.y - mousePosition.y;
//		
//		return Math.atan2(tempY, tempX);
//	}
//	
//	DPoint getOriginPoint(Player player){
//		DPoint dp = new DPoint();
//		if (player.playerNumber == 0) {//Hardcoding where the cannons start for now, only two players
//			dp.y = 300;
//			dp.x = 5;
//		} else if (player.playerNumber == 1){
//			dp.y = 300;
//			dp.x = 890;
//		}
//		return dp;
//	}
//	
//	double getInitialAngleInDegrees(Player player){
//		double tempAngle = 90.0;
//		if (player.playerNumber == 0) {//Hardcoding where the cannons start for now, only two players
//			tempAngle = 90.0;
//		} else if (player.playerNumber == 1){
//			tempAngle = 270.0;
//		}
//		return tempAngle;
//	}
//	
//	double getCannonRadius(){
//		return radius;
//	}
//	
//////INNER CLASS ////
//	class BulletPoint implements Serializable
//	{
//		/**
//		 * This class makes it possible for us to compute the correct 
//		 * bullet velocity as it leaves the cannon
//		 * It is essentially a translated point on the canvas to make the cannon
//		 * originPoint appear at 0, 0
//		 */
//		private static final long serialVersionUID = 1L;
//		double x, y;
//		
//		BulletPoint()
//		{
//			x = originPoint.x;
//			y = originPoint.y;
//		}
//		BulletPoint(DPoint dp)
//		{
//			this.x = (dp.x - originPoint.x);
//			this.y = (originPoint.y - dp.y);
//		}
//		DPoint toGenericPoint(BulletPoint bp){
//			DPoint dp = new DPoint();
//			dp.x = bp.x;
//			dp.y = (bp.y + originPoint.y);
//			
//			return dp;
//		}
//		BulletPoint toBulletPoint(DPoint dp){
//			BulletPoint bp = new BulletPoint();
//			bp.x = dp.x;
//			bp.y = (dp.y - originPoint.y);
//			return bp;
//		}
//	}
//
//}
