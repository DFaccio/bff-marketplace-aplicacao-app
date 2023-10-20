package br.com.dducl.bffmarketplaceapp.modelo.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Table(name = "produto")
@Entity
@Getter
@Setter
public class Produto implements Serializable {

    private static final long serialVersionUID = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String descricao;

    @Column
    private String conteudo;

    @Column
    private Integer quantidade;

    @Column
    private BigDecimal valor;

    @Column
    private boolean disponivel;

    @Column
    private LocalDate dataCriacao;

}
