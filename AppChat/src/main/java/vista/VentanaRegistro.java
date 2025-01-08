package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet.ColorAttribute;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

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
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;

import com.toedter.calendar.JDateChooser;

import controlador.Controlador;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.ScrollPaneConstants;

public class VentanaRegistro extends JFrame {

	private JPanel contentPane;
	private JTextField nombreTxtField;
	private JTextField apellidosTxtField;
	private JTextField TelefonoTxtField;
	private JPasswordField passwordField1;
	private JPasswordField passwordField2;
	private JTextField imagenTxtField;
	private JLabel txtNombre;
	private JLabel txtApellidos;
	private JLabel txtTelefono;
	private JDateChooser FechaNacimientoDateChooser;
	private JLabel txtFechaNacimiento;
	private JLabel txtPass1;
	private JLabel txtPass2;
	private JLabel imagenPerfil;
	private JLabel txtImagen;
	private JTextArea saludoField;
	
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
		
		// Crear título
		JLabel tituloTxt = new JLabel("AppChat");
		tituloTxt.setHorizontalAlignment(SwingConstants.CENTER);
		tituloTxt.setForeground(new Color(51, 51, 51));
		tituloTxt.setFont(new Font("Tahoma", Font.PLAIN, 30));
		contentPane.add(tituloTxt, BorderLayout.NORTH);
		
