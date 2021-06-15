package br.com.teste.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.teste.domain.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
    
}
