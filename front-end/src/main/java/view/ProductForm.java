package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

import org.checkerframework.checker.nullness.qual.Nullable;

import br.ufop.stocker.model.Produto;
import br.ufop.stocker.repository.exception.RepositoryActionException;
import br.ufop.stocker.repository.factory.RepositoryFactory;
import br.ufop.stocker.repository.interfaces.Repository;
import utils.CustomTextField;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class ProductForm {
	private Boolean isEdit;
	private int id;
	private JFrame frame;
	private JTextField nomeField;
	private JButton btnSalvar;
	private JLabel lblEstoque;
	private JTextField precoField;
	private JLabel lblDescrio;
	private JTextField estoqueField;
	private JTextArea descricaoField;
	private RepositoryFactory rep = RepositoryFactory.create();
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductForm window = new ProductForm(true, 2);
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
		this(false, -1);
	}

	public ProductForm(Boolean isEdit, int id) {
		this.isEdit = isEdit;
		this.id = id;
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
		
		JLabel lblCadastroDeProdutos;
		if(isEdit) lblCadastroDeProdutos = new JLabel("Edição de Produto");
		else lblCadastroDeProdutos = new JLabel("Cadastro de Produto");
		lblCadastroDeProdutos.setFont(new Font("Dialog", Font.BOLD, 20));
		lblCadastroDeProdutos.setBounds(180, 12, 276, 34);
		frame.getContentPane().add(lblCadastroDeProdutos);
		
		nomeField = new JTextField();
		nomeField.setBounds(48, 97, 495, 27);
		frame.getContentPane().add(nomeField);
		nomeField.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(49, 76, 60, 17);
		frame.getContentPane().add(lblNome);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.setForeground(new Color(0, 0, 0));
		btnSalvar.setFont(new Font("Dialog", Font.BOLD, 16));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Produto produto = new Produto(
						id, 
						nomeField.getText(),
						Double.parseDouble(precoField.getText()), 
						Integer.parseInt(estoqueField.getText()),
						descricaoField.getText()
				);
				try {
					rep.produto().insert(produto);
				} catch (RepositoryActionException e1) {
					System.out.println(e1);
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
		precoField = CustomTextField.decimal();
		
		precoField.setColumns(10);
		precoField.setBounds(48, 175, 212, 27);
		frame.getContentPane().add(precoField);
		
		lblDescrio = new JLabel("Descrição");
		lblDescrio.setBounds(49, 226, 60, 17);
		frame.getContentPane().add(lblDescrio);
		
		estoqueField = CustomTextField.integer();
		estoqueField.setColumns(10);
		estoqueField.setBounds(331, 175, 212, 27);
		frame.getContentPane().add(estoqueField);
		
		descricaoField = new JTextArea();
		descricaoField.setBounds(48, 247, 495, 84);
		frame.getContentPane().add(descricaoField);
		
		
		
	}
}
