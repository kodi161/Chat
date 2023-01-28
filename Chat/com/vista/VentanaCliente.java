package vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controlador.Eventos;
import modelo.Cliente;


public class VentanaCliente extends JFrame{
	
	//COMPONENETES VENTENA
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

	public void setBtnEntrar(JButton btnEntrar) {
		this.btnEntrar = btnEntrar;
	}

	
	//constructor
	public VentanaCliente() {
		//se inicializan componentes que se agregaran en la interfaz
		jlnombre = new JLabel("Ingrese su nombre");
		jlnombre.setBounds(90, 10, 200, 30);
		
		jtNombre = new  JTextField();
		jtNombre.setBounds(50, 40, 200, 25);
		
		jta = new JTextArea(20,20);
	
		jbtn = new JButton("Enviar");
		
		jtf = new JTextField(20);
		jlab = new JLabel("Ingrese texto y luego oprima cambiar");
		
		jsp = new JScrollPane(jta,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		btnEntrar = new JButton("Entrar");
		btnEntrar.setBounds(90, 70, 100, 25);
		
		Eventos e = new Eventos(this);
		
		jbtn.addActionListener(e);
		jtf.addActionListener(e);
		jtNombre.addActionListener(e);
		jta.setEditable(false);
		
		btnEntrar.addActionListener(e);
		add(jlnombre);
		add(jtNombre);
		add(btnEntrar);
		
		
		setTitle("Cliente");
		setSize(300, 150);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
				
	}
	public void agregarLinea(String linea)
	{
		getJta().append(linea);
	}
	
}
