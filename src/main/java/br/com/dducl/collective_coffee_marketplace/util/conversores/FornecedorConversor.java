package br.com.dducl.collective_coffee_marketplace.util.conversores;

import br.com.dducl.collective_coffee_marketplace.dto.FornecedorCadastro;
import br.com.dducl.collective_coffee_marketplace.dto.FornecedorDto;
import br.com.dducl.collective_coffee_marketplace.dto.pessoa.PessoaInfoDto;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Fornecedor;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.ValidationsException;
import org.springframework.stereotype.Component;

@Component
public class FornecedorConversor implements Conversores<Fornecedor, FornecedorDto> {

    private final PessoaConversor pessoaConversor;

    private final EnderecoConversor enderecoConversor;

    private final ChavePixConversor chavePixConversor;

    public FornecedorConversor(PessoaConversor pessoaConversor, EnderecoConversor enderecoConversor, ChavePixConversor chavePixConversor) {
        this.pessoaConversor = pessoaConversor;
        this.enderecoConversor = enderecoConversor;
        this.chavePixConversor = chavePixConversor;
    }


    @Override
    public Fornecedor converte(FornecedorDto dto) throws ValidationsException {
        return Fornecedor.builder()
                .razaoSocial(dto.getRazaoSocial())
                .pessoa(pessoaConversor.converte(dto))
                .build();
    }

    @Override
    public FornecedorDto converte(Fornecedor entidade) {
        FornecedorDto fornecedorDto = new FornecedorDto();
        fornecedorDto.setId(entidade.getId());
        fornecedorDto.setRazaoSocial(entidade.getRazaoSocial());
        fornecedorDto.setNome(entidade.getPessoa().getNome());
        fornecedorDto.setDocumento(entidade.getPessoa().getDocumento());
        fornecedorDto.setEmail(entidade.getPessoa().getEmail());
        fornecedorDto.setTelefone(entidade.getPessoa().getTelefone());
        fornecedorDto.setDataCadastro(entidade.getPessoa().getDataCadastro().toString());
        fornecedorDto.setAtivo(entidade.getPessoa().isAtivo());
        fornecedorDto.setPerfil(entidade.getPessoa().getPerfil());
        fornecedorDto.setEndereco(entidade.getPessoa().getEndereco() == null ? null : enderecoConversor.converte(entidade.getPessoa().getEndereco()));
        fornecedorDto.setChavesPix(chavePixConversor.converteEntidades(entidade.getPessoa().getChaves()));

        return fornecedorDto;
    }

    public Fornecedor converte(PessoaInfoDto dto) throws ValidationsException {
        return Fornecedor.builder()
                .razaoSocial(((FornecedorCadastro) dto.getPerfilDto()).getRazaoSocial())
                .pessoa(pessoaConversor.converte(dto))
                .build();
    }
}
