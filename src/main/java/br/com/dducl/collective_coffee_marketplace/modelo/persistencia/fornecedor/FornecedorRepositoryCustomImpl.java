package br.com.dducl.collective_coffee_marketplace.modelo.persistencia.fornecedor;

import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Fornecedor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import org.hibernate.query.sqm.tree.domain.SqmEntityValuedSimplePath;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

class FornecedorRepositoryCustomImpl implements FornecedorRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String ALIAS_PERSON = "pessoa";

    @Override
    public Page<Fornecedor> findAll(String razaoSocial, String telefone, String documento, String email, Pageable pageable) {
        List<Fornecedor> fornecedores = getFornecedores(razaoSocial, telefone, documento, email, pageable);
        Long quantidade = getQuantidade(razaoSocial, telefone, documento, email);

        return new PageImpl<>(fornecedores, pageable, quantidade);
    }

    private Long getQuantidade(String razaoSocial, String telefone, String documento, String email) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Fornecedor> fornecedorRoot = criteriaQuery.from(Fornecedor.class);
        Selection<Object> alias = fornecedorRoot.get(ALIAS_PERSON).alias(ALIAS_PERSON);

        Predicate finalPredicate = getPredicates(razaoSocial, telefone, documento, email, criteriaBuilder, fornecedorRoot, (SqmEntityValuedSimplePath) alias);
        criteriaQuery.where(finalPredicate);

        criteriaQuery.select(criteriaBuilder.count(fornecedorRoot));

        Query query = entityManager.createQuery(criteriaQuery);

        return (Long) query.getSingleResult();
    }

    private List<Fornecedor> getFornecedores(String razaoSocial, String telefone, String documento, String email, Pageable page) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Fornecedor> criteriaQuery = criteriaBuilder.createQuery(Fornecedor.class);
        Root<Fornecedor> fornecedorRoot = criteriaQuery.from(Fornecedor.class);
        Selection<Object> alias = fornecedorRoot.get(ALIAS_PERSON).alias(ALIAS_PERSON);

        Predicate finalPredicate = getPredicates(razaoSocial, telefone, documento, email, criteriaBuilder, fornecedorRoot, (SqmEntityValuedSimplePath) alias);
        criteriaQuery.where(finalPredicate);

        criteriaQuery.select(fornecedorRoot)
                .orderBy(criteriaBuilder.asc(fornecedorRoot.get("razaoSocial")));

        Query query = entityManager.createQuery(criteriaQuery)
                .setFirstResult(page.getPageNumber())
                .setMaxResults(page.getPageSize());

        return query.getResultList();
    }

    private Predicate getPredicates(String razaoSocial, String telefone, String documento, String email,
                                    CriteriaBuilder criteriaBuilder, Root<Fornecedor> pessoaRoot, SqmEntityValuedSimplePath alias) {
        List<Predicate> predicates = new ArrayList<>();

        if (telefone != null && !telefone.isBlank()) {
            predicates.add(criteriaBuilder.like(alias.get("telefone"), "%" + telefone + "%"));
        }

        if (documento != null && !documento.isBlank()) {
            predicates.add(criteriaBuilder.like(alias.get("documento"), "%" + documento + "%"));
        }

        if (razaoSocial != null && !razaoSocial.trim().isEmpty()) {
            Expression<String> upperColumn = criteriaBuilder.upper(pessoaRoot.get("razaoSocial"));
            String razaoToSearch = "%" + razaoSocial.toUpperCase() + "%";

            predicates.add(criteriaBuilder.like(upperColumn, razaoToSearch));
        }

        if (email != null && !email.trim().isEmpty()) {
            Expression<String> upperColumn = criteriaBuilder.upper(alias.get("email"));
            String emailToSearch = "%" + email.toUpperCase() + "%";

            predicates.add(criteriaBuilder.like(upperColumn, emailToSearch));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
