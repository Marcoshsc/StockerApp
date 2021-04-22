package fornecedor;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;

import br.ufop.stocker.model.Fornecedor;
import produto.ProductForm;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FornecedorEscolhe extends JFrame {

	private Fornecedor fornecedor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FornecedorEscolhe window = new FornecedorEscolhe();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @param for1 
	 */
	public FornecedorEscolhe(Fornecedor for1) {
		fornecedor = for1;
		initialize();
	}
	public FornecedorEscolhe() {
		initialize();
	}
	
	
	
	
	/**
	 * Initialize the contents of the
	 */
	private void initialize() {
		setBounds(100, 100, 686, 425);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		getContentPane().setLayout(null);
		
		JButton editar = new JButton("Editar");
		editar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FornecedorForm(true, fornecedor, "LISTA_FORNECEDOR").setVisible(true);
				dispose();
			}
		});
		editar.setFont(new Font("Dialog", Font.BOLD, 16));
		editar.setBorderPainted(false);
		editar.setBackground(new Color(135, 206, 250));
		editar.setBounds(10, 130, 650, 49);
		getContentPane().add(editar);
		
		JButton cadastrar_p = new JButton("Cadastrar Produto");
		cadastrar_p.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FornecedorListProdutos(fornecedor).setVisible(true);
				dispose();
			}
		});
		cadastrar_p.setFont(new Font("Dialog", Font.BOLD, 16));
		cadastrar_p.setBorderPainted(false);
		cadastrar_p.setBackground(new Color(135, 206, 250));
		cadastrar_p.setBounds(10, 228, 650, 49);
		getContentPane().add(cadastrar_p);
		
		JLabel lblEscolhaSeDeseja = new JLabel("Escolha ");
		lblEscolhaSeDeseja.setHorizontalAlignment(SwingConstants.CENTER);
		lblEscolhaSeDeseja.setFont(new Font("Dialog", Font.BOLD, 20));
		lblEscolhaSeDeseja.setBounds(10, 49, 650, 32);
		getContentPane().add(lblEscolhaSeDeseja);
		
		JButton ver_produdos = new JButton("Ver Produtos Cadastrados");
		ver_produdos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FornecedorListPContido(fornecedor).setVisible(true);
				dispose();
			}
		});
		ver_produdos.setFont(new Font("Dialog", Font.BOLD, 16));
		ver_produdos.setBorderPainted(false);
		ver_produdos.setBackground(new Color(135, 206, 250));
		ver_produdos.setBounds(10, 326, 650, 49);
		getContentPane().add(ver_produdos);
	}
}
