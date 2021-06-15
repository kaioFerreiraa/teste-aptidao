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
public class VendaDTO {

    private Integer cliente;

    private String data;

    private BigDecimal valorFinal;

    private List<ItemVendaDTO> itemVendas;
}
