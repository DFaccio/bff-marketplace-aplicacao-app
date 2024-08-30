package br.com.dducl.collective_coffee_marketplace.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GrupoRecebimentoDto extends Dto {

    @Schema(description = "Nome para o grupo de recebimento ou pessoa responsável")
    @NotEmpty
    private String nome;

    @Email(message = "Não é um e-mail válido")
    @NotBlank
    @Schema(example = "ana@email.com")
    private String email;

    @NotBlank
    @Pattern(regexp = "\\+55 [0-9]{3} 9 [0-9]{4}-[0-9]{4}")
    private String telefone;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String dataCadastro;

    private boolean ativo;

    @NotNull
    private EnderecoDto endereco;

    @Builder
    public GrupoRecebimentoDto(Integer id, String nome, String email, String telefone, String dataCadastro, boolean ativo, EnderecoDto endereco) {
        super(id);
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.dataCadastro = dataCadastro;
        this.ativo = ativo;
        this.endereco = endereco;
    }
}
