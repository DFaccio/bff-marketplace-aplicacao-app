package br.com.dducl.bffmarketplaceaplicacaoapp.entidades;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@Setter
public class SenhaUsuario implements Serializable {

    private Integer id;

    private LocalDateTime ultimaAtualizacao;

    private String senha;

}
