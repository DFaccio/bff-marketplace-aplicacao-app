package br.com.dducl.bffmarketplaceaplicacaoapp.entidades;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Endereco implements Serializable {

    private Integer id;

    private String apelido;

    private String logradouro;

    private String numero;

    private String bairro;

    private String cidade;

    private String estado;

    private String complemento;

    private String cep;
}