		contentPane.add(panelDatos(), BorderLayout.CENTER);
		contentPane.add(panelAceptarCancelar(), BorderLayout.SOUTH);
		
	}
	
	private JPanel panelAceptarCancelar() {
		JPanel panelAceptarCancelar = new JPanel();
		
		// Botón guardar el registro
		JButton aceptarButton = new JButton("Aceptar");
		aceptarButton.setBackground(FuncionesVentanas.COLOR_ROSA);
		panelAceptarCancelar.add(aceptarButton);
		addManejadorProcesarRegistro(aceptarButton);
		
		// Botón volver al login
		JButton cancelarButton = new JButton("Cancelar");
		addManejadorIrAlLogin(cancelarButton);
		cancelarButton.setBackground(FuncionesVentanas.COLOR_ROSA);
		panelAceptarCancelar.add(cancelarButton);
		
		return panelAceptarCancelar;
	}
	
	private void addManejadorIrAlLogin(JButton button) {
		button.addActionListener(ev -> {
			VentanaLogin ventanaLogin = new VentanaLogin();
			ventanaLogin.mostrarVentana(VentanaRegistro.this);
			VentanaRegistro.this.dispose();
		});
	}
	
	private void addManejadorProcesarRegistro(JButton button) {
		button.addActionListener(ev -> {
			Boolean datosCorrectos = comprobarDatosIntroducidos();
			if (datosCorrectos) {
				// TODO: Si no añade un saludo, usar el registro de usuario sin especificar el saludo
				Controlador.getUnicaInstancia().registrarUsuario(nombreTxtField.getText() + " " + apellidosTxtField.getText(), new String(passwordField1.getPassword()), Integer.parseInt(TelefonoTxtField.getText()), "", FechaNacimientoDateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), imagenTxtField.getText(), saludoField.getText());
				JOptionPane.showMessageDialog(contentPane, "Usuario registrado correctamente", "Registro correcto", JOptionPane.PLAIN_MESSAGE);
				// Ir al login
				VentanaLogin ventanaLogin = new VentanaLogin();
				ventanaLogin.mostrarVentana(VentanaRegistro.this);
				VentanaRegistro.this.dispose();
			}
		});
	}
	
	private JPanel panelDatos() {
		JPanel panelDatos = new JPanel();
		contentPane.add(panelDatos, BorderLayout.CENTER);
		GridBagLayout gbl_panelDatos = new GridBagLayout();
		gbl_panelDatos.columnWidths = new int[]{15, 0, 0, 40, 0, 15, 0, 40, 0, 15, 0};
		gbl_panelDatos.rowHeights = new int[]{15, 0, 5, 0, 10, 0, 20, 0, 10, 0, 0, 0, 0};
		gbl_panelDatos.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panelDatos.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 2.0, Double.MIN_VALUE};
		panelDatos.setLayout(gbl_panelDatos);
		
		// Campo nombre: Texto
		txtNombre = new JLabel("Nombre:");
		GridBagConstraints gbc_txtNombre = new GridBagConstraints();
		gbc_txtNombre.anchor = GridBagConstraints.EAST;
		gbc_txtNombre.insets = new Insets(0, 0, 5, 5);
		gbc_txtNombre.gridx = 1;
		gbc_txtNombre.gridy = 1;
		panelDatos.add(txtNombre, gbc_txtNombre);
		
		// Campo nombre: field
		nombreTxtField = new JTextField();
		GridBagConstraints gbc_nombreTxtField = new GridBagConstraints();
		gbc_nombreTxtField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nombreTxtField.gridwidth = 5;
		gbc_nombreTxtField.insets = new Insets(0, 0, 5, 5);
		gbc_nombreTxtField.gridx = 3;
		gbc_nombreTxtField.gridy = 1;
		panelDatos.add(nombreTxtField, gbc_nombreTxtField);
		nombreTxtField.setColumns(10);
		
		// Campo apellidos: Texto
		txtApellidos = new JLabel("Apellidos:");
		GridBagConstraints gbc_txtApellidos = new GridBagConstraints();
		gbc_txtApellidos.anchor = GridBagConstraints.EAST;
		gbc_txtApellidos.insets = new Insets(0, 0, 5, 5);
		gbc_txtApellidos.gridx = 1;
		gbc_txtApellidos.gridy = 3;
		panelDatos.add(txtApellidos, gbc_txtApellidos);
		
		// Campo apellidos: field
		apellidosTxtField = new JTextField();
		GridBagConstraints gbc_apellidosTxtField = new GridBagConstraints();
		gbc_apellidosTxtField.fill = GridBagConstraints.HORIZONTAL;
		gbc_apellidosTxtField.gridwidth = 5;
		gbc_apellidosTxtField.insets = new Insets(0, 0, 5, 5);
		gbc_apellidosTxtField.gridx = 3;
		gbc_apellidosTxtField.gridy = 3;
		panelDatos.add(apellidosTxtField, gbc_apellidosTxtField);
		apellidosTxtField.setColumns(10);
		
		// Campo teléfono: Texto
		txtTelefono = new JLabel("Teléfono:");
		GridBagConstraints gbc_txtTelefono = new GridBagConstraints();
		gbc_txtTelefono.anchor = GridBagConstraints.EAST;
		gbc_txtTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_txtTelefono.gridx = 1;
		gbc_txtTelefono.gridy = 5;
		panelDatos.add(txtTelefono, gbc_txtTelefono);
		
		// Campo teléfono: field
		TelefonoTxtField = new JTextField();
		FuncionesVentanas.addManejadorFieldTelefono(TelefonoTxtField);
		GridBagConstraints gbc_TelefonoTxtField = new GridBagConstraints();
		gbc_TelefonoTxtField.fill = GridBagConstraints.HORIZONTAL;
		gbc_TelefonoTxtField.insets = new Insets(0, 0, 5, 5);
		gbc_TelefonoTxtField.gridx = 3;
		gbc_TelefonoTxtField.gridy = 5;
		panelDatos.add(TelefonoTxtField, gbc_TelefonoTxtField);
		TelefonoTxtField.setColumns(10);
		
		// Campo fecha de nacimiento: Texto
		txtFechaNacimiento = new JLabel("Fecha de nacimiento:");
		GridBagConstraints gbc_txtFechaNacimiento = new GridBagConstraints();
		gbc_txtFechaNacimiento.anchor = GridBagConstraints.EAST;
		gbc_txtFechaNacimiento.insets = new Insets(0, 0, 5, 5);
		gbc_txtFechaNacimiento.gridx = 6;
		gbc_txtFechaNacimiento.gridy = 5;
		panelDatos.add(txtFechaNacimiento, gbc_txtFechaNacimiento);
		
		// Campo fecha de nacimiento: date chooser
		FechaNacimientoDateChooser = new JDateChooser();
		GridBagConstraints gbc_FechaNacimientoDateChooser = new GridBagConstraints();
		gbc_FechaNacimientoDateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_FechaNacimientoDateChooser.fill = GridBagConstraints.BOTH;
		gbc_FechaNacimientoDateChooser.gridx = 7;
		gbc_FechaNacimientoDateChooser.gridy = 5;
		panelDatos.add(FechaNacimientoDateChooser, gbc_FechaNacimientoDateChooser);
		
		// Campo contraseña (1º): Texto
		txtPass1 = new JLabel("Contraseña:");
		GridBagConstraints gbc_txtPass1 = new GridBagConstraints();
		gbc_txtPass1.anchor = GridBagConstraints.EAST;
		gbc_txtPass1.insets = new Insets(0, 0, 5, 5);
		gbc_txtPass1.gridx = 1;
		gbc_txtPass1.gridy = 7;
		panelDatos.add(txtPass1, gbc_txtPass1);
		
		// Campo contraseña (1º): field
		passwordField1 = new JPasswordField();
		GridBagConstraints gbc_passwordField1 = new GridBagConstraints();
		gbc_passwordField1.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField1.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField1.gridx = 3;
		gbc_passwordField1.gridy = 7;
		panelDatos.add(passwordField1, gbc_passwordField1);
		
		// Campo contraseña (1º): JLabel para mostrarla/ocultarla
		JLabel mostrarPassJLabel1 = FuncionesVentanas.opcionMostrarPassword(passwordField1);
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 4;
		gbc_lblNewLabel_3.gridy = 7;
		panelDatos.add(mostrarPassJLabel1, gbc_lblNewLabel_3);
		
		// Campo contraseña (2º): Texto
		txtPass2 = new JLabel("Repite la contraseña:");
		GridBagConstraints gbc_txtPass2 = new GridBagConstraints();
		gbc_txtPass2.anchor = GridBagConstraints.EAST;
		gbc_txtPass2.insets = new Insets(0, 0, 5, 5);
		gbc_txtPass2.gridx = 6;
		gbc_txtPass2.gridy = 7;
		panelDatos.add(txtPass2, gbc_txtPass2);
		
		// Campo contraseña (2º): field
		passwordField2 = new JPasswordField();
		GridBagConstraints gbc_passwordField2 = new GridBagConstraints();
		gbc_passwordField2.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField2.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField2.gridx = 7;
		gbc_passwordField2.gridy = 7;
		panelDatos.add(passwordField2, gbc_passwordField2);
		
		// Campo contraseña (2º): JLabel para mostrarla/ocultarla
		JLabel mostrarPassJLabel2 = FuncionesVentanas.opcionMostrarPassword(passwordField2);
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 8;
		gbc_lblNewLabel_4.gridy = 7;
		panelDatos.add(mostrarPassJLabel2, gbc_lblNewLabel_4);
		
		// Campo saludo: Texto
		JLabel txtSaludo = new JLabel("Saludo:");
		GridBagConstraints gbc_txtSaludo = new GridBagConstraints();
		gbc_txtSaludo.anchor = GridBagConstraints.NORTHEAST;
		gbc_txtSaludo.insets = new Insets(0, 0, 5, 5);
		gbc_txtSaludo.gridx = 1;
		gbc_txtSaludo.gridy = 9;
		panelDatos.add(txtSaludo, gbc_txtSaludo);
		
		// Campo saludo: field
		GridBagConstraints gbc_scrollPaneSaludo = new GridBagConstraints();
		gbc_scrollPaneSaludo.gridheight = 2;
		gbc_scrollPaneSaludo.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPaneSaludo.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneSaludo.gridx = 3;
		gbc_scrollPaneSaludo.gridy = 9;
		panelDatos.add(textAreaConScrollSaludo(), gbc_scrollPaneSaludo);
		
		// Campo imagen: Texto
		txtImagen = new JLabel("Imagen:");
		GridBagConstraints gbc_txtImagen = new GridBagConstraints();
		gbc_txtImagen.anchor = GridBagConstraints.NORTHEAST;
		gbc_txtImagen.insets = new Insets(0, 0, 5, 5);
		gbc_txtImagen.gridx = 6;
		gbc_txtImagen.gridy = 9;
		panelDatos.add(txtImagen, gbc_txtImagen);
		
		// Campo imagen: field para la url
		imagenTxtField = new JTextField();
		GridBagConstraints gbc_imagenTxtField = new GridBagConstraints();
		gbc_imagenTxtField.anchor = GridBagConstraints.NORTH;
		gbc_imagenTxtField.insets = new Insets(0, 0, 5, 5);
		gbc_imagenTxtField.fill = GridBagConstraints.HORIZONTAL;
		gbc_imagenTxtField.gridx = 7;
		gbc_imagenTxtField.gridy = 9;
		panelDatos.add(imagenTxtField, gbc_imagenTxtField);
		imagenTxtField.setColumns(10);
		
		// Campo imagen: Imagen mostrada de la url del field
		imagenPerfil = new JLabel("");
		imagenPerfil.setIcon(new ImageIcon(VentanaRegistro.class.getResource("/vista/imagenes/usuario.png")));
		GridBagConstraints gbc_imagenPerfil = new GridBagConstraints();
		gbc_imagenPerfil.fill = GridBagConstraints.HORIZONTAL;
		gbc_imagenPerfil.insets = new Insets(0, 0, 5, 5);
		gbc_imagenPerfil.gridx = 7;
		gbc_imagenPerfil.gridy = 10;
		panelDatos.add(imagenPerfil, gbc_imagenPerfil);
		
		return panelDatos;
	}
	
	private JScrollPane textAreaConScrollSaludo() {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		// Text area
		saludoField = new JTextArea();
		saludoField.setLineWrap(true);
		saludoField.setWrapStyleWord(true);
		scrollPane.setViewportView(saludoField);
		
		return scrollPane;
	}
	
	private boolean comprobarDatosIntroducidos() {
		limpiarDatos();
		
		boolean datosCorrectos = true;
		String explicacionDeErrores = "";
		// nombre no vacío
		if (nombreTxtField.getText().trim().isEmpty()) {
			txtNombre.setForeground(Color.RED);
			datosCorrectos = false;
			explicacionDeErrores += "El nombre no puede estar vacío" + '\n';
		}
		// apellidos no vacíos
		if (apellidosTxtField.getText().trim().isEmpty()) {
			txtApellidos.setForeground(Color.RED);
			datosCorrectos = false;
			explicacionDeErrores += "Los apellidos no pueden estar vacíos" + '\n';
		}
		// teléfono no vacío y sólo tiene números
		if (TelefonoTxtField.getText().isEmpty()) {
			txtTelefono.setForeground(Color.RED);
			explicacionDeErrores += "El teléfono no puede estar vacío" + '\n';
			datosCorrectos = false;
		}
		else if(TelefonoTxtField.getText().chars().mapToObj(c -> (char) c).anyMatch(c -> !Character.isDigit(c))) {
			txtTelefono.setForeground(Color.RED);
			explicacionDeErrores += "El teléfono solo puede tener números" + '\n';
			datosCorrectos = false;
		}
		// Fecha de nacimiento anterior a hoy
		Date hoy = new Date();
		if(FechaNacimientoDateChooser.getDate() == null || FechaNacimientoDateChooser.getDate().after(hoy)) {
			txtFechaNacimiento.setForeground(Color.RED);
			datosCorrectos = false;
			explicacionDeErrores += "La fecha de nacimiento debe contener una fecha anterior a hoy" + '\n';
		}
		// contraseñas iguales
		if(!Arrays.equals(passwordField1.getPassword(), passwordField2.getPassword())) {
			txtPass1.setForeground(Color.RED);
			txtPass2.setForeground(Color.RED);
			datosCorrectos = false;
			explicacionDeErrores += "Los dos campos de contraseña deben contener el mismo valor" + '\n';
		}
		// contraseña no vacía
		if(passwordField1.getPassword().length == 0) {
			txtPass1.setForeground(Color.RED);
			datosCorrectos = false;
			explicacionDeErrores += "El campo de la contraseña 1 está vacío: La contraseña no puede estar vacía" + '\n';
		}
		if(passwordField2.getPassword().length == 0) {
			txtPass2.setForeground(Color.RED);
			datosCorrectos = false;
			explicacionDeErrores += "El campo de la contraseña 2 está vacío: La contraseña no puede estar vacía" + '\n';
		}
		// url de la imagen hace referencia a una imagen existente
		URL url = null;
		try {
			url = new URL(imagenTxtField.getText());
			BufferedImage image = ImageIO.read(url);
			ImageIcon imageIcon = new ImageIcon(image);
			
		} catch (Exception e) {
			txtImagen.setForeground(Color.RED);
			datosCorrectos = false;
			explicacionDeErrores += "La url de la imagen debe hacer referencia a una imagen existente" + '\n';
		}
		if (!datosCorrectos) {
			JOptionPane.showMessageDialog(contentPane, explicacionDeErrores, "Campos erroneos", JOptionPane.ERROR_MESSAGE);
		}
		return datosCorrectos;
	}
	
	private void limpiarDatos() {
		Color color = new JLabel().getForeground();
		txtNombre.setForeground(color);
		txtApellidos.setForeground(color);
		txtTelefono.setForeground(color);
		txtFechaNacimiento.setForeground(color);
		txtPass2.setForeground(color);
		txtPass1.setForeground(color);
		txtImagen.setForeground(color);
	}
}