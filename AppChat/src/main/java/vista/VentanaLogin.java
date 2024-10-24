package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.ImageIcon;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Insets;
import javax.swing.UIManager;
import javax.swing.JPasswordField;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VentanaLogin {

	private JFrame frame;

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

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(new ImageIcon(VentanaLogin.class.getResource("/vista/imagenes/icono-appchat.png")).getImage());
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
		botonRegistrar.setBackground(new Color(247, 210, 249));
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
		
		// Campo teléfono: Texto
		JLabel telfTxt = new JLabel("Teléfono:");
		GridBagConstraints gbc_telfTxt = new GridBagConstraints();
		gbc_telfTxt.insets = new Insets(0, 0, 5, 5);
		gbc_telfTxt.anchor = GridBagConstraints.NORTHEAST;
		gbc_telfTxt.gridx = 1;
		gbc_telfTxt.gridy = 1;
		panelLogin.add(telfTxt, gbc_telfTxt);
		
		// Campo teléfono: field
		JTextField telfTxtField = new JTextField();
		addManejadorLabelPassword(telfTxtField);
		GridBagConstraints gbc_telfTxtField = new GridBagConstraints();
		gbc_telfTxtField.fill = GridBagConstraints.HORIZONTAL;
		gbc_telfTxtField.insets = new Insets(0, 0, 5, 5);
		gbc_telfTxtField.gridx = 2;
		gbc_telfTxtField.gridy = 1;
		panelLogin.add(telfTxtField, gbc_telfTxtField);
		telfTxtField.setColumns(20);
		
		// Campo contraseña: Texto
		JLabel passTxt = new JLabel("Contrase\u00F1a:");
		GridBagConstraints gbc_passTxt = new GridBagConstraints();
		gbc_passTxt.anchor = GridBagConstraints.EAST;
		gbc_passTxt.insets = new Insets(0, 0, 5, 5);
		gbc_passTxt.gridx = 1;
		gbc_passTxt.gridy = 2;
		panelLogin.add(passTxt, gbc_passTxt);
		
		// Campo contraseña: field
		final JPasswordField passwordField = new JPasswordField();
		passwordField.setColumns(20);
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 2;
		gbc_passwordField.gridy = 2;
		panelLogin.add(passwordField, gbc_passwordField);
		
		// Campo contraseña: Opción mostrar
		JLabel mostrar = opcionMostrarPassword(passwordField);
		GridBagConstraints gbc_Mostrar = new GridBagConstraints();
		gbc_Mostrar.insets = new Insets(0, 0, 5, 5);
		gbc_Mostrar.gridx = 3;
		gbc_Mostrar.gridy = 2;
		panelLogin.add(mostrar, gbc_Mostrar);
		
		// Campo login (botón)
		JButton botonLogin = new JButton("Login");
		botonLogin.setForeground(new Color(0, 0, 0));
		botonLogin.setBackground(new Color(247, 210, 249));
		GridBagConstraints gbc_botonLogin = new GridBagConstraints();
		gbc_botonLogin.gridwidth = 3;
		gbc_botonLogin.insets = new Insets(0, 0, 5, 5);
		gbc_botonLogin.gridx = 1;
		gbc_botonLogin.gridy = 4;
		panelLogin.add(botonLogin, gbc_botonLogin);
		botonLogin.setPreferredSize(new Dimension(90, 21));
		
		return panelLogin;
	}
	
	private void addManejadorLabelPassword(JTextField passwordField) {
		// El teléfono solo puede contener números
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (c < '0' || c > '9') {
					e.consume();
				}
			}
		});
	}
	
	private JLabel opcionMostrarPassword(final JPasswordField password) {
		final JLabel mostrar = new JLabel("Mostrar");
		mostrar.setForeground(Color.DARK_GRAY);
		
		// Se define el caracter de ocultación de la contraseña si esta está oculta
		if (password.getEchoChar() != (char) 0) {
			password.setEchoChar('•');
		}
		
		// Manejador
		mostrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean passwordMostrada = password.getEchoChar() == (char) 0;
				if (!passwordMostrada) {
					mostrar.setText("Ocultar");
					password.setEchoChar((char)0);
					passwordMostrada = true;
				}
				else {
					mostrar.setText("Mostrar");
					password.setEchoChar('•');
					passwordMostrada = false;
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				mostrar.setForeground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mostrar.setForeground(Color.DARK_GRAY);
			}
		});
		return mostrar;
	}

}
