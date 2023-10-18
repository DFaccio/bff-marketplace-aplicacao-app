package br.com.dducl.bffmarketplaceaplicacaoapp.entidades;

import br.com.dducl.bffmarketplaceaplicacaoapp.util.Perfil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Usuario implements Serializable {

    private Integer id;

    private String nome;

    private SenhaUsuario senha;

    private Perfil perfil;

}
