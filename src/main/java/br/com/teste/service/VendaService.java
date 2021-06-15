package br.com.teste.service;

import java.util.List;
import java.util.Optional;

import br.com.teste.domain.model.Venda;
import br.com.teste.rest.DTO.VendaDTO;

public interface VendaService {
    List<Venda> getAll();
    Optional<Venda> getById(Integer id);
    Integer insert(VendaDTO clienteDto);
    Optional<Venda> update(Integer id, VendaDTO clienteDto);
    Optional<Venda> delete(Integer id);
}
