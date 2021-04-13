package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import br.ufop.stocker.model.Produto;
import br.ufop.stocker.repository.exception.RepositoryActionException;
import br.ufop.stocker.repository.factory.RepositoryFactory;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProductList extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Produto> listProduto;
	private RepositoryFactory rep = RepositoryFactory.create();
	private String[] colunas = { "Nome", "Preço Unit.", "Estoque", "Descrição" };
	public JFrame frame;
	private JTable table;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductList window = new ProductList();
					window.frame.setVisible(true);
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
				new ProductForm(true, listProduto.get(row-1)).frame.setVisible(true);
				frame.dispose();
				
			}
		});

		Object rowData[] = new Object[4];
		model.setColumnCount(4);
		model.addRow(colunas);
		for (int i = 0; i < 4; i++) {
			DefaultTableCellRenderer render = new DefaultTableCellRenderer();
			render.setHorizontalAlignment(SwingConstants.CENTER);
			table.getColumnModel().getColumn(i).setCellRenderer(render);
		}

		for (int i = 0; i < listProduto.size(); i++) {
			rowData[0] = listProduto.get(i).getNome();
			rowData[1] = listProduto.get(i).getPreco();
			rowData[2] = listProduto.get(i).getEstoque();
			rowData[3] = listProduto.get(i).getDescricao();
			model.addRow(rowData);
			System.out.println(rowData);
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
		frame = new JFrame();
		frame.setBounds(100, 100, 779, 577);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(12, 88, 761, 355);
		frame.getContentPane().add(scrollPane);

		JLabel lblProdutos = new JLabel("Produtos");
		lblProdutos.setFont(new Font("Dialog", Font.BOLD, 20));
		lblProdutos.setBounds(336, 31, 200, 17);
		frame.getContentPane().add(lblProdutos);

		iniciarTabela();
		scrollPane.setViewportView(table);

	}
}