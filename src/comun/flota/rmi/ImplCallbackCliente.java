package comun.flota.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ImplCallbackCliente extends UnicastRemoteObject implements IntCallbackCliente {
	
	/**
	 * 
	 */

	public ImplCallbackCliente() throws RemoteException {
		super();
	}

	@Override
	public String notifica(String mensaje) {
		String retunMsj = "Callback recibido: "+mensaje;
		System.out.println(retunMsj);
		return retunMsj;
	}

}
