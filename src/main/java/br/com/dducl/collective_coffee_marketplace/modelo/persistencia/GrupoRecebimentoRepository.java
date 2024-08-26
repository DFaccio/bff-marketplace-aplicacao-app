package br.com.dducl.collective_coffee_marketplace.modelo.persistencia;

import br.com.dducl.collective_coffee_marketplace.modelo.entidades.GrupoRecebimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRecebimentoRepository extends JpaRepository<GrupoRecebimento, Integer> {
}
