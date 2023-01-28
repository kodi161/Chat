package controlador;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JOptionPane;
import modelo.Cliente;
import vista.VentanaCliente;

public class Eventos implements ActionListener{
	
	VentanaCliente v;
	
	public Eventos(VentanaCliente vc) {
		v=vc;
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String nombre = v.getJtNombre().getText();
		
		/**
		 * este condicional crea el cliente cuando el usuario
		 * ingrese su nombre.
		 */
		if((v.getBtnEntrar() == e.getSource() || v.getJtNombre() == e.getSource())&& !nombre.equals("") )
		{
			v.setCliente(new Cliente());
			v.getJlnombre().setVisible(false);
			v.getJtNombre().setVisible(false);
			v.getBtnEntrar().setVisible(false);
			
			v.setSize(300,430);
			v.setLayout(new FlowLayout());
			
			
			v.add(v.getJsp());
			v.add(v.getJtf());
			v.add(v.getJbtn());
			v.setTitle(nombre);
		}
		
		/**
		 * Este condicional solo funciona cuando un cliente envia un mensaje
		 */
		else if(v.getJbtn()== e.getSource() || v.getJtf()== e.getSource())
		{
			v.setMensaje(v.getJtf().getText());
			
			if(!v.getMensaje().equals(""))
			{
				try {
					v.getCliente().prueba(nombre + "-->" +v.getMensaje());
					v.getJtf().setText("");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Por favor llene los campos");
			}

		}
		else
		{
			JOptionPane.showMessageDialog(null, "Ingrese el nombre de usuario");
		}
	}
	
	

}
