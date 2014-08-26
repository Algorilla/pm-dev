package JFrames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import Controller.DisplayController;
import Controller.ErrorController;

public class ManagerView extends UserInterfaceView {

	// Elements that get updated in project (left-hand) panel
	private JLabel lblName;
	private JLabel projectName;
	private final JTextField textFieldActivityName;
	private TextArea textAreaDescription;
	private final JTextField textFieldPercentComplete;
	private JTextField textFieldActualCost;

	// Elements that get updated in activities (right-hand) panel
	private JTable activitiesTable;

	public ManagerView() {
		setBounds(200, 100, 1170, 650);

		// ==================== menu and options ===============================
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnProjectOptions = new JMenu("Options");
		menuBar.add(mnProjectOptions);

		JMenuItem mntmNew = new JMenuItem("New Project");
		mnProjectOptions.add(mntmNew);

		JMenuItem mntmOpen = new JMenuItem("Open Project");
		mnProjectOptions.add(mntmOpen);

		JMenuItem mntmDelete = new JMenuItem("Delete Project");
		mnProjectOptions.add(mntmDelete);

		JMenuItem mntmLogout = new JMenuItem("Logout");
		mnProjectOptions.add(mntmLogout);

		// menu event handlers
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DisplayController.get().createNewProject(activitiesTable);
			}
		});

		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DisplayController.get().openProject(activitiesTable);
			}
		});

		mntmDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DisplayController.get().deleteProject();
			}
		});

		mntmLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DisplayController.get().logout();
			}
		});

		// ======================== main panel ================================
		// contains both project and activity panel
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(new Color(0, 153, 204));
		mainPanel.setBorder(new TitledBorder(null, "Project",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		mainPanel.setBounds(0, 0, 1150, 590);
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(null);

		// ======================== project panel ==============================
		JPanel projectPanel = new JPanel();
		projectPanel.setPreferredSize(new Dimension(505, 560));
		projectPanel.setBackground(Color.WHITE);
		projectPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		projectPanel.setBounds(10, 30, 505, 550);
		mainPanel.add(projectPanel);
		projectPanel.setLayout(null);

		JLabel lblWelcome = new JLabel("Welcome");
		lblWelcome.setBounds(10, 14, 80, 14);
		projectPanel.add(lblWelcome);

		lblName = new JLabel("");
		lblName.setBounds(131, 14, 364, 14);
		projectPanel.add(lblName);

		JLabel lblCurrentProject = new JLabel("Current Project:");
		lblCurrentProject.setBounds(10, 48, 111, 14);
		projectPanel.add(lblCurrentProject);

		projectName = new JLabel("");
		projectName.setBounds(131, 48, 364, 14);
		projectPanel.add(projectName);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 69, 485, 7);
		projectPanel.add(separator);

		JLabel lblActivityName = new JLabel("Activity Name: ");
		lblActivityName.setBounds(20, 84, 101, 14);
		projectPanel.add(lblActivityName);

		textFieldActivityName = new JTextField();
		textFieldActivityName.setBounds(131, 84, 364, 20);
		textFieldActivityName.setColumns(10);
		projectPanel.add(textFieldActivityName);

		JLabel labelDesription = new JLabel("Description:");
		labelDesription.setBounds(20, 121, 101, 14);
		projectPanel.add(labelDesription);

		textAreaDescription = new TextArea();
		textAreaDescription.setBounds(131, 121, 364, 154);
		projectPanel.add(textAreaDescription);

		JLabel lblPercentComplete = new JLabel("Percentage Complete:");
		lblPercentComplete.setBounds(20, 337, 159, 20);
		projectPanel.add(lblPercentComplete);

		textFieldPercentComplete = new JTextField();
		textFieldPercentComplete.setBounds(190, 335, 143, 20);
		textFieldPercentComplete.setColumns(10);
		projectPanel.add(textFieldPercentComplete);

		JButton btnUpdatePercentComplete = new JButton("Update");
		btnUpdatePercentComplete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnUpdatePercentComplete.setBounds(343, 332, 88, 25);
		projectPanel.add(btnUpdatePercentComplete);

		btnUpdatePercentComplete.addMouseListener(new MouseAdapter() {
			// @Override
			public void mouseClicked(MouseEvent e) {
				Double percentComplete;
				try {
					percentComplete = Double
							.parseDouble(textFieldPercentComplete.getText());
					DisplayController.get().updatePercentComplete(
							percentComplete);
				} catch (NumberFormatException numFormatException) {
					ErrorController.get().showError(
							"Please enter a value between 0.0 and 1.0");
				}
			}
		});

		JLabel lblActualCost = new JLabel("Actual Cost:");
		lblActualCost.setBounds(20, 365, 160, 14);
		projectPanel.add(lblActualCost);

		textFieldActualCost = new JTextField();
		textFieldActualCost.setBounds(190, 363, 143, 20);
		textFieldActualCost.setColumns(10);
		projectPanel.add(textFieldActualCost);

		JButton btnUpdateActualCost = new JButton("Update");
		btnUpdateActualCost.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Double actualCost;
				try {
					actualCost = Double.parseDouble(textFieldActualCost
							.getText());
					DisplayController.get().updateActualCost(actualCost);
				} catch (NumberFormatException numFormatException) {
					ErrorController.get().showError(
							"Please enter a monetary amount");
				}
			}
		});
		btnUpdateActualCost.setBounds(343, 360, 88, 25);
		projectPanel.add(btnUpdateActualCost);

		JButton btnEVA = new JButton("Earned Value Analysis");
		btnEVA.setBackground(new Color(0, 153, 102));
		btnEVA.setIcon(new ImageIcon("./resources/img/icon/plus-icon.png"));
		btnEVA.setBounds(49, 445, 195, 30);
		projectPanel.add(btnEVA);

		JButton btnPert = new JButton("Pert Analysis");
		btnPert.setBackground(new Color(0, 153, 102));
		btnPert.setIcon(new ImageIcon("./resources/img/icon/plus-icon.png"));
		btnPert.setBounds(254, 404, 195, 30);
		projectPanel.add(btnPert);

		JButton btnGantt = new JButton("GANTT Chart");
		btnGantt.setBackground(new Color(0, 153, 102));
		btnGantt.setIcon(new ImageIcon("./resources/img/icon/plus-icon.png"));
		btnGantt.setBounds(49, 404, 195, 30);
		projectPanel.add(btnGantt);

		JButton btnAddMem = new JButton("Add Team Member");
		btnAddMem.setBackground(new Color(0, 153, 102));
		btnAddMem.setIcon(new ImageIcon("./resources/img/icon/plus-icon.png"));
		btnAddMem.setBounds(254, 445, 195, 30);
		projectPanel.add(btnAddMem);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnSave.setBackground(new Color(0, 153, 102));
		btnSave.setBounds(159, 488, 195, 30);
		projectPanel.add(btnSave);

		// register analysis buttons event-handles
		btnGantt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DisplayController.get().createGantt();
			};
		});
		btnPert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DisplayController.get().createPert();
			};
		});

		btnEVA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DisplayController.get().createEVA();
			};
		});

		btnAddMem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DisplayController.get().addTeamMember();
			};
		});

		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = textFieldActivityName.getText();
				String description = textAreaDescription.getText();
				String percent = textFieldPercentComplete.getText();
				String cost = textFieldActualCost.getText();
				if (name.isEmpty()) {
					ErrorController.get()
							.showError("Activity must have a name");
				} else if (description.isEmpty()) {
					ErrorController.get().showError(
							"Activity must have a description");
				} else {
					double percentComplete;
					double actualCost;

					try {
						percentComplete = Double.parseDouble(percent);
						actualCost = Double.parseDouble(cost);
						DisplayController.get().save(name, description,
								percentComplete, actualCost);
					} catch (NumberFormatException error) {
						ErrorController
								.get()
								.showError(
										"Percent Complete and Actual Cost must be numbers");
					}
				}
			};
		});

		// ====================== activity panel ===============================
		JPanel activitiesPanel = new JPanel();
		activitiesPanel.setBackground(Color.WHITE);
		activitiesPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		activitiesPanel.setBounds(525, 30, 615, 550);
		mainPanel.add(activitiesPanel);
		activitiesPanel.setLayout(null);

		JLabel lblProjectAndActivity = new JLabel("Activity List:");
		lblProjectAndActivity.setBounds(10, 14, 80, 14);
		activitiesPanel.add(lblProjectAndActivity);

		activitiesTable = new JTable();
		activitiesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		activitiesTable.setBorder(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 595, 337);
		activitiesPanel.add(scrollPane);

		scrollPane.setViewportView(activitiesTable);

		JButton btnCreateNewActivity = new JButton("Create New Activity");
		btnCreateNewActivity.setBackground(new Color(0, 153, 102));
		btnCreateNewActivity.setBounds(84, 386, 195, 23);
		activitiesPanel.add(btnCreateNewActivity);

		JButton btnDeleteActivity = new JButton("Delete Activity");
		btnDeleteActivity.setBackground(new Color(0, 153, 102));
		btnDeleteActivity.setBounds(289, 386, 195, 23);
		activitiesPanel.add(btnDeleteActivity);

		activitiesTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = activitiesTable.getSelectedRow();
				int PID = Integer.parseInt(activitiesTable.getModel()
						.getValueAt(row, 0).toString());
				int number = Integer.parseInt(activitiesTable.getModel()
						.getValueAt(row, 1).toString());
				DisplayController.get().selectActivity(PID, number, true);
			}
		});

		btnCreateNewActivity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DisplayController.get().createNewActivity();
			}
		});

		btnDeleteActivity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = activitiesTable.getSelectedRow();
				int PID = (int) activitiesTable.getModel().getValueAt(row, 0);
				int number = (int) activitiesTable.getModel()
						.getValueAt(row, 1);
				DisplayController.get().deleteActivity(PID, number);
			}
		});
	}

	public void setUserName(String username) {
		lblName.setText(username);
	}

	public void setProjectName(String name) {
		projectName.setText(name);
	}

	public void setActivityName(String name) {
		textFieldActivityName.setText(name);
	}

	public void setActivityDescription(String desc) {
		textAreaDescription.setText(desc);
	}

	public void setPercentComplete(Double percentComplete) {
		textFieldPercentComplete.setText(percentComplete.toString());
	}

	public void setActualCost(Double actualCost) {
		textFieldActualCost.setText(actualCost.toString());
	}

	public void resetActivity(boolean isCurrentProjectDeleted) {
		if (isCurrentProjectDeleted) {
			textFieldActivityName.setText("");
		} else {
			textFieldActivityName.setText("Please select an activity");
		}
		textAreaDescription.setText("");
		textFieldPercentComplete.setText("");
		textFieldActualCost.setText("");
	}
}