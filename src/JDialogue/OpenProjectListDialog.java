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
import java.awt.List;

public class OpenProjectListDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	final List projectList = new List();
	String projectName = null;
	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	private String userName = null;
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;

	public OpenProjectListDialog(String name) {
		conn = SQLiteDBConnection.ConnecrDb();
		this.userName = name;
		
		setModalityType(ModalityType.APPLICATION_MODAL);
	    setTitle("Open Project List");
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    setLocationRelativeTo(null);
		setBounds(100, 100, 450, 250);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		projectList.setBounds(44, 46, 329, 108);
		contentPanel.add(projectList);
		String sql = "select * from Project where ProjectManager = ?;";
		try{
			pst = conn.prepareStatement(sql);	
			pst.setString(1, userName);
			rs = pst.executeQuery();
			while(rs.next()){
				projectList.add(rs.getString("ProjectName"));					
			}
			rs.close();
			pst.close();
			
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null,ex);	
		}
			
			JLabel lblNowYouHave = new JLabel("Now, you have the following projects: ");
			lblNowYouHave.setBounds(23, 11, 321, 14);
			contentPanel.add(lblNowYouHave);

			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);

			JButton okButton = new JButton("OK");
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String projectNam = projectList.getSelectedItem().toString();
					setProjectName(projectNam);
					//JOptionPane.showMessageDialog(null, "Project "+projectName+" Opened");	
					dispose();
				}
			});
			okButton.setActionCommand("OK");
			buttonPane.add(okButton);
			getRootPane().setDefaultButton(okButton);			
	}

}
