/*
 * Programación Interactiva
 * Autor: Diego Fernando Chaverra Castillo - 1940322
 * Correo: diego.chaverra@correounivalle.edu.co
 * Mini proyecto 4. Juego de Serpientes y escaleras
 */

package modelSnakesAndLadders;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * The Class FileIO.
 * Clase para leer imagenes
 */
public class FileIO {
	
	/**
	 * Read image file.
	 * Lee imagenes y las guarda en un espacio de memoria
	 * @param requestor the requestor
	 * @param fileName the file name
	 * @return the buffered image
	 */
	public static BufferedImage readImageFile(Object requestor, String fileName) {
		BufferedImage image = null;
		try {
			InputStream input = requestor.getClass().getResourceAsStream(fileName);
			image = ImageIO.read(input);
		}catch(Exception e) {
			String message = "El archivo " + fileName + " No se pudo abrir.";
		    JOptionPane.showMessageDialog(null, message); 
		}
       return image;
	}
}
