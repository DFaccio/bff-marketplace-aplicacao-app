package br.com.dducl.collective_coffee_marketplace.modelo.persistencia.pessoa;

import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Pessoa;

interface PessoaRepositoryCustom {

    Pessoa insert(Pessoa pessoa);
}
