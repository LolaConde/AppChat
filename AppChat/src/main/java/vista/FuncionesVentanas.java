package vista;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import controlador.Controlador;
import modelo.Usuario;
import tds.BubbleText;

public class FuncionesVentanas {
	
	private static final ImageIcon ICONO_PASSWORD_OCULTA = new ImageIcon(FuncionesVentanas.class.getResource("/vista/imagenes/ocultar.png"));
	
	private static final ImageIcon ICONO_PASSWORD_MOSTRADA = new ImageIcon(FuncionesVentanas.class.getResource("/vista/imagenes/mostrar.png"));
	
	private static final ImageIcon ICONO_APP = new ImageIcon(FuncionesVentanas.class.getResource("/vista/imagenes/icono-appchat.png"));
	
	public static final ImageIcon ICONO_USUARIO_SIN_FOTO = new ImageIcon(FuncionesVentanas.class.getResource("/vista/imagenes/usuario-sin-foto.png"));
	
	public static final Color COLOR_ROSA = new Color(247, 210, 249);
	

	private static final ImageIcon ICONO_EMOTICONOS = new ImageIcon(FuncionesVentanas.class.getResource("/vista/imagenes/emoticonos.png"));
	
	/**
	 * Añade un KeyListener a <code>telefonoField</code> para permitir únicamente números. Cualquier otro carácter será ignorado.
	 * @param telefonoField
	 */
	public static void addManejadorFieldTelefono(JTextField telefonoField) {
		// El teléfono solo puede contener números
		telefonoField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (c < '0' || c > '9') {
					e.consume();
				}
			}
		});
	}
	
	/**
	 * Añade el icono de la aplicación a <code>frame</code>.
	 * @param frame
	 */
	public static void addIcon(JFrame frame) {
		frame.setIconImage(ICONO_APP.getImage());
	}
	
	/**
	 * @param ventanaAntigua JFrame actualmente visible que se cerrará
	 * @param ventanaNueva JFrame que se hará visible
	 */
	public static void irAVentana(JFrame ventanaAntigua, JFrame ventanaNueva) {
		ventanaNueva.setVisible(true);
		ventanaNueva.setLocationRelativeTo(ventanaAntigua);
		ventanaAntigua.dispose();
	}
	
	/**
	 * Crea un JLabel que alterna la visibilidad de la contraseña en <code>password</code> al hacer clic.
	 * El cursor cambia a HAND_CURSOR cuando pasa sobre el JLabel creado.
	 * @param password
	 * @return El JLabel creado
	 */
	public static JLabel opcionMostrarPassword(final JPasswordField password) {
		
		// Se define el caracter de ocultación de la contraseña si esta está oculta
		if (password.getEchoChar() != (char) 0) {
			password.setEchoChar('\u2022');
		}
		
		final JLabel mostrar = new JLabel("");
		mostrar.setIcon(ICONO_PASSWORD_OCULTA);
		
		mostrar.addMouseListener(new MouseAdapter() {
			// Mostrar/ocultar la contraseña
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean passwordMostrada = password.getEchoChar() == (char) 0;
				if (!passwordMostrada) {
					mostrar.setIcon(ICONO_PASSWORD_MOSTRADA);
					password.setEchoChar((char)0);
				}
				else {
					mostrar.setIcon(ICONO_PASSWORD_OCULTA);
					password.setEchoChar('\u2022');
				}
			}
			// HAND_CURSOR si el ratón pasa por encima
			@Override
			public void mouseEntered(MouseEvent e) {
				mostrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mostrar.setCursor(Cursor.getDefaultCursor());
			}
			
		});
		return mostrar;
	}
	
	/**
	 * @param usuario
	 * @return Nombre de contacto de <code>usuario</code> si el usuario actual lo tiene agregado. 
	 * Número de teléfono de <code>usuario</code> en caso contrario.
	 */
	public static String getNombreDeContactoOTelefono(Usuario usuario) {
		return Controlador.getUnicaInstancia().getNombreDeContacto(usuario)
        .orElse(Integer.toString(usuario.getNumeroTelefono()));
	}
	
	/**
	 * @return panel con el string <code>texto</code>, seguido de <code>textField</code>, y un
	 * botón de emoticonos. Si se pulsa el botón se ven los emoticonos, de tal forma
	 * que si se pulsa un emoticono, su código se muestra en textField.
	 * @param texto
	 * @param textField
	 * @param frame El JFrame en el que se muestra el panel resultante
	 */
	public static JPanel panelTexto(String texto, JTextField textField, JFrame frame) {
		JPanel panelMensaje = new JPanel();
		panelMensaje.setLayout(new BoxLayout(panelMensaje, BoxLayout.Y_AXIS));
		
		JPanel panelEscribirMensaje = new JPanel();
		panelEscribirMensaje.setLayout(new BoxLayout(panelEscribirMensaje, BoxLayout.X_AXIS));
		panelMensaje.add(panelEscribirMensaje);
		
		// Texto:
		JLabel lblTexto = new JLabel(texto);
		panelEscribirMensaje.add(lblTexto);
		
		// Escribir
		panelEscribirMensaje.add(textField);
		textField.setToolTipText("");
		
		// Mostrar emoticonos
		JButton btnMostrarEmoticonos = new JButton("");
		btnMostrarEmoticonos.setIcon(ICONO_EMOTICONOS);
		btnMostrarEmoticonos.setBackground(FuncionesVentanas.COLOR_ROSA);
		panelEscribirMensaje.add(btnMostrarEmoticonos);
		
		// Los emoticonos
		JScrollPane scrollPaneEmoticonos = scrollPaneEmoticonos(textField);
		panelMensaje.add(scrollPaneEmoticonos);
		
		btnMostrarEmoticonos.addActionListener(ev -> {
			scrollPaneEmoticonos.setVisible(!scrollPaneEmoticonos.isVisible());
			frame.revalidate();
			frame.repaint();
		});
		
		return panelMensaje;
	}
	
	/**
	 * @return Un scrollPane que muestra una serie de emoticonos, de tal forma que si se selecciona
	 * uno, se escribe en el <code>fieldTexto</code> el codigo que se corresponde con dicho emoticono
	 * @param fieldTexto
	 */
	private static JScrollPane scrollPaneEmoticonos(JTextField fieldTexto) {
		// Lista de emoticonos
		DefaultListModel<Integer> modeloEmoticonos = new DefaultListModel<Integer>();
		for(int i=0; i<BubbleText.MAXICONO; i++) {
			modeloEmoticonos.addElement(i);
		}
		JList<Integer> listaEmoticonos = new JList<Integer>(modeloEmoticonos);
		listaEmoticonos.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		listaEmoticonos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaEmoticonos.setBackground(new Color(238, 238, 238));
		listaEmoticonos.setCellRenderer(new EmoticonoCellRenderer2());
		listaEmoticonos.setVisibleRowCount(-1);
		
		listaEmoticonos.addListSelectionListener(
				ev -> {
					if(!ev.getValueIsAdjusting() && listaEmoticonos.getSelectedValue() != null) {
						fieldTexto.setText(":"+listaEmoticonos.getSelectedValue());
					}
				}
		);
		
		// Scroll para la lista de emoticonos
		JScrollPane scrollPaneEmoticonos = new JScrollPane(listaEmoticonos);
		scrollPaneEmoticonos.setMaximumSize(new Dimension(800, 100));
		
		return scrollPaneEmoticonos;
	}
}
