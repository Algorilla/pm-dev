package JDialogue;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JComboBox;

import Controller.DisplayController;

public class AddTeamMember extends JDialog {

	private String memberName;

	public AddTeamMember(final JComboBox<String> memberComboBox) {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Add Team Member");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 477, 253);
		getContentPane().setLayout(null);
		getContentPane().setBackground(new Color(204, 255, 204));

		JLabel lblSelectTeamMember = new JLabel(
				"Select Team Member to Add to Activity");
		lblSelectTeamMember.setBounds(35, 24, 297, 22);
		getContentPane().add(lblSelectTeamMember);

		JLabel lblMembers = new JLabel("Members:");
		lblMembers.setBounds(79, 84, 61, 16);
		getContentPane().add(lblMembers);

		memberComboBox.setBounds(174, 82, 277, 29);
		getContentPane().add(memberComboBox);

		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(174, 138, 117, 29);
		getContentPane().add(btnAdd);

		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				memberName = ((String)memberComboBox.getSelectedItem());
				DisplayController.get().setMemberNameToAdd(memberName);
				dispose();
			}
		});

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(334, 138, 117, 29);
		getContentPane().add(btnCancel);

		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DisplayController.get().setMemberNameToAdd(null);
				dispose();
			}
		});

		setVisible(true);
	}
}