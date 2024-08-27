package br.com.dducl.collective_coffee_marketplace.modelo.persistencia.pessoa;

import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Pessoa;
import br.com.dducl.collective_coffee_marketplace.util.enums.Perfil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

interface PessoaRepositoryCustom {

    Pessoa insert(Pessoa pessoa);

    Page<Pessoa> findAll(String nome, String telefone, String documento, String email, Perfil perfil, Pageable pageable);
}
