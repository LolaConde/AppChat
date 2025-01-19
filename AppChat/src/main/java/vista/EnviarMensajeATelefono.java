package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controlador.Controlador;
import modelo.Contacto;

public class EnviarMensajeATelefono extends JFrame {

	private JPanel contentPane;
	private JTextField fieldTelefono;
	private JTextField fieldMensaje;

	/**
	 * Create the frame.
	 */
	public EnviarMensajeATelefono() {
		setTitle("AppChat");
		FuncionesVentanas.addIcon(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		contentPane.add(panelDatos());
		contentPane.add(panelEnviarCancelar(), BorderLayout.SOUTH);
	}
	
	private JPanel panelEnviarCancelar() {
		JPanel panelEnviarCancelar = new JPanel();
		panelEnviarCancelar.setLayout(new BoxLayout(panelEnviarCancelar, BoxLayout.X_AXIS));
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panelEnviarCancelar.add(horizontalGlue);
		
		// Botón cancelar
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBackground(new Color(192, 192, 192));
		btnCancelar.addActionListener(ev -> {
			FuncionesVentanas.irAVentana(this, new VentanaEnviarMensaje());
		});
		panelEnviarCancelar.add(btnCancelar);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panelEnviarCancelar.add(horizontalGlue_1);
		
		// Botón enviar
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.setBackground(FuncionesVentanas.COLOR_ROSA);
		btnEnviar.addActionListener(ev -> {
			String mensaje = fieldMensaje.getText();
			String stringNumero = fieldTelefono.getText();
			if(mensaje.length() == 0) {
				JOptionPane.showMessageDialog(this, "No se ha podido enviar el mensaje, el mensaje no puede estar vacío", "Error con el mensaje", JOptionPane.ERROR_MESSAGE);
			} else if(!stringNumero.matches("^[0-9]+$")) {
				JOptionPane.showMessageDialog(this, "No se ha podido enviar el mensaje, el número debe contener números y ningún otro caracter", "Error con el número", JOptionPane.ERROR_MESSAGE);
			} else {
				int telefono = Integer.parseInt(stringNumero);
				boolean existeNumero = Controlador.getUnicaInstancia().enviarMensaje(telefono, mensaje);
				if(existeNumero) {
					// Campos correctos. Mensaje se envía
					JOptionPane.showMessageDialog(this, "Mensaje enviado", "Información", JOptionPane.INFORMATION_MESSAGE);
					FuncionesVentanas.irAVentana(this, new VentanaPrincipal());
				}
				else {
					JOptionPane.showMessageDialog(this, "El teléfono indicado no existe", "Alert", JOptionPane.ERROR_MESSAGE);
				}
			}
			
		});
		panelEnviarCancelar.add(btnEnviar);
		
		Component horizontalGlue_2 = Box.createHorizontalGlue();
		panelEnviarCancelar.add(horizontalGlue_2);
		
		return panelEnviarCancelar;
	}
	
	private JPanel panelDatos() {
		JPanel panelDatos = new JPanel();
		panelDatos.setLayout(new BoxLayout(panelDatos, BoxLayout.Y_AXIS));
		
		panelDatos.add(Box.createGlue());
		// Introducir el numero
		JPanel panelNumero = new JPanel();
		panelDatos.add(panelNumero);
		panelNumero.setLayout(new BoxLayout(panelNumero, BoxLayout.X_AXIS));
		
		JLabel lblNumero = new JLabel("Número:");
		panelNumero.add(lblNumero);
		
		fieldTelefono = new JTextField();
		fieldTelefono.setMaximumSize(new Dimension(2147483647, 20));
		panelNumero.add(fieldTelefono);
		
		// Introducir el mensaje
		fieldMensaje = new JTextField();
		panelDatos.add(FuncionesVentanas.panelTexto("Mensaje :", fieldMensaje, this));
		fieldMensaje.setMaximumSize(new Dimension(2147483647, 20));
		
		panelDatos.add(Box.createGlue());
		return panelDatos;
	}

}
