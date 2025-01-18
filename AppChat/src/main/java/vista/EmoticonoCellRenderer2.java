package vista;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.BevelBorder;

import tds.BubbleText;

public class EmoticonoCellRenderer2 extends JPanel implements ListCellRenderer<Integer>{
	
	private JLabel emoticono;
	
	public EmoticonoCellRenderer2() {
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setLayout(new BorderLayout(0, 0));
		emoticono = new JLabel("");
		add(emoticono, BorderLayout.WEST);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Integer> list, Integer value, int index,
			boolean isSelected, boolean cellHasFocus) {
		emoticono.setIcon(BubbleText.getEmoji(value));
		return this;
	}

}
