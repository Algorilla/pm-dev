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
 * 
 * @author Administrator
 *
 */
public class AddTeamMemberClone extends JDialog {

	private Activity activity;
	private ArrayList<Member> teamMembers;

	public AddTeamMemberClone(Activity a) {
		activity = a;
		teamMembers = MainController.get()
				.getMemberListForAddMemberToActivity();

		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Add Team Member");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setBounds(100, 100, 477, 593);
		setVisible(true);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(new Color(204, 255, 204));
		//getContentPane().setBorder(new EmptyBorder(5, 5, 5, 5));
		//getContentPane().add(getContentPane(), BorderLayout.CENTER);
		getContentPane().setLayout(null);

		JLabel lblSelectTeamMember = new JLabel(
				"Select Team Member to Add to Activity");
		lblSelectTeamMember.setBounds(35, 24, 297, 22);
		getContentPane().add(lblSelectTeamMember);

		JLabel lblMembers = new JLabel("Members");
		lblMembers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}// /WHAT
		});
		lblMembers.setBounds(92, 207, 61, 16);
		getContentPane().add(lblMembers);

		final JComboBox membersComboBox = new JComboBox();
		membersComboBox.setBounds(189, 203, 193, 27);

		for (Member m : teamMembers) {
			membersComboBox.addItem(m);
		}
		getContentPane().add(membersComboBox);

		JButton btnAdd = new JButton("Add");
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int v = ((Member) membersComboBox.getSelectedItem())
						.getMemberID();

				MemberActivity ma = new MemberActivity(
						((Member) membersComboBox.getSelectedItem())
								.getMemberID(), activity.getProjectID(),
						activity.getNumber());
				MainController.get().initializeMemberActivity(ma);
				//TODO: initilize members through displaycontroller and not MainController
			}
		});
		btnAdd.setBounds(265, 366, 117, 29);
		getContentPane().add(btnAdd);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");

				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

}
