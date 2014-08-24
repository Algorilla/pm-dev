package JFrames;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

public class UserInterfaceView extends JFrame
{
	public UserInterfaceView()
	{
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}