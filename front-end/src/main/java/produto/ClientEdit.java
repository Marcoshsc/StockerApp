package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;

public class ClientEdit extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientEdit frame = new ClientEdit();
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
	public ClientEdit() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 603, 519);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEdioDeCliente = new JLabel("Edi\u00E7\u00E3o de Cliente");
		lblEdioDeCliente.setFont(new Font("Dialog", Font.BOLD, 20));
		lblEdioDeCliente.setBounds(205, 23, 172, 20);
		contentPane.add(lblEdioDeCliente);
		
		JLabel lblNewLabel_1 = new JLabel("Nome");
		lblNewLabel_1.setToolTipText("");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(51, 118, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(51, 134, 480, 25);
		contentPane.add(textField);
		
		JLabel lblNewLabel_2 = new JLabel("CPF");
		lblNewLabel_2.setToolTipText("");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_2.setBounds(51, 165, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(51, 180, 131, 25);
		contentPane.add(textField_1);
		
		JLabel lblNewLabel_3 = new JLabel("Telefone");
		lblNewLabel_3.setToolTipText("");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_3.setBounds(214, 165, 54, 14);
		contentPane.add(lblNewLabel_3);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(214, 180, 131, 25);
		contentPane.add(textField_2);
		
		JLabel lblNewLabel_4 = new JLabel("Email");
		lblNewLabel_4.setToolTipText("");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_4.setBounds(361, 165, 46, 14);
		contentPane.add(lblNewLabel_4);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(361, 180, 172, 25);
		contentPane.add(textField_3);
		
		JLabel lblNewLabel_5 = new JLabel("Endere\u00E7o");
		lblNewLabel_5.setToolTipText("");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_5.setBounds(51, 211, 61, 14);
		contentPane.add(lblNewLabel_5);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(51, 225, 480, 25);
		contentPane.add(textField_4);
		
		JLabel lblNewLabel_6 = new JLabel("Descri\u00E7\u00E3o");
		lblNewLabel_6.setToolTipText("");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_6.setBounds(51, 266, 61, 14);
		contentPane.add(lblNewLabel_6);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(51, 279, 480, 49);
		contentPane.add(textField_5);
		
		JLabel lblNewLabel_6_1 = new JLabel("Data:");
		lblNewLabel_6_1.setToolTipText("");
		lblNewLabel_6_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_6_1.setBounds(427, 353, 104, 14);
		contentPane.add(lblNewLabel_6_1);
		
		textField_6 = new JTextField();
		textField_6.setEditable(false);
		textField_6.setColumns(10);
		textField_6.setBounds(427, 367, 104, 20);
		contentPane.add(textField_6);
		
		JButton btnNewButton = new JButton("Salvar");
		btnNewButton.setFont(new Font("Dialog", Font.BOLD, 16));
		btnNewButton.setBorderPainted(false);
		btnNewButton.setBackground(new Color(135, 206, 250));
		btnNewButton.setBounds(217, 408, 149, 49);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_6_1_1 = new JLabel("Cliente:");
		lblNewLabel_6_1_1.setToolTipText("");
		lblNewLabel_6_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_6_1_1.setBounds(51, 62, 104, 14);
		contentPane.add(lblNewLabel_6_1_1);
		
		textField_7 = new JTextField();
		textField_7.setEditable(false);
		textField_7.setColumns(10);
		textField_7.setBounds(51, 76, 238, 20);
		contentPane.add(textField_7);
	}
}
