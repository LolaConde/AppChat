package vista;

import java.awt.EventQueue;
import java.awt.Image;
import java.net.URL;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import controlador.Controlador;
import modelo.Mensaje;
import modelo.Usuario;

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.ImageIcon;

import tds.BubbleText;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * Ventana principal de la aplicación.
 */
public class VentanaPrincipal extends JFrame {

	private JPanel contentPane;
	private static final ImageIcon ICONO_ENVIAR_MENSAJE = new ImageIcon(VentanaPrincipal.class.getResource("/vista/imagenes/enviar.png"));
	private static final ImageIcon ICONO_BUSCAR_MENSAJES = new ImageIcon(VentanaPrincipal.class.getResource("/vista/imagenes/buscar.png"));
	private static final ImageIcon ICONO_AGENDA_CONTACTOS = new ImageIcon(VentanaPrincipal.class.getResource("/vista/imagenes/agenda.png"));
	JPanel panelDelChat;
	JPanel panelCentral;
	JLabel lblImagen;
	JScrollPane scrollPane;
	private static final int TAMANO_LETRA_MENSAJES = 14;
	private JTextField textField;
	private JList<Mensaje> lista;
	private JPopupMenu popupMenuAddContacto;
	private DefaultListModel<Mensaje> modelo;
	private Usuario usuarioConChatMostrado = null;
	
	/**
	 * Crea la ventana
	 */
	public VentanaPrincipal() {
		setTitle("AppChat");
		FuncionesVentanas.addIcon(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 803, 555);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		contentPane.add(panelChatsRecientes(), BorderLayout.WEST);
		contentPane.add(botonera(), BorderLayout.NORTH);
		
		crearPanelCentral();
		contentPane.add(panelCentral, BorderLayout.CENTER);
	}
	
