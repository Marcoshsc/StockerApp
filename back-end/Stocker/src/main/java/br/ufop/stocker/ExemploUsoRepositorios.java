package br.ufop.stocker;

import br.ufop.stocker.model.Cliente;
import br.ufop.stocker.repository.exception.RepositoryActionException;
import br.ufop.stocker.repository.factory.RepositoryFactory;
import br.ufop.stocker.repository.interfaces.ClienteRepository;
import br.ufop.stocker.repository.interfaces.ProdutoRepository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class ExemploUsoRepositorios {

    public static void main(String[] args) throws RepositoryActionException {
        RepositoryFactory factory = new RepositoryFactory();
        ProdutoRepository produtoRepository = factory.produto();
        ClienteRepository clienteRepository = factory.cliente();
        
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        Cliente cliente = new Cliente(1, "Charles", "Descricao", "Rua teste 00", "91234-5678", "teste@gmail.com", timestamp, "12345678999");
        Cliente c1 = clienteRepository.insert(cliente);
        
        Cliente cliente1 = new Cliente(10, "Lucas", "Teste", "Rua teste 22", "91234-5678", "teste@gmail.com", timestamp, "44332211221");
        System.out.println(c1);
        c1.setNome("JoséFino");
        c1 = clienteRepository.update(c1.getId(), c1);
        System.out.println(c1);
        Cliente search = clienteRepository.findOne(c1.getId());
//        System.out.println(search);

//        System.out.println(c1.toString());
        
        //clienteRepository.delete(1);
        
        //Set<Cliente> clientes = clienteRepository.findAll();
        //Iterator<Cliente> i = clientes.iterator();
        //while (i.hasNext())
        //   System.out.println(i.next().toString());
        
    }

}
