package JDialogue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DatabaseConnect.SQLiteDBConnection;

public class InitialJDialog extends JDialog {

	public JPanel contentPanel = new JPanel();
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
	public InitialJDialog(int x, int y, int width, int height,String title, String name) {
		initialDialog(x,y,width,height,title);
		connectDatabase();
		this.userName = name;
		//okButtonAction();

	}
	public void initialDialog(int x, int y, int width, int height,String title){
		setBounds(x, y, width, height);
        setTitle(title);
        setLocationRelativeTo(null);
        
		setModalityType(ModalityType.APPLICATION_MODAL);
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
       
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		getContentPane().setLayout(null);				
	}
	public void connectDatabase(){
		conn = SQLiteDBConnection.ConnecrDb();		
	}
	public void okButtonAction(){
		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(0, 229, 434, 33);
		JButton okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);	
		
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		OkButtonActionPerformed();
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);		
	}
	public void OkButtonActionPerformed(){		
		
	}
}
