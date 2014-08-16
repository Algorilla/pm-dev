package JFrames;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

public class InitialJFrameClone extends JFrame
{
	public InitialJFrameClone()
	{
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
	}
}