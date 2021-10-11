/*
 * Programación Interactiva
 * Autor: Diego Fernando Chaverra Castillo - 1940322
 * Correo: diego.chaverra@correounivalle.edu.co
 * Mini proyecto 4. Juego de Serpientes y escaleras
 */

package modelSnakesAndLadders;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.Border;

import view.GamePanel;

/**
 * The Class Players.
 * Clase que crea los jugadores
 * Cada jugador es un thread
 */
public class Players extends JLabel implements Runnable {
	//Atributos
	private String name,				//Nombre del jugador
	   			   directionMove;		//Direccion en la que se debe mover el jugador
	private boolean isHuman,			//El jugador es humano o simulado
					winner;				//Este jugador ha ganado o no
	private int turn,					//Turno que le corresponde en el juego
				idPlayer,				//Numero que identifica al jugador
				xPosition,				//Posicion x del jugador en el tablero
				yPosition;				//Posicion y del jugador
	private ImageIcon avatar;			//Imagen del jugador
	private GamePanel gamePanel;		//Tablero de juego
	private Dice dice;					//Dado con que se juega
	
	/**
	 * Instantiates a new players.
	 * Constructor de la clase
	 * @param name the name
	 * @param isHuman the is human
	 * @param idPlayer the id player
	 * @param gamePanel the game panel
	 * @param dice the dice
	 */
	public Players(String name, Boolean isHuman, int idPlayer, GamePanel gamePanel, Dice dice) {
		//Iniciamos la svariables necesarias
		this.name = name;
		this.isHuman = isHuman;
		this.idPlayer = idPlayer;
		//En ese caso el id del jugador sera tambien su turno
		this.turn = idPlayer;
		this.gamePanel = gamePanel;
		this.dice = dice;
		this.xPosition = 20;
		this.yPosition = 380;
		this.directionMove = "Right";
		
		//JLabel del jugador
		//Aspectos graficos del jugador
		//Borde solo para el jugador humano
		Border border = BorderFactory.createLineBorder(Color.WHITE,1);
		if (isHuman) {
			this.setBorder(border); 							//sets border of label (not image+text)
			avatar = new ImageIcon(FileIO.readImageFile(this,"/images/Player5.png"));
		}
		else {
			avatar = new ImageIcon(FileIO.readImageFile(this,"/images/Player" + this.idPlayer + ".png"));
		}
		this.setText(this.name); 							//set text of label
		this.setIcon(avatar);
		this.setHorizontalTextPosition(JLabel.CENTER); 		//set text LEFT,CENTER, RIGHT of imageicon
		this.setVerticalTextPosition(JLabel.TOP); 			//set text TOP,CENTER, BOTTOM of imageicon
		this.setForeground(Color.WHITE); 					//set font color of text
		this.setFont(new Font("MV Boli",Font.PLAIN,20)); 	//set font of text
		this.setIconTextGap(-25); 							//set gap of text to image
		this.setOpaque(false); 								//display background color
		this.setVerticalAlignment(JLabel.CENTER); 			//set vertical position of icon+text within label
		this.setHorizontalAlignment(JLabel.CENTER); 		//set horizontal position of icon+text within label
		this.setEnabled(false);
	}

	/**
	 * Gets the name.
	 * Retorna el nombre del jugador
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * Cambia el nombre del jugador
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
		this.setText(name);
	}

	/**
	 * Checks if is human.
	 * Retorna si el jugador es humano o no
	 * @return true, if is human
	 */
	public boolean isHuman() {
		return isHuman;
	}
	
	/**
	 * Acivate player.
	 * Activa el jugador (JLabel)
	 */
	public void acivatePlayer() {
		this.setEnabled(true);
	}
	
	/**
	 * Desactivate player.
	 * Desactiva el jugador (JLabel)
	 */
	public void desactivatePlayer() {
		this.setEnabled(false);
	}
	
	/**
	 * Run.
	 * Ejecuta el thread (jugador)
	 */
	public void run() {
		dice.turnos(turn, this);
	}
	
	/**
	 * Sets the avatar.
	 * Cambia el avatar del jugador
	 * @param avatar the new avatar
	 */
	public void setAvatar(int avatar) {
		this.avatar = new ImageIcon(FileIO.readImageFile(this,"/images/Player" + avatar + ".png"));
		this.setIcon(this.avatar);
	}
	
	/**
	 * Gets the avatar.
	 * Retorna el avatar del jugador
	 * @return the avatar
	 */
	public ImageIcon getAvatar() {
		return avatar;
	}

	/**
	 * Gets the x position.
	 * retorna la posicion x del jugador
	 * @return the x position
	 */
	public int getxPosition() {
		return xPosition;
	}

	/**
	 * Sets the x position.
	 * Cambia la posicion x del jugador
	 * @param xPosition the new x position
	 */
	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	/**
	 * Gets the y position.
	 * Retorna la posicion y del jugador
	 * @return the y position
	 */
	public int getyPosition() {
		return yPosition;
	}

	/**
	 * Sets the y position.
	 * Cambia la posicion y del jugador
	 * @param yPosition the new y position
	 */
	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}

	/**
	 * Gets the direction move.
	 * Retorna la direccion del jugador
	 * @return the direction move
	 */
	public String getDirectionMove() {
		return directionMove;
	}

	/**
	 * Sets the direction move.
	 * Cambia la direccion del jugador
	 * @param directionMove the new direction move
	 */
	public void setDirectionMove(String directionMove) {
		this.directionMove = directionMove;
	}

	/**
	 * Checks if is winner.
	 * Retorna si el jugador ha ganado o no
	 * @return true, if is winner
	 */
	public boolean isWinner() {
		return winner;
	}

	/**
	 * Sets the winner.
	 * Cambia el estado de victoria del jugador
	 * @param winner the new winner
	 */
	public void setWinner(boolean winner) {
		this.winner = winner;
	}

	/**
	 * Gets the id player.
	 * Retorna el numero identificador del jugador
	 * @return the id player
	 */
	public int getIdPlayer() {
		return idPlayer;
	}
	
}
