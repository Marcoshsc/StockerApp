package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

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
	private Produto produto;
	private int id;
	public JFrame frame;
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
		this(false, null);
		this.id = -1;
	}

	public ProductForm(Boolean isEdit, Produto produto) {
		this.isEdit = isEdit;
		this.produto = produto;
		this.id = produto.getId();
		initialize();		
	}
	
	private void iniciarValores() {  
		if(isEdit) {  
		  nomeField.setText(produto.getNome());
		  precoField.setText(String.valueOf(produto.getPreco()));
		  estoqueField.setText(String.valueOf(produto.getEstoque()));
		  descricaoField.setText(produto.getDescricao());
		}
	}
   private void salvarProduto() {  
			double preco = 0;
			int estoque = 0;
			try {  
				preco = Double.parseDouble(precoField.getText());
				estoque = Integer.parseInt(estoqueField.getText());
			} catch (NumberFormatException error) {  
				JOptionPane.showMessageDialog(null, error.toString());
			}
			try {
				Produto produto = new Produto(
						id, 
						nomeField.getText(),
						preco, 
						estoque,
						descricaoField.getText()
				);
				if(isEdit) rep.produto().update(id, produto);
					else rep.produto().insert(produto);
				
				new ProductList().frame.setVisible(true);
				frame.dispose();
			} catch (RepositoryActionException e1) {
				JOptionPane.showMessageDialog(null, e1.toString());
			}
	}
   
   
   /*
	 * Initialize the contents of the frame.
	 */
	private void initialize() {		
		frame = new JFrame();
		frame.setBounds(100, 100, 611, 493);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	new ProductList().frame.setVisible(true);;
				frame.dispose();
		    }
		});
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
				salvarProduto();
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
		iniciarValores();
	}
}
