package debitosativo;

import br.ufop.stocker.enums.EnumTipoOperacao;
import br.ufop.stocker.model.NomeDebito;
import br.ufop.stocker.repository.exception.RepositoryActionException;
import br.ufop.stocker.repository.factory.RepositoryFactory;
import utils.Functions;
import view.Menu;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DebitosAtivosFornecedor extends JFrame {

    private List<NomeDebito> nomeDebitoList;
    private RepositoryFactory rep = RepositoryFactory.create();
    private JTable table;
    private String[] headers = {"Nome cliente", "Valor debitos"};
    private JScrollPane scrollPane;

    public DebitosAtivosFornecedor() {
        initialize();
    }

    private void iniciarTabela() {
        DefaultTableModel model = new DefaultTableModel();
        table = new JTable(model) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            };
        };

        model.setColumnCount(2);
        model.addRow(headers);
        for (int i = 0; i < 2; i++) {
            DefaultTableCellRenderer render = new DefaultTableCellRenderer();
            render.setHorizontalAlignment(SwingConstants.CENTER);
            table.getColumnModel().getColumn(i).setCellRenderer(render);
        }

        for (NomeDebito nomeDebito : nomeDebitoList) {
            Object rowData[] = new Object[2];

            rowData[0] = nomeDebito.getNome();
            rowData[1] = nomeDebito.getValor();
            model.addRow(rowData);
        }

        model.fireTableDataChanged();

        table.setBounds(12, 88, 761, 355);

        table.setVisible(true);
        table.setTableHeader(null);
    }

    private void initialize() {
        try {
            nomeDebitoList = rep.operacao().getNomeDebitos(EnumTipoOperacao.COMPRA);
        } catch (RepositoryActionException e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        iniciarTabela();
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

        JLabel lblRealizarCompra = new JLabel("Fornecedores com débitos ativos");
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
