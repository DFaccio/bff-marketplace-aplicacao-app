package br.com.dducl.bffmarketplaceapp.modelo.persistencia;

import br.com.dducl.bffmarketplaceapp.modelo.entidades.Fornecedor;
import org.springframework.stereotype.Repository;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Integer> {

    Optional<Portfolio> findPortfolioById(Integer id);

    Optional<Portfolio> findByDescricaoEqualsIgnoreCase(String descricao);

    Optional<Portfolio> findPortfolioByFornecedorAndDescricao(Fornecedor fornecedor, String descricao);
}