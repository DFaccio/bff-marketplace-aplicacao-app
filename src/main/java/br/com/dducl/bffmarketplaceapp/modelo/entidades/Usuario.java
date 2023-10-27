package br.com.dducl.bffmarketplaceapp.modelo.entidades;

import br.com.dducl.bffmarketplaceapp.util.enums.Perfil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1;

    @Id
    private String nome;

    @ManyToMany
    @JoinTable(name = "usuario_senha", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "senha_id"))
    private List<SenhaUsuario> senha;

    @Enumerated(EnumType.STRING)
    private Perfil perfil;

}
