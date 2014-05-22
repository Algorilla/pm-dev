package JFrames;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;

import DatabaseConnect.SQLiteDBConnection;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;

public class Employee_info extends JFrame {

	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	
	private JPanel contentPane;
	private JLabel nameLabel;

	final List listProjectList = new List();
	
	/**
	 * Create the frame.
	 */
	public Employee_info() {
		conn = SQLiteDBConnection.ConnecrDb();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblWelcome = new JLabel("Welcome");
		lblWelcome.setBounds(10, 11, 58, 14);
		contentPane.add(lblWelcome);
		
		nameLabel = new JLabel("");
		nameLabel.setBounds(64, 11, 46, 14);
		contentPane.add(nameLabel);
		
		listProjectList.setBounds(10, 48, 170, 129);
		
		String sql = "select * from Project;";
		try{
			pst = conn.prepareStatement(sql);
			
			rs = pst.executeQuery();
			while(rs.next()){
				listProjectList.add(rs.getString("ProjectName"));					
			}
			
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null,ex);	
		}
		
		contentPane.add(listProjectList);
		
		JButton btnCreate = new JButton("Create Project");
		btnCreate.setBounds(236, 82, 127, 23);
		contentPane.add(btnCreate);
		
		JButton btnDelete = new JButton("Delete Project");
		btnDelete.setBounds(236, 116, 127, 23);
		contentPane.add(btnDelete);
		
		JButton btnOpenProject = new JButton("Open Project");
		btnOpenProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String projectName = listProjectList.getSelectedItem().toString();
				JOptionPane.showMessageDialog(null, "Project "+projectName+" Opened");	
			}
		});
		btnOpenProject.setBounds(236, 48, 127, 23);
		contentPane.add(btnOpenProject);
	}
	
	public void setUserNameFiled(String s){
		nameLabel.setText(s);		
	}
}
