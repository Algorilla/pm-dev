package JFrames;

import javax.swing.*;

import java.awt.*;
import java.sql.*;
import java.awt.event.*;

import javax.swing.UIManager;
import javax.swing.border.*;

import DatabaseConnect.SQLiteDBConnection;
import PModel.MainController;




public class Login_Frame extends JFrame {
	
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	
	private JPanel contentPane;
	private  JTextField txt_username;
	private  JPasswordField txt_passwordField;

	private void initComponents(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		contentPane = new JPanel();
		contentPane.setForeground(Color.YELLOW);
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel loginPanel = new JPanel();
		loginPanel.setBackground(new Color(255, 250, 205));
		loginPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), 
											 "Login", TitledBorder.LEADING, TitledBorder.TOP, 
											 null, new Color(0, 128, 128)));
		
		loginPanel.setBounds(119, 187, 293, 173);
		contentPane.add(loginPanel);	
		loginPanel.setLayout(null);
		
		JLabel lblUserName = new JLabel("User Name:");
		lblUserName.setBounds(43, 43, 76, 14);
		loginPanel.add(lblUserName);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(53, 68, 76, 14);
		loginPanel.add(lblPassword);
		
		txt_passwordField = new JPasswordField();
		txt_passwordField.setBounds(141, 65, 86, 20);
		loginPanel.add(txt_passwordField);
		
		txt_username = new JTextField();
		txt_username.setBounds(141, 40, 86, 20);
		loginPanel.add(txt_username);
		txt_username.setColumns(10);
		
		JButton btnSubmit = new JButton("Submit");
		
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainController.get().Login(txt_username.getText(), txt_passwordField.getText());
				close();
			}
		});
		
		btnSubmit.setBounds(90, 110, 92, 23);
		loginPanel.add(btnSubmit);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon("./resources/img/banner-Process.jpg"));
		lblNewLabel_1.setBounds(10, 0, 964, 551);
		contentPane.add(lblNewLabel_1);
	}

	/**
	 * Create the frame.
	 */
	public Login_Frame() {
		initComponents();
		conn = SQLiteDBConnection.ConnecrDb();
	}
	public void close(){
		WindowEvent winClosingEvent = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
	}
	
}
