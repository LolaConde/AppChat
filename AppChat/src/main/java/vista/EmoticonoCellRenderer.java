package vista;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import tds.BubbleText;
import java.awt.BorderLayout;
import javax.swing.border.BevelBorder;

public class EmoticonoCellRenderer extends JPanel implements ListCellRenderer<Integer>{
	
	private JLabel emoticono;
	private JLabel numero;
	
	public EmoticonoCellRenderer() {
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setLayout(new BorderLayout(0, 0));
		emoticono = new JLabel("");
		add(emoticono, BorderLayout.WEST);
		
		numero = new JLabel("Numero");
		add(numero, BorderLayout.CENTER);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Integer> list, Integer value, int index,
			boolean isSelected, boolean cellHasFocus) {
		emoticono.setIcon(BubbleText.getEmoji(value));
		numero.setText(":"+value.toString());
		return this;
	}
}