	private void crearPanelCentral() {
		panelCentral = new JPanel();
		panelCentral.setLayout(new BorderLayout(0, 0));
		
		// Escribir mensaje
		textField = new JTextField();
		addManejadorEnviarMensaje();
		textField.setColumns(10);
		textField.setVisible(false); // No se muestra hasta que no se selecciona un chat
		panelCentral.add(textField, BorderLayout.SOUTH);
		
		// Mensajes del chat actual
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		panelDelChat = new JPanel();
		panelDelChat.setLayout(new BoxLayout(panelDelChat, BoxLayout.Y_AXIS));
		panelDelChat.setSize(400,700);
		scrollPane.setViewportView(panelDelChat);
		
		// PopupMenu: Añadir contacto
		popupMenuAddContacto = new JPopupMenu();
		JMenuItem opcionAddContacto = new JMenuItem("Añadir contacto");
		addManejadorAddContacto(opcionAddContacto);
		popupMenuAddContacto.add(opcionAddContacto);
		panelDelChat.add(popupMenuAddContacto);
		
		panelDelChat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Sólo si el usuario no está agregado
				if(SwingUtilities.isRightMouseButton(e) && usuarioConChatMostrado != null && !Controlador.getUnicaInstancia().getNombreDeContacto(usuarioConChatMostrado).isPresent())
					popupMenuAddContacto.show(panelDelChat, e.getX(), e.getY());
			}
		});
		
		panelCentral.add(scrollPane, BorderLayout.CENTER);
		
	}
	
	/**
	 * Añade un manejador de eventos a <code>textField</code> para enviar mensajes
	 * 
	 * <p> Funcionalidad:
	 * <ul>
	 * 	<li> Envía un mensaje en el chat actual al darle a Enter.</li>
	 *  <li> Muestra el mensaje enviado en el chat actual (<code>panelDelChat</code>).</li>
	 *  <li> Actualiza <code>lista</code> mostrando el mensaje enviado como el último del chat.</li>
	 * </ul>
	 * </p>
	 */
	private void addManejadorEnviarMensaje() {
		textField.addActionListener(
				ev -> {
					Mensaje ultimoMensajeAnterior = lista.getSelectedValue();
					Mensaje ultimoMensaje = Controlador.getUnicaInstancia().enviarMensaje(ultimoMensajeAnterior, textField.getText());
					textField.setText("");
					
					mostrarMensaje(ultimoMensaje);
					SwingUtilities.invokeLater(() -> {
				        JScrollBar vertical = scrollPane.getVerticalScrollBar();
				        vertical.setValue(vertical.getMaximum());
				    });
					
					modelo.removeElement(ultimoMensajeAnterior);
					modelo.add(0,ultimoMensaje);
					lista.setSelectedIndex(0);
	    			lista.revalidate();
	    			lista.repaint();
				});
	}
	
	/**
	 * Si se selecciona <code>menuItem</code>, se pide el nombre de contacto y se añade el contacto del usuario cuyo chat se muestra actualmente.
	 */
	private void addManejadorAddContacto(JMenuItem menuItem) {
		menuItem.addActionListener(ev -> {
			String nombreDeContacto = JOptionPane.showInputDialog(this, "Introduce el nombre del contacto:");
			nombreDeContacto = nombreDeContacto.replaceAll("\\s+", " ").trim();
			if (nombreDeContacto != null) {
				if(nombreDeContacto.length() == 0) {
					JOptionPane.showMessageDialog(this, "No se ha podido añadir el contacto, el nombre no puede estar vacío", "Error con el nombre", JOptionPane.ERROR_MESSAGE);
				}else {
					Controlador.getUnicaInstancia().addContactoIndividual(usuarioConChatMostrado, nombreDeContacto);
					JOptionPane.showMessageDialog(this, "Contacto añadido correctamente", "Información", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}
	
	/**
	 * @return JPanel con botones útiles para el usuario
	 */
	private JPanel botonera() {
		JPanel botonera = new JPanel();
		botonera.setBorder(new EmptyBorder(0, 0, 10, 0));
		botonera.setLayout(new BoxLayout(botonera, BoxLayout.X_AXIS));
		
		// Lista de contactos
		JComboBox comboBox = new JComboBox();
		comboBox.setMaximumSize(new Dimension(500, 5120));
		comboBox.setBackground(FuncionesVentanas.COLOR_ROSA);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"contacto 1", "contacto 2", "contacto 3"}));
		botonera.add(comboBox);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setPreferredSize(new Dimension(10, 0));
		horizontalStrut.setMaximumSize(new Dimension(10, 32767));
		horizontalStrut.setMinimumSize(new Dimension(10, 0));
		botonera.add(horizontalStrut);
		
		// Botón de enviar mensaje
		JButton botonEnviar = new JButton("");
		botonEnviar.setIcon(ICONO_ENVIAR_MENSAJE);
		botonEnviar.setBackground(FuncionesVentanas.COLOR_ROSA);
		botonEnviar.addActionListener(ev -> {
			VentanaEnviarMensaje nuevaVentana = new VentanaEnviarMensaje();
	        nuevaVentana.setVisible(true);
	        nuevaVentana.setLocationRelativeTo(this);
			this.dispose();
		});
		botonera.add(botonEnviar);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalStrut_1.setPreferredSize(new Dimension(10, 0));
		botonera.add(horizontalStrut_1);
		
		// Botón de buscar mensajes
		JButton botonBuscar = new JButton("");
		botonBuscar.setIcon(ICONO_BUSCAR_MENSAJES);
		botonBuscar.setBackground(FuncionesVentanas.COLOR_ROSA);
		botonBuscar.addActionListener(ev -> {FuncionesVentanas.irAVentana(this, new VentanaBuscarMensajes());});
		botonera.add(botonBuscar);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		horizontalStrut_2.setPreferredSize(new Dimension(10, 0));
		botonera.add(horizontalStrut_2);
		
		// Botón de agenda de contactos
		JButton botonContactos = new JButton("");
		botonContactos.setIcon(ICONO_AGENDA_CONTACTOS);
		botonContactos.setBackground(FuncionesVentanas.COLOR_ROSA);
		botonContactos.addActionListener(ev -> {
			VentanaContactos nuevaVentana = new VentanaContactos();
	        nuevaVentana.setVisible(true);
	        nuevaVentana.setLocationRelativeTo(this);
			this.dispose();
		});
		botonera.add(botonContactos);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		botonera.add(horizontalGlue);
		
		// Botón de Premium
		JButton botonPremium = new JButton("Hazte premium");
		botonPremium.setBackground(FuncionesVentanas.COLOR_ROSA);
		botonPremium.addActionListener(ev -> {FuncionesVentanas.irAVentana(this, new VentanaPagarPremium());});
		botonera.add(botonPremium);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		horizontalStrut_3.setPreferredSize(new Dimension(10, 0));
		botonera.add(horizontalStrut_3);
		
		// Imagen de perfil del usuario
		lblImagen = new JLabel("");
		muestraImagenDePerfil(Controlador.getUnicaInstancia().getImagenDePerfil());
		botonera.add(lblImagen);
		
		// PopUp Menú: Cambio foto de perfil (en imagen de perfil del usuario)
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem opcionCambiarFoto = new JMenuItem("Cambiar foto de perfil");
		addManejadorCambioDeImagen(opcionCambiarFoto);
		popupMenu.add(opcionCambiarFoto);
		botonera.add(popupMenu);
		
		lblImagen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				popupMenu.show(lblImagen, e.getX(), e.getY());
			}
		});
		
		return botonera;
	}
	
	/**
	 * Cuando se selecciona <code>menuItem</code>, solicita una URL de una nueva imagen de perfil, 
	 * y la actualiza si es válida y accesible.
	 * 
	 * @param menuItem
	 */
	private void addManejadorCambioDeImagen(JMenuItem menuItem) {
		menuItem.addActionListener(ev -> {
			String nuevaImagen = JOptionPane.showInputDialog(this, "Introduce la url a la imagen:");
			if(nuevaImagen != null) {
				if(Controlador.getUnicaInstancia().cambiarImagenDePerfil(nuevaImagen)) {
					muestraImagenDePerfil(nuevaImagen);
					JOptionPane.showMessageDialog(this, "Cambio de imagen realizado", "Cambio realizado", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(this, "No se ha podido hacer el cambio de imagen", "Error con la imagen", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
	
	/**
	 * Muestra en el chat actual (en <code>panelDelChat</code>) el mensaje <code>mensaje</code>.
	 * @param mensaje
	 */
	private void mostrarMensaje(Mensaje mensaje) {
		if(mensaje.getEmoticono().isPresent()) {
			panelDelChat.add(new BubbleText(panelDelChat,
					mensaje.getEmoticono().get().intValue(),
					Controlador.getUnicaInstancia().isUsuarioActualEmisor(mensaje) ? Color.LIGHT_GRAY : Color.PINK,
					"", 
					Controlador.getUnicaInstancia().isUsuarioActualEmisor(mensaje) ? BubbleText.SENT : BubbleText.RECEIVED,
					TAMANO_LETRA_MENSAJES));
		}
		else {
			panelDelChat.add(new BubbleText(panelDelChat,
					mensaje.getTexto().orElse(""),
					Controlador.getUnicaInstancia().isUsuarioActualEmisor(mensaje) ? Color.LIGHT_GRAY : Color.PINK,
					"", 
					Controlador.getUnicaInstancia().isUsuarioActualEmisor(mensaje) ? BubbleText.SENT : BubbleText.RECEIVED,
					TAMANO_LETRA_MENSAJES));
		}
	}
	
	/**
	 * Inicializa <code>lista</code> con el último mensaje de cada chat
	 */
	private void crearListaDeUltimosMensajes() {
		// Crear un modelo de lista y agregar elementos
        modelo = new DefaultListModel<>();
        Controlador.getUnicaInstancia().getUltimosMensajes().stream().forEachOrdered(modelo::addElement);
        
        // Crear el JList basado en el modelo
        lista = new JList<>(modelo);
        lista.setBackground(new Color(238, 238, 238));
        lista.setCellRenderer(new MensajeCellRenderer());
	}
	
	/** Crea el listener de <code>lista</code> para mostrar los mensajes del chat seleccionado.
	 * 
	 * <p>Funcionamiento:
	 * <ul>
	 *	<li>El usuario selecciona un mensaje en <code>lista</code>.</li>
	 *	<li>Se identifica el chat al que pertenece ese mensaje.</li>
	 *	<li>Se muestran todos los mensajes de ese chat específico.</li>
	 * </ul>
	 * </p>
	 */
	private void addManejadorMostrarMensajesDeChat() {
		lista.addListSelectionListener(ev -> {
        	if(lista.getSelectedValue() != null) {
        		textField.setVisible(true); // No se muestra el textField hasta que no se selecciona un chat
        		
        		panelDelChat.removeAll();
        		
        		Mensaje mensajeSeleccionado = lista.getSelectedValue();
        		List<Mensaje> mensajesDelChat = Controlador.getUnicaInstancia().getOtrosMensajes(mensajeSeleccionado);
        		mensajesDelChat.stream().forEachOrdered(this::mostrarMensaje);
        		
        		usuarioConChatMostrado = Controlador.getUnicaInstancia().obtenerUsuario(mensajeSeleccionado);
        		
    			panelCentral.revalidate();
        		panelCentral.repaint();
        		SwingUtilities.invokeLater(() -> {
			        JScrollBar vertical = scrollPane.getVerticalScrollBar();
			        vertical.setValue(vertical.getMaximum());
			    });
    		}
        	else {
        		textField.setVisible(false);
        		usuarioConChatMostrado = null;
        	}
        });
	}
	
	/**
	 * @return JPanel con la lista de chats del usuario
	 */
	private JPanel panelChatsRecientes() {
		JPanel panelChatsRecientes = new JPanel();
		panelChatsRecientes.setLayout(new BorderLayout(0, 0));
        
		// Lista con scroll
		crearListaDeUltimosMensajes();
		addManejadorMostrarMensajesDeChat();
        JScrollPane scrollPaneUltimosMensajes = new JScrollPane(lista);
        scrollPaneUltimosMensajes.setPreferredSize(new Dimension(200, 300));
        scrollPaneUltimosMensajes.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panelChatsRecientes.add(scrollPaneUltimosMensajes, BorderLayout.CENTER);
		
		return panelChatsRecientes;
	}
	
	/**
	 * Muestra la imagen de perfil en la ventana, 
	 * actualizando el componente <code>lblImage</code> con la imagen obtenida de la URL <code>url</code>.
	 * 
	 * @param url
	 */
	private void muestraImagenDePerfil(String url) {
		Image image;
		try {
			URL imageUrl = new URL(url);
			image = ImageIO.read(imageUrl);
			
		} catch (Exception e) {
			image = FuncionesVentanas.ICONO_USUARIO_SIN_FOTO.getImage(); // Si no se puede acceder a la foto
			e.printStackTrace();
		}
		ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(25, 25, Image.SCALE_SMOOTH));
		lblImagen.setIcon(imageIcon);
	}

}
