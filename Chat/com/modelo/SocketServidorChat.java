package modelo;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;


public class SocketServidorChat {
	static int puerto = 8025;
	static HiloCliente[] arrayHiloClientes;

	public static void main(String[] args) {
		ServerSocket ssocketServidor = null;
		try {
			ssocketServidor = new ServerSocket(puerto);
			arrayHiloClientes = new HiloCliente[10];
			while(ssocketServidor.isClosed() == false) {
				if (MensajeChat.estadoMensaje==true) {
					enviarMensaje(MensajeChat.mensaje);
				}
				Socket socketAceptado = ssocketServidor.accept();
				System.out.println("Conectado el usuario con IP:"+socketAceptado.getInetAddress());
				rellenarArrayHilos(new HiloCliente(socketAceptado));
				
			}
		}catch (BindException e) {
			System.out.println("El servidor se ha reiniciado");
		} catch (IOException e) {
			System.out.println("Problema 1");
			e.printStackTrace();
		} 
	}

	public static void rellenarArrayHilos(HiloCliente hilo) {
		for(int a = 0; a< arrayHiloClientes.length; a++) {
			if(arrayHiloClientes[a] == null) {
				arrayHiloClientes[a]= hilo;
				arrayHiloClientes[a].start();
				break;
			}else{ 
				if(arrayHiloClientes[a].isAlive()!= false) {
					try {
						arrayHiloClientes[a].join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					arrayHiloClientes[a] = hilo;
					arrayHiloClientes[a].start();
				}
			}
		}
	}
	//Método 1:
	//Hace que el servidor envie un mensaje al cliente
	public static void enviarMensaje(String mensaje) {
		try {
			for (int i = 0; i < SocketServidorChat.arrayHiloClientes.length; i++) {
				if(SocketServidorChat.arrayHiloClientes[i] != null &&
						SocketServidorChat.arrayHiloClientes[i].isAlive() ) {
					SocketServidorChat.arrayHiloClientes[i].getDataOutputStream().writeUTF(mensaje);
				}
			}
		}catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void vaciarArrayHilos() {
		for (int i = 0; i < arrayHiloClientes.length; i++) {
			arrayHiloClientes[i]=null;
		}
	}
}
