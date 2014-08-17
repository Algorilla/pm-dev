package JDialogue;

import Controller.DisplayController;
import Controller.MainController;
import Controller.PModelChange;
import Controller.SQLiteDBConnection;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.List;
/**
 * A dialogue to open project from list.
 * @author Administrator
 *
 */
public class OpenProjectListDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	List projectList = new List();
	String projectName = null;
	
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;

	public OpenProjectListDialog() {
		conn = SQLiteDBConnection.ConnectDb();
		
		setModalityType(ModalityType.APPLICATION_MODAL);
	    setTitle("Open Project List");
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    setLocationRelativeTo(null);
		setBounds(100, 100, 450, 250);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		projectList = MainController.get().getProjectList();
		projectList.setBounds(44, 46, 329, 108);
		contentPanel.add(projectList);
		
		JLabel lblNowYouHave = new JLabel("Now, you have the following projects: ");
		lblNowYouHave.setBounds(23, 11, 321, 14);
		contentPanel.add(lblNowYouHave);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String projectName = projectList.getSelectedItem().toString();
				MainController.get().openProject(projectName);
				MainController.get().notifyDisplayController(PModelChange.OPENED_PROJECT);
				//JOptionPane.showMessageDialog(null, "Project "+projectName+" Opened");	
				dispose();
			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);			
	}
}
