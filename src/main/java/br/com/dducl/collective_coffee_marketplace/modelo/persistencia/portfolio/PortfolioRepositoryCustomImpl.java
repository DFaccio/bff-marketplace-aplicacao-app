package br.com.dducl.collective_coffee_marketplace.modelo.persistencia.portfolio;

import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Portfolio;
import br.com.dducl.collective_coffee_marketplace.util.enums.StatusPortfolio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

class PortfolioRepositoryCustomImpl implements PortfolioRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Portfolio> findAll(String documento, Integer idFornecedor, LocalDateTime dataEncerramento, StatusPortfolio status, String produto, Pageable pageable) {
        return null;
    }
}
