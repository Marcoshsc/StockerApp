package view;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import compra.CompraRelatorio;
import debitosativo.DebitosAtivoUmCliente;
import debitosativo.DebitosAtivoUmFornecedor;
import debitosativo.DebitosAtivosCliente;
import debitosativo.DebitosAtivosFornecedor;
import venda.VendaRelatorio;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuRelatorio extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuRelatorio frame = new MenuRelatorio();
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
	public MenuRelatorio() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setBounds(100, 100, 603, 514);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblRelatorio = new JLabel("Relatorios");
		lblRelatorio.setFont(new Font("Dialog", Font.BOLD, 24));
		lblRelatorio.setBounds(218, 63, 150, 41);
		contentPane.add(lblRelatorio);

		JButton btnVenda = new JButton("Relatorio de Venda");
		btnVenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new VendaRelatorio().setVisible(true);
				dispose();
			}
		});
		btnVenda.setBounds(184, 188, 192, 51);
		contentPane.add(btnVenda);
		
		JButton btnCompra = new JButton("Relatorio de Compra");
		btnCompra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CompraRelatorio().setVisible(true);
				dispose();
			}
		});
		btnCompra.setBounds(184, 261, 192, 51);
		contentPane.add(btnCompra);

		JButton btnNomeDebitos = new JButton("DA - Clientes");
		btnNomeDebitos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DebitosAtivosCliente().setVisible(true);
				dispose();
			}
		});
		btnNomeDebitos.setBounds(184, 330, 192, 51);
		contentPane.add(btnNomeDebitos);

		JButton btnNomeDebitosForn = new JButton("DA - Fornecedores");
		btnNomeDebitosForn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DebitosAtivosFornecedor().setVisible(true);
				dispose();
			}
		});
		btnNomeDebitosForn.setBounds(184, 400, 192, 51);
		contentPane.add(btnNomeDebitosForn);

		JButton btnDebitosUmCliente = new JButton("DA - Um Cliente");
		btnDebitosUmCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DebitosAtivoUmCliente().setVisible(true);
				dispose();
			}
		});
		btnDebitosUmCliente.setBounds(184, 470, 192, 51);
		contentPane.add(btnDebitosUmCliente);

		JButton btnDebitosUmFornecedor = new JButton("DA - Um Fornecedor");
		btnDebitosUmFornecedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DebitosAtivoUmFornecedor().setVisible(true);
				dispose();
			}
		});
		btnDebitosUmFornecedor.setBounds(184, 540, 192, 51);
		contentPane.add(btnDebitosUmFornecedor);

		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.setFont(new Font("Dialog", Font.BOLD, 16));
		btnVoltar.setBackground(new Color(135, 206, 250));
		btnVoltar.setHorizontalAlignment(SwingConstants.CENTER);
		btnVoltar.setBounds(12, 660, 138, 36);
		btnVoltar.addActionListener(e -> {
			new Menu().setVisible(true);
			dispose();
		});
		getContentPane().add(btnVoltar);
	}
}
