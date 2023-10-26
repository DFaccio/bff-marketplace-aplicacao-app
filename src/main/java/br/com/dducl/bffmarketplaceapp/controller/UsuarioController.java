package br.com.dducl.bffmarketplaceapp.controller;

import br.com.dducl.bffmarketplaceapp.dto.UsuarioCadastroDto;
import br.com.dducl.bffmarketplaceapp.dto.UsuarioDto;
import br.com.dducl.bffmarketplaceapp.negocio.UsuarioBusiness;
import br.com.dducl.bffmarketplaceapp.util.exceptions.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {

    @Resource
    private UsuarioBusiness usuarioBusiness;

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody UsuarioCadastroDto dto) throws ValidationsException {
        usuarioBusiness.insert(dto);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/new/username/{username}")
    public ResponseEntity<Void> updateUsername(@PathVariable String username, @RequestBody UsuarioDto usuarioDto) {
        usuarioBusiness.update(username, usuarioDto);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/new/password/{username}")
    public ResponseEntity<Void> updatePassword(@PathVariable String username, @RequestBody String password) {
        usuarioBusiness.updatePassword(username, password);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<UsuarioDto> findByUsername(@PathVariable String username) {
        return ResponseEntity.ok(usuarioBusiness.findByUsername(username));
    }
}
