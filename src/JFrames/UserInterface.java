package JFrames;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.JLabel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import Analysis.Analyzer;
import Analysis.GanttNetwork;
import Analysis.PertNetwork;
import Controller.ErrorController;
import Controller.MainController;
import Controller.SQLiteDBConnection;
import JDialogue.AddTeamMember;
import JDialogue.CreateNewActivityDialog;
import JDialogue.CreateNewProjectDialog;
import JDialogue.DeleteProjectDialog;
import JDialogue.EarnedValueDisplay;
import JDialogue.GanttDisplay;
import JDialogue.OpenProjectListDialog;
import JDialogue.PertDisplay;
import PModel.Activity;
import PModel.ActivityOnNodeNetwork;
import PModel.Member;
import PModel.MemberActivity;
import PModel.Project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JSeparator;

import net.proteanit.sql.DbUtils;

import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;

import java.awt.TextArea;

import javax.swing.JTextField;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.border.TitledBorder;

import com.toedter.calendar.JDateChooser;
/**
 * A class used to manipulate projects and activities. This is the main interface of the software.
 * @author Team B
 *
 */
public class UserInterface extends InitialJFrame {
	
	private JLabel nameLabel;
//	private ProjectPanel projectPanel;
	JPanel activity_update_panel;
    JPanel panel_projectlist;
    private  TextArea textArea_description;
    private JTextField textField_ActivityName, textField_PercentComplete, textField_ActualCost;
    final JTable table_1 = new JTable();
    private Activity activity;
	/**
	 * Create the frame.
	 */
	public UserInterface(int x, int y, int width, int height, String title, String name) {
		super(x,y,width,height,title,name);	
		addMenuBar();
		addImage();
		//projectPanel();
//		projectPanel = new ProjectPanel();
	}

	private void addToolBar(){
		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(0, 0, 984, 23);
		contentPane.add(toolBar);				
	}
	/**
	 * project control panel,user can create,delete and update activity of the project.
	 */
	private void projectPanel(){
        getContentPane().setLayout(null);
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 153, 204));
        panel.setBorder(new TitledBorder(null, "Project", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel.setBounds(10, 11, 1179, 649);
        getContentPane().add(panel);
        panel.setLayout(null);
        
		activity_update_panel = new JPanel();
		activity_update_panel.setBackground(Color.WHITE);
		activity_update_panel.setBorder(new LineBorder(new Color(0, 0, 0)));
        activity_update_panel.setBounds(10, 30, 505, 575);
        panel.add(activity_update_panel);
        
        panel_projectlist = new JPanel();
        panel_projectlist.setBackground(Color.WHITE);
        panel_projectlist.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_projectlist.setBounds(545, 30, 615, 575);
        panel.add(panel_projectlist);
        panel_projectlist.setLayout(null);
        
        JLabel lblProjectAndActivity = new JLabel("Activity List:");
        lblProjectAndActivity.setBounds(10, 11, 168, 14);
        panel_projectlist.add(lblProjectAndActivity);
        
        JScrollPane scrollPane = new JScrollPane();

        scrollPane.setBounds(10, 36, 595, 337);
        panel_projectlist.add(scrollPane);
        
        scrollPane.setViewportView(table_1);
        table_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_1.setBorder(null);
//        MainController.get().getActivitiesListForCurrentProject(table_1);
        
        JButton btnCreateNewActivity = new JButton("Create New Activity");
        btnCreateNewActivity.setBackground(new Color(0, 153, 102));
        btnCreateNewActivity.setBounds(84, 384, 164, 26);
        panel_projectlist.add(btnCreateNewActivity);
        
	        JButton btnDeleteActivity = new JButton("Delete Activity");
	        btnDeleteActivity.setBackground(new Color(0, 153, 102));
	        btnDeleteActivity.setBounds(344, 386, 164, 23);
	        panel_projectlist.add(btnDeleteActivity);
	        btnDeleteActivity.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		int row = table_1.getSelectedRow();
	        		int PID = (int) table_1.getModel().getValueAt(row, 0);
	        		int number = (int) table_1.getModel().getValueAt(row, 1);
	        		 MainController.get().deleteActivity(PID,number);
		        		resetFrame();
		        		//projectPanel();
		        		addImage();
		        	    validate();
		        	    repaint();
	        	}
	        });
        
	        btnCreateNewActivity.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {				
	        		CreateNewActivityDialog newActivity = new CreateNewActivityDialog();
	        		newActivity.setVisible(true);	
	        		resetFrame();
	        		projectPanel();
	        	    validate();
	        	    repaint();
	        	}
	        });
        table_1.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		int row = table_1.getSelectedRow();
        		int PID =  Integer.parseInt(table_1.getModel().getValueAt(row, 0).toString());
        		int number = Integer.parseInt(table_1.getModel().getValueAt(row, 1).toString());
        		activity = MainController.get().getActivityFromID(PID, number);
        		String name = table_1.getModel().getValueAt(row, 2).toString();
        		textField_ActivityName.setText(name);  
