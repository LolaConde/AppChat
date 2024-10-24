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
	private JTextField telfTxtField;
	private JPasswordField passwordField;
	private boolean passwordMostrada = false;

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
		
		JLabel tituloTxt = new JLabel("AppChat");
		tituloTxt.setHorizontalAlignment(SwingConstants.CENTER);
		tituloTxt.setFont(new Font("Tahoma", Font.PLAIN, 30));
		frame.getContentPane().add(tituloTxt, BorderLayout.NORTH);
		
		JPanel panelSur = new JPanel();
		frame.getContentPane().add(panelSur, BorderLayout.SOUTH);
		
		JButton botonRegistrar = new JButton("Registrar");
		botonRegistrar.setBackground(new Color(247, 210, 249));
		botonRegistrar.setPreferredSize(new Dimension(90, 21));
		botonRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JLabel registrateTxt = new JLabel("¿Aún no tienes una cuenta?");
		registrateTxt.setForeground(new Color(128, 128, 128));
		panelSur.add(registrateTxt);
		panelSur.add(botonRegistrar);
		
		JPanel panelCentro = new JPanel();
		frame.getContentPane().add(panelCentro, BorderLayout.CENTER);
		GridBagLayout gbl_panelCentro = new GridBagLayout();
		gbl_panelCentro.columnWidths = new int[]{0, 0, 200, 0, 15, 0};
		gbl_panelCentro.rowHeights = new int[]{15, 0, 0, 15, 0, 0, 0};
		gbl_panelCentro.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panelCentro.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panelCentro.setLayout(gbl_panelCentro);
		
		JLabel telfTxt = new JLabel("Teléfono:");
		GridBagConstraints gbc_telfTxt = new GridBagConstraints();
		gbc_telfTxt.insets = new Insets(0, 0, 5, 5);
		gbc_telfTxt.anchor = GridBagConstraints.NORTHEAST;
		gbc_telfTxt.gridx = 1;
		gbc_telfTxt.gridy = 1;
		panelCentro.add(telfTxt, gbc_telfTxt);
		
		telfTxtField = new JTextField();
		telfTxtField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// Si ha escrito algo que no sea un número, hay que borrarlo
				char c = e.getKeyChar();
				if (c < '0' || c > '9') {
					e.consume();
				}
			}
		});
		GridBagConstraints gbc_telfTxtField = new GridBagConstraints();
		gbc_telfTxtField.fill = GridBagConstraints.HORIZONTAL;
		gbc_telfTxtField.insets = new Insets(0, 0, 5, 5);
		gbc_telfTxtField.gridx = 2;
		gbc_telfTxtField.gridy = 1;
		panelCentro.add(telfTxtField, gbc_telfTxtField);
		telfTxtField.setColumns(20);
		
		JLabel passTxt = new JLabel("Contrase\u00F1a:");
		GridBagConstraints gbc_passTxt = new GridBagConstraints();
		gbc_passTxt.anchor = GridBagConstraints.EAST;
		gbc_passTxt.insets = new Insets(0, 0, 5, 5);
		gbc_passTxt.gridx = 1;
		gbc_passTxt.gridy = 2;
		panelCentro.add(passTxt, gbc_passTxt);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(20);
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 2;
		gbc_passwordField.gridy = 2;
		panelCentro.add(passwordField, gbc_passwordField);
		
		final JLabel lblNewLabel = new JLabel("Mostrar");
		passwordField.setEchoChar('•');
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (passwordMostrada == false) {
					lblNewLabel.setText("Ocultar");
					passwordField.setEchoChar((char)0);
					passwordMostrada = true;
				}
				else {
					lblNewLabel.setText("Mostrar");
					passwordField.setEchoChar('•');
					passwordMostrada = false;
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lblNewLabel.setForeground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblNewLabel.setForeground(Color.DARK_GRAY);
			}
		});
		lblNewLabel.setForeground(Color.DARK_GRAY);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 3;
		gbc_lblNewLabel.gridy = 2;
		panelCentro.add(lblNewLabel, gbc_lblNewLabel);
		
		JButton botonLogin = new JButton("Login");
		botonLogin.setForeground(new Color(0, 0, 0));
		botonLogin.setBackground(new Color(247, 210, 249));
		GridBagConstraints gbc_botonLogin = new GridBagConstraints();
		gbc_botonLogin.gridwidth = 3;
		gbc_botonLogin.insets = new Insets(0, 0, 5, 5);
		gbc_botonLogin.gridx = 1;
		gbc_botonLogin.gridy = 4;
		panelCentro.add(botonLogin, gbc_botonLogin);
		botonLogin.setIcon(null);
		botonLogin.setPreferredSize(new Dimension(90, 21));
	}

}
