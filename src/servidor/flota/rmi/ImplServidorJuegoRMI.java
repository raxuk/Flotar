package servidor.flota.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import comun.flota.rmi.IntCallbackCliente;
import comun.flota.rmi.IntServidorJuegoRMI;
import comun.flota.rmi.IntServidorPartidasRMI;

public class ImplServidorJuegoRMI extends UnicastRemoteObject implements IntServidorJuegoRMI{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2386563524179102652L;

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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean borraPartida(String nombreJugador) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String[] listaPartidas() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean aceptaPartida(String nombreJugador, String nombreRival) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}
	
}