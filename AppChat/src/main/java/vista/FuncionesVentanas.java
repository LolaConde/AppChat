package vista;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FuncionesVentanas {
	
	private static final ImageIcon ICONO_PASSWORD_OCULTA = new ImageIcon(FuncionesVentanas.class.getResource("/vista/imagenes/ocultar.png"));
	
	private static final ImageIcon ICONO_PASSWORD_MOSTRADA = new ImageIcon(FuncionesVentanas.class.getResource("/vista/imagenes/mostrar.png"));
	
	private static final ImageIcon ICONO_APP = new ImageIcon(FuncionesVentanas.class.getResource("/vista/imagenes/icono-appchat.png"));
	
	public static final ImageIcon ICONO_USUARIO_SIN_FOTO = new ImageIcon(FuncionesVentanas.class.getResource("/vista/imagenes/usuario-sin-foto.png"));

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
	
	public static void addIcon(JFrame frame) {
		frame.setIconImage(ICONO_APP.getImage());
	}
	
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
}
