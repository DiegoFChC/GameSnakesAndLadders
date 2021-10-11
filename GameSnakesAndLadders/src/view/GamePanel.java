/*
 * Programación Interactiva
 * Autor: Diego Fernando Chaverra Castillo - 1940322
 * Correo: diego.chaverra@correounivalle.edu.co
 * Mini proyecto 4. Juego de Serpientes y escaleras
 */

package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import audio.Audio;
import controlSnakesAndLadders.ControlSnakesAndLadders;
import modelSnakesAndLadders.FileIO;
import modelSnakesAndLadders.Players;

/**
 * The Class GamePanel.
 * Tablero del juego
 * Es un thread
 */
public class GamePanel extends JPanel implements Runnable {
	//Atributos
	private static final String IMAGE_SNAKES_LADDERS = "/images/SnakesAndLadders.jpg";		//Direccion de la imagen del tablero
	private static final String IMAGE_PIECE_1 = "/images/Player1.png";						//Direccion de la imagen del primer jugador
	private static final String IMAGE_PIECE_2 = "/images/Player2.png";						//Direccion de la imagen del segundo jugador
	private static final String IMAGE_PIECE_3 = "/images/Player3.png";						//Direccion de la imagen del tercer jugador
	private static final String IMAGE_PIECE_4 = "/images/Player4.png";						//Direccion de la imagen del cuarto jugador
	private static final String IMAGE_PIECE_5 = "/images/Player5.png";						//Direccion de la imagen del quinto jugador
	private ImageIcon image1,										//Para almacenar el tablero de juego
					  image2,										//Para almacenar el primer jugador
					  image3,										//Para almacenar el segundo jugador
					  image4,										//Para almacenar el tercer jugador
					  image5,										//Para almacenar el cuarto jugador
					  image6;										//Para almacenar el quinto jugador
	private Image imageTableGame,									//Tablero de juego
				  piece1,											//Jugador 1
				  piece2,											//Jugador 2
				  piece3,											//Jugador 3
				  piece4,											//Jugador 4
				  piece5;											//Jugador 5
	private int xPlayer1,											//Posicion x del jugador 1
				yPlayer1,											//Posicion y del jugador 1
				xPlayer2,											//Posicion x del jugador 2
				yPlayer2,											//Posicion y del jugador 2
				xPlayer3,											//Posicion x del jugador 3
				yPlayer3,											//Posicion y del jugador 3
				numberDice,											//Numero del dado
				player,												//Id del jugador que esta actualmente usando la clase
				numbersWinners;										//Numero de ganadores (Solo puede ser 1 o 2)
	private boolean run,											//Permite la ejecusion del movimiento de los jugadores
					inAction,										//Saber si el metodo run de este thread esta en funcionamiento
					thereWinner;									//Alguien ha ganado o no
	private int winner;												//Si hay ganador, aqui se guarda el numero del ganador
	private Thread thread;											//Hilo de la clase
	private ControlSnakesAndLadders controlSnakesAndLadders;		//Contro del juego
	private Audio audio;											//Audio del juego
	private Players actualPlayer;									//Jugador que esta actualmente usando la clase
	//Metodos
	/**
	 * Instantiates a new game panel.
	 * COnstructor de la clase
	 * @param controlSnakesAndLadders the control snakes and ladders
	 * @param audio the audio
	 */
	public GamePanel(ControlSnakesAndLadders controlSnakesAndLadders, Audio audio) {
		//Iniciamos las variables necesarias
		this.setSize(new Dimension(420,420));
		this.setOpaque(false);
		thread = new Thread(this);
		this.controlSnakesAndLadders = controlSnakesAndLadders;
		this.audio = audio;
		//Cargamos las imagenes necesarias
		image1 = new ImageIcon(FileIO.readImageFile(this, IMAGE_SNAKES_LADDERS));
		imageTableGame = image1.getImage();
		image2 = new ImageIcon(FileIO.readImageFile(this, IMAGE_PIECE_1));
		piece1 = image2.getImage();
		image3 = new ImageIcon(FileIO.readImageFile(this, IMAGE_PIECE_2));
		piece2 = image3.getImage();
		image4 = new ImageIcon(FileIO.readImageFile(this, IMAGE_PIECE_3));
		piece3 = image4.getImage();
		image5 = new ImageIcon(FileIO.readImageFile(this, IMAGE_PIECE_4));
		piece4 = image5.getImage();
		image6 = new ImageIcon(FileIO.readImageFile(this, IMAGE_PIECE_5));
		piece5 = image6.getImage();
		run = false;
		inAction = false;
		thereWinner = false;
		winner = 0;
		numbersWinners = 0;
		//Coordenadas iniciales de los jugadores, deben coincidir con las del constructor de cada jugador
		xPlayer1 = 20;
		yPlayer1 = 380;
		xPlayer2 = 20;
		yPlayer2 = 380;
		xPlayer3 = 20;
		yPlayer3 = 380;
	}

