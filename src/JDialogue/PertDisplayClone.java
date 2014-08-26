package JDialogue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Dialog.ModalityType;
import java.awt.List;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Analysis.PertNetwork;
import Analysis.MilestoneNode;
import Controller.MainController;
import PModel.Activity;
import PModel.Member;
import PModel.MemberActivity;
import PModel.Project;

import com.toedter.calendar.JDateChooser;

import javax.swing.JComboBox;
import javax.swing.JList;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextArea;

import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;

import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JEditorPane;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
/**
 * A dialogue used to create new activity of the project
 * @author Administrator
 *
 */
public class PertDisplayClone extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private PertNetwork pertNetwork;
	private MilestoneNode activeNode;
	private Date chosenDate = new Date();
	private Date target;
	
	public PertDisplayClone(PertNetwork pn) {
		
		MainController mc = MainController.get();
		this.pertNetwork = pn;
		
		setModalityType(ModalityType.APPLICATION_MODAL);
	    setTitle("Check Ur Targetz");
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    setLocationRelativeTo(null);
		setBounds(100, 100, 477, 593);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
			}
		});
		contentPanel.setBackground(new Color(204, 255, 204));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblSelectMilestone = new JLabel("Pick A Milestone");
		lblSelectMilestone.setBounds(35, 24, 297, 22);
		contentPanel.add(lblSelectMilestone);
		
		JLabel lblMilestones = new JLabel("Milestones");
		lblMilestones.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		lblMilestones.setBounds(45, 58, 121, 16);
		contentPanel.add(lblMilestones);
		
		JLabel lblPrecedents = new JLabel("Precedents");
		lblPrecedents.setBounds(45, 133, 101, 16);
		contentPanel.add(lblPrecedents);
		
		final JEditorPane precedentPane = new JEditorPane();
		precedentPane.setBounds(129, 133, 297, 83);
		contentPanel.add(precedentPane);
		
		final JEditorPane dependentPane = new JEditorPane();
		dependentPane.setBounds(129, 269, 297, 83);
		contentPanel.add(dependentPane);
		
		JLabel lblDependents = new JLabel("Dependents");
		lblDependents.setBounds(45, 269, 101, 16);
		contentPanel.add(lblDependents);
		
		final JEditorPane targetPane = new JEditorPane();
		targetPane.setBounds(196, 367, 230, 22);
		contentPanel.add(targetPane);
		
		JLabel lblTargetDate = new JLabel("Projected Target Date");
		lblTargetDate.setBounds(45, 367, 151, 16);
		contentPanel.add(lblTargetDate);
		
		JLabel lblDate = new JLabel("Test Likely Date");
		lblDate.setBounds(45, 429, 139, 16);
		contentPanel.add(lblDate);
		
		JLabel lblLikelihoodOfAchieving = new JLabel("Likelihood");
		lblLikelihoodOfAchieving.setBounds(45, 485, 139, 22);
		contentPanel.add(lblLikelihoodOfAchieving);
		
		final JEditorPane liklelihoodPane = new JEditorPane();
		liklelihoodPane.setBounds(189, 485, 230, 22);
		contentPanel.add(liklelihoodPane);
				
		final JComboBox milestoneComboBox = new JComboBox();
		milestoneComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				activeNode = (MilestoneNode) milestoneComboBox.getSelectedItem();
				target = activeNode.getTargetDate();
				String pres, deps, trgt;
				pres = activeNode.toStringArrows("in");
				deps = activeNode.toStringArrows("out");
				trgt = activeNode.toStringTargetDate();
				
				precedentPane.setText(pres);
				dependentPane.setText(deps);
				targetPane.setText(trgt);
			}
		});
		milestoneComboBox.setBounds(189, 58, 193, 27);
		
		for(MilestoneNode n : pertNetwork.getGraph().getNodes().keySet()){
			milestoneComboBox.addItem(n);
		}
		contentPanel.add(milestoneComboBox);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		
		final JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(157, 429, 160, 20);
		contentPanel.add(dateChooser);
		
		JButton btnSelectDate = new JButton("Select Date");
		btnSelectDate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				chosenDate = dateChooser.getDate();

				double diff = (chosenDate.getTime() - target.getTime()) / (1000.0 * 60 * 60 * 24);
				double diffOverStd = diff/activeNode.getStandardDeviation();
				double prob = PertNetwork.getNormalDist().cumulativeProbability(diffOverStd);
		
				liklelihoodPane.setText(Double.toString(prob));
			}
		});
		btnSelectDate.setBounds(329, 424, 117, 29);
		contentPanel.add(btnSelectDate);
		
	setVisible(true);
	}
}

