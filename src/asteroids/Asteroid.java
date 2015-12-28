package asteroids;

import java.awt.Color;
import java.io.Serializable;

public class Asteroid implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	double vx,vy;
	DPoint currentPoint;
	Color color;
	double speed = 10; // Adjust this number to change asteroid speed.
	
	Asteroid()
	{
		currentPoint = new DPoint();// generates random location
		vx = (-0.5 + Math.random()) * speed;
		vy = (-1.0 + 2*Math.random()) * speed;
		color = new Color((float)Math.random(),(float)Math.random(),(float)Math.random());
	}
	boolean isInPlay()
	{
		if (currentPoint.x < 0)
			return false;
		else
			return true;
	}
	boolean insideDisk(DPoint bulletPoint, double bulletRadius, double asteroidRadius)
	{
		if ( bulletPoint.x <= (currentPoint.x + asteroidRadius)  &&
				bulletPoint.x >=	(currentPoint.x - asteroidRadius) &&
				bulletPoint.y <=	(currentPoint.y + asteroidRadius) &&
				bulletPoint.y >= (currentPoint.y - asteroidRadius))
		{
			currentPoint.x= MyGame.OUT_OF_BOUNDS; // Take this disk out of play
			return true;  // the click is inside a box that surrounds the disk
		}
		else
			return false;
	}
	void updateAsteroid()
	{
		if (currentPoint.x <= MyGame.OUT_OF_BOUNDS ) return;
		//This is moving the asteroid
		currentPoint.x += vx;
		currentPoint.y += vy;

		// Bounce off top wall
		if (currentPoint.y < MyGame.asteroidRadius)
		{
			vy = -vy;
			currentPoint.y= MyGame.asteroidRadius;
		}
		// Bounce off bottom wall
		if (currentPoint.y > MyGame.fieldHeight-MyGame.asteroidRadius)
		{
			vy = - vy;
			currentPoint.y= MyGame.fieldHeight-MyGame.asteroidRadius;
		}

		// Check Right Wall
		if (currentPoint.x > MyGame.fieldWidth-MyGame.asteroidRadius)
		{
			vx = -vx;
			currentPoint.x = MyGame.fieldWidth-MyGame.asteroidRadius;
		}


		// Check Left Wall
		if (currentPoint.x < MyGame.asteroidRadius)
		{
			vx = -vx;
			currentPoint.x = MyGame.asteroidRadius;
		}
	}
}
