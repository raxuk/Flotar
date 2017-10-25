package comun.flota.rmi;

import java.rmi.RemoteException;

import comun.comun.Partida;

public class ImplServidorPartidasRMI implements IntServidorPartidasRMI {
	Partida partida = null;

	@Override
	public void nuevaPartida(int nf, int nc, int nb) throws RemoteException {
		this.partida = new Partida(nf, nc, nb);
	}

	@Override
	public void pruebaCasilla(int nf, int nc) throws RemoteException {
		this.partida.pruebaCasilla(nf, nc);
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
