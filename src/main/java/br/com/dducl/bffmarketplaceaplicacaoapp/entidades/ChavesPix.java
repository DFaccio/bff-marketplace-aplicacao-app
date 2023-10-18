package br.com.dducl.bffmarketplaceaplicacaoapp.entidades;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class ChavesPix implements Serializable {

    private String chave;

    private boolean ativo;

    private LocalDate dataCadastro;
}
