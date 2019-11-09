package es.uvigo.esei.tfg.usb;

import es.uvigo.esei.tfg.usb.ServerAction;
import java.net.*;
import java.io.*;
import java.util.*;

public class ClientTesterThread extends Thread {
	private Socket socket = null;
	// private DataInputStream input = null;
	// private DataOutputStream out = null;
	private boolean isConnected = false;
	private ObjectInputStream inputObject = null;
	private ObjectOutputStream outObject = null;
	private String address;
	int port;
	private boolean running = false;

	public ClientTesterThread(String address, int port) {
		this.address = address;
		this.port = port;
		this.setRunning(true);
	}

	public void run() {
		while (isRunning()) {
			while (socket == null) {
				try {
					socket = new Socket(address, port);
					System.out.println("Connected");

					System.out.println("Inicializados outObject InputObject");

				} catch (UnknownHostException u) {
					System.out.println(u);
				} catch (IOException i) {/*
											 * System.out.println("Error al conectar con el server");
											 * System.out.println(i);
											 */
				}

			}

			if (this.isConnected == false) {
				System.out.println("Cambiando estado de conexion a true!");
				this.isConnected = true;
			}
		}
	}

	public void sendToServer(Object obj) {
		try {
			ServerAction serverAction = (ServerAction) obj;
			System.out.println(serverAction.toString());
			if (this.outObject == null) {
				this.outObject = new ObjectOutputStream(socket.getOutputStream());
			}

			System.out.println("Escribiendo objecto server action");
			outObject.writeObject(obj);
			outObject.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Object readToServer() {

		Object toret = null;
		try {
			if (this.inputObject == null) {
				inputObject = new ObjectInputStream(socket.getInputStream());
			}
			while (toret == null) {
				// System.out.println("Intentando leer objeto");
				// inputObject = new ObjectInputStream(socket.getInputStream());
				// sends output to the socket
				// outObject = new ObjectOutputStream(socket.getOutputStream());
				// System.out.println("Leyendo object");
				toret = (Object) inputObject.readObject();
				// System.out.println(toret);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return toret;
	}

	public void closeConnection() {
		try {
			System.out.println("Cerrando conexcion");
			inputObject.close();
			outObject.close();
			socket.close();
		} catch (IOException i) {
			System.out.println("Error al cerrar ");
			System.out.println(i);
		}

	}

	public boolean isConnected() {

		if (socket == null) {
			// System.out.println("Conexion falsa");
			return false;

		} else {
			try {
				// System.out.println(socket.getInputStream().read());
				// System.out.println("Comprobacion con isReachable:
				// "+socket.getInetAddress().isReachable(1));
				// System.out.println("Conprobacion con isSocketAliveUitlitybyCrunchify:
				// "+isSocketAliveUitlitybyCrunchify("127.0.0.1", 5000));
				// return socket.isConnected();
				// return isSocketAliveUitlitybyCrunchify("127.0.0.1", 5000);
				if (isSocketAliveUitlitybyCrunchify(address, port)) {
					return true;
				} else if (!isSocketAliveUitlitybyCrunchify(address, port)) {
					closeConnection();
					socket = null;
					return false;
				} else {
					return false;
				}

			} catch (Exception e) {

				return false;
			}
		}
		/*
		 * System.out.println("Leyendo estado de la conexion: "+ this.isConnected);
		 * return this.isConnected;
		 */

	}

	public static boolean isSocketAliveUitlitybyCrunchify(String hostName, int port) {
		boolean isAlive = false;

		// Creates a socket address from a hostname and a port number
		SocketAddress socketAddress = new InetSocketAddress(hostName, port);
		Socket socket = new Socket();

		// Timeout required - it's in milliseconds
		int timeout = 2000;

		log("hostName: " + hostName + ", port: " + port);
		try {
			socket.connect(socketAddress, timeout);
			socket.close();
			isAlive = true;

		} catch (SocketTimeoutException exception) {
			System.out.println("SocketTimeoutException " + hostName + ":" + port + ". " + exception.getMessage());
		} catch (IOException exception) {
			System.out.println(
					"IOException - Unable to connect to " + hostName + ":" + port + ". " + exception.getMessage());
		}
		return isAlive;
	}

	// Simple log utility
	private static void log(String string) {
		// System.out.println(string);
	}

	// Simple log utility returns boolean result
	private static void log(boolean isAlive) {
		// System.out.println("isAlive result: " + isAlive + "\n");
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

}