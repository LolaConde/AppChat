package vista;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import controlador.Controlador;
import modelo.Mensaje;
import modelo.Usuario;
import tds.BubbleText;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.BorderLayout;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.border.BevelBorder;

public class MensajeCellRenderer4 extends JPanel implements ListCellRenderer<Mensaje>{
	private JLabel lblEmisor;
	private JLabel lblReceptor;
	private JLabel lblEmoticono;
	 private JTextArea txtMensaje;
	 
	public MensajeCellRenderer4() {
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{10, 0, 0, 0, 5, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 10, 0, 5, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblTituloEmisor = new JLabel("Emisor: ");
		lblTituloEmisor.setFont(new Font("Tahoma", Font.BOLD, 10));
		GridBagConstraints gbc_lblTituloEmisor = new GridBagConstraints();
		gbc_lblTituloEmisor.anchor = GridBagConstraints.WEST;
		gbc_lblTituloEmisor.insets = new Insets(0, 0, 5, 5);
		gbc_lblTituloEmisor.gridx = 1;
		gbc_lblTituloEmisor.gridy = 1;
		add(lblTituloEmisor, gbc_lblTituloEmisor);
		
		// Emisor
		lblEmisor = new JLabel("Emisor");
		lblEmisor.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 10));
		GridBagConstraints gbc_lblEmisor = new GridBagConstraints();
		gbc_lblEmisor.anchor = GridBagConstraints.WEST;
		gbc_lblEmisor.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmisor.gridx = 2;
		gbc_lblEmisor.gridy = 1;
		add(lblEmisor, gbc_lblEmisor);
		
		JLabel lblTituloReceptor = new JLabel("Receptor: ");
		lblTituloReceptor.setFont(new Font("Tahoma", Font.BOLD, 10));
		GridBagConstraints gbc_lblTituloReceptor = new GridBagConstraints();
		gbc_lblTituloReceptor.anchor = GridBagConstraints.WEST;
		gbc_lblTituloReceptor.insets = new Insets(0, 0, 5, 5);
		gbc_lblTituloReceptor.gridx = 1;
		gbc_lblTituloReceptor.gridy = 2;
		add(lblTituloReceptor, gbc_lblTituloReceptor);
		
		// Receptor
		lblReceptor = new JLabel("Receptor");
		lblReceptor.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 10));
		GridBagConstraints gbc_lblReceptor = new GridBagConstraints();
		gbc_lblReceptor.anchor = GridBagConstraints.WEST;
		gbc_lblReceptor.insets = new Insets(0, 0, 5, 5);
		gbc_lblReceptor.gridx = 2;
		gbc_lblReceptor.gridy = 2;
		add(lblReceptor, gbc_lblReceptor);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 3;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 4;
		add(panel, gbc_panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		// Emoticono (si hay)
		lblEmoticono = new JLabel("");
		panel.add(lblEmoticono, BorderLayout.WEST);
		
		// Texto del mensaje (si hay)
		txtMensaje = new JTextArea();
		txtMensaje.setLineWrap(true);
        txtMensaje.setWrapStyleWord(true);
        txtMensaje.setEditable(false);
        txtMensaje.setOpaque(false);
		panel.add(txtMensaje, BorderLayout.CENTER);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Mensaje> list, Mensaje mensaje, int index,
			boolean isSelected, boolean cellHasFocus) {
		// Emisor y receptor
		if (Controlador.getUnicaInstancia().isUsuarioActualEmisor(mensaje)) {
            lblEmisor.setText("Tu");
            lblReceptor.setText(getNombreDeContactoOTelefono(mensaje.getReceptor()));
        } else {
            lblReceptor.setText("Tu");
            lblEmisor.setText(getNombreDeContactoOTelefono(mensaje.getEmisor()));
        }
		if(mensaje.getEmoticono().isPresent()) {
			// Emoticono
			txtMensaje.setText("");
			lblEmoticono.setIcon(BubbleText.getEmoji(mensaje.getEmoticono().get()));
			lblEmoticono.setVisible(true);
		}
		else {
			// Texto
			txtMensaje.setText(mensaje.getTexto().orElse(""));
			lblEmoticono.setIcon(null);
			lblEmoticono.setVisible(false);
		}
		return this;
	}
	
	private String getNombreDeContactoOTelefono(Usuario usuario) {
        return Controlador.getUnicaInstancia().getNombreDeContacto(usuario)
                .orElse(Integer.toString(usuario.getNumeroTelefono()));
    }

}
