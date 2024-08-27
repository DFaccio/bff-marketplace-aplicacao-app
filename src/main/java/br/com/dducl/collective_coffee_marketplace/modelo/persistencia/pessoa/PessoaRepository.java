package br.com.dducl.collective_coffee_marketplace.modelo.persistencia.pessoa;

import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Pessoa;
import br.com.dducl.collective_coffee_marketplace.util.enums.Perfil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, String>, PessoaRepositoryCustom {

    Optional<Pessoa> findPessoaByDocumentoEquals(String identificador);

    Page<Pessoa> findAllByPerfilEquals(Perfil perfil, Pageable pageable);

    Optional<Pessoa> findPessoaByDocumentoAndPerfil(String documento, Perfil perfil);

    List<Pessoa> findByDocumentoIn(List<String> documentos);
}