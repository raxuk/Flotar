package servidor.flota.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import comun.flota.rmi.IntServidorPartidasRMI;

public class ImplServidorPartidasRMI extends UnicastRemoteObject implements IntServidorPartidasRMI {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8094810623415437567L;
	Partida partida = null;

	protected ImplServidorPartidasRMI() throws RemoteException {
		super();
	}


	@Override
	public void nuevaPartida(int nf, int nc, int nb) throws RemoteException {
		this.partida = new Partida(nf, nc, nb);
	}

	@Override
	public int pruebaCasilla(int nf, int nc) throws RemoteException {
		return this.partida.pruebaCasilla(nf, nc);
	}

	@Override
	public String getBarco(int idBarco) throws RemoteException {
		return this.partida.getBarco(idBarco);
	}

	@Override
	public String[] getSolucion() throws RemoteException {
		return this.partida.getSolucion();
	}

}
