package vista;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.Controlador;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.GrupoDeContactos;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class VentanaContactos extends JFrame {

	private JPanel contentPane;
	private DefaultListModel<Contacto> modeloTodosContactos;
	private JList<Contacto> listaTodosContactos;
	private DefaultListModel<Contacto> modeloContactosDeGrupo;
	private JList<Contacto> listaContactosDeGrupo;

	/**
	 * Create the frame.
	 */
	public VentanaContactos() {
		setTitle("AppChat");
		FuncionesVentanas.addIcon(this);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		contentPane.add(panelMostrarContactos(), BorderLayout.CENTER);
		contentPane.add(panelAddContactos(), BorderLayout.SOUTH);
	}
	
	private JPanel panelMostrarContactos(){
		JPanel panelMostrarContactos = new JPanel();
		panelMostrarContactos.setLayout(new BoxLayout(panelMostrarContactos, BoxLayout.X_AXIS));
		
		// Panel con todos los contactos (individual y grupo)
		JPanel panelTodosLosContactos = new JPanel();
		panelTodosLosContactos.setLayout(new BorderLayout(0, 0));
		panelMostrarContactos.add(panelTodosLosContactos);
		
		crearListaDeContactos();
		JScrollPane scrollTodosLosContactos = new JScrollPane(listaTodosContactos);
		scrollTodosLosContactos.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panelTodosLosContactos.add(scrollTodosLosContactos, BorderLayout.CENTER);
		
		// Panel con los contactos de un grupo
		JPanel panelContactosDeGrupo = new JPanel();
		panelMostrarContactos.add(panelContactosDeGrupo);
		crearListaDeContactosVacia();
		panelContactosDeGrupo.add(listaContactosDeGrupo);
		
		addManejadorMostrarContactosDeGrupo();
		
		return panelMostrarContactos;
	}
	
	/**
	 * Inicializa <code>listaTodosContactos</code> con los contactos del usuario actual
	 */
	private void crearListaDeContactos() {
		// Crear un modelo de lista y agregar elementos
        modeloTodosContactos = new DefaultListModel<>();
        Controlador.getUnicaInstancia().getContactos().stream().forEachOrdered(modeloTodosContactos::addElement);
        
        // Crear el JList basado en el modelo
        listaTodosContactos = new JList<>(modeloTodosContactos);
        listaTodosContactos.setBackground(new Color(238, 238, 238));
        listaTodosContactos.setCellRenderer(new ContactoCellRenderer());
	}
	/**
	 * Inicializa <code>listaContactosDeGrupo</code> con una lista vacía
	 */
	private void crearListaDeContactosVacia() {
		// Crear un modelo de lista
        modeloContactosDeGrupo = new DefaultListModel<>();
        
        // Crear el JList basado en el modelo
        listaContactosDeGrupo = new JList<>(modeloContactosDeGrupo);
        listaContactosDeGrupo.setBackground(new Color(238, 238, 238));
        listaContactosDeGrupo.setCellRenderer(new ContactoCellRenderer());
	}

	
	/** Crea el listener de <code>listaTodosContactos</code> para,
	 * en el caso de seleccionar un grupo, mostrar sus contactos individuales.
	 * 
	 * Estos se muestran en la lista <code>listaContactosDeGrupo</code>.
	 */
	private void addManejadorMostrarContactosDeGrupo() {
		listaTodosContactos.addListSelectionListener(ev -> {
			Contacto contactoSeleccionado = listaTodosContactos.getSelectedValue();
			modeloContactosDeGrupo.removeAllElements();
        	if(contactoSeleccionado != null && contactoSeleccionado instanceof GrupoDeContactos) {
    			List<ContactoIndividual> contactosAMostrar = ((GrupoDeContactos) contactoSeleccionado).getContactos();
    			contactosAMostrar.stream().forEach(modeloContactosDeGrupo::addElement);
    		}
    		listaContactosDeGrupo.revalidate();
			listaContactosDeGrupo.repaint();
			
        });
	}
	
	private JPanel panelAddContactos() {
		JPanel panelAddContactos = new JPanel();
		panelAddContactos.setLayout(new BoxLayout(panelAddContactos, BoxLayout.X_AXIS));
		
		Component horizontalGlue_2 = Box.createHorizontalGlue();
		panelAddContactos.add(horizontalGlue_2);
		
		// Botón añadir contacto
		JButton btnAddContactoIndividual = new JButton("Añadir Contacto");
		btnAddContactoIndividual.setBackground(FuncionesVentanas.COLOR_ROSA);
		addManejadorAñadirContactoIndividual(btnAddContactoIndividual);
		panelAddContactos.add(btnAddContactoIndividual);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panelAddContactos.add(horizontalGlue);
		
		// Botón añadir grupo
		JButton btnAddGrupo = new JButton("Añadir Grupo");
		btnAddGrupo.addActionListener(ev -> {
			FuncionesVentanas.irAVentana(this, new VentanaAddGrupo());
		});
		btnAddGrupo.setBackground(FuncionesVentanas.COLOR_ROSA);
		panelAddContactos.add(btnAddGrupo);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panelAddContactos.add(horizontalGlue_1);
		
		// Botón ir a la ventana principal
		JButton btnIrAVentanaPrincipal = new JButton("Volver a la ventana principal");
		btnIrAVentanaPrincipal.addActionListener(ev -> {
			FuncionesVentanas.irAVentana(this, new VentanaPrincipal());
		});
		btnIrAVentanaPrincipal.setBackground(FuncionesVentanas.COLOR_ROSA);
		panelAddContactos.add(btnIrAVentanaPrincipal);
		
		Component horizontalGlue_3 = Box.createHorizontalGlue();
		panelAddContactos.add(horizontalGlue_3);
		
		return panelAddContactos;
	}
	
	/**
	 * Si se pulsa <code>boton</code>, pide los datos al usuario y, si son correctos, añade un contacto individual
	 * @param boton
	 */
	private void addManejadorAñadirContactoIndividual(JButton boton) {
		boton.addActionListener(ev -> {
			// Crea JOptionPane preguntando número y nombre
			JTextField fieldNumero = new JTextField();
			JTextField fieldNombreContacto = new JTextField();
			
			Object[] inputs = {
					"Número: ", fieldNumero,
					"Nombre del nuevo contacto: ", fieldNombreContacto,
			};
			
			int option = JOptionPane.showConfirmDialog(this, inputs, "Añadir nuevo contacto", JOptionPane.OK_CANCEL_OPTION);
			
			if(option == JOptionPane.OK_OPTION) {
				// Se dió a crear
				String nombreContacto = fieldNombreContacto.getText();
				nombreContacto = nombreContacto.replaceAll("\\s+", " ").trim();
				String stringNumero = fieldNumero.getText();
				
				if(nombreContacto.length() == 0) {
					JOptionPane.showMessageDialog(this, "No se ha podido añadir el contacto, el nombre no puede estar vacío", "Error con el nombre", JOptionPane.ERROR_MESSAGE);
				} else if(!stringNumero.matches("^[0-9]+$")) {
					JOptionPane.showMessageDialog(this, "No se ha podido añadir el contacto, el número debe contener números y ningún otro caracter", "Error con el número", JOptionPane.ERROR_MESSAGE);
				} else {
					int telefono = Integer.parseInt(stringNumero);
					boolean existeNumero = Controlador.getUnicaInstancia().addContactoIndividual(telefono, nombreContacto);
					if(existeNumero) {
						// Campos correctos. Contacto se ha creado
						JOptionPane.showMessageDialog(this, "Contacto guardado", "Información", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(this, "El teléfono indicado no existe", "Alert", JOptionPane.ERROR_MESSAGE);
					}
				}
				// Actualizar la vista
				modeloTodosContactos.removeAllElements();
		        Controlador.getUnicaInstancia().getContactos().stream().forEachOrdered(modeloTodosContactos::addElement);
				
		        listaTodosContactos.revalidate();
				listaTodosContactos.repaint();
			}
		});
	}

}
