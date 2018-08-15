package engine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import graphics.BufferedImageLoader;
import graphics.SpriteSheet;
import graphics.Window;
import objects.Board;
import objects.BoardState;
import objects.Player;
import pieces.*;

import java.awt.Graphics;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = -1178041823728909735L;
	public static final int WIDTH = 720, HEIGHT = WIDTH / 12 * 9;
	public static int FPS;
	private Thread thread;
	private boolean running = false;
	private Handler handler;
	public static BufferedImage spriteSheet;
	public Board board;
	public final int tileWidth = 48;

	/*
	 * Instantiates objects and window
	 */
	public Game() {
		
		BufferedImageLoader loader = new BufferedImageLoader();
		this.addKeyListener(new InputManager());
		handler = new Handler();

		try {
			spriteSheet = loader.loadImage("/pieces.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// AudioPlayer.load();
		// AudioPlayer.getMusic("music").loop();
		board = new Board(WIDTH/2-tileWidth*4,HEIGHT/2-tileWidth*4, ID.BOARD, 48);
		handler.addObject(board);
		setupBoard();
		new Window(WIDTH, HEIGHT, "Chess", this);
	}

	
	/*
	 * Starts a new thread, on which all game is held
	 */
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	/*
	 * Stops the thread;
	 */
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Ads 'ticks' to the game. Gets FPS. Calls tick() and render()
	 */
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			if (running) {
				render();
			}
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				FPS = frames;
				frames = 0;
			}
		}
		stop();
	}

	/*
	 * Ticks the game objects
	 */
	private void tick() {
		handler.tick();
	}

	/*
	 * Renders all game objects, backgrouds, HUD's and etc
	 */
	private void render() {
		BufferStrategy bufferStrategy = this.getBufferStrategy();
		if (bufferStrategy == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bufferStrategy.getDrawGraphics();

		g.setColor(new Color(66,55,21));
		g.fillRect(0, 0, WIDTH, HEIGHT);

		handler.render(g);

		g.dispose();
		bufferStrategy.show();
	}

	private void setupBoard() {
		SpriteSheet spriteSheet = new SpriteSheet(Game.spriteSheet, 32);
		
		/*
		 * Tests
		 */
		//handler.addObject(new Knight(2,4,spriteSheet.grabImage(3, 3, 32, 32), board, this, BoardState.BLACK));
		//handler.addObject(new Rook(2,2,spriteSheet.grabImage(3, 2, 32, 32), board, this, BoardState.BLACK));
		
		/*
		 * Whites
		 */
		//Add 
		
		//Add pawns
		/*for(int i = 0; i < board.getBoardSize(); i++) {
			handler.addObject(new Pawn(1, i, spriteSheet.grabImage(1, 1, 32, 32), board, this, BoardState.WHITE));
		}*/
		
		/*
		 * Blacks
		 */
		/*for(int i = 0; i < board.getBoardSize(); i++) {
			handler.addObject(new Pawn(6, i, spriteSheet.grabImage(3, 1, 32, 32), board, this, BoardState.BLACK));
		}*/
		
		
	}
	public static void main(String args[]) {
		new Game();
	}
}
