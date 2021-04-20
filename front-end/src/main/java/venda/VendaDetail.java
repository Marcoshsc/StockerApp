package venda;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import br.ufop.stocker.model.Debito;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import br.ufop.stocker.model.Fornecedor;
import br.ufop.stocker.model.Operacao;
import br.ufop.stocker.model.Produto;
import br.ufop.stocker.repository.exception.RepositoryActionException;
import br.ufop.stocker.repository.factory.RepositoryFactory;
import produto.ProductForm;
import utils.DateLabelFormatter;
import utils.Functions;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class VendaDetail extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private List<Operacao> listOperacao;
	private Operacao operacao;
	private List<Debito> debitos;
	private RepositoryFactory rep = RepositoryFactory.create();
//	private String[] colunas = { "Pago", "Nome", "Preço ", "Quantidade", "Vencimento" };
	private String[] colunas = { "", "Parcela", "Pago", "Valor", "Vencimento" };
	private JScrollPane scrollPane;
	private JTable table;
	private JLabel lblCliente;
	private JLabel lblData;
	private JLabel lblRaquel;
	private JLabel label;
	
	public void iniciarTabela() {
		try {
			listOperacao = new ArrayList<>(rep.operacao().findAll());
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
				int column = table.columnAtPoint(e.getPoint());
				if(row == 0)
					return;
				if(column == 0) {
					Debito debito = debitos.get(row - 1);
					if(debito.isPago())
						return;
					try {
						rep.operacao().pagarDebito(debito);
						debito.setPago(true);
						model.setValueAt("", row, column);
						model.setValueAt("Sim", row, 2);
					} catch (RepositoryActionException repositoryActionException) {
						repositoryActionException.printStackTrace();
					}
				}
		//		new ProductForm(true, listOperacao.get(row-1), "LISTA_PRODUTOS").frame.setVisible(true);
//				dispose();
			}
		});

		int nColumns = 5;
		model.setColumnCount(nColumns);
		model.addRow(colunas);
		for (int i = 0; i < nColumns; i++) {
			DefaultTableCellRenderer render = new DefaultTableCellRenderer();
			render.setHorizontalAlignment(SwingConstants.CENTER);
			table.getColumnModel().getColumn(i).setCellRenderer(render);
		}

		for (Debito debito : debitos) {
			Object rowData[] = new Object[nColumns];
			rowData[0] = debito.isPago() ? "" : "Pagar débito";
			rowData[1] = Integer.toString(debito.getSequencial());
			rowData[2] = debito.isPago() ? "Sim" : "Não";
			rowData[3] = Double.toString(debito.getValor());
			rowData[4] = debito.getVencimento().toString();
			model.addRow(rowData);
		}
//		//	for (int i = 0; i < listOperacao.size(); i++) {
//
//				rowData[0] = "Sim";
//				rowData[1] = "Produto 1";
//				rowData[2] = "20.0";
//				rowData[3] = "3";
//				rowData[4] = "16/04/2021";
//				model.addRow(rowData);
//				System.out.println(rowData);
//		//	}
		
		model.fireTableDataChanged();

		table.setBounds(22, 120, 892, 461);

		table.setVisible(true);
		table.setTableHeader(null);
	}
	
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					VendaDetail frame = new VendaDetail();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public VendaDetail(Operacao operacao) {
		this.operacao = operacao;
		this.debitos = new ArrayList<>(operacao.getDebitos());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 956, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		iniciarTabela();
		
		JLabel lblNewLabel = new JLabel("Venda");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(12, 0, 924, 60);
		contentPane.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 150, 892, 431);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setViewportView(table);
		contentPane.add(scrollPane);
		
		lblCliente = new JLabel("Cliente:");
		lblCliente.setBounds(22, 46, 60, 17);
		contentPane.add(lblCliente);
		
		lblData = new JLabel("Data:");
		lblData.setBounds(22, 75, 60, 17);
		contentPane.add(lblData);
		
		lblRaquel = new JLabel(operacao.getCliente().getNome());
		lblRaquel.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblRaquel.setBounds(180, 46, 326, 17);
		contentPane.add(lblRaquel);
		
		label = new JLabel(operacao.getData().toString());
		label.setFont(new Font("Dialog", Font.PLAIN, 12));
		label.setBounds(180, 75, 156, 17);
		contentPane.add(label);

		lblRaquel = new JLabel("Forma de Pagamento:");
		lblRaquel.setFont(new Font("Dialog", Font.BOLD, 12));
		lblRaquel.setBounds(22, 100, 326, 17);
		contentPane.add(lblRaquel);

		label = new JLabel(operacao.getFormaPagamento().getForma());
		label.setFont(new Font("Dialog", Font.PLAIN, 12));
		label.setBounds(180, 100, 156, 17);
		contentPane.add(label);

		lblRaquel = new JLabel("Valor final:");
		lblRaquel.setFont(new Font("Dialog", Font.BOLD, 12));
//		lblRaquel.setBounds(22, 125, 326, 17);
		lblRaquel.setHorizontalAlignment(SwingConstants.LEFT);
		lblRaquel.setSize(326, 17);
		contentPane.add(lblRaquel);

		label = new JLabel(Double.toString(operacao.getValorFinal()));
		label.setFont(new Font("Dialog", Font.PLAIN, 12));
		label.setBounds(180, 125, 156, 17);
		contentPane.add(label);
	}
}
