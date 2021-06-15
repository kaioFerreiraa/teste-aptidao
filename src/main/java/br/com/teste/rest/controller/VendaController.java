package br.com.teste.rest.controller;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.teste.domain.model.ItemVenda;
import br.com.teste.domain.model.Venda;
import br.com.teste.exception.RegraNegocioException;
import br.com.teste.rest.DTO.InfoProdutoDTO;
import br.com.teste.rest.DTO.InfoVendaDTO;
import br.com.teste.rest.DTO.VendaDTO;
import br.com.teste.service.VendaService;

@RestController
@RequestMapping("api/venda")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @GetMapping
    public List<InfoVendaDTO> getAll() {
        return converter(vendaService.getAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer insert(@RequestBody VendaDTO venda) {
        return vendaService.insert(venda);
    }

    @GetMapping("{id}")
	public InfoVendaDTO getVendaById(@PathVariable(name="id") Integer id) {
        return vendaService.getById(id).map(venda-> 
            converter(venda)).orElseThrow(() ->
            new RegraNegocioException("Venda não encontrada."));
	}

    private InfoVendaDTO converter(Venda venda) {
        return InfoVendaDTO.builder()
        .nomeCliente(venda.getCliente().getNome())
        .cpf(venda.getCliente().getCpfCnpj())
        .valorTotal(venda.getValorFinal())
        .infoProduto(converterInfoProduto(venda.getItemVenda()))
        .data(venda.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
        .prazoEntrega(venda.getData().plusDays(10).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
        .build();
    }

    @PutMapping("{id}")
	@ResponseStatus(HttpStatus.OK)
	public void update(@PathVariable Integer id, @RequestBody VendaDTO vendaDto) {
        vendaService.update(id, vendaDto).orElseThrow(() -> 
        new RegraNegocioException("Venda não encontrada.")); 
	}

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Integer id) {
        vendaService.delete(id).orElseThrow(() ->
        new RegraNegocioException("Venda não encontrada."));
    }

    private List<InfoProdutoDTO> converterInfoProduto(List<ItemVenda> vendas) {
		if(org.springframework.util.CollectionUtils.isEmpty(vendas)) {
			return Collections.emptyList();
		}
		
		return vendas.stream().map(item -> InfoProdutoDTO
												.builder()
                                                .nomeProduto(item.getProduto().getNome())
                                                .valor(item.getProduto().getValor())
												.build()).collect(Collectors.toList());
	}

    private List<InfoVendaDTO> converter(List<Venda> vendas) {
		if(org.springframework.util.CollectionUtils.isEmpty(vendas)) {
			return Collections.emptyList();
		}
		
		return (List<InfoVendaDTO>) vendas.stream().map(venda -> InfoVendaDTO
												.builder()
                                                .nomeCliente(venda.getCliente().getNome())
                                                .cpf(venda.getCliente().getCpfCnpj())
                                                .valorTotal(venda.getValorFinal())
                                                .infoProduto(converterInfoProduto(venda.getItemVenda()))
                                                .data(venda.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                                                .prazoEntrega(venda.getData().plusDays(10).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
												.build()).collect(Collectors.toList());
	}
}
