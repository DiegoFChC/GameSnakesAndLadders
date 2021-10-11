/*
 * Programación Interactiva
 * Autor: Diego Fernando Chaverra Castillo - 1940322
 * Correo: diego.chaverra@correounivalle.edu.co
 * Mini proyecto 4. Juego de Serpientes y escaleras
 */

package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import audio.Audio;
import controlSnakesAndLadders.ControlSnakesAndLadders;
import modelSnakesAndLadders.FileIO;
import modelSnakesAndLadders.Players;

/**
 * The Class GUIWinner.
 * Ventana que muestra el jugador ganador
 */
public class GUIWinner extends JFrame {
	
	private JLabel nameWinner,									// Nombre del jugador ganador
				   avatarWinner;								// Avatar del ganador
	private JPanel winner;										// Panel que muestra al ganador
	private Players playerWinner;								// Jugador ganador
	private JButton accept;										// Boton para volver al menu
	private int wait;											// Esperar para evitar errores con los hilos
	private GUIMenu menu;										// Menu del juego
	private JFrame here;										// Referencia a esta ventana
	private Listener listener;									// Clase controladora de eventos
	private ControlSnakesAndLadders controlSnakesAndLadders;	// Control del juego
	private Audio audio;										// Sonidos del juego
	private GUISnakesLadders guiSnakesLadders;					// Ventana principal del juego
	private Timer timer;										// Timer para la espera
	
	/**
	 * Instantiates a new GUI winner.
	 * Constructor de la clase
	 * @param menu the menu
	 * @param winner the winner
	 * @param controlSnakesAndLadders the control snakes and ladders
	 * @param GUISnakesLadders the GUI snakes ladders
	 * @param audio the audio
	 */
	public GUIWinner(GUIMenu menu, Players winner, ControlSnakesAndLadders controlSnakesAndLadders, GUISnakesLadders GUISnakesLadders, Audio audio) {
		//Iniciamos las variables necesarias
		this.audio = audio;
		this.playerWinner = winner;
		this.menu = menu;
		this.controlSnakesAndLadders = controlSnakesAndLadders;
		this.guiSnakesLadders = GUISnakesLadders;
		
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
		//Iniciamos las variables necesarias
		this.here = this;
		this.wait = 0;
		listener = new Listener();
		timer = new Timer(1000, listener);
		
		//Cambiamos el tipo de layout del JFrame
		Container contenedor = this.getContentPane();
		contenedor.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		winner = new JPanel();
		winner.setOpaque(false);
		//Cambiamos el layout del panel
		winner.setLayout(new BoxLayout(winner, BoxLayout.Y_AXIS));
		//Componentes del JPanel
		//Titulo del panel
		Font font = new Font(Font.SERIF, Font.BOLD + Font.ITALIC, 40);
		Font font2 = new Font(Font.SERIF, Font.BOLD + Font.ITALIC, 20);
		JLabel win = new JLabel("WINNER");
		win.setFont(font2);
		win.setForeground(Color.BLUE);
		win.setAlignmentX(Component.CENTER_ALIGNMENT);
		nameWinner = new JLabel(playerWinner.getName());
		nameWinner.setAlignmentX(Component.CENTER_ALIGNMENT);
		nameWinner.setFont(font);
		nameWinner.setForeground(Color.WHITE);
		avatarWinner = new JLabel(playerWinner.getAvatar());
		avatarWinner.setAlignmentX(Component.CENTER_ALIGNMENT);
		accept = new JButton("Accept");
		accept.setAlignmentX(Component.CENTER_ALIGNMENT);
		accept.addActionListener(listener);
		accept.setEnabled(false);
		winner.add(win);
		winner.add(Box.createRigidArea(new Dimension(0,10)));
		winner.add(nameWinner);
		winner.add(Box.createRigidArea(new Dimension(0,10)));
		winner.add(avatarWinner);
		winner.add(Box.createRigidArea(new Dimension(0,10)));
		winner.add(accept);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		add(winner, constraints);
	}
	
	/**
	 * Inits the timer.
	 * inicia el timer de esta clase
	 */
	public void initTimer() {
		timer.start();
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
			wait++;
			//Debemos esperar minimo 6 segundos para que el jugador pueda volver a jugar
			//Esto, para que no se interrumpan los hilos
			if (wait == 6) {
				accept.setEnabled(true);
				timer.stop();
				wait = 0;
			}
			//Volvemos al menu del juego
			if (eventAction.getSource() == accept) {
				here.setEnabled(false);
				here.setVisible(false);
				menu.setEnabled(true);
				menu.setVisible(true);
				guiSnakesLadders.resetValues();
			}
		}
		
	}
	
}
