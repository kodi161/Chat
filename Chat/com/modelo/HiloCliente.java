package modelo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class HiloCliente extends Thread{
	//Atributos Simples
	Socket csocket;
	DataOutputStream dos;
	DataInputStream dis;
	String mensajeRecibido;
	String mensajeReal;
	boolean conectado=true;
	String nickUsuario;

	//Constructor
	public HiloCliente(Socket sc) {
		csocket=sc;
		try {
			dos = new DataOutputStream(csocket.getOutputStream());
			dis = new DataInputStream(csocket.getInputStream());
		}catch (SocketException e) {
			System.out.println("Ha habido un problema con el socket");
		} catch (IOException e) {
			e.printStackTrace();
			cierreConexion();
		}
	}
	
	//Getter
	public DataOutputStream getDataOutputStream() {
		return dos;
	}

	//Método 1:
	//Hace que el servidor reciba mensajes del cliente
	public void recibirMensaje() {
		try {
			mensajeRecibido = dis.readUTF();
			System.out.println();
			obtenerNick();
			obtenerMensajeReal();
			palabrasClave();
			MensajeChat.mensajeActualizado(mensajeRecibido);
			System.out.println(mensajeRecibido);
		}catch (EOFException e) {
			System.out.println("Ha habido algun problema al recibir el mensaje");
		}catch (SocketException e) {
			cierreConexion();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//Método 2:
		//Obtiene el nick del usuario
		public void obtenerNick() {
			for (int i = 0; i < mensajeRecibido.length(); i++) {
				if(mensajeRecibido.charAt(i)==':') {
					nickUsuario = mensajeRecibido.substring(0, i);
				}
			}
		}
		
	//Método 3:
	//Obtiene el mensaje que ha enviado el cliente pero sin su nick
	public void obtenerMensajeReal() {
		for (int i = 0; i < mensajeRecibido.length(); i++) {
			if(mensajeRecibido.charAt(i)==':') {
				mensajeReal = mensajeRecibido.substring(i+2);
			}
		}
	}
	
	

	//Metodo 4:
	//Permite que ciertas palabras claves tengan una cierta funcionalidad
	public void palabrasClave() {
		switch (mensajeReal) {
		case "ADIOS":
			mensajeRecibido=nickUsuario+"ya no esta conectado";
			cierreConexion();
			break;
		case "HOLA":
			mensajeReal = "HOOLAAAAAAAAAAAAAAAAAAAAAAAAAAA!!!";
			mensajeRecibido=nickUsuario+": "+mensajeReal;
			break;
		case "PIRAMIDE":
			mensajeRecibido=nickUsuario+(":\n* \n **\n *** \n*****\n");
			break;
		default:
			break;
		}
	}

	//Método 5:
	//Se cierra la conexion
	public void cierreConexion() {
		try {
			csocket.close();
			dos.close();
			dis.close();
			conectado = false;
			currentThread().join();
			System.out.println("Se ha desconectado un cliente");
		} catch (Exception e) {
			conectado=false;
		}
	}

	@Override
	public synchronized void run() {
		while(conectado) {
			//Comprueba si hay mensajes nuevos, TRUE es que si hay nuevos mensajes
			if(MensajeChat.estadoMensaje==false) {
				recibirMensaje();
				MensajeChat.estadoMensaje=false;
			}			
		}
		cierreConexion();
	}

}
