import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class GUI {
	private JFrame WindowFrame;
	private JComponent Drawing;
	private JPanel TopPanel;
	private int FrameWidth = 800;
	private int FrameHeight = 800;
	Graphics g;
	public static void main(String[] arg){
		new GUI();
	}
	public GUI(){
		setupFrame();
	}


	private void setupFrame() {
		JButton quitButton = new JButton("Quit");
		WindowFrame = new JFrame("3D Rendering");
		WindowFrame.setSize(FrameWidth, FrameHeight);
		WindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		WindowFrame.setResizable(true);
		Drawing = new JPanel() {
			protected void paintComponent(Graphics g){
				super.paintComponent(g);
				draw(g);
			}
		};
		TopPanel = new JPanel();
		TopPanel.add(quitButton);
		quitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(1);
			}
		});
		Drawing.setSize(FrameWidth,FrameHeight);
		
		WindowFrame.add(Drawing);
		WindowFrame.add(TopPanel, BorderLayout.NORTH);
		
		WindowFrame.setVisible(true);
	}
	
	private void draw(Graphics g) {
		this.g = g;
		g.setColor(Color.CYAN);
		g.drawRect(10, 10, 100, 100);
	}
}
