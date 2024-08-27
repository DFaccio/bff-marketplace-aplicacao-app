package br.com.dducl.collective_coffee_marketplace.negocio;

import br.com.dducl.collective_coffee_marketplace.dto.fornecedor.FornecedorDto;
import br.com.dducl.collective_coffee_marketplace.dto.pessoa.PessoaInfoDto;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Fornecedor;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Pessoa;
import br.com.dducl.collective_coffee_marketplace.modelo.persistencia.fornecedor.FornecedorRepository;
import br.com.dducl.collective_coffee_marketplace.modelo.persistencia.pessoa.PessoaRepository;
import br.com.dducl.collective_coffee_marketplace.util.Pagination;
import br.com.dducl.collective_coffee_marketplace.util.ResultadoPaginado;
import br.com.dducl.collective_coffee_marketplace.util.conversores.FornecedorConversor;
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
public class FornecedorBusiness {

    private final FornecedorRepository repository;

    private final FornecedorConversor conversor;

    private final PessoaRepository pessoaRepository;

    public FornecedorBusiness(FornecedorRepository repository, FornecedorConversor conversor, PessoaRepository pessoaRepository) {
        this.repository = repository;
        this.conversor = conversor;
        this.pessoaRepository = pessoaRepository;
    }

    public ResultadoPaginado<FornecedorDto> findAll(String razaoSocial, String telefone, String documento, String email, Pagination page) {
        Pageable pageable = PageRequest.of(page.getPage(), page.getPageSize(), Sort.by("pessoa.nome"));

        Page<Fornecedor> pagina = repository.findAll(razaoSocial, telefone, documento, email, pageable);

        return conversor.converteEntidades(pagina);
    }

    public FornecedorDto insert(PessoaInfoDto pessoaInfoDto) throws ValidationsException {
        Optional<Fornecedor> optionalFornecedor = repository.findByPessoaDocumento(pessoaInfoDto.getDocumento());

        if (optionalFornecedor.isPresent()) {
            throw new ValidationsException("FORNECEDOR_JA_CADASTRADO");
        }

        Fornecedor fornecedor = validateAndInsertPessoa(pessoaInfoDto);

        fornecedor = repository.save(fornecedor);

        return conversor.converte(fornecedor);
    }

    private Fornecedor validateAndInsertPessoa(PessoaInfoDto fornecedorDto) throws ValidationsException {
        validaAndNormalizaDocumento(fornecedorDto);

        Fornecedor fornecedor = conversor.converte(fornecedorDto);
        Optional<Pessoa> optionalPessoa = pessoaRepository.findPessoaByDocumentoEquals(fornecedorDto.getDocumento());

        if (optionalPessoa.isPresent()) {
            fornecedor.setPessoa(optionalPessoa.get());
        } else {
            fornecedor.setPessoa(pessoaRepository.insert(fornecedor.getPessoa()));
        }

        return fornecedor;
    }

    private void validaAndNormalizaDocumento(PessoaInfoDto pessoa) throws ValidationsException {
        String documento = pessoa.getDocumento().replaceAll("\\D", "");

        if (documento.isBlank() || (documento.length() != 11 && documento.length() != 14)) {
            throw new ValidationsException("DOCUMENTO_INVALIDO");
        }
    }

    public FornecedorDto updateToVendor(String documento, String razaoSocial) throws ValidationsException, NotFoundException {
        Optional<Fornecedor> optionalFornecedor = repository.findByPessoaDocumento(documento);

        if (optionalFornecedor.isPresent()) {
            throw new ValidationsException("FORNECEDOR_JA_CADASTRADO");
        }

        Optional<Pessoa> optionalPessoa = pessoaRepository.findPessoaByDocumentoEquals(documento);

        if (optionalPessoa.isEmpty()) {
            throw new NotFoundException("PESSOA_DOCUMENTO_NAO_ENCONTRADO");
        }

        validarRazaoSocial(razaoSocial);

        Fornecedor fornecedor = Fornecedor.builder()
                .pessoa(optionalPessoa.get())
                .razaoSocial(razaoSocial)
                .build();

        fornecedor.getPessoa().setPerfil(Perfil.FORNECEDOR);

        fornecedor = repository.save(fornecedor);

        return conversor.converte(fornecedor);
    }

    private static void validarRazaoSocial(String razaoSocial) throws ValidationsException {
        if (razaoSocial == null || razaoSocial.isBlank()) {
            throw new ValidationsException("RAZAO_SOCIAL_OBRIGATORIA");
        }
    }

    public FornecedorDto findFornecedorByDocumento(String documento) throws NotFoundException {
        Optional<Fornecedor> optional = repository.findByPessoaDocumento(documento);

        if (optional.isEmpty()) {
            throw new NotFoundException("PESSOA_DOCUMENTO_NAO_ENCONTRADO");
        }

        return conversor.converte(optional.get());
    }

    public FornecedorDto updateRazaoSocial(String documento, String razao) throws NotFoundException, ValidationsException {
        validarRazaoSocial(razao);

        Optional<Fornecedor> optional = repository.findByPessoaDocumento(documento);

        if (optional.isEmpty()) {
            throw new NotFoundException("PESSOA_DOCUMENTO_NAO_ENCONTRADO");
        }

        Fornecedor fornecedor = optional.get();
        fornecedor.setRazaoSocial(razao);

        fornecedor = repository.save(fornecedor);

        return conversor.converte(fornecedor);
    }
}
