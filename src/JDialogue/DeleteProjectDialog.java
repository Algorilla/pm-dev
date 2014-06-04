package JDialogue;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DatabaseConnect.SQLiteDBConnection;
import PModel.MainController;

import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.List;
/**
 * a dialouge used to delete project from list.
 * @author Administrator
 *
 */
public class DeleteProjectDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	List projectList = new List();
	String projectName = null;
	
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;

	public String getProjectName()
	{
		return projectName;
	}
	
	public DeleteProjectDialog() {
		conn = SQLiteDBConnection.ConnectDb();
		
		setModalityType(ModalityType.APPLICATION_MODAL);
	    setTitle("Delete Project from List");
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    setLocationRelativeTo(null);
		setBounds(100, 100, 450, 250);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		projectList = MainController.get().GetProjectList();
		projectList.setBounds(44, 46, 329, 108);
		contentPanel.add(projectList);
		
		JLabel lblNowYouHave = new JLabel("Now, you have the following projects: ");
		lblNowYouHave.setBounds(23, 11, 321, 14);
		contentPanel.add(lblNowYouHave);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("Delete");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				projectName = projectList.getSelectedItem().toString();
				MainController.get().DeleteProject(projectName);
				//JOptionPane.showMessageDialog(null, "Project "+projectName+" Deleted");	
				dispose();
			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);			
	}

}