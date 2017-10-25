package cliente.flota.rmi;

import java.rmi.Naming;

import comun.flota.rmi.IntServidorJuegoRMI;
import comun.flota.rmi.IntServidorPartidasRMI;

public class ClienteFlotaRMI {

	public static void main(String args[]) {
		try {
			// start a security manager - this is needed if stub
			// downloading is in use for this application.
			System.setSecurityManager(new SecurityManager());

			String registryURL = "rmi://localhost:1099/hello";
			// find the remote object and cast it to an
			// interface object
			IntServidorJuegoRMI servJuego = (IntServidorJuegoRMI) Naming.lookup(registryURL);
			System.out.println("Lookup completed ");
			// invoke the remote method
			// cada cliente usara este objeto "servPartidas" para crear y
			// acceder a sus partidas, que se guardaran en el servidor
			IntServidorPartidasRMI servPartidas = servJuego.nuevoServidorPartidas();
		} // end try
		catch (Exception e) {
			System.out.println("Exception in HelloClient: " + e);
		}
	} // end main
}// end class
