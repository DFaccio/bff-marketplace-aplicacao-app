package br.com.dducl.collective_coffee_marketplace.dto.portfolio;

import br.com.dducl.collective_coffee_marketplace.dto.ProdutoPortfolioDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioDto extends PortfolioResumidoDto {

    @NotNull
    private List<ProdutoPortfolioDto> produtos;
}
