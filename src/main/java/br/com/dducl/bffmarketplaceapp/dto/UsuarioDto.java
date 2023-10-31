package br.com.dducl.bffmarketplaceapp.dto;

import br.com.dducl.bffmarketplaceapp.util.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = {"id"})
public class UsuarioDto extends Dto {

    @NotBlank(message = "Usuário é um campo obrigatório")
    private String username;

    private Perfil perfil;
}