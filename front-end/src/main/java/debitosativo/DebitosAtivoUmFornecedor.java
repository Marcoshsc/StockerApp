package debitosativo;

import br.ufop.stocker.model.Cliente;
import br.ufop.stocker.model.Debito;
import br.ufop.stocker.model.Fornecedor;
import br.ufop.stocker.repository.exception.RepositoryActionException;
import br.ufop.stocker.repository.factory.RepositoryFactory;
import utils.Functions;
import view.Menu;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class DebitosAtivoUmFornecedor extends JFrame {

    private List<Debito> debitos;
    private RepositoryFactory rep = RepositoryFactory.create();
    private JTable table;
    private List<Fornecedor> fornecedores;
    private String[] headersCliente = { "CNPJ", "Nome", "Telefone", "Email", "Endereço", "Descrição"};
    private String[] headersDebito = { "Compra", "Valor", "Vencimento" };
    private JScrollPane scrollPane;

    public DebitosAtivoUmFornecedor() {
        initialize();
    }

    private void iniciarTabelaCliente() {
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
                Fornecedor fornecedor = fornecedores.get(row - 1);
                try {
                    debitos = rep.operacao().getAllDebitosAtivos(fornecedor);
                    iniciarTabelaDebito();
                } catch (RepositoryActionException repositoryActionException) {
                    repositoryActionException.printStackTrace();
                }
            }
        });

        model.setColumnCount(headersCliente.length);
        model.addRow(headersCliente);
        for (int i = 0; i < headersCliente.length; i++) {
            DefaultTableCellRenderer render = new DefaultTableCellRenderer();
            render.setHorizontalAlignment(SwingConstants.CENTER);
            table.getColumnModel().getColumn(i).setCellRenderer(render);
        }

        for (Fornecedor fornecedor : fornecedores) {
            Object[] rowData = new Object[headersCliente.length];

            rowData[0] = fornecedor.getCnpj();
            rowData[1] = fornecedor.getNome();
            rowData[2] = fornecedor.getDescricao();
            rowData[3] = fornecedor.getEndereco();
            rowData[4] = fornecedor.getTelefone();
            rowData[5] = fornecedor.getEmail();
            model.addRow(rowData);
        }

        model.fireTableDataChanged();

        table.setBounds(12, 88, 761, 355);

        table.setVisible(true);
        table.setTableHeader(null);
    }

    private void iniciarTabelaDebito() {
        DefaultTableModel model = new DefaultTableModel();
        table = new JTable(model) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.setColumnCount(headersDebito.length);
        model.addRow(headersDebito);
        for (int i = 0; i < headersDebito.length; i++) {
            DefaultTableCellRenderer render = new DefaultTableCellRenderer();
            render.setHorizontalAlignment(SwingConstants.CENTER);
            table.getColumnModel().getColumn(i).setCellRenderer(render);
        }

        for (Debito debito : debitos) {
            Object[] rowData = new Object[headersDebito.length];

            rowData[0] = debito.getOperacao().getId();
            rowData[1] = debito.getValor();
            rowData[2] = debito.getVencimento().toString();
            model.addRow(rowData);
        }

        model.fireTableDataChanged();

        table.setBounds(12, 88, 761, 355);

        table.setVisible(true);
        table.setTableHeader(null);

        scrollPane.setViewportView(table);
        SwingUtilities.updateComponentTreeUI(this);
    }

    private void initialize() {
        try {
            fornecedores = new ArrayList<>(rep.fornecedor().findAll());
        } catch (RepositoryActionException e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        iniciarTabelaCliente();
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

        JLabel lblRealizarCompra = new JLabel("Débitos com um cliente");
        lblRealizarCompra.setFont(new Font("Dialog", Font.BOLD, 20));
        lblRealizarCompra.setHorizontalAlignment(SwingConstants.CENTER);
        lblRealizarCompra.setBounds(12, 12, 936, 28);
        getContentPane().add(lblRealizarCompra);

        scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(12, 88, 1100, 355);
        scrollPane.setViewportView(table);
        getContentPane().add(scrollPane);

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
