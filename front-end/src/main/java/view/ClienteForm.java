package view;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;


public class ClienteForm extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JTextField textField_2;
	private JLabel lblNewLabel_4;
	private JTextField textField_3;
	private JLabel lblNewLabel_5;
	private JTextField textField_4;
	private JLabel lblNewLabel_6;
	private JTextField txtDescrio;
	private JTextField textField_5;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClienteForm frame = new ClienteForm();
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
	public ClienteForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 603, 519);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Cadastro de Cliente");
		lblNewLabel.setBounds(199, 34, 193, 20);
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nome");
		lblNewLabel_1.setBounds(55, 104, 46, 14);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setToolTipText("");
		contentPane.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(55, 120, 480, 25);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(55, 166, 131, 25);
		textField_1.setColumns(10);
		contentPane.add(textField_1);
		
		lblNewLabel_2 = new JLabel("CPF");
		lblNewLabel_2.setBounds(55, 151, 46, 14);
		lblNewLabel_2.setToolTipText("");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		contentPane.add(lblNewLabel_2);
		
		lblNewLabel_3 = new JLabel("Telefone");
		lblNewLabel_3.setBounds(218, 151, 54, 14);
		lblNewLabel_3.setToolTipText("");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		contentPane.add(lblNewLabel_3);
		
		textField_2 = new JTextField();
		textField_2.setBounds(218, 166, 131, 25);
		textField_2.setColumns(10);
		contentPane.add(textField_2);
		
		lblNewLabel_4 = new JLabel("Email");
		lblNewLabel_4.setBounds(365, 151, 46, 14);
		lblNewLabel_4.setToolTipText("");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		contentPane.add(lblNewLabel_4);
		
		textField_3 = new JTextField();
		textField_3.setBounds(365, 166, 172, 25);
		textField_3.setColumns(10);
		contentPane.add(textField_3);
		
		lblNewLabel_5 = new JLabel("Endere\u00E7o");
		lblNewLabel_5.setBounds(55, 197, 61, 14);
		lblNewLabel_5.setToolTipText("");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 11));
		contentPane.add(lblNewLabel_5);
		
		textField_4 = new JTextField();
		textField_4.setBounds(55, 211, 480, 25);
		textField_4.setColumns(10);
		contentPane.add(textField_4);
		
		lblNewLabel_6 = new JLabel("Descri\u00E7\u00E3o");
		lblNewLabel_6.setBounds(55, 252, 61, 14);
		lblNewLabel_6.setToolTipText("");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 11));
		contentPane.add(lblNewLabel_6);
		
		txtDescrio = new JTextField();
		txtDescrio.setBounds(55, 265, 480, 49);
		txtDescrio.setColumns(10);
		contentPane.add(txtDescrio);
		
		JButton btnNewButton = new JButton("Salvar");
		btnNewButton.setBounds(221, 394, 149, 49);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setBackground(new Color(135, 206, 250));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setFont(new Font("Dialog", Font.BOLD, 16));
		contentPane.add(btnNewButton);
		
		textField_5 = new JTextField();
		textField_5.setEditable(false);
		textField_5.setBounds(431, 353, 104, 20);
		contentPane.add(textField_5);
		textField_5.setColumns(10);
		
		JLabel lblNewLabel_6_1 = new JLabel("Data:");
		lblNewLabel_6_1.setToolTipText("");
		lblNewLabel_6_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_6_1.setBounds(431, 339, 104, 14);
		contentPane.add(lblNewLabel_6_1);
	}
}
