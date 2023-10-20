package br.com.dducl.bffmarketplaceapp.modelo.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = "fornecedor")
public class Fornecedor implements Serializable {

    private static final long serialVersionUID = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @OneToOne
    @JoinColumn(name = "pessoa_identificador")
    private Pessoa pessoa;

    @Column(name = "razao_social")
    private String razaoSocial;

}
