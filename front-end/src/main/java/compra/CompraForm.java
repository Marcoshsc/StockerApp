package compra;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import br.ufop.stocker.model.Cliente;
import br.ufop.stocker.model.Fornecedor;
import br.ufop.stocker.model.Produto;
import br.ufop.stocker.repository.exception.RepositoryActionException;
import br.ufop.stocker.repository.factory.RepositoryFactory;
import produto.ProductForm;

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
import javax.swing.JButton;

public class CompraForm extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private List<Produto> listProduto;
	private RepositoryFactory rep = RepositoryFactory.create();
	private String[] colunas = { "Nome", "Preço Unit.", "Estoque", "Quantidade" };
	private List<JTextField> listQuantidade = new ArrayList<JTextField>();
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CompraForm frame = new CompraForm();
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
		table = new JTable(model);
		table.setEnabled(false);

		Object rowData[] = new Object[4];
		model.setColumnCount(4);
		model.addRow(colunas);
		for (int i = 0; i < 4; i++) {
			DefaultTableCellRenderer render = new DefaultTableCellRenderer();
			render.setHorizontalAlignment(SwingConstants.CENTER);
			table.getColumnModel().getColumn(i).setCellRenderer(render);
		}

		for (int i = 0; i < listProduto.size(); i++) {
			JTextField field = new JTextField();
			field.setColumns(10);
			listQuantidade.add(field);			
			
			rowData[0] = listProduto.get(i).getNome();
			rowData[1] = listProduto.get(i).getPreco();
			rowData[2] = listProduto.get(i).getEstoque();
			rowData[3] = "";
			model.addRow(rowData);
			System.out.println(listProduto.get(i).getNome());
		}
		
		model.fireTableDataChanged();
		
		table.setBounds(22, 59, 884, 205);
		table.setVisible(true);
		table.setTableHeader(null);
	}

	/**
	 * Create the frame.
	 */
	public CompraForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 956, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	
		JLabel lblNewLabel = new JLabel("Realizar Compra");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(12, 12, 936, 17);
		contentPane.add(lblNewLabel);
		
		iniciarTabela();

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(22, 59, 884, 205);
		scrollPane.setViewportView(table);
		contentPane.add(scrollPane);		
		
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(22, 479, 468, 23);
		contentPane.add(comboBox);
		
		JLabel lblFormaDePagamento = new JLabel("Forma de Pagamento");
		lblFormaDePagamento.setBounds(22, 456, 468, 23);
		contentPane.add(lblFormaDePagamento);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(22, 539, 789, 23);
		contentPane.add(comboBox_1);
		
		JLabel lblCliente = new JLabel("Cliente");
		lblCliente.setBounds(22, 514, 468, 23);
		contentPane.add(lblCliente);
		
		JButton btnNewButton = new JButton("+ Adicionar");
		btnNewButton.setBounds(823, 537, 101, 27);
		btnNewButton.setBackground(new Color(204, 255, 204));
		contentPane.add(btnNewButton);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setFont(new Font("Dialog", Font.BOLD, 16));
		btnSalvar.setBackground(new Color(135, 206, 250));
		btnSalvar.setBounds(798, 633, 138, 36);
		contentPane.add(btnSalvar);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(24, 275, 900, 122);
		contentPane.add(textArea);
		
		JLabel lblNewLabel_1 = new JLabel("Total: ");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(798, 413, 126, 17);
		contentPane.add(lblNewLabel_1);
	}
}
