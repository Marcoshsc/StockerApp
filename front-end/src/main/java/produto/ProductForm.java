package produto;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import org.checkerframework.checker.nullness.qual.Nullable;

import br.ufop.stocker.model.Fornecedor;
import br.ufop.stocker.model.Produto;
import br.ufop.stocker.repository.exception.RepositoryActionException;
import br.ufop.stocker.repository.factory.RepositoryFactory;
import br.ufop.stocker.repository.interfaces.Repository;
import utils.CustomTextField;
import utils.Functions;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.UIManager;

public class ProductForm extends JFrame {
	private Boolean isEdit;
	private Produto produto;
	private int id;
	private String proximaPagina;
	public JFrame frame;
	private JTextField nomeField;
	private JButton btnSalvar;
	private JLabel lblEstoque;
	private JTextField precoField;
	private JLabel lblDescrio;
	private JTextField estoqueField;
	private JTextArea descricaoField;
	private RepositoryFactory rep = RepositoryFactory.create();
	private List<String> listNomeFornecedores = new ArrayList<String>();
	private List<Fornecedor> listFornecedor;

	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductForm window = new ProductForm();
					window.setVisible(true);
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
		this(false, null, "LISTA_PRODUTOS");
	}

	public ProductForm(Boolean isEdit, Produto produto, String proximaPagina) {
		this.isEdit = isEdit;
		this.produto = produto;
		if(isEdit) this.id = produto.getId();
			else this.id = -1;
		this.proximaPagina = proximaPagina;
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
	
	private void iniciarComboBox() {  
		try {
			listFornecedor = new ArrayList<>(rep.fornecedor().findAll());
		} catch (RepositoryActionException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < listFornecedor.size(); i++) {  
			listNomeFornecedores.add(listFornecedor.get(i).getNome());
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
				
				Functions.abrirProximaPagina(proximaPagina);
				dispose();

			} catch (RepositoryActionException e1) {
				JOptionPane.showMessageDialog(null, e1.toString());
			}
	}
   
   private void apagarProduto() {  
	   int option = JOptionPane.showConfirmDialog(
				 this,
				 "Deseja mesmo apagar?",
			     "",
			    JOptionPane.YES_NO_OPTION);
	  if(option == 0) {  
		  try {  
			  rep.produto().delete(produto.getId());
			  Functions.abrirProximaPagina(proximaPagina);
			  dispose();
		  } catch (RepositoryActionException e) {  
			  JOptionPane.showMessageDialog(null, "Produto possui dependências com outros objetos. Não é possível excluir.");
		  }
		  
	  }
   }
   
   
   /*
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize() {	
		iniciarComboBox();
		setBounds(100, 100, 605, 550);
		getContentPane().setLayout(null);
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	Functions.abrirProximaPagina(proximaPagina);
				dispose();
		    }
		});
		JLabel lblCadastroDeProdutos;
		if(isEdit) {  
			lblCadastroDeProdutos = new JLabel("Edição de Produto");
			lblCadastroDeProdutos.setBounds(200, 12, 276, 34);
		}
			else  {  
				lblCadastroDeProdutos = new JLabel("Cadastro de Produto");
				lblCadastroDeProdutos.setBounds(180, 60, 276, 34);
			}
		lblCadastroDeProdutos.setFont(new Font("Dialog", Font.BOLD, 20));
		getContentPane().add(lblCadastroDeProdutos);
		
		nomeField = new JTextField();
		nomeField.setBounds(52, 158, 495, 27);
		getContentPane().add(nomeField);
		nomeField.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(53, 137, 60, 17);
		getContentPane().add(lblNome);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.setForeground(new Color(0, 0, 0));
		btnSalvar.setFont(new Font("Dialog", Font.BOLD, 16));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				salvarProduto();
			}
		});
		btnSalvar.setBackground(new Color(135, 206, 250));
		btnSalvar.setBounds(412, 434, 135, 44);
		getContentPane().add(btnSalvar);
		
		lblEstoque = new JLabel("Estoque");
		lblEstoque.setBounds(335, 215, 60, 17);
		getContentPane().add(lblEstoque);
		
		JLabel lblPreoUnitrio = new JLabel("Preço Unitário");
		lblPreoUnitrio.setBounds(53, 215, 110, 17);
		getContentPane().add(lblPreoUnitrio);
		precoField = CustomTextField.decimal();
		
		precoField.setColumns(10);
		precoField.setBounds(53, 236, 212, 27);
		getContentPane().add(precoField);
		
		lblDescrio = new JLabel("Descrição");
		lblDescrio.setBounds(53, 287, 60, 17);
		getContentPane().add(lblDescrio);
		
		estoqueField = CustomTextField.integer();
		estoqueField.setColumns(10);
		estoqueField.setBounds(335, 236, 212, 27);
		getContentPane().add(estoqueField);
		
		descricaoField = new JTextArea();
		descricaoField.setBounds(52, 308, 495, 84);
		getContentPane().add(descricaoField);
				
		if(isEdit) {  
			JButton btnApagar = new JButton("Apagar");
			btnApagar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					apagarProduto();
				}
			});
			btnApagar.setForeground(Color.BLACK);
			btnApagar.setFont(new Font("Dialog", Font.BOLD, 16));
			btnApagar.setBackground(new Color(255, 153, 153));
			btnApagar.setBounds(53, 434, 135, 44);
			getContentPane().add(btnApagar);
			
			JLabel lblFornecedor = new JLabel("Fornecido por:");
			lblFornecedor.setBounds(52, 73, 200, 25);
			getContentPane().add(lblFornecedor);
			
			JLabel lblNomeFornecedores = new JLabel(
				listNomeFornecedores.toString().replace("[", "").replace("]", "")
			);
			lblNomeFornecedores.setFont(new Font("Dialog", 400, 14));
			lblNomeFornecedores.setBounds(52, 100, 438, 25);
			getContentPane().add(lblNomeFornecedores);
		}
	
		iniciarValores();
	}
}
