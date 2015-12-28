package asteroids;

import java.awt.Color;
import java.io.Serializable;

public class Bullet implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DPoint cannonMouth;
	DPoint originPoint;
	DPoint currentPoint;
	boolean isExploded;
	boolean isExploding;//TODO: use this to show exploding animation?
	double vx,vy;
	double speed = 7.0;
	Color color = Color.BLACK;
	
//	Cannon cannon = new Cannon();
			
	BoardDimensions boardDimensions = new BoardDimensions();
	
	Bullet(Player player){
		isExploded = true;
		isExploding = false;
		if (player.playerNumber != 0){// If this is player 2
			speed *= -1;
		}
	}
	
	void updateBullet(){
		if (isExploded == false) {
			currentPoint.x += vx;
//			currentPoint.y += vy;
//			if (currentPoint.x == destinationPoint.x
//				&& currentPoint.y == destinationPoint.y) {
//				explode();
//			}
			// Stop at top wall
			if (currentPoint.y <= 0)
			{
				isExploded = true;
			}
//			// Stop at bottom wall
//			if (currentPoint.y > MyGame.fieldHeight)
//			{
//				isExploded = true;
//			}
			// Check Right Wall
			if (currentPoint.x > MyGame.fieldWidth)
			{
				explode();
			}
			// Check Left Wall
			if (currentPoint.x <= 0)
			{
				explode();
			}
		}
	}
	
	
	void explode(){
		//set image == explosion temporarily, velocity == 0, remove bullet from game
		isExploded = true;
	}
	
	
//	double calculateXVelocity(double angle){
//		double vx = speed * Math.sin(angle);
//		return vx;
//	}
//	
//	double calculateYVelocity(double angle){
//		double vy = (speed * Math.cos(angle)) * -1;
//		return vy;
//	}
	
//	double calculateXVelocity(DPoint dest){
//		double vx = Math.cos(Math.toRadians(angle)) * velocity;
//		return vx;
//	}
//	
//	double calculateYVelocity(DPoint dest){
//		double vy = Math.sqrt(Math.pow(velocity, 2) - Math.pow(vx, 2));
//		return vy;
//	}
//	
//	double getAngle(DPoint dest) {
//		BulletPoint bp = new BulletPoint(dest);
//		double tempX = bp.x;
//		double tempY = bp.y;
//	    return Math.toDegrees(Math.atan2(tempY, tempX));
//	}
//
//	double getVelocityWithAngle(DPoint dest) {
//		BulletPoint bp = new BulletPoint(dest);
//		double tempX = bp.x;
//		double tempY = bp.y;
//	    return Math.sqrt(Math.pow(tempX, 2) + Math.pow(tempY, 2));
//	}
	
	
	
	
	

}
