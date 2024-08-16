package br.com.dducl.collective_coffee_marketplace.util.conversores;

import br.com.dducl.collective_coffee_marketplace.dto.UsuarioDto;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioConversor implements Conversores<Usuario, UsuarioDto> {

    @Override
    public UsuarioDto converte(Usuario entidade) {
        UsuarioDto usuarioDto = new UsuarioDto();

        usuarioDto.setPerfil(entidade.getPerfil());
        usuarioDto.setUsername(entidade.getNome());

        return usuarioDto;
    }

    @Override
    public Usuario converte(UsuarioDto dto) {
        Usuario usuario = new Usuario();

        usuario.setPerfil(dto.getPerfil());
        usuario.setNome(dto.getUsername());

        return usuario;
    }
}
