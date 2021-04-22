package fornecedor;

import java.awt.EventQueue;



import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class FornecedorForm extends JFrame {
	private Boolean isEdit;
	private Fornecedor fornecedor;
	private int id;
	private String proximaPagina;
	private JButton btnSalvar;
	private JTextArea descricao;
	private RepositoryFactory rep = RepositoryFactory.create();
	private List<String> listNomeFornecedores = new ArrayList<String>();
	private List<Fornecedor> listFornecedor;
	private JTextField nome_fornecedor;
	private JTextField cnpj;
	private JTextField telefone;
	private JTextField email;
	private JTextField endereco;
	
	

	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FornecedorForm window = new FornecedorForm();
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
	
	public FornecedorForm() {
		this(false, null, "LISTA_FORNECEDOR");
	}

	public FornecedorForm(Boolean isEdit, Fornecedor fornecedor, String proximaPagina) {
		this.isEdit = isEdit;
		this.fornecedor = fornecedor;
		if(isEdit) this.id = fornecedor.getId();
			else this.id = -1;
		this.proximaPagina = proximaPagina;
		initialize();		
	}
	
	private void iniciarValores() {
		
		if(isEdit) {  
			nome_fornecedor.setText(fornecedor.getNome());
			descricao.setText(fornecedor.getDescricao());
			endereco.setText(fornecedor.getEndereco());
			telefone.setText(fornecedor.getTelefone());
			email.setText(fornecedor.getEmail());
			cnpj.setText(fornecedor.getCnpj());
		 
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
	
	
   private void salvarFornecedor() {  
	   		
//			
	   		Timestamp dataCadastro =new Timestamp(System.currentTimeMillis());;
	   		

			try {
				Fornecedor fornecedor = new Fornecedor(
						id, 
						nome_fornecedor.getText(),
						descricao.getText(),
						endereco.getText(),
						telefone.getText(),
						email.getText(),
						dataCadastro,
						cnpj.getText()
					
						
				);
				if(isEdit) rep.fornecedor().update(id, fornecedor);
					else {
						Fornecedor f = rep.fornecedor().insert(fornecedor);
						}
				
				Functions.abrirProximaPagina(proximaPagina);
				dispose();

			} catch (RepositoryActionException e1) {
				JOptionPane.showMessageDialog(null, e1.toString());
			}
	}
   
   private void apagarFormulario() {  
	   int option = JOptionPane.showConfirmDialog(
				 this,
				 "Deseja mesmo apagar?",
			     "",
			    JOptionPane.YES_NO_OPTION);
	  if(option == 0) {  
		  try {  
			  rep.fornecedor().delete(fornecedor.getId());
			  Functions.abrirProximaPagina(proximaPagina);
		  } catch (RepositoryActionException e) {  
			  JOptionPane.showMessageDialog(null, e.toString());
		  }
		  
	  }
   }
   
   
   /*
	 * Initialize the contents of the
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize() {	
		iniciarComboBox();
		setBounds(100, 100, 605, 550);
		getContentPane().setLayout(null);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	Functions.abrirProximaPagina(proximaPagina);
				dispose();
		    }
		});
		JLabel lblCadastroDeFornecedor;
		if(isEdit) {  
			lblCadastroDeFornecedor = new JLabel("Edi��o de Fornecedor");
			lblCadastroDeFornecedor.setBounds(200, 12, 276, 34);
		}
			else  {  
				lblCadastroDeFornecedor = new JLabel("Cadastro de Fornecedor");
				lblCadastroDeFornecedor.setBounds(180, 60, 276, 34);
			}
		lblCadastroDeFornecedor.setFont(new Font("Dialog", Font.BOLD, 20));
		getContentPane().add(lblCadastroDeFornecedor);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.setForeground(new Color(0, 0, 0));
		btnSalvar.setFont(new Font("Dialog", Font.BOLD, 16));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				salvarFornecedor();
			}
		});
		btnSalvar.setBackground(new Color(135, 206, 250));
		btnSalvar.setBounds(412, 434, 135, 44);
		getContentPane().add(btnSalvar);
		
		
		
		
		if(isEdit) {  
			JButton btnApagar = new JButton("Apagar");
			btnApagar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					apagarFormulario();
				}
			});
			btnApagar.setForeground(Color.BLACK);
			btnApagar.setFont(new Font("Dialog", Font.BOLD, 16));
			btnApagar.setBackground(new Color(255, 153, 153));
			btnApagar.setBounds(53, 434, 135, 44);
			getContentPane().add(btnApagar);
			
	
		}
//=======================================
		

		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setToolTipText("");
		lblNome.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNome.setBounds(53, 81, 46, 14);
		getContentPane().add(lblNome);
		
		nome_fornecedor = new JTextField();
		nome_fornecedor.setColumns(10);
		nome_fornecedor.setBounds(53, 97, 480, 25);
		getContentPane().add(nome_fornecedor);
		
		cnpj = new JTextField();
		cnpj.setColumns(10);
		cnpj.setBounds(53, 143, 131, 25);
		getContentPane().add(cnpj);
		
		JLabel lblCnpj = new JLabel("CNPJ");
		lblCnpj.setToolTipText("");
		lblCnpj.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCnpj.setBounds(53, 128, 46, 14);
		getContentPane().add(lblCnpj);
		
		telefone = new JTextField();
		telefone.setColumns(10);
		telefone.setBounds(216, 143, 131, 25);
		getContentPane().add(telefone);
		
		JLabel lblTel = new JLabel("Telefone");
		lblTel.setToolTipText("");
		lblTel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTel.setBounds(216, 128, 54, 14);
		getContentPane().add(lblTel);
		
		email = new JTextField();
		email.setColumns(10);
		email.setBounds(363, 143, 172, 25);
		getContentPane().add(email);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setToolTipText("");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEmail.setBounds(363, 128, 46, 14);
		getContentPane().add(lblEmail);
		
		endereco = new JTextField();
		endereco.setColumns(10);
		endereco.setBounds(53, 188, 480, 25);
		getContentPane().add(endereco);
		
		JLabel lblEndereco = new JLabel("Endere\u00E7o");
		lblEndereco.setToolTipText("");
		lblEndereco.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEndereco.setBounds(53, 174, 61, 14);
		getContentPane().add(lblEndereco);
		
		JLabel lblDescricao = new JLabel("Descri\u00E7\u00E3o");
		lblDescricao.setToolTipText("");
		lblDescricao.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDescricao.setBounds(53, 229, 61, 14);
		getContentPane().add(lblDescricao);
		

		descricao = new JTextArea();
		descricao.setColumns(10);
		descricao.setBounds(53, 242, 480, 118);
		getContentPane().add(descricao);
	
		
//=======================================
		iniciarValores();
	}
}
