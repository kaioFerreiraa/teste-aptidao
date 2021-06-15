package br.com.teste.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="venda")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Venda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(name="data_pedido")
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name="id_cliente")
    private Cliente cliente;

    @Column(name="valor_final", precision=20, scale=2)
    private BigDecimal valorFinal;

    @OneToMany(mappedBy="venda")
    private List<ItemVenda> itemVenda;
}
