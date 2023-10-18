package br.com.dducl.bffmarketplaceaplicacaoapp.entidades;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class Pessoa implements Serializable {

    private Integer id;

    private String nome;

    private String identificador;

    private String email;

    private String telefone;

    private LocalDateTime dataCadastro;

    private boolean ativo;

    private Usuario usuario;
}
