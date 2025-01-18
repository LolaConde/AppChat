package vista;

import java.awt.Font;
import java.awt.Image;
import java.net.URL;
import java.time.format.DateTimeFormatter;
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
import javax.swing.JFileChooser;

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
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

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
	private static final ImageIcon ICONO_EMOTICONOS = new ImageIcon(VentanaPrincipal.class.getResource("/vista/imagenes/emoticonos.png"));
	JPanel panelDelChat;
	JPanel panelCentral;
	JLabel lblImagen;
	JScrollPane scrollPane;
	private static final int TAMANO_LETRA_MENSAJES = 12;
	private JTextField textField;
	private JList<Mensaje> lista;
	private JPopupMenu popupMenuEnChat;
	private DefaultListModel<Mensaje> modelo;
	private Usuario usuarioConChatMostrado = null;
	private Mensaje ultimoMensajeChatMostrado = null;
	JMenuItem opcionAddContacto;
	JMenuItem opcionExportarPdf;
	private JPanel panelEnviarMensaje;
	
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
		
		// Escribir mensaje/emoticonos
		panelEnviarMensaje = new JPanel();
		panelEnviarMensaje.setVisible(false); // No se muestra hasta que no se selecciona un chat
		panelEnviarMensaje.setLayout(new BoxLayout(panelEnviarMensaje, BoxLayout.Y_AXIS));
		panelCentral.add(panelEnviarMensaje, BorderLayout.SOUTH);
		
		JPanel panelEscribirMensaje = new JPanel();
		panelEscribirMensaje.setLayout(new BoxLayout(panelEscribirMensaje, BoxLayout.X_AXIS));
		panelEnviarMensaje.add(panelEscribirMensaje);
		
		textField = new JTextField();
		addManejadorEnviarMensaje();
		textField.setColumns(10);
		panelEscribirMensaje.add(textField);
		
		JButton btnMostrarEmoticonos = new JButton("");
		btnMostrarEmoticonos.setIcon(ICONO_EMOTICONOS);
		btnMostrarEmoticonos.setBackground(FuncionesVentanas.COLOR_ROSA);
		panelEscribirMensaje.add(btnMostrarEmoticonos);
		
		JScrollPane scrollPaneEmoticonos = scrollPaneEmoticonos();
		panelEnviarMensaje.add(scrollPaneEmoticonos);
		
		btnMostrarEmoticonos.addActionListener(ev -> {
			scrollPaneEmoticonos.setVisible(!scrollPaneEmoticonos.isVisible());
			panelCentral.revalidate();
			panelCentral.repaint();
		});
		
		// Mensajes del chat actual
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		
		panelDelChat = new JPanel();
		panelDelChat.setLayout(new BoxLayout(panelDelChat, BoxLayout.Y_AXIS));
		panelDelChat.setSize(400,700);
		scrollPane.setViewportView(panelDelChat);
		
		// PopupMenu: Añadir contacto / Exportar conversación pdf
		popupMenuEnChat = new JPopupMenu();
		opcionAddContacto = new JMenuItem("Añadir contacto");
		addManejadorAddContacto(opcionAddContacto);
		opcionExportarPdf = new JMenuItem("Exportar conversación en PDF");
		addManejadorExportarConversacionPDF(opcionExportarPdf);
		popupMenuEnChat.add(opcionAddContacto);
		popupMenuEnChat.add(opcionExportarPdf);
		panelDelChat.add(popupMenuEnChat);
		
		panelDelChat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e) && usuarioConChatMostrado != null) {
					boolean usuarioNoAgregado = !Controlador.getUnicaInstancia().getNombreDeContacto(usuarioConChatMostrado).isPresent();
					boolean premium = Controlador.getUnicaInstancia().isUsuarioActualPremium();
					if(usuarioNoAgregado || premium) {
						opcionAddContacto.setVisible(usuarioNoAgregado);
						opcionExportarPdf.setVisible(premium);
						popupMenuEnChat.show(panelDelChat, e.getX(), e.getY());
					}
				}
			}
		});
		
		panelCentral.add(scrollPane, BorderLayout.CENTER);
		
	}
	
	/**
	 * Si hay un chat seleccionado, se envía el mensaje <code>mensaje</code> a este, se muestra en 
	 * el chat actual (<code>panelDelChat</code>), y se actualiza <code>lista</code> mostrando 
	 * el mensaje enviado como el último del chat.
	 */
	
	private void enviarMensajeAlChatActual(String mensaje) {
		if(lista.getSelectedValue() != null) {
			Mensaje ultimoMensajeAnterior = lista.getSelectedValue();
			Mensaje ultimoMensaje = Controlador.getUnicaInstancia().enviarMensaje(ultimoMensajeAnterior, mensaje);
			
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
		}
	}
	
	/**
	 * @return Un scrollPane que muestra una serie de emoticonos, de tal forma que si se selecciona
	 * uno, se envía al chat actual.
	 */
	private JScrollPane scrollPaneEmoticonos() {
		// Lista de emoticonos
		DefaultListModel<Integer> modeloEmoticonos = new DefaultListModel<Integer>();
		for(int i=0; i<BubbleText.MAXICONO; i++) {
			modeloEmoticonos.addElement(i);
		}
		JList<Integer> listaEmoticonos = new JList<Integer>(modeloEmoticonos);
		listaEmoticonos.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		listaEmoticonos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaEmoticonos.setBackground(new Color(238, 238, 238));
		listaEmoticonos.setCellRenderer(new EmoticonoCellRenderer2());
		listaEmoticonos.setVisibleRowCount(-1);
		
		listaEmoticonos.addListSelectionListener(
				ev -> {
					if(!ev.getValueIsAdjusting()) {
						enviarMensajeAlChatActual(":"+listaEmoticonos.getSelectedValue());
					}
				}
		);
		
		// Scroll para la lista de emoticonos
		JScrollPane scrollPaneEmoticonos = new JScrollPane(listaEmoticonos);
		scrollPaneEmoticonos.setMaximumSize(new Dimension(800, 100));
		
		return scrollPaneEmoticonos;
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
					enviarMensajeAlChatActual(textField.getText());
	    			textField.setText("");
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
	 * Si se selecciona <code>menuItem</code>, exporta a pdf la conversación mostrada actualmente.
	 * Se pide el nombre del fichero donde guardar el pdf.
	 */
	private void addManejadorExportarConversacionPDF(JMenuItem menuItem) {
		menuItem.addActionListener(ev -> {
			// FileChooser
			JFileChooser chooser = new JFileChooser();
			int returnValue = chooser.showOpenDialog(this);
			File file = chooser.getSelectedFile();
			String url = file.getAbsolutePath();
		
			if (file.getParent()== null || !file.getParentFile().isDirectory()) {
				JOptionPane.showMessageDialog(this, "Se debe seleccionar un fichero (no es necesario que exista) dentro de una carpeta existente", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else if(returnValue == JFileChooser.APPROVE_OPTION && url.toLowerCase().endsWith(".pdf")) {
				// Si todo correcto, se crea el pdf
				Controlador.getUnicaInstancia().exportarPdf(usuarioConChatMostrado, url);
				JOptionPane.showMessageDialog(this, "Se ha guardado el pdf correctamente, lo encontrarás en " + url, "Información", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(this, "Se debe seleccionar un fichero acabado en .pdf (no es necesario que exista)", "Error", JOptionPane.ERROR_MESSAGE);
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
		
		// Botón consultar emoticonos
		JButton btnConsultarEmoticonos = new JButton("Consulta los emoticonos");
		btnConsultarEmoticonos.addActionListener(ev -> {
			new VentanaElegirEmoticono();
		});
		btnConsultarEmoticonos.setBackground(FuncionesVentanas.COLOR_ROSA);
		botonera.add(btnConsultarEmoticonos);
		
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
		JButton botonPremium = new JButton("");
		if(Controlador.getUnicaInstancia().isUsuarioActualPremium()){
			botonPremium.setText("Eres premium");
			botonPremium.addActionListener(ev -> {FuncionesVentanas.irAVentana(this, new VentanaPremium());});
		}
		else {
			botonPremium.setText("Hazte premium");
			botonPremium.addActionListener(ev -> {FuncionesVentanas.irAVentana(this, new VentanaPagarPremium());});
		}
		
		botonPremium.setBackground(FuncionesVentanas.COLOR_ROSA);
		botonera.add(botonPremium);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		horizontalStrut_3.setPreferredSize(new Dimension(10, 0));
		botonera.add(horizontalStrut_3);
		
		// Imagen de perfil del usuario
		lblImagen = new JLabel("");
		muestraImagenDePerfil(Controlador.getUnicaInstancia().getImagenDePerfil());
		botonera.add(lblImagen);
		
		// PopUp Menú: Cambio foto de perfil y salir (en imagen de perfil del usuario)
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem opcionCambiarFoto = new JMenuItem("Cambiar foto de perfil");
		addManejadorCambioDeImagen(opcionCambiarFoto);
		popupMenu.add(opcionCambiarFoto);
		JMenuItem opcionSalir = new JMenuItem("Log out");
		opcionSalir.addActionListener(ev -> {
			Controlador.getUnicaInstancia().logout();
			VentanaLogin ventana = new VentanaLogin();
			ventana.mostrarVentana(this);
			this.dispose();
		});
		popupMenu.add(opcionSalir);
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
		boolean mensajeEnviado = Controlador.getUnicaInstancia().isUsuarioActualEmisor(mensaje);
		// Primer mensaje o distinto día al anterior mensaje -> Día
		if(ultimoMensajeChatMostrado == null || !ultimoMensajeChatMostrado.getFechaYHora().toLocalDate().isEqual(mensaje.getFechaYHora().toLocalDate())) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			JLabel label = new JLabel(mensaje.getFechaYHora().format(formatter));
			label.setAlignmentX(Component.CENTER_ALIGNMENT);
			label.setFont(new Font("Tahoma", Font.BOLD, 12));
			panelDelChat.add(label);
		}
		// Hora + emisor
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		String hora = " (" + mensaje.getFechaYHora().format(formatter) + ")";
		String emisor = mensajeEnviado ? "Tu" : FuncionesVentanas.getNombreDeContactoOTelefono(mensaje.getEmisor());
		
		// Mensaje
		if(mensaje.getEmoticono().isPresent()) {
			panelDelChat.add(new BubbleText(panelDelChat,
					mensaje.getEmoticono().get().intValue(),
					mensajeEnviado ? Color.LIGHT_GRAY : Color.PINK,
					emisor + hora, 
					mensajeEnviado ? BubbleText.SENT : BubbleText.RECEIVED,
					TAMANO_LETRA_MENSAJES));
		}
		else {
			panelDelChat.add(new BubbleText(panelDelChat,
					mensaje.getTexto().orElse(""),
					mensajeEnviado ? Color.LIGHT_GRAY : Color.PINK,
					emisor + hora,
					mensajeEnviado ? BubbleText.SENT : BubbleText.RECEIVED,
					TAMANO_LETRA_MENSAJES));
		}
		ultimoMensajeChatMostrado = mensaje;
		
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
        		ultimoMensajeChatMostrado = null;
        		panelEnviarMensaje.setVisible(true); // No se muestra hasta que no se selecciona un chat
        		
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
        		panelEnviarMensaje.setVisible(false);
        		usuarioConChatMostrado = null;
        		ultimoMensajeChatMostrado = null;
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
		Image image = null;
		try {
			URL imageUrl = new URL(url);
			image = ImageIO.read(imageUrl);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(image == null) {
			image = FuncionesVentanas.ICONO_USUARIO_SIN_FOTO.getImage(); // Si no se puede acceder a la foto o no se puede leer
		}
		ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(25, 25, Image.SCALE_SMOOTH));
		lblImagen.setIcon(imageIcon);
	}

}
