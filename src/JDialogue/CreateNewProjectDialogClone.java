package JDialogue;
import Controller.DisplayController;
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
import java.util.Date;
import java.awt.TextArea;

import com.toedter.calendar.JDateChooser;
/**
 * A dialogue used to create new project.
 * @author team B
 *
 */
// TODO: Add fields necessary to use CreateProject function in MainController
public class CreateNewProjectDialogClone extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txt_newProjectNameField;
	private Project newPro = null;
	private TextArea textArea_Description = new TextArea();
	JDateChooser StartedDate;
	private DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	
	
	
	
	
	
}