package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.toedter.calendar.JDateChooser;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.ScrollPaneConstants;

public class VentanaRegistro extends JFrame {

	private JPanel contentPane;
	private JTextField nombreTxtField;
	private JTextField apellidosTxtField;
	private JTextField TelefonoTxtField;
	private JPasswordField passwordField1;
	private JPasswordField passwordField2;
	private JTextField textField_1;
	
	/**
	 * Create the frame.
	 */
	public VentanaRegistro() {
		FuncionesVentanas.addIcon(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 793, 476);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel tituloTxt = new JLabel("AppChat");
		tituloTxt.setHorizontalAlignment(SwingConstants.CENTER);
		tituloTxt.setForeground(new Color(51, 51, 51));
		tituloTxt.setFont(new Font("Tahoma", Font.PLAIN, 30));
		contentPane.add(tituloTxt, BorderLayout.NORTH);
		
		JPanel panelCentro = new JPanel();
		contentPane.add(panelCentro, BorderLayout.CENTER);
		GridBagLayout gbl_panelCentro = new GridBagLayout();
		gbl_panelCentro.columnWidths = new int[]{15, 0, 0, 40, 0, 15, 0, 40, 0, 15, 0};
		gbl_panelCentro.rowHeights = new int[]{15, 0, 5, 0, 10, 0, 20, 0, 10, 0, 0, 0, 0};
		gbl_panelCentro.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panelCentro.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 2.0, Double.MIN_VALUE};
		panelCentro.setLayout(gbl_panelCentro);
		
		JLabel txtNombre = new JLabel("Nombre:");
		GridBagConstraints gbc_txtNombre = new GridBagConstraints();
		gbc_txtNombre.anchor = GridBagConstraints.EAST;
		gbc_txtNombre.insets = new Insets(0, 0, 5, 5);
		gbc_txtNombre.gridx = 1;
		gbc_txtNombre.gridy = 1;
		panelCentro.add(txtNombre, gbc_txtNombre);
		
		nombreTxtField = new JTextField();
		GridBagConstraints gbc_nombreTxtField = new GridBagConstraints();
		gbc_nombreTxtField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nombreTxtField.gridwidth = 5;
		gbc_nombreTxtField.insets = new Insets(0, 0, 5, 5);
		gbc_nombreTxtField.gridx = 3;
		gbc_nombreTxtField.gridy = 1;
		panelCentro.add(nombreTxtField, gbc_nombreTxtField);
		nombreTxtField.setColumns(10);
		
		JLabel txtApellidos = new JLabel("Apellidos:");
		GridBagConstraints gbc_txtApellidos = new GridBagConstraints();
		gbc_txtApellidos.anchor = GridBagConstraints.EAST;
		gbc_txtApellidos.insets = new Insets(0, 0, 5, 5);
		gbc_txtApellidos.gridx = 1;
		gbc_txtApellidos.gridy = 3;
		panelCentro.add(txtApellidos, gbc_txtApellidos);
		
		apellidosTxtField = new JTextField();
		GridBagConstraints gbc_apellidosTxtField = new GridBagConstraints();
		gbc_apellidosTxtField.fill = GridBagConstraints.HORIZONTAL;
		gbc_apellidosTxtField.gridwidth = 5;
		gbc_apellidosTxtField.insets = new Insets(0, 0, 5, 5);
		gbc_apellidosTxtField.gridx = 3;
		gbc_apellidosTxtField.gridy = 3;
		panelCentro.add(apellidosTxtField, gbc_apellidosTxtField);
		apellidosTxtField.setColumns(10);
		
		JLabel txtTelefono = new JLabel("Teléfono:");
		GridBagConstraints gbc_txtTelefono = new GridBagConstraints();
		gbc_txtTelefono.anchor = GridBagConstraints.EAST;
		gbc_txtTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_txtTelefono.gridx = 1;
		gbc_txtTelefono.gridy = 5;
		panelCentro.add(txtTelefono, gbc_txtTelefono);
		
