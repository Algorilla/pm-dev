package JFrames;

import javax.swing.*;

import java.awt.*;
import java.sql.*;
import java.awt.event.*;

import javax.swing.UIManager;
import javax.swing.border.*;
import javax.swing.GroupLayout.Alignment;

import Controller.DisplayController;
import Controller.MainController;

public class LoginFrameClone extends JFrame {

	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;

	private JPanel contentPane;
	private JTextField textUserNameField;
	private JPasswordField passwordField;

	public LoginFrameClone() {
		setBounds(new Rectangle(0, 0, 980, 400));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setForeground(Color.YELLOW);
		getContentPane().setLayout(null);
		getContentPane().setBounds(new Rectangle(100, 100, 1000, 600));
		
		JPanel loginPanel = new JPanel();
		loginPanel.setBackground(new Color(255, 250, 205));
		loginPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"Login", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 128, 128)));
		loginPanel.setBounds(119, 187, 293, 173);
		getContentPane().add(loginPanel);
		loginPanel.setLayout(null);
		
		JLabel lblUserName = new JLabel("User Name:");
		lblUserName.setBounds(new Rectangle(43, 43, 76, 14));
		loginPanel.add(lblUserName);
		
		textUserNameField = new JTextField();
		textUserNameField.setBounds(new Rectangle(141, 40, 86, 20));
		lblUserName.setLabelFor(textUserNameField);
		loginPanel.add(textUserNameField);
		textUserNameField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(new Rectangle(53, 68, 76, 14));
		loginPanel.add(lblPassword);
		lblPassword.setLabelFor(passwordField);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(new Rectangle(141, 65, 86, 20));
		loginPanel.add(passwordField);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(90, 110, 92, 23);
		loginPanel.add(btnSubmit);
		
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DisplayController.get().login(textUserNameField.getText(),
						new String(passwordField.getPassword()));
			}

		});
		JLabel imageLabel = new JLabel("");
		//TODO: make it relative!
		imageLabel.setIcon(new ImageIcon("C:\\Users\\dmitri\\Documents\\git\\soen\\pm-dev\\resources\\img\\banner-Process.jpg"));
		imageLabel.setBounds(10, 0, 964, 551);
		getContentPane().add(imageLabel);
	
		
	}
	
	public void close() {
		WindowEvent closeWindowEvent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWindowEvent);
	}
}