package br.com.dducl.bffmarketplaceapp.controller;

import br.com.dducl.bffmarketplaceapp.dto.UsuarioDto;
import br.com.dducl.bffmarketplaceapp.negocio.UsuarioBusiness;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {

    @Resource
    private UsuarioBusiness usuarioBusiness;

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody UsuarioDto dto) {
        usuarioBusiness.insert(dto);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/new/username/{username}")
    public ResponseEntity<Void> updateUsername(@PathVariable String username, @RequestParam String newUsername) {
        usuarioBusiness.update(username, newUsername);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/new/password")
    public ResponseEntity<Void> updatePassword(@RequestBody UsuarioDto usuarioDto) {
        usuarioBusiness.updatePassword(usuarioDto);

        return ResponseEntity.noContent().build();
    }
}