//        		textArea_description.s
        		String des = table_1.getModel().getValueAt(row, 3).toString();
        		textArea_description.setText(des); 
//        		textArea_description.lineWrap(true);
        	}
        });
        headingInfo();
		updateActivity();

	}
	/**
	 * display information of the project and user.
	 */
	private void headingInfo(){
		activity_update_panel.setLayout(null);
		JLabel lblWelcome = new JLabel("Welcome");
		lblWelcome.setBounds(10, 11, 84, 26);
		activity_update_panel.add(lblWelcome);
		
		nameLabel = new JLabel("");
		nameLabel.setBounds(73, 13, 110, 23);
		

		activity_update_panel.add(nameLabel);
		nameLabel.setText(MainController.get().getCurrentUser().getName());		
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 69, 285, 2);
		activity_update_panel.add(separator);
		
		JLabel lblYouAreNow = new JLabel("You are now in project: ");
		lblYouAreNow.setBounds(10, 48, 143, 14);
		activity_update_panel.add(lblYouAreNow);
		
		JLabel projectName = new JLabel("");
		projectName.setBounds(163, 47, 81, 14);
		activity_update_panel.add(projectName);
		projectName.setText(MainController.get().getCurrentProject().getName());
						
	}
	/**
	 * a panel used to update activity of project.
	 */
	private void updateActivity(){   
		
//		final 
		
        JLabel lblActivityName = new JLabel("Activity Name: ");
        lblActivityName.setBounds(20, 87, 101, 14);
        activity_update_panel.add(lblActivityName);
        
        textField_ActivityName = new JTextField();
        textField_ActivityName.setBounds(131, 84, 343, 20);
        activity_update_panel.add(textField_ActivityName);
        textField_ActivityName.setColumns(10);
        
        JLabel labelDesription = new JLabel("Description:");
        labelDesription.setBounds(20, 124, 83, 14);
        activity_update_panel.add(labelDesription);
        
        textArea_description = new TextArea();	
        textArea_description.setBounds(131, 121, 343, 154);
        activity_update_panel.add(textArea_description);
        

        
        JLabel lblPercentComplete = new JLabel("Percent Complete: ");
        lblPercentComplete.setBounds(20, 332, 101, 14);
        activity_update_panel.add(lblPercentComplete);
        
        textField_PercentComplete = new JTextField();
        textField_PercentComplete.setBounds(131, 332, 143, 20);
        activity_update_panel.add(textField_PercentComplete);
        textField_PercentComplete.setColumns(10);
        
        JButton btnUpdatePC = new JButton("Update");
        btnUpdatePC.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				double percentComplete = Double.parseDouble(textField_PercentComplete.getText());
				activity.setPercentComplete(percentComplete);
			}
		});
        btnUpdatePC.setBounds(305, 332, 117, 29);
        activity_update_panel.add(btnUpdatePC);
        
        JLabel lblActualCost = new JLabel("Actual Cost: ");
        lblActualCost.setBounds(20, 362, 101, 14);
        activity_update_panel.add(lblActualCost);
        
        textField_ActualCost = new JTextField();
        textField_ActualCost.setBounds(131, 362, 143, 20);
        activity_update_panel.add(textField_ActualCost);
        textField_ActualCost.setColumns(10);
        
        JButton btnUpdateAC = new JButton("Update");
        btnUpdateAC.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				double actualCost = Double.parseDouble(textField_ActualCost.getText());
				activity.setActualCost(actualCost);
			}
		});
        btnUpdateAC.setBounds(305, 362, 117, 29);
        activity_update_panel.add(btnUpdateAC);
		
        JButton btnEVA = new JButton("Earned Value Analysis");
        btnEVA.setBackground(new Color(0, 153, 102));
        btnEVA.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		

        		Project cp = MainController.get().getCurrentProject();
        		
        		Date today = new Date();
        		Date start = cp.getStartDate();

        		int daysSinceStart = daysBetween(today, start);
        		
        		Analyzer a = new Analyzer(MainController.get().getCurrentProject(), Math.abs(daysSinceStart));
        		
        		EarnedValueDisplay evd = new EarnedValueDisplay(cp);//cp);
        		evd.setVisible(true);
        	};
        });
        btnEVA.setIcon(new ImageIcon("./resources/img/icon/plus-icon.png"));
        btnEVA.setBounds(336, 496, 150, 30);
        activity_update_panel.add(btnEVA);
        
        JButton btnPert = new JButton("Pert Analysis");
        btnPert.setBackground(new Color(0, 153, 102));
        btnPert.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		Project cp = MainController.get().getCurrentProject();
        		
        		Date today = new Date();
        		Date start = cp.getStartDate();

        		int daysSinceStart = daysBetween(today, start);
        		
        		Analyzer a = new Analyzer(MainController.get().getCurrentProject(), daysSinceStart);
        		
        		PertNetwork p = a.getPertNetwork();