	/**
	 * Paint component. Pinta o dibuja
	 * Pinta o dibuja
	 * @param g the g
	 */
	public void paintComponent(Graphics g) {
		// Metodo padre
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		
		//Table of game
		g2D.drawImage(imageTableGame, 20, 20, this);
		
		//Piece1
		//La imagen del jugador 1 (Usuario) depende del del avatar que eligio
		//Si eligio el avatar 4
		if (controlSnakesAndLadders.getAvatarUser() == 4) {
			g2D.drawImage(piece4, xPlayer1, yPlayer1, 40, 40, this);
		}
		//Si eligio el avatar 1
		else if (controlSnakesAndLadders.getAvatarUser() == 1) {
			g2D.drawImage(piece1, xPlayer1, yPlayer1, 40, 40, this);
		}
		//Si no eligio avatar
		else {
			g2D.drawImage(piece5, xPlayer1, yPlayer1, 40, 40, this);
		}
		//Piece2
		g2D.drawImage(piece2, xPlayer2, yPlayer2, 40, 40, this);
		//Piece1
		g2D.drawImage(piece3, xPlayer3, yPlayer3, 40, 40, this);

	}
	
	/**
	 * Move piece.
	 * Identifica el jugador que va a moverse y sus datos
	 * Llama a la funcion que inicia esta clase que es un thread
	 * @param numberDice the number dice
	 * @param player the player
	 * @param actualPlayer the actual player
	 */
	public void movePiece(int numberDice, int player, Players actualPlayer) {
		this.actualPlayer = actualPlayer;
		this.numberDice = numberDice;
		this.player = player;
		startThread();
	}

	/**
	 * Start thread.
	 * Inicia este thread
	 */
	public void startThread() {
		stopThread();
		run = true;
		thread = new Thread(this);
		thread.start();
	}
	
	/**
	 * Stop thread.
	 * Para este thread
	 */
	public void stopThread() {
		run = false;
		if(thread != null) {
			thread.interrupt();
			thread = null;
		}
	}
	
