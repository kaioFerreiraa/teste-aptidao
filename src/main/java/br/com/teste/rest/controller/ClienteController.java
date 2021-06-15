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

import br.com.teste.domain.model.Cliente;
import br.com.teste.exception.RegraNegocioException;
import br.com.teste.rest.DTO.ClienteDTO;
import br.com.teste.service.ClienteService;

@RestController
@RequestMapping("api/cliente")
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<ClienteDTO> getAll() {
        return converter(clienteService.getAll());
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer insert(@RequestBody ClienteDTO cliente) {
        return clienteService.insert(cliente);
    }

    @GetMapping("{id}")
	public ClienteDTO getClienteById(@PathVariable(name="id") Integer id) {
        return clienteService.getById(id).map(cliente -> 
            converter(cliente)).orElseThrow(() ->
            new RegraNegocioException("Cliente não encontrado."));
	}

    private ClienteDTO converter(Cliente cliente) {
        return ClienteDTO.builder()
                         .nome(cliente.getNome())
                         .cpfCnpj(cliente.getCpfCnpj())
                         .build();
    }

    @PutMapping("{id}")
	@ResponseStatus(HttpStatus.OK)
	public void update(@PathVariable Integer id, @RequestBody ClienteDTO clienteDto) {
        clienteService.update(id, clienteDto).orElseThrow(() -> 
        new RegraNegocioException("Cliente não encontrado.")); 
	}

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Integer id) {
        clienteService.delete(id).orElseThrow(() ->
        new RegraNegocioException("Cliente não encontrado."));
    }

    private List<ClienteDTO> converter(List<Cliente> clientes) {
		if(org.springframework.util.CollectionUtils.isEmpty(clientes)) {
			return Collections.emptyList();
		}
		
		return clientes.stream().map(cliente -> ClienteDTO
												.builder().nome(cliente.getNome())
												.cpfCnpj(cliente.getCpfCnpj())
												.build()).collect(Collectors.toList());
	}

}
