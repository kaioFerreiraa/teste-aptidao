package br.com.teste.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.teste.domain.model.Venda;

public interface VendaRepository extends JpaRepository<Venda, Integer> {
    
}
