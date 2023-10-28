package br.com.dducl.bffmarketplaceapp.util.conversores;

import br.com.dducl.bffmarketplaceapp.dto.PortfolioDto;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.Portfolio;
import org.springframework.stereotype.Component;

@Component
public class PortfolioConversor implements Conversores<Portfolio, PortfolioDto> {
    @Override
    public PortfolioDto converte(Portfolio entidade) {
        PortfolioDto dto = new PortfolioDto();

        dto.setIdFornecedor(entidade.getIdFornecedor());
        dto.setDataVigencia(entidade.getDataVigencia().toString());
        dto.setDataCriacao(entidade.getDataCriacao().toString());

        /* TODO RETORNAR A LISTA DE PRODUTOS
            List<Integer> produtos = entidade.getProduto()
                .stream()
                .filter(Produto::disponivel)
                .map(Produto::getProduto)
                .toList();
        */

        return dto;
    }

    @Override
    public Portfolio converte(PortfolioDto dto) {
        Portfolio portfolio = new Portfolio();
        portfolio.setIdFornecedor(dto.getIdFornecedor());

        /*  TODO A DATA DE VIGENCIA É INSERIDA COMO UMA STRING CONVERTER PARA UM CAMPO DATETIME
            String dataAsString = dto.getDataVigencia();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
            portfolio.setDataVigencia();
         */
        return portfolio;
    }
}
