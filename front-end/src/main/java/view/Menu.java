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
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
		
		JButton btnProdutos = new JButton("Produtos");
		btnProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ProductList().frame.setVisible(true);
			}
		});
		btnProdutos.setBounds(208, 245, 155, 34);
		contentPane.add(btnProdutos);
		
		JButton btnVenda = new JButton("Venda");
		btnVenda.setBounds(208, 200, 155, 34);
		contentPane.add(btnVenda);
		
		JButton btnCompra = new JButton("Compra");
		btnCompra.setBounds(208, 155, 155, 34);
		contentPane.add(btnCompra);
		
		JButton btnFornecedores = new JButton("Fornecedores");
		btnFornecedores.setBounds(208, 290, 155, 34);
		contentPane.add(btnFornecedores);
		
		JButton btnClientes = new JButton("Clientes");
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ClienteForm().setVisible(true);
			}
		});
		btnClientes.setBounds(208, 335, 155, 34);
		contentPane.add(btnClientes);
	}
}
