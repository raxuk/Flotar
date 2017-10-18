package servidor.flota.sockets;


import java.io.IOException;
import java.net.SocketException;

import partida.flota.sockets.*;
import comun.flota.sockets.MyStreamSocket;

/**
 * Clase ejecutada por cada hebra encargada de servir a un cliente del juego Hundir la flota.
 * El metodo run contiene la logica para gestionar una sesion con un cliente.
 */

 // Revisar el apartado 5.5. del libro de Liu

class HiloServidorFlota implements Runnable {
   MyStreamSocket myDataSocket;
   private Partida partida = null;

	/**
	 * Construye el objeto a ejecutar por la hebra para servir a un cliente
	 * @param	myDataSocket	socket stream para comunicarse con el cliente
	 */
   HiloServidorFlota(MyStreamSocket myDataSocket) {
	   this.myDataSocket = myDataSocket;
   }
 
   /**
	* Gestiona una sesion con un cliente	
   */
   public void run( ) {
      boolean done = false;
      int operacion = 0;
      // ...
      try {
         while (!done) {
        	 // Recibe una peticion del cliente
        	 String peticionCliente = myDataSocket.receiveMessage();

        	 // Extrae la operación y los argumentos
        	 String[] peticionSplit=peticionCliente.split("#");
        	 int [] peticion = new int[peticionSplit.length];
        	 
        	 for(int i=0;i<peticion.length;i++) 
        		 peticion[i] = Integer.parseInt(peticionSplit[i]);
        	 
        	 operacion = peticion[0];
                      
             switch (operacion) {
             case 0:  // fin de conexión con el cliente
            	 myDataSocket.close();
            	 done = true;
            	 break;

             case 1: { // Crea nueva partida
            	 partida = new Partida(peticion[1], peticion[2], peticion[3]);
            	 break;
             }             
             case 2: { // Prueba una casilla y devuelve el resultado al cliente
            	 if(partida!=null){
            	 int resultado = partida.pruebaCasilla(peticion[1], peticion[2]);
            	 myDataSocket.sendMessage(String.valueOf(resultado));}
                 break;
             }
             case 3: { // Obtiene los datos de un barco y se los devuelve al cliente
            	 if(partida!=null){
            	 String barco = partida.getBarco(peticion[1]);
            	 myDataSocket.sendMessage(barco);}
                 break;
             }
             case 4: { // Devuelve al cliente la solucion en forma de vector de cadenas
            	 if(partida!=null){
            	 String[] solucionBarcos = partida.getSolucion();
        	   // Primero envia el numero de barcos 
            	 myDataSocket.sendMessage(String.valueOf(solucionBarcos.length));
               // Despues envia una cadena por cada barco
            	 for(String barcoSol : solucionBarcos)
            		 myDataSocket.sendMessage(barcoSol);}
               break;
             }
         } // fin switch
       } // fin while   
     } // fin try
     catch (Exception ex) {
        System.out.println("Exception caught in thread: " + ex);
     } // fin catch
   } //fin run
   
} //fin class 
