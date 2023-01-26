package modelo;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;


public class SocketServidorChat {
static int puerto = 8025;
public static void main(String[] args) {
	ServerSocket ssocketServidor = null;
	try {
		ssocketServidor = new ServerSocket(puerto);
		while(true) {
			Socket socketAceptado = ssocketServidor.accept();
			System.out.println("Conectado el usuario con IP:"+socketAceptado.getInetAddress());
			new Thread(new HiloCliente(socketAceptado)).start();
		}
	}catch (BindException e) {
		System.out.println("El servidor se esta activando de nuevo ");
	} catch (IOException e) {
		System.out.println("Problema 1");
		e.printStackTrace();
	} 
}
}
