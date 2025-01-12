package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.Component;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.Box;
import javax.swing.JTextArea;
import javax.swing.DropMode;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.CardLayout;
import java.awt.BorderLayout;
import javax.swing.JTextPane;

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
		
		Component verticalStrut_1 = Box.createVerticalStrut(10);
		contentPane.add(verticalStrut_1);
		
		// Explicación del descuento
		JLabel lblExplicacionDescuento = new JLabel("<html><center>Lo siento, no has tenido suerte. No cumples la condición para obtener un descuento. Pero no te desanimes, ¡Quizás el año que viene tengas más suerte!</center></html>");
		lblExplicacionDescuento.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblExplicacionDescuento.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblExplicacionDescuento.setHorizontalAlignment(SwingConstants.CENTER);
		lblExplicacionDescuento.setHorizontalTextPosition(SwingConstants.CENTER);
		contentPane.add(lblExplicacionDescuento);
		
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
			FuncionesVentanas.irAVentana(this, new VentanaEnhorabuenaPremium());
		});
		panelPagarCancelar.add(btnPagarPremium);
		
		
		
		
	}

}
