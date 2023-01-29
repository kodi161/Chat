package modelo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import vista.VentanaCliente;

public class Cliente {
	//ATRIBUTOS
	public static int PORT = 8025;
	public static String SERVER_LOCATION = "localhost";// Esto se puede cambiar por una ip sin problema
	private static Socket socketCliente;
	private static DataOutputStream dosCliente;
	private BufferedReader brCliente;
	static VentanaCliente vc; 

	//MAIN
	public static void main(String[] args) {
		vc = new VentanaCliente();
	}

	//Constructor
	public Cliente() {
		try {
			socketCliente = new Socket(Cliente.SERVER_LOCATION, Cliente.PORT);
			brCliente = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
			dosCliente = new DataOutputStream(socketCliente.getOutputStream());
			/*
			 * Creamos un hilo al iniciarse esta clase, esto se hará despues de pulsar el boton
			 * entrar de la ventana cliente, con esta forma creamos un hilo a la vez.
			 */
			Thread escuchador = new Thread(new Runnable() {
				@Override
				public void run() {
					while(true){
						try {
							if(!socketCliente.isClosed() && brCliente.ready()){
								String mensaje = brCliente.readLine();
								vc.agregarLinea(mensaje+"\n");
								//System.out.println("Mensaje entrante> " + mensaje);
							}
						} catch (IOException e) {
							e.printStackTrace();
							break;//rompe el hilo
						}
					}
				}
			});
			escuchador.start(); //arrancamos el hilo

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Envia un mensaje al servidor
	public  void prueba(String mensaje) throws IOException {
		if(!socketCliente.isClosed() && !mensaje.equals("")) {	
			try {
				dosCliente.writeBytes(mensaje+'\n');	
			} catch (SocketException e) {
				System.out.println("Ha salido del servidor");
			}
		}
	}
}

