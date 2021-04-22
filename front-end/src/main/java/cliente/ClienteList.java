package cliente;

import java.awt.EventQueue;

import javax.swing.*;

import java.awt.Font;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import br.ufop.stocker.model.Cliente;
import br.ufop.stocker.repository.exception.RepositoryActionException;
import br.ufop.stocker.repository.factory.RepositoryFactory;
import view.Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
	private JComboBox comboTempoCadastro;
	private boolean firstRender = true;
	private String[] meses = new String[] {"Qualquer", "1", "2", "3", "4", "5", "10", "15", "20"};

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
			LocalDateTime now = LocalDateTime.now();
			Timestamp dataCadastro = listCliente.get(i).getDataCadastro();

			if(comboTempoCadastro != null && comboTempoCadastro.getSelectedIndex() > 0) {
				int months = Integer.parseInt(meses[comboTempoCadastro.getSelectedIndex()]);
				now = now.minusMonths(months);
				Timestamp ts = Timestamp.valueOf(now);
				if (ts.before(dataCadastro))
					continue;
			}
			
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

		if(!firstRender) {
			scrollPane.setViewportView(table);
			SwingUtilities.updateComponentTreeUI(this);
		}
	}
	
	
	/**
	 * Initialize the contents of the
	 */
	private void initialize() {
		try {
			listCliente = new ArrayList<>(rep.cliente().findAll());
		} catch (RepositoryActionException e) {
			e.printStackTrace();
		}
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

		firstRender = false;
		
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

		comboTempoCadastro = new JComboBox(meses);
		comboTempoCadastro.setBounds(250, 50, 150, 23);
		comboTempoCadastro.setVisible(true);
		comboTempoCadastro.addActionListener(e -> {
			iniciarTabela();
		});
		getContentPane().add(comboTempoCadastro);

		JLabel lblNome = new JLabel("Tempo cadastro (meses):");
		lblNome.setBounds(30, 50, 200, 14);
		lblNome.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNome.setToolTipText("");
		getContentPane().add(lblNome);

	}
}
