package br.com.dducl.collective_coffee_marketplace.modelo.persistencia.pessoa;

import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Pessoa;
import br.com.dducl.collective_coffee_marketplace.util.enums.Perfil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class PessoaRepositoryCustomImpl implements PessoaRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private Clock clock;

    @Override
    @Transactional
    public Pessoa insert(Pessoa pessoa) {
        pessoa.setDataCadastro(LocalDateTime.now(clock));

        pessoa.getChaves()
                .forEach(chavesPix -> chavesPix.setDataCadastro(LocalDate.now(clock)));

        return entityManager.merge(pessoa);
    }

    @Override
    public Page<Pessoa> findAll(String nome, String telefone, String documento, String email, Perfil perfil, Pageable pageable) {
        List<Pessoa> pessoas = getPessoas(nome, telefone, documento, email, perfil, pageable);
        Long quantidade = getQuantidade(nome, telefone, documento, email, perfil);

        return new PageImpl<>(pessoas, pageable, quantidade);
    }

    private Long getQuantidade(String nome, String telefone, String documento, String email, Perfil perfil) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Pessoa> pessoaRoot = criteriaQuery.from(Pessoa.class);

        Predicate finalPredicate = getPredicates(nome, telefone, documento, email, perfil, criteriaBuilder, pessoaRoot);
        criteriaQuery.where(finalPredicate);

        criteriaQuery.select(criteriaBuilder.count(pessoaRoot));

        Query query = entityManager.createQuery(criteriaQuery);

        return (Long) query.getSingleResult();
    }

    private List<Pessoa> getPessoas(String nome, String telefone, String documento, String email, Perfil perfil, Pageable page) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pessoa> criteriaQuery = criteriaBuilder.createQuery(Pessoa.class);
        Root<Pessoa> pessoaRoot = criteriaQuery.from(Pessoa.class);

        Predicate finalPredicate = getPredicates(nome, telefone, documento, email, perfil, criteriaBuilder, pessoaRoot);
        criteriaQuery.where(finalPredicate);

        criteriaQuery.select(pessoaRoot)
                .orderBy(criteriaBuilder.asc(pessoaRoot.get("nome")));

        Query query = entityManager.createQuery(criteriaQuery)
                .setFirstResult(page.getPageNumber())
                .setMaxResults(page.getPageSize());

        return query.getResultList();
    }

    private Predicate getPredicates(String nome, String telefone, String documento, String email, Perfil perfil,
                                    CriteriaBuilder criteriaBuilder, Root<Pessoa> pessoaRoot) {
        List<Predicate> predicates = new ArrayList<>();

        if (telefone != null && !telefone.isBlank()) {
            predicates.add(criteriaBuilder.like(pessoaRoot.get("telefone"), "%" + telefone + "%"));
        }

        if (documento != null && !documento.isBlank()) {
            predicates.add(criteriaBuilder.like(pessoaRoot.get("documento"), "%" + documento + "%"));
        }

        if (nome != null && !nome.trim().isEmpty()) {
            Expression<String> upperColumn = criteriaBuilder.upper(pessoaRoot.get("nome"));
            String nameToSearch = "%" + nome.toUpperCase() + "%";

            predicates.add(criteriaBuilder.like(upperColumn, nameToSearch));
        }

        if (email != null && !email.trim().isEmpty()) {
            Expression<String> upperColumn = criteriaBuilder.upper(pessoaRoot.get("email"));
            String emailToSearch = "%" + email.toUpperCase() + "%";

            predicates.add(criteriaBuilder.like(upperColumn, emailToSearch));
        }

        if (perfil != null) {
            predicates.add(criteriaBuilder.equal(pessoaRoot.get("perfil"), perfil));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
