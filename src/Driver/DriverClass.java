package Driver;

import java.awt.EventQueue;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;

import Controller.DisplayController;
import Controller.MainController;
import JFrames.LoginFrame;
import PModel.Project;
import PModel.Activity;

/**
 * Main Driver Class for Project Management Software
 * 
 * @author COMP354 Team B
 */
public class DriverClass {

	/**
	 * Main driver
	 * 
	 * @param args
	 */
	MainController mc = MainController.get();
	public static boolean newGui = true;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					launchApplication();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static void launchApplication() {
		if (newGui) {
			DisplayController.get();
		} else {
			LoginFrame frame = new LoginFrame();
			frame.setSize(1000, 600);
			frame.setVisible(true);
        }
    }
}
