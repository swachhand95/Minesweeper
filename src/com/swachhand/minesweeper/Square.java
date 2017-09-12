package com.swachhand.minesweeper;

public class Square {
	private int value = 0;
	private boolean mine = false;
	private boolean opened = false;
	private boolean flagged = false;
	
	public Square() {}

	public Square(boolean mine) {
		this.mine = mine;
	}
	
	public int getValue() {
		return value; 
	}
	
	public void setValue(int val) {
		value = val >= 0 ? val : 0;
	}

	public boolean isMine() {
		return mine;
	}
	
	public void setMine(boolean val) {
		mine = val;
	}
	
	public boolean isOpened() {
		return opened;
	}
	
	public void setOpened(boolean opened) {
		this.opened = opened;
	}
	
	public boolean isFlagged() {
		return flagged;
	}

	public void setFlagged(boolean flagged) {
		this.flagged = flagged;
	}

	// For debugging purposes
	public String toString() {
		return "[value=" + value + ",mine=" + mine + "]";
	}
}
