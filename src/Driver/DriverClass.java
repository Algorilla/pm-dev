package Driver;
import java.awt.EventQueue;
import java.sql.*;

import javax.swing.*;

import JFrames.Login_Frame;
import PModel.MainController;

/**
 * Main Driver Class for Project Management Software 
 * @author COMP354 Team B
 */
public class DriverClass {

	/**
	 * Main driver
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
