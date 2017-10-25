package comun.flota.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IntServidorPartidasRMI extends Remote {

	/**
	 * dado el numero de filas y columnas del tablero y el numero de
	 * barcos,instancia un nuevo objeto de la clase Partida
	 * 
	 * @throws RemoteException
	 */
	public void nuevaPartida(int nf, int nc, int nb) throws RemoteException;

	/**
	 * dada la fila y columna de una casilla, llama a la funcion del mismo
	 * nombre del objeto Partida y devuelve el resultado obtenido
	 * 
	 * @throws RemoteException
	 */
	public void pruebaCasilla(int nf, int nc) throws RemoteException;

	/**
	 * dado el identificador de un barco, llama a la función del mismo nombre
	 * del objeto Partida y devuelve el resultado obtenido
	 * 
	 * @thows RemoteException
	 */
	public String getBarco(int idBarco) throws RemoteException;

	/**
	 * llama a la función del mismo nombre del objeto Partida y devuelve el
	 * resultado obtenido
	 * 
	 * @thows RemoteException
	 */
	public String[] getSolucion() throws RemoteException;

}
