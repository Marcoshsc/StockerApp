package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import br.ufop.stocker.model.Cliente;
import produto.ProductForm;

import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class ClientList extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JButton btnAdicionar;
	private List<Cliente> listCliente;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientList frame = new ClientList();
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
	public ClientList() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 577);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"NOME", "TELEFONE", "EMAIL", "ENDEREÇO", "DESCRIÇÃO"
			}
		));
		table.setBounds(12, 88, 761, 355);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 90, 1100, 355);
		contentPane.add(scrollPane);
		
		scrollPane.setViewportView(table);
		
		btnAdicionar = new JButton("+ Adicionar");
		btnAdicionar.setBorderPainted(false);
		btnAdicionar.setFocusPainted(false);
		btnAdicionar.setBackground(new Color(204, 255, 204));
		btnAdicionar.setBounds(969, 40, 105, 27);
		
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ClientForm().setVisible(true);
				

			}
		});
			
		contentPane.add(btnAdicionar);
	
		
		JLabel lblClientes = new JLabel("Clientes");
		lblClientes.setBounds(475, 33, 89, 33);
		lblClientes.setFont(new Font("Dialog", Font.BOLD, 20));
		contentPane.add(lblClientes);
		
		
		
		
		
	}
}
