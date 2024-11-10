package vista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.text.Collator;
import java.awt.Color;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.border.BevelBorder;

import controlador.Controlador;
import modelo.Mensaje;
import modelo.Usuario;

public class MensajeCellRenderer extends JPanel 
		implements ListCellRenderer<Mensaje> {
	private JLabel userLabel;
	private JLabel imageLabel;
	private JLabel mensajeLabel;
	private JPanel panel;

	public MensajeCellRenderer() {
		setLayout(new BorderLayout(5, 5));
		
		imageLabel = new JLabel();
		add(imageLabel, BorderLayout.WEST);

		userLabel = new JLabel();
		mensajeLabel = new JLabel();
		mensajeLabel.setForeground(new Color(128, 128, 128));
		
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(userLabel);
		panel.add(Box.createVerticalGlue());
		panel.add(mensajeLabel);
		panel.add(Box.createVerticalGlue());
		add(panel, BorderLayout.CENTER);
		this.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Mensaje> list, Mensaje mensaje, int index,
			boolean isSelected, boolean cellHasFocus) {
		
		// Set the text
		Usuario usuario = Controlador.isUsuarioActual(mensaje.getEmisor()) ? mensaje.getReceptor() : mensaje.getEmisor();
		String nombreDeUsuario = Controlador.getNombreDeContacto(usuario);
		userLabel.setText(nombreDeUsuario);

		// Load the image from a random URL (for example, using "https://robohash.org")
		try {
			URL imageUrl = new URL(usuario.getImagenPerfil());
			Image image = ImageIO.read(imageUrl);
			ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
			imageLabel.setIcon(imageIcon);
		} catch (IOException e) {
			e.printStackTrace();
			imageLabel.setIcon(null); // Default to no image if there was an issue
		}
		
		mensajeLabel.setText(" "+ mensaje.getTexto() + " ");
		
		// Set background and foreground based on selection
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		return this;
	}
}