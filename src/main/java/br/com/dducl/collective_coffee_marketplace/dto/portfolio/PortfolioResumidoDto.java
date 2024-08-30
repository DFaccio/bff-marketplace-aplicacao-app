package br.com.dducl.collective_coffee_marketplace.dto.portfolio;

import br.com.dducl.collective_coffee_marketplace.dto.Dto;
import br.com.dducl.collective_coffee_marketplace.dto.fornecedor.FornecedorDto;
import br.com.dducl.collective_coffee_marketplace.util.enums.StatusPortfolio;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioResumidoDto extends Dto {

    @NotNull
    private FornecedorDto fornecedor;

    @Schema(description = "Informações gerais a respeito")
    @Size(max = 200)
    private String descricao;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String dataCriacao;

    @Pattern(regexp = "[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}")
    private String dataVigencia;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private StatusPortfolio status;
}