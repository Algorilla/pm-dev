package JFrames;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

import javax.swing.UIManager;
import javax.swing.border.*;
import DatabaseConnect.SQLiteDBConnection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Login_Frame extends JFrame {
	
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	
	private JPanel contentPane;
	private  JTextField txt_username;
	private  JPasswordField txt_passwordField;
	private final Action action = new SwingAction();

	private void initComponents(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUserName = new JLabel("User Name:");
		lblUserName.setBounds(110, 66, 77, 14);
		contentPane.add(lblUserName);
		
		txt_username = new JTextField();
		txt_username.setBounds(186, 63, 86, 20);
		contentPane.add(txt_username);
		txt_username.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(113, 105, 74, 14);
		contentPane.add(lblPassword);
		
		txt_passwordField = new JPasswordField();
		txt_passwordField.setBounds(186, 102, 86, 20);
		contentPane.add(txt_passwordField);
		
		JButton btnSubmit = new JButton("Submit");
		
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = "select * from UserInfo where username = ? and password = ?";
				try{
					pst = conn.prepareStatement(sql);
					pst.setString(1, txt_username.getText());
					pst.setString(2, txt_passwordField.getText());
					
					rs = pst.executeQuery();
					if(rs.next()){
						JOptionPane.showMessageDialog(null, "username and password is correct.");											
						Employee_info info = new Employee_info();
						info.setUserNameFiled(txt_username.getText());
						info.setVisible(true);
					}else{
						JOptionPane.showMessageDialog(null, "username and password is not correct.");
					}
					
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null,ex);	
				}
				System.out.println(sql);
				
			}
		});
		
		btnSubmit.setAction(action);
		btnSubmit.setBounds(163, 151, 89, 23);
		contentPane.add(btnSubmit);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Login", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		panel.setBounds(84, 35, 238, 173);
		contentPane.add(panel);		
	}

	/**
	 * Create the frame.
	 */
	public Login_Frame() {
		initComponents();
		conn = SQLiteDBConnection.ConnecrDb();
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "Login");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
