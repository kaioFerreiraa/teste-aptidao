package br.com.teste.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.teste.domain.model.Produto;
import br.com.teste.domain.repository.ProdutoRepository;
import br.com.teste.rest.DTO.ProdutoDTO;
import br.com.teste.service.ProdutoService;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    @Override
    public List<Produto> getAll() {
        return produtoRepository.findAll();
    }

    @Override
    public Optional<Produto> getById(Integer id) {
        return produtoRepository.findById(id);
    }

    @Override
    public Integer insert(ProdutoDTO produtoDto) {
        Produto produto = new Produto();

        produto.setNome(produtoDto.getNome());
        produto.setValor(produtoDto.getValor());

        produtoRepository.save(produto);

        return produto.getId();
    }

    @Override
    public Optional<Produto> update(Integer id, ProdutoDTO produtoDto) {
        Produto produto = new Produto();

        produto.setNome(produtoDto.getNome());
        produto.setValor(produtoDto.getValor());

       return produtoRepository.findById(id).map(produtoExistente -> {
			produto.setId(produtoExistente.getId());
			produtoRepository.save(produto);
			return produtoExistente;
            });
    }

    @Override
    public Optional<Produto> delete(Integer id) {
        return produtoRepository.findById(id)
        .map(produto -> {
            produtoRepository.delete(produto);
            return produto;
        });
    }

    
}
