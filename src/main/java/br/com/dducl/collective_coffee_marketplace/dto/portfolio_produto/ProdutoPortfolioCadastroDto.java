package br.com.dducl.collective_coffee_marketplace.dto.portfolio_produto;

import br.com.dducl.collective_coffee_marketplace.util.enums.TipoDesconto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ProdutoPortfolioCadastroDto(
        @Schema(description = "Identificador do produto", example = "1")
        @NotNull
        Integer produtoId,

        @Schema(description = "Novo valor a ser considerado para o portifólio", example = "2.66")
        @PositiveOrZero
        BigDecimal valor,

        @Schema(description = "Quantidade a ser considerada para aplicação de desconto", example = "26")
        @PositiveOrZero
        Integer quantidade,

        @Schema(description = "Valor do desconto", example = "15")
        @PositiveOrZero
        BigDecimal desconto,

        @Schema(description = "Formato que o desconto deve ser calculado", example = "PORCENTAGEM")
        @NotNull
        TipoDesconto tipoDesconto
) {
}
