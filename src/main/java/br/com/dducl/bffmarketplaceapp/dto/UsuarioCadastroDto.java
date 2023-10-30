package br.com.dducl.bffmarketplaceapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UsuarioCadastroDto extends UsuarioDto {

    private String password;

    private String pessoaId;
}
