package br.com.dducl.collective_coffee_marketplace.modelo.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GrupoRecebimento implements Serializable {

    @Serial
    private static final long serialVersionUID = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String nome;

    @Column
    private String email;

    @Column
    private String telefone;

    @Temporal(value = TemporalType.TIMESTAMP)
    private LocalDateTime dataCriacao;

    @Column
    private boolean ativo;

    @OneToOne(cascade = CascadeType.ALL)
    private Endereco endereco;
}