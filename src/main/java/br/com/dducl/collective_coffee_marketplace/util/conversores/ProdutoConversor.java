package br.com.dducl.collective_coffee_marketplace.util.conversores;

import br.com.dducl.collective_coffee_marketplace.dto.ProdutoDto;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Produto;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.ValidationsException;
import org.springframework.stereotype.Component;

@Component
public class ProdutoConversor implements Conversores<Produto, ProdutoDto> {

    private final FornecedorConversor fornecedorConversor;

    public ProdutoConversor(FornecedorConversor fornecedorConversor) {
        this.fornecedorConversor = fornecedorConversor;
    }


    @Override
    public ProdutoDto converte(Produto entidade) {
        ProdutoDto dto = new ProdutoDto();

        dto.setId(entidade.getId());
        dto.setDescricao(entidade.getDescricao());
        dto.setConteudo(entidade.getConteudo());
        dto.setQuantidade(entidade.getQuantidade());
        dto.setValor(entidade.getValor());
        dto.setDisponivel(entidade.isDisponivel());
        dto.setDataCriacao(entidade.getDataCriacao().toString());
        dto.setFornecedor(fornecedorConversor.converte(entidade.getFornecedor()));

        return dto;
    }

    @Override
    public Produto converte(ProdutoDto dto) throws ValidationsException {
        Produto produto = new Produto();

        produto.setId(dto.getId());
        produto.setDescricao(dto.getDescricao());
        produto.setConteudo(dto.getConteudo());
        produto.setQuantidade(dto.getQuantidade());
        produto.setValor(dto.getValor());
        produto.setDisponivel(dto.isDisponivel());

        if (dto.getFornecedor() != null) {
            produto.setFornecedor(fornecedorConversor.converte(dto.getFornecedor()));
        }

        return produto;
    }
}
