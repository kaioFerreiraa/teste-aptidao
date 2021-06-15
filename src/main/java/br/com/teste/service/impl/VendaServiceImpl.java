package br.com.teste.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.teste.domain.model.Cliente;
import br.com.teste.domain.model.ItemVenda;
import br.com.teste.domain.model.Produto;
import br.com.teste.domain.model.Venda;
import br.com.teste.domain.repository.ClienteRepository;
import br.com.teste.domain.repository.ItemVendaRepository;
import br.com.teste.domain.repository.ProdutoRepository;
import br.com.teste.domain.repository.VendaRepository;
import br.com.teste.exception.RegraNegocioException;
import br.com.teste.rest.DTO.ItemVendaDTO;
import br.com.teste.rest.DTO.VendaDTO;
import br.com.teste.service.VendaService;

@Service
public class VendaServiceImpl implements VendaService {

    @Autowired
    private VendaRepository vendaRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ItemVendaRepository itemVendaRepository;

    @Override
    public List<Venda> getAll() {
        return vendaRepository.findAll();
    }

    @Override
    public Optional<Venda> getById(Integer id) {
        return vendaRepository.findById(id);
    }

    @Override
    public Integer insert(VendaDTO vendaDto) {
        Integer idCliente = vendaDto.getCliente();
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(() ->
            new RegraNegocioException("Id do cliente inválido."));

        Venda venda = new Venda();

        venda.setData(LocalDate.now());
        venda.setCliente(cliente);
        venda.setValorFinal(vendaDto.getValorFinal());

        List<ItemVenda> itemVenda = converterItens(venda, vendaDto.getItemVendas());
        vendaRepository.save(venda);

        itemVendaRepository.saveAll(itemVenda);
        venda.setItemVenda(itemVenda);

        return venda.getId();
    }

	private List<ItemVenda> converterItens(Venda venda, List<ItemVendaDTO> itens) {
		if(itens.isEmpty()) {
			throw new RegraNegocioException("Não é possivel realizar um pedido sem items.");
		}
		
		return itens
				.stream()
				.map(dto -> {
					Integer idProduto = dto.getProduto();
					Produto produto = produtoRepository.findById(idProduto).orElseThrow(() -> 
					new RegraNegocioException("Id do produto inválido: " + idProduto));
					
					ItemVenda itemVenda = new ItemVenda();
					itemVenda.setProduto(produto);
					itemVenda.setVenda(venda);
					
					return itemVenda;
				}).collect(Collectors.toList());
	}

    @Override
    public Optional<Venda> update(Integer id, VendaDTO vendaDto) {
        Integer idCliente = vendaDto.getCliente();
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(() ->
            new RegraNegocioException("Id do Cliente inválido."));

       return vendaRepository.findById(id).map(vendaExistente -> {

            vendaExistente.setCliente(cliente);
            vendaExistente.setValorFinal(vendaDto.getValorFinal());
            vendaExistente.setData(LocalDate.parse(vendaDto.getData(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            List<ItemVenda> itens = itemVendaRepository.findByVenda(vendaExistente);

            itens = converter(vendaExistente, vendaDto.getItemVendas(), itens);

			vendaRepository.save(vendaExistente);
            vendaExistente.setItemVenda(itens);

            itemVendaRepository.saveAll(itens);


			return vendaExistente;
            });
    }

    private List<ItemVenda> converter(Venda venda, List<ItemVendaDTO> itens, List<ItemVenda> list) {
		if(itens.isEmpty()) {
			throw new RegraNegocioException("Não é possivel realizar um pedido sem items.");
		}
		
		return itens
				.stream()
				.map(dto -> {
					Integer idProduto = dto.getProduto();
					Produto produto = produtoRepository.findById(idProduto).orElseThrow(() -> 
					    new RegraNegocioException("Id do produto inválido: " + idProduto));
					
					ItemVenda itemVenda = list.iterator().next();
					itemVenda.setProduto(produto);
					itemVenda.setVenda(venda);

					return itemVenda;
				}).collect(Collectors.toList());
	}

    @Override
    public Optional<Venda> delete(Integer id) {
        return vendaRepository.findById(id)
            .map(venda -> {
                List<ItemVenda> itens = itemVendaRepository.findByVenda(venda);

                itemVendaRepository.deleteAll(itens);
                vendaRepository.delete(venda);
                return venda;
            });
    }
    
}
