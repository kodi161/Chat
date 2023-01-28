package vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class VentanaCliente extends JFrame{

	private JTextArea jta;
	private JScrollPane jsp;
	private JButton jbtn;
	private JTextField jtf;
	private JLabel jlab;
	//private Cliente cliente;
	private String mensaje="";
	private JLabel jlnombre;
	private JTextField jtNombre;
	private JButton btnEntrar;
	
	
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
		
		add(jlnombre);
		add(jtNombre);
		add(btnEntrar);
		
		
		setTitle("Cliente");
		setSize(300, 150);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
				
	}
}
