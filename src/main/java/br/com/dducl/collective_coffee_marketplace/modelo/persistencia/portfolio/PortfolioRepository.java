package br.com.dducl.collective_coffee_marketplace.modelo.persistencia.portfolio;

import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Fornecedor;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Integer>, PortfolioRepositoryCustom {

    Optional<Portfolio> findPortfolioById(Integer id);

    Optional<Portfolio> findByDescricaoEqualsIgnoreCase(String descricao);

    Optional<Portfolio> findPortfolioByFornecedorAndDescricao(Fornecedor fornecedor, String descricao);
}