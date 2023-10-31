package br.com.dducl.bffmarketplaceapp.modelo.persistencia;

import br.com.dducl.bffmarketplaceapp.modelo.entidades.ChavesPix;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

class ChavePixRepositoryCustomImpl implements ChavePixRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ChavesPix findChavePixByPessoaAndChave(String identificador, String chave) {
        String stringQuery = new StringBuilder()
                .append("select cp.* from chaves_pix cp ")
                .append("inner join pessoa_chave pc on cp.id = pc.chaves_pix ")
                .append("inner join pessoa p on p.identificador  = pc.pessoa_id ")
                .append("where p.identificador = '")
                .append(identificador)
                .append("' and cp.chave = '")
                .append(chave)
                .append("'").toString();

        Query query = entityManager.createNativeQuery(stringQuery, ChavesPix.class);

        try {
            return (ChavesPix) query.getSingleResult();
        } catch (NoResultException exception) {
            return null;
        }
    }
}
