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
import java.awt.Cursor;
import java.awt.Dimension;
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
import view.GUISnakesLadders;

/**
 * The Class GUIMenu.
 * Menu del juego
 */
public class GUIMenu extends JFrame {
	//Atributos
	private JPanel options;							 // Panel de options del juego
	private JButton newGame, 						 // Boton para iniciar un nuevo juego
					instructions,					 // Boton para continuar una partida del juego
					exit; 							 // Salir del juego
	private ImageIcon background;					 // Guardamos la imagen del juego
	private JLabel containerBackground;				 // Contenedor de la imangen de fondo
	private Listener listener; 						 // Clase controladora de eventos
	private GUISnakesLadders GUISnakesLadders;		 // Vista del juego
	private Audio audio;							 // Sonidos del juego
	private GUIInstructions GUIinstructions;		 // Ventana de instrucciones
	private JFrame here;							 // Referencia a esta ventana

	// Metodos
	/**
	 * Instantiates a new GUI menu.
	 * Constructor de la clase 
	 * Establece los parametros por defecto de la ventana e inicia los componentes grficos
	 * @param GUISnakesLadders the GUI snakes ladders
	 * @param instructions the instructions
	 * @param audio the audio
	 */
	public GUIMenu(GUISnakesLadders GUISnakesLadders, GUIInstructions instructions, Audio audio) {
		//Inciamos las variables necesarias
		this.GUIinstructions = instructions;
		this.GUISnakesLadders = GUISnakesLadders;
		this.audio = audio;

		initGUI();
		
		//Opciones de la ventana
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
		// Inicializamos las variables necesarias
		this.here = this;
		listener = new Listener();

		// Cambiamos el tipo de layout del JFrame
		Container container = this.getContentPane();
		container.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		// Panel contenedor del menu de options
		options = new JPanel();
		// Cambiamos el layout del panel
		options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));
		options.setOpaque(false);
		// Creamos los botones
		newGame = new JButton("New Game");
		newGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		newGame.addActionListener(listener);
		newGame.setCursor(new Cursor(HAND_CURSOR));
		newGame.setFocusable(false);
		newGame.setMaximumSize(new Dimension(150, 60));
		instructions = new JButton("Instructions");
		instructions.setAlignmentX(Component.CENTER_ALIGNMENT);
		instructions.addActionListener(listener);
		instructions.setMaximumSize(new Dimension(150, 60));
		instructions.setCursor(new Cursor(HAND_CURSOR));
		instructions.setFocusable(false);
		exit = new JButton("Exit");
		exit.setAlignmentX(Component.CENTER_ALIGNMENT);
		exit.addActionListener(listener);
		exit.setMaximumSize(new Dimension(150, 60));
		exit.setCursor(new Cursor(HAND_CURSOR));
		exit.setFocusable(false);
		// Añadimos los componentes
		options.add(newGame);
		options.add(Box.createRigidArea(new Dimension(0, 5)));
		options.add(instructions);
		options.add(Box.createRigidArea(new Dimension(0, 5)));
		options.add(exit);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = this.getWidth();
		constraints.gridheight = this.getHeight();
		add(options, constraints);

		// Panel Fondo de pantalla
		background = new ImageIcon("src/images/Background.jpg");
		containerBackground = new JLabel(background);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = this.getWidth();
		constraints.gridheight = this.getHeight();
		add(containerBackground, constraints);
	}

	/**
	 * Sets the window.
	 * Desactiva esta ventana, y activa la del juego
	 */
	private void setWindow() {
		// Desactivamos la ventana actual
		setVisible(false);
		setEnabled(false);
		// Activamos la ventana siguiente
		GUISnakesLadders.setVisible(true);
		GUISnakesLadders.setEnabled(true);
	}

	/**
	 * The Class Listener. 
	 * Clase privada de escucha, monitorea los eventos en la ventana.
	 */
	private class Listener implements ActionListener {

		/**
		 * Action performed.
		 * Monitorea los eventos
		 * @param eventAction the event action
		 */
		public void actionPerformed(ActionEvent eventAction) {
			// Salir del Juego
			if (eventAction.getSource() == exit) {
				audio.playSound(1);
				System.exit(0);
			}
			// Abrimos la ventana de instrucciones del juego
			if (eventAction.getSource() == instructions) {
				audio.playSound(1);
				GUIinstructions.setEnabled(true);
				GUIinstructions.setVisible(true);
				here.setEnabled(false);
				here.setVisible(false);
			}
			// Iniciar una partida nueva
			if (eventAction.getSource() == newGame) {
				audio.playSound(1);
				setWindow();
			}
		}
	}
}
