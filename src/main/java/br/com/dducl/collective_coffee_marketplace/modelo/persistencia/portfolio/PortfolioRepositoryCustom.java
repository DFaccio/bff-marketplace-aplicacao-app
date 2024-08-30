package br.com.dducl.collective_coffee_marketplace.modelo.persistencia.portfolio;

import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Portfolio;
import br.com.dducl.collective_coffee_marketplace.util.enums.StatusPortfolio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

interface PortfolioRepositoryCustom {

    Page<Portfolio> findAll(String documento, Integer idFornecedor, LocalDateTime dataEncerramento, StatusPortfolio status, String produto, Pageable pageable);
}
