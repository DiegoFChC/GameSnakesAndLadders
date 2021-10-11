/*
 * Programación Interactiva
 * Autor: Diego Fernando Chaverra Castillo - 1940322
 * Correo: diego.chaverra@correounivalle.edu.co
 * Mini proyecto 4. Juego de Serpientes y escaleras
 */

package modelSnakesAndLadders;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import view.GamePanel;

/**
 * The Class Dice.
 * Clase que controla el dado del juego
 * Extiende de JLabel
 */
public class Dice extends JLabel {
	//Atributos
	private ImageIcon dice;										//Imagen del dado
	private int numberDice;										//Numero que saco el dado
	private int turn;											//Turno con el cual se identifica el jugador que puede lanzar el dado
	private GamePanel gamePanel;								//Panel del tablero del juego
	
	private Lock lock = new ReentrantLock();					//Bloqueo de threads
	private Condition waitTurn = lock.newCondition();	//Condicion de bloqueo
	
	//Metodos
	/**
	 * Instantiates a new dice.
	 * Constructoo de la clase
	 * @param gamePanel the game panel
	 */
	public Dice(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		//Asignamos una imagen inicial al dado
		dice = new ImageIcon(FileIO.readImageFile(this,"/images/Dice.png"));
		this.setIcon(dice);
	}
	
	/**
	 * Number random.
	 * Genera un numero random
	 */
	public void numberRandom() {
		Random random = new Random();
		numberDice = random.nextInt(6) + 1;
	}
	
	/**
	 * Roll dice.
	 * Cambia la imagen del dado por la del numero sacado por el jugador
	 */
	public void rollDice() {
		dice = new ImageIcon(FileIO.readImageFile(this,"/images/" + numberDice + ".png"));
		this.setIcon(dice);
	}
	
	/**
	 * Turnos.
	 * Controla las entradas de los jugadores a la clase
	 * Tira el dado y mueve las fichas
	 * @param idPlayer the id player
	 * @param player the player
	 */
	public void turnos(int idPlayer, Players player) {
		lock.lock();
		try {
			//Si entra un jugador al cual no le corresponde jugar, lo duerme
			while(player.getIdPlayer() != turn) {
				waitTurn.await();
			}
			//Si ya hay un ganador
			if (gamePanel.getWinner() != 0) {
				numberDice = 0;
			}
			//Si aun no hay gandores
			else {
				//Si es el segundo o tercer turno, deben esperar antes de tirar, para que el jugador anterior termine de moverse
				if (turn != 1) {
					waitTurn(waitTurn);
				}
				//Generamos el numero del dado de manera aleatoria
				numberRandom();
				//Cambiamos el dado con el numero aleatorio
				rollDice();
				//Movemos la ficha del jugador actual
				gamePanel.movePiece(numberDice, idPlayer, player);
			}
			//Damos paso al siguiente jugador
			turn++;
			//Despertamos los jugadores dormidos
			waitTurn.signalAll();
		}catch(InterruptedException e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}
	
	/**
	 * Wait turn.
	 * Duerme el thread actual, segun el numero que tenga el dado
	 * Debemos dormir el hilo, para que no interrumpa el movimiento del jugador anterior
	 * @param condition the condition
	 */
	private void waitTurn(Condition condition) {
		try {
			//Se usa la funcion de dormir varias veces, ya que el limite de la funcion awaitNanos no acepta numeros mayores a 2100000000
			if (numberDice == 1) {
				condition.awaitNanos(1100000000);
				condition.awaitNanos(1100000000);
			}
			if (numberDice == 2) {
				condition.awaitNanos(2100000000);
				condition.awaitNanos(1100000000);
			}
			if (numberDice == 3) {
				condition.awaitNanos(2000000000);
				condition.awaitNanos(1100000000);
				condition.awaitNanos(1100000000);
			}
			if (numberDice == 4) {
				condition.awaitNanos(2000000000);
				condition.awaitNanos(2100000000);
				condition.awaitNanos(1100000000);
			}
			if (numberDice == 5) {
				condition.awaitNanos(2000000000);
				condition.awaitNanos(2000000000);
				condition.awaitNanos(1100000000);
				condition.awaitNanos(1100000000);
			}
			if (numberDice == 6) {
				condition.awaitNanos(2000000000);
				condition.awaitNanos(2000000000);
				condition.awaitNanos(2100000000);
				condition.awaitNanos(1100000000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Reset dice.
	 * Resetea los valores del dado
	 */
	public void resetDice() {
		dice = new ImageIcon(FileIO.readImageFile(this,"/images/Dice.png"));
		this.setIcon(dice);
		turn = 1;
	}
	
	/**
	 * Reroll.
	 * Cambia la imagen del dado antes de que vuelva a ser usado
	 * Se pone la imagen original
	 */
	public void rollAgain() {
		dice = new ImageIcon(FileIO.readImageFile(this,"/images/Dice.png"));
		this.setIcon(dice);
	}
	
	/**
	 * Gets the number dice.
	 * Retorna el numero actual del dado
	 * @return the number dice
	 */
	public int getNumberDice() {
		return numberDice;
	}

	/**
	 * Sets the number dice.
	 * Cambiar el numero de cara del dado
	 * @param numberDice the new number dice
	 */
	public void setNumberDice(int numberDice) {
		this.numberDice = numberDice;
	}

	/**
	 * Gets the turn.
	 * Retorna el turno actual de jugador
	 * @return the turn
	 */
	public int getTurn() {
		return turn;
	}

	/**
	 * Sets the turn.
	 * Cambia el turno actual
	 * @param turn the new turn
	 */
	public void setTurn(int turn) {
		this.turn = turn;
	}
	
}
