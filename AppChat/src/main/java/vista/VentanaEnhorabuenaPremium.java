package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import tds.BubbleText;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JButton;

public class VentanaEnhorabuenaPremium extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public VentanaEnhorabuenaPremium() {
		setTitle("AppChat");
		FuncionesVentanas.addIcon(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 168);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		// Enhorabuena
		JLabel lblNewLabel = new JLabel("AppChat quiere darte la enhorabuena");
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setForeground(new Color(255, 0, 255));
		contentPane.add(lblNewLabel);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut);
		
		// Más enhorabuena
		JLabel lblNewLabel_1 = new JLabel("Ya formas parte de los usuarios Premium de AppChat. ¡Disfrútalo!");
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel_1);
		
		Component verticalStrut_1 = Box.createVerticalStrut(10);
		contentPane.add(verticalStrut_1);
		
		// Recordatorio de pagar anualmente
		JLabel lblNewLabel_2 = new JLabel("(Y no olvides pagar todos los años)");
		lblNewLabel_2.setForeground(new Color(128, 128, 128));
		lblNewLabel_2.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel_2);
		
		Component verticalStrut_2 = Box.createVerticalStrut(10);
		contentPane.add(verticalStrut_2);
		
		Component verticalGlue = Box.createVerticalGlue();
		contentPane.add(verticalGlue);
		
		// Ir a la página principal
		JButton btnNewButton = new JButton("Volver a la página principal");
		btnNewButton.setBackground(FuncionesVentanas.COLOR_ROSA);
		btnNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnNewButton.addActionListener(ev -> {FuncionesVentanas.irAVentana(this, new VentanaPrincipal());});
		
		contentPane.add(btnNewButton);
	}
	

}
