package comun.flota.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IntCallbackCliente extends Remote {

	/**
	 * Notifica a un cliente
	 * @param mensaje Mensaje que se envia al cliete como notificacion
	 * @return 
	 * @throws RemoteException
	 */
	public String notifica(String mensaje) throws RemoteException;
}
