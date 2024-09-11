package br.com.dducl.collective_coffee_marketplace.dto;

import br.com.dducl.collective_coffee_marketplace.util.enums.TipoDesconto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProdutoPortfolioDto extends Dto {

    @Schema(description = "Identificador do produto", example = "1")
    @NotNull
    Integer produtoId;

    @Schema(description = "Novo valor a ser considerado para o portfólio. Quando não informado é considerado o valor do produto", example = "2.66")
    @PositiveOrZero
    private BigDecimal valor;

    @Schema(description = "Quantidade a ser considerada para aplicação de desconto", example = "26")
    @PositiveOrZero
    private Integer quantidade;

    @Schema(description = "Valor do desconto", example = "15")
    @PositiveOrZero
    private BigDecimal desconto;

    @Schema(description = "Formato que o desconto deve ser calculado", example = "PORCENTAGEM")
    private TipoDesconto tipoDesconto;
}
