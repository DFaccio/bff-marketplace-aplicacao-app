package br.com.dducl.bffmarketplaceaplicacaoapp.entidades;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class Portifolio implements Serializable {

    private List<Fornecedor> fornecedores;

    private List<Produto> produto;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataVigencia;

    private boolean ativo;
}
