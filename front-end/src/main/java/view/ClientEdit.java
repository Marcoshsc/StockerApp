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
import javax.swing.JTextArea;

public class ClientEdit extends JFrame {

	private JPanel contentPane;
	private JTextField textField_7;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;

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
		lblEdioDeCliente.setBounds(205, 23, 172, 26);
		contentPane.add(lblEdioDeCliente);
		
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
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setToolTipText("");
		lblNome.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNome.setBounds(51, 142, 46, 14);
		contentPane.add(lblNome);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(51, 158, 480, 25);
		contentPane.add(textField);
		
		JLabel lblCpf = new JLabel("CPF");
		lblCpf.setToolTipText("");
		lblCpf.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCpf.setBounds(51, 189, 46, 14);
		contentPane.add(lblCpf);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(51, 204, 131, 25);
		contentPane.add(textField_1);
		
		JLabel lblTel = new JLabel("Telefone");
		lblTel.setToolTipText("");
		lblTel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTel.setBounds(214, 189, 54, 14);
		contentPane.add(lblTel);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(214, 204, 131, 25);
		contentPane.add(textField_2);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setToolTipText("");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEmail.setBounds(361, 189, 46, 14);
		contentPane.add(lblEmail);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(361, 204, 172, 25);
		contentPane.add(textField_3);
		
		JLabel lblEndereco = new JLabel("Endere\u00E7o");
		lblEndereco.setToolTipText("");
		lblEndereco.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEndereco.setBounds(51, 235, 61, 14);
		contentPane.add(lblEndereco);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(51, 249, 480, 25);
		contentPane.add(textField_4);
		
		JLabel lblDescricao = new JLabel("Descri\u00E7\u00E3o");
		lblDescricao.setToolTipText("");
		lblDescricao.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDescricao.setBounds(51, 290, 61, 14);
		contentPane.add(lblDescricao);
		
		JTextArea descricaoField = new JTextArea();
		descricaoField.setColumns(10);
		descricaoField.setBounds(51, 303, 480, 49);
		contentPane.add(descricaoField);
	}
}
