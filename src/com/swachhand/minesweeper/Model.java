package com.swachhand.minesweeper;

import java.util.ArrayList;
import java.util.Random;


public class Model {
	// Dimensions of the board
	private static int width = 8;
	private static int height = 8;
	
	// Number of mines on the board
	private static int numMines = 10;
	
	// Squares store the number of mines surrounding itself
	private Square squares[][];
	
	// ArrayList containing the squares that have mines
	ArrayList<Square> mines;
	
	// Number of available flags
	private int numFlags;
			
	public Model() {
		squares = new Square[height][width];
		for (int i = 0; i < squares.length; ++i)
			for (int j = 0; j < squares[i].length; ++j) 
				squares[i][j] = new Square();
		
		mines = new ArrayList<Square>();
		
		numFlags = numMines;
	}
	
	// Generates mines randomly protecting the square positioned at (x, y) on the board
	public void generateMines(int x, int y) {
		Random mineGenerator = new Random();
		int j;
		int i;
		for (int minesSet = 0; minesSet < numMines; minesSet++) {
			do {
				j = mineGenerator.nextInt(width);
				i = mineGenerator.nextInt(height);
			} while (checkSurroundingMines(j, i) || (x == j && y == i));
			squares[i][j].setMine(true);
			setValuesOfAdjacentSquares(j, i);
			mines.add(squares[i][j]);
		}
	}
	
	private boolean checkSurroundingMines(int x, int y) {
		return false;
		// Placeholder function.
	}
	
	private void setValuesOfAdjacentSquares(int x, int y) {
		for (int y1 = y - 1; y1 < y - 1 + 3; ++y1) {
			for (int x1 = x - 1; x1 < x - 1 + 3; ++x1) {
				if (y1 == y && x1 == x) continue;
				if (y1 < 0 || x1 < 0) continue;
				if (y1 >= height || x1 >= width) continue;
				squares[y1][x1].setValue(squares[y1][x1].getValue() + 1);
			}
		}
	}
	
	public void openSquares(int x, int y) {
		if (x < 0 ||  y < 0 || x >= width || y >= height) return;
		
		if (squares[y][x].isMine()) return;
		
		if (squares[y][x].isOpened() || squares[y][x].isFlagged()) return;
		
		squares[y][x].setOpened(true);
		
		// if (y - 1 < 0 || x - 1 < 0 || x + 1 >= width || y + 1 >= height) return;
		
		if (squares[y][x].getValue() == 0) {
			openSquares(x - 1, y - 1);
			openSquares(x, y - 1);
			openSquares(x + 1, y - 1);
			
			openSquares(x - 1, y);
			openSquares(x + 1, y);
			
			openSquares(x - 1, y + 1);
			openSquares(x, y + 1);
			openSquares(x + 1, y + 1);			
		}
	}
	
	// For debugging purposes
	public void printBoard() {
		for (Square arr[] : squares) {
			for (Square s : arr) 
				System.out.print((s.isMine() ? "M" : s.getValue()) + (s.isOpened() ? "X" : " ") + " ");
			System.out.println();
		}
	}
	
	public boolean checkIfGameWon() {
		for (Square arr[] : squares) {
			for (Square s : arr) {
				if (s.isOpened())
					continue;
				if (!s.isMine())
					return false;
			}
		}
		return true;
	}
	
	public Square[][] getSquares() { return squares; }
	
	public ArrayList<Square> getMines() { return mines; }	
	
	// Getters and setters for the attributes for the board
	public static int getWidth() {	return width; }

	public static int getHeight() { return height; }

	public static int getNumMines() { return numMines; }
	
	public static void setDimensionsAndMines(int height, int width, int numMines) {
		Model.height = height > 0 ? height : 1;
		Model.width = width > 0 ? width : 1;
		Model.numMines = numMines < height * width ? numMines : height * width - 1;
	}
	
	public int getNumFlags() { return numFlags; }
	
	public void setNumFlags(int val) { numFlags = val; }

	public static void main(String args[]) {
		Model m = new Model();
		m.generateMines(4, 4);
		m.printBoard();
		m.openSquares(4, 4);
		System.out.println("done");
		m.printBoard();
	}
}
