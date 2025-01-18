package vista;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import tds.BubbleText;

import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;

public class VentanaElegirEmoticono extends JFrame {

	private JPanel contentPane;
	/**
	 * Create the frame.
	 */
	public VentanaElegirEmoticono() {
		setVisible(true);
		setResizable(false);
		setTitle("AppChat");
		FuncionesVentanas.addIcon(this);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 249, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelEmoticonos = new JPanel();
		panelEmoticonos.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		// Lista de emoticonos
		DefaultListModel<Integer> modelo = new DefaultListModel<Integer>();
		for(int i=0; i<BubbleText.MAXICONO; i++) {
			modelo.addElement(i);
		}
		JList<Integer> listaEmoticonos = new JList<Integer>(modelo);
		listaEmoticonos.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		listaEmoticonos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaEmoticonos.setBackground(new Color(238, 238, 238));
		listaEmoticonos.setCellRenderer(new EmoticonoCellRenderer());
		
		// Scroll para la lista de emoticonos
		JScrollPane scrollPane = new JScrollPane(listaEmoticonos);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scrollPane);
		
		// label Selecciona el emoticono
		
		JLabel lblSeleccionaTitulo = new JLabel("<html>Consulta el emoticono que necesites enviar:</html>");
		lblSeleccionaTitulo.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(lblSeleccionaTitulo, BorderLayout.NORTH);

	}

}
