package br.com.teste.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.teste.domain.model.Cliente;
import br.com.teste.domain.repository.ClienteRepository;
import br.com.teste.rest.DTO.ClienteDTO;
import br.com.teste.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public List<Cliente> getAll() {
        return clienteRepository.findAll();
    }

    @Override
    public Optional<Cliente> getById(Integer id) {
        return clienteRepository.findById(id);
    }

    @Override
    public Integer insert(ClienteDTO clienteDto) {
        Cliente cliente = new Cliente();

        cliente.setNome(clienteDto.getNome());
        cliente.setCpfCnpj(clienteDto.getCpfCnpj());

        clienteRepository.save(cliente);

        return cliente.getId();
    }

    @Override
    public Optional<Cliente> update(Integer id, ClienteDTO clienteDto) {
        Cliente cliente = new Cliente();

        cliente.setNome(clienteDto.getNome());
        cliente.setCpfCnpj(clienteDto.getCpfCnpj());

       return clienteRepository.findById(id).map(clienteExistente -> {
			cliente.setId(clienteExistente.getId());
			clienteRepository.save(cliente);
			return clienteExistente;
            });
    }

    @Override
    public Optional<Cliente> delete(Integer id) {
        return clienteRepository.findById(id)
            .map(cliente -> {
                clienteRepository.delete(cliente);
                return cliente;
            });
    }
    
}
