package br.com.dducl.bffmarketplaceaplicacaoapp.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Produto implements Serializable {

    private Integer id;

    private String descricao;

    private String conteudo;

    private Integer quantidade;

    private BigDecimal valor;

    private boolean disponivel;

    private LocalDate dataCriacao;

    private Integer status;

}
