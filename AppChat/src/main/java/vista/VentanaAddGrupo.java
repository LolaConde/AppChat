package vista;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.Controlador;
import modelo.ContactoIndividual;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import java.awt.Component;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class VentanaAddGrupo extends JFrame {

	private JPanel contentPane;
	private JTextField fieldNombreDelGrupo;
	private JTextField fieldFotoDelGrupo;
	private JList<ContactoIndividual> listaContactos;

	/**
	 * Create the frame.
	 */
	public VentanaAddGrupo() {
		setTitle("AppChat");
		FuncionesVentanas.addIcon(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		contentPane.add(panelAceptarCancelar(), BorderLayout.SOUTH);
		contentPane.add(panelNombreYFoto(), BorderLayout.NORTH);
		contentPane.add(panelIndicaContactos(), BorderLayout.CENTER);
	}
	
	/**
	 * @return Panel con scroll en el que seleccionar los contactos del grupo a crear
	 */
	private JScrollPane panelIndicaContactos() {
		JScrollPane scrollPaneContactos = new JScrollPane();
		
		// Modelo de lista con los contactos individuales del usuario actual
		DefaultListModel<ContactoIndividual> modelo = new DefaultListModel<>();
		Controlador.getUnicaInstancia().getContactos().stream()
			.filter(c -> c instanceof ContactoIndividual)
			.map(c -> (ContactoIndividual) c)
			.forEach(modelo::addElement);
		
		// Lista basada en el modelo
		listaContactos = new JList<ContactoIndividual>(modelo);
		listaContactos.setBackground(new Color(238, 238, 238));
        listaContactos.setCellRenderer(new ContactoCellRenderer());
        listaContactos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		scrollPaneContactos.setViewportView(listaContactos);
		
		return scrollPaneContactos;
	}
	
	/**
	 * @return Panel en el que indicar el nombre y foto del grupo a crear
	 */
	private JPanel panelNombreYFoto() {
		JPanel panelNombreYFoto = new JPanel();
		panelNombreYFoto.setLayout(new BoxLayout(panelNombreYFoto, BoxLayout.Y_AXIS));
		
		// Introduce el nombre
		JPanel panelNombre = new JPanel();
		panelNombreYFoto.add(panelNombre);
		panelNombre.setLayout(new BoxLayout(panelNombre, BoxLayout.X_AXIS));
		
		JLabel lblNombre = new JLabel("Introduce el nombre:");
		panelNombre.add(lblNombre);
		
		fieldNombreDelGrupo = new JTextField();
		panelNombre.add(fieldNombreDelGrupo);
		fieldNombreDelGrupo.setColumns(10);
		
		// Introduce la url de la imagen
		JPanel panelFoto = new JPanel();
		panelNombreYFoto.add(panelFoto);
		panelFoto.setLayout(new BoxLayout(panelFoto, BoxLayout.X_AXIS));
		
		JLabel lblFoto = new JLabel("Introduce la URL a la imagen (opcional): ");
		panelFoto.add(lblFoto);
		
		fieldFotoDelGrupo = new JTextField();
		panelFoto.add(fieldFotoDelGrupo);
		fieldFotoDelGrupo.setColumns(10);
		
		return panelNombreYFoto;
	}
	
	private JPanel panelAceptarCancelar() {
		JPanel panelAceptarCancelar = new JPanel();
		panelAceptarCancelar.setLayout(new BoxLayout(panelAceptarCancelar, BoxLayout.X_AXIS));
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panelAceptarCancelar.add(horizontalGlue);
		
		// Botón crear grupo
		JButton btnCrearGrupo = new JButton("Crear grupo");
		btnCrearGrupo.setBackground(FuncionesVentanas.COLOR_ROSA);
		addManejadorCrearGrupo(btnCrearGrupo);
		panelAceptarCancelar.add(btnCrearGrupo);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panelAceptarCancelar.add(horizontalGlue_1);
		
		// Botón cancelar
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBackground(FuncionesVentanas.COLOR_ROSA);
		btnCancelar.addActionListener(ev -> {
			FuncionesVentanas.irAVentana(this, new VentanaContactos());
		});
		panelAceptarCancelar.add(btnCancelar);
		
		Component horizontalGlue_2 = Box.createHorizontalGlue();
		panelAceptarCancelar.add(horizontalGlue_2);
		
		return panelAceptarCancelar;
	}
	
	/**
	 * Cuando se pulsa <code>boton</code>, se crea un grupo con los campos especificados si son correctos.
	 * @param boton
	 */
	private void addManejadorCrearGrupo(JButton boton) {
		boton.addActionListener(ev -> {
			// Nombre
			String nombreDeGrupo = fieldNombreDelGrupo.getText();
			nombreDeGrupo = nombreDeGrupo.replaceAll("\\s+", " ").trim();
			// Contactos
			List<ContactoIndividual> contactosSeleccionados = listaContactos.getSelectedValuesList();
			// Imagen
			String imagen = fieldFotoDelGrupo.getText();
			 // Si la imagen se ha introducido y es incorrecta, se dice
			boolean imagenCorrectaOEnBlanco = true;
			try {
				if(imagen.length() == 0) {
					imagenCorrectaOEnBlanco = true;
				}else {
					URL url = new URL(imagen);
					BufferedImage image = ImageIO.read(url);
					imagenCorrectaOEnBlanco = true; // No ha saltado excepción, la imagen introducida es correcta
				}
			} catch (Exception e) {
				imagenCorrectaOEnBlanco = false;
			}
			// Comprueba que todo es correcto
			if(nombreDeGrupo.length() == 0) {
				JOptionPane.showMessageDialog(this, "No se ha podido añadir el grupo, el nombre no puede estar vacío", "Error con el nombre", JOptionPane.ERROR_MESSAGE);
			} else if(contactosSeleccionados.size() == 0) {
				JOptionPane.showMessageDialog(this, "No se ha podido añadir el grupo, debe contener un contacto como mínimo", "Error con los contactos", JOptionPane.ERROR_MESSAGE);
			} else if(!imagenCorrectaOEnBlanco) {
				JOptionPane.showMessageDialog(this, "No se ha podido añadir el grupo, la url debe contener una referencia a una imagen válida y accesible, o estar en blanco", "Error con la imagen", JOptionPane.ERROR_MESSAGE);
			} else {
				// Todo correcto, vuelve a la ventana de contactos
				Controlador.getUnicaInstancia().addGrupo(contactosSeleccionados, nombreDeGrupo, imagen);
				JOptionPane.showMessageDialog(this, "Se ha creado el grupo correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
				VentanaContactos nuevaVentana = new VentanaContactos();
				nuevaVentana.setVisible(true);
				nuevaVentana.setLocationRelativeTo(this);
				this.dispose();
			}
		});
	}

}
