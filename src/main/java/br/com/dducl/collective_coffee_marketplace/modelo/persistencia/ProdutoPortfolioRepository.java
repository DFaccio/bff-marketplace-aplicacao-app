package br.com.dducl.collective_coffee_marketplace.modelo.persistencia;

import br.com.dducl.collective_coffee_marketplace.modelo.entidades.ProdutoPortfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoPortfolioRepository extends JpaRepository<ProdutoPortfolio, Integer> {
}
