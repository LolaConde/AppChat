package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.Controlador;
import modelo.ContactoIndividual;
import modelo.GrupoDeContactos;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class PanelAddContactoAGrupo extends JFrame {

	private JPanel contentPane;
	private JList<ContactoIndividual> listaContactosNoEnGrupo;

	/**
	 * Create the frame.
	 */
	public PanelAddContactoAGrupo(GrupoDeContactos grupo) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		// Titulo
		JLabel lblTitulo = new JLabel("<html><center> Selecciona el contacto a añadir al grupo " + grupo.getNombre() + "</html>");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblTitulo, BorderLayout.NORTH);
		
		// Contactos individuales que no están en el grupo
		crearListaContactosNoEnGrupo(grupo);
		JScrollPane scrollPane = new JScrollPane(listaContactosNoEnGrupo);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		// Aceptar/cancelar
		contentPane.add(panelAceptarCancelar(grupo), BorderLayout.SOUTH);
		
	}
	
	private JPanel panelAceptarCancelar(GrupoDeContactos grupo) {
		JPanel panelAceptarCancelar = new JPanel();
		panelAceptarCancelar.setLayout(new BoxLayout(panelAceptarCancelar, BoxLayout.X_AXIS));
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panelAceptarCancelar.add(horizontalGlue);
		
		// Botón añadir contacto
		JButton btnAddContacto = new JButton("Añadir contacto");
		btnAddContacto.setBackground(FuncionesVentanas.COLOR_ROSA);
		btnAddContacto.addActionListener(ev -> {
        	ContactoIndividual contactoSeleccionado = listaContactosNoEnGrupo.getSelectedValue();
        	if(contactoSeleccionado == null) {
        		JOptionPane.showMessageDialog(this, "Se debe seleccionar un contacto", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				Controlador.getUnicaInstancia().addContactoAGrupo(contactoSeleccionado, grupo);	
				JOptionPane.showMessageDialog(this, "Se ha añadido el contacto " + contactoSeleccionado.getNombre() + " al grupo " + grupo.getNombre(), "Información", JOptionPane.INFORMATION_MESSAGE);
				FuncionesVentanas.irAVentana(this, new VentanaContactos());
        	}
        	
		});
		panelAceptarCancelar.add(btnAddContacto);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panelAceptarCancelar.add(horizontalStrut);
		
		// Botón cancelar
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBackground(new Color(192, 192, 192));
		panelAceptarCancelar.add(btnCancelar);
		btnCancelar.addActionListener(ev -> {
			FuncionesVentanas.irAVentana(this, new VentanaContactos());
		});
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panelAceptarCancelar.add(horizontalGlue_1);
		
		return panelAceptarCancelar;
	}
	
	private void crearListaContactosNoEnGrupo(GrupoDeContactos grupo) {
		// Crear un modelo de lista
		DefaultListModel<ContactoIndividual> modelo = new DefaultListModel<ContactoIndividual>();
		Controlador.getUnicaInstancia().getContactos().stream()
			.filter(c -> c instanceof ContactoIndividual)
			.map(c -> (ContactoIndividual) c)
			.filter(c -> !grupo.contiene(c))
			.forEach(c -> modelo.addElement(c));
		
        // Crear el JList basado en el modelo
		listaContactosNoEnGrupo = new JList<ContactoIndividual>(modelo);
        listaContactosNoEnGrupo.setBackground(new Color(238, 238, 238));
        listaContactosNoEnGrupo.setCellRenderer(new ContactoCellRenderer());
        listaContactosNoEnGrupo.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	}

}
