package modelo;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;


public class SocketServidorChat {
	static int puerto = 8025;
	static Thread[] arrayHiloClientes;

	public static void main(String[] args) {
		ServerSocket ssocketServidor = null;
		Socket socketAceptado=null;
		try {
			ssocketServidor = new ServerSocket(puerto);
			arrayHiloClientes = new Thread[10];
			do{
				socketAceptado = ssocketServidor.accept();
				System.out.println("Conectado el usuario con IP:"+socketAceptado.getInetAddress());
				rellenarArrayHilos(new Thread( new HiloCliente(socketAceptado)));

			}while(ssocketServidor.isClosed() == false);
		}catch (BindException e) {
			System.out.println("El servidor se ha reiniciado");
		} catch (IOException e) {
			System.out.println("Problema 1");
			e.printStackTrace();
		} 
	}

	public static void rellenarArrayHilos(Thread hilo) {
		for(int a = 0; a< arrayHiloClientes.length; a++) {
			if(arrayHiloClientes[a] == null) {
				System.out.println("Ha ingresado un usuario a la lista "+a);
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

	public static void vaciarArrayHilos() {
		for (int i = 0; i < arrayHiloClientes.length; i++) {
			if(arrayHiloClientes[i].isAlive()!= false) {
				arrayHiloClientes[i]=null;
			}
		}
	}
}
