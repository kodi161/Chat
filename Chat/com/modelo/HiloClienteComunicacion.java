package modelo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class HiloClienteComunicacion extends Thread{
	//Atributos Simples
	private Socket socketCliente;
	private ServidorChat servidor;
	private DataOutputStream dos;
	private BufferedReader dis;
	Cliente cliente;

	//Constructor
	
	//Getter
	public DataOutputStream getDataOutputStream() {
		return dos;
	}
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
		case "ADIOS":
			mensajeEnviado="ya no esta conectado";
			return mensajeEnviado;
		case "HOLA":
			mensajeEnviado="HOOLAAAAAAAAAAAAAAAAAAAAAAAAAAA!!!";
			return mensajeEnviado;
		case "CORAZON":
			mensajeEnviado="<3 <3 <3 <3 <3 <3";
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
                    String mensaje = dis.readLine();
                    String nick = obtenerNick(mensaje.trim());
                    String mensajeReal = obtenerMensajeReal(mensaje.trim());
                    String contenido = palabrasClave(mensajeReal);
                    String resultado = nick+contenido;
                    System.out.println(resultado);
					servidor.enviarMensajeTodos(resultado);
					if(contenido.equals("ya nos esta conectado")) {
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
