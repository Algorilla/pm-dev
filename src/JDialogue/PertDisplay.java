//package JDialogue;
//
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.FlowLayout;
//import java.awt.Font;
//import java.awt.Dialog.ModalityType;
//import java.awt.List;
//import java.awt.TextArea;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.ArrayList;
//import java.util.Date;
//
//import javax.swing.JButton;
//import javax.swing.JDialog;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JTextField;
//import javax.swing.border.EmptyBorder;
//
//import Analysis.PertNetwork;
//import Analysis.MilestoneNode;
//import Controller.MainController;
//import PModel.Activity;
//import PModel.Member;
//import PModel.MemberActivity;
//import PModel.Project;
//
//import com.toedter.calendar.JDateChooser;
//
//import javax.swing.JComboBox;
//import javax.swing.JList;
//
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//
//import javax.swing.JTextArea;
//
//import java.awt.event.InputMethodListener;
//import java.awt.event.InputMethodEvent;
//
//import javax.swing.event.CaretListener;
//import javax.swing.event.CaretEvent;
//
//import java.awt.event.ItemListener;
//import java.awt.event.ItemEvent;
///**
// * A dialogue used to create new activity of the project
// * @author Administrator
// *
// */
//public class PertDisplay extends JDialog {
//
//	private final JPanel contentPanel = new JPanel();
//	private PertNetwork pertNetwork;
//	private JTextField newTargetDate;
//	private MilestoneNode activeNode;
//	
//	public PertDisplay(PertNetwork pn) {
//		
//		MainController mc = MainController.get();
//		this.pertNetwork = pn;
//		
//		setModalityType(ModalityType.APPLICATION_MODAL);
//	    setTitle("Check Ur Targetz");
//	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//	    setLocationRelativeTo(null);
//		setBounds(100, 100, 477, 593);
//		
//		getContentPane().setLayout(new BorderLayout());
//		contentPanel.setBackground(new Color(204, 255, 204));
//		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
//		getContentPane().add(contentPanel, BorderLayout.CENTER);
//		contentPanel.setLayout(null);
//		
//		JLabel lblSelectTeamMember = new JLabel("Pick A Milestone");
//		lblSelectTeamMember.setBounds(35, 24, 297, 22);
//		contentPanel.add(lblSelectTeamMember);
//		
//		JLabel lblMembers = new JLabel("Milestones");
//		lblMembers.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//			}///WHAT
//		});
//		lblMembers.setBounds(92, 62, 61, 16);
//		contentPanel.add(lblMembers);
//		
//		final JTextArea nodeDesc = new JTextArea();
//		nodeDesc.setBounds(35, 90, 407, 111);
//		contentPanel.add(nodeDesc);
//		
//		final JComboBox nodeComboBox = new JComboBox();
//		nodeComboBox.addItemListener(new ItemListener() {
//			public void itemStateChanged(ItemEvent e) {
//				activeNode = (MilestoneNode) nodeComboBox.getSelectedItem();
//				nodeDesc.setText(activeNode.toStringVerbose());
//				nodeDesc.setLineWrap(true);;
//			}
//		});
//		nodeComboBox.setBounds(189, 58, 193, 27);
//		
//		for(MilestoneNode n : pertNetwork.getGraph().getNodes().keySet()){
//			nodeComboBox.addItem(n);
//		}
//		contentPanel.add(nodeComboBox);
//		
//		
//		JLabel lblTargetDate = new JLabel("Target Date");
//		lblTargetDate.setBounds(108, 299, 234, 16);
//		contentPanel.add(lblTargetDate);
//		
//		
//		final JTextArea newChance = new JTextArea();
//		newChance.setBounds(155, 431, 164, 16);
//		contentPanel.add(newChance);
//		
//		newTargetDate = new JTextField();
//		newTargetDate.addCaretListener(new CaretListener() {
//			public void caretUpdate(CaretEvent e) {
//				
//				double d = Double.parseDouble(newTargetDate.getText());
//				
//				double diff = d - activeNode.getExpectedDate();
//				double diffOverStd = diff/activeNode.getStandardDeviation();
//				double prob = PertNetwork.getNormalDist().cumulativeProbability(diffOverStd);
//				
//				newChance.setText(Double.toString(prob));
//			}
//		});
//
//		newTargetDate.setBounds(170, 327, 134, 28);
//		contentPanel.add(newTargetDate);
//		newTargetDate.setColumns(10);
//				
//
//		{
//			JPanel buttonPane = new JPanel();
//			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
//			getContentPane().add(buttonPane, BorderLayout.SOUTH);
//			{
//				JButton okButton = new JButton("OK");
//				
//				okButton.addActionListener(new ActionListener() {
//					public void actionPerformed(ActionEvent e) {
//						
//					}
//				});
//				okButton.setActionCommand("OK");
//				buttonPane.add(okButton);
//				getRootPane().setDefaultButton(okButton);
//			}
//		}
//	setVisible(true);
//	}
//}
//
