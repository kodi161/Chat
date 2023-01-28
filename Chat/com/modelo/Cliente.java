package modelo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import vista.VentanaCliente;

public class Cliente 
{
	//hacer una interfaz
	public static int PORT = 8025;
	public static String SERVER_LOCATION = "localhost";

	private static Socket socketCliente;

	private static DataOutputStream salidaDatos;
	private BufferedReader entradaDatos;


	static VentanaCliente vc; 

	public static void main(String[] args) {
		vc = new VentanaCliente();
	}


	public Cliente() {
		try {
			socketCliente = new Socket(Cliente.SERVER_LOCATION, Cliente.PORT);
			entradaDatos = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
			salidaDatos = new DataOutputStream(socketCliente.getOutputStream());
			//se crea un hilo que se va a ejecutar en paralelo

			Thread escuchador = new Thread(new Runnable() {

				@Override
				public void run() 
				{
					while(true)
					{
						try {
							if(!socketCliente.isClosed() && entradaDatos.ready())
							{
								String mensaje = entradaDatos.readLine();

								vc.agregarLinea(mensaje+"\n");
								//System.out.println("Mensaje entrante> " + mensaje);

							}
						} catch (IOException e) {

							e.printStackTrace();
							break;//rompe el hilo
						}
					}
				}
			});
			escuchador.start(); //arrancamos el hilo

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}




	}


	/**
	 * Este metodo le envia los mensajes al servidor
	 * @param mensaje
	 * @throws IOException
	 */

	public  void prueba(String mensaje) throws IOException
	{

		if(!socketCliente.isClosed() && !mensaje.equals(""))
		{	
			salidaDatos.writeBytes(mensaje+'\n');
		}
	}

}

