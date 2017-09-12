package com.swachhand.minesweeper;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;


@SuppressWarnings("serial")
public class SquareButton extends JButton {
	private static final int SIDE_LEN = 50;
	
	public SquareButton() {
		setForeground(Color.BLUE);
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(SIDE_LEN, SIDE_LEN);
	}
}
