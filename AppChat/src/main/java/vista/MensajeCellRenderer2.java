package vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.BevelBorder;

import controlador.Controlador;
import modelo.Mensaje;
import tds.BubbleText;

public class MensajeCellRenderer2 extends JPanel 
implements ListCellRenderer<Mensaje> {
	
	private int TAMANO_MENSAJE = 12;
	private JLabel lblEmisor;
	private JLabel lblReceptor;
	private JPanel panelMensaje;
	
	public MensajeCellRenderer2(){
		this.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gbl_contentPane);
		
		lblEmisor = new JLabel("Emisor");
		GridBagConstraints gbc_lblEmisor = new GridBagConstraints();
		gbc_lblEmisor.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmisor.gridx = 1;
		gbc_lblEmisor.gridy = 1;
		add(lblEmisor, gbc_lblEmisor);
		
		lblReceptor = new JLabel("Receptor");
		GridBagConstraints gbc_lblReceptor = new GridBagConstraints();
		gbc_lblReceptor.insets = new Insets(0, 0, 5, 5);
		gbc_lblReceptor.gridx = 3;
		gbc_lblReceptor.gridy = 1;
		add(lblReceptor, gbc_lblReceptor);
		
		panelMensaje = new JPanel();
		GridBagConstraints gbc_panelMensaje = new GridBagConstraints();
		gbc_panelMensaje.gridwidth = 3;
		gbc_panelMensaje.insets = new Insets(0, 0, 0, 5);
		gbc_panelMensaje.fill = GridBagConstraints.BOTH;
		gbc_panelMensaje.gridx = 1;
		gbc_panelMensaje.gridy = 3;
		add(panelMensaje, gbc_panelMensaje);
		panelMensaje.setLayout(new BoxLayout(panelMensaje, BoxLayout.Y_AXIS));
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Mensaje> list, Mensaje mensaje, int index,
			boolean isSelected, boolean cellHasFocus) {
		// Emisor y receptor
		if (Controlador.getUnicaInstancia().isUsuarioActualEmisor(mensaje)) {
			lblEmisor.setText("tú");
			lblReceptor.setText(FuncionesVentanas.getNombreDeContactoOTelefono(mensaje.getReceptor()));
		}
		else {
			lblReceptor.setText("tú");
			lblEmisor.setText(FuncionesVentanas.getNombreDeContactoOTelefono(mensaje.getEmisor()));
		}
		// Mensaje
		panelMensaje.removeAll();
		if(mensaje.getEmoticono().isPresent()) {
			panelMensaje.add(new BubbleText(panelMensaje,
					mensaje.getEmoticono().get().intValue(),
					Color.LIGHT_GRAY,
					"", 
					BubbleText.RECEIVED,
					TAMANO_MENSAJE));
		}
		else {
			panelMensaje.add(new BubbleText(panelMensaje,
					mensaje.getTexto().orElse(""),
					Color.LIGHT_GRAY,					
					"", 
					BubbleText.RECEIVED,
					TAMANO_MENSAJE));
		}
		return this;
	}

}
