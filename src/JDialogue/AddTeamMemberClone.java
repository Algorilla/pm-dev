package JDialogue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Dialog.ModalityType;
import java.awt.List;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Controller.MainController;
import PModel.Activity;
import PModel.Member;
import PModel.MemberActivity;
import PModel.Project;

import com.toedter.calendar.JDateChooser;

import javax.swing.JComboBox;
import javax.swing.JList;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 * A dialogue used to create new activity of the project
 * @author Administrator
 *
 */
public class AddTeamMemberClone extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Activity activity;
	private ArrayList<Member> teamMembers;

	public AddTeamMemberClone(Activity a) {
		
	}
	
	
	
}
	