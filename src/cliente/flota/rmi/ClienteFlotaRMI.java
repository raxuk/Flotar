package cliente.flota.rmi;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;

import javax.swing.*;

import comun.flota.rmi.ImplCallbackCliente;
import comun.flota.rmi.IntCallbackCliente;
import comun.flota.rmi.IntServidorJuegoRMI;
import comun.flota.rmi.IntServidorPartidasRMI;

public class ClienteFlotaRMI {

	/**
	 * Implementa el juego 'Hundir la flota' mediante una interfaz gráfica (GUI)
	 */

	/**
	 * Parametros por defecto de una partida
	 */
	public static final int NUMFILAS = 8, NUMCOLUMNAS = 8, NUMBARCOS = 6;

	static Scanner scan = new Scanner(System.in);

	protected GuiTablero guiTablero = null;
	protected IntServidorPartidasRMI partida = null;
	protected IntServidorJuegoRMI servJuego = null;
	protected String nombreJugador = null;

	/**
	 * Atributos de la partida guardados en el juego para simplificar su
	 * implementación
	 */
	private int quedan = NUMBARCOS, disparos = 0;

	/**
	 * Programa principal. Crea y lanza un nuevo juego
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		ClienteFlotaRMI juego = new ClienteFlotaRMI();
		juego.ejecuta();
	} // end main

	/**
	 * Lanza una nueva hebra que crea la primera partida y dibuja la interfaz
	 * grafica: tablero
	 */
	private void ejecuta() {
		// Instancia la primera partida
		try {
			// start a security manager - this is needed if stub
			// downloading is in use for this application.
			//System.setSecurityManager(new SecurityManager());

			String registryURL = "rmi://localhost:1099/juego";
			// find the remote object and cast it to an
			// interface object
			this.servJuego = (IntServidorJuegoRMI) Naming.lookup(registryURL);
			System.out.println("Lookup completed ");
			while (nombreJugador == null || nombreJugador.equals("")) {
				// Nombre jugador
				System.out.println("Introducir nombre del jugador: ");
				this.nombreJugador = scan.nextLine();
			}
			// invoke the remote method
			// cada cliente usara este objeto "servPartidas" para crear y
			// acceder a sus partidas, que se guardaran en el servidor
			this.partida = servJuego.nuevoServidorPartidas();
			// Pedir nombre del jugador e iniciar partida
			partida.nuevaPartida(NUMFILAS, NUMCOLUMNAS, NUMBARCOS);
		} // end try
		catch (Exception e) {
			System.out.println("Exception en ClienteFlotaRMI: " + e);
		}
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				guiTablero = new GuiTablero(NUMFILAS, NUMCOLUMNAS);
				guiTablero.dibujaTablero();
			}
		});
	} // end ejecuta

	private void salir() {
		try {
			guiTablero.liberaRecursos();
			servJuego.borraPartida(nombreJugador);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	/******************************************************************************************/
	/*********************
	 * * CLASE INTERNA GuiTablero *
	 ***************************************/
	/******************************************************************************************/
	private class GuiTablero {

		private int numFilas, numColumnas;

		private JFrame frame = null; // Tablero de juego
		private JLabel estado = null; // Texto en el panel de estado
		private JButton buttons[][] = null; // Botones asociados a las casillas
											// de la partida

		/**
		 * Constructor de una tablero dadas sus dimensiones
		 */
		GuiTablero(int numFilas, int numColumnas) {
			this.numFilas = numFilas;
			this.numColumnas = numColumnas;
			frame = new JFrame("Partida de " + nombreJugador);
			frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			frame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					salir();
				}
			});
		}

		/**
		 * Dibuja el tablero de juego y crea la partida inicial
		 */
		public void dibujaTablero() {
			anyadeMenu();
			anyadeGrid(numFilas, numColumnas);
			anyadePanelEstado("Intentos: " + disparos + "    Barcos restantes: " + quedan);
			frame.setSize(300, 300);
			frame.setVisible(true);
		} // end dibujaTablero

		/**
		 * Anyade el menu de opciones del juego y le asocia un escuchador
		 */
		private void anyadeMenu() {
			// Listener del menu
			MenuListener mlist = new MenuListener();

			// barra del menu
			JMenuBar menuBar = new JMenuBar();
			// añadir barra del menu al frame
			frame.getContentPane().add(menuBar, BorderLayout.NORTH);

			// menu
			JMenu opcionesPartidaMenu = new JMenu("Opciones");
			JMenu opcionesMultiMenu = new JMenu("Multijugador");
			// añadir menu a la barra
			menuBar.add(opcionesPartidaMenu);
			menuBar.add(opcionesMultiMenu);

			// MENU OPCIONES
			// elementos del menuOpciones
			JMenuItem mostrarSolucion = new JMenuItem("Mostrar Solucion");
			JMenuItem nuevaPartida = new JMenuItem("Nueva Partida");
			JMenuItem salirJuego = new JMenuItem("Salir");
			// añadir elementos al menu Opciones
			opcionesPartidaMenu.add(mostrarSolucion);
			opcionesPartidaMenu.add(nuevaPartida);
			opcionesPartidaMenu.add(salirJuego);
			// añadir listeners a los elementos del menu opciones
			mostrarSolucion.addActionListener(mlist);
			nuevaPartida.addActionListener(mlist);
			salirJuego.addActionListener(mlist);

			// MENU MULTI
			// elementos del menu multi
			JMenuItem proponPartida = new JMenuItem("Proponer partida");
			JMenuItem borraPartida = new JMenuItem("Borrar partida propuesta");
			JMenuItem listaPartidas = new JMenuItem("Listar partidas");
			JMenuItem aceptaPartida = new JMenuItem("Aceptar Partidas");
			// añadir elementos al menuMulti
			opcionesMultiMenu.add(proponPartida);
			opcionesMultiMenu.add(borraPartida);
			opcionesMultiMenu.add(listaPartidas);
			opcionesMultiMenu.add(aceptaPartida);
			// añadir listeners a los elementos del menu multi
			proponPartida.addActionListener(mlist);
			borraPartida.addActionListener(mlist);
			listaPartidas.addActionListener(mlist);
			aceptaPartida.addActionListener(mlist);

		} // end anyadeMenu

		/**
		 * Anyade el panel con las casillas del mar y sus etiquetas. Cada
		 * casilla sera un boton con su correspondiente escuchador
		 *
		 * @param nf
		 *            numero de filas
		 * @param nc
		 *            numero de columnas
		 */
		private void anyadeGrid(int nf, int nc) { // COMENTAR
			// JPANEL
			JPanel centro = new JPanel();
			// crear layout
			GridLayout centroLay = new GridLayout(nf + 1, nc + 2);
			// asignar layout al jpanelCentro
			centro.setLayout(centroLay);

			// asignar tamaño
			buttons = new JButton[nf][nc];

			// crear button listener
			ButtonListener blist = new ButtonListener();

			// añadir botones, letras, numeros al jpanel
			centro.add(new JLabel(" "));

			for (int i = 1; i <= nc; i++) {
				centro.add(new JLabel(Integer.toString(i), SwingConstants.CENTER));
			}

			centro.add(new JLabel(" "));

			char letra = 'A';
			for (int i = 0; i < nf; i++) {
				centro.add(new JLabel(String.valueOf((letra)), SwingConstants.CENTER));

				for (int n = 0; n < nc; n++) {
					buttons[i][n] = new JButton();
					buttons[i][n].addActionListener(blist);
					buttons[i][n].putClientProperty("fila", i);
					buttons[i][n].putClientProperty("columna", n);
					centro.add(buttons[i][n]);
				}
				centro.add(new JLabel(String.valueOf((letra)), SwingConstants.CENTER));
				letra++;
			}

			frame.getContentPane().add(centro, BorderLayout.CENTER);

		}

		/**
		 * Anyade el panel de estado al tablero
		 *
		 * @param cadena
		 *            cadena inicial del panel de estado
		 */
		private void anyadePanelEstado(String cadena) {
			JPanel panelEstado = new JPanel();
			estado = new JLabel(cadena);
			panelEstado.add(estado);
			// El panel de estado queda en la posición SOUTH del frame
			frame.getContentPane().add(panelEstado, BorderLayout.SOUTH);
		} // end anyadePanel Estado

		/**
		 * Cambia la cadena mostrada en el panel de estado
		 *
		 * @param cadenaEstado
		 *            nuevo estado
		 */
		public void cambiaEstado(String cadenaEstado) {
			estado.setText(cadenaEstado);
		} // end cambiaEstado

		/**
		 * Muestra la solucion de la partida y marca la partida como finalizada
		 */
		public void muestraSolucion() {
			// Colorea casillas a mar y despues comprueba el id de cada casilla
			// para pintarlo
			String[] solucion = null;
			try {
				solucion = partida.getSolucion();
			} catch (IOException e) {
				e.printStackTrace();
			}

			for (int i = 0; i < numFilas; i++)
				for (int j = 0; j < numColumnas; j++)
					guiTablero.pintaBoton(buttons[i][j], Color.CYAN);

			for (String barco : solucion)
				pintaBarcoHundido(barco, Color.MAGENTA);

		} // end muestraSolucion

		/**
		 * Pinta un barco como hundido en el tablero
		 *
		 * @param cadenaBarco
		 *            cadena con los datos del barco codifificados como
		 *            "filaInicial#columnaInicial#orientacion#tamanyo"
		 * @param color
		 *            color a usar
		 */
		public void pintaBarcoHundido(String cadenaBarco, Color color) {
			// Una vez hundido se encarga de pintarlo segun orientacion
			String[] partes = cadenaBarco.split("#");

			int fInicial = Integer.parseInt(partes[0]);
			int cInicial = Integer.parseInt(partes[1]);
			String orientacion = partes[2];
			int tamayo = Integer.parseInt(partes[3]);

			if (orientacion.equals("V")) {
				for (int i = 0; i < tamayo; i++) {
					pintaBoton(buttons[fInicial + i][cInicial], color);
				}
			} else {
				for (int i = 0; i < tamayo; i++) {
					pintaBoton(buttons[fInicial][cInicial + i], color);
				}
			}
		}

		// end pintaBarcoHundido

		/**
		 * Pinta un botón de un color dado
		 *
		 * @param b
		 *            boton a pintar
		 * @param color
		 *            color a usar
		 */
		public void pintaBoton(JButton b, Color color) {
			b.setBackground(color);
			// El siguiente código solo es necesario en Mac OS X
			b.setOpaque(true);
			b.setBorderPainted(false);
		} // end pintaBoton

		/**
		 * Limpia las casillas del tablero pintándolas del gris por defecto
		 */
		public void limpiaTablero() {
			for (int i = 0; i < numFilas; i++) {
				for (int j = 0; j < numColumnas; j++) {
					buttons[i][j].setBackground(null);
					buttons[i][j].setOpaque(true);
					buttons[i][j].setBorderPainted(true);
				}
			}
		} // end limpiaTablero

		/**
		 * Destruye y libera la memoria de todos los componentes del frame
		 */
		public void liberaRecursos() {
			frame.dispose();
		} // end liberaRecursos

	} // end class GuiTablero

	/******************************************************************************************/
	/*********************
	 * * CLASE INTERNA MenuListener *
	 ***************************************/
	/******************************************************************************************/

	/**
	 * Clase interna que escucha el menu de Opciones del tablero
	 */
	private class MenuListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// Segun elemento menu realiza accion
			JMenuItem elem = (JMenuItem) e.getSource();
			String texto = elem.getText();

			switch (texto) {
			// elementos MenuOpciones
			case "Mostrar Solucion":
				guiTablero.muestraSolucion();
				break;

			case "Nueva Partida":
				try {
					partida.nuevaPartida(NUMFILAS, NUMCOLUMNAS, NUMBARCOS);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				// Resetea el juego a partir de aqui
				guiTablero.limpiaTablero();
				quedan = NUMBARCOS;
				disparos = 0;
				guiTablero.cambiaEstado("Intentos: " + disparos + "    Barcos restantes: " + quedan);
				break;

			case "Salir":
				salir();
				break;

			// elementos MenuMulti
			case "Proponer partida":
				IntCallbackCliente callbackClientObject = null;
				try {
					callbackClientObject = new ImplCallbackCliente();
				} catch (RemoteException e2) {
					System.out.println("Error en ClienteFlotaRMI  - crear callbackClientObject");
					e2.printStackTrace();
				}
				try {
					if(servJuego.proponPartida(nombreJugador, callbackClientObject))
						System.out.println("Partida propuesta con éxito.");
					else
						System.out.println("No se ha podido proponer la partida.");
				} catch (RemoteException e1) {
					System.out.println("Error en ClienteFlotaRMI  - propon partida");
					e1.printStackTrace();
				}
				break;

			case "Borrar partida propuesta":
				try {
					if(servJuego.borraPartida(nombreJugador))
						System.out.println("Partida borrada con éxito.");
					else
						System.out.println("No se ha podido borrar la partida.");
				} catch (RemoteException e1) {
					System.out.println("Error en ClienteFlotaRMI  - borrar partida");
					e1.printStackTrace();
				}
				break;

			case "Listar partidas":
				String[] listaPartidas = null;
				try {
					listaPartidas = servJuego.listaPartidas();
				} catch (RemoteException e1) {
					System.out.println("Error en ClienteFlotaRMI - lista partida");
					e1.printStackTrace();
				}
				if (listaPartidas[0].equals("")) {
					System.out.println("No hay partidas propuestas.");
				} else {
					System.out.println("Lista partidas:");
					for (String s : listaPartidas)
						System.out.println("Partida de: " + s);
				}
				break;

			case "Aceptar Partidas":
				// Introducir nombre del rival
				System.out.println("Introducir nombre del rival: ");
				String nombreRival = scan.nextLine();
				try {
					if (nombreJugador.equals(nombreRival)) {
						System.out.println("No puedes aceptar tu propia partida.");
						break;
					}
					if (servJuego.aceptaPartida(nombreJugador, nombreRival))
						System.out.println("Partida contra " + nombreRival + " aceptada con éxito");
					else {
						System.out.println("Partida de " + nombreRival + " no existe.");
					}
				} catch (RemoteException e1) {
					System.out.println("Error en ClienteFlotaRMI - aceptar partida");
					e1.printStackTrace();
				}
				break;

			default:
				break;
			}// end switch

		} // end actionPerformed

	} // end class MenuListener

	/******************************************************************************************/
	/*********************
	 * * CLASE INTERNA ButtonListener *
	 *************************************/
	/******************************************************************************************/
	/**
	 * Clase interna que escucha cada uno de los botones del tablero Para poder
	 * identificar el boton que ha generado el evento se pueden usar las
	 * propiedades de los componentes, apoyandose en los metodos
	 * putClientProperty y getClientProperty
	 */
	private class ButtonListener implements ActionListener {
		// Segun casilla la prueba si no ha sido ya tocada

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton boton = new JButton();
			Color colorDefault = boton.getBackground();
			boton = (JButton) e.getSource();

			if (quedan > 0 && boton.getBackground().equals(colorDefault)) {
				int fila = (int) boton.getClientProperty("fila");
				int columna = (int) boton.getClientProperty("columna");
				int casilla = -5;
				try {
					casilla = partida.pruebaCasilla(fila, columna);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				if (casilla == -1)
					guiTablero.pintaBoton(boton, Color.CYAN);
				else if (casilla == -2)
					guiTablero.pintaBoton(boton, Color.ORANGE);
				else {
					try {
						guiTablero.pintaBarcoHundido(partida.getBarco(casilla), Color.RED);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					quedan--;
				}
				disparos++;

				if (quedan == 0) {
					guiTablero.cambiaEstado("Game Over con disparos: " + disparos);

				} else {
					guiTablero.cambiaEstado("Intentos: " + disparos + "    Barcos restantes: " + quedan);
				}
			}
			// end actionPerformed

		} // end class ButtonListener
	}
}// end class Juego