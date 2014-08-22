package JFrames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class TeamMemberViewClone extends InitialJFrameClone {

	public TeamMemberViewClone() {
		setBounds(250, 100, 1170, 650);

		// ======================== main panel ================================
		// contains both activities & description panel

		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(new Color(0, 153, 204));
		mainPanel.setBorder(new TitledBorder(null, "Activities",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		mainPanel.setBounds(0, 0, 1150, 590);
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(null);

		// ======================== activities panel ==============================
		JPanel activitiesPanel = new JPanel();
		activitiesPanel.setPreferredSize(new Dimension(505, 560));
		activitiesPanel.setBackground(Color.WHITE);
		activitiesPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		activitiesPanel.setBounds(10, 30, 650, 550);
		mainPanel.add(activitiesPanel);
		activitiesPanel.setLayout(null);

		JLabel lblWelcome = new JLabel("Activities");
		lblWelcome.setBounds(10, 14, 80, 14);
		activitiesPanel.add(lblWelcome);

		// ====================== activity panel ===============================
		JPanel descriptionPanel = new JPanel();
		descriptionPanel.setBackground(Color.WHITE);
		descriptionPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		descriptionPanel.setBounds(670, 30, 470, 550);
		mainPanel.add(descriptionPanel);
		descriptionPanel.setLayout(null);

		JLabel lblProjectAndActivity = new JLabel("Description");
		lblProjectAndActivity.setBounds(10, 14, 80, 14);
		descriptionPanel.add(lblProjectAndActivity);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 450, 503);
		descriptionPanel.add(scrollPane);

		//scrollPane.setViewportView(activitiesTable);
	}
}
