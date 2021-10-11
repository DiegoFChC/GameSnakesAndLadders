/*
 * Programación Interactiva
 * Autor: Diego Fernando Chaverra Castillo - 1940322
 * Correo: diego.chaverra@correounivalle.edu.co
 * Mini proyecto 4. Juego de Serpientes y escaleras
 */

package controlSnakesAndLadders;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import audio.Audio;
import modelSnakesAndLadders.Dice;
import modelSnakesAndLadders.Players;
import view.GUIInstructions;
import view.GUIMenu;
import view.GUISnakesLadders;
import view.GamePanel;
import view.GUIStart;
import view.GUIWinner;

/**
 * The Class ControlSnakesAndLadders.
 * Clase que controla el juego y sus reglas
 */
public class ControlSnakesAndLadders {

	//Atributos
	private Dice dice;								// Objeto dado, el dado que se usara en todo el juego
	private Players player1,						// Objeto jugador, jugador manejado por el usuario
					player2,						// Objeto jugador, jugador simulado
					player3;						// Objeto jugador, jugador simulado
	private List<Players> players;					// Lista que contiene los tres jugadores
	private int avatarUser = 5;						// Avatar del usuario, comienza con un signo de pregunta, puede ser cambiado
	private GUISnakesLadders guiSnakeLadders;		// Vista del juego completo
	private GamePanel gamePanel;					// Vista del tablero de fichas
	private GUIStart GUIStart;						// Vista del inicio, donde se asigna el nombre y el avatar al usuario
	private GUIMenu menu;							// Vista del menu del juego
	private GUIWinner winner;						// Vista del ganador del juego
	private Audio audio;							// Clase manejadora del audio del juego
	private GUIInstructions instructions;			// Ventana de instrucciones
	
	//Metodos
	/**
	 * Instantiates a new control snakes and ladders.
	 * COnstructor de la clase controladora
	 */
	public ControlSnakesAndLadders() {
		
		//Iniciamos las variables necesarias
		audio = new Audio();
		gamePanel = new GamePanel(this, audio);
		dice = new Dice(gamePanel);
		//Creamos los jugadores
		player1 = new Players("Yo", true, 1, gamePanel, dice); 
		player2 = new Players("Felipe", false, 2, gamePanel, dice);
		player3 = new Players("Laura", false, 3, gamePanel, dice);
		//Losañadimos a la lista
		players = new ArrayList();
		players.add(player1);
		players.add(player2);
		players.add(player3);
		
		guiSnakeLadders = new GUISnakesLadders(players, gamePanel, dice, this, menu, audio);
		instructions = new GUIInstructions(this, audio);
		menu = new GUIMenu(guiSnakeLadders, instructions, audio);
		GUIStart = new GUIStart(this, menu, audio);
	}
	
	/**
	 * Iniciar jugadores simulados.
	 * Ejecuta los thread que son los jugadores
	 */
	public void iniciarJugadoresSimulados() {
		//Inicia el truno 1
		dice.setTurn(1);
		//Iniciamos los hilos 
		ExecutorService ejecutorSubprocesos = Executors.newCachedThreadPool();
		ejecutorSubprocesos.execute(player1);
		ejecutorSubprocesos.execute(player2);
		ejecutorSubprocesos.execute(player3);
		ejecutorSubprocesos.shutdown();
	}
	
	/**
	 * Check winner.
	 * Mustra la ventana del ganador si hay uno
	 * @param player the player -> Jugador ganador
	 */
	public void checkWinner(Players player) {
		//Quitamos la vista del juego
		guiSnakeLadders.setWindow();
		//Creamos y mostramos la vista del ganador
		winner = new GUIWinner(menu, player, this, guiSnakeLadders, audio);
		winner.setEnabled(true);
		winner.setVisible(true);
		winner.initTimer();	
	}
	
	/**
	 * Sets the name user.
	 * Cambiar el nombre del usuario
	 * @param name the new name user
	 */
	public void setNameUser(String name) {
		player1.setName(name);
	}
	
	/**
	 * Reset game.
	 * Reinicia los valores de todo el juego para volver a iniciar
	 */
	public void resetGame() {
		//Reiniciar tablero de juego
		gamePanel.resetValues();
		//Reiniciar dado
		dice.resetDice();
	}
	
	/**
	 * Close GUI snakes ladders.
	 * Cambia la vista del juego a la del menu
	 */
	public void closeGUISnakesLadders() {
		//Desaparece la vista del juego
		guiSnakeLadders.setWindow();
	}
	
	
	/**
	 * Activate window menu.
	 * Activa la ventana de menu
	 */
	public void activateWindowMenu() {
		//Aparece el menu
		menu.setEnabled(true);
		menu.setVisible(true);
	}
	
	/**
	 * Sets the avatar player.
	 * Cambia el avatar del usuario
	 * @param avatar the new avatar player
	 */
	public void setAvatarPlayer(int avatar) {
		player1.setAvatar(avatar);
		avatarUser = avatar;
	}

	/**
	 * Gets the avatar user.
	 * Retorna el numero que identifica al usuario, el cual tambien identifica a su avatar
	 * @return the avatar user
	 */
	public int getAvatarUser() {
		return avatarUser;
	}
	
	/**
	 * Reset players.
	 * Reinicia las coordenadas y tipo de movimiento de los jugadores
	 */
	public void resetPlayers() {
		//Reiniciamos el jugador 1
		player1.setxPosition(20);
		player1.setyPosition(380);
		player1.setDirectionMove("Right");
		player1.setEnabled(false);
		player1.setWinner(false);
		//Reiniciamos el jugador 2
		player2.setxPosition(20);
		player2.setyPosition(380);
		player2.setDirectionMove("Right");
		player2.setEnabled(false);
		player2.setWinner(false);
		//Reiniciamos el jugador 3
		player3.setxPosition(20);
		player3.setyPosition(380);
		player3.setDirectionMove("Right");
		player3.setEnabled(false);
		player3.setWinner(false);
	}
	
	/**
	 * There is winner.
	 *	Retorna si hay o no un ganador del juego
	 * @return true, if successful
	 */
	public boolean thereIsWinner() {
		return gamePanel.isThereWinner();
	}
	
	/**
	 * Change dice.
	 * Cambia el dado por su imagen inicial, la cual es sin tirar
	 */
	public void changeDice() {
		dice.rollAgain();
	}
	
}
