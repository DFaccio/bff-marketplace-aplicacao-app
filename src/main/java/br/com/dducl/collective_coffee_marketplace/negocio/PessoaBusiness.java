package br.com.dducl.collective_coffee_marketplace.negocio;

import br.com.dducl.collective_coffee_marketplace.dto.EnderecoDto;
import br.com.dducl.collective_coffee_marketplace.dto.pessoa.PessoaDto;
import br.com.dducl.collective_coffee_marketplace.dto.pessoa.PessoaInfoDto;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Pessoa;
import br.com.dducl.collective_coffee_marketplace.modelo.persistencia.pessoa.PessoaRepository;
import br.com.dducl.collective_coffee_marketplace.util.Pagination;
import br.com.dducl.collective_coffee_marketplace.util.ResultadoPaginado;
import br.com.dducl.collective_coffee_marketplace.util.conversores.PessoaConversor;
import br.com.dducl.collective_coffee_marketplace.util.enums.Perfil;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.NotFoundException;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.ValidationsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PessoaBusiness {

    private final PessoaConversor conversor;

    private final PessoaRepository repository;

    public PessoaBusiness(PessoaConversor conversor, PessoaRepository repository) {
        this.conversor = conversor;
        this.repository = repository;
    }

    public ResultadoPaginado<PessoaDto> findAll(Perfil perfil, Pagination page) {
        Pageable pageable = PageRequest.of(page.getPage(), page.getPageSize(), Sort.by("documento"));

        Page<Pessoa> pagina = repository.findAllByPerfilEquals(perfil, pageable);

        return conversor.converteEntidades(pagina);
    }

    public PessoaDto findByIdentificador(Perfil perfil, String identificador) throws NotFoundException {
        Optional<Pessoa> pessoa = repository.findPessoaByDocumentoAndPerfil(identificador, perfil);

        if (pessoa.isEmpty()) {
            throw new NotFoundException("PESSOA_DOCUMENTO_NAO_ENCONTRADO");
        }

        return conversor.converte(pessoa.get());
    }

    public PessoaDto insert(PessoaInfoDto pessoaInfoDto) throws ValidationsException {
        validaAndNormalizaDocumento(pessoaInfoDto);

        Optional<Pessoa> optional = repository.findPessoaByDocumentoEquals(pessoaInfoDto.getDocumento());

        if (optional.isPresent()) {
            throw new ValidationsException("PESSOA_JA_CADASTRADA");
        }

        Pessoa pessoa = conversor.converte(pessoaInfoDto);

        pessoa = repository.insert(pessoa);

        return conversor.converte(pessoa);
    }

    private void validaAndNormalizaDocumento(PessoaInfoDto pessoa) throws ValidationsException {
        String documento = pessoa.getDocumento().replaceAll("\\D", "");

        if (documento.isBlank() || (documento.length() != 11 && documento.length() != 14)) {
            throw new ValidationsException("DOCUMENTO_INVALIDO");
        }
    }

    public PessoaDto update(String documento, PessoaDto pessoa) throws NotFoundException, ValidationsException {
        Optional<Pessoa> optional = repository.findPessoaByDocumentoEquals(documento);

        if (optional.isEmpty()) {
            throw new NotFoundException("PESSOA_DOCUMENTO_NAO_ENCONTRADO");
        }

        Pessoa toUpdate = optional.get();

        toUpdate.setAtivo(pessoa.isAtivo());
        toUpdate.setNome(pessoa.getNome());
        toUpdate.setEmail(pessoa.getEmail());
        toUpdate.setTelefone(pessoa.getTelefone());

        updateEndereco(toUpdate, pessoa.getEndereco());

        toUpdate = repository.save(toUpdate);

        return conversor.converte(toUpdate);
    }

    private void updateEndereco(Pessoa saved, EnderecoDto enderecoDto) {
        saved.getEndereco().setApelido(enderecoDto.getApelido());
        saved.getEndereco().setLogradouro(enderecoDto.getLogradouro());
        saved.getEndereco().setNumero(enderecoDto.getNumero());
        saved.getEndereco().setBairro(enderecoDto.getBairro());
        saved.getEndereco().setCidade(enderecoDto.getCidade());
        saved.getEndereco().setEstado(enderecoDto.getBairro());
        saved.getEndereco().setComplemento(enderecoDto.getComplemento());
        saved.getEndereco().setCep(enderecoDto.getCep());
    }
}