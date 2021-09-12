package de.wieland.Snake;

/**
 * Public class SnakePiece.
 * 
 * @author Moritz Wieland
 * @version 1.0
 * @date 12.09.2021
 */
public class SnakePiece {
	private int x;
	private int y;
	
	public SnakePiece(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter and Setter methods.
	 */
	public int getX() { return x; }
	public void setX(int x) { this.x = x; }
	public int getY() { return y; }
	public void setY(int y) { this.y = y; }
}
