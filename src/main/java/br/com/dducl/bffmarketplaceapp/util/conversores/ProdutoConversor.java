package br.com.dducl.bffmarketplaceapp.util.conversores;

import br.com.dducl.bffmarketplaceapp.dto.ProdutoDto;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoConversor implements Conversores<Produto, ProdutoDto>{

    @Override
    public ProdutoDto converte(Produto entidade){
        ProdutoDto dto = new ProdutoDto();

        dto.setDescricao(entidade.getDescricao());
        dto.setConteudo(entidade.getConteudo());
        dto.setQuantidade(entidade.getQuantidade());
        dto.setValor(entidade.getValor());
        dto.setDisponivel(entidade.isDisponivel());
        dto.setDataCriacao(entidade.getDataCriacao().toString());

        return dto;

    }

    @Override
    public Produto converte(ProdutoDto dto) {
        Produto produto = new Produto();

        produto.setDescricao(dto.getDescricao());
        produto.setConteudo(dto.getConteudo());
        produto.setQuantidade(dto.getQuantidade());
        produto.setValor(dto.getValor());
        produto.setDisponivel(dto.isDisponivel());

        return produto;
    }
}