		TelefonoTxtField = new JTextField();
		TelefonoTxtField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// Si ha escrito algo que no sea un número, hay que borrarlo
				char c = e.getKeyChar();
				if (c < '0' || c > '9') {
					e.consume();
				}
			}
		});
		GridBagConstraints gbc_TelefonoTxtField = new GridBagConstraints();
		gbc_TelefonoTxtField.fill = GridBagConstraints.HORIZONTAL;
		gbc_TelefonoTxtField.insets = new Insets(0, 0, 5, 5);
		gbc_TelefonoTxtField.gridx = 3;
		gbc_TelefonoTxtField.gridy = 5;
		panelCentro.add(TelefonoTxtField, gbc_TelefonoTxtField);
		TelefonoTxtField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Fecha de nacimiento:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 6;
		gbc_lblNewLabel.gridy = 5;
		panelCentro.add(lblNewLabel, gbc_lblNewLabel);
		
		JDateChooser dateChooser = new JDateChooser();
		GridBagConstraints gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser.fill = GridBagConstraints.BOTH;
		gbc_dateChooser.gridx = 7;
		gbc_dateChooser.gridy = 5;
		panelCentro.add(dateChooser, gbc_dateChooser);
		
		JLabel txtPass1 = new JLabel("Contraseña:");
		GridBagConstraints gbc_txtPass1 = new GridBagConstraints();
		gbc_txtPass1.anchor = GridBagConstraints.EAST;
		gbc_txtPass1.insets = new Insets(0, 0, 5, 5);
		gbc_txtPass1.gridx = 1;
		gbc_txtPass1.gridy = 7;
		panelCentro.add(txtPass1, gbc_txtPass1);
		
		passwordField1 = new JPasswordField();
		GridBagConstraints gbc_passwordField1 = new GridBagConstraints();
		gbc_passwordField1.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField1.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField1.gridx = 3;
		gbc_passwordField1.gridy = 7;
		panelCentro.add(passwordField1, gbc_passwordField1);
		
		JLabel txtMostrarPass1 = txtMostrarPass1 = FuncionesVentanas.opcionMostrarPassword(passwordField1);
		GridBagConstraints gbc_txtMostrarPass1 = new GridBagConstraints();
		gbc_txtMostrarPass1.anchor = GridBagConstraints.WEST;
		gbc_txtMostrarPass1.insets = new Insets(0, 0, 5, 5);
		gbc_txtMostrarPass1.gridx = 4;
		gbc_txtMostrarPass1.gridy = 7;
		panelCentro.add(txtMostrarPass1, gbc_txtMostrarPass1);
		
		JLabel txtPass2 = new JLabel("Repite la contraseña:");
		GridBagConstraints gbc_txtPass2 = new GridBagConstraints();
		gbc_txtPass2.anchor = GridBagConstraints.EAST;
		gbc_txtPass2.insets = new Insets(0, 0, 5, 5);
		gbc_txtPass2.gridx = 6;
		gbc_txtPass2.gridy = 7;
		panelCentro.add(txtPass2, gbc_txtPass2);
		
		passwordField2 = new JPasswordField();
		GridBagConstraints gbc_passwordField2 = new GridBagConstraints();
		gbc_passwordField2.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField2.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField2.gridx = 7;
		gbc_passwordField2.gridy = 7;
		panelCentro.add(passwordField2, gbc_passwordField2);
		
		JLabel txtMostrarPass2 = txtMostrarPass2 = FuncionesVentanas.opcionMostrarPassword(passwordField2);
		GridBagConstraints gbc_txtMostrarPass2 = new GridBagConstraints();
		gbc_txtMostrarPass2.anchor = GridBagConstraints.WEST;
		gbc_txtMostrarPass2.insets = new Insets(0, 0, 5, 5);
		gbc_txtMostrarPass2.gridx = 8;
		gbc_txtMostrarPass2.gridy = 7;
		panelCentro.add(txtMostrarPass2, gbc_txtMostrarPass2);
		
		JLabel txtSaludo = new JLabel("Saludo:");
		GridBagConstraints gbc_txtSaludo = new GridBagConstraints();
		gbc_txtSaludo.anchor = GridBagConstraints.NORTHEAST;
		gbc_txtSaludo.insets = new Insets(0, 0, 5, 5);
		gbc_txtSaludo.gridx = 1;
		gbc_txtSaludo.gridy = 9;
		panelCentro.add(txtSaludo, gbc_txtSaludo);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 3;
		gbc_scrollPane.gridy = 9;
		panelCentro.add(scrollPane, gbc_scrollPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		scrollPane.setViewportView(textArea);
		
		JLabel lblNewLabel_1 = new JLabel("Imagen:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 6;
		gbc_lblNewLabel_1.gridy = 9;
		panelCentro.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.anchor = GridBagConstraints.NORTH;
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 7;
		gbc_textField_1.gridy = 9;
		panelCentro.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(VentanaRegistro.class.getResource("/vista/imagenes/usuario.png")));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 7;
		gbc_lblNewLabel_2.gridy = 10;
		panelCentro.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JButton aceptarButton = new JButton("Aceptar");
		aceptarButton.setBackground(new Color(247, 210, 249));
		panel.add(aceptarButton);
		
		JButton cancelarButton = new JButton("Cancelar");
		cancelarButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				VentanaLogin ventanaLogin = new VentanaLogin();
				ventanaLogin.mostrarVentana(VentanaRegistro.this);
				VentanaRegistro.this.dispose();
				
			}
		});
		cancelarButton.setBackground(new Color(247, 210, 249));
		panel.add(cancelarButton);
	}

}
