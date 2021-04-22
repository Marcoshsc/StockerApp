package fornecedor;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import java.awt.Color;

public class FornecedorListPContido extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Produto> listProduto;
	private RepositoryFactory rep = RepositoryFactory.create();
	private String[] colunas = { "Nome", "Preço Unit.", "Estoque", "Fornecido por:", "Descrição" };
	private JTable table;
	private JScrollPane scrollPane;
	
	private Fornecedor fornecedor ;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FornecedorListPContido window = new FornecedorListPContido();
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
	public FornecedorListPContido() {
		initialize();
	}
	public FornecedorListPContido(Fornecedor forn) {
		System.out.println(forn.getProdutosFornecidos());
		fornecedor = forn ;
		initialize();
	}
	
	public void iniciarTabela() {
		
			listProduto = new ArrayList<>(fornecedor.getProdutosFornecidos());
		

		DefaultTableModel model = new DefaultTableModel();
		table = new JTable(model) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};

		Object rowData[] = new Object[5];
		model.setColumnCount(5);
		model.addRow(colunas);
		for (int i = 0; i < 5; i++) {
			DefaultTableCellRenderer render = new DefaultTableCellRenderer();
			render.setHorizontalAlignment(SwingConstants.CENTER);
			table.getColumnModel().getColumn(i).setCellRenderer(render);
		}

		for (int i = 0; i < listProduto.size(); i++) {
			rowData[0] = listProduto.get(i).getNome();
			rowData[1] = listProduto.get(i).getPreco();
			rowData[2] = listProduto.get(i).getEstoque();
			rowData[3]=fornecedor.getNome();
			rowData[4] = listProduto.get(i).getDescricao();
			model.addRow(rowData);
			
			
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
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		iniciarTabela();
		setBounds(100, 100, 1100, 577);
		getContentPane().setLayout(null);
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	Functions.abrirProximaPagina("LISTA_FORNECEDOR");
				dispose();
		    }
		});

		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(12, 88, 1100, 355);
		getContentPane().add(scrollPane);

		JLabel lblProdutos = new JLabel("Produtos");
		lblProdutos.setFont(new Font("Dialog", Font.BOLD, 20));
		lblProdutos.setBounds(500, 31, 200, 17);
		getContentPane().add(lblProdutos);

		scrollPane.setViewportView(table);

		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.setFont(new Font("Dialog", Font.BOLD, 16));
		btnVoltar.setBackground(new Color(135, 206, 250));
		btnVoltar.setHorizontalAlignment(SwingConstants.CENTER);
		btnVoltar.setBounds(12, 660, 138, 36);
		btnVoltar.addActionListener(e -> {
			new FornecedorList().setVisible(true);
			dispose();
		});
		getContentPane().add(btnVoltar);


	}
}
