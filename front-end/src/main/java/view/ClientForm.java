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

import utils.CustomTextField;
import utils.Functions;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextArea;



public class ClientForm extends JFrame {

	private JPanel contentPane;
	private JTextField nomeField;
	private JTextField cpfField;
	private JLabel lblCpf;
	private JLabel lblTel;
	private JLabel lblEmail;
	private JTextField emailField;
	private JLabel lblEndereco;
	private JTextField enderecoField;
	private JLabel lblDescricao;
	private JTextArea descricaoField;
	private JTextField telField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientForm frame = new ClientForm();
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
	public ClientForm() {
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
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(55, 104, 46, 14);
		lblNome.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNome.setToolTipText("");
		contentPane.add(lblNome);
		
		nomeField = new JTextField();
		nomeField.setBounds(55, 120, 480, 25);
		contentPane.add(nomeField);
		nomeField.setColumns(10);
		
		cpfField = new JTextField();
		cpfField.setBounds(55, 166, 131, 25);
		cpfField.setColumns(10);
		contentPane.add(cpfField);
		
		lblCpf = new JLabel("CPF");
		lblCpf.setBounds(55, 151, 46, 14);
		lblCpf.setToolTipText("");
		lblCpf.setFont(new Font("Tahoma", Font.BOLD, 11));
		contentPane.add(lblCpf);
		
		lblTel = new JLabel("Telefone");
		lblTel.setBounds(218, 151, 54, 14);
		lblTel.setToolTipText("");
		lblTel.setFont(new Font("Tahoma", Font.BOLD, 11));
		contentPane.add(lblTel);
		
		telField = new JTextField();
		telField.setBounds(218, 166, 131, 25);
		telField.setColumns(10);
		contentPane.add(telField);
		
		lblEmail = new JLabel("Email");
		lblEmail.setBounds(365, 151, 46, 14);
		lblEmail.setToolTipText("");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 11));
		contentPane.add(lblEmail);
		
		emailField = new JTextField();
		emailField.setBounds(365, 166, 172, 25);
		emailField.setColumns(10);
		contentPane.add(emailField);
		
		lblEndereco = new JLabel("Endere\u00E7o");
		lblEndereco.setBounds(55, 197, 61, 14);
		lblEndereco.setToolTipText("");
		lblEndereco.setFont(new Font("Tahoma", Font.BOLD, 11));
		contentPane.add(lblEndereco);
		
		enderecoField = new JTextField();
		enderecoField.setBounds(55, 211, 480, 25);
		enderecoField.setColumns(10);
		contentPane.add(enderecoField);
		
		lblDescricao = new JLabel("Descri\u00E7\u00E3o");
		lblDescricao.setBounds(55, 252, 61, 14);
		lblDescricao.setToolTipText("");
		lblDescricao.setFont(new Font("Tahoma", Font.BOLD, 11));
		contentPane.add(lblDescricao);
		
		descricaoField = new JTextArea();
		descricaoField.setBounds(55, 265, 480, 49);
		descricaoField.setColumns(10);
		contentPane.add(descricaoField);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(221, 394, 149, 49);
		btnSalvar.setBorderPainted(false);
		btnSalvar.setBackground(new Color(135, 206, 250));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnSalvar.setFont(new Font("Dialog", Font.BOLD, 16));
		contentPane.add(btnSalvar);
	}
}