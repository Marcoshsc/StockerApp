package produto;

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

import br.ufop.stocker.model.Fornecedor;
import br.ufop.stocker.model.Produto;
import br.ufop.stocker.repository.exception.RepositoryActionException;
import br.ufop.stocker.repository.factory.RepositoryFactory;
import utils.Functions;
import venda.VendaRelatorio;
import view.Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import java.awt.Color;

public class ProductList extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Produto> listProduto;
	private RepositoryFactory rep = RepositoryFactory.create();
	private String[] colunas = { "Nome", "Preço Unit.", "Estoque", "Fornecido por:", "Descrição" };
	public JFrame frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btnAdicionar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductList window = new ProductList();
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
	public ProductList() {
		initialize();
	}
	
	public void iniciarTabela() {
		try {
			listProduto = new ArrayList<>(rep.produto().findAll());
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
				new ProductForm(true, listProduto.get(row-1), "LISTA_PRODUTOS").setVisible(true);
				dispose();
				
			}
		});

		Object rowData[] = new Object[5];
		model.setColumnCount(5);
		model.addRow(colunas);
		for (int i = 0; i < 5; i++) {
			DefaultTableCellRenderer render = new DefaultTableCellRenderer();
			render.setHorizontalAlignment(SwingConstants.CENTER);
			table.getColumnModel().getColumn(i).setCellRenderer(render);
		}

		for (Produto produto : listProduto) {
			List<String> nomeFornedores = new ArrayList<>();
			List<Fornecedor> list = new ArrayList<>(produto.getFornecedores());

			for (Fornecedor fornecedor : list)
				nomeFornedores.add(fornecedor.getNome());

			rowData[0] = produto.getNome();
			rowData[1] = produto.getPreco();
			rowData[2] = produto.getEstoque();
			rowData[3] = nomeFornedores.toString().replace("[", "").replace("]", "");
			rowData[4] = produto.getDescricao();
			model.addRow(rowData);
		}
		
		model.fireTableDataChanged();

		table.setBounds(12, 88, 761, 355);

		table.setVisible(true);
		table.setTableHeader(null);
	}
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		iniciarTabela();
		setBounds(100, 100, 1100, 577);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		getContentPane().setLayout(null);

		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				Functions.abrirProximaPagina("MENU");
				dispose();
			}
		});

		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(12, 88, 1100, 355);
		scrollPane.setViewportView(table);
		getContentPane().add(scrollPane);
		
		JLabel lblProdutos = new JLabel("Produtos");
		lblProdutos.setFont(new Font("Dialog", Font.BOLD, 20));
		lblProdutos.setBounds(500, 31, 200, 17);
		getContentPane().add(lblProdutos);
		
		btnAdicionar = new JButton("+ Adicionar");
		btnAdicionar.setFocusPainted(false);
		btnAdicionar.setBackground(new Color(204, 255, 204));
		btnAdicionar.setBounds(975, 29, 105, 27);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ProductForm().setVisible(true);
				dispose();
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
