package JDialogue;
import Controller.DisplayController;
import Controller.ErrorController;
import Controller.MainController;
import Controller.PModelChange;
import PModel.Member;
import PModel.Project;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.awt.TextArea;

import com.toedter.calendar.JDateChooser;
/**
 * A dialogue used to create new project.
 * @author team B
 *
 */
// TODO: Add fields necessary to use CreateProject function in MainController
public class CreateNewProjectDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txt_newProjectNameField;
	private Project newPro = null;
	private TextArea textArea_Description = new TextArea();
	JDateChooser StartedDate;
	private DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * Create the dialog.
	 */
	public CreateNewProjectDialog() {
		setModalityType(ModalityType.APPLICATION_MODAL);
	    setTitle("Create New Project");
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    setLocationRelativeTo(null);
		setBounds(100, 100, 450, 460);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(204, 255, 204));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblProjectName = new JLabel("Project Name:");
			lblProjectName.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
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
			lblPleaseEnterNew.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
			lblPleaseEnterNew.setBounds(10, 25, 225, 14);
			contentPanel.add(lblPleaseEnterNew);
		}
		
		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setBounds(47, 126, 83, 14);
		contentPanel.add(lblDescription);
		
		textArea_Description.setBounds(170, 110, 200, 119);
		contentPanel.add(textArea_Description);
		
		StartedDate = new JDateChooser();
		StartedDate.setBounds(170, 238, 200, 20);
		contentPanel.add(StartedDate);
		{
			JLabel lblStartDate = new JLabel("Start Date");
			lblStartDate.setBounds(44, 242, 86, 16);
			contentPanel.add(lblStartDate);
		}
		
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String projectName = txt_newProjectNameField.getText();

						String projectDescription = textArea_Description.getText();
						Date startDate = StartedDate.getDate(); //date from JDateChooser
						
						//Today's date for error checking
						Calendar c = Calendar.getInstance();
						c.set(Calendar.HOUR, 0);
						c.set(Calendar.MINUTE, 0);
						c.set(Calendar.SECOND, 0);
						Date today = c.getTime();
						
						Member currentUser = MainController.get().getCurrentUser();
						int managerID = currentUser.getMemberID();
						
						if(projectName.isEmpty()){
							ErrorController.get().showError("Please enter a project name");
						}else if(projectDescription.isEmpty()){
							ErrorController.get().showError("Please enter a project description");
						}else if(startDate == null){
							ErrorController.get().showError("Date enterned is null. Please enter correct date");
						}else if(startDate.before(today)){
							ErrorController.get().showError("Date entered is in the past");
						}else{
								newPro = new Project(managerID, projectName, projectDescription,
										startDate, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
//								MainController.get().initializeProject(newPro);
//								MainController.get().openProject(newPro.getName());
								MainController.get().notifyDisplayController(PModelChange.CREATED_PROJECT);
								dispose();
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
}
