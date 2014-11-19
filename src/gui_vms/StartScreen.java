/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_vms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

/**
 *
 * @author abduh
 */
public class StartScreen extends JWindow {
  private int duration;
  public StartScreen(int d) {
    duration = d;
  }
  
   public void showSplash() {
    JPanel content = (JPanel)getContentPane();
    content.setBackground(Color.white);

    // Set the window's bounds, centering the window
    int width = 504;
    int height =308;
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (screen.width-width)/2;
    int y = (screen.height-height)/2;
    setBounds(x,y,width,height);

      JLabel label = new JLabel(new ImageIcon(getClass().getResource("/gui_vms/splash.png")));
      content.add(label);


    // Display it
    setVisible(true);

    // Wait a little while, maybe while loading resources
    try { Thread.sleep(duration); } catch (Exception e) {}

    setVisible(false);
  }

  public void showSplashAndExit() {
    showSplash();
    //System.exit(0);
  }


}

 