package modelo;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class HiloClienteComunicacion extends Thread{
	//Atributos Simples
	private Socket socketCliente;
	private ServidorChat servidor;
	private DataOutputStream dos;
	private BufferedReader dis;
	private String mensajeRecibido;
	private String nickUsuario;
	//Cliente cliente;
	private String mensajeReal;

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
	public void obtenerNick() {
		for (int i = 0; i < mensajeRecibido.length(); i++) {
			if(mensajeRecibido.charAt(i)=='>') {
				nickUsuario = mensajeRecibido.substring(0, i);
			}
		}
	}

	//Obtiene el mensaje que ha enviado el cliente pero sin su nick
	public void obtenerMensajeReal() {
		for (int i = 0; i < mensajeRecibido.length(); i++) {
			if(mensajeRecibido.charAt(i)=='>') {
				mensajeReal = mensajeRecibido.substring(i+2);
			}
		}
	}


	//Permite que ciertas palabras claves tengan una cierta funcionalidad
	public void palabrasClave() {
		switch (mensajeRecibido) {
		case "ADIOS":
			mensajeRecibido=nickUsuario+"ya no esta conectado";
			try {
				socketCliente.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "HOLA":
			mensajeRecibido = nickUsuario+"HOOLAAAAAAAAAAAAAAAAAAAAAAAAAAA!!!";
			break;
		case "PIRAMIDE":
			mensajeRecibido= nickUsuario+":\n* \n **\n *** \n*****\n";
			break;
		default:
			break;
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
                    obtenerNick();
                    obtenerMensajeReal();
                    mensajeRecibido = mensaje;
                    if (mensaje != null) {
                        System.out.println( mensajeRecibido);
                        servidor.enviarMensajeTodos(mensajeRecibido);
                    } else {
                    	//No pasa nada
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
