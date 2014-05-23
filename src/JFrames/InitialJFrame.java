package JFrames;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DatabaseConnect.SQLiteDBConnection;
import java.awt.Color;

public class InitialJFrame extends JFrame {

	public JPanel contentPane;
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	public String userName = null;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * Create the frame.
	 */
	public InitialJFrame(){
		getContentPane().setBackground(Color.WHITE);}
	public InitialJFrame(int x, int y, int width, int height,String title, String name) {
		initialFrame(x,y,width,height,title);		
		this.userName = name;
		getContentPane().setBackground(Color.WHITE);

	}
	public void initialFrame(int x, int y, int width, int height,String title){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(x, y, width, height);
        setTitle(title);
        setLocationRelativeTo(null);
        
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);		
				
	}
	public void connectDatabase(){
		conn = SQLiteDBConnection.ConnecrDb();		
	}


}
