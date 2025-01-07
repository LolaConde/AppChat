package vista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.BevelBorder;

import modelo.Contacto;

public class ContactoCellRenderer extends JPanel 
implements ListCellRenderer<Contacto>{
	
	private JLabel imageLabel;
	private JLabel contactoLabel;
	
	public ContactoCellRenderer() {
		setLayout(new BorderLayout(5, 5));
		this.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		
		// Imagen de contacto
		imageLabel = new JLabel();
		add(imageLabel, BorderLayout.WEST);
		
		// Nombre de contacto
		contactoLabel = new JLabel();
		add(contactoLabel, BorderLayout.CENTER);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Contacto> list, Contacto contacto, int index,
			boolean isSelected, boolean cellHasFocus) {
		
		// Imagen de contacto
		try {
			URL imageUrl = new URL(contacto.getImagen());
			Image image = ImageIO.read(imageUrl);
			ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
			imageLabel.setIcon(imageIcon);
		} catch (Exception e) {
			Image image = FuncionesVentanas.ICONO_USUARIO_SIN_FOTO.getImage();
			ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
			imageLabel.setIcon(imageIcon);
			e.printStackTrace();
		}
		
		// Nombre de contacto
		contactoLabel.setText(contacto.getNombre());
		
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
