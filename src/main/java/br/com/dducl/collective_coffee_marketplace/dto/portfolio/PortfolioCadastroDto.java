package br.com.dducl.collective_coffee_marketplace.dto.portfolio;

import br.com.dducl.collective_coffee_marketplace.dto.portfolio_produto.ProdutoPortfolioCadastroDto;
import br.com.dducl.collective_coffee_marketplace.util.enums.StatusPortfolio;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public record PortfolioCadastroDto(

        @NotNull
        @Schema(description = "Identificação do fornecedor", example = "1")
        Integer fornecedorId,

        @Schema(description = "Informações gerais a respeito")
        @Size(max = 200)
        String descricao,

        @Schema(accessMode = Schema.AccessMode.READ_ONLY, allowableValues = {"ABERTO", "FECHADO"})
        StatusPortfolio status,

        @Pattern(regexp = "[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}")
        String dataVigencia,

        @NotNull
        List<ProdutoPortfolioCadastroDto> produtos
) {
}
