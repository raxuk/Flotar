package servidor.flota.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map.Entry;

import comun.flota.rmi.IntCallbackCliente;
import comun.flota.rmi.IntServidorJuegoRMI;
import comun.flota.rmi.IntServidorPartidasRMI;

public class ImplServidorJuegoRMI extends UnicastRemoteObject implements IntServidorJuegoRMI{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2386563524179102652L;
	private HashMap<String, IntCallbackCliente> listaClientesPartidas;

	protected ImplServidorJuegoRMI() throws RemoteException {
		super();
	}

	@Override
	public IntServidorPartidasRMI nuevoServidorPartidas() throws RemoteException {
		IntServidorPartidasRMI servidorPartidas = new ImplServidorPartidasRMI();
		return servidorPartidas;		
	}

	@Override
	public boolean proponPartida(String nombreJugador, IntCallbackCliente callbackClientObject) throws RemoteException {
	if(!listaClientesPartidas.containsKey(nombreJugador)){
		listaClientesPartidas.put(nombreJugador, callbackClientObject);
		return true;
	}return false;
	}

	@Override
	public boolean borraPartida(String nombreJugador) throws RemoteException {
		if(listaClientesPartidas.containsKey(nombreJugador)){
			listaClientesPartidas.remove(nombreJugador);
		return true;}
		return false;
	}

	@Override
	public String[] listaPartidas() throws RemoteException {
		String[] listaClientes = new String[listaClientesPartidas.size()];
		for(int i = 0;i<listaClientes.length;i++){
			
		}
		return listaClientes;
	}

	@Override
	public boolean aceptaPartida(String nombreJugador, String nombreRival) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}
	
}