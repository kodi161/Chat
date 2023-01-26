package modelo;

public class MensajeChat {
	static String mensaje;
	static boolean estadoMensaje=false;
	private MensajeChat() {

	}
	
	//Método1:
	//Cambia el mensaje y el estado
	public static void  mensajeActualizado(String mensaje) {
		MensajeChat.mensaje=mensaje;
		estadoMensaje=true;
	}

}
