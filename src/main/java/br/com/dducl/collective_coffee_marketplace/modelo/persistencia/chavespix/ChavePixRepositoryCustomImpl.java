package br.com.dducl.collective_coffee_marketplace.modelo.persistencia.chavespix;

import br.com.dducl.collective_coffee_marketplace.modelo.entidades.ChavesPix;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.Optional;

class ChavePixRepositoryCustomImpl implements ChavePixRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<ChavesPix> findByChaveAndDocumentoPessoa(String chave, String documentoPessoal) {
        String sql = "SELECT chaves.* " +
                "FROM chaves_pix AS chaves " +
                "INNER JOIN pessoa_chave AS pc ON pc.chaves_pix = chaves.id " +
                "WHERE pc.pessoa_documento = '" + documentoPessoal +
                "' AND chaves.chave = '" + chave + "'";

        Query query = entityManager.createNativeQuery(sql, ChavesPix.class);

        try {
            return Optional.of((ChavesPix) query.getSingleResult());
        } catch (NoResultException exception) {
            return Optional.empty();
        }
    }
}
