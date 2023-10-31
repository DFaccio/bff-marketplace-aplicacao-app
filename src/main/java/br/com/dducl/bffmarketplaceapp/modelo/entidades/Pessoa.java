package br.com.dducl.bffmarketplaceapp.modelo.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "pessoa")
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1;

    @Column
    private String nome;

    @Id
    private String identificador;

    @Column
    private String email;

    @Column
    private String telefone;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataCadastro;

    @Column
    private boolean ativo;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "pessoa_chave", joinColumns = @JoinColumn(name = "pessoa_id"), inverseJoinColumns = @JoinColumn(name = "chaves_pix"))
    private List<ChavesPix> chaves;

    @OneToOne(cascade = CascadeType.ALL)
    private Endereco endereco;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Pessoa)) return false;

        Pessoa pessoa = (Pessoa) o;

        return identificador.equals(pessoa.identificador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }
}