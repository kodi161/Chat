package modelo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketCliente {
	//Atributos
	private Socket csocketCliente;
	private int puerto=8025;
	private String direccion = "localhost";
	private String nombreUsuario;
	private String mensajeRecibido="";
	private String mensajeEnviado="";
	private boolean conectado=true;
	
	//Getters
		public Socket getCsocketCliente() {
			return csocketCliente;
		}

		public int getPuerto() {
			return puerto;
		}

		public String getDireccion() {
			return direccion;
		}

		public String getMensajeRecibido() {
			return mensajeRecibido;
		}

		public String getMensajeEnviado() {
			return mensajeEnviado;
		}

		public boolean isConectado() {
			return conectado;
		}
		
	//Constructor
	public SocketCliente() {
		try {
			//Inicialización del socket
			csocketCliente = new Socket(direccion, puerto);
			recivirConstatntementeMensajes();//Metodo 1
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//Metodo 1:
	//El socket esta pendiente en todo momento para recibir un mensaje
	public void recivirConstatntementeMensajes() {
		while(conectado) {
			//Entrada de datos
			DataInputStream dis;
			try {
				dis = new DataInputStream(csocketCliente.getInputStream());
				mensajeRecibido = dis.readUTF().trim();
				dis.close();
			} catch (IOException e) {
				conectado=false;
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//Metodo 2:
	//Se envia un mensaje al servidor
	public void enviarMensajeServidor(String mensajeEnviado, String nombreUsuario) {
		//Salida de datos
		DataOutputStream dos;
		try {
			dos = new DataOutputStream(csocketCliente.getOutputStream());
			this.mensajeEnviado = mensajeEnviado.trim();
			this.nombreUsuario= nombreUsuario;
			String mensajeYnombre = this.nombreUsuario+": "+this.mensajeEnviado;
			dos.writeUTF(mensajeYnombre);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
