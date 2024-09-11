package br.com.dducl.collective_coffee_marketplace.negocio;

import br.com.dducl.collective_coffee_marketplace.dto.GrupoCompraFullDto;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.GrupoCompra;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Pessoa;
import br.com.dducl.collective_coffee_marketplace.modelo.persistencia.GrupoCompraRepository;
import br.com.dducl.collective_coffee_marketplace.modelo.persistencia.pessoa.PessoaRepository;
import br.com.dducl.collective_coffee_marketplace.util.Pagination;
import br.com.dducl.collective_coffee_marketplace.util.ResultadoPaginado;
import br.com.dducl.collective_coffee_marketplace.util.conversores.GrupoCompraConversor;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.NotFoundException;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.ValidationsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GrupoCompraBusiness {

    private final GrupoCompraRepository repository;

    private final GrupoCompraConversor conversor;

    private final PessoaRepository pessoaRepository;

    public GrupoCompraBusiness(GrupoCompraRepository repository, GrupoCompraConversor conversor, PessoaRepository pessoaRepository) {
        this.repository = repository;
        this.conversor = conversor;
        this.pessoaRepository = pessoaRepository;
    }

    public GrupoCompraFullDto insert(GrupoCompraFullDto grupoCompraDto) throws NotFoundException, ValidationsException {
        GrupoCompra grupoCompra = conversor.converte(grupoCompraDto);
        grupoCompra.setDataCriacao(LocalDateTime.now());

        if (!grupoCompra.getPessoas().isEmpty()) {
            validateAndInsertPessoas(grupoCompra);
        }

        grupoCompra = repository.save(grupoCompra);

        return conversor.converte(grupoCompra);
    }

    private void validateAndInsertPessoas(GrupoCompra grupoCompra) throws ValidationsException {
        List<String> documentos = grupoCompra.getPessoas().stream()
                .map(Pessoa::getDocumento)
                .toList();

        List<Pessoa> pessoasCadastradas = pessoaRepository.findByDocumentoIn(documentos);

        if (pessoasCadastradas.isEmpty() || pessoasCadastradas.size() != documentos.size()) {
            throw new ValidationsException("PESSOA_GRUPO_COMPRA_NAO_EXISTE");
        }

        grupoCompra.setPessoas(pessoasCadastradas);

        grupoCompra.setAdministrador(getAdministrador(grupoCompra.getAdministrador().getDocumento()));
    }

    private Pessoa getAdministrador(String documento) throws ValidationsException {
        Optional<Pessoa> administradorOptional = pessoaRepository.findPessoaByDocumentoEquals(documento);

        if (administradorOptional.isEmpty()) {
            throw new ValidationsException("ADM_NAO_IDENTIFICADO");
        }

        return administradorOptional.get();
    }

    public GrupoCompraFullDto update(GrupoCompraFullDto grupoCompraDto) throws NotFoundException, ValidationsException {
        Optional<GrupoCompra> optional = repository.findById(grupoCompraDto.getId());

        if (optional.isEmpty()) {
            throw new NotFoundException("GRUPO_COMPRA_NAO_ENCONTRADO");
        }

        GrupoCompra toUpdate = optional.get();

        if (!toUpdate.isAtivo()) {
            throw new ValidationsException("GRUPO_DESATIVADO_NAO_ATUALIZA");
        }

        GrupoCompra newValues = conversor.converte(grupoCompraDto);

        toUpdate.setNome(newValues.getNome());
        toUpdate.setAtivo(newValues.isAtivo());

        if (!Objects.equals(toUpdate.getAdministrador().getDocumento(), newValues.getAdministrador().getDocumento())) {
            toUpdate.setAdministrador(getAdministrador(newValues.getAdministrador().getDocumento()));
        }

        validaAndUpdatePessoas(toUpdate, newValues.getPessoas());

        toUpdate = repository.save(toUpdate);

        return conversor.converte(toUpdate);
    }

    private void validaAndUpdatePessoas(GrupoCompra toUpdate, List<Pessoa> pessoas) throws ValidationsException {
        List<String> pessoasPermanecem = pessoas.stream()
                .map(Pessoa::getDocumento)
                .toList();

        List<Pessoa> saved = pessoaRepository.findByDocumentoIn(pessoasPermanecem);

        if (saved.isEmpty() || saved.size() != pessoasPermanecem.size()) {
            throw new ValidationsException("PESSOA_GRUPO_COMPRA_NAO_EXISTE");
        }

        toUpdate.setPessoas(saved);
    }

    public ResultadoPaginado<GrupoCompraFullDto> findAll(boolean status, String nome, Pagination page) {
        Pageable pageable = PageRequest.of(page.getPage(), page.getPageSize(), Sort.by(Sort.Order.asc("dataCriacao")));

        Page<GrupoCompra> pagina;

        if (nome != null && !nome.isBlank()) {
            pagina = repository.findByAtivoAndNome(status, nome, pageable);
        } else {
            pagina = repository.findByAtivo(status, pageable);
        }

        return conversor.converteEntidades(pagina);
    }
}
