package venda;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import br.ufop.stocker.model.Cliente;
import br.ufop.stocker.model.Fornecedor;
import br.ufop.stocker.model.ItemOperacao;
import br.ufop.stocker.model.Produto;
import br.ufop.stocker.repository.exception.RepositoryActionException;
import br.ufop.stocker.repository.factory.RepositoryFactory;
import produto.ProductForm;
import utils.Functions;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputMethodEvent;

public class VendaForm extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;	
	private RepositoryFactory rep = RepositoryFactory.create();
	private String[] colunas = { "Nome", "Preço Unit.", "Estoque", "Quantidade", "Preço" };
	private List<Produto> listProduto;
	private List<Cliente> listCliente;
	private List<String> listNomeCliente = new ArrayList<String>();
	private List<ItemOperacao> listItemOperacao = new ArrayList<ItemOperacao>();
	private JTable table;
	private Double precoTotal = 0.0;
	private JLabel lblTotal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VendaForm frame = new VendaForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
			public boolean isCellEditable(int row, int col) {
				if(row == 0) return false;
			     switch (col) {
			         case 3:
			             return true;
			         default:
			             return false;
			      }
			}
			
			@Override
			public Class<?> getColumnClass(int columnIndex) { 
			    return columnIndex == 3 ? Integer.class : super.getColumnClass(columnIndex);
			}
		};

		Object rowData[] = new Object[5];
		model.setColumnCount(5);
		model.addRow(colunas);
		for (int i = 0; i < 5; i++) {
			DefaultTableCellRenderer render = new DefaultTableCellRenderer();
			render.setHorizontalAlignment(SwingConstants.CENTER);
			if(i == 3) render.setBackground(new Color(255, 255, 255));
			else render.setBackground(new Color(255, 234, 207));
			table.getColumnModel().getColumn(i).setCellRenderer(render);
		}

		for (int i = 0; i < listProduto.size(); i++) {
			
			rowData[0] = listProduto.get(i).getNome();
			rowData[1] = listProduto.get(i).getPreco();
			rowData[2] = listProduto.get(i).getEstoque();
			rowData[3] = "";
			rowData[4] = "";
			model.addRow(rowData);
			System.out.println(listProduto.get(i).getNome());
		}
		
		model.fireTableDataChanged();
   	
		table.getModel().addTableModelListener(new TableModelListener() {

	      public void tableChanged(TableModelEvent e) {
	    	  model.removeTableModelListener(this);
	    	  int row = table.getSelectedRow();
	           if(row > -1) {  
	        	   String result = table.getValueAt(row, 3).toString();
	        	   int quantidade = Integer.parseInt(result);
	        	   double preco =  quantidade * listProduto.get(row - 1).getPreco();
		           model.setValueAt(preco, row, 4);
		           precoTotal += preco;
		           lblTotal.setText("Total: " + Functions.doubleParaDinheiro(precoTotal));
		           
	           }
	           model.addTableModelListener(this);
	      }
	    });

		table.setBounds(22, 59, 884, 205);
		table.setVisible(true);
		table.setTableHeader(null);
	}
	
	public void iniciarComboCliente() {  
		try {
			listCliente = new ArrayList<>(rep.cliente().findAll());
		} catch (RepositoryActionException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < listCliente.size(); i++)
			listNomeCliente.add(listCliente.get(i).getNome());
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public VendaForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 956, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	
		JLabel lblRealizarCompra = new JLabel("Realizar Venda");
		lblRealizarCompra.setFont(new Font("Dialog", Font.BOLD, 20));
		lblRealizarCompra.setHorizontalAlignment(SwingConstants.CENTER);
		lblRealizarCompra.setBounds(12, 12, 936, 28);
		contentPane.add(lblRealizarCompra);
		
		iniciarTabela();

		JScrollPane scrollPane = new JScrollPane();		
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(22, 59, 884, 205);
		scrollPane.setViewportView(table);
		contentPane.add(scrollPane);		
		
		
		JComboBox comboFormaPagamento = new JComboBox();
		comboFormaPagamento.setBounds(12, 342, 394, 23);
		contentPane.add(comboFormaPagamento);
		
		JLabel lblFormaDePagamento = new JLabel("Forma de Pagamento");
		lblFormaDePagamento.setBounds(12, 319, 468, 23);
		contentPane.add(lblFormaDePagamento);	
		
		iniciarComboCliente();
		
		JComboBox comboCliente = new JComboBox(listNomeCliente.toArray());
		comboCliente.setBounds(12, 402, 789, 23);
		comboCliente.setSelectedIndex(-1);
		contentPane.add(comboCliente);
		
		JLabel lblCliente = new JLabel("Cliente");
		lblCliente.setBounds(12, 377, 468, 23);
		contentPane.add(lblCliente);
		
		JButton btnAdicionarCliente = new JButton("+ Adicionar");
		btnAdicionarCliente.setBounds(813, 400, 101, 27);
		btnAdicionarCliente.setBackground(new Color(204, 255, 204));
		contentPane.add(btnAdicionarCliente);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setFont(new Font("Dialog", Font.BOLD, 16));
		btnSalvar.setBackground(new Color(135, 206, 250));
		btnSalvar.setBounds(776, 473, 138, 36);
		contentPane.add(btnSalvar);
		
		lblTotal = new JLabel("Total: " + Functions.doubleParaDinheiro(precoTotal));
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setBounds(780, 276, 126, 17);
		contentPane.add(lblTotal);
	}
}

   