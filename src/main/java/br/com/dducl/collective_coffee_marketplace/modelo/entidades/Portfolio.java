package br.com.dducl.collective_coffee_marketplace.modelo.entidades;

import br.com.dducl.collective_coffee_marketplace.util.enums.StatusPortfolio;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "portfolio")
public class Portfolio implements Serializable {

    @Serial
    private static final long serialVersionUID = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String descricao;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "portfolio_produtos", joinColumns = @JoinColumn(name = "portfolio_id"), inverseJoinColumns = @JoinColumn(name = "produto_portifolio_id"))
    private Set<DetalhesProdutosPortifolio> produtos;

    @Column
    @Temporal(value = TemporalType.TIMESTAMP)
    private LocalDateTime dataCriacao;

    @Column
    @Temporal(value = TemporalType.TIMESTAMP)
    private LocalDateTime dataVigencia;

    @Column
    @Enumerated(value = EnumType.STRING)
    private StatusPortfolio status;
}