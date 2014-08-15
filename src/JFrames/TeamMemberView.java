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

import Controller.MainController;
import Controller.SQLiteDBConnection;
import JDialogue.AddTeamMember;
import JDialogue.CreateNewActivityDialog;
import JDialogue.CreateNewProjectDialog;
import JDialogue.DeleteProjectDialog;
import JDialogue.OpenProjectListDialog;
import PModel.Activity;

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
import javax.swing.BoxLayout;
import javax.swing.JTextArea;
/**
 * A class used to manipulate projects and activities. This is the main interface of the software.
 * @author Team B
 *
 */
public class TeamMemberView extends InitialJFrame {
	
	private JLabel nameLabel;
    JPanel panel_activitylist;
    final JTable table_1 = new JTable();
	
	public TeamMemberView() {
		setBounds(100,100,1000,600);
        getContentPane().setLayout(null);
		
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 153, 204));
        panel.setBorder(new TitledBorder(null, "Activities", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel.setBounds(10, 11, 961, 519);
        getContentPane().add(panel);
        panel.setLayout(null);
        
        panel_activitylist = new JPanel();
        panel_activitylist.setBackground(Color.WHITE);
        panel_activitylist.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_activitylist.setBounds(10, 30, 615, 425);
        panel.add(panel_activitylist);
        panel_activitylist.setLayout(null);
        
        JLabel lblProjectAndActivity = new JLabel("Activity List:");
        lblProjectAndActivity.setBounds(10, 11, 168, 14);
        panel_activitylist.add(lblProjectAndActivity);
        
        JScrollPane scrollPane = new JScrollPane();

        scrollPane.setBounds(10, 36, 595, 337);
        panel_activitylist.add(scrollPane);
        
        scrollPane.setViewportView(table_1);
        table_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_1.setBorder(null);

        MainController.get().getActivityListForCurrentTeamMember(table_1);
        
        final TextArea textArea_description = new TextArea();
        textArea_description.setBounds(637, 68, 300, 332);
        panel.add(textArea_description);
        
        JLabel lblDescription = new JLabel("Description");
        lblDescription.setBackground(Color.WHITE);
        lblDescription.setBounds(637, 32, 93, 16);
        panel.add(lblDescription);;
        
        table_1.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		Activity activity;
        		
        		int row = table_1.getSelectedRow();
        		int PID =  Integer.parseInt(table_1.getModel().getValueAt(row, 0).toString());
        		int number = Integer.parseInt(table_1.getModel().getValueAt(row, 1).toString());
        		
        		activity = MainController.get().getActivityFromID(PID, number);
        		
        		textArea_description.setText(activity.getDescr());
        	}
        });
        
	}
}


