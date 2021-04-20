package cliente;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClientEdit extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;

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
		setBounds(100, 100, 605, 521);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEdiçãoDeCliente = new JLabel("Edi\u00E7\u00E3o de Cliente");
		lblEdiçãoDeCliente.setFont(new Font("Dialog", Font.BOLD, 20));
		lblEdiçãoDeCliente.setBounds(209, 37, 172, 32);
		contentPane.add(lblEdiçãoDeCliente);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setToolTipText("");
		lblNome.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNome.setBounds(55, 140, 46, 14);
		contentPane.add(lblNome);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(55, 156, 480, 25);
		contentPane.add(textField);
		
		JLabel lblCpf = new JLabel("CPF");
		lblCpf.setToolTipText("");
		lblCpf.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCpf.setBounds(55, 187, 46, 14);
		contentPane.add(lblCpf);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(55, 202, 131, 25);
		contentPane.add(textField_1);
		
		JLabel lblTel = new JLabel("Telefone");
		lblTel.setToolTipText("");
		lblTel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTel.setBounds(218, 187, 54, 14);
		contentPane.add(lblTel);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(218, 202, 131, 25);
		contentPane.add(textField_2);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setToolTipText("");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEmail.setBounds(365, 187, 46, 14);
		contentPane.add(lblEmail);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(365, 202, 172, 25);
		contentPane.add(textField_3);
		
		JLabel lblEndereco = new JLabel("Endere\u00E7o");
		lblEndereco.setToolTipText("");
		lblEndereco.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEndereco.setBounds(55, 233, 61, 14);
		contentPane.add(lblEndereco);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(55, 247, 480, 25);
		contentPane.add(textField_4);
		
		JLabel lblDescricao = new JLabel("Descri\u00E7\u00E3o");
		lblDescricao.setToolTipText("");
		lblDescricao.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDescricao.setBounds(55, 288, 61, 14);
		contentPane.add(lblDescricao);
		
		JTextArea descricaoField = new JTextArea();
		descricaoField.setColumns(10);
		descricaoField.setBounds(55, 301, 480, 49);
		contentPane.add(descricaoField);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setFont(new Font("Dialog", Font.BOLD, 16));
		btnSalvar.setBorderPainted(false);
		btnSalvar.setBackground(new Color(135, 206, 250));
		btnSalvar.setBounds(386, 397, 149, 49);
		contentPane.add(btnSalvar);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnExcluir.setFont(new Font("Dialog", Font.BOLD, 16));
		btnExcluir.setBorderPainted(false);
		btnExcluir.setBackground(new Color(255, 153, 153));
		btnExcluir.setBounds(55, 397, 149, 49);
		contentPane.add(btnExcluir);
		
		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setToolTipText("");
		lblCliente.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCliente.setBounds(55, 88, 46, 14);
		contentPane.add(lblCliente);
		
		textField_5 = new JTextField();
		textField_5.setEditable(false);
		textField_5.setColumns(10);
		textField_5.setBounds(55, 104, 480, 25);
		contentPane.add(textField_5);
	}
}
