package br.ufop.stocker;

import br.ufop.stocker.enums.EnumFormaPagamento;
import br.ufop.stocker.model.Compra;
import br.ufop.stocker.model.Fornecedor;
import br.ufop.stocker.model.ItemOperacao;
import br.ufop.stocker.model.Produto;
import br.ufop.stocker.repository.exception.RepositoryActionException;
import br.ufop.stocker.repository.factory.RepositoryFactory;
import br.ufop.stocker.repository.interfaces.FornecedorRepository;
import br.ufop.stocker.repository.interfaces.ProdutoRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

public class ExemploUsoRepositorios {

    public static void main(String[] args) throws RepositoryActionException {
        RepositoryFactory factory = RepositoryFactory.create();
        ProdutoRepository produtoRepository = factory.produto();
        FornecedorRepository fornecedorRepository = factory.fornecedor();

//        Produto produto4 = produtoRepository.findOne(4, true);
//        Produto produto2 = produtoRepository.findOne(2, true);
//        System.out.println(produto4);
//        System.out.println(produto2);
//        Set<Fornecedor> fornecedorSet = fornecedorRepository.findAll();
//        System.out.println(fornecedorSet);
//        Fornecedor fornecedor = fornecedorRepository.findOne(4, true);
//        Compra compra = new Compra(-1, Timestamp.valueOf(LocalDateTime.now()), -1, EnumFormaPagamento.DINHEIRO);
//        compra.setFornecedor(fornecedor);
//        ItemOperacao itemOperacao = new ItemOperacao(-1, 3);
//        itemOperacao.setProduto(produto4);
//        compra.addItem(itemOperacao);

        Compra compra = new Compra(factory.operacao().findOne(2, true));
        System.out.println(compra);
        Produto produto4 = produtoRepository.findOne(4, true);
        Fornecedor fornecedor = fornecedorRepository.findOne(4, true);
        System.out.println(compra);
        System.out.println(produto4);
        System.out.println(fornecedor);
        factory.operacao().delete(2);
//        Fornecedor fornecedor = new Fornecedor(1, "Charles", "Descricao", "Rua teste 00", "91234-5678", "teste@gmail.com", Timestamp.valueOf(LocalDateTime.now()), "12345678999");
//        fornecedor.addProduto(produto);
//        fornecedor = fornecedorRepository.insert(fornecedor);
//        Fornecedor fornecedor = fornecedorRepository.findOne(3, true);
//        System.out.println(fornecedor);
//        fornecedor.addProduto(produto4);
//        fornecedor.removeProduto(produto2);
//        fornecedor = fornecedorRepository.update(fornecedor.getId(), fornecedor);
//        System.out.println(fornecedor);
//        ClienteRepository clienteRepository = factory.cliente();
//
//        Date date = new Date();
//        Timestamp timestamp = new Timestamp(date.getTime());
//        Cliente cliente = new Cliente(1, "Charles", "Descricao", "Rua teste 00", "91234-5678", "teste@gmail.com", timestamp, "12345678999");
//        Cliente c1 = clienteRepository.insert(cliente);
//
//        Cliente cliente1 = new Cliente(10, "Lucas", "Teste", "Rua teste 22", "91234-5678", "teste@gmail.com", timestamp, "44332211221");
//        System.out.println(c1);
//        c1.setNome("JoséFino");
//        c1 = clienteRepository.update(c1.getId(), c1);
//        System.out.println(c1);
//        Cliente search = clienteRepository.findOne(c1.getId());
//        System.out.println(search);

//        System.out.println(c1.toString());
        
        //clienteRepository.delete(1);
        
        //Set<Cliente> clientes = clienteRepository.findAll();
        //Iterator<Cliente> i = clientes.iterator();
        //while (i.hasNext())
        //   System.out.println(i.next().toString());
        
    }

}
