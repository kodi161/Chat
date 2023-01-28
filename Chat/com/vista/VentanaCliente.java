package vista;


import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controlador.Eventos;
import modelo.ConexionCliente;

public class VentanaCliente extends JFrame{

	private static final long serialVersionUID = 1L;
	//Componentes
	JPanel p;
	JPanel p2;
	JLabel jlNombre;
	JTextField cajaNombre;
	JTextArea areaChat;
	JScrollPane jsPane;
	JLabel jlMensaje;
	JTextField cajaMensaje;
	public JButton botonEnviar;

	public static void main(String[] args) {
		VentanaCliente v1 = new VentanaCliente();
		try {
			Socket csocket = new Socket("localhost",8025);
			ConexionCliente cc1 = ConexionCliente.getInstancia();
			cc1.setSocket(csocket);
			do{
				cc1.recibirMensajes(v1);
			}while(cc1.isConectado());
			csocket.close();
		} catch (UnknownHostException e) {
			System.out.println("Ha habido algun problema con el host");
		} catch (IOException e) {
			System.out.println("No te has podido conectar al servidor");
		}
	}
	public VentanaCliente() {
		//Inicialización de componenetes de la ventana
		p = new JPanel();
		p2 = new JPanel();
		jlNombre = new JLabel("Nombre de usuario");
		cajaNombre = new JTextField(20);//Almacena el nombre del usuario
		areaChat = new JTextArea();//En el se muestra el chat
		JScrollPane jsp = new JScrollPane(areaChat,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jlMensaje = new JLabel("Mensaje");
		cajaMensaje = new JTextField(20);//Almacena el mensaje que se quiere enviar
		botonEnviar = new JButton("Enviar");

		//Configuracion de los componenetes
		areaChat.setEditable(false);//Permite que no se puede editar
		areaChat.setLineWrap(true);//Si llega alfinal del ancho del text area el texto se pone abajo
		
		//Layout
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(new BorderLayout());
		gbc.insets = new Insets(1,1,1,1);

		//Se añaden componenetes a los paneles
		addComponentesNombre(jlNombre, cajaNombre,areaChat, p, gbc);
		addComponentesMensaje(jlMensaje, cajaMensaje, botonEnviar, p2, gbc);
		
		//Eventos
		botonEnviar.addActionListener(new Eventos(this));
		
		//Se añaden componentes a la ventana
		add(p, BorderLayout.NORTH);
		add(jsp, BorderLayout.CENTER);
		add(p2, BorderLayout.SOUTH);

		//Configuración de la ventaba
		setSize(400, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	public void addComponentesNombre(JLabel etiqueta,
			JTextField caja, 
			JTextArea areaTexto,
			JPanel panel, 
			GridBagConstraints gbc) {
		panel.setComponentOrientation(getComponentOrientation());
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridwidth = GridBagConstraints.RELATIVE;
		panel.add(etiqueta);
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(caja);	
	}
	public void addComponentesMensaje(JLabel etiqueta,
			JTextField caja,
			JButton boton,
			JPanel panel,
			GridBagConstraints gbc) {
		panel.setComponentOrientation(getComponentOrientation());
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridwidth = GridBagConstraints.RELATIVE;
		panel.add(etiqueta);
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridwidth = GridBagConstraints.RELATIVE;
		panel.add(caja);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridwidth = GridBagConstraints.RELATIVE;
		panel.add(boton);
	}
	
	public void setTextChat(String str) {
		areaChat.append(str+"\n");
	}
	
	public String getTextNombreUsuario() {
		return cajaNombre.getText().trim();
	}
	public String getTextMensaje() {
		return cajaMensaje.getText().trim();
	}
}
