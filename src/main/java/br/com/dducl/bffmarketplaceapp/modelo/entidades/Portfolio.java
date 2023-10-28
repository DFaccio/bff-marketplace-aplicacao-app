package br.com.dducl.bffmarketplaceapp.modelo.entidades;

import br.com.dducl.bffmarketplaceapp.util.enums.StatusPortifolio;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "portfolio")
public class Portfolio implements Serializable {

    private static final long serialVersionUID = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    // TODO Campo não mapeado no MER
    private String descricao;

    private Integer IdFornecedor;

    /*
        @ManyToMany
        @JoinTable(name = "portfolio_fornecedor", joinColumns = @JoinColumn(name = "portfolio_id"), inverseJoinColumns = @JoinColumn(name = "fornecedor_id"))
        private List<Fornecedor> fornecedores;
    */

    @ManyToMany
    @JoinTable(name = "portfolio_produto", joinColumns = @JoinColumn(name = "portfolio_id"), inverseJoinColumns = @JoinColumn(name = "produto_id"))
    private List<Produto> produto;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataCriacao;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataVigencia;

    @Enumerated(value = EnumType.STRING)
    private StatusPortifolio status;
}
