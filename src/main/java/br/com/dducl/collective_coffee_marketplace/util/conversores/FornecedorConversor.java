package br.com.dducl.collective_coffee_marketplace.util.conversores;

import br.com.dducl.collective_coffee_marketplace.dto.FornecedorDto;
import br.com.dducl.collective_coffee_marketplace.dto.PessoaDto;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Fornecedor;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class FornecedorConversor implements Conversores<Fornecedor, FornecedorDto> {

    @Resource
    private PessoaConversor pessoaConversor;

    @Override
    public FornecedorDto converte(Fornecedor entidade) {
        FornecedorDto dto = new FornecedorDto();

        PessoaDto pessoaDto = pessoaConversor.converte(entidade.getPessoa());

        dto.setInformacoes(pessoaDto);
        dto.setRazaoSocial(entidade.getRazaoSocial());
        dto.setId(entidade.getId());

        return dto;
    }

    @Override
    public Fornecedor converte(FornecedorDto dto) throws ValidationsException {
        Fornecedor fornecedor = new Fornecedor();

        fornecedor.setId(dto.getId());
        fornecedor.setRazaoSocial(dto.getRazaoSocial());
        fornecedor.setPessoa(pessoaConversor.converte(dto.getInformacoes()));

        return fornecedor;
    }
}
