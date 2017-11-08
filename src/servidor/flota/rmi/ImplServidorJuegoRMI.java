package servidor.flota.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map.Entry;

import comun.flota.rmi.IntCallbackCliente;
import comun.flota.rmi.IntServidorJuegoRMI;
import comun.flota.rmi.IntServidorPartidasRMI;

public class ImplServidorJuegoRMI extends UnicastRemoteObject implements IntServidorJuegoRMI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3773717936590029767L;

	// Nombre jugador y su callback
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
		if (!listaClientesPartidas.containsKey(nombreJugador)) {
			listaClientesPartidas.put(nombreJugador, callbackClientObject);
			String mensaje = "Partida propuesta con éxito.";
			callbackClientObject.notifica(mensaje);
			return true;
		}
		return false;
	}

	@Override
	public boolean borraPartida(String nombreJugador) throws RemoteException {
		if (listaClientesPartidas.containsKey(nombreJugador)) {
			String mensaje = "Partida borrada con éxito.";
			listaClientesPartidas.get(nombreJugador).notifica(mensaje);
			listaClientesPartidas.remove(nombreJugador);
			return true;
		}
		return false;
	}

	@Override
	public String[] listaPartidas() throws RemoteException {
		if(listaClientesPartidas.isEmpty())
			return null;
		String[] listaClientes = new String[listaClientesPartidas.size()];
		int i=0;
		for (Entry<String, IntCallbackCliente> cliente : listaClientesPartidas.entrySet()) {
			listaClientes[i] = cliente.getKey();
			i++;
		}
		return listaClientes;
	}

	@Override
	public boolean aceptaPartida(String nombreJugador, String nombreRival) throws RemoteException {
		String mensajeRival = nombreJugador + " ha aceptado partida.";
		String mensajeJug = "Partida de: " + nombreJugador+" aceptada";
		if (listaClientesPartidas.containsKey(nombreRival)) {
			try {
				listaClientesPartidas.get(nombreRival).notifica(mensajeRival);
				listaClientesPartidas.get(nombreJugador).notifica(mensajeJug);
			} catch (Exception e) {
				listaClientesPartidas.remove(nombreRival);
				return false;
			}
			listaClientesPartidas.remove(nombreJugador);
			listaClientesPartidas.remove(nombreRival);
			return true;
		} else {
			return false;
		}
	}

}