package engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import objects.Board;
import objects.Player;
import objects.menu.ButtonExit;
import objects.menu.ButtonGotoMenu;
import objects.menu.ButtonPlay;
import objects.menu.MenuButton;

public class LevelManager {
	private static Levels currentLevel;
	public static Handler handler;
	
	LevelManager(Levels startLevel) {
		handler = new Handler();
		currentLevel = startLevel;
		startLevel();
	}
	
	public static void changeLevel(Levels level) {
		currentLevel = level;
		startLevel();
	}
	
	private static void startLevel() {
		handler.clear();
		switch (currentLevel) {
			case MENU:{
				Font buttonFont = new Font("Bebas", Font.TRUETYPE_FONT, Game.DEFAULT_FONT_SIZE);
				MenuButton buttonPlay = new ButtonPlay(Game.WINDOW_WIDTH/2, Game.WINDOW_HEIGHT/3, "Play!", buttonFont);
				MenuButton buttonExit = new ButtonExit(Game.WINDOW_WIDTH/2, Game.WINDOW_HEIGHT/2, "Quit", buttonFont);
				handler.addObject(buttonPlay);
				handler.addObject(buttonExit);
				break;
			}
			
			case GAME:{
				/*
				 * Objects:
				 */
				Player player0 = new Player(Color.WHITE,true);
				Player player1 = new Player(Color.BLACK,false);
				Board board = new Board(Game.WINDOW_WIDTH/4,Game.WINDOW_HEIGHT/8, 48, player0, player1);
				MenuButton buttonGoBackToMenu = new ButtonGotoMenu(Game.WINDOW_WIDTH/2, Game.WINDOW_HEIGHT-72, "Go back to menu", new Font("Bebas", Font.TRUETYPE_FONT, Game.DEFAULT_FONT_SIZE));
				player0.setBoard(board);
				player1.setBoard(board);
				board.setupBoard();
				handler.addObject(player0);
				handler.addObject(player1);
				handler.addObject(board);
				handler.addObject(buttonGoBackToMenu);
			}
			
			default:
				break;
		}
	}
	
	public void tick() {
		handler.tick();
	}
	
	public void render(Graphics g) {
		handler.render(g);
	}
}