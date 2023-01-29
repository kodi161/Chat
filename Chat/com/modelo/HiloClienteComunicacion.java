package modelo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
/*
 * Seguramente el nombre no explique bien lo que hace esta clase
 * Lo primero esta clase representa un hilo al extender de Thread
 * Esta clase está pensada para permitir la comunicación entre el Servidor
 * y un cliente.
 * Recibe los datos del cliente, pero utiliza el método del servidor que envia el mensaje 
 * al resto de clientes
 * Cada cliente que se conecte  tendra una clase de estas asociado a él.
 * 
 */
public class HiloClienteComunicacion extends Thread{

	//Atributos Simples
	private Socket socketCliente;
	private ServidorChat servidor;
	private DataOutputStream dos;
	private BufferedReader dis;
	Cliente cliente;



	//CONSTRUCTOR
	public HiloClienteComunicacion(Socket socketCliente, ServidorChat servidor) {
		super();
		this.socketCliente = socketCliente;
		this.servidor = servidor;

		try {
			dis = new BufferedReader(new InputStreamReader(this.socketCliente.getInputStream()));

			dos = new DataOutputStream(this.socketCliente.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	//GETTERS Y SETTERS
	public DataOutputStream getDataOutputStream() {
		return dos;
	}
	public Socket getSocketCliente() {
		return socketCliente;
	}

	public void setSocketCliente(Socket sc) {
		socketCliente = sc;
	}

	public ServidorChat getServidor() {
		return servidor;
	}

	public void setServidorChat(ServidorChat servidor) {
		this.servidor = servidor;
	}

	//Envia un mensaje a un cliente y un boolean para saber si ha habido algun problema
	public boolean enviarMensaje(String mensaje) {
		if (socketCliente.isClosed()==false) {
			try {
				dos.writeBytes( mensaje + "\n");
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	//Obtiene el nick del usuario
	public String obtenerNick(String mensaje) {
		String nickUsuario = null;
		for (int i = 0; i < mensaje.length(); i++) {
			if(mensaje.charAt(i)=='>') {
				nickUsuario = mensaje.substring(0, i+1);
				return nickUsuario;
			}
		}
		return nickUsuario;
	}

	//Obtiene el mensaje que ha enviado el cliente pero sin su nick
	public String obtenerMensajeReal(String mensaje) {
		String mensajeReal = null;
		for (int i = 0; i < mensaje.length(); i++) {
			if(mensaje.charAt(i)=='>') {
				mensajeReal = mensaje.substring(i+1);
				return mensajeReal;
			}
		}
		return mensajeReal;
	}


	//Permite que ciertas palabras claves tengan una cierta funcionalidad
	public String palabrasClave(String mensaje) {
		String mensajeEnviado = null;
		switch (mensaje) {

		case "HOLA":
			mensajeEnviado="HOOLAAAAAAAAAAAAAAAAAAAAAAAAAAA!!!";
			return mensajeEnviado;
		case "CARA1":
			mensajeEnviado=":)";
			return mensajeEnviado;
		case "CARA2":
			mensajeEnviado=":(";
			return mensajeEnviado;
		case "ADIOS":
			mensajeEnviado="ya no esta conectado";
			return mensajeEnviado;
		default:
			mensajeEnviado=mensaje;
			return mensajeEnviado;
		}
	}


	@Override
	public void run() {
		super.run();
		boolean activo = true;
		while (activo) {
			if (socketCliente.isClosed()) {
				activo = false;
				continue;
			} else {
				try {
					String mensaje = dis.readLine();//Recibimos el mensaje
					String nick = obtenerNick(mensaje.trim());//Guardamos el nick
					String mensajeReal = obtenerMensajeReal(mensaje.trim());//Obtenemos el mensaje sin nick
					String contenido = palabrasClave(mensajeReal);//Capatmos palabras claves y devolvemos una string
					String resultado = nick+contenido;//Mensaje final
					System.out.println(resultado);
					servidor.enviarMensajeTodos(resultado);
					if(contenido.equals("ya no esta conectado")) {
						socketCliente.close();
					}
				} catch (IOException e) {
					try {
						socketCliente.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		}
	}

}
