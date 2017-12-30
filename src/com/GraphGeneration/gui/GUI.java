package com.GraphGeneration.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class GUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private JLabel JL1 = new JLabel("Generate all Graphs: ");
	private JLabel JL3 = new JLabel("");
	private JPanel ContentPane = new JPanel();
	public GUI(){
		super("Progress");
		ContentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		ContentPane.setLayout(new BoxLayout(ContentPane, BoxLayout.Y_AXIS));
		ContentPane.add(JL1);
		ContentPane.add(JL3);
		JComponent newContentPane = ContentPane;
		newContentPane.setOpaque(true);
		setContentPane(newContentPane);
		pack();
		this.setSize(500,400);
		//Center the JFrame on any size screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void refresh1(final int size, final int percent){
		this.JL1.setText("Generate all Graphs: " + size + " graphs");
		this.JL3.setText(percent + "%");
		repaint();
		pack();
	}
	public void end_Step1(int size){
		this.JL1.setText("<html>Generate all Graphs: <font color='red'>" + size + " graphs</font></html>");
		repaint();
	}
	
	public void refresh_2and3(int size, int percent){
		this.JL3.setText(percent + "%");
		repaint();
		pack();
	}
}