	/**
	 * Run.
	 * Metodo que ejecuta el thread
	 * Ejecuta los movimientos del jugador
	 */
	public void run() {
		inAction = true;
		//Identificamos el numero que saco el jugador en el dado
		int x = numberDice;
		//El jugador se juvera mientras no halla un ganador, y tenga movimientos
		while (run && x > 0 && !thereWinner) {
			try {
				//Cambiamos las coordenadas del jugador segun su dado
				move();
				//Sonido de salto
				audio.playSound(2);
				x--;
				//Repintamos para cambiar el jugador de lugar
				repaint();
				//Dormimos el thread un momento, para simular saltos
				Thread.sleep(800);
				//Si ya ha ganado y le faltan saltos, no salta mas
				if (actualPlayer.isWinner()) {
					x = 0;
					//Mostramos al ganador
					controlSnakesAndLadders.checkWinner(actualPlayer);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//Reiniciamos la imagen del dado cada vez que halla tirado y movido un jugador
		controlSnakesAndLadders.changeDice();
		//Paramos el hilo
		stopThread();
		//Verificamos si el jugador puede usar una escalera
		useLadder();
		//Verificamos si el jugador puede caer por una serpiente
		fallSnake();
		inAction = false;
	}
	
	/**
	 * Move.
	 * Cambia las coordenadas del jugador que esta utilizando la clase, segun el numero que saco en el dado
	 * Mueve al jugador
	 */
	public void move() {
		//Si no hay un ganador puede moverse
		if (!thereWinner) {
			int xPlayer = actualPlayer.getxPosition();
			int yPlayer = actualPlayer.getyPosition();
			if (actualPlayer.getDirectionMove().equals("Right")) {
				if (xPlayer >= 380) {
					xPlayer = 380;
					yPlayer -= 40;
					actualPlayer.setDirectionMove("Left");
					actualPlayer.setxPosition(xPlayer);
					actualPlayer.setyPosition(yPlayer);
				}
				else {
					xPlayer += 40;
					actualPlayer.setxPosition(xPlayer);
				}
			}
			else {
				if (xPlayer <= 20) {
					xPlayer = 20;
					yPlayer -= 40;
					actualPlayer.setDirectionMove("Right");
					actualPlayer.setxPosition(xPlayer);
					actualPlayer.setyPosition(yPlayer);
				}
				else {
					xPlayer -= 40;
					actualPlayer.setxPosition(xPlayer);
				}
			}
			if (player == 1) {
				xPlayer1 = xPlayer;
				yPlayer1 = yPlayer;
			}
			if (player == 2) {
				xPlayer2 = xPlayer;
				yPlayer2 = yPlayer;
			}
			if (player == 3) {
				xPlayer3 = xPlayer;
				yPlayer3 = yPlayer;
			}
		}
		//Verificamos si el jugador quedo en la ultima casilla y gano o no
		win();
	}
	
	
	/**
	 * Use leadder.
	 * Si el jugador puede usar una escalera, es movido hasta donde lo sube la escalera
	 */
	public void useLadder() {
		int xPlayer = actualPlayer.getxPosition();
		int yPlayer = actualPlayer.getyPosition();
		//Escalera 1
		if (xPlayer == 220 && yPlayer == 380) {
			xPlayer = 220;
			yPlayer = 340;
			actualPlayer.setDirectionMove("Left");
			actualPlayer.setxPosition(xPlayer);
			actualPlayer.setyPosition(yPlayer);
		}
		//Escalera 2
		if (xPlayer == 380 && yPlayer == 340) {
			xPlayer = 380;
			yPlayer = 260;
			actualPlayer.setDirectionMove("Left");
			actualPlayer.setxPosition(xPlayer);
			actualPlayer.setyPosition(yPlayer);
		}
		//Escalera 3
		if (xPlayer == 100 && yPlayer == 260) {
			xPlayer = 100;
			yPlayer = 140;
			actualPlayer.setDirectionMove("Right");
			actualPlayer.setxPosition(xPlayer);
			actualPlayer.setyPosition(yPlayer);
		}
		//Escalera 4
		if (xPlayer == 380 && yPlayer == 180) {
			xPlayer = 380;
			yPlayer = 140;
			actualPlayer.setDirectionMove("Right");
			actualPlayer.setxPosition(xPlayer);
			actualPlayer.setyPosition(yPlayer);
		}
		//Escalera 5
		if (xPlayer == 300 && yPlayer == 220) {
			xPlayer = 300;
			yPlayer = 20;
			actualPlayer.setDirectionMove("Left");
			actualPlayer.setxPosition(xPlayer);
			actualPlayer.setyPosition(yPlayer);
		}
		//Actualizamos las coordenadas
		if (player == 1) {
			xPlayer1 = xPlayer;
			yPlayer1 = yPlayer;
		}
		if (player == 2) {
			xPlayer2 = xPlayer;
			yPlayer2 = yPlayer;
		}
		if (player == 3) {
			xPlayer3 = xPlayer;
			yPlayer3 = yPlayer;
		}
		//Repintamos
		repaint();
	}
	
	/**
	 * Fall snake.
	 * Si el jugador puede caer por una serpiente, es movido hasta donde debe bajar
	 */
	public void fallSnake() {
		int xPlayer = actualPlayer.getxPosition();
		int yPlayer = actualPlayer.getyPosition();
		//Serpiente 1
		if (xPlayer == 300 && yPlayer == 340) {
			xPlayer = 300;
			yPlayer = 380;
			actualPlayer.setDirectionMove("Right");
			actualPlayer.setxPosition(xPlayer);
			actualPlayer.setyPosition(yPlayer);
		}
		//Serpiente 2
		if (xPlayer == 140 && yPlayer == 260) {
			xPlayer = 100;
			yPlayer = 340;
			actualPlayer.setDirectionMove("Left");
			actualPlayer.setxPosition(xPlayer);
			actualPlayer.setyPosition(yPlayer);
		}
		//Serpiente 3
		if (xPlayer == 340 && yPlayer == 140) {
			xPlayer = 340;
			yPlayer = 300;
			actualPlayer.setDirectionMove("Right");
			actualPlayer.setxPosition(xPlayer);
			actualPlayer.setyPosition(yPlayer);
		}
		//Serpiente 4
		if (xPlayer == 260 && yPlayer == 60) {
			xPlayer = 260;
			yPlayer = 140;
			actualPlayer.setDirectionMove("Right");
			actualPlayer.setxPosition(xPlayer);
			actualPlayer.setyPosition(yPlayer);
		}
		//Serpiente 5
		if (xPlayer == 180 && yPlayer == 20) {
			xPlayer = 20;
			yPlayer = 300;
			actualPlayer.setDirectionMove("Right");
			actualPlayer.setxPosition(xPlayer);
			actualPlayer.setyPosition(yPlayer);
		}
		//Actualizamos las coordenadas
		if (player == 1) {
			xPlayer1 = xPlayer;
			yPlayer1 = yPlayer;
		}
		if (player == 2) {
			xPlayer2 = xPlayer;
			yPlayer2 = yPlayer;
		}
		if (player == 3) {
			xPlayer3 = xPlayer;
			yPlayer3 = yPlayer;
		}
		//Repitamos
		repaint();
	}
	
	/**
	 * Reset values.
	 * Reinicia las variables de la clase
	 */
	public void resetValues() {
		run = false;
		inAction = false;
		thereWinner = false;
		winner = 0;
		numbersWinners = 0;
		numberDice = 0;
		player = 0;
		
		xPlayer1 = 20;
		yPlayer1 = 380;
		xPlayer2 = 20;
		yPlayer2 = 380;
		xPlayer3 = 20;
		yPlayer3 = 380;
		controlSnakesAndLadders.resetPlayers();
	}
	
	/**
	 * Win.
	 * Comprueba si el jugador que esta utilizando la clase ha ganado
	 */
	public void win() {
		int xPlayer = actualPlayer.getxPosition();
		int yPlayer = actualPlayer.getyPosition();
		if (xPlayer < 60 && yPlayer < 60) {
			thereWinner = true;
			actualPlayer.setWinner(true);
			numbersWinners = 1;
			if (player == 1) {
				winner = 1;
			}
			if (player == 2) {
				winner = 2;
			}
			if (player == 3) {
				winner = 3;
			}
		}
	}
	
	/**
	 * Checks if is run.
	 * Retorna si el jugador tiene permiso de moverse
	 * @return true, if is run
	 */
	public boolean isRun() {
		return run;
	}

	/**
	 * Checks if is in action.
	 * Retorna si el thread se esta ejecutando
	 * @return true, if is in action
	 */
	public boolean isInAction() {
		return inAction;
	}

	/**
	 * Gets the player.
	 * Retorna el identificador del jugador que esta actualmente en la clase
	 * @return the player
	 */
	public int getPlayer() {
		return player;
	}

	/**
	 * Checks if is there winner.
	 * Retorna si hay un ganador o no
	 * @return true, if is there winner
	 */
	public boolean isThereWinner() {
		return thereWinner;
	}
	
	/**
	 * Gets the winner.
	 * Si hay un ganador, retorna el numero que lo identifica
	 * @return the winner
	 */
	public int getWinner() {
		return winner;
	}
	
}
