package JFrames;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import java.awt.BorderLayout;

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
		
		// ======================== project panel ==============================
				JPanel projectPanel = new JPanel();
				projectPanel.setPreferredSize(new Dimension(505, 560));
				projectPanel.setBackground(Color.WHITE);
				projectPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
				projectPanel.setBounds(10, 30, 650, 550);
				mainPanel.add(projectPanel);
				projectPanel.setLayout(null);
				
				JLabel lblWelcome = new JLabel("Activities");
				lblWelcome.setBounds(10, 14, 80, 14);
				projectPanel.add(lblWelcome);
	}

}
