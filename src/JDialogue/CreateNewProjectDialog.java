package JDialogue;
import DatabaseConnect.SQLiteDBConnection;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CreateNewProjectDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txt_newProjectNameField;
	private String userName = null;
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	/**
	 * Create the dialog.
	 */
	public CreateNewProjectDialog(String name) {
		conn = SQLiteDBConnection.ConnecrDb();
		this.userName = name;
		
		setModalityType(ModalityType.APPLICATION_MODAL);
	    setTitle("Create New Project");
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    setLocationRelativeTo(null);
		setBounds(100, 100, 450, 250);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblProjectName = new JLabel("Project Name:");
			lblProjectName.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblProjectName.setBounds(44, 84, 123, 14);
			contentPanel.add(lblProjectName);
		}
		{
			txt_newProjectNameField = new JTextField();
			txt_newProjectNameField.setBounds(170, 82, 200, 20);
			contentPanel.add(txt_newProjectNameField);
			txt_newProjectNameField.setColumns(10);
		}
		{
			JLabel lblPleaseEnterNew = new JLabel("Please enter new project name:");
			lblPleaseEnterNew.setFont(new Font("Verdana", Font.PLAIN, 13));
			lblPleaseEnterNew.setBounds(10, 25, 225, 14);
			contentPanel.add(lblPleaseEnterNew);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String sql = "insert into Project (ProjectName, ProjectManager)values(?,?)";
						try{
							pst = conn.prepareStatement(sql);
							pst.setString(1, txt_newProjectNameField.getText());
							pst.setString(2, userName);
							pst.execute();
							pst.close();
						}catch(Exception ex){
							JOptionPane.showMessageDialog(null,ex);	
						}
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
