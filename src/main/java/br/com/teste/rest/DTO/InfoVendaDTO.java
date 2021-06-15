package br.com.teste.rest.DTO;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class InfoVendaDTO {
    private String cpf;
    
    private String nomeCliente;

    private String data;

    private BigDecimal valorTotal;

    private String prazoEntrega;

    private List<InfoProdutoDTO> infoProduto;

}
