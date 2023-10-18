package br.com.dducl.bffmarketplaceaplicacaoapp.entidades;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class GrupoCompra implements Serializable {

    private Integer id;

    private LocalDateTime dataCriacao;

    private String nome;

    private boolean ativo;

    private List<Pessoa> pessoas;

}
