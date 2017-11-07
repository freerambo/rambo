/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * common -> ScreenShot.java
 * Created on 16 Oct 2017-2:42:44 pm
 */
package common;

import java.awt.Dimension;    
import java.awt.Rectangle;    
import java.awt.Robot;    
import java.awt.Toolkit;    
import java.awt.image.BufferedImage;    
import javax.imageio.ImageIO;    
import java.io.File;    
 
/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  16 Oct 2017 2:42:44 pm
 */
public class ScreenShot {

	/**
	 * @function:
	 * @param args
	 * @author: Rambo Zhu     16 Oct 2017 2:42:44 pm
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		captureScreen("screenshot.png");
	}
	
	public static void captureScreen(String fileName) throws Exception {    
		  
		   Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();    
		   Rectangle screenRectangle = new Rectangle(screenSize);    
		   Robot robot = new Robot();    
		   BufferedImage image = robot.createScreenCapture(screenRectangle);    
		   ImageIO.write(image, "png", new File(fileName));    
		  
		}
	

}
