package br.com.dducl.collective_coffee_marketplace.modelo.persistencia.chavespix;

import br.com.dducl.collective_coffee_marketplace.modelo.entidades.ChavesPix;

import java.util.Optional;

interface ChavePixRepositoryCustom {

    Optional<ChavesPix> findByChaveAndDocumentoPessoa(String chave, String documentoPessoal);
}
