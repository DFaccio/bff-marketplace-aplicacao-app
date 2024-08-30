package br.com.dducl.collective_coffee_marketplace.dto;

import br.com.dducl.collective_coffee_marketplace.dto.fornecedor.FornecedorDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"id", "dataCadastro"}, allowGetters = true, ignoreUnknown = true)
public class ProdutoDto extends Dto {

    @NotEmpty
    @Schema(description = "Composição do café", example = "Café torrado moído e aromatizantes")
    @Size(max = 200)
    private String conteudo;

    @NotEmpty
    @Size(max = 200)
    @Schema(description = "Resumo da composição ou até mesmo o nome", example = "Baggio Chocolate Trufado")
    private String descricao;

    @Schema(description = "Disponível para venda")
    private boolean disponivel;

    @Schema(description = "Quantidade do produto", example = "10")
    @PositiveOrZero
    private int quantidade;

    @Schema(description = "Valor do produto", example = "5.99")
    @PositiveOrZero
    private BigDecimal valor;

    @Schema(description = "Data do cadastro", accessMode = Schema.AccessMode.READ_ONLY)
    private String dataCriacao;

    @Schema(description = "Fornecedor do produto")
    private FornecedorDto fornecedor;

}
