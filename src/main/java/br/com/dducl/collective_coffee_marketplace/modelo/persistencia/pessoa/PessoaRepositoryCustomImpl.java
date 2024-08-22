package br.com.dducl.collective_coffee_marketplace.modelo.persistencia.pessoa;

import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Pessoa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
}
