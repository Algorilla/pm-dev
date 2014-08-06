///**
// * 
// */
//package JFrames;
//
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseAdapter;
//
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JTable;
//import javax.swing.ListSelectionModel;
//import javax.swing.border.EmptyBorder;
//import javax.swing.border.LineBorder;
//import javax.swing.border.TitledBorder;
//
//import JDialogue.CreateNewActivityDialog;
//import PModel.MainController;
//
///**
// *
// */
//public class ProjectPanel extends JFrame{
//	
//	private JPanel activity_update_panel, panel_projectlist;
//	final   JTable table = new JTable();
//	
//	/**
//	 * project control panel,user can create,delete and update activity of the project.
//	 */
//	public ProjectPanel(){
//        getContentPane().setLayout(null);
//		
//        JPanel panel = new JPanel();
//        panel.setBackground(new Color(0, 153, 204));
//        panel.setBorder(new TitledBorder(null, "Project", TitledBorder.LEADING, TitledBorder.TOP, null, null));
//        panel.setBounds(10, 11, 961, 519);
//        getContentPane().add(panel);
//        panel.setLayout(null);
//        
//		activity_update_panel = new JPanel();
//		activity_update_panel.setBackground(Color.WHITE);
//		activity_update_panel.setBorder(new LineBorder(new Color(0, 0, 0)));
//        activity_update_panel.setBounds(10, 30, 305, 425);
//        panel.add(activity_update_panel);
//        
//        panel_projectlist = new JPanel();
//        panel_projectlist.setBackground(Color.WHITE);
//        panel_projectlist.setBorder(new LineBorder(new Color(0, 0, 0)));
//        panel_projectlist.setBounds(325, 30, 615, 425);
//        panel.add(panel_projectlist);
//        panel_projectlist.setLayout(null);
//        
//        JLabel lblProjectAndActivity = new JLabel("Activity List:");
//        lblProjectAndActivity.setBounds(10, 11, 168, 14);
//        panel_projectlist.add(lblProjectAndActivity);
//        
//        JScrollPane scrollPane = new JScrollPane();
//
//        scrollPane.setBounds(10, 36, 595, 337);
//        panel_projectlist.add(scrollPane);
//        
//        scrollPane.setViewportView(table);
//        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        table.setBorder(null);
//        MainController.get().getActivityList(table);
//        
//        JButton btnCreateNewActivity = new JButton("Create New Activity");
//        btnCreateNewActivity.setBackground(new Color(0, 153, 102));
//        btnCreateNewActivity.setBounds(84, 384, 164, 26);
//        panel_projectlist.add(btnCreateNewActivity);
//        
//	        JButton btnDeleteActivity = new JButton("Delete Activity");
//	        btnDeleteActivity.setBackground(new Color(0, 153, 102));
//	        btnDeleteActivity.setBounds(344, 386, 164, 23);
//	        panel_projectlist.add(btnDeleteActivity);
//	        btnDeleteActivity.addActionListener(new ActionListener() {
//	        	public void actionPerformed(ActionEvent e) {
//	        		int row = table.getSelectedRow();
//	        		int PID = (int) table.getModel().getValueAt(row, 0);
//	        		int number = (int) table.getModel().getValueAt(row, 1);
//	        		 MainController.get().DeleteActivity(PID,number);
//		        		resetFrame();
//		        		//projectPanel();
//		        		addImage();
//		        	    validate();
//		        	    repaint();
//	        	}
//	        });
//        
//	        btnCreateNewActivity.addActionListener(new ActionListener() {
//	        	public void actionPerformed(ActionEvent e) {				
//	        		CreateNewActivityDialog newActivity = new CreateNewActivityDialog();
//	        		newActivity.setVisible(true);	
//	        		resetFrame();
//	        		projectPanel();
//	        	    validate();
//	        	    repaint();
//	        	}
//	        });
//        table.addMouseListener(new MouseAdapter() {
//        	@Override
//        	public void mouseClicked(MouseEvent e) {
//        		int row = table.getSelectedRow();
//        		int PID =  Integer.parseInt(table.getModel().getValueAt(row, 0).toString());
//        		int number = Integer.parseInt(table.getModel().getValueAt(row, 1).toString());
//        		activity = MainController.get().GetActivityFromID(PID, number);
//        		String name = table.getModel().getValueAt(row, 2).toString();
//        		textField_ActivityName.setText(name);        		
//        		String des = table.getModel().getValueAt(row, 3).toString();
//        		textArea_description.setText(des);      		
//        	}
//        });
////        headingInfo();
////		updateActivity();
//
//	}
//	
//	 /**
//	  * reset frame if you want to refresh the main frame
//	  */
//		public void resetFrame(){
//			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//			setBounds(100, 100, 1000, 600);
//	        setTitle("");
//	        setLocationRelativeTo(null);
//	        
//	        JPanel contentPane = new JPanel();
//	        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//	        contentPane.setLayout(new BorderLayout(0, 0));
//			setContentPane(contentPane);		
//			getContentPane().setBackground(Color.WHITE);					
//		}
//}
