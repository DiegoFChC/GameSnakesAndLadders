/*
 * Programación Interactiva
 * Autor: Diego Fernando Chaverra Castillo - 1940322
 * Correo: diego.chaverra@correounivalle.edu.co
 * Mini proyecto 4. Juego de Serpientes y escaleras
 */

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import audio.Audio;
import controlSnakesAndLadders.ControlSnakesAndLadders;

/**
 * The Class GUIInstructions.
 * Ventana de instrucciones
 */
public class GUIInstructions extends JFrame {
	//Atributos
	private ImageIcon background;								// Imagen de fondo
	private JLabel containerBackground;							// Contenedor del fondo
	private JButton menu;										// Boton oara regresar al menu
	private Listener listener;									// Clase privada administradora de eventos
	private ControlSnakesAndLadders controlSnakesAndLadders;	// Control del juego
	private JFrame here;										// Referencia a esta ventana
	private Audio audio;
	//Metodos
	/**
	 * Instantiates a new GUI instructions.
	 * Constructor de la clase
	 * @param controlSnakesAndLadders the control snakes and ladders
	 * @param audio the audio
	 */
	public GUIInstructions (ControlSnakesAndLadders controlSnakesAndLadders, Audio audio) {
		this.audio = audio;
		this.controlSnakesAndLadders = controlSnakesAndLadders;
		
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
	public void initGUI() {
		//Iniciamos las variables necesarias
		listener = new Listener();
		this.here = this;
		background = new ImageIcon("src/images/instructions.jpg");
		containerBackground = new JLabel(background);
		
		//Panel que contiene el boton para volver al menu
		JPanel button = new JPanel();
		button.setOpaque(false);
		menu = new JButton("Menu");
		menu.addActionListener(listener);
		button.add(menu);
		
		add(containerBackground, BorderLayout.CENTER);
		add(button, BorderLayout.PAGE_END);
		
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
		public void actionPerformed (ActionEvent eventAction) {
			//Si el jugador decide volver al menu
			if (eventAction.getSource() == menu) {
				audio.playSound(1);
				controlSnakesAndLadders.activateWindowMenu();
				here.setEnabled(false);
				here.setVisible(false);
			}
		}
		
	}
	
}
