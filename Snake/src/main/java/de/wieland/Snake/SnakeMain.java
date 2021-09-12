package de.wieland.Snake;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Public class SnakeMain.
 * 
 * @author Moritz Wieland
 * @version 1.0
 * @date 12.09.2021
 */
public class SnakeMain extends Application {
	private static final int WIDTH = 20;
	private static final int HEIGHT = 20;
	private static final int SNAKE_PIECE_SIZE = 25;
	
	private Random random = new Random();
	
	private int snakeSpeed = 5;
	private List<SnakePiece> snakePieces = new ArrayList<>();
	private Direction direction = Direction.NULL;
	private boolean gameOver = false;
	
	private int foodX;
	private int foodY;
	private Color foodColor;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		generateFood();
		
		Group root = new Group();
		Canvas canvas = new Canvas(WIDTH * SNAKE_PIECE_SIZE, HEIGHT * SNAKE_PIECE_SIZE);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		root.getChildren().add(canvas);
		
		setupAnimationTimer(gc);
		
		Scene scene = new Scene(root, WIDTH * SNAKE_PIECE_SIZE, HEIGHT * SNAKE_PIECE_SIZE);
		addControls(scene);
		
		snakePieces.add(new SnakePiece(WIDTH / 2, WIDTH / 2));
		snakePieces.add(new SnakePiece(WIDTH / 2, WIDTH / 2));
		snakePieces.add(new SnakePiece(WIDTH / 2, WIDTH / 2));
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Snake");
		primaryStage.show();
	}
	
	private void addControls(Scene scene) {
		scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
			if (key.getCode() == KeyCode.W &&
				direction != Direction.DOWN) {
				direction = Direction.UP;
			}
			if (key.getCode() == KeyCode.S &&
				direction != Direction.UP) {
				direction = Direction.DOWN;
			}
			if (key.getCode() == KeyCode.A &&
				direction != Direction.RIGHT) {
				direction = Direction.LEFT;
			}
			if (key.getCode() == KeyCode.D &&
				direction != Direction.LEFT) {
				direction = Direction.RIGHT;
			}
		});
	}

	private void setupAnimationTimer(GraphicsContext gc) {
		new AnimationTimer() {
			long lastTick = 0;
			
			@Override
			public void handle(long now) {
				if (lastTick == 0) {
					lastTick = now;
					tick(gc);
					return;
				}
				
				if (now - lastTick > 1000000000 / snakeSpeed) {
					lastTick = now;
					tick(gc);
				}
			}
		}.start();
	}
	
	private void tick(GraphicsContext gc) {
		if (gameOver) {
			gc.setFill(Color.RED);
			gc.setFont(new Font(50));
			gc.fillText("GAME OVER", 100, 250);
			return;
		}
		
		for (int i = snakePieces.size() - 1; i >= 1; i--) {
			snakePieces.get(i).setX(snakePieces.get(i - 1).getX());
			snakePieces.get(i).setY(snakePieces.get(i - 1).getY());
		}
		
		switch (direction) {
		case UP:
			snakePieces.get(0).setY(snakePieces.get(0).getY() - 1);
			if (snakePieces.get(0).getY() < 0) {
				snakePieces.get(0).setY(HEIGHT);
				//gameOver = true;
			}
			break;
		case DOWN:
			snakePieces.get(0).setY(snakePieces.get(0).getY() + 1);
			if (snakePieces.get(0).getY() > HEIGHT) {
				snakePieces.get(0).setY(0);
				//gameOver = true;
			}
			break;
		case LEFT:
			snakePieces.get(0).setX(snakePieces.get(0).getX() - 1);
			if (snakePieces.get(0).getX() < 0) {
				snakePieces.get(0).setX(WIDTH);
				//gameOver = true;
			}
			break;
		case RIGHT:
			snakePieces.get(0).setX(snakePieces.get(0).getX() + 1);
			if (snakePieces.get(0).getX() > WIDTH) {
				snakePieces.get(0).setX(0);
				//gameOver = true;
			}
		default:
			break;
		}
		
		//eat food
		if (foodX == snakePieces.get(0).getX() &&
			foodY == snakePieces.get(0).getY()) {
			snakePieces.add(new SnakePiece(-1, -1));
			generateFood();
			
		}
		
		//self destroy
		for (int i = 1; i < snakePieces.size(); i++) {
			if (direction != Direction.NULL &&
				snakePieces.get(0).getX() == snakePieces.get(i).getX() &&
				snakePieces.get(0).getY() == snakePieces.get(i).getY()) {
				gameOver = true;
			}
		}
		
		//background
		gc.setFill(Color.GHOSTWHITE);
		gc.fillRect(0, 0, WIDTH * SNAKE_PIECE_SIZE, HEIGHT * SNAKE_PIECE_SIZE);
		
		//food
		gc.setFill(foodColor);
		gc.fillOval(foodX * SNAKE_PIECE_SIZE, foodY * SNAKE_PIECE_SIZE, SNAKE_PIECE_SIZE, SNAKE_PIECE_SIZE);
		
		//snake
		for (int i = 0; i < snakePieces.size(); i++) {
			gc.setFill(i == 0 ? Color.TOMATO : Color.GRAY);
			gc.fillRect(snakePieces.get(i).getX() * SNAKE_PIECE_SIZE, snakePieces.get(i).getY() * SNAKE_PIECE_SIZE, SNAKE_PIECE_SIZE, SNAKE_PIECE_SIZE);
			gc.setStroke(Color.BLACK);
			gc.fillRect(snakePieces.get(i).getX() * SNAKE_PIECE_SIZE, snakePieces.get(i).getY() * SNAKE_PIECE_SIZE, SNAKE_PIECE_SIZE, SNAKE_PIECE_SIZE);
		}
		
		//score
		gc.setFill(Color.BLACK);
		gc.setFont(new Font(30));
		gc.fillText("Score: " + (snakeSpeed - 6), 10, 30);
	}

	private void generateFood() {		
		loop: while (true) {
			foodX = random.nextInt(WIDTH);
			foodY = random.nextInt(HEIGHT);
			
			for (SnakePiece snakePiece : snakePieces) {
				if ((snakePiece.getX() == foodX) &&
					(snakePiece.getY() == foodY)) {
					continue loop;
				}
			}
			
			foodColor = randomColor();
			snakeSpeed++;
			break;
		}
	}

	private Color randomColor() {
		float r = random.nextFloat();
		float g = random.nextFloat();
		float b = random.nextFloat();
		Color color = new Color(r, g, b, 1);
		
		return color;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
