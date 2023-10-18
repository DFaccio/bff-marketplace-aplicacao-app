package br.com.dducl.bffmarketplaceaplicacaoapp.entidades;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Fornecedor implements Serializable {

    private Integer id;

    private Pessoa pessoa;

    private String razaoSocial;

    private ChavesPix pix;

}
