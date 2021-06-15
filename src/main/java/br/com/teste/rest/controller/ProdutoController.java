package br.com.teste.rest.controller;

import static org.springframework.http.HttpStatus.CREATED;

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

import br.com.teste.domain.model.Produto;
import br.com.teste.exception.RegraNegocioException;
import br.com.teste.rest.DTO.ProdutoDTO;
import br.com.teste.service.ProdutoService;


@RestController
@RequestMapping("api/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public List<ProdutoDTO> getAll() {
        return converter(produtoService.getAll());
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer insert(@RequestBody ProdutoDTO produto) {
        return produtoService.insert(produto);
    }

    @GetMapping("{id}")
	public ProdutoDTO getProdutoById(@PathVariable(name="id") Integer id) {
        return produtoService.getById(id).map(produto -> 
            converter(produto)).orElseThrow(() ->
            new RegraNegocioException("Produto não encontrado."));
	}

    private ProdutoDTO converter(Produto produto) {
        return ProdutoDTO.builder()
        .nome(produto.getNome())
        .valor(produto.getValor())
        .build();
    }

    @PutMapping("{id}")
	@ResponseStatus(HttpStatus.OK)
	public void update(@PathVariable Integer id, @RequestBody ProdutoDTO produtoDto) {
        produtoService.update(id, produtoDto).orElseThrow(() -> 
        new RegraNegocioException("Cliente não encontrado.")); 
	}

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Integer id) {
        produtoService.delete(id).orElseThrow(() ->
        new RegraNegocioException("Cliente não encontrado."));
    }

    private List<ProdutoDTO> converter(List<Produto> produtos) {
		if(org.springframework.util.CollectionUtils.isEmpty(produtos)) {
			return Collections.emptyList();
		}
		
		return produtos.stream().map(produto -> ProdutoDTO
												.builder().nome(produto.getNome())
												.valor(produto.getValor())
												.build()).collect(Collectors.toList());
	}

}
