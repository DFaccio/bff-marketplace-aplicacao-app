package br.com.dducl.bffmarketplaceapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UsuarioCadastroDto extends UsuarioDto {

    @NotBlank(message = "Senha é um campo obrigatório")
    private String password;

    @NotBlank(message = "O identificador da pessoa é um campo obrigatório")
    private String pessoaId;
}