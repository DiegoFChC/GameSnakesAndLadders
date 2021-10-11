/*
 * Programación Interactiva
 * Autor: Diego Fernando Chaverra Castillo - 1940322
 * Correo: diego.chaverra@correounivalle.edu.co
 * Mini proyecto 4. Juego de Serpientes y escaleras
 */

package controlSnakesAndLadders;

import java.awt.EventQueue;

import javax.swing.UIManager;

/**
 * The Class PrincipalSnakesLadders.
 * Clase principal, contiene el metodo main que inicia todo
 */
public class PrincipalSnakesLadders {
	/**
	 * The main method.
	 * Inicia el juego o la GUI del mismo.
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		try {
			//Esto se hace para que el programa tenga la misma apariencia en todos los sistemas operativos.
			String javaLookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
			UIManager.setLookAndFeel(javaLookAndFeel);
		} catch (Exception e) {
			
		}
		EventQueue.invokeLater(new Runnable() {
			//Iniciamos el control de juego, que a su vez inicia la interfaz
			public void run() {	
				ControlSnakesAndLadders control = new ControlSnakesAndLadders();
			}
		});	
	}
}
