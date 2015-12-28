package asteroids;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Assets {
	// Images
	String cannonLeftImage = "images/cannonLeft.png";
	String cannonRightImage = "images/cannonRight.png";
	BufferedImage cannonLeft;
	BufferedImage cannonRight;
//	Image cannonLeft = new ImageIcon(cannonLeftImage).getImage();
//	Image cannonRight = new ImageIcon(cannonRightImage).getImage();
	
	void drawAssets(Graphics graphics, DPoint cannonMouth){
		graphics.drawImage(cannonLeft, (int)cannonMouth.x, (int)cannonMouth.y, null);
	}
	
	Assets(){
		try {
			cannonLeft = ImageIO.read(new File(cannonLeftImage));
			cannonRight = ImageIO.read(new File(cannonRightImage));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
