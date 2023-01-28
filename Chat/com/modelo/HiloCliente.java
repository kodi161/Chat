package modelo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class HiloCliente extends Thread{
	//Atributos Simples
	private Socket csocket;
	private DataOutputStream dos;
	private DataInputStream dis;
	private String mensajeRecibido;
	private String mensajeReal;
	private boolean conectado=true;
	private String nickUsuario;

	//Constructor
	public HiloCliente(Socket sc) {
		csocket=sc;
		try {
			dos = new DataOutputStream(csocket.getOutputStream());
			dis = new DataInputStream(csocket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	//Getter
	public DataOutputStream getDataOutputStream() {
		return dos;
	}
	//Hace que el servidor envie un mensaje al cliente
	public void enviarMensaje(String mensaje) {
		try {
			for (int i = 0; i < SocketServidorChat.arrayHiloClientes.length; i++) {
				if(SocketServidorChat.arrayHiloClientes[i] != null ) {
					System.out.println("Se ha enviado el mensaje al chat");
					SocketServidorChat.arrayHiloClientes[i].getDataOutputStream().writeUTF(MensajeChat.mensaje);				
				}
			}
		}catch (SocketException e) {
			cierreConexion();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Hace que el servidor reciba mensajes del cliente
	public void recibirMensaje() {
		try {
			mensajeRecibido = dis.readUTF();
			obtenerNick();
			obtenerMensajeReal();
			palabrasClave();
			MensajeChat.mensajeActualizado(mensajeRecibido);
			System.out.println(mensajeRecibido);
		}catch (EOFException e) {
			System.out.println("Ha habido algun problema al recibir el mensaje");
			conectado=false;
		}catch (SocketException e) {
			cierreConexion();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Obtiene el nick del usuario
	public void obtenerNick() {
		for (int i = 0; i < mensajeRecibido.length(); i++) {
			if(mensajeRecibido.charAt(i)==':') {
				nickUsuario = mensajeRecibido.substring(0, i);
			}
		}
	}

	//Obtiene el mensaje que ha enviado el cliente pero sin su nick
	public void obtenerMensajeReal() {
		for (int i = 0; i < mensajeRecibido.length(); i++) {
			if(mensajeRecibido.charAt(i)==':') {
				mensajeReal = mensajeRecibido.substring(i+2);
			}
		}
	}


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

	//Se cierra la conexion
	public void cierreConexion() {
		try {
			csocket.close();
			dos.close();
			dis.close();
			conectado = false;
			System.out.println("Se ha desconectado un cliente");
			
		} catch (Exception e) {
			conectado=false;
		}
	}

	@Override
	public synchronized void run() {
		do{
			//Comprueba si hay mensajes nuevos, TRUE es que si hay nuevos mensajes
			if(MensajeChat.estadoMensaje==false) {
				recibirMensaje();
				enviarMensaje(MensajeChat.mensaje);
				MensajeChat.estadoMensaje=false;
			}			
		}while(conectado);
		cierreConexion();
	}

}
