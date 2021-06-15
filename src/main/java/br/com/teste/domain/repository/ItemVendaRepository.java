package br.com.teste.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.teste.domain.model.ItemVenda;
import br.com.teste.domain.model.Venda;

public interface ItemVendaRepository extends JpaRepository<ItemVenda, Integer> {

    List<ItemVenda> findByVenda(Venda venda);
}
