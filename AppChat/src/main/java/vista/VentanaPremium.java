package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controlador.Controlador;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Component;
import java.awt.Color;
import javax.swing.Box;
import javax.swing.JButton;

public class VentanaPremium extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public VentanaPremium() {
		FuncionesVentanas.addIcon(this);
		setTitle("AppChat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		// Eres premium
		JLabel lblEresPremium = new JLabel("Premium");
		lblEresPremium.setForeground(new Color(255, 0, 255));
		lblEresPremium.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblEresPremium.setFont(new Font("Tahoma", Font.BOLD, 30));
		contentPane.add(lblEresPremium);
		
		Component verticalStrut = Box.createVerticalStrut(10);
		contentPane.add(verticalStrut);
		
		JLabel lblDisfruta = new JLabel("<html><center>¡Esperamos que estés disfrutando de la experiencia de ser un usuario premium!</center></html>\r\n");
		lblDisfruta.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblDisfruta.setHorizontalAlignment(SwingConstants.CENTER);
		lblDisfruta.setHorizontalTextPosition(SwingConstants.CENTER);
		contentPane.add(lblDisfruta);
		
		Component verticalGlue = Box.createVerticalGlue();
		contentPane.add(verticalGlue);
		
		// Descuento aplicado
		JLabel lblTituloDescuento = new JLabel("Descuento aplicado:");
		lblTituloDescuento.setFont(new Font("Dialog", Font.BOLD, 14));
		lblTituloDescuento.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblTituloDescuento);
		
		Component verticalStrut_1 = Box.createVerticalStrut(10);
		contentPane.add(verticalStrut_1);
		
		JLabel lblExplicacionDescuento = new JLabel(Controlador.getUnicaInstancia().getDescuentoUsuarioActual().toString());
		lblExplicacionDescuento.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblExplicacionDescuento.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblExplicacionDescuento);
		
		Component verticalGlue_1 = Box.createVerticalGlue();
		contentPane.add(verticalGlue_1);
		
		// Botón volver
		JButton btnVolver = new JButton("Volver a la página principal");
		btnVolver.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnVolver.setBackground(FuncionesVentanas.COLOR_ROSA);
		btnVolver.addActionListener(ev -> {FuncionesVentanas.irAVentana(this, new VentanaPrincipal());});
		contentPane.add(btnVolver);
	}

}
