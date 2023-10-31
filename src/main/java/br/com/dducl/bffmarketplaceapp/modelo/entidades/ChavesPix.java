package br.com.dducl.bffmarketplaceapp.modelo.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "chaves_pix")
public class ChavesPix implements Serializable {

    private static final long serialVersionUID = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column
    private String chave;

    @Column
    private boolean ativo;

    @Column
    @Temporal(TemporalType.DATE)
    private LocalDate dataCadastro;
}
