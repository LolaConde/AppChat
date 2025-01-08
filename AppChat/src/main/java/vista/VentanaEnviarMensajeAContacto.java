package vista;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.Controlador;
import modelo.Contacto;
import modelo.ContactoIndividual;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JButton;

public class VentanaEnviarMensajeAContacto extends JFrame {

	private JPanel contentPane;
	private JTextField fieldMensaje;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaEnviarMensajeAContacto frame = new VentanaEnviarMensajeAContacto();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaEnviarMensajeAContacto() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelDatos = new JPanel();
		contentPane.add(panelDatos);
		panelDatos.setLayout(new BoxLayout(panelDatos, BoxLayout.Y_AXIS));
		
		JPanel panelContacto = new JPanel();
		panelDatos.add(panelContacto);
		panelContacto.setLayout(new BoxLayout(panelContacto, BoxLayout.X_AXIS));
		
		JLabel lblContacto = new JLabel("Contacto:");
		panelContacto.add(lblContacto);
		
		JScrollPane scrollPaneContacto = new JScrollPane();
		panelContacto.add(scrollPaneContacto);
		
		DefaultListModel<Contacto> modelo = new DefaultListModel<>();
		Controlador.getUnicaInstancia().getContactos().stream().forEach(modelo::addElement);
		
		JList<Contacto> listContactos = new JList<Contacto>(modelo);
		listContactos.setBackground(new Color(238, 238, 238));
        listContactos.setCellRenderer(new ContactoCellRenderer());
        listContactos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		scrollPaneContacto.setViewportView(listContactos);
		
		JPanel panelMensaje = new JPanel();
		panelDatos.add(panelMensaje);
		panelMensaje.setLayout(new BoxLayout(panelMensaje, BoxLayout.X_AXIS));
		
		JLabel lblMensaje = new JLabel("Mensaje:");
		panelMensaje.add(lblMensaje);
		
		fieldMensaje = new JTextField();
		panelMensaje.add(fieldMensaje);
		fieldMensaje.setColumns(10);
		
		JPanel panelEnviarCancelar = new JPanel();
		contentPane.add(panelEnviarCancelar, BorderLayout.SOUTH);
		panelEnviarCancelar.setLayout(new BoxLayout(panelEnviarCancelar, BoxLayout.X_AXIS));
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panelEnviarCancelar.add(horizontalGlue);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBackground(new Color(192, 192, 192));
		btnCancelar.addActionListener(ev -> {
			FuncionesVentanas.irAVentana(this, new VentanaEnviarMensaje());
		});
		panelEnviarCancelar.add(btnCancelar);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panelEnviarCancelar.add(horizontalGlue_1);
		
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.setBackground(FuncionesVentanas.COLOR_ROSA);
		btnEnviar.addActionListener(ev -> {
			Contacto contacto = listContactos.getSelectedValue();
			String mensaje = fieldMensaje.getText();
			if(contacto == null) {
				JOptionPane.showMessageDialog(this, "No se ha podido enviar el mensaje, debe seleccionar un contacto", "Error", JOptionPane.ERROR_MESSAGE);
			} else if(mensaje.length() == 0) {
				JOptionPane.showMessageDialog(this, "No se ha podido enviar el mensaje, el mensaje no puede estar vacío", "Error con el mensaje", JOptionPane.ERROR_MESSAGE);
			} else {
				Controlador.getUnicaInstancia().enviarMensaje(contacto, mensaje);
				JOptionPane.showMessageDialog(this, "Mensaje enviado", "Información", JOptionPane.INFORMATION_MESSAGE);
				FuncionesVentanas.irAVentana(this, new VentanaPrincipal());
			}
			
		});
		panelEnviarCancelar.add(btnEnviar);
		
		Component horizontalGlue_2 = Box.createHorizontalGlue();
		panelEnviarCancelar.add(horizontalGlue_2);
	}

}
