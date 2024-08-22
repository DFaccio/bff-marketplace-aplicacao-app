package br.com.dducl.collective_coffee_marketplace.modelo.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = "fornecedor")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Fornecedor implements Serializable {

    @Serial
    private static final long serialVersionUID = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @OneToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    @Column(name = "razao_social")
    private String razaoSocial;

}
