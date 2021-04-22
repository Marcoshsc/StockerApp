package compra;

import br.ufop.stocker.enums.EnumFormaPagamento;
import br.ufop.stocker.enums.EnumTipoOperacao;
import br.ufop.stocker.model.*;
import br.ufop.stocker.repository.exception.RepositoryActionException;
import br.ufop.stocker.repository.factory.RepositoryFactory;
import utils.Functions;
import view.Menu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CompraForm extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int sequential = 0;
	private JPanel contentPane;	
	private RepositoryFactory rep = RepositoryFactory.create();
	private String[] colunas = { "Nome", "Preço Unit.", "Estoque", "Quantidade", "Preço" };
	private String[] listFormaPagamento = {"DINHEIRO", "PRAZO"};
	private List<Integer> listParcelas = IntStream.range(1, 25).boxed().collect(Collectors.toList());
	private List<Produto> listProduto;
	private List<Fornecedor> listFornecedor;
	private Fornecedor selectedFornecedor;
	private List<String> listNomeFornecedor = new ArrayList<String>();
	private List<ItemOperacao> listItemOperacao = new ArrayList<ItemOperacao>();
	private JTable table;
	private Double precoTotal = 0.0;
	private JLabel lblTotal;
	@SuppressWarnings("rawtypes")
	private JComboBox comboParcelas;
	private JLabel lblParcelas;
	private JComboBox comboFormaPagamento;
	private JComboBox comboFornecedor;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				CompraForm frame = new CompraForm();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	public void iniciarTabela() {
		listProduto = selectedFornecedor == null ? new ArrayList<>() : new ArrayList<>(selectedFornecedor.getProdutosFornecidos());

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
		           for(int i = 0; i < listItemOperacao.size(); i++) {  
		        	   if(listItemOperacao.get(i).getProduto().getId() == listProduto.get(row - 1).getId()) {
		        		   listItemOperacao.remove(i);
		        	   }
		           }
		        	   ItemOperacao item = new ItemOperacao(++sequential, -1);
		        	   item.setProduto(listProduto.get(row - 1));
		        	   item.setQuantidade(quantidade);
		        	   listItemOperacao.add(item);
		           
	           }
	           model.addTableModelListener(this);
	      }
	    });

		table.setBounds(22, 59, 884, 205);
		table.setVisible(true);
		table.setTableHeader(null);

		if(scrollPane != null)
			scrollPane.setViewportView(table);
	}
	
	public void iniciarComboFornecedor() {
		try {
			listFornecedor = new ArrayList<>(rep.fornecedor().findAll());
		} catch (RepositoryActionException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < listFornecedor.size(); i++)
			listNomeFornecedor.add(listFornecedor.get(i).getNome());
	}
	
	public void salvarCompra() {
		if(comboFornecedor.getSelectedIndex() == -1)
			return;
		Operacao operacao = new Operacao(
				-1, 
				new Timestamp(System.currentTimeMillis()),
				precoTotal, 
				EnumTipoOperacao.COMPRA,
				EnumFormaPagamento.valueOf(listFormaPagamento[comboFormaPagamento.getSelectedIndex()]
		));
		operacao.setFornecedor(listFornecedor.get(comboFornecedor.getSelectedIndex()));
		operacao.setItens(new HashSet<>(listItemOperacao));
		if (comboFormaPagamento.getSelectedIndex() == 1) {  
			double valorParcela = precoTotal / comboParcelas.getSelectedIndex() + 1;
			Date today = new Date(System.currentTimeMillis());
			System.out.println(comboParcelas.getSelectedIndex() + 1);
			for (int i = 0; i < comboParcelas.getSelectedIndex() + 1; i++) {
				Date date = Functions.sqlDateAddMonth(today, i);
				System.out.println("Adicionou o " + i + " Debito");
				operacao.addDebito(new Debito( 
						i, i + 1, valorParcela, false, date
				));
			}
		}

		try {
			Operacao op = rep.operacao().insert(operacao);
			System.out.println(op);
			Functions.abrirProximaPagina("LISTA_COMPRA");
			dispose();
		} catch (RepositoryActionException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
//		System.out.println(operacao);
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CompraForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setBounds(100, 100, 956, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	
		JLabel lblRealizarCompra = new JLabel("Realizar Compra");
		lblRealizarCompra.setFont(new Font("Dialog", Font.BOLD, 20));
		lblRealizarCompra.setHorizontalAlignment(SwingConstants.CENTER);
		lblRealizarCompra.setBounds(12, 12, 936, 28);
		contentPane.add(lblRealizarCompra);
		
		iniciarTabela();

		this.scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(22, 59, 884, 205);
		scrollPane.setViewportView(table);
		contentPane.add(scrollPane);		
		
		
		comboFormaPagamento = new JComboBox(listFormaPagamento);
		comboFormaPagamento.setBounds(12, 342, 394, 23);
		comboFormaPagamento.addItemListener(new ItemListener () {
		    @Override
			public void itemStateChanged(ItemEvent e) {
				int indexSelected = comboFormaPagamento.getSelectedIndex();				
				comboParcelas.setVisible(indexSelected == 1);
				lblParcelas.setVisible(indexSelected == 1);
			}
		});
		contentPane.add(comboFormaPagamento);
		
		JLabel lblFormaDePagamento = new JLabel("Forma de Pagamento");
		lblFormaDePagamento.setBounds(12, 319, 468, 23);
		contentPane.add(lblFormaDePagamento);	
		
		comboParcelas = new JComboBox(listParcelas.toArray());
		comboParcelas.setBounds(516, 342, 394, 23);	
		comboParcelas.setVisible(false);
		contentPane.add(comboParcelas);
		
		lblParcelas = new JLabel("Parcelas");
		lblParcelas.setBounds(516, 319, 468, 23);
		lblParcelas.setVisible(false);
		contentPane.add(lblParcelas);	
		
		iniciarComboFornecedor();
		
		comboFornecedor = new JComboBox(listNomeFornecedor.toArray());
		comboFornecedor.setBounds(12, 402, 789, 23);
		comboFornecedor.setSelectedIndex(-1);
		comboFornecedor.addActionListener(e -> {
			selectedFornecedor = listFornecedor.get(comboFornecedor.getSelectedIndex());
			iniciarTabela();
		});
		contentPane.add(comboFornecedor);
		
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
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvarCompra();
			}
		});
		contentPane.add(btnSalvar);
		
		lblTotal = new JLabel("Total: " + Functions.doubleParaDinheiro(precoTotal));
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setBounds(780, 276, 126, 17);
		contentPane.add(lblTotal);

		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.setFont(new Font("Dialog", Font.BOLD, 16));
		btnVoltar.setBackground(new Color(135, 206, 250));
		btnVoltar.setHorizontalAlignment(SwingConstants.CENTER);
		btnVoltar.setBounds(12, 473, 138, 36);
		btnVoltar.addActionListener(e -> {
			new Menu().setVisible(true);
			dispose();
		});
		getContentPane().add(btnVoltar);
	}
}

   