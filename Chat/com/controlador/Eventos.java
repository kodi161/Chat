package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.SocketCliente;
import vista.VentanaCliente;

public class Eventos implements ActionListener{
	
	SocketCliente scliente;
	VentanaCliente v;
	
	public Eventos(SocketCliente sc, VentanaCliente vc) {
		scliente=sc;
		v=vc;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==v.botonEnviar) {
			String usuarioNombre =v.getTextNombreUsuario();
			String mensaje =v.getTextMensaje();
			scliente.enviarMensajeServidor(mensaje, usuarioNombre);
		}
	}

}
