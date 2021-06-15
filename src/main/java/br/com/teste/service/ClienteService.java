package br.com.teste.service;

import java.util.List;
import java.util.Optional;

import br.com.teste.domain.model.Cliente;
import br.com.teste.rest.DTO.ClienteDTO;


public interface ClienteService {
    List<Cliente> getAll();
    Optional<Cliente> getById(Integer id);
    Integer insert(ClienteDTO clienteDto);
    Optional<Cliente> update(Integer id, ClienteDTO clienteDto);
    Optional<Cliente> delete(Integer id);
}
