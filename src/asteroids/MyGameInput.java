package asteroids;
import java.io.Serializable;

public class MyGameInput implements Serializable
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
// command options
   static final int CONNECTING = 1;
   static final int CLICK = 2;//TODO: Optional, add secondary click for bomb?
   static final int MOVE = 3;
   static final int DISCONNECTING = 100;
   static final int RESETTING = 200;
   
   int command = CONNECTING;  
   
   String myName;
   DPoint dpoint;  
   int playerNumber;
//   Point point;
   
   public void setName(String name)
   {
	   myName = name;
   }   
   
   public void setPlayerNumber(int pNumber){
	   playerNumber = pNumber;
   }
    
}


class DPoint implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	double x,y;
	
	DPoint()
	{
		x = MyGame.fieldWidth * Math.random();
		y = MyGame.fieldHeight * Math.random();
	}
	DPoint(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
}
