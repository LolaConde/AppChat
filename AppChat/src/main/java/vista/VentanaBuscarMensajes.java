package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.Controlador;
import modelo.Mensaje;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;

public class VentanaBuscarMensajes extends JFrame {

	private JPanel contentPane;
	private JTextField fieldTexto;
	private JTextField fieldTelefono;
	private JTextField fieldContacto;
	private DefaultListModel<Mensaje> modelo;
	private JScrollPane panelConLista;
	private JList<Mensaje> listaMensajes;
	
	/**
	 * Create the frame.
	 */
	public VentanaBuscarMensajes() {
		setTitle("AppChat");
		FuncionesVentanas.addIcon(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		// Panel con filtros
		contentPane.add(panelBusqueda(), BorderLayout.NORTH);
		// Panel con mensajes filtrados
		listaDeMensajesConScroll();
		contentPane.add(panelConLista, BorderLayout.CENTER);
		
		// Botón de cancelar
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(ev -> {
				FuncionesVentanas.irAVentana(this, new VentanaPrincipal());
		});
		btnCancelar.setBackground(new Color(192, 192, 192));
		contentPane.add(btnCancelar, BorderLayout.SOUTH);
		
		// Añadir todos los mensajes
		SwingUtilities.invokeLater(() -> {
			addMensajes();
		});
	}
	
	/**
	 * panelConLista se inicializa como un JScrollPane con una lista de mensajes filtrados según <code>modelo</code>
	 */
	private void listaDeMensajesConScroll() {
		panelConLista = new JScrollPane();
		panelConLista.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panelConLista.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		// Crear un modelo de lista
		modelo = new DefaultListModel<Mensaje>();
        
        // Crear el JList basado en el modelo
        listaMensajes = new JList<>(modelo);
        listaMensajes.setBackground(new Color(238, 238, 238));
        listaMensajes.setCellRenderer(new MensajeCellRenderer4());
        listaMensajes.setLayoutOrientation(JList.VERTICAL);
        listaMensajes.setFixedCellWidth(panelConLista.getWidth() -5); // Mensajes no se pasen del ancho + 5 píxeles extra de margen
        panelConLista.setViewportView(listaMensajes);
        
        addMensajes();
	}
	
	/**
	 * @return Panel con campos para filtrar mensajes, y un botón que los filtra actualizando <code>modelo</code>.
	 */
	
	private JPanel panelBusqueda() {
		JPanel panelBusqueda = new JPanel();
		panelBusqueda.setLayout(new BoxLayout(panelBusqueda, BoxLayout.Y_AXIS));
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		panelBusqueda.add(verticalStrut_1);
		
		// Filtro texto
		JPanel panelTexto = new JPanel();
		panelBusqueda.add(panelTexto);
		panelTexto.setLayout(new BoxLayout(panelTexto, BoxLayout.X_AXIS));
		
		JLabel lblTexto = new JLabel("Texto: ");
		panelTexto.add(lblTexto);
		
		fieldTexto = new JTextField();
		panelTexto.add(fieldTexto);
		fieldTexto.setToolTipText("");
		
		// 
		Component verticalStrut = Box.createVerticalStrut(20);
		verticalStrut.setPreferredSize(new Dimension(0, 10));
		panelBusqueda.add(verticalStrut);
		
		//
		JPanel panelTlfContacto = new JPanel();
		panelBusqueda.add(panelTlfContacto);
		panelTlfContacto.setLayout(new BoxLayout(panelTlfContacto, BoxLayout.X_AXIS));
		
		// Filtro teléfono
		JLabel lblTelefono = new JLabel("Teléfono: ");
		panelTlfContacto.add(lblTelefono);
		
		fieldTelefono = new JTextField();
		FuncionesVentanas.addManejadorFieldTelefono(fieldTelefono);
		panelTlfContacto.add(fieldTelefono);
		
		// 
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panelTlfContacto.add(horizontalStrut);
		
		// Filtro nombre del contacto
		JLabel lblContacto = new JLabel("Contacto: ");
		panelTlfContacto.add(lblContacto);
		
		fieldContacto = new JTextField();
		panelTlfContacto.add(fieldContacto);
		
		// 
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		verticalStrut_2.setPreferredSize(new Dimension(0, 10));
		panelBusqueda.add(verticalStrut_2);
		
		// Botón buscar mensajes
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnBuscar.setBackground(FuncionesVentanas.COLOR_ROSA);
		btnBuscar.addActionListener(ev -> {
			addMensajes();
		});
		panelBusqueda.add(btnBuscar);
		
		return panelBusqueda;
	}
	
	/**
	 * Elimina los mensajes del modelo, y añade los mensajes que cumplen los filtros indicados.
	 */
	private void addMensajes() {
		modelo.removeAllElements();
		Controlador.getUnicaInstancia().getMensajesFiltrados(fieldContacto.getText(), fieldTexto.getText(), fieldTelefono.getText()).stream().forEachOrdered(modelo::addElement);
	}

}
