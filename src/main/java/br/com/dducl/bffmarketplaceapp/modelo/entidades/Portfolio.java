package br.com.dducl.bffmarketplaceapp.modelo.entidades;

import br.com.dducl.bffmarketplaceapp.util.enums.StatusPortifolio;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private Fornecedor fornecedor;

    @Column
    private String descricao;

    @ManyToMany
    @JoinTable(name = "portfolio_produto", joinColumns = @JoinColumn(name = "idPortfolio"), inverseJoinColumns = @JoinColumn(name = "idProduto"))
    private List<PortfolioProdutos> portfolioProdutos;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataCriacao;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataVigencia;

    @Column
    @Enumerated(value = EnumType.STRING)
    private StatusPortifolio status;
}