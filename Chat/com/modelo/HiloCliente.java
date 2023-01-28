package modelo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
			System.out.println("Ha salido un cliente");
			cierreConexion();
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
	//Hace que el servidor envie un mensaje al cliente
	public void enviarMensaje(String mensaje) {
		try {
			for (int i = 0; i < SocketServidorChat.arrayHiloClientes.length; i++) {
				if(SocketServidorChat.arrayHiloClientes[i] != null &&
						SocketServidorChat.arrayHiloClientes[i].isAlive()) {
					SocketServidorChat.arrayHiloClientes[i].getDataOutputStream().writeUTF(mensaje);
				}
			}
		}catch (SocketException e) {
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
			mensajeRecibido = dis.readUTF().trim();//Recibe el mensaje
			obtenerNick();
			obtenerMensajeReal();//Método 3
			palabrasClave();//Método 4
			MensajeChat.mensajeActualizado(mensajeRecibido);
			System.out.println(mensajeRecibido);
		}catch (SocketException e) {
			cierreConexion();
		} catch (IOException e) {
			e.printStackTrace();
			cierreConexion();
		}
	}
	//Método 3:
		//Obtiene el nick del usuario
		public void obtenerNick() {
			for (int i = 0; i < mensajeRecibido.length(); i++) {
				if(mensajeRecibido.charAt(i)==':') {
					nickUsuario = mensajeRecibido.substring(0, i);
					System.out.println(nickUsuario);
				}
			}
		}
		
	//Método 4:
	//Obtiene el mensaje que ha enviado el cliente pero sin su nick
	public void obtenerMensajeReal() {
		for (int i = 0; i < mensajeRecibido.length(); i++) {
			if(mensajeRecibido.charAt(i)==':') {
				mensajeReal = mensajeRecibido.substring(i+1);
				System.out.println(mensajeReal);
			}
		}
	}
	
	

	//Metodo 5:
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

	//Método 6:
	//Se cierra la conexion
	public void cierreConexion() {
		try {
			csocket.close();
			dis.close();
			dos.close();
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
				enviarMensaje(MensajeChat.mensaje);
				MensajeChat.estadoMensaje=false;
			}			
		}
		cierreConexion();
	}

}
