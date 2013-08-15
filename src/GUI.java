import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI {
	private JFrame WindowFrame;
	private JComponent Drawing;
	private JPanel TopPanel;
	private int FrameWidth = 800;
	private int FrameHeight = 800;
	private Graphics g;
	private Main main;

	public GUI(Main m) {
		this.main = m;
		setupFrame();

	}

	private void setupFrame() {
		JButton quitButton = new JButton("Quit");
		JButton loadButton = new JButton("Load");
		WindowFrame = new JFrame("3D Rendering");
		WindowFrame.setSize(FrameWidth, FrameHeight);
		WindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		WindowFrame.setResizable(true);
		Drawing = new JPanel() {
			protected void paintComponent(Graphics g) {
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
		TopPanel.add(loadButton);
		loadButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				main.loadData();

			}
		});
		Drawing.setSize(FrameWidth, FrameHeight);
		WindowFrame.add(Drawing);
		WindowFrame.add(TopPanel, BorderLayout.NORTH);

		WindowFrame.setVisible(true);
	}

	public void draw(Graphics g) {
		this.g = g;
		if (main.dataLoaded) {
			for (Polygon p : main.getPolygons()) {
				p.draw(g);
			}
			
		}
		Drawing.repaint();
	}
}
