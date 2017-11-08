package comun.flota.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IntCallbackCliente extends Remote {

	public String notifica(String mensaje) throws RemoteException;
}
