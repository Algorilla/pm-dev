package Driver;
import java.awt.EventQueue;
import java.sql.*;
import javax.swing.*;

import JFrames.Login_Frame;
/**
 * 
 * @author COMP354 Team B
 * @version 1.0
 *
 */
public class DriverClass {

	/**
	 * main driver
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login_Frame frame = new Login_Frame();
					frame.setSize(1000, 600);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});		
	}

}