//        		String art = p.toString();
        		
        		PertDisplay pd = new PertDisplay(p);
        		
        		pd.setVisible(true);
        		
        	};
        });
        btnPert.setIcon(new ImageIcon("./resources/img/icon/plus-icon.png"));
        btnPert.setBounds(174, 496, 150, 30);
        activity_update_panel.add(btnPert);
        
        JButton btnGantt = new JButton("Create Gantt Chart");
        btnGantt.setBackground(new Color(0, 153, 102));
        btnGantt.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		Analyzer a = new Analyzer(MainController.get().getCurrentProject(), 161);
        		String projectName = MainController.get().getCurrentProject().getName();
        		GanttNetwork gn = a.getGanttNetwork();
        		String art  = a.getGanttNetwork().toString();
        		GanttDisplay gantt = new GanttDisplay(projectName, gn);
        		gantt.setVisible(true);
        		gantt.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        	};
        });
        btnGantt.setIcon(new ImageIcon("./resources/img/icon/plus-icon.png"));
        btnGantt.setBounds(12, 496, 150, 30);
        activity_update_panel.add(btnGantt);
        
        JButton btnSave = new JButton("Save");
        btnSave.setBackground(new Color(0, 153, 102));
        btnSave.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
				int pid = MainController.get().getCurrentProject().getProjectID();
				String name = textField_ActivityName.getText();
				String description = textArea_description.getText();
								
				if(name.isEmpty()){
					ErrorController.get().showError("Please enter an activity name");
				}else if(description.isEmpty()){
					ErrorController.get().showError("Please enter an activity description");
				}else{
					activity.setName(name);
					activity.setDescr(description);
	
			        MainController.get().updateActivity(activity);
	        		resetFrame();
	        		projectPanel();
	        	    validate();
	        	    repaint();
				}  		
        		        		
        	}
        });
        btnSave.setIcon(new ImageIcon("./resources/img/icon/save-icon.png"));
        btnSave.setBounds(82, 536, 150, 30);
        activity_update_panel.add(btnSave);  
        
        JButton btnAddMem = new JButton("Add Team Member");
        btnAddMem.setBackground(new Color(0, 153, 102));
        btnAddMem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		AddTeamMember teamWindow = new AddTeamMember(activity);
        		teamWindow.setVisible(true);
        	};
        });
        btnAddMem.setIcon(new ImageIcon("./resources/img/icon/plus-icon.png"));
        btnAddMem.setBounds(275, 536, 150, 30);
        activity_update_panel.add(btnAddMem);
	}
	/**
	 * add image to index page of the software
	 */
     private void addImage(){
	        JLabel lblNewLabel = new JLabel("");
	        lblNewLabel.setIcon(new ImageIcon("./resources/img/524-p.jpg"));
	        lblNewLabel.setBounds(174, 11, 620, 519);
	        contentPane.add(lblNewLabel);   	 
     }
     /**
      * menu bar components.
      */
	 private void addMenuBar() {
	        JMenuBar  menubar  = new JMenuBar();
	        ImageIcon iconNew  = new ImageIcon("new.png");
	        ImageIcon iconOpen = new ImageIcon("open.png");
	        ImageIcon iconSave = new ImageIcon("save.png");
	        ImageIcon iconExit = new ImageIcon("exit.png");

	        JMenu file = new JMenu("Project");
	        file.setMnemonic(KeyEvent.VK_F);
	        JMenu view = new JMenu("View");
	        view.setMnemonic(KeyEvent.VK_V);

	        JMenuItem fileNew = new JMenuItem("New", iconNew);
	        fileNew.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {    
	        		CreateNewProjectDialog newProject = new CreateNewProjectDialog();
	        		newProject.setVisible(true);
	        		resetFrame();
	        		projectPanel();
	        	    validate();
	        	    repaint();
	        	}
	        });
	        fileNew.setMnemonic(KeyEvent.VK_N);

	        JMenuItem fileOpen = new JMenuItem("Open", iconOpen);
	        fileOpen.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		OpenProjectListDialog list = new OpenProjectListDialog();
	        		list.setVisible(true);
	        		resetFrame();
	        		projectPanel();
	        	    validate();
	        	    repaint();
	        		//JOptionPane.showMessageDialog(null, "Project "+currentProjectName+" Opened");	
	        	}
	        });
	        fileNew.setMnemonic(KeyEvent.VK_O);

	        JMenuItem fileDelete = new JMenuItem("Delete", iconSave);
	        fileDelete.setMnemonic(KeyEvent.VK_D);
	        fileDelete.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		DeleteProjectDialog list = new DeleteProjectDialog();
	        		list.setVisible(true);
	        		String test = list.getProjectName();
	        		if (	MainController.get().getCurrentProject() != null &&
	        				list.getProjectName().equals(MainController.get().getCurrentProject().getName()))
	        		{
	        			MainController.get().closeCurrentProject();
		        		resetFrame();
		        		//projectPanel();
		        		//addImage();
		        	    validate();
		        	    repaint();
	        		}
	        		//JOptionPane.showMessageDialog(null, "Project "+currentProjectName+" Deleted");	
	        	}
	        });
	        JMenuItem fileExit = new JMenuItem("Exit", iconExit);
	        fileExit.setMnemonic(KeyEvent.VK_C);
	        fileExit.setToolTipText("Exit application");
	        fileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
	            ActionEvent.CTRL_MASK));

	        fileExit.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent event) {
	                System.exit(0);
	            }
	        });

	        file.add(fileNew);
	        file.add(fileOpen);
	        file.add(fileDelete);
	        file.addSeparator();
	        file.addSeparator();
	        file.add(fileExit);

	        menubar.add(file);
	        menubar.add(view);

	        setJMenuBar(menubar);
	        getContentPane().setLayout(null);
	        
	        Calendar cal = new GregorianCalendar();
	        int month = cal.get(Calendar.MONTH);
	        int year = cal.get(Calendar.YEAR);
	        int day = cal.get(Calendar.DAY_OF_MONTH);
	        
//	        JMenu Time_txt = new JMenu("Time");
//	        menubar.add(Time_txt);
//	        getContentPane().setLayout(null);
//	        int second = cal.get(Calendar.SECOND);
//	        int minute = cal.get(Calendar.MINUTE);
//	        int hour = cal.get(Calendar.HOUR);
//	        Time_txt.setText("Time "+ hour+":"+minute +":" + second);	        
	    }
	 /**
	  * reset frame if you want to refresh the main frame
	  */
		public void resetFrame(){
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 1200, 700);
	        setTitle("");
	        setLocationRelativeTo(null);
	        
	        contentPane = new JPanel();
	        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	        contentPane.setLayout(new BorderLayout(0, 0));
			setContentPane(contentPane);		
			getContentPane().setBackground(Color.WHITE);					
		}
		
		public static int daysBetween(Date d1, Date d2){
            return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }
}
