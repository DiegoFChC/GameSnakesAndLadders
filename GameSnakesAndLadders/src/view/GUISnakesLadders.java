/*
 * Programación Interactiva
 * Autor: Diego Fernando Chaverra Castillo - 1940322
 * Correo: diego.chaverra@correounivalle.edu.co
 * Mini proyecto 4. Juego de Serpientes y escaleras
 */

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;

import audio.Audio;
import controlSnakesAndLadders.ControlSnakesAndLadders;
import modelSnakesAndLadders.Dice;
import modelSnakesAndLadders.Players;

/**
 * The Class GUISnakesLadders.
 * Muesta el tablero y opciones del juego
 */
public class GUISnakesLadders extends JFrame {
	//Atributos
	private JPanel actionPanel;									// Panel que contiene a los jugadore y los botones
	private Players player1,									// Jugador 1
					player2,									// Jugador 2
					player3;									// Jugador 3
	private JButton start,										// Boton para ic¿niciar el juegoo
					roll,										// Boton para lanzar el dado
					exitMenu;									// Boton para terminar el juego y volver al menu
	private GamePanel gamePanel;								// Mesa de juego
	private Dice dice;											// Dado del juego
	private ControlSnakesAndLadders controlSnakesAndLadders;	// Control del juego
	private GUIMenu menu;										// Menu del juego
	private Listener listener;									// Clase controladora de eventos
	private Timer timer;										// Timer
	private Audio audio;										//Sonido del juego
	//Metodos
	/**
	 * Instantiates a new GUI snakes ladders.
	 * Constructor de la clase
	 * Carga los jugadores y las clase necesarias para manejar el juego
	 * @param players the players
	 * @param gamePanel the game panel
	 * @param dice the dice
	 * @param controlSnakesAndLadders the control snakes and ladders
	 * @param menu the menu
	 * @param audio the audio
	 */
	public GUISnakesLadders(List<Players> players, GamePanel gamePanel, Dice dice, ControlSnakesAndLadders controlSnakesAndLadders, GUIMenu menu, Audio audio) {
		//Cargamos los jugadores
		this.player1 = players.get(0);
		this.player2 = players.get(1);
		this.player3 = players.get(2);
		//Iniciamos las variables necesarias
		this.gamePanel = gamePanel;
		this.dice = dice;
		this.controlSnakesAndLadders = controlSnakesAndLadders;
		this.menu = menu;
		this.audio = audio;
		
		initGUI();
		
		this.setTitle("Snakes and Ladders");
		this.setSize(820, 435);
		this.getContentPane().setBackground(Color.decode("#00a6ff"));
		this.setUndecorated(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(false);
		this.setEnabled(false);
		
	}
	
	/**
	 * Inits the GUI.
	 * Inicia los componentes graficos del GUI, al igual que su layout
	 */
	private void initGUI() {
		
		listener = new Listener();
		timer = new Timer(300, listener);
		
		//Creamos y añadimos la mesa de juego
		add(gamePanel, BorderLayout.EAST);
		
		//Jugadores, dado y botones
		actionPanel = new JPanel();
		actionPanel.setLayout(new BorderLayout());
		actionPanel.setOpaque(false);
		JPanel players = new JPanel();
		players.setOpaque(false);
		//Players---------------------------------
		players.add(player1);
		players.add(player2);
		players.add(player3);
		//Dado------------------------------------
		JPanel diceFaces = new JPanel();
		diceFaces.setOpaque(false);
		diceFaces.add(dice);
		//Navigation buttons---------------------
		JPanel navigationButtons = new JPanel();
		navigationButtons.setOpaque(false);
		start = new JButton("Start");
		start.setCursor(new Cursor(HAND_CURSOR));
		roll = new JButton("Roll");
		roll.setCursor(new Cursor(HAND_CURSOR));
		roll.setEnabled(false);
		exitMenu = new JButton("Menu");
		exitMenu.setCursor(new Cursor(HAND_CURSOR));
		start.addActionListener(listener);
		roll.addActionListener(listener);
		exitMenu.addActionListener(listener);
		navigationButtons.add(start);
		navigationButtons.add(roll);
		navigationButtons.add(exitMenu);
		//---------------------------------------
		actionPanel.add(players, BorderLayout.NORTH);
		actionPanel.add(diceFaces, BorderLayout.CENTER);
		actionPanel.add(navigationButtons, BorderLayout.SOUTH);
		
		add(actionPanel, BorderLayout.EAST);
		
		ImageIcon image = new ImageIcon("src/images/Background1.jpg");
		JLabel background = new JLabel(image);
		
		add(background, BorderLayout.LINE_START);
		
	}
	
	/**
	 * Sets the window.
	 * Desaparce y desactiva esta ventana
	 */
	public void setWindow() {
		this.setEnabled(false);
		this.setVisible(false);
	}
	
	/**
	 * Activate player.
	 * Activa y desactiva los jugadores
	 */
	public void activatePlayer() {
		if (gamePanel.getPlayer() == 1) {
			player1.setEnabled(true);
			player2.setEnabled(false);
			player3.setEnabled(false);
		}
		if (gamePanel.getPlayer() == 2) {
			player1.setEnabled(false);
			player2.setEnabled(true);
			player3.setEnabled(false);
		}
		if (gamePanel.getPlayer() == 3) {
			player1.setEnabled(false);
			player2.setEnabled(false);
			player3.setEnabled(true);
		}
		
	}
	
	/**
	 * Reset values.
	 * Reinicia los valores de esta ventana
	 */
	public void resetValues() {
		start.setEnabled(true);
		roll.setEnabled(false);
		timer.stop();
		controlSnakesAndLadders.resetGame();
		controlSnakesAndLadders.closeGUISnakesLadders();
	}
	
	/**
	 * The Class Escucha.
	 * Clase privada de escucha, monitorea los eventos en la ventana.
	 */
	private class Listener implements ActionListener {

		/**
		 * Action performed.
		 * Monitorea los eventos
		 * @param eventAction the event action
		 */
		public void actionPerformed(ActionEvent eventAction) {
			//Si el timer esta iniciado
			if (timer.isRunning() && !start.isEnabled()) {
				//Si se esta ejecutando la clase del panel de juego (Si alguien se esta moviento)
				if (gamePanel.isInAction() ) {
					roll.setEnabled(false);
					activatePlayer();
					exitMenu.setEnabled(false);
				}
				else {
					roll.setEnabled(true);
					exitMenu.setEnabled(true);
					player1.setEnabled(false);
					player2.setEnabled(false);
					player3.setEnabled(false);
				}
				//Desactivamos los botones de roll y exitMenu, cuando los jugadores se enten moviendo, para evitar choques en los thread
				if (gamePanel.getPlayer() == 1 || gamePanel.getPlayer() == 2) {
					roll.setEnabled(false);
					exitMenu.setEnabled(false);
				}
				if (player1.isEnabled() || player2.isEnabled() || player3.isEnabled()) {
					roll.setEnabled(false);
					exitMenu.setEnabled(false);
				}
			}
			//Inicia el juego
			if (eventAction.getSource() == start) {
				audio.playSound(1);
				start.setEnabled(false);
				roll.setEnabled(true);
				timer.start();
			}
			//Inicia los threads y lanza el dado
			if (eventAction.getSource() == roll) {
				audio.playSound(1);
				controlSnakesAndLadders.iniciarJugadoresSimulados();
			}
			//Se devuelve a menu y termina la partida actual
			if (eventAction.getSource() == exitMenu) {
				audio.playSound(1);
				resetValues();
				controlSnakesAndLadders.activateWindowMenu();
			}
			
		}
		
	}
	
}
