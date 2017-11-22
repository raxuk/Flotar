package comun.flota.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ImplCallbackCliente extends UnicastRemoteObject implements IntCallbackCliente {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7196600449787817015L;

	public ImplCallbackCliente() throws RemoteException {
		super();
	}

	@Override
	public String notifica(String mensaje) {
		String retunMsj = mensaje;
		System.out.println(retunMsj);
		return retunMsj;
	}

}
