package cliente;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import br.ufop.stocker.model.Cliente;
import br.ufop.stocker.model.Fornecedor;
import br.ufop.stocker.model.Produto;
import br.ufop.stocker.repository.exception.RepositoryActionException;
import br.ufop.stocker.repository.factory.RepositoryFactory;
import view.Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import java.awt.Color;

public class ClienteList extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Cliente> listCliente;
	private RepositoryFactory rep = RepositoryFactory.create();
	private String[] colunas = { "CPF", "Nome", "Telefone", "Email", "Endereço", "Descrição"};
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btnAdicionar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClienteList window = new ClienteList();
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
	public ClienteList() {
		initialize();
	}
	
	public void iniciarTabela() {
		try {
			listCliente = new ArrayList<>(rep.cliente().findAll());
		} catch (RepositoryActionException e) {
			e.printStackTrace();
		}

		DefaultTableModel model = new DefaultTableModel();
		table = new JTable(model) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				new ClienteForm(true, listCliente.get(row-1), "LISTA_CLIENTES").setVisible(true);
				dispose();
			}
		});

		Object rowData[] = new Object[6];
		model.setColumnCount(6);
		model.addRow(colunas);
		for (int i = 0; i < 6; i++) {
			DefaultTableCellRenderer render = new DefaultTableCellRenderer();
			render.setHorizontalAlignment(SwingConstants.CENTER);
			table.getColumnModel().getColumn(i).setCellRenderer(render);
		}

		for (int i = 0; i < listCliente.size(); i++) {
			//List<String> nomeFornedores = new ArrayList<String>();
			//List<Fornecedor> list = new ArrayList<Fornecedor>(listProduto.get(i).getFornecedores());

			//for(int j = 0; i < list.size(); j++) 
				//nomeFornedores.add(list.get(j).getNome());
			
			rowData[0] = listCliente.get(i).getCpf();
			rowData[1] = listCliente.get(i).getNome();
			rowData[2] = listCliente.get(i).getDescricao();
			rowData[3] = listCliente.get(i).getEndereco();
			rowData[4] = listCliente.get(i).getTelefone();
			rowData[5] = listCliente.get(i).getEmail();
			
			model.addRow(rowData);
			System.out.println(rowData);
		}
		
		model.fireTableDataChanged();

		table.setBounds(12, 88, 761, 355);

		table.setVisible(true);
		table.setTableHeader(null);
	}
	
	
	/**
	 * Initialize the contents of the
	 */
	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		iniciarTabela();
		setBounds(100, 100, 1100, 577);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(12, 88, 1100, 355);
		scrollPane.setViewportView(table);
		getContentPane().add(scrollPane);
		
		JLabel lblClientes = new JLabel("Clientes");
		lblClientes.setFont(new Font("Dialog", Font.BOLD, 20));
		lblClientes.setBounds(500, 31, 200, 17);
		getContentPane().add(lblClientes);
		
		btnAdicionar = new JButton("+ Adicionar");
		btnAdicionar.setFocusPainted(false);
		btnAdicionar.setBackground(new Color(204, 255, 204));
		btnAdicionar.setBounds(975, 29, 105, 27);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ClienteForm().setVisible(true);

			}
		});
			
		getContentPane().add(btnAdicionar);

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
