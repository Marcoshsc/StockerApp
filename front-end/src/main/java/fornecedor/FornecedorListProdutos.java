package fornecedor;

import java.awt.EventQueue;

import javax.swing.*;

import java.awt.Font;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import view.Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class FornecedorListProdutos extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Produto> listProduto;
	private RepositoryFactory rep = RepositoryFactory.create();
	private String[] colunas = { "Nome", "Preço Unit.", "Estoque", "Fornecido por:", "Descrição", "Fornecido" };
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btnAdicionar;
	private JButton btnRemover;
	private ArrayList selecao= new ArrayList() ;
	private List<Produto> showing = new ArrayList<>();
	private String[] opcoes = {"Todos", "Fornece", "Não fornece"};
	private JComboBox comboView;
	private boolean firstRender = true;
	
	private Fornecedor fornecedor ;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FornecedorListProdutos window = new FornecedorListProdutos();
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
	public FornecedorListProdutos() {
		initialize();
	}
	public FornecedorListProdutos(Fornecedor forn) {
		
		fornecedor = forn ;
		System.out.println(forn.getProdutosFornecidos());
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
				int aux = table.rowAtPoint(e.getPoint());
				if(!selecao.contains(aux)) {
					selecao.add(aux);
				}
			}
		});

		model.setColumnCount(6);
		model.addRow(colunas);
		for (int i = 0; i < 6; i++) {
			DefaultTableCellRenderer render = new DefaultTableCellRenderer();
			render.setHorizontalAlignment(SwingConstants.CENTER);
			table.getColumnModel().getColumn(i).setCellRenderer(render);
		}

		showing.clear();
		for (int i = 0; i < listProduto.size(); i++) {

			List<String> nomeFornedores = new ArrayList<>();

			if(comboView != null && comboView.getSelectedIndex() > 0) {
				String opcao = opcoes[comboView.getSelectedIndex()];
				if(opcao.equalsIgnoreCase("Não fornece")) {
					if(fornecedor.getProdutosFornecidos().contains(listProduto.get(i)))
						continue;
				}
				else if(opcao.equalsIgnoreCase("Fornece")) {
					if(!fornecedor.getProdutosFornecidos().contains(listProduto.get(i)))
						continue;
				}
			}

			showing.add(listProduto.get(i));

			for (Fornecedor fornecedor : listProduto.get(i).getFornecedores())
				nomeFornedores.add(fornecedor.getNome());

			Object rowData[] = new Object[6];
			rowData[0] = listProduto.get(i).getNome();
			rowData[1] = listProduto.get(i).getPreco();
			rowData[2] = listProduto.get(i).getEstoque();
			rowData[3] = nomeFornedores.toString().replace("[", "").replace("]", "");
			rowData[4] = listProduto.get(i).getDescricao();
			rowData[5] = fornecedor.getProdutosFornecidos().contains(listProduto.get(i)) ? "Sim" : "Não";

			model.addRow(rowData);
			
			
		}
		
		model.fireTableDataChanged();

		table.setBounds(12, 88, 761, 355);

		table.setVisible(true);
		table.setTableHeader(null);

		if(!firstRender) {
			scrollPane.setViewportView(table);
			SwingUtilities.updateComponentTreeUI(this);
		}
	}
	
	
	/**
	 * Initialize the contents of the
	 */
	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		iniciarTabela();
		setBounds(100, 100, 1100, 577);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		firstRender = false;

		JLabel lblProdutos = new JLabel("Produtos");
		lblProdutos.setFont(new Font("Dialog", Font.BOLD, 20));
		lblProdutos.setBounds(500, 31, 200, 17);
		getContentPane().add(lblProdutos);

		scrollPane.setViewportView(table);
		
		btnAdicionar = new JButton("+ Adicionar");
		btnAdicionar.setFocusPainted(false);
		btnAdicionar.setBackground(new Color(204, 255, 204));
		btnAdicionar.setBounds(975, 29, 150, 27);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				for(int i=0;i<selecao.size();i++) {
					
					int row = (int) selecao.get(i);
					if(fornecedor.getProdutosFornecidos().contains(showing.get(row - 1)))
						continue;
					fornecedor.addProduto(showing.get(row-1));
					try {
						rep.fornecedor().update(fornecedor.getId(), fornecedor);
					} catch (RepositoryActionException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
				}
				
				new FornecedorListProdutos(fornecedor).setVisible(true);
				dispose();
				
				
			}
		});

		getContentPane().add(btnAdicionar);

		btnRemover = new JButton("- Remover");
		btnRemover.setFocusPainted(false);
		btnRemover.setBackground(new Color(236, 148, 148));
		btnRemover.setBounds(700, 29, 150, 27);
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				for(int i=0;i<selecao.size();i++) {

					int row = (int) selecao.get(i);
					if(!fornecedor.getProdutosFornecidos().contains(showing.get(row - 1)))
						continue;
					fornecedor.removeProduto(showing.get(row-1));
					try {
						rep.fornecedor().update(fornecedor.getId(), fornecedor);
					} catch (RepositoryActionException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}


				}

				new FornecedorListProdutos(fornecedor).setVisible(true);
				dispose();


			}
		});

		getContentPane().add(btnRemover);

		comboView = new JComboBox(opcoes);
		comboView.setBounds(250, 50, 150, 23);
		comboView.setVisible(true);
		comboView.addActionListener(e -> {
			iniciarTabela();
		});
		getContentPane().add(comboView);

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
