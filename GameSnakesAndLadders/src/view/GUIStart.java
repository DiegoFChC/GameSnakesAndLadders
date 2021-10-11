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
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import audio.Audio;
import controlSnakesAndLadders.ControlSnakesAndLadders;
import modelSnakesAndLadders.FileIO;

/**
 * The Class GUIStart.
 * Clase que mustra la ventana de inicio del juego
 */
public class GUIStart extends JFrame {
	// Atributos
	private JPanel userData;									// Panel para pedir datos al usuario
	private JLabel askName,										// Pregunta para el usuario (nombre)
				   containerBackground;							// Donde se contendra la imagen de fondo para mostrar
	private JTextField fieldName;								// Campo para ingresar el dato solicitado
	private JButton okInicio,									// Boton de OK en el inicio
					avatar1,									// Boton para que el usuario elija un avatar
					avatar2;									// Boton para que el usuario elija un avatar
	private ImageIcon background;								// Donde se guarda la imagen
	private String nameUser;									// Nombre del usuario
	private Listener listener;									// Clase privada administradora de eventos
	private ControlSnakesAndLadders controlSnakesAndLadders;	// Referencia al controlador del jego
	private GUIMenu menu;										// Menu del juego
	private Audio audio;										// Audio del juego

	// Metodos
	/**
	 * Instantiates a new GUI inicio.
	 * Constructor de la clase.
	 * Establece los parametros por defecto de la ventana e inicia los componentes grficos
	 * @param controlSnakesAndLadders the control snakes and ladders
	 * @param menu the menu
	 * @param audio the audio
	 */
	public GUIStart(ControlSnakesAndLadders controlSnakesAndLadders, GUIMenu menu, Audio audio) {
		//Iniciamos las variables necesarias
		this.controlSnakesAndLadders = controlSnakesAndLadders;
		this.menu = menu;
		this.audio = audio;

		initGUI();
		
		//Configuracion de la ventana
		this.setTitle("Snakes and Ladders");
		this.setSize(820, 435);
		this.getContentPane().setBackground(Color.decode("#00a6ff"));
		this.setUndecorated(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setEnabled(true);

	}

	/**
	 * Inits the GUI.
	 * Inicia los componentes graficos del GUI, al igual que su layout
	 */
	private void initGUI() {
		// Inicializamos las variables necesarias
		JFrame vista = new JFrame();
		listener = new Listener();

		// Cambiamos el tipo de layout del JFrame
		Container contenedor = this.getContentPane();
		contenedor.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		// Panel para pedir datos al usuario
		userData = new JPanel();
		userData.setBackground(new Color(150, 152, 154));
		// Cambiamos el layout del panel
		userData.setLayout(new BoxLayout(userData, BoxLayout.Y_AXIS));
		// Componentes del JPanel
		// Titulo del panel
		askName = new JLabel("     USERNAME     ");
		askName.setAlignmentX(Component.CENTER_ALIGNMENT);
		Font font = new Font(Font.SERIF, Font.BOLD, 15);
		askName.setFont(font);
		askName.setBackground(null);
		askName.setForeground(Color.WHITE);
		askName.setOpaque(true);
		// Area donde se escribe
		fieldName = new JTextField(7);
		fieldName.setAlignmentX(Component.CENTER_ALIGNMENT);
		fieldName.addKeyListener(listener);
		// Boton para identificar que ya estan los datos
		okInicio = new JButton("OK!");
		okInicio.setAlignmentX(Component.CENTER_ALIGNMENT);
		okInicio.addActionListener(listener);
		// Elegir avatar
		JLabel elegirAvatar = new JLabel("SELECT YOUR AVATAR");
		elegirAvatar.setAlignmentX(Component.CENTER_ALIGNMENT);
		Font font2 = new Font(Font.SERIF, Font.BOLD, 10);
		elegirAvatar.setFont(font2);
		elegirAvatar.setBackground(null);
		elegirAvatar.setForeground(Color.WHITE);
		elegirAvatar.setOpaque(true);
		// Avatars
		JPanel avatars = new JPanel();
		ImageIcon icon1 = new ImageIcon(FileIO.readImageFile(this, "/images/Player11.png"));
		avatar1 = new JButton(icon1);
		avatar1.setBorder(null);
		avatar1.setContentAreaFilled(false);
		avatar1.addActionListener(listener);
		avatar1.setToolTipText("Si no eliges un avatar, se pondra el que esta por defecto");
		ImageIcon icon2 = new ImageIcon(FileIO.readImageFile(this, "/images/Player44.png"));
		avatar2 = new JButton(icon2);
		avatar2.setBorder(null);
		avatar2.setContentAreaFilled(false);
		avatar2.addActionListener(listener);
		avatar2.setToolTipText("Si no eliges un avatar, se pondra el que esta por defecto");
		avatars.add(avatar1);
		avatars.add(avatar2);
		// Añadimos los componentes al panel y a la ventana
		userData.add(askName);
		userData.add(Box.createRigidArea(new Dimension(0, 10)));
		userData.add(fieldName);
		userData.add(Box.createRigidArea(new Dimension(0, 10)));
		userData.add(elegirAvatar);
		userData.add(avatars);
		userData.add(Box.createRigidArea(new Dimension(0, 10)));
		userData.add(okInicio);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		add(userData, constraints);

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
	 * Cambiar de ventana.
	 * Desactiva esta ventana y activa la siguiente (Menu de opciones del juego)
	 */
	private void setWindow() {
		// Desactivamos la ventana actual
		this.setVisible(false);
		this.setEnabled(false);
		// Activamos la ventana siguiente
		menu.setVisible(true);
		menu.setEnabled(true);
	}

	/**
	 * The Class Escucha. 
	 * Clase privada de escucha, monitorea los eventos en la ventana.
	 */
	private class Listener extends KeyAdapter implements ActionListener {

		/**
		 * Action performed. 
		 * Monitorea los eventos
		 * @param eventAction the event action
		 */
		public void actionPerformed(ActionEvent eventAction) {
			// Si el usuario confirma su nombre
			if (eventAction.getSource() == okInicio) {
				audio.playSound(1);
				// Si ya digito el nombre de usuario
				if (fieldName.getText().length() != 0) {
					// Guardamos el nombre del usuario
					nameUser = fieldName.getText();
					controlSnakesAndLadders.setNameUser(nameUser);
					// Cambiamos de ventana
					setWindow();
				}
				// En caso de qeue el usuario de ok sin haber dado su nombre de usuario
				else {
					JOptionPane.showMessageDialog(null, "Por favor, digite un nombre de usuario", null,
							JOptionPane.DEFAULT_OPTION);
					okInicio.setFocusable(false);
					fieldName.setFocusable(true);
				}
			}
			//Si el usuario elige el avatar 1
			if (eventAction.getSource() == avatar1) {
				avatar2.setEnabled(false);
				controlSnakesAndLadders.setAvatarPlayer(1);
			}
			//Si el usuario elige el avatar 2
			if (eventAction.getSource() == avatar2) {
				avatar1.setEnabled(false);
				controlSnakesAndLadders.setAvatarPlayer(4);
			}

		}

		/**
		 * Key pressed.
		 * Monitorea los eventos del teclado
		 * @param eventKey the event key
		 */
		public void keyPressed(KeyEvent eventKey) {

			if (eventKey.getKeyCode() == KeyEvent.VK_ENTER) {
				// Si ya digito el nombre de usuario
				if (fieldName.getText().length() != 0) {
					audio.playSound(1);
					// Guardamos el nombre del usuario
					nameUser = fieldName.getText();
					controlSnakesAndLadders.setNameUser(nameUser);
					// Cambiamos de ventana
					setWindow();
				}
				// En caso de qeue el usuario de ok sin haber dado su nombre de usuario
				else {
					JOptionPane.showMessageDialog(null, "Por favor, digite un nombre de usuario", null,
							JOptionPane.DEFAULT_OPTION);
					okInicio.setFocusable(false);
					fieldName.setFocusable(true);
				}
			}

		}

	}
}
