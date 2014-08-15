package Driver;

import java.awt.EventQueue;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;

import Controller.DisplayController;
import Controller.MainController;
import JFrames.Login_Frame;
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

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					launch();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static void launch() {
		boolean newGui = true;
		if (newGui) {
			DisplayController.get();
		} else {
			Login_Frame frame = new Login_Frame();
			frame.setSize(1000, 600);
			frame.setVisible(true);
		}
	}

}
