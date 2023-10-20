package br.com.dducl.bffmarketplaceapp.modelo.persistencia;

import br.com.dducl.bffmarketplaceapp.modelo.entidades.SenhaUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SenhaUsuarioRepository extends JpaRepository<SenhaUsuario, Integer> {

}
