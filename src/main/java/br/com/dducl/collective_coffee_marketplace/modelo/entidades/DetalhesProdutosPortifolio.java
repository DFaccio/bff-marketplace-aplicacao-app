package br.com.dducl.collective_coffee_marketplace.modelo.entidades;

import br.com.dducl.collective_coffee_marketplace.util.enums.TipoDesconto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Table
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalhesProdutosPortifolio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @OneToOne
    private Produto produto;

    @Column
    private BigDecimal valor;

    @Column
    private Integer quantidadeParaDesconto;

    @Column
    private BigDecimal desconto;

    @Enumerated(EnumType.STRING)
    private TipoDesconto tipoDesconto;

}
