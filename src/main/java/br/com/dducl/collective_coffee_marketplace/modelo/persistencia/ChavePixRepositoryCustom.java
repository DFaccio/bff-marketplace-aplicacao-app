package br.com.dducl.collective_coffee_marketplace.modelo.persistencia;

import br.com.dducl.collective_coffee_marketplace.modelo.entidades.ChavesPix;

interface ChavePixRepositoryCustom {

    ChavesPix findChavePixByPessoaAndChave(String identificador, String chave) throws NoSuchFieldException;
}
