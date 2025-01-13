package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.Controlador;
import modelo.Descuento;
import modelo.DescuentoNull;
import premium.PreciosPremium;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;

import java.awt.Component;
import javax.swing.Box;
import java.awt.EventQueue;

import javax.swing.JButton;

public class VentanaPagarPremium extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPagarPremium frame = new VentanaPagarPremium();
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
	public VentanaPagarPremium() {
		setTitle("AppChat");
		FuncionesVentanas.addIcon(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 620, 392);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		// Hazte premium
		JLabel lblTitulo = new JLabel("Hazte Premium");
		lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setForeground(new Color(0, 0, 0));
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 30));
		contentPane.add(lblTitulo);
		
		Component verticalStrut = Box.createVerticalStrut(10);
		contentPane.add(verticalStrut);
		
		// Ventajas exclusivas
		JLabel lblVentajasPremium = new JLabel("Y disfrutarás de ventajas exclusivas tales cómo exportar mensajes a PDF");
		lblVentajasPremium.setForeground(new Color(255, 0, 255));
		lblVentajasPremium.setFont(new Font("Dialog", Font.BOLD, 12));
		lblVentajasPremium.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblVentajasPremium);
		
		Component verticalGlue = Box.createVerticalGlue();
		contentPane.add(verticalGlue);
		
		// Descuento
		JLabel lblTituloDescuento = new JLabel("Descuento a aplicar:");
		lblTituloDescuento.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblTituloDescuento.setBounds(157, 6, 143, 19);
		lblTituloDescuento.setFont(new Font("Dialog", Font.BOLD, 14));
		contentPane.add(lblTituloDescuento);
		
		Component verticalStrut_4 = Box.createVerticalStrut(10);
		contentPane.add(verticalStrut_4);
		
		JLabel lblDescuentoMenor = new JLabel("(se aplica el que menor precio final dé)");
		lblDescuentoMenor.setFont(new Font("Dialog", Font.PLAIN, 10));
		lblDescuentoMenor.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblDescuentoMenor);
		
		Component verticalStrut_1 = Box.createVerticalStrut(10);
		contentPane.add(verticalStrut_1);
		
		// Explicación del descuento
		Descuento descuento = Controlador.getUnicaInstancia().obtenerMejorDescuentoDisponible();
		String explicacionNoDescuento = "Lo siento, no has tenido suerte. No cumples la condición para obtener un descuento. Pero no te desanimes, ¡Quizás el año que viene tengas más suerte!";
		String explicacionDescuento = descuento instanceof DescuentoNull ? explicacionNoDescuento : descuento.toString();
		
		JLabel lblExplicacionDescuento = new JLabel("<html><center>" + explicacionDescuento + "</center></html>");
		lblExplicacionDescuento.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblExplicacionDescuento.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblExplicacionDescuento.setHorizontalAlignment(SwingConstants.CENTER);
		lblExplicacionDescuento.setHorizontalTextPosition(SwingConstants.CENTER);
		contentPane.add(lblExplicacionDescuento);
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_2);
		
		JLabel lblNewLabel = new JLabel("Precio final:");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel);
		
		Component verticalStrut_3 = Box.createVerticalStrut(10);
		contentPane.add(verticalStrut_3);
		
		JLabel lblNewLabel_1 = new JLabel(descuento.calcularDescuento(PreciosPremium.PRECIO_PREMIUM) + " €");
		lblNewLabel_1.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel_1);
		
		Component verticalGlue_1 = Box.createVerticalGlue();
		contentPane.add(verticalGlue_1);
		
		JPanel panelPagarCancelar = new JPanel();
		contentPane.add(panelPagarCancelar);
		panelPagarCancelar.setLayout(new BoxLayout(panelPagarCancelar, BoxLayout.X_AXIS));
		
		// No pagar premium
		JButton btnCancelar = new JButton("Otra vez será");
		btnCancelar.setForeground(new Color(92, 92, 92));
		btnCancelar.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		btnCancelar.setBackground(new Color(212, 212, 212));
		btnCancelar.addActionListener(ev -> {FuncionesVentanas.irAVentana(this, new VentanaPrincipal());});
		panelPagarCancelar.add(btnCancelar);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panelPagarCancelar.add(horizontalStrut);
		
		// Pagar premium
		JButton btnPagarPremium = new JButton("Pagar Premium");
		btnPagarPremium.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		btnPagarPremium.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnPagarPremium.setBackground(FuncionesVentanas.COLOR_ROSA);
		btnPagarPremium.addActionListener(ev -> {
			Controlador.getUnicaInstancia().hacerPremium(descuento);
			FuncionesVentanas.irAVentana(this, new VentanaEnhorabuenaPremium());
		});
		panelPagarCancelar.add(btnPagarPremium);
		
		
		
		
	}

}
