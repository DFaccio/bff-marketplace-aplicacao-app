package br.com.dducl.collective_coffee_marketplace.dto;

import br.com.dducl.collective_coffee_marketplace.dto.pessoa.PessoaDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GrupoCompraDto extends Dto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String dataCriacao;

    @NotBlank(message = "Nome do Grupo é obrigatório")
    private String nome;

    private boolean ativo;

    @NotNull
    private PessoaDto administrador;
}
