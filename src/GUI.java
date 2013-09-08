import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GUI extends JFrame {
	private JComponent drawing;
	private JPanel TopPanel;
	static int FrameWidth = 800;
	static int FrameHeight = 800;
	private Graphics g;
	private Main main;
	private JButton quitButton;
	private JButton loadButton;
	private JButton saveButton;
	private JSlider ambientScroll;
	private JSlider intesityScroll;


	public GUI(Main m) {
		this.main = m;
		setupFrame();

	}
	
	public void changeAmb(){
		float value = (float) ambientScroll.getValue()/10;
		Main.ambient.set(value);
	}
	
	public void changeIntensity(){
		float value = (float) intesityScroll.getValue()/10;
		Main.intensity.set(value);
	}
	
	private void setupFrame() {
		quitButton = new JButton("Quit");
		loadButton = new JButton("Load");
		saveButton = new JButton("Save");
		ambientScroll = new JSlider(1, 10, 5);
		JLabel ambientLabel = new JLabel("Ambient");
		JLabel intensityLabel = new JLabel("Intensity");
		intesityScroll = new JSlider(1, 10, 5);
		
		this.setSize(FrameWidth, FrameHeight);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		drawing = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				draw(g);
			}
		};
		ambientScroll.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				changeAmb();
				
			}
		});
		
		intesityScroll.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				changeIntensity();
				
			}
		});
		TopPanel = new JPanel();
		TopPanel.add(quitButton);
		TopPanel.add(loadButton);
		TopPanel.add(saveButton);
		TopPanel.add(ambientLabel);
		TopPanel.add(ambientScroll);
		TopPanel.add(intensityLabel);
		TopPanel.add(intesityScroll);
		
		saveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				main.save();
				
			}
		});
		
		quitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(1);
			}
		});
		loadButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				main.loadData();

			}
		});
		
		
		drawing.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				main.mouseReleased(arg0);
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				main.mousePressed(arg0);
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});
		drawing.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent arg0) {}
			@Override
			public void mouseDragged(MouseEvent arg0) {
				main.mouseDragged(arg0);
			}
		});
		drawing.setSize(FrameWidth, FrameHeight);
		this.add(drawing);
		this.add(TopPanel, BorderLayout.NORTH);

		this.setVisible(true);
	}

	public void draw(Graphics g) {
		this.g = g;
		if (main.dataLoaded) {
			main.getModel().draw(g);
			
		}
		drawing.repaint();
	}
}
