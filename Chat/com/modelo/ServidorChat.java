package modelo;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;


public class ServidorChat {

	public final static int puerto = 8025;
	private ArrayList<HiloClienteComunicacion> arrayHiloCliente;
	private ServerSocket socketEntrada;
	public static void main(String[] args) {
		new ServidorChat();
	}
	public ServidorChat() {
		arrayHiloCliente = new ArrayList<HiloClienteComunicacion>();
		try {
			socketEntrada = new ServerSocket(puerto);
			while(true) {
				Socket cliente = socketEntrada.accept();
				System.out.println("-------Conectado------");
				arrayHiloCliente.add(new HiloClienteComunicacion(cliente, this));
				arrayHiloCliente.get(arrayHiloCliente.size() -1).start();;

				for (int i = 0; i < arrayHiloCliente.size(); i++) {
					if (arrayHiloCliente.get(i).isAlive()==false) {
						arrayHiloCliente.remove(i);
					}
				}
			}
		} catch (BindException e) {
			System.out.println("El servidor se ha reiniciado");
		} catch (IOException e) {
			System.out.println("Problema 1");
			e.printStackTrace();
		} finally {
			try {
				if (socketEntrada != null) {
					socketEntrada.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public void enviarMensajeTodos(String mensaje) {
		for (Iterator<HiloClienteComunicacion> iterator = arrayHiloCliente.iterator(); iterator.hasNext();) {
			HiloClienteComunicacion hiloCliente = (HiloClienteComunicacion) iterator.next();
			if (hiloCliente.isAlive()) {
				boolean enviarmensaje = hiloCliente.enviarMensaje(mensaje);
				if (enviarmensaje=false) {
					iterator.remove();
				}
			}

		}
	}
}