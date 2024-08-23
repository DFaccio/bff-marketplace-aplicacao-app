package br.com.dducl.collective_coffee_marketplace.modelo.persistencia;

import br.com.dducl.collective_coffee_marketplace.modelo.entidades.ChavesPix;

import java.util.Optional;

interface ChavePixRepositoryCustom {

    Optional<ChavesPix> findByChaveAndDocumentoPessoa(String chave, String documentoPessoal);
}
