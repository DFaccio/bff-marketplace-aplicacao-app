package br.com.dducl.bffmarketplaceapp.modelo.entidades;

import br.com.dducl.bffmarketplaceapp.util.enums.StatusSenha;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;


@Getter
@Setter
@Entity
@Table(name = "senha")
@NoArgsConstructor
public class SenhaUsuario implements Serializable {

    private static final long serialVersionUID = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime ultimaAtualizacao;

    @Column
    private String senha;

    @Enumerated(EnumType.STRING)
    private StatusSenha status;

    public SenhaUsuario(String senha) {
        this.senha = senha;
        this.status = StatusSenha.ATIVO;
        this.ultimaAtualizacao = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof SenhaUsuario)) return false;

        SenhaUsuario that = (SenhaUsuario) o;

        return senha.equals(that.senha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(senha);
    }

    public boolean isSenhaAtiva() {
        return this.status.equals(StatusSenha.ATIVO);
    }

    public void desativaSenha() {
        this.status = StatusSenha.EXPIRADO;
        this.ultimaAtualizacao = LocalDateTime.now();
    }
}
