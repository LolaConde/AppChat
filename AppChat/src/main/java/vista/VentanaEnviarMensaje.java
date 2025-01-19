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
		btnEscribirATlf.addActionListener(ev -> {
			FuncionesVentanas.irAVentana(this, new EnviarMensajeATelefono());
		});
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

}
