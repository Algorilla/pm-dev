package JFrames;

import java.awt.Color;
import java.awt.TextArea;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Controller.DisplayController;
import javax.swing.JButton;

public class TeamMemberView extends UserInterfaceView {

	private TextArea textAreaDescription;

	public TeamMemberView(final JTable activitiesTable) {
		setBounds(250, 100, 1170, 668);

		// ======================== main panel ================================
		// contains both activities & description panel

		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(new Color(0, 153, 204));

		mainPanel.setBorder(new TitledBorder(null, "Team Member",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		mainPanel.setBounds(0, 0, 1150, 590);
		getContentPane().add(mainPanel);
		mainPanel.setLayout(null);

		// ====================== activity panel ===============================
		JPanel activitiesPanel = new JPanel();
		activitiesPanel.setBackground(Color.WHITE);
		activitiesPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		activitiesPanel.setBounds(20, 30, 615, 550);
		mainPanel.add(activitiesPanel);
		activitiesPanel.setLayout(null);
		
		JLabel lblActivities = new JLabel("Activity List:");
		lblActivities.setBounds(10, 14, 80, 14);
		activitiesPanel.add(lblActivities);

		activitiesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		activitiesTable.setBorder(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 35, 595, 491);
		activitiesPanel.add(scrollPane);

		scrollPane.setViewportView(activitiesTable);

		activitiesTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = activitiesTable.getSelectedRow();
				int PID = Integer.parseInt(activitiesTable.getModel()
						.getValueAt(row, 0).toString());
				int number = Integer.parseInt(activitiesTable.getModel()
						.getValueAt(row, 1).toString());
				DisplayController.get().selectActivity(PID, number, false);;
			}
		});
		
		// ====================== description panel ===============================
		JPanel descriptionPanel = new JPanel();
		descriptionPanel.setBackground(Color.WHITE);
		descriptionPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		descriptionPanel.setBounds(645, 30, 495, 550);
		mainPanel.add(descriptionPanel);
		descriptionPanel.setLayout(null);

		JLabel lblDescription = new JLabel("Description");
		lblDescription.setBounds(10, 14, 80, 14);
		descriptionPanel.add(lblDescription);
		textAreaDescription = new TextArea();
		textAreaDescription.setBounds(10, 34, 475, 489);
		descriptionPanel.add(textAreaDescription);

		JButton btnLogout = new JButton("Logout");
		btnLogout.setBounds(1051, 591, 89, 23);
		mainPanel.add(btnLogout);

		btnLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DisplayController.get().logout();
			}
		});
	}

	public void setDescription(String desc){
		textAreaDescription.setText(desc);
	}
}