package br.ufop.stocker;

import br.ufop.stocker.model.Cliente;
import br.ufop.stocker.repository.factory.RepositoryFactory;
import br.ufop.stocker.repository.interfaces.ClienteRepository;
import br.ufop.stocker.repository.interfaces.ProdutoRepository;

import java.util.Set;

public class ExemploUsoRepositorios {

    public static void main(String[] args) {
        RepositoryFactory factory = new RepositoryFactory();
        ProdutoRepository produtoRepository = factory.produto();
        produtoRepository.delete(1);

        ClienteRepository clienteRepository = factory.cliente();
        Set<Cliente> clientes = clienteRepository.findAll();
        System.out.println(clientes);
    }

}
