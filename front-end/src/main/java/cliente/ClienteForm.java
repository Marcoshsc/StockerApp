package cliente;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.ufop.stocker.model.Cliente;
import br.ufop.stocker.model.Produto;
import br.ufop.stocker.repository.exception.RepositoryActionException;
import br.ufop.stocker.repository.factory.RepositoryFactory;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

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
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.UIManager;


public class ClienteForm extends JFrame {

	private Boolean isEdit;
	private String proximaPagina;	
	private Cliente cliente;
	private int id;
	private RepositoryFactory rep = RepositoryFactory.create();
	private Timestamp timestamp;	

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
					ClienteForm window = new ClienteForm();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public ClienteForm() {
		this(false, null, "LISTA_CLIENTES");
	}
	public ClienteForm(Boolean isEdit, Cliente cliente, String proximaPagina)	{
		
		this.isEdit = isEdit;
		this.cliente = cliente;
		if(isEdit) this.id = cliente.getId();
			else this.id = -1;
		this.proximaPagina = proximaPagina;
		initialize();
	}
	
	private void iniciarValores() {  
		if(isEdit) {  
		  nomeField.setText(cliente.getNome());
		  cpfField.setText((cliente.getCpf()));
		  telField.setText((cliente.getTelefone()));
		  emailField.setText((cliente.getEmail()));
		  enderecoField.setText((cliente.getEndereco()));
		  descricaoField.setText(cliente.getDescricao());
		  
		}
	}
	private void salvarCliente() {  
		
		try {
			Cliente cliente = new Cliente(
					id, 
					nomeField.getText(),
					telField.getText(), 
					emailField.getText(),
					enderecoField.getText(),
					descricaoField.getText(),
					timestamp = new Timestamp(System.currentTimeMillis()),					
					cpfField.getText()
			);
			if(isEdit) rep.cliente().update(id, cliente);
				else rep.cliente().insert(cliente);
			
			Functions.abrirProximaPagina(proximaPagina);
			dispose();

		} catch (RepositoryActionException e1) {
			JOptionPane.showMessageDialog(null, e1.toString());
		}
}
	private void excluirCliente() {  
		   int option = JOptionPane.showConfirmDialog(
					 this,
					 "Deseja mesmo excluir?",
				     "",
				    JOptionPane.YES_NO_OPTION);
		  if(option == 0) {  
			  try {  
				  rep.cliente().delete(cliente.getId());
				  Functions.abrirProximaPagina(proximaPagina);
			  } catch (RepositoryActionException e) {  
				  JOptionPane.showMessageDialog(null, e.toString());
			  }
			  
		  }
	   }
	/**
	 * Create the
	 */
	
	
	private void initialize(){

		setBounds(100, 100, 603, 519);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		getContentPane().setLayout(null);
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	Functions.abrirProximaPagina(proximaPagina);
				dispose();
		    }
		});
		JLabel lblNewLabel;
		if(isEdit) {  
			lblNewLabel = new JLabel("Edição de Cliente");
			lblNewLabel.setBounds(199, 34, 193, 30);
			
		}
			else  {  
				lblNewLabel = new JLabel("Cadastro de Cliente");
				lblNewLabel.setBounds(199, 34, 193, 20);
			}
		
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		getContentPane().add(lblNewLabel);
		
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(55, 104, 46, 14);
		lblNome.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNome.setToolTipText("");
		getContentPane().add(lblNome);
		
		nomeField = new JTextField();
		nomeField.setBounds(55, 120, 480, 25);
		getContentPane().add(nomeField);
		nomeField.setColumns(10);
		
		cpfField = new JTextField();
		cpfField.setBounds(55, 166, 131, 25);
		cpfField.setColumns(10);
		getContentPane().add(cpfField);
		
		lblCpf = new JLabel("CPF");
		lblCpf.setBounds(55, 151, 46, 14);
		lblCpf.setToolTipText("");
		lblCpf.setFont(new Font("Tahoma", Font.BOLD, 11));
		getContentPane().add(lblCpf);
		
		lblTel = new JLabel("Telefone");
		lblTel.setBounds(218, 151, 54, 14);
		lblTel.setToolTipText("");
		lblTel.setFont(new Font("Tahoma", Font.BOLD, 11));
		getContentPane().add(lblTel);
		
		telField = new JTextField();
		telField.setBounds(218, 166, 131, 25);
		telField.setColumns(10);
		getContentPane().add(telField);
		
		lblEmail = new JLabel("Email");
		lblEmail.setBounds(365, 151, 46, 14);
		lblEmail.setToolTipText("");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 11));
		getContentPane().add(lblEmail);
		
		emailField = new JTextField();
		emailField.setBounds(365, 166, 172, 25);
		emailField.setColumns(10);
		getContentPane().add(emailField);
		
		lblEndereco = new JLabel("Endereço");
		lblEndereco.setBounds(55, 197, 61, 14);
		lblEndereco.setToolTipText("");
		lblEndereco.setFont(new Font("Tahoma", Font.BOLD, 11));
		getContentPane().add(lblEndereco);
		
		enderecoField = new JTextField();
		enderecoField.setBounds(55, 211, 480, 25);
		enderecoField.setColumns(10);
		getContentPane().add(enderecoField);
		
		lblDescricao = new JLabel("Descri\u00E7\u00E3o");
		lblDescricao.setBounds(55, 252, 61, 14);
		lblDescricao.setToolTipText("");
		lblDescricao.setFont(new Font("Tahoma", Font.BOLD, 11));
		getContentPane().add(lblDescricao);
		
		descricaoField = new JTextArea();
		descricaoField.setBounds(55, 265, 480, 49);
		descricaoField.setColumns(10);
		getContentPane().add(descricaoField);
		
		JButton btnSalvar = new JButton("Salvar");
		//btnSalvar.setBounds(221, 394, 149, 49);
		btnSalvar.setBounds(382, 400, 135, 44);
		btnSalvar.setBorderPainted(false);
		btnSalvar.setBackground(new Color(135, 206, 250));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				salvarCliente();
			}
		});
		btnSalvar.setFont(new Font("Dialog", Font.BOLD, 16));
		getContentPane().add(btnSalvar);
		
		if(isEdit) {  
			JButton btnExcluir = new JButton("Excluir");
			btnExcluir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					excluirCliente();
				}
			});
			btnExcluir.setForeground(Color.BLACK);
			btnExcluir.setFont(new Font("Dialog", Font.BOLD, 16));
			btnExcluir.setBackground(new Color(255, 153, 153));
			btnExcluir.setBounds(83, 400, 135, 44);
			getContentPane().add(btnExcluir);
			
		}
		
		iniciarValores();
		
	}
	
}
