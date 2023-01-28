package modelo;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;


public class SocketServidorChat {
	static int puerto = 8025;
	static HiloCliente[] arrayHiloClientes = new HiloCliente[10];
	public static void main(String[] args) {
		ServerSocket ssocketServidor = null;
		try {
			ssocketServidor = new ServerSocket(puerto);
			while(true) {
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
					arrayHiloClientes[a] = hilo;
					arrayHiloClientes[a].start();
				}
			}
		}
	}
}
