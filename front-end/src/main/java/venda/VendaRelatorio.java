package venda;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import br.ufop.stocker.enums.EnumTipoOperacao;
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
import view.Menu;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class VendaRelatorio extends JFrame {

	private JPanel contentPane;
	private List<Operacao> listOperacao;
	private RepositoryFactory rep = RepositoryFactory.create();
	private String[] colunas = { "ID", "Cliente", "Preço Final", "Forma Pagamento", "Data" };
	private JScrollPane scrollPane;
	private Date initialDate;
	private Date finalDate;
	private JTable table;
	private boolean firstRender = true;
	
	public void iniciarTabela() {
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
//				System.out.println();
		//		new ProductForm(true, listOperacao.get(row-1), "LISTA_PRODUTOS").frame.setVisible(true);
				try {
					VendaDetail frame = new VendaDetail(listOperacao.get(row - 1));
					frame.setVisible(true);
				} catch (Exception exc) {
					exc.printStackTrace();
				}
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

		for (int i = 0; i < listOperacao.size(); i++) {
			Timestamp date = listOperacao.get(i).getData();
			if(initialDate != null && initialDate.getTime() > date.getTime())
				continue;
			if(finalDate != null && finalDate.getTime() < date.getTime())
				continue;
			rowData[0] = listOperacao.get(i).getId();
			rowData[1] = listOperacao.get(i).getCliente().getNome();
			rowData[2] = listOperacao.get(i).getValorFinal();
			rowData[3] = listOperacao.get(i).getFormaPagamento().name();
			rowData[4] = Functions.formatData(listOperacao.get(i).getData().toString());
			model.addRow(rowData);
			System.out.println(rowData);
		}
		
		model.fireTableDataChanged();

		table.setBounds(22, 120, 892, 461);

		table.setVisible(true);
		table.setTableHeader(null);

		if(!firstRender) {
			scrollPane.setViewportView(table);
			SwingUtilities.updateComponentTreeUI(this);
		}
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VendaRelatorio frame = new VendaRelatorio();
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
	public VendaRelatorio() {
		try {
			listOperacao = new ArrayList<>(rep.operacao().findAll(EnumTipoOperacao.VENDA));
		} catch (RepositoryActionException e) {
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 956, 650);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		iniciarTabela();
		
		JLabel lblNewLabel = new JLabel("Relatório de Vendas");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(12, 0, 924, 40);
		contentPane.add(lblNewLabel);
		
		JLabel lblFiltrarPor = new JLabel("Filtrar por:      Data Inicial");
		lblFiltrarPor.setBounds(22, 52, 176, 17);
		contentPane.add(lblFiltrarPor);
		
		UtilDateModel model = new UtilDateModel();
		UtilDateModel model2 = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Hoje");
		p.put("text.month", "Mês");
		p.put("text.year", "Ano");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p);
		JDatePickerImpl datePickerInicio = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePickerInicio.setBackground(new Color(255,255,255));
		datePickerInicio.setBounds(105, 70, 176, 28);
		datePickerInicio.addActionListener(e -> {
			initialDate = datePickerInicio.getModel().getValue() == null ? null : (Date) datePickerInicio.getModel().getValue();
			iniciarTabela();
		});
		contentPane.add(datePickerInicio);
		
		JDatePickerImpl datePickerFim = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
		datePickerFim.setBackground(new Color(255,255,255));
		datePickerFim.setBounds(314, 70, 176, 28);
		datePickerFim.addActionListener(e -> {
			finalDate = datePickerFim.getModel().getValue() == null ? null : (Date) datePickerFim.getModel().getValue();
			iniciarTabela();
		});
		contentPane.add(datePickerFim);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 120, 892, 461);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setViewportView(table);
		contentPane.add(scrollPane);
		firstRender = false;
		
		JLabel lblDataFim = new JLabel("Data Final");
		lblDataFim.setBounds(313, 52, 176, 17);
		contentPane.add(lblDataFim);

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
