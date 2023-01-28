package modelo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import vista.VentanaCliente;

public class ConexionCliente {
	//Atributos
	private static ConexionCliente cc1 = new ConexionCliente();
	private Socket csocketCliente;
	private String nombreUsuario;
	private String mensajeRecibido="";
	private String mensajeEnviado="";
	private boolean conectado=true;
	
	//Obtener Instancia
	public  static ConexionCliente getInstancia() {
		return cc1;
	}

	//Getters
	public boolean isConectado() {
		conectado= csocketCliente.isConnected();
		return conectado;
	}

	//SETTERS
	public void setSocket(Socket socket) {
		csocketCliente = socket;
	}
	
	//Constructor
	private ConexionCliente() {

	}

	//Metodo 1:
	//El socket esta pendiente en todo momento para recibir un mensaje
	public void recibirMensajes(VentanaCliente vc) {
		//Entrada de datos
		DataInputStream dis;
		try {
			dis = new DataInputStream(csocketCliente.getInputStream());
			mensajeRecibido = dis.readUTF().trim();
			//vc.setTextChat(mensajeRecibido);
		} catch (SocketException e) {
			conectado = false;
		}catch (IOException e) {
			e.printStackTrace();
			conectado=false;
		}
	}

	//Metodo 2:
	//Se envia un mensaje al servidor
	public void enviarMensajeServidor(String mensajeEnviado, String nombreUsuario) {
		//Salida de datos
		DataOutputStream dos;
		try {
			if (conectado && (csocketCliente != null)) {
				dos = new DataOutputStream(csocketCliente.getOutputStream());
				this.mensajeEnviado = mensajeEnviado.trim();
				this.nombreUsuario= nombreUsuario;
				String mensajeYnombre = this.nombreUsuario+": "+this.mensajeEnviado;
				dos.writeUTF(mensajeYnombre);
			}else {
				System.out.println("No se ha podido enviar el mensaje");
			}
		} catch (SocketException e) {
			System.out.println("El socket no esta funcionando correctamente");
			conectado=false;
		} catch (IOException e) {
			e.printStackTrace();
			conectado=false;
		}
	}
}
