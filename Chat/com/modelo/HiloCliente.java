package modelo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class HiloCliente implements Runnable{
	//Atributos Simples
	Socket csocket;
	DataOutputStream dos;
	DataInputStream dis;
	String mensajeRecibido;
	String mensajeReal;
	boolean conectado=true;

	//Constructor
	public HiloCliente(Socket sc) {
		csocket=sc;
		try {
			dos = new DataOutputStream(csocket.getOutputStream());
			dis = new DataInputStream(csocket.getInputStream());
		}catch (SocketException e) {
			System.out.println("Ha salido un cliente");
			cierreConexion();
		} catch (IOException e) {
			e.printStackTrace();
			cierreConexion();
		}
	}

	//Método 1:
	//Hace que el servidor envie un mensaje al cliente
	public void enviarMensaje(String me) {
		try {
			System.out.println("Se ha enviado el mensaje a todos");
			dos.writeUTF(me.trim());
		}catch (SocketException e) {
			System.out.println("Ha salido un cliente");
			cierreConexion();
		} catch (IOException e) {
			e.printStackTrace();
			cierreConexion();
		}
	}

	//Método 2:
	//Hace que el servidor reciba mensajes del cliente
	public void recibirMensaje() {
		try {
			mensajeRecibido = dis.readUTF();//Recibe el mensaje
			obtenerMensajeReal();//Método 3
			palabrasClave();//Método 4
			MensajeChat.mensajeActualizado(mensajeRecibido);
			System.out.println(mensajeRecibido);
		}catch (SocketException e) {
			System.out.println("Ha salido un cliente");
			cierreConexion();
		} catch (IOException e) {
			e.printStackTrace();
			cierreConexion();
		}
	}

	//Método 3:
	//Obtiene el mensaje que ha enviado el cliente pero sin su nick
	public void obtenerMensajeReal() {
		for (int i = 0; i < mensajeRecibido.length(); i++) {
			if(mensajeRecibido.charAt(i)==':') {
				mensajeReal = mensajeRecibido.substring(i+1).trim() ;
			}
		}
	}

	//Metodo 4:
	//Permite que ciertas palabras claves tengan una cierta funcionalidad
	public void palabrasClave() {
		switch (mensajeReal) {
		case "ADIOS":
			conectado=false;
			break;
		case "HOLAA":
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
		dis.close();
		dos.close();
		conectado = false;
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
				enviarMensaje(MensajeChat.mensaje);
				MensajeChat.estadoMensaje=false;
			}			
		}
	}

}
