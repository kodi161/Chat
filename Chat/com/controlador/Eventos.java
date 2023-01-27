package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.ConexionCliente;
import vista.VentanaCliente;

public class Eventos implements ActionListener{
	
	VentanaCliente v;
	
	public Eventos(VentanaCliente vc) {
		v=vc;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==v.botonEnviar) {
			String usuarioNombre =v.getTextNombreUsuario();
			String mensaje =v.getTextMensaje();
			ConexionCliente cc = ConexionCliente.getInstancia();
			cc.enviarMensajeServidor(mensaje, usuarioNombre);
		}
	}

}
