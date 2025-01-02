package vista;

import java.awt.EventQueue;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import controlador.Controlador;
import modelo.Mensaje;

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
import javax.swing.AbstractListModel;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.ImageIcon;

import tds.BubbleText;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class VentanaPrincipal extends JFrame {

	private JPanel contentPane;
	private static final ImageIcon ICONO_ENVIAR_MENSAJE = new ImageIcon(VentanaPrincipal.class.getResource("/vista/imagenes/enviar.png"));
	private static final ImageIcon ICONO_BUSCAR_MENSAJES = new ImageIcon(VentanaPrincipal.class.getResource("/vista/imagenes/buscar.png"));
	private static final ImageIcon ICONO_AGENDA_CONTACTOS = new ImageIcon(VentanaPrincipal.class.getResource("/vista/imagenes/agenda.png"));
	JPanel panelChatActual;
	private static final int TAMANO_LETRA_MENSAJES = 18;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal frame = new VentanaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
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
		
		JPanel panelChatsRecientes = new JPanel();
		contentPane.add(panelChatsRecientes, BorderLayout.WEST);
		panelChatsRecientes.setLayout(new BorderLayout(0, 0));
		
		// Crear un modelo de lista y agregar elementos
        DefaultListModel<Mensaje> modelo = new DefaultListModel<>();
        for (Mensaje m : Controlador.getUnicaInstancia().getUltimosMensajes()) {
        	modelo.addElement(m);
        }
        
        // Crear el JList basado en el modelo
        JList<Mensaje> lista = new JList<>(modelo);
        lista.addListSelectionListener(ev -> {
        	if(lista.getSelectedValue() != null) {
    			panelChatActual.removeAll();
    			panelChatActual.revalidate();
    			panelChatActual.repaint();
    			for (Mensaje m : Controlador.getUnicaInstancia().getOtrosMensajes(lista.getSelectedValue())) {
    				if(m.getEmoticono().isPresent()) {
    					panelChatActual.add(new BubbleText(panelChatActual,
    							m.getEmoticono().get().intValue(),
    							Color.LIGHT_GRAY,
    							"", 
    							Controlador.getUnicaInstancia().isUsuarioActualEmisor(m) ? BubbleText.SENT : BubbleText.RECEIVED,
    							this.TAMANO_LETRA_MENSAJES));
    				}
    				else {
    					panelChatActual.add(new BubbleText(panelChatActual,
    							m.getTexto().orElse(""),
    							Color.PINK,
    							"", 
    							Controlador.getUnicaInstancia().isUsuarioActualEmisor(m) ? BubbleText.SENT : BubbleText.RECEIVED,
    							this.TAMANO_LETRA_MENSAJES));
    				}
    			}
    		}
        });
        lista.setBackground(new Color(238, 238, 238));
        lista.setCellRenderer(new MensajeCellRenderer());

		panelChatsRecientes.add(new JScrollPane(lista), BorderLayout.CENTER);
		
		panelChatActual = new JPanel();
		contentPane.add(panelChatActual, BorderLayout.CENTER);
		panelChatActual.setLayout(new BoxLayout(panelChatActual, BoxLayout.Y_AXIS));
		panelChatActual.setSize(400,700);
		
		JPanel botonera = new JPanel();
		botonera.setBorder(new EmptyBorder(0, 0, 10, 0));
		contentPane.add(botonera, BorderLayout.NORTH);
		botonera.setLayout(new BoxLayout(botonera, BoxLayout.X_AXIS));
		
		JComboBox comboBox = new JComboBox();
		comboBox.setMaximumSize(new Dimension(500, 5120));
		comboBox.setBackground(new Color(247, 210, 249));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"contacto 1", "contacto 2", "contacto 3"}));
		botonera.add(comboBox);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setPreferredSize(new Dimension(10, 0));
		horizontalStrut.setMaximumSize(new Dimension(10, 32767));
		horizontalStrut.setMinimumSize(new Dimension(10, 0));
		botonera.add(horizontalStrut);
		
		JButton botonEnviar = new JButton("");
		botonEnviar.setIcon(ICONO_ENVIAR_MENSAJE);
		botonEnviar.setBackground(new Color(247, 210, 249));
		botonera.add(botonEnviar);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalStrut_1.setPreferredSize(new Dimension(10, 0));
		botonera.add(horizontalStrut_1);
		
		JButton botonBuscar = new JButton("");
		botonBuscar.setIcon(ICONO_BUSCAR_MENSAJES);
		botonBuscar.setBackground(new Color(247, 210, 249));
		botonera.add(botonBuscar);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		horizontalStrut_2.setPreferredSize(new Dimension(10, 0));
		botonera.add(horizontalStrut_2);
		
		JButton botonContactos = new JButton("");
		botonContactos.setIcon(ICONO_AGENDA_CONTACTOS);
		botonContactos.setBackground(new Color(247, 210, 249));
		botonera.add(botonContactos);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		botonera.add(horizontalGlue);
		
		JButton botonPremium = new JButton("Premium");
		botonPremium.setBackground(new Color(247, 210, 249));
		botonera.add(botonPremium);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		horizontalStrut_3.setPreferredSize(new Dimension(10, 0));
		botonera.add(horizontalStrut_3);
		
		JLabel lblImagen = new JLabel("Imagen");
		try {
			URL imageUrl = new URL(Controlador.getUnicaInstancia().getImagenDePerfil());
			Image image = ImageIO.read(imageUrl);
			ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(25, 25, Image.SCALE_SMOOTH));
			lblImagen.setIcon(imageIcon);
			lblImagen.setText("");
		} catch (IOException e) {
			Image image = FuncionesVentanas.ICONO_USUARIO_SIN_FOTO.getImage();
			ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(25, 25, Image.SCALE_SMOOTH));
			lblImagen.setIcon(imageIcon);
			lblImagen.setText("");
			e.printStackTrace();
		}
		botonera.add(lblImagen);
	}

}
