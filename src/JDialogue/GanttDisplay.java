/**
 * 
 */
package JDialogue;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTree;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Scrollbar;

/**
 *
 */
public class GanttDisplay extends JDialog{
	private final JPanel contentPanel = new JPanel();
	
	public GanttDisplay(String theArtWork) {
		getContentPane().setLayout(null);
		
		setModalityType(ModalityType.APPLICATION_MODAL);
	    setTitle("Gantt Chart");
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    setLocationRelativeTo(null);
		setBounds(100, 100, 1000, 593);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(204, 255, 204));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		
		getContentPane().add(contentPanel);
		
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Menlo", Font.PLAIN, 13));
		textArea.setBounds(26, 27, 968, 501);
		contentPanel.add(textArea);
		textArea.setText(theArtWork);
		
	}	
}
