package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.UIManager;

import controlador.Controlador;

import javax.swing.JPasswordField;
import java.awt.Color;

public class VentanaLogin {

	private JFrame frame;
	private JTextField telfTxtField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaLogin window = new VentanaLogin();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaLogin() {
		initialize();
	}
	
	public void mostrarVentana() {
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	public void mostrarVentana(JFrame f) {
		frame.setLocationRelativeTo(f);
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		FuncionesVentanas.addIcon(frame);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Crear título
		JLabel tituloTxt = new JLabel("AppChat");
		tituloTxt.setHorizontalAlignment(SwingConstants.CENTER);
		tituloTxt.setFont(new Font("Tahoma", Font.PLAIN, 30));
		frame.getContentPane().add(tituloTxt, BorderLayout.NORTH);
		
		frame.getContentPane().add(panelRegistro(), BorderLayout.SOUTH);
		frame.getContentPane().add(panelLogin(), BorderLayout.CENTER);
	}
	
	private JPanel panelRegistro() {
		JPanel panelRegistro = new JPanel();
		
		// Campo texto incentivando registro
		JLabel registrateTxt = new JLabel("¿Aún no tienes una cuenta?");
		registrateTxt.setForeground(new Color(128, 128, 128));
		panelRegistro.add(registrateTxt);
		
		// Campo botón registro
		JButton botonRegistrar = new JButton("Registrar");
		addManejadorIrAlRegistro(botonRegistrar);
		botonRegistrar.setBackground(FuncionesVentanas.COLOR_ROSA);
		botonRegistrar.setPreferredSize(new Dimension(90, 21));
		panelRegistro.add(botonRegistrar);
		
		return panelRegistro;
	}
	
	private JPanel panelLogin() {
		JPanel panelLogin = new JPanel();
		GridBagLayout gbl_panelLogin = new GridBagLayout();
		gbl_panelLogin.columnWidths = new int[]{0, 0, 200, 0, 15, 0};
		gbl_panelLogin.rowHeights = new int[]{15, 0, 0, 15, 0, 0, 0};
		gbl_panelLogin.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panelLogin.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panelLogin.setLayout(gbl_panelLogin);
		
		// Introduce el teléfono
		JLabel telfTxt = new JLabel("Teléfono:");
		GridBagConstraints gbc_telfTxt = new GridBagConstraints();
		gbc_telfTxt.insets = new Insets(0, 0, 5, 5);
		gbc_telfTxt.anchor = GridBagConstraints.NORTHEAST;
		gbc_telfTxt.gridx = 1;
		gbc_telfTxt.gridy = 1;
		panelLogin.add(telfTxt, gbc_telfTxt);
		
		telfTxtField = new JTextField();
		FuncionesVentanas.addManejadorFieldTelefono(telfTxtField);
		GridBagConstraints gbc_telfTxtField = new GridBagConstraints();
		gbc_telfTxtField.fill = GridBagConstraints.HORIZONTAL;
		gbc_telfTxtField.insets = new Insets(0, 0, 5, 5);
		gbc_telfTxtField.gridx = 2;
		gbc_telfTxtField.gridy = 1;
		panelLogin.add(telfTxtField, gbc_telfTxtField);
		telfTxtField.setColumns(20);
		
		// Introduce la contraseña
		JLabel passTxt = new JLabel("Contrase\u00F1a:");
		GridBagConstraints gbc_passTxt = new GridBagConstraints();
		gbc_passTxt.anchor = GridBagConstraints.EAST;
		gbc_passTxt.insets = new Insets(0, 0, 5, 5);
		gbc_passTxt.gridx = 1;
		gbc_passTxt.gridy = 2;
		panelLogin.add(passTxt, gbc_passTxt);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(20);
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 2;
		gbc_passwordField.gridy = 2;
		panelLogin.add(passwordField, gbc_passwordField);
		
		JLabel mostrar = FuncionesVentanas.opcionMostrarPassword(passwordField);
		GridBagConstraints gbc_Mostrar = new GridBagConstraints();
		gbc_Mostrar.insets = new Insets(0, 0, 5, 5);
		gbc_Mostrar.gridx = 3;
		gbc_Mostrar.gridy = 2;
		panelLogin.add(mostrar, gbc_Mostrar);
		
		// Botón login
		JButton botonLogin = new JButton("Login");
		botonLogin.setForeground(new Color(0, 0, 0));
		botonLogin.setBackground(FuncionesVentanas.COLOR_ROSA);
		addManejadorHacerLogin(botonLogin);
		GridBagConstraints gbc_botonLogin = new GridBagConstraints();
		gbc_botonLogin.gridwidth = 3;
		gbc_botonLogin.insets = new Insets(0, 0, 5, 5);
		gbc_botonLogin.gridx = 1;
		gbc_botonLogin.gridy = 4;
		panelLogin.add(botonLogin, gbc_botonLogin);
		botonLogin.setPreferredSize(new Dimension(90, 21));
		
		return panelLogin;
	}

	
	private void addManejadorIrAlRegistro(JButton button) {
		button.addActionListener(ev -> {
			JFrame ventanaRegistro = new VentanaRegistro();
			ventanaRegistro.setVisible(true);
			ventanaRegistro.setLocationRelativeTo(frame);
			frame.dispose();
		});
	}
	
	private void addManejadorHacerLogin(JButton button) {
		button.addActionListener(ev -> {
			if(this.telfTxtField.getText().equals("") || passwordField.getPassword().length == 0) {
				JOptionPane.showMessageDialog(frame, "Debes introducir el teléfono y la contraseña");
			}
			else {
				int telf = Integer.parseInt(this.telfTxtField.getText());
				String passw = new String(this.passwordField.getPassword());
				if(Controlador.getUnicaInstancia().login(telf, passw)) {
					FuncionesVentanas.irAVentana(frame, new VentanaPrincipal());
				} else {
					JOptionPane.showMessageDialog(frame, "Error de login");
				}
			}
		});
	}

}
