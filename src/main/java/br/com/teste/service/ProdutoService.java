package br.com.teste.service;

import java.util.List;
import java.util.Optional;

import br.com.teste.domain.model.Produto;
import br.com.teste.rest.DTO.ProdutoDTO;

public interface ProdutoService {
    List<Produto> getAll();
    Optional<Produto> getById(Integer id);
    Integer insert(ProdutoDTO produtoDto);
    Optional<Produto> update(Integer id, ProdutoDTO ProdutoDto);
    Optional<Produto> delete(Integer id);
}
