package br.com.teste.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.teste.domain.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    
}
