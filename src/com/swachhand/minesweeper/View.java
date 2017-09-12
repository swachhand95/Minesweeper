package com.swachhand.minesweeper;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class View extends JFrame {
	
	// for timer, reset and remaining flags
	private JPanel topPanel;
		
	private JLabel flagsLabel;
	
	private JButton resetButton;
	
	private JPanel gridPanel;
	
	private JButton squareButtons[][];
	
	public static final String flagsString = "Flags: ";
	
	public View() {
		setLayout(new BorderLayout());
		
		topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		
		flagsLabel = new JLabel(flagsString);
		topPanel.add(flagsLabel);
		
		resetButton = new JButton("Reset");
		topPanel.add(resetButton);
		
		add(topPanel, BorderLayout.NORTH);
		
		gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(Model.getHeight(), Model.getWidth()));
		
		squareButtons = new SquareButton[Model.getHeight()][Model.getWidth()];
		for (int i = 0; i < Model.getHeight(); ++i) {
			for (int j = 0; j < Model.getWidth(); ++j) {
				squareButtons[i][j] = new SquareButton();
				gridPanel.add(squareButtons[i][j]);
			}
		}
		
		add(gridPanel, BorderLayout.CENTER);
		
		pack();
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void gameOver(boolean win) {
		if (win) {
			JOptionPane.showMessageDialog(null, "OMFG!! YOU WON!! We got a real badass over here!");
			System.exit(0);
		}
		JOptionPane.showMessageDialog(null, "Game Over :(");
		System.exit(0);
	}
	
	public JButton[][] getSquareButtons() {	return squareButtons; }

	public String getFlagsLabelString() { return flagsLabel.getText(); }
	
	public JButton getResetButton() { return resetButton; }
	
	public JPanel getGridPanel() { return gridPanel; }
	
	public void setFlagsLabelString(String str) { 
		flagsLabel.setText(str);
	}
	
	public static void main(String args[]) {
		new View();
	}
}
