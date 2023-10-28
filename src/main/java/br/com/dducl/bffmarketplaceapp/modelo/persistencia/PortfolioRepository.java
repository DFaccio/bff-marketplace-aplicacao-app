package br.com.dducl.bffmarketplaceapp.modelo.persistencia;

import br.com.dducl.bffmarketplaceapp.modelo.entidades.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Integer> {
    Optional<Portfolio> findPortfolioByIdEquals(Integer id);
}
