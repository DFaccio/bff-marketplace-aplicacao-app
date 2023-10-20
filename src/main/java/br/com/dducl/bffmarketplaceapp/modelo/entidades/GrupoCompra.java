package br.com.dducl.bffmarketplaceapp.modelo.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "grupocompra")
public class GrupoCompra implements Serializable {

    private static final long serialVersionUID = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private LocalDateTime dataCriacao;

    @Column
    private String nome;

    @Column
    private boolean ativo;

    @ManyToMany
    @JoinTable(name = "membros_grupo_compra", joinColumns = @JoinColumn(name = "grupocompra_id"), inverseJoinColumns = @JoinColumn(name = "pessoa_id"))
    private List<Pessoa> pessoas;

}
