package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JToggleButton;

public class Menu extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu frame = new Menu();
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
	public Menu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 603, 519);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblStocker = new JLabel("Stocker");
		lblStocker.setFont(new Font("Dialog", Font.BOLD, 24));
		lblStocker.setBounds(240, 48, 94, 31);
		contentPane.add(lblStocker);
		
		JToggleButton tglbtnNewToggleButton = new JToggleButton("Compra");
		tglbtnNewToggleButton.setBounds(208, 158, 155, 31);
		contentPane.add(tglbtnNewToggleButton);
		
		JToggleButton tglbtnVenda = new JToggleButton("Venda");
		tglbtnVenda.setBounds(208, 204, 155, 31);
		contentPane.add(tglbtnVenda);
		
		JToggleButton tglbtnProdutos = new JToggleButton("Produtos");
		tglbtnProdutos.setBounds(208, 246, 155, 31);
		contentPane.add(tglbtnProdutos);
		
		JToggleButton tglbtnFornecedores = new JToggleButton("Fornecedores");
		tglbtnFornecedores.setBounds(208, 290, 155, 31);
		contentPane.add(tglbtnFornecedores);
		
		JToggleButton tglbtnClientes = new JToggleButton("Clientes");
		tglbtnClientes.setBounds(208, 334, 155, 31);
		contentPane.add(tglbtnClientes);
	}
}
