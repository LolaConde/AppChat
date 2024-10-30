package vista;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class FuncionesVentanas {
	public static void addManejadorLabelPassword(JTextField passwordField) {
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
	
	public static JLabel opcionMostrarPassword(final JPasswordField password) {
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
	
	public static void addIcon(JFrame frame) {
		frame.setIconImage(new ImageIcon(VentanaLogin.class.getResource("/vista/imagenes/icono-appchat.png")).getImage());
	}
}
