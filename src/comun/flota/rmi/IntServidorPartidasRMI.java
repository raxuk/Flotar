package comun.flota.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IntServidorPartidasRMI extends Remote {

	/**
	 * Instancia un nuevo objeto de la clase Partida
	 * 
	 * @param nf Numero de filas
	 * @param nc Numero de columnas
	 * @param nb Numero de barcos
	 * 
	 * @throws RemoteException
	 */
	public void nuevaPartida(int nf, int nc, int nb) throws RemoteException;

	/**
	 * Llama a la funcion del mismo nombre del objeto Partida
	 * 
	 * @param nf Numero de filas
	 * @param nc Numero de columnas
	 * 
	 * @return Propiedad de la casilla: Agua(-1), Tocado(-2), Hundido(-3)
	 * @throws RemoteException
	 */
	public int pruebaCasilla(int nf, int nc) throws RemoteException;

	/**
	 * Llama a la función del mismo nombre del objeto Partida
	 * 
	 * @param idBarco Numero identificativo del barco
	 * 
	 * @return Cadena con los datos del barco 
	 * @thows RemoteException
	 */
	public String getBarco(int idBarco) throws RemoteException;

	/**
	 * llama a la función del mismo nombre del objeto Partida y devuelve el
	 * resultado obtenido
	 * 
	 * @return Vector de cadenas, una por barco con la informacion de getBarco
	 * @thows RemoteException
	 */
	public String[] getSolucion() throws RemoteException;

}
