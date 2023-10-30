package br.com.dducl.bffmarketplaceapp.modelo.persistencia;

import br.com.dducl.bffmarketplaceapp.modelo.entidades.Portfolio;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.PortfolioProdutos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioProdutosRepository extends JpaRepository<PortfolioProdutos, Integer> {
    Iterable<PortfolioProdutos> findByPortfolioEquals(Portfolio portfolio);
}