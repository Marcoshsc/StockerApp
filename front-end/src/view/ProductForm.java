package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ProductForm {

	private JFrame frame;
	private JTextField nameField;
	private JButton btnSalvar;
	private JLabel lblEstoque;
	private JTextField textField;
	private JLabel lblDescrio;
	private JTextField textField_2;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductForm window = new ProductForm();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ProductForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 611, 493);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblCadastroDeProdutos = new JLabel("Cadastro de Produtos");
		lblCadastroDeProdutos.setFont(new Font("Dialog", Font.BOLD, 20));
		lblCadastroDeProdutos.setBounds(180, 12, 276, 34);
		frame.getContentPane().add(lblCadastroDeProdutos);
		
		nameField = new JTextField();
		nameField.setBounds(48, 97, 495, 27);
		frame.getContentPane().add(nameField);
		nameField.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(49, 76, 60, 17);
		frame.getContentPane().add(lblNome);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.setForeground(new Color(0, 0, 0));
		btnSalvar.setFont(new Font("Dialog", Font.BOLD, 16));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nameField.setText("Raquel");
				
			}
		});
		btnSalvar.setBackground(new Color(135, 206, 250));
		btnSalvar.setBounds(236, 374, 135, 44);
		frame.getContentPane().add(btnSalvar);
		
		lblEstoque = new JLabel("Estoque");
		lblEstoque.setBounds(331, 154, 60, 17);
		frame.getContentPane().add(lblEstoque);
		
		JLabel lblPreoUnitrio = new JLabel("Preço Unitário");
		lblPreoUnitrio.setBounds(49, 154, 110, 17);
		frame.getContentPane().add(lblPreoUnitrio);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(48, 175, 212, 27);
		frame.getContentPane().add(textField);
		
		lblDescrio = new JLabel("Descrição");
		lblDescrio.setBounds(49, 226, 60, 17);
		frame.getContentPane().add(lblDescrio);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(48, 247, 495, 82);
		frame.getContentPane().add(textField_2);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(331, 175, 212, 27);
		frame.getContentPane().add(textField_1);
	}
}
