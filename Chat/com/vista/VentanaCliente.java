package vista;

import java.awt.Component;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controlador.Eventos;
import modelo.Cliente;


public class VentanaCliente extends JFrame{

	private static final long serialVersionUID = 1L;//Para que no ponga un aviso eclipse, se puede quitar
	//COMPONENTES VENTENA
	private JTextArea jta;
	private JScrollPane jsp;
	private JButton jbtn;
	private JTextField jtf;
	private JLabel jlab;
	private Cliente cliente;
	private String mensaje="";
	private JLabel jlnombre;
	private JTextField jtNombre;
	private JButton btnEntrar;

	//GETTERS Y SETTERS
	/*
	 * Son eseciales para obtener o establecer datos en la ventana del cliente	
	 */
	public JTextArea getJta() {
		return jta;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void setJta(JTextArea jta) {
		this.jta = jta;
	}

	public JScrollPane getJsp() {
		return jsp;
	}

	public void setJsp(JScrollPane jsp) {
		this.jsp = jsp;
	}

	public JButton getJbtn() {
		return jbtn;
	}

	public void setJbtn(JButton jbtn) {
		this.jbtn = jbtn;
	}

	public JTextField getJtf() {
		return jtf;
	}

	public void setJtf(JTextField jtf) {
		this.jtf = jtf;
	}

	public JLabel getJlab() {
		return jlab;
	}

	public void setJlab(JLabel jlab) {
		this.jlab = jlab;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public JLabel getJlnombre() {
		return jlnombre;
	}

	public void setJlnombre(JLabel jlnombre) {
		this.jlnombre = jlnombre;
	}

	public JTextField getJtNombre() {
		return jtNombre;
	}

	public void setJtNombre(JTextField jtNombre) {
		this.jtNombre = jtNombre;
	}

	public JButton getBtnEntrar() {
		return btnEntrar;
	}

	//constructor
	public VentanaCliente() {
		/*
		 * Se inicializan componentes que se agregaran en la interfaz
		 * y configuramos los atributos de los componentes
		 */

		jlnombre = new JLabel("Ingrese su nombre");
		jlnombre.setBounds(90, 10, 200, 30);

		jtNombre = new  JTextField();
		jtNombre.setBounds(50, 40, 200, 25);

		jta = new JTextArea(20,20);
		jta.setLineWrap(true);//El texto se adapta al textarea
		jta.setEditable(false);

		jbtn = new JButton("Enviar");

		jtf = new JTextField(20);
		jlab = new JLabel("Ingrese texto y luego oprima cambiar");

		jsp = new JScrollPane(jta,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		btnEntrar = new JButton("Entrar");
		btnEntrar.setBounds(90, 70, 100, 25);

		//Layout
		setLayout(null);


		//Eventos
		Eventos e = new Eventos(this);

		jbtn.addActionListener(e);
		jtf.addActionListener(e);
		jtNombre.addActionListener(e);
		btnEntrar.addActionListener(e);

		//Componenetes que se añaden al crear la ventana
		add(jlnombre);
		add(jtNombre);
		add(btnEntrar);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Cliente");
		setSize(300, 150);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);

	}

	//GridbagLayout configura la ventana del principio
	public void addComponenetesPanel(JPanel panel,
			Object obj, GridBagConstraints gbc) {
		gbc.gridwidth = GridBagConstraints.CENTER;
		gbc.gridheight = GridBagConstraints.REMAINDER;
		panel.add((Component) obj);
	}


	//Permite añadir texto al textare de la venta del usuario
	public void agregarLinea(String linea)
	{
		getJta().append(linea);
	}

}
