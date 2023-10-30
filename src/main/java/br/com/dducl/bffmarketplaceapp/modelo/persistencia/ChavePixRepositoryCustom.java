package br.com.dducl.bffmarketplaceapp.modelo.persistencia;

import br.com.dducl.bffmarketplaceapp.modelo.entidades.ChavesPix;

interface ChavePixRepositoryCustom {

    ChavesPix findChavePixByPessoaAndChave(String identificador, String chave) throws NoSuchFieldException;
}
