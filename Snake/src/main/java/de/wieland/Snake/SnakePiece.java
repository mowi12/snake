package de.wieland.Snake;

public class SnakePiece {
	private int x;
	private int y;
	
	public SnakePiece(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() { return x; }
	public void setX(int x) { this.x = x; }
	public int getY() { return y; }
	public void setY(int y) { this.y = y; }
}
