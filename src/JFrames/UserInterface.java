package JFrames;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.JLabel;
import javax.swing.JButton;

import JDialogue.CreateNewProjectDialog;
import JDialogue.OpenProjectListDialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

public class UserInterface extends InitialJFrame {
	
	private JLabel nameLabel;
	private String projectName = "Tab";
	private JPanel panel2 = new JPanel();
	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * Create the frame.
	 */
	public UserInterface(int x, int y, int width, int height,String title, String name) {
		super(x,y,width,height,title,name);	
		addMenuBar();
		welcomeGreeting();
	}

	private void projectTabbedPanel(){       							
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setToolTipText("");
		tabbedPane.setBounds(0, 0, 1000, 600);
		panel2.add(tabbedPane);
		
		JPanel currentProjectPanel = new JPanel();
		
		tabbedPane.add(projectName, currentProjectPanel);		
		String rowData[][] = { { "", "", "" ,"",""}};
		
		Object columnNames[] = { "ProjectName", "Task", "Member","Manager", "Status" };	
		String sql = "select * from Tasks where ProjectManager = ?";
		try{
			pst = conn.prepareStatement(sql);
			//pst.setString(1, projectName);
			pst.setString(1, userName);	
			JOptionPane.showMessageDialog(null,projectName+ userName);	
			rs = pst.executeQuery();
			int i=0;
			while(rs.next()){
				rowData[i][0] = rs.getString("ProjectName");
				rowData[i][0] = rs.getString("Task");
				rowData[i][0] = rs.getString("ProjectMember");
				rowData[i][0] = rs.getString("ProjectManager");
				rowData[i][0] = rs.getString("Status");		
				i++;
			}	
			rs.close();
			pst.close();
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null,ex);	
		}	
		
//		Object rowData[][] = { { "Row1-Column1", "Row1-Column2", "Row1-Column3" },
//		        { "Row2-Column1", "Row2-Column2", "Row2-Column3" } };
				
	    JTable table = new JTable(rowData, columnNames);
	    JScrollPane scrollPane = new JScrollPane(table);
		currentProjectPanel.add(scrollPane, BorderLayout.EAST);		
		
	}
	private void addToolBar(){
		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(0, 0, 984, 23);
		contentPane.add(toolBar);				
	}
	private void welcomeGreeting(){
		JLabel lblWelcome = new JLabel("Welcome");
		lblWelcome.setBounds(10, 34, 58, 14);
		contentPane.add(lblWelcome);
		
		nameLabel = new JLabel("");
		nameLabel.setBounds(78, 34, 46, 14);
		contentPane.add(nameLabel);
		nameLabel.setText(userName);		
	}

	 private void addMenuBar() {
	        JMenuBar menubar = new JMenuBar();
	        ImageIcon iconNew = new ImageIcon("new.png");
	        ImageIcon iconOpen = new ImageIcon("open.png");
	        ImageIcon iconSave = new ImageIcon("save.png");
	        ImageIcon iconExit = new ImageIcon("exit.png");

	        JMenu file = new JMenu("Project");
	        file.setMnemonic(KeyEvent.VK_F);
	        JMenu view = new JMenu("View");
	        view.setMnemonic(KeyEvent.VK_V);
	        
	        JMenu imp = new JMenu("Import");
	        imp.setMnemonic(KeyEvent.VK_M);

	        JMenuItem dbfile = new JMenuItem("Import from database");
	        imp.add(dbfile);

	        JMenuItem fileNew = new JMenuItem("New", iconNew);
	        fileNew.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {    
	        		CreateNewProjectDialog newProject = new CreateNewProjectDialog(userName);
	        		newProject.setVisible(true);		        		
	        	}
	        });
	        fileNew.setMnemonic(KeyEvent.VK_N);

	        JMenuItem fileOpen = new JMenuItem("Open", iconOpen);
	        fileOpen.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		OpenProjectListDialog list = new OpenProjectListDialog(userName);
	        		list.setVisible(true);
	        		String currentProjectName = list.getProjectName();
	        		setProjectName(currentProjectName);
	        		resetFrame();
	        		projectTabbedPanel();
	        	    validate();
	        	    repaint();
	        		//JOptionPane.showMessageDialog(null, "Project "+currentProjectName+" Opened");	
	        	}
	        });
	        fileNew.setMnemonic(KeyEvent.VK_O);

	        JMenuItem fileSave = new JMenuItem("Save", iconSave);
	        fileSave.setMnemonic(KeyEvent.VK_S);

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
	        file.add(fileSave);
	        file.addSeparator();
	        file.add(imp);
	        file.addSeparator();
	        file.add(fileExit);

	        menubar.add(file);
	        menubar.add(view);

	        setJMenuBar(menubar);
	        getContentPane().setLayout(null);
	    }
		public void resetFrame(){
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 1000, 600);
	        setTitle("");
	        setLocationRelativeTo(null);
	        
	        panel2 = new JPanel();
	        panel2.setBorder(new EmptyBorder(5, 5, 5, 5));
	        panel2.setLayout(new BorderLayout(0, 0));
			setContentPane(panel2);		
			getContentPane().setBackground(Color.WHITE);
					
		}
}
