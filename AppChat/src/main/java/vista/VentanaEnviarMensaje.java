package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controlador.Controlador;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JButton;

public class VentanaEnviarMensaje extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public VentanaEnviarMensaje() {
		setTitle("AppChat");
		FuncionesVentanas.addIcon(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 501, 190);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		// JLabel: ¿A quién deseas escribir?
		JLabel lblPregunta = new JLabel("¿A quién deseas escribir?");
		lblPregunta.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_lblPregunta = new GridBagConstraints();
		gbc_lblPregunta.gridwidth = 2;
		gbc_lblPregunta.insets = new Insets(0, 0, 5, 5);
		gbc_lblPregunta.gridx = 1;
		gbc_lblPregunta.gridy = 0;
		contentPane.add(lblPregunta, gbc_lblPregunta);
		
		// Botón escribir a contacto
		JButton btnEscribirAContacto = new JButton("Contacto o grupo");
		btnEscribirAContacto.setBackground(FuncionesVentanas.COLOR_ROSA);
		GridBagConstraints gbc_btnEscribirAContacto = new GridBagConstraints();
		gbc_btnEscribirAContacto.insets = new Insets(0, 0, 5, 5);
		gbc_btnEscribirAContacto.gridx = 1;
		gbc_btnEscribirAContacto.gridy = 2;
		btnEscribirAContacto.addActionListener(ev -> {
			FuncionesVentanas.irAVentana(this, new VentanaEnviarMensajeAContacto());
		});
		contentPane.add(btnEscribirAContacto, gbc_btnEscribirAContacto);
		
		// Botón escribir si no está en mis contactos
		JButton btnEscribirATlf = new JButton("Otro");
		btnEscribirATlf.setBackground(FuncionesVentanas.COLOR_ROSA);
		GridBagConstraints gbc_btnEscribirATlf = new GridBagConstraints();
		gbc_btnEscribirATlf.insets = new Insets(0, 0, 5, 5);
		gbc_btnEscribirATlf.gridx = 2;
		gbc_btnEscribirATlf.gridy = 2;
		addManejadorEscribirATelefono(btnEscribirATlf);
		contentPane.add(btnEscribirATlf, gbc_btnEscribirATlf);
		
		// Botón cancelar
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBackground(new Color(192, 192, 192));
		btnCancelar.addActionListener(ev -> {
			FuncionesVentanas.irAVentana(this, new VentanaPrincipal());
		});
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.gridwidth = 2;
		gbc_btnCancelar.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancelar.gridx = 1;
		gbc_btnCancelar.gridy = 4;
		contentPane.add(btnCancelar, gbc_btnCancelar);
	}
	
	private void addManejadorEscribirATelefono(JButton boton) {
		boton.addActionListener(ev -> {
			// Crea JOptionPane preguntando número y mensaje
			JTextField fieldNumero = new JTextField();
			JTextField fieldMensaje = new JTextField();
			Object[] inputs = {
					"Número: ", fieldNumero,
					"Mensaje: ", fieldMensaje,
			};
			
			int option = JOptionPane.showConfirmDialog(this, inputs, "Enviar mensaje", JOptionPane.OK_CANCEL_OPTION);
			
			if(option == JOptionPane.OK_OPTION) {
				String mensaje = fieldMensaje.getText();
				String stringNumero = fieldNumero.getText();
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
			}
		});
	}

}
