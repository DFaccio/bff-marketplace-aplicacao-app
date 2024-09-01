package br.com.dducl.collective_coffee_marketplace.dto.portfolio;

import br.com.dducl.collective_coffee_marketplace.dto.Dto;
import br.com.dducl.collective_coffee_marketplace.util.enums.StatusPortfolio;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
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
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class PortfolioResumidoDto extends Dto {

    @Schema(description = "Informações gerais a respeito")
    @Size(max = 200)
    private String descricao;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String dataCriacao;

    @Pattern(regexp = "[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}")
    @Schema(description = "Data de encerramento", example = "2025-10-03T10:15")
    private String dataVigencia;

    @Schema(description = "Status do portfólio", example = "ABERTO")
    private StatusPortfolio status;
}