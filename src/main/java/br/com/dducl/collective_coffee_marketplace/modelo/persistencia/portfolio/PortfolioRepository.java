package br.com.dducl.collective_coffee_marketplace.modelo.persistencia.portfolio;

import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Integer>, PortfolioRepositoryCustom {

}