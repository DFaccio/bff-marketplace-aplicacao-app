package br.com.dducl.collective_coffee_marketplace.modelo.persistencia;

import br.com.dducl.collective_coffee_marketplace.modelo.entidades.GrupoCompra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoCompraRepository extends JpaRepository<GrupoCompra, Integer> {

    Page<GrupoCompra> findByAtivo(boolean status, Pageable pageable);

    Page<GrupoCompra> findByAtivoAndNome(boolean status, String nome, Pageable pageable);
}
