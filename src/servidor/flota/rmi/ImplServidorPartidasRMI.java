package servidor.flota.rmi;

import java.rmi.RemoteException;

import comun.flota.rmi.IntServidorPartidasRMI;

public class ImplServidorPartidasRMI implements IntServidorPartidasRMI {
	Partida partida = null;

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
