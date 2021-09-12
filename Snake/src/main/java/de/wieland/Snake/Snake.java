package de.wieland.Snake;

import static de.wieland.Snake.SnakeMain.SNAKE_START_SIZE;
import static de.wieland.Snake.SnakeMain.WIDTH;

import java.util.ArrayList;
import java.util.List;

/**
 * Public class Snake.
 * 
 * @author Moritz Wieland
 * @version 1.0
 * @date 12.09.2021
 */
public class Snake {
	private int speed = 5;
	private List<SnakePiece> snakePieces = new ArrayList<>();
	
	public Snake() {
		for (int i = 0; i < SNAKE_START_SIZE; i++) {
			snakePieces.add(new SnakePiece(WIDTH / 2, WIDTH / 2, snakePieces.size()));
		}
	}

	public void addSnakePiece() {
		snakePieces.add(new SnakePiece(-1, -1, snakePieces.size()));
	}
	
	public void increaseSpeed() {
		this.speed++;
	}

	/**
	 * Getter and Setter methods.
	 */
	public int getSpeed() { return speed; }
	public List<SnakePiece> getSnakePieces() { return snakePieces; }
}
