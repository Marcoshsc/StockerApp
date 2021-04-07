package br.ufop.stocker;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.sql.Date;
import java.util.Set;

import br.ufop.stocker.enums.EnumFormaPagamento;
import br.ufop.stocker.enums.EnumTipoOperacao;
import br.ufop.stocker.model.Cliente;
import br.ufop.stocker.model.Compra;
import br.ufop.stocker.model.Debito;
import br.ufop.stocker.model.Fornecedor;
import br.ufop.stocker.model.ItemOperacao;
import br.ufop.stocker.model.Operacao;
import br.ufop.stocker.model.Produto;
import br.ufop.stocker.model.Venda;
import br.ufop.stocker.repository.exception.RepositoryActionException;
import br.ufop.stocker.repository.factory.RepositoryFactory;
import br.ufop.stocker.repository.interfaces.ClienteRepository;
import br.ufop.stocker.repository.interfaces.FornecedorRepository;
import br.ufop.stocker.repository.interfaces.OperacaoRepository;
import br.ufop.stocker.repository.interfaces.ProdutoRepository;

public class ExemploUsoRepositorios {

    public static void main(String[] args) throws RepositoryActionException {
        RepositoryFactory factory = RepositoryFactory.create();
        ProdutoRepository produtoRepository = factory.produto();
        ClienteRepository clienteRepository = factory.cliente();
        FornecedorRepository fornecedorRepository = factory.fornecedor();
        OperacaoRepository operacaoRepository = factory.operacao();

        // TESTES PRODUTO E FORNECEDOR
        Produto produto1 = new Produto(1, "Celular Teste", 1099.90, 12, "5.5 Polegadas");
        Produto produto2 = new Produto(2, "Notebook Teste", 3400.00, 5, "8 GB - 1 TB");
        Produto produto3 = new Produto(3, "TV Teste", 2600.00, 2, "48 pol");
        
        produtoRepository.insert(produto1);
        produtoRepository.insert(produto2);
        produtoRepository.insert(produto3);
        
        System.out.println("Todos os produtos: ");
        Set<Produto> produtoSet = produtoRepository.findAll();
        System.out.println(produtoSet);
        
        Fornecedor fornecedor = new Fornecedor(1, "Charles", "Descricao", "Rua teste 00", "91234-5678", "teste@gmail.com", Timestamp.valueOf(LocalDateTime.now()), "12345678999");
        fornecedor.addProduto(produto1);
        fornecedor.addProduto(produto3);
        fornecedor = fornecedorRepository.insert(fornecedor);
        
        System.out.println("------------------------------------------------");
        
        System.out.println("Fornecedor de id 1: ");
        Fornecedor f = fornecedorRepository.findOne(1, true);
        System.out.println(f);
        
        System.out.println("------------------------------------------------");
        
        System.out.println("Produto de id 1: ");
        Produto p = produtoRepository.findOne(1, true);
        System.out.println(p);

        System.out.println("Produto de id 2: ");
        Produto p1 = produtoRepository.findOne(2, true);
        System.out.println(p1);
        
        System.out.println("------------------------------------------------");
        
        produto1.setNome("Xiaomi Mi 10"); produto1.setPreco(2200.00);
        produtoRepository.update(1, produto1);
        System.out.println("Produto de id 1 atualizado: ");
        p = produtoRepository.findOne(1, true);
        System.out.println(p);
        
        System.out.println("------------------------------------------------");
        
        produtoRepository.delete(2);
        System.out.println("Produto de id 2 deletado: ");
        p1 = produtoRepository.findOne(2, true);
        System.out.println(p1);
        
        System.out.println("------------------------------------------------");

        fornecedor.removeProduto(produto2);
        fornecedor = fornecedorRepository.update(fornecedor.getId(), fornecedor);
        System.out.println("Fornecedor atualizado: ");
        System.out.println(fornecedor);

        System.out.println("------------------------------------------------");
        
        System.out.println("Todos os fornecedores: ");
        Set<Fornecedor> fornecedorSet = fornecedorRepository.findAll();
        System.out.println(fornecedorSet);
        
        // TESTES CLIENTE
        Date date = new Date(System.currentTimeMillis());
        Timestamp timestamp = new Timestamp(date.getTime());
        Cliente cliente1 = new Cliente(1, "Charles", "Descricao", "Rua teste 00", "91234-5678", "teste@gmail.com", timestamp, "12345678999");
        Cliente cliente2 = new Cliente(10, "Lucas", "Teste", "Rua teste 22", "91234-5678", "teste@gmail.com", timestamp, "44332211221");
        
        Cliente c1 = clienteRepository.insert(cliente1);
        Cliente c2 = clienteRepository.insert(cliente2);

        System.out.println(c1);
        System.out.println(c2);
        c1.setNome("José Fino");
        c1 = clienteRepository.update(c1.getId(), c1);
        
        System.out.println("------------------------------------------------");
        System.out.println("Cliente atualizado com sucesso: ");
        Cliente search = clienteRepository.findOne(c1.getId(), true);
        System.out.println(search);

        System.out.println("------------------------------------------------");
        
        System.out.println("Todos os clientes: ");
        Set<Cliente> clienteSet = clienteRepository.findAll();
        System.out.println(clienteSet);

        System.out.println("------------------------------------------------");
        clienteRepository.delete(1);
        System.out.println("Cliente de id 1 deletado: ");
        clienteSet = clienteRepository.findAll();
        System.out.println(clienteSet);
        
        // TESTES COMPRA
        Compra compra = new Compra(-1, Timestamp.valueOf(LocalDateTime.now()), -1, EnumFormaPagamento.DINHEIRO);
        compra.setFornecedor(fornecedor);
        ItemOperacao itemOperacao = new ItemOperacao(1, 3);
        itemOperacao.setProduto(produto1);
        compra.addItem(itemOperacao);
        operacaoRepository.insert(compra);
        
        Compra compra1 = new Compra(-1, Timestamp.valueOf(LocalDateTime.now()), -1, EnumFormaPagamento.PRAZO);
        compra1.setFornecedor(fornecedor);
        ItemOperacao itemOperacao1 = new ItemOperacao(3, 4);
        itemOperacao1.setProduto(produto3);
        compra1.addItem(itemOperacao1);

        Date data = Date.valueOf("2023-04-09");
        Debito debito = new Debito(1, 8, compra1.getValorFinal(), false, data);
        compra1.addDebito(debito);
        operacaoRepository.insert(compra1);

        System.out.println("------------------------------------------------");
        
        System.out.println("Todas as Compras: ");
        Set<Operacao> operacaoSet = operacaoRepository.findAll(EnumTipoOperacao.COMPRA);
        System.out.println(operacaoSet);
        
        System.out.println("------------------------------------------------");
        
        System.out.println("Compras do fornecedor " + fornecedor.getNome() + ":");
        Set<Operacao> c = operacaoRepository.findAllByFornecedor(fornecedor);
        System.out.println(c);
        
        factory.operacao().delete(compra.getId());
        factory.operacao().delete(compra1.getId());
        
        // TESTES VENDA
        Venda venda = new Venda(-1, Timestamp.valueOf(LocalDateTime.now()), -1, EnumFormaPagamento.DINHEIRO);
        venda.setCliente(c2);
        ItemOperacao itemOperacao2 = new ItemOperacao(1, 3);
        itemOperacao2.setProduto(produto1);
        venda.addItem(itemOperacao2);
        operacaoRepository.insert(venda);
        
        Venda venda1 = new Venda(-1, Timestamp.valueOf(LocalDateTime.now()), -1, EnumFormaPagamento.PRAZO);
        venda1.setCliente(c2);
        ItemOperacao itemOperacao3 = new ItemOperacao(3, 4);
        itemOperacao.setProduto(produto3);
        venda1.addItem(itemOperacao3);

        Date data1 = Date.valueOf("2023-04-09");
        Debito debito1 = new Debito(1, 8, venda1.getValorFinal(), false, data1);
        venda1.addDebito(debito1);
        operacaoRepository.insert(venda1);

        System.out.println("------------------------------------------------");
        
        System.out.println("Todas as vendas: ");
        Set<Operacao> operacaoSet1 = operacaoRepository.findAll(EnumTipoOperacao.VENDA);
        System.out.println(operacaoSet1);
        
        System.out.println("------------------------------------------------");
        
        System.out.println("Todas as vendas do cliente " + c2.getNome() + ": ");
        Set<Operacao> v = operacaoRepository.findAllByCliente(c2);
        System.out.println(v);
        
        factory.operacao().delete(venda.getId());
        
    }

}
