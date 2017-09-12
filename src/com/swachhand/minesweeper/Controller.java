package com.swachhand.minesweeper;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.UIManager;


public class Controller {
	
	private Model model;
	private View view;
	
	private boolean firstSquareClicked = false;
	
	JButton[][] buttons;
	Square[][] squares;
	
	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
		
		buttons = this.view.getSquareButtons();
		
		for (int i = 0; i < Model.getHeight(); ++i) {
			for (int j = 0; j < Model.getWidth(); ++j) {
				SquareOpenListener l = new SquareOpenListener(j, i);
				buttons[i][j].addActionListener(l);
				buttons[i][j].addMouseListener(l);
			}
		}
		
		squares = this.model.getSquares();
		
		this.view.setFlagsLabelString(View.flagsString + this.model.getNumFlags());
		
		this.view.getResetButton().addActionListener(new ResetGameListener());
	}
	
	public class ResetGameListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			view.dispose();
			
			Model.setDimensionsAndMines(15, 15, 15);
			model = new Model();
			
			
			view = new View();
			
			buttons = view.getSquareButtons();
			firstSquareClicked = false;
			for (int i = 0; i < Model.getHeight(); ++i) {
				for (int j = 0; j < Model.getWidth(); ++j) {
					SquareOpenListener l = new SquareOpenListener(j, i);
					buttons[i][j].addActionListener(l);
					buttons[i][j].addMouseListener(l);
				}
			}
			
			squares = model.getSquares();
			
			view.setFlagsLabelString(View.flagsString + model.getNumFlags());
			
			view.getResetButton().addActionListener(new ResetGameListener());
			
			
		}
		
	}
	
	private void updateSquareButton(int x, int y) {
		if (squares[y][x].isOpened() && !squares[y][x].isMine()) {
			buttons[y][x].setEnabled(false);
		
			int squareVal = squares[y][x].getValue();
			buttons[y][x].setText(squareVal > 0 ? Integer.toString(squareVal) : "");
		}
		else if (squares[y][x].isFlagged()) 
			buttons[y][x].setText("*");
		else {
			buttons[y][x].setEnabled(true);
			buttons[y][x].setText("");
		}
		
		if (squares[y][x].isOpened() && squares[y][x].isMine()) {
			buttons[y][x].setBackground(Color.RED);
		}
	
		buttons[y][x].repaint();
	}
	
	public void updateAllButtons() {
		for (int i = 0; i < Model.getHeight(); ++i) 
			for (int j = 0; j < Model.getWidth(); ++j)
				updateSquareButton(j, i);
	}
	
	public void revealAllMines() {
		for (Square mine : model.getMines()) {
			mine.setOpened(true);
		}
		
	}
	
	public class SquareOpenListener extends MouseAdapter implements ActionListener {
		
		private int x;
		private int y;
		
		public SquareOpenListener(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public void mousePressed(MouseEvent e) {
			// square flagging code goes here
			if (e.getButton() == MouseEvent.BUTTON3) {
				System.out.println("Flag " + x + "," + y);
				
				if (squares[y][x].isOpened()) return;
				
				if (squares[y][x].isFlagged()) 
					model.setNumFlags(model.getNumFlags() + 1);
				else
					model.setNumFlags(model.getNumFlags() - 1);
				
				squares[y][x].setFlagged(!squares[y][x].isFlagged());
				
				updateSquareButton(x, y);
				view.setFlagsLabelString(View.flagsString + model.getNumFlags());
				
				if (firstSquareClicked && model.checkIfGameWon())
					view.gameOver(true);
			}			
		}
		
		public void actionPerformed(ActionEvent e) {
			// square opening code goes here
			System.out.println(x + "," + y);
			
			if (squares[y][x].isFlagged()) return;
			
			if (!firstSquareClicked) {
				model.generateMines(x, y);
				firstSquareClicked = !firstSquareClicked;
			}
			
			model.openSquares(x, y);
			squares[y][x].setOpened(true);
			
			if (squares[y][x].isMine()) {
				// reveal all mines
				revealAllMines();
				// Game over
				updateAllButtons();
				view.gameOver(false);
				
				System.out.println("MINE");
			}
			
			updateAllButtons();
			
			if (firstSquareClicked && model.checkIfGameWon())
				view.gameOver(true);
		}
		
	}
	
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				UIManager.put("Button.disabledText", new Color(40,40,255));
				new Controller(new Model(), new View());				
			}
		});
	}
	
}
