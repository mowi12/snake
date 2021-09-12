package de.wieland.Snake;

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
	public static final int WIDTH = 20;
	public static final int HEIGHT = 20;
	public static final int SNAKE_PIECE_SIZE = 25;
	public static final int SNAKE_START_SIZE = 3;
	
	private Snake snake;
	private Direction direction = Direction.NULL;
	private boolean gameOver = false;
	
	private Food food;
	
	private int score = 0;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		food = new Food();
		
		Group root = new Group();
		Canvas canvas = new Canvas(WIDTH * SNAKE_PIECE_SIZE, HEIGHT * SNAKE_PIECE_SIZE);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		root.getChildren().add(canvas);
		
		setupAnimationTimer(gc);
		
		Scene scene = new Scene(root, WIDTH * SNAKE_PIECE_SIZE, HEIGHT * SNAKE_PIECE_SIZE);
		addControls(scene);
		
		snake = new Snake();
		
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
				
				if (now - lastTick > 1000000000 / snake.getSpeed()) {
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
		
		for (int i = snake.getSnakePieces().size() - 1; i >= 1; i--) {
			snake.getSnakePieces().get(i).setX(snake.getSnakePieces().get(i - 1).getX());
			snake.getSnakePieces().get(i).setY(snake.getSnakePieces().get(i - 1).getY());
		}
		
		switch (direction) {
		case UP:
			snake.getSnakePieces().get(0).setY(snake.getSnakePieces().get(0).getY() - 1);
			if (snake.getSnakePieces().get(0).getY() < 0) {
				snake.getSnakePieces().get(0).setY(HEIGHT);
				//gameOver = true;
			}
			break;
		case DOWN:
			snake.getSnakePieces().get(0).setY(snake.getSnakePieces().get(0).getY() + 1);
			if (snake.getSnakePieces().get(0).getY() > HEIGHT) {
				snake.getSnakePieces().get(0).setY(0);
				//gameOver = true;
			}
			break;
		case LEFT:
			snake.getSnakePieces().get(0).setX(snake.getSnakePieces().get(0).getX() - 1);
			if (snake.getSnakePieces().get(0).getX() < 0) {
				snake.getSnakePieces().get(0).setX(WIDTH);
				//gameOver = true;
			}
			break;
		case RIGHT:
			snake.getSnakePieces().get(0).setX(snake.getSnakePieces().get(0).getX() + 1);
			if (snake.getSnakePieces().get(0).getX() > WIDTH) {
				snake.getSnakePieces().get(0).setX(0);
				//gameOver = true;
			}
		default:
			break;
		}
		
		//eat food
		if (food.getX() == snake.getSnakePieces().get(0).getX() &&
			food.getY() == snake.getSnakePieces().get(0).getY()) {
			snake.addSnakePiece();
			food = new Food();
			score++;
			snake.increaseSpeed();
			
		}
		
		//self destroy
		for (int i = 1; i < snake.getSnakePieces().size(); i++) {
			if (direction != Direction.NULL &&
				snake.getSnakePieces().get(0).getX() == snake.getSnakePieces().get(i).getX() &&
				snake.getSnakePieces().get(0).getY() == snake.getSnakePieces().get(i).getY()) {
				gameOver = true;
			}
		}
		
		//background
		gc.setFill(Color.GHOSTWHITE);
		gc.fillRect(0, 0, WIDTH * SNAKE_PIECE_SIZE, HEIGHT * SNAKE_PIECE_SIZE);
		
		//food
		gc.setFill(food.getColor());
		gc.fillOval(food.getX() * SNAKE_PIECE_SIZE, food.getY() * SNAKE_PIECE_SIZE, SNAKE_PIECE_SIZE, SNAKE_PIECE_SIZE);
		
		//snake
		for (int i = 0; i < snake.getSnakePieces().size(); i++) {
			gc.setFill(snake.getSnakePieces().get(i).getColor());
			gc.fillRect(snake.getSnakePieces().get(i).getX() * SNAKE_PIECE_SIZE, snake.getSnakePieces().get(i).getY() * SNAKE_PIECE_SIZE, SNAKE_PIECE_SIZE, SNAKE_PIECE_SIZE);
			gc.setStroke(Color.BLACK);
			gc.fillRect(snake.getSnakePieces().get(i).getX() * SNAKE_PIECE_SIZE, snake.getSnakePieces().get(i).getY() * SNAKE_PIECE_SIZE, SNAKE_PIECE_SIZE, SNAKE_PIECE_SIZE);
		}
		
		//score
		gc.setFill(Color.BLACK);
		gc.setFont(new Font(30));
		gc.fillText("Score: " + score, 10, 30);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
