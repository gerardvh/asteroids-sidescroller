package asteroids;
import java.io.Serializable;



public class MyGameOutput  implements Serializable
{
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
MyGame myGame =null;   
   MyGameOutput(){};
   MyGameOutput(MyGame mygame)
   {
	   this.myGame = mygame;
   }
}