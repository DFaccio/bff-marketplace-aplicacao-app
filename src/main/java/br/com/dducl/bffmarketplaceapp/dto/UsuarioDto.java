package br.com.dducl.bffmarketplaceapp.dto;

import br.com.dducl.bffmarketplaceapp.util.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = {"id"})
public class UsuarioDto extends Dto {

    private String username;

    private Perfil perfil;
}
