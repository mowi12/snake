package de.wieland.Snake;

import javafx.scene.paint.Color;

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
	private Color color;
	
	public SnakePiece(int x, int y, int positionInSnake) {
		this.x = x;
		this.y = y;
		this.color = assignColor(positionInSnake);
	}
	
	private Color assignColor(int positionInSnake) {
		if (positionInSnake == 0) {
			return Color.TOMATO;
		} else {
			return Color.GRAY;
		}
	}

	/**
	 * Getter and Setter methods.
	 */
	public int getX() { return x; }
	public void setX(int x) { this.x = x; }
	public int getY() { return y; }
	public void setY(int y) { this.y = y; }
	public Color getColor() { return color; }
}
