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
			System.out.println("Server registrado.");
			listRegistry(registryURL);
			System.out.println("Juego Server ready.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	  // This method starts a RMI registry on the local host, if
	  // it doesn't already exists at the specified port number.
	   private static void startRegistry(int RMIPortNum)
	      throws RemoteException{
	      try {
	         Registry registry = 
	            LocateRegistry.getRegistry(RMIPortNum);
	         registry.list();  
	         // This call will throw an exception if the registry does not already exist
	      } catch (RemoteException e) { 
	         // No valid registry at that port.
	         System.out.println("RMI registry cannot be located at port " + RMIPortNum);
	         LocateRegistry.createRegistry(RMIPortNum);
	         System.out.println("RMI registry created at port " + RMIPortNum);
	      }
	   } // end startRegistry

	  //This method lists the names registered with a Registry
	  private static void listRegistry(String registryURL)
	       throws RemoteException, MalformedURLException {
	       System.out.println("Registry " + registryURL + " contains: ");
	       String [] names = Naming.list(registryURL);
	       for (int i=0; i < names.length; i++)
	          System.out.println(names[i]);
	  } //end listRegistry

}
