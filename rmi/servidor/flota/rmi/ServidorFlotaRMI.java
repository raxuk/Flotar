package servidor.flota.rmi;

import java.rmi.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.net.*;

public class ServidorFlotaRMI {

	public static void main(String args[]) {
		try {
			System.setSecurityManager(new SecurityManager());

			startRegistry(1099);
			ImplServidorJuegoRMI exportedObj = new ImplServidorJuegoRMI();
			String registryURL = "rmi://localhost:1099/juego";
			Naming.rebind(registryURL, exportedObj);
			System.out.println("Server registrado. Registro contiene: ");
			listRegistry(registryURL);
			System.out.println("Hello Server ready.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void listRegistry(String registryURL) {
		// TODO Auto-generated method stub

	}

	private static void startRegistry(int RMIPort) {
		// TODO Auto-generated method stub

	}

}
